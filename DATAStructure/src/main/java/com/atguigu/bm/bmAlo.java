package com.atguigu.bm;

public class bmAlo {
    /**
     * 散列表长度
     */
    private int size;

    public static void main(String[] args) {
        bmAlo obj = new bmAlo();
        坏字符规则 regular = new 坏字符规则().invoke();
        char[] mainStr = regular.getMainStr();
        char[] patternStr = regular.getPatternStr();
        mainStr="3  4  7  3  4  5 5  6  6  3  4  5".replaceAll(" ","").toCharArray();
        patternStr="5  6  6  3  4  5".replaceAll(" ","").toCharArray();
        obj.setSize(65535);
        int bm = obj.bm(mainStr, mainStr.length, patternStr, patternStr.length);
        System.out.println(bm);
    }

    public void setSize(int size) {
        this.size = size;
    }


    /**
     * bm算法
     *
     * @param mainStr       主串
     * @param mainLength    示主串的长度
     * @param patternStr    模式串
     * @param patternLength 模式串的长度
     * @return 模式串 在 主串 位置
     */
    public int bm(char[] mainStr, int mainLength, char[] patternStr, int patternLength) {
        //散列表 key:ascall码值 value：模式串下标
        int[] hash = new int[size];
        //散列表初始化 key:ascall码值 value：模式串下标
        generateHashAscallAndIndexInPattern(patternStr, patternLength, hash);
        int[] suffix = new int[patternLength];
        boolean[] prefix = new boolean[patternLength];
        //前轴和后缀计算过程
        generateSuffixAndPrefix(patternStr, patternLength, suffix, prefix);
        int i = 0;
        while (i <= mainLength - patternLength) {
            int j;
            for (j = patternLength - 1; j >= 0; --j) {
                //主串和模式串从最后一个元素开始比较  从右到左
                if (mainStr[i + j] != patternStr[j]) {
                    break;
                }
            }
            //上一个for循环全部走完 说明全匹配 那么j最终会小于0
            // 说明"i"就是模式字符串在主串的位置
            if (j < 0) {
                return i;
            }
            //坏字符 移动距离(散列值只会取最后一次) 这样滑动的距离最短（防止滑动过多）
            int x = j - hash[(int) mainStr[i + j]];
            int y = 0;
            if (j < patternLength - 1) {
                //好后缀 移动距离
                y = moveBySuffixAndPrefix(j, patternLength, suffix, prefix);
            }
            //这种处理方法还可以避免我们前面提到的，根据坏字符规则，计算得到的往后滑动的位数，有可能是负数的情况。
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
    private void generateSuffixAndPrefix(char[] b, int m, int[] suffix, boolean[] prefix) {
        for (int i = 0; i < m; ++i) {
            suffix[i] = -1;
            prefix[i] = false;
        }
        //模式串的长度为m 则前缀子串的最大长度为m-1
        for (int i = 0; i < m - 1; ++i) {
            int j = i;
            int k = 0;
            while (j >= 0 && b[j] == b[m - 1 - k]) {
                --j;
                ++k;
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
     * @param patternStr    模式串
     * @param patternLength 模式串的长度
     * @param hash            表示刚刚讲的散列表
     */
    private void generateHashAscallAndIndexInPattern(char[] patternStr, int patternLength, int[] hash) {
        for (int i = 0; i < size; i++) {
            hash[i] = -1;
        }
        for (int i = 0; i < patternLength; ++i) {
            int ascii = patternStr[i];
            hash[ascii] = i;
        }
    }


    /**
     * 模式串-滑动
     *
     * @param j             j表示坏字符对应的模式串中的字符下标
     * @param patternLength m表示模式串长度
     * @param suffix        后缀数组
     * @param prefix        前缀数组
     * @return 滑动距离
     */
    @SuppressWarnings("AlibabaUndefineMagicConstant")
    private int moveBySuffixAndPrefix(int j, int patternLength, int[] suffix, boolean[] prefix) {
        //3  4  j  3  4  j的时候不等那么 设从后往前一共有X位相等 X=patternLength-length（0..j）=patternLength-（j+1）
        //5  6  5  3  4
        int k = patternLength - 1 - j;
        if (suffix[k] != -1) {
            //要把suffix[k]移动到后缀开始的地方 必须先移动到j的位置
            //要移动j - suffix[k] 到达j
            //那么j的后面就是开始匹配的位置 所以要再+1 即下面
            return j - suffix[k] + 1;
        }
        //      j
        //0  1  2  3  4  5
        //3  4  7  3  4  5
        //5  6  6  3  4  5
        //因为j后面至少1位相等 所以从+2开始
        for (int r = j + 2; r <= patternLength - 1; ++r) {
            if (prefix[patternLength - r]) {
                return r;
            }
        }
        return patternLength;
    }
    private static class 坏字符规则 {
        private char[] mainStr;
        private char[] patternStr;

        public char[] getMainStr() {
            return mainStr;
        }

        public char[] getPatternStr() {
            return patternStr;
        }

        public 坏字符规则 invoke() {
            mainStr = ("a b b a d c a b a b a c a b".replaceAll(" ","")).toCharArray();
            patternStr = "b a b a c".replaceAll(" ","").toCharArray();
            return this;
        }
    }
    private static class 好后缀规则一 {
        private char[] mainStr;
        private char[] patternStr;

        public char[] getMainStr() {
            return mainStr;
        }

        public char[] getPatternStr() {
            return patternStr;
        }

        public 好后缀规则一 invoke() {
            mainStr = "abc aac bab bac ab".replaceAll(" ","").toCharArray();
            patternStr = "bc ab ab".replaceAll(" ","").toCharArray();
            return this;
        }
    }
    private static class 好后缀规则二 {
        private char[] mainStr;
        private char[] patternStr;

        public char[] getMainStr() {
            return mainStr;
        }

        public char[] getPatternStr() {
            return patternStr;
        }

        public 好后缀规则二 invoke() {
            mainStr = ("    "+"b a b c d e".replaceAll(" ","")).toCharArray();
            patternStr = "c d e a b c  d e".replaceAll(" ","").toCharArray();
            return this;
        }
    }
}
