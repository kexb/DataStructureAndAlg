public class Calculator {
    public static void mainCalculator(String[] args) {
        String exp = "3+2*6-2";
        //String exp="1+3*(1+1)";
        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);
        int index = 0;
        int num1 = 0;
        int num2 = 0;
        int oper = 0;
        int res = 0;
        char ch = ' ';
        String keepNum = "";
        while (true) {
            ch = exp.substring(index, index + 1).charAt(0);
            //是一个操作符
            if (operStack.isOper(ch)) {
                if (!operStack.isEmpty()) {
                    //当前扫描的优先级<=符号栈——栈顶的优先级 数栈弹出2个数运算 符号栈弹出一个符号 将结果压入数栈
                    //当前的操作符入符号栈
                    if (operStack.priority(ch) <= operStack.priority(operStack.peek())) {
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = numStack.cal(num1, num2, oper);
                        //运算结果入数栈
                        numStack.push(res);
                        //当前符号入符号栈
                        operStack.push(ch);
                    } else {
                        //如果当前符号优先级>符号栈顶的优先级 直接入符号栈
                        operStack.push(ch);
                    }
                }
                //如果符号栈为空 直接入符号栈
                else {
                    operStack.push(ch);
                }
            }
            //扫描到数字 直接入数栈
            else {
                keepNum += ch;
                if(index==exp.length()-1){
                    numStack.push(Integer.parseInt(keepNum));
                }else{
                    if(operStack.isOper(exp.substring(index+1,index+2).charAt(0))){
                        numStack.push(Integer.parseInt(keepNum));
                        keepNum="";
                    }
                }
            }
            index++;
            if (index >= exp.length()) {
                break;
            }

        }
        //扫描完毕
        while (true) {
            if (operStack.isEmpty()) {
                break;
            }
            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = numStack.cal(num1, num2, oper);
            numStack.push(res);
        }
        int res2 = numStack.pop();
        System.out.printf(exp + "=" + res2 + "\r\n");
    }
}

class ArrayStack2 {
    private int top = -1;
    private int maxSize;
    private int[] stack;

    public ArrayStack2(int maxSize) {
        this.maxSize = maxSize;
        this.stack = new int[maxSize];
    }

    public boolean isFull() {
        return top == maxSize - 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void push(int k) {
        stack[++top] = k;
    }

    public int pop() {
        int value = stack[top];
        top--;
        return value;
    }

    public void list() {
        if (isEmpty()) {
            System.out.println("栈空。。");
            return;
        }
        for (int i = top; top >= 0; top--) {
            System.out.printf("stack[%d]=%d\n", i, stack[i]);
        }
    }

    public int priority(int oper) {
        if (oper == '*' || oper == '/') {
            return 1;
        } else if (oper == '+' || oper == '-') {
            return 0;
        } else {
            return -1;//目前只有+ - * / 四种表达式
        }
    }

    public boolean isOper(char val) {
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    public int cal(int num1, int num2, int oper) {
        int res = 0;
        switch (oper) {
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1;
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                res = num2 / num1;
                break;
            default:
                break;
        }
        return res;
    }


    public int peek() {
        return stack[top];
    }
}
