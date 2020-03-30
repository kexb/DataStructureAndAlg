package com.atguigu.horse;

import java.awt.*;
import java.util.ArrayList;
//com/atguigu/horse/图解.xlsx
public class HorseChessboard {
    private static int X;//棋盘的列数
    private static int Y;//棋盘的行数

    public static void main(String[] args) {

    }

    /**
     * 功能: 根据当前位置(Point对象),计算马儿还能走哪些位置(Point),并放入到一个集合中ArrayList,最多有8个位置
     *
     * @param curPoint
     * @return
     */
    public static ArrayList<Point> next(Point curPoint) {
        //创建一个ArrayList
        ArrayList<Point> ps = new ArrayList<>();
        //创建一个Point
        Point p1 = new Point();
        //表示马儿可以走5这个位置
        if ((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y - 1) >= 0) {
            ps.add(new Point(p1));
        }

    }
}
