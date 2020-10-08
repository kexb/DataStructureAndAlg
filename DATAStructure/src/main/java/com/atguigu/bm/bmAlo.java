package com.atguigu.bm;

public class bmAlo {
    /**
     * 散列表长度
     */
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


    /**
     * bm算法
     *
     * @param a 主串
     * @param n 模式串
     * @param b 示主串的长度
     * @param m 模式串的长度
     * @return 模式串 在 主串 位置
     */
    public int bm(char[] a, int n, char[] b, int m) {
        //散列表 key:ascall码值 value：模式串下标
        int[] bc = new int[size];
        //散列表初始化 key:ascall码值 value：模式串下标
        generateBc(b, m, bc);
        int[] suffix = new int[m];
        boolean[] prefix = new boolean[m];
        //前轴和后缀计算过程
        generateGs(b, m, suffix, prefix);
        int i = 0;
        while (i <= n - m) {
            int j;
            for (j = m - 1; j >= 0; --j) {
                //主串和模式串从最后一个元素开始比较  从右到左
                if (a[i + j] != b[j]) {
                    break;
                }
            }
            //上一个for循环全部走完 说明全匹配 那么j最终会小于0
            // 说明"i"就是模式字符串在主串的位置
            if (j < 0) {
                return i;
            }
            //坏字符 移动距离(散列值只会取最后一次) 这样滑动的距离最短（防止滑动过多）
            int x = j - bc[(int) a[i + j]];
            int y = 0;
            if (j < m - 1) {
                //好字符 移动距离
                y = moveByGs(j, m, suffix, prefix);
            }
            //取最大值 (坏字符可能小于0，X,Y大小关系也未定)
            i = i + Math.max(x, y);
        }
        return -1;
    }

    /**
     * 前轴和后缀计算过程
     *
     * @param b      模式串
     * @param m      模式串长度
     * @param suffix 后缀数组
     * @param prefix 前缀数组
     */
    private void generateGs(char[] b, int m, int[] suffix, boolean[] prefix) {
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


    /**
     * 散列表初始化
     *
     * @param b  模式串
     * @param m  模式串的长度
     * @param bc 表示刚刚讲的散列表
     */
    private void generateBc(char[] b, int m, int[] bc) {
        for (int i = 0; i < size; i++) {
            bc[i] = -1;
        }
        for (int i = 0; i < m; ++i) {
            int ascii = b[i];
            bc[ascii] = i;
        }
    }


    /**
     * 模式串-滑动
     *
     * @param j      j表示坏字符对应的模式串中的字符下标
     * @param m      m表示模式串长度
     * @param suffix 后缀数组
     * @param prefix 前缀数组
     * @return 滑动距离
     */
    private int moveByGs(int j, int m, int[] suffix, boolean[] prefix) {
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
