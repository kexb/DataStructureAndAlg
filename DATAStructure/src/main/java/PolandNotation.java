import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class PolandNotation {

    //中缀表达式转后缀表达式并计算
    public static void main7(String[] args) {
        //1+((2+3)*4)-5=>1 2 3 + 4 * + 5 -
         String expression = "1+(6*4-2*4+3*5)";
        //String expression="6*4- 2*4 + 3 * 2".replaceAll(" ","");
        //String expression="3*4*5";
        //中缀表达式
        List<String> infixExpressionList = toInfixExpressionList(expression);
        List<String> suffixExpressionList = parseSuffixExpressionList(infixExpressionList);
        System.out.printf("%s=%s\n",suffixExpressionList,calculatoe(suffixExpressionList));
    }

    //后缀表达式计算
    public static void main77(String[] args) {
        String suffixExpression = "4 5 * 8 - 60 + 8 2 / +";
        //4 * 5 - 8 + 60 + 8 / 2  => 4 5 * 8 - 60 + 8 2 / +
        //String suffixExpression = "3 4 + 5 * 6 -";
        List<String> rpnList = getListString(suffixExpression);
        System.out.println("rpnList=" + rpnList);
        System.out.printf("%s=%s", suffixExpression, calculatoe(rpnList));
    }
    //1+((2+3)*4)-5=>1 2 3 + 4 * + 5 -
    public static List<String> parseSuffixExpressionList(List<String> ls) {
        Stack<String> parternStack = new Stack<String>();//符号栈
        //因为s2在整个过程中 没有pop操作 而且最后还要逆序输出
        //因此我们直接使用List<String>
        List<String> resultStack=new ArrayList<String>();//中间结果栈
        int count=0;
        //遍历ls
        //1+((2+3)*4)-5  =>     1 2 3 + 4 * + 5 -
        //1 + ( ( (6*4- 2*4 + 3 * 2 ) * 3 * 4 + 3 ) * 4 ) - 5
        for (String item : ls) {
            //如果是item是一个数 入resultStack
            if(item.matches("\\d+")){
                resultStack.add(item);
            }else if(item.equals("(")){
                parternStack.push(item);
            }else if(item.equals(")")){
                //6*4-2*4 猜测最多执行两次 因括号")"优先级变高
                while (!parternStack.peek().equals("(")){
                    resultStack.add(parternStack.pop());
                }
                //将该右括号对应的左括号消除
                parternStack.pop();
            }else{

                int topPriority=0;
                int itemPriority=0;
                int tag=1;
                //6*4-2*4 + 本身优先级高,而入结果栈(遍历到最后一个+的时候)
                //当item的优先级小于等于s1栈顶运算符的优先级 将s1弹出加入到s2
                while (parternStack.size()!=0
                        && (topPriority=Operation.getValue(parternStack.peek()))>=(itemPriority=Operation.getValue(item))
                        &&tag==1
                ){//栈顶优先级比较高
                    String out=String.format("topPriority[%s]=%s item[%s]=%s\n",parternStack.peek(),topPriority,item,itemPriority);
                    System.out.printf(out);
                    resultStack.add(parternStack.pop());
                }
                parternStack.push(item);
            }
            count++;
        }
        //将s1中剩余的运算符加入到s2中   //6*4-2*4+3*2
        while(parternStack.size()!=0){
            resultStack.add(parternStack.pop());
        }
        return resultStack;
    }

    //s=1+((2+3)*4)-5
    //字符串=》中缀表达式list
    public static List<String> toInfixExpressionList(String s) {
        List<String> ls = new ArrayList<String>();
        int i = 0;//index指针
        String str = "";//多位数拼接
        char c;
        do {
            //如果c是一个非数字 我需要加入到ls
            if ((c = s.charAt(i)) < 48 || (c = s.charAt(i)) > 57) {
                ls.add("" + c);
                i++;
            } else {//是数字需要拼接
                str = "";
                while (i < s.length() && (c = s.charAt(i)) >= 48 && (c = s.charAt(i)) <= 57) {
                    str += c;
                    i++;
                }
                ls.add(str);
            }
        } while (i < s.length());
        return ls;
    }


    public static List<String> getListString(String suffixExpression) {
        String[] split = suffixExpression.split(" ");
        return Arrays.asList(split);
    }
    //完成对逆波兰表达式的运算


    /**
     * 完成对逆波兰表达式的运算
     * 1)从左至右扫描，将3和4压入栈
     * 2)遇到+运算符，弹出4和3（4为栈顶元素，3位次顶元素），计算3+4的值 得7 将7压入栈
     * 3)接下来将5入栈
     * 4)接下来是x运算符，弹出5和7，计算7x5=35 将35入栈
     * 5)将6入栈
     * 6)最后是-运算符 计算35-6的值 即29,因此得出最终结果
     */
    public static int calculatoe(List<String> ls) {
        Stack<String> stack = new Stack<String>();
        int count=0;
        for (String item : ls) {
            //匹配的事多位数
            if (item.matches("\\d+")) {
                //入栈
                stack.push(item);
            } else {
                //pop出2个数 并运算 再入栈(注意顺序 我先num2 再num1)
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                if (item.equals("+")) {
                    res = num1 + num2;
                } else if (item.equals("-")) {
                    res = num1 - num2;
                } else if (item.equals("*")) {
                    res = num1 * num2;
                } else if (item.equals("/")) {
                    res = num1 / num2;
                } else {
                    throw new RuntimeException("无效运算符");
                }
                //res入栈
                stack.push(res + "");
            }
            count++;
        }
        return Integer.parseInt(stack.pop());
    }
}
class Operation{
    private static int ADD=1;
    private static int SUB=1;
    private static int MUL=2;
    private static int DIV=2;
    //写一个方法返回对应的优先级
    //1+((2+3)*4)-5=>1 2 3 + 4 * + 5 -
    public static int getValue(String oper){
        if ("+".equals(oper)) {
            return ADD;
        } else if ("-".equals(oper)) {
            return SUB;
        } else if ("*".equals(oper)) {
            return MUL;
        } else if ("/".equals(oper)) {
            return DIV;
        }
        return 0;
    }
}
