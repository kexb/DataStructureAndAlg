package com.atguigu.inflixexpression;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
//中缀表达式综合案例
public class testinflixExpression {
    public static void main(String[] args) {
        String str = "((((1+3*5*2-3*4)))+(6*2)-1)/2";
        List<String> ls = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            ls.add(str.charAt(i) + "");
        }
        Stack<String> data = new Stack<>();
        Stack<String> p = new Stack<>();
        for (String ch : ls) {
            if (ch.matches("\\d+")) {
                data.push(ch);
            } else {
                if (ch.equals("(")) {
                    p.push(ch);
                } else if (!ch.equals(")")) {
                    if (!p.empty()) {
                        String before = p.peek();
                        int beforePriory = getPrioty(before);
                        int currPriority = getPrioty(ch);
                        if (beforePriory >= currPriority) {
                            calculator(data, before);
                            p.pop();
                        }
                    }
                    p.push(ch);
                } else if (ch.equals(")")) {
                    while (!p.peek().equals("(")) {
                        calculator(data, p.peek());
                        p.pop();
                    }
                    p.pop();
                }
            }
        }
        while (!p.empty()) {
            String peek = p.pop();
            calculator(data, peek);
        }
        int result = Integer.parseInt(data.pop());
        System.out.println(str+"="+result);
    }

    private static void calculator(Stack<String> data, String ch) {
        switch (ch) {
            case "+": {
                String num1 = data.pop();
                String num2 = data.pop();
                data.push(String.valueOf(Integer.parseInt(num2) + Integer.parseInt(num1)));
                break;
            }
            case "-": {
                String num1 = data.pop();
                String num2 = data.pop();
                data.push(String.valueOf(Integer.parseInt(num2) - Integer.parseInt(num1)));
                break;
            }
            case "*": {
                String num1 = data.pop();
                String num2 = data.pop();
                data.push(String.valueOf(Integer.parseInt(num2) * Integer.parseInt(num1)));
                break;
            }
            case "/": {
                String num1 = data.pop();
                String num2 = data.pop();
                data.push(String.valueOf(Integer.parseInt(num2) / Integer.parseInt(num1)));
                break;
            }
        }
    }

    private static int getPrioty(String ch) {
        if (ch.equals("+")) {
            return 1;
        }
        if (ch.equals("+")) {
            return 1;
        }
        if (ch.equals("*")) {
            return 2;
        }
        if (ch.equals("/")) {
            return 2;
        }
        return -1;
    }
}
