package NaturalNumber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

    public class NaturalNumber{
        public static int min_;
        public static String start;
        public static int n;
        public static int[] one;
        public static int[] two;
        public static int[] three;
        public static int[] four;
        public static void dfs(int k,int startx,LinkedList<Integer>member) {
            if(k==n) {
                for(int i=0;i<member.size()-1;i++) {
                    System.out.print(member.get(i)+"+");
                }
                System.out.println(member.get(member.size()-1));
                return;
            }
            for(int i=startx;i<n;i++) {
                if(k+i<=n) {
                    member.add(i);
                    dfs(k+i,i,member);
                    member.remove(member.size()-1);
                }
                else {
                    return;
                }

            }
        }
        public static void main(String args[]) throws IOException {
            Scanner in =new Scanner(System.in);
            n=in.nextInt();
            LinkedList<Integer>temp=new LinkedList<Integer>();
            dfs(0,1,temp);
        }
    }
