package com.atguigu.ntaijie;

public class njietaijie {
    public static int count = 0;

    public static void main(String[] args) {
        int n = 7;
        zou(n);
        System.out.println("zou=" + zou(n));
        System.out.println("zou2=" + zou2(n));
    }


    private static int zou(int n) {
        if (n == 2) {
            return 2;
        } else if (n == 1) {
            return 1;
        }
        return zou(n - 2) + zou(n - 1);
    }

    public static int zou2(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n should  > 1");
        }
        if (n == 1 || n == 2) {
            return n;
        } else {
            // 初始化走到第二阶台阶的走法有2种
            int n_2 = 2;
            // 初始化走到第一阶台阶的走法有1种
            int n_1 = 1;
            // 总走法
            int sum = 0;
            /**
             * n(1) = 1
             * n(2) = 2
             * n(3)=n(3-1)+n(3-2)
             * n(4)=n(4-1)+n(4-2)
             * n(5)=n(5-1)+n(5-2)
             * ...
             * n(m)=n(m-1)+n(m-2)
             */
            for (int i = 3; i <= n; i++) {
                sum = n_2 + n_1;
                n_1 = n_2;
                n_2 = sum;

            }
            return sum;
        }

    }


    private static String sout(String str, int n) {
        String s = "";
        s += str.charAt(n);
        if (n - 1 >= 0) {
            s += sout(str, n - 1);
        }
        return s;
    }


}
