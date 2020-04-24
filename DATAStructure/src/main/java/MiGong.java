public class MiGong {
    public static void main8(String[] args) {
        //先创建一个二维数组，模拟迷宫
        //地图
        int[][] map = new int[8][7];
        //使用1表示墙
        //上下全部置为1
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        //全部置为1
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }
        //设置挡板，1表示
        map[3][1] = 1;
        map[3][2] = 1;
        //输出地图
        outPutMap(map);
        //使用递归给小球找路
        setWay(map,1,1);
        //再次输出地图
        System.out.println("====================================================");
        outPutMap(map);
        System.out.printf("cout=%s",count);
    }
    static int count=0;

    private static void outPutMap(int[][] map) {
        System.out.println("地图的情况");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 1.map表示地图
     * 2.i,j表示从地图的那个位置开始出发(1,1)
     * 3.小球能到map[6][5] 位置,说明通路找到
     * 4.约定: 当map[i][j]为 0：表示该点没有走过 1：表示墙 ; 2：表示通路可以走 ; 3:表示该点已经走过，但是走不通
     * 5.走迷宫时,需要确定一个策略(方法) 下->右->上->左,如果该点走不通,再回溯
     * 使用递归回溯来小球找路
     *
     * @param map 地图
     * @param i   位置坐标i
     * @param j   位置坐标j
     * @return 如果找到通路返回true, 否则返回false
     */
    public static boolean setWay(int[][] map, int i, int j) {
        count++;
        if (map[6][5] == 2) {//通路已经找到ok
            return true;
        } else {
            if (map[i][j] == 0) {//表示这个点还没有走过
                //按照策略 下->右->上->左 走
                map[i][j] = 2;//假定该点可以走通
                if (setWay(map, i + 1, j)) {//向下走
                    return true;
                } else if (setWay(map, i, j + 1)) {//向右走
                    return true;
                } else if (setWay(map, i - 1, j)) {//向上走
                    return true;
                } else if (setWay(map, i, j - 1)) {//向左走
                    return true;
                } else {
                    //说明该点走不通,是死路
                    map[i][j] = 3;
                    return false;
                }
            } else {
                //如果map[i][j]!=0 可能是1:墙,2:走过,3:走不通 不管任何一种都说明没必要走了
                //1.走进墙里那就成bug了 不可能所以凡是走进过这个格子的路 都不是通路
                //3.凡是经过这个点的路径 都没有通路 即走不通
                //2.绕回来的路，你是在走圈圈吗 走过的路没必要再走
                //是递归回溯回来的，递归会从底层开始调整，如果回溯回来，说明经过这个点的上下左右都走过，万策尽,已无路
                return false;
            }
        }
    }
}
