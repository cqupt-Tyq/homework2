package DoubleLinked;

public class DoubleLinked {
    public static  class Node<T>{
        public T value;
        Node last;
        Node next;
        public Node(){};
        public Node(T value){
            this.value=value;
        }
        public void setValue(T value){
            this.value=value;
        }
        public T getValue(){
            return this.value;
        }
    }
//测试
    public static void main(String[] args) {
      NewList<Integer> list=new NewList<Integer>(7);
        System.out.println(list.header.getValue());
        Node<Integer> n=new Node<Integer>(8);
        insert(list,n);
        System.out.println(list.header.next.next.getValue());
        reverse(list);
        System.out.println(list.header.next.next.getValue());
    }
    //建一个双向链表
    public static class NewList<T> {
        public int size = 0;
        Node<T> header = new Node<T>();
        Node<T> ender;

        public NewList(T k) {
            Node<T> ml = new Node<T>(k);
            header.next = ml;
            ml.last = header;
            ender = ml;
            size++;
        }
    }
    public static void insert(NewList list,Node n){
        n.last=list.ender;
        list.ender.next=n;
       n.next=null;
       list.ender=n;
       list.size++;
    }
    public static void delete (NewList list,int index)throws errorException{
        if(index==0&&list.size==1){
            list.header.next=null;
            list.ender=list.header;
            list.ender.next=null;
            list.size--;
        }
        else if(index==0&&list.size==0){

        } else if (index == list.size-1 && index != 0){
            Node p=list.ender;
            list.ender=p.last;
            p.last=null;
            list.ender.next=null;
            list.size--;
        } else if (index >= 0 && index < list.size - 1) {
            Node p=list.header;
            for(int i=0;i<index;i++){
                p=p.next;
            }
            p.next.last=null;
            p.next=p.next.next;
            p.next.last=p;
        }else{
            throw new errorException("越界");
        }
    }
    public Node search(NewList list,int index)throws errorException{//查
        Node p = list.header.next;
        if(index>=0&&index<list.size){
            for(int i=0;i<index;i++){
                p = p.next;
            }
            return (Node)p.getValue();
        }else{
            throw new errorException("下标越界");
        }
    }
    public static void ergodic(NewList list){
        Node p=list.header;
        while (p.next!=null){
            System.out.println(p.getValue());
            p=p.next;
        }
    }
    public static void reverse(NewList list) {
        if (list.header.next == list.ender) {
            return;
        }
        Node n = new Node();
        n.next = list.ender;
        Node node = list.header.next;
        Node cn;
        while (node != list.ender) {
            cn = node.next;
            node.next = n.next;
            n.next.last = node;
            node.last = n;
            n.next = node;
            node = cn;
        }
        n.next.last = list.header;
        list.header.next = n.next;
    }
    }

