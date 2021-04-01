import Util.Queue;
import Util.Stack;

import java.util.Scanner;

public class Palindrome {
    public static void main(String[] args) {
        String w = "[({})]";

        /*Scanner sc = new Scanner(System.in);
        Queue<Character> q = new Queue<>();
        Stack<Character> s = new Stack<>();

        System.out.println("Insert string: ");
        w=sc.nextLine();

        regString(w, q, s);

        System.out.println(isPalindrome(q, s));*/

        System.out.println(checkParenthesis(w));


    }

    public static void regChar(char c, Queue<Character> q, Stack<Character> s){
         q.enqueue(c);
         s.push(c);
    }

    public static void regString(String w, Queue<Character> q, Stack<Character> s){
        for (int i=0; i<w.length(); i++){
            if(w.charAt(i) != ' ') regChar(w.charAt(i), q, s);
        }
    }

    public static boolean isPalindrome(Queue<Character> q, Stack<Character> s){
        int oldSize=q.getSize()/2;
        while(q.getSize()>oldSize){
            if(q.dequeue() != s.pop()) return false;
        }
        return true;
    }


    public static boolean checkParenthesis(String s){
        char c;
        boolean verification=true;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length() && verification; i++) {
            c=s.charAt(i);
            switch(c){
                case '(':
                case '[':
                case '{':
                    stack.push(c);
                    break;
                case ')':
                case ']':
                case '}':
                    if(stack.isEmpty()) return false;

                    verification=isCompliant(stack.pop(), c);
                    break;
            }
        }
        return verification;
    }

    public static boolean isCompliant(char open, char close){
        switch(close){
            case ')':
                return (open=='(');
            case ']':
                return (open=='[');
            case '}':
                return (open=='{');
            default: return false;
        }
    }


}
