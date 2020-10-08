package com.atguigu.bm;

public class bmAlo {
    private int size;

    public static void main(String[] args) {
        bmAlo obj = new bmAlo();
        char[] a = {'1', '2', '3', '我', '5'};
        char[] b = {'2', '3'};
        obj.setSize(65535);
        int bm = obj.bm(a, a.length, b, b.length);
        System.out.println(bm);
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int bm(char[] a, int n, char[] b, int m) {
        int[] bc = new int[size];
        generateBC(b, m, bc);
        int[] suffix = new int[m];
        boolean[] prefix = new boolean[m];
        generateGS(b, m, suffix, prefix);
        int i = 0;
        while (i <= n - m) {
            int j;
            for (j = m - 1; j >= 0; --j) {
                if (a[i + j] != b[j]) {
                    break;
                }
            }
            if (j < 0) {
                return i;
            }
            int x = j - bc[(int) a[i + j]];
            int y = 0;
            if (j < m - 1) {
                y = moveByGS(j, m, suffix, prefix);
            }
            i = i + Math.max(x, y);
        }
        return -1;
    }

    private void generateGS(char[] b, int m, int[] suffix, boolean[] prefix) {
        for (int i = 0; i < m; ++i) {
            suffix[i] = -1;
            prefix[i] = false;
        }
        for (int i = 0; i < m - 1; ++i) {
            int j = i;
            int k = 0;
            while (j >= 0 && b[j] == b[m - 1 - k]) {
                --j;
                suffix[k] = j + 1;
            }
            if (j == -1) {
                prefix[k] = true;
            }
        }
    }

    private void generateBC(char[] b, int m, int[] bc) {
        for (int i = 0; i < size; i++) {
            bc[i] = -1;
        }
        for (int i = 0; i < m; ++i) {
            int ascii = b[i];
            bc[ascii] = i;
        }
    }

    private int moveByGS(int j, int m, int[] suffix, boolean[] prefix) {
        int k = m - 1 - j;
        if (suffix[k] != -1) {
            return j - suffix[k] + 1;
        }
        for (int r = j + 2; r <= m - 1; ++r) {
            if (prefix[m - r]) {
                return r;
            }
        }
        return m;
    }
}
