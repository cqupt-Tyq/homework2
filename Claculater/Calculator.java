package Claculater;

import com.sun.source.tree.LiteralTree;

import java.util.*;
class overFlow{
    private double answer;
    private final double  upperLimit= 1000000000.0;
    private final double  loweLimit= -1000000000.0;

    public double getNumber() {
        return answer;
    }

    public void setAnswer(double answer) throws spillException {
        if (answer>upperLimit || answer<loweLimit){
            throw new spillException("你所计算的结果超出了计算范围，重新输一次吧");
        }
        this.answer = answer;
    }
}
public class Calculator {
    public static void main(String[] args) {
        System.out.println("please input: ");
        Scanner s =new Scanner(System.in);
        String Str=s.nextLine();
        //取出空格
        String str=Str.trim();
        //解决负数
        //第一步，判断括号是否对称，如果不对称，直接终止程序
        try {
            isSymmetry(str);
        } catch (errorException e) {
            throw new RuntimeException(e);
        }
        //第二步，将字符串转化为list
        List<String> list=StrToList(str);
        //第三步，将list转化为一定出栈顺序
        List<String> OutStack=orderToStack(list);
        //第四步,入栈计算
        double answer=calculator(OutStack);
        //第五步，判断result的值是否溢出
        overFlow num=new overFlow();
        try {
            num.setAnswer(answer);
        } catch (spillException e) {
            throw new RuntimeException(e);
        }
        //输出结果
        System.out.println(answer);
    }

    //判断是否对称
   public static void isSymmetry(String str) throws errorException {
        //设一个list来装str内的内容
       int a = 0,b=0;
       boolean answer=true;//先假设为相等
       List<String> list=new ArrayList<>();
       for(int i=0;i<str.length();i++) {
            char s=str.charAt(i);
            if(str.charAt(0)=='-'){
                answer=false;
            }else{
                list.add(s+"");
                if(s=='['){
                    a++;
                }else if(s=='('){
                    b++;
                }else if(s==']'){
                    a--;
                }else if(s==')'){
                    b--;
                }
            }
       }
       if(a!=0||b!=0){
           answer=false;
       }
       if(!answer){
           throw new errorException("输入的有问题");
       }
   }
    //1、将字符串转化为list结构
    public static List<String> StrToList(String str){
        int i=0;
        List<String> list=new ArrayList<>();
        while(i<str.length()) {
            char s=str.charAt(i);
            //获取str元素的ascll码值
            //这些字符包括了括号和操作符(+,-,*,/)
            if(s!=46 && (s <= 47 || s>= 58)){
                //是操作符，直接添加到list中
                i++ ;
                list.add(s+"");
            }else{
                String str1 = "";
                while (i < str.length() && (str.charAt(i) >47 && str.charAt(i) < 58 || str.charAt(i)==46)){
                    str1+= str.charAt(i);
                    i ++;
                }
                list.add(str1);
            }
            }
        return list;
    }
    //按照list的顺序入栈，方便下一步计算
    public static List<String> orderToStack(List<String> exList){
        //传入由字符串转化来的list
        //先建立一个栈
        Stack<String> operatorStack=new Stack<>();
        //创建一个list保存有序的字符顺序（后缀表达式子，单行逆波兰表达式）CSDN上看到的
        List<String> fixStack = new ArrayList<>(); //用来存储出栈顺序
        Map<String,Integer> operatorLevelMap=new HashMap<>();//
        operatorLevelMap.put("(",0);
        operatorLevelMap.put("+",1);
        operatorLevelMap.put("-",1);
        operatorLevelMap.put("*",2);
        operatorLevelMap.put("/",2);
        operatorLevelMap.put(")",3);
        String[] operator2={"+","-","*","/"};
        //(7+2)/2    （先压到栈里去，7传到list, +压到栈里去   ，2传到list, 遇到），+传到list  ，（弹出栈，   /压到栈里去，
        for(String current:exList){
            //比较两个运算符先后顺序
            if(Arrays.asList(operator2).contains(current)){
                while (!operatorStack.empty()&&operatorLevelMap.get(operatorStack.peek())>=operatorLevelMap.get(current)){
                    ////如果exList的值存在operator2中，并且栈顶不为空，并且Map中代入栈顶的值得到的值大于Map中的值，就弹出并添加到fixStack中
                    fixStack.add(operatorStack.pop());
                }
                //栈顶为空或者栈顶的加减乘除运算符优先级没有current的优先级大，（类如括号没有加号大）就压到栈里面去
                operatorStack.push((current));

            }else if("(".equals(current)){//当current为（压到栈里去
                operatorStack.push(current);
            }else if(")".equals(current)){//当current为）
                while (!operatorStack.empty()&&operatorLevelMap.get(operatorStack.peek())>=operatorLevelMap.get("(")+1){
                    ////如果exList的值存在operator2中，并且栈顶不为空，并且Map中代入栈顶的值得到的值大于等于1，就弹出并添加到fixStack中
                    fixStack.add(operatorStack.pop());
                }
                if(!operatorStack.empty()){
                    operatorStack.pop();
                }
            }else{//当current为数字时，直接传到list里面去
                fixStack.add(current);
            }
            }
        while(!operatorStack.empty()){
            fixStack.add(operatorStack.pop());
        }
        //栈里面的运算符顺序始终上大于下，遇到“)”时候，把栈里面”(“之上的运算符全部传到list里面去，
        //传入（1 + 2 * （3 - 5）） * （2 - 1）
        //list为1,2,3,5,-,*,+,2,1,-,*
        //Stack
        return fixStack;
        }

        //计算结果
        //把上面list中的，第一遇到的符号面前的两个数拿出来进行运算，然后压进去
        public static double calculator(List<String> fixStack) {
        //先准备个栈,直到遇到符号
            Stack<Double> calculator=new Stack<>();
            for(String fix:fixStack){
                if (fix.matches("^([0-9]{1,}[.][0-9]*)$") || fix.matches("^([0-9]{1,})$")){
                    //是数字
                    calculator.push(Double.parseDouble(fix));
        }else{
                    double n2 = calculator.pop();
                    double n1 = calculator.pop();
                    //定义一个double型的变量，来记录两个数的运算结果
                    double res = switch (fix) {
                        case "+" -> n1 + n2;
                        case "-" -> n1 - n2;
                        case "*" -> n1 * n2;
                        case "/" -> n1 / n2;
                        default -> throw new RuntimeException("运算符号错误");
                    };
                    calculator.push(res);
                }

        }
            return calculator.pop();
}}

