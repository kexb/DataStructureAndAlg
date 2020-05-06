package com.atguigu.Queue.Simulation.Stack;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 两个队列模拟链表
 */
public class QueuesToStack {
    public static void main(String[] args) {
        MyStack stack = new MyStack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        System.out.println(stack);
        stack.peekAll();
        System.out.println(stack);

    }
}

class MyStack {

    private LinkedList<Integer>[] queue;

    public MyStack() {
        queue = new LinkedList[2];
        queue[0] = new LinkedList<>();
        queue[1] = new LinkedList<>();
    }

    private int cx = 0;

    //x1  3  2  1
    //x2  4  3  2  1
    public void push(int i) {
        int index1 = cx % 2;
        int index2 = (cx + 1) % 2;
        while (queue[index2].size() != 0) {
            queue[index1].addLast(queue[index2].removeFirst());
        }
        queue[index1].addFirst(i);
        cx = index2;
    }

    public int pop() {
        // LinkedList
        return queue().removeFirst();

    }

    public void popAll() {
        LinkedList<Integer> q = queue();
        while (q.size() != 0) {
            System.out.print(q.removeFirst() + "\t");
        }
        System.out.println();
    }

    public void peekAll() {
        LinkedList<Integer> q = queue();
        for (Integer i : q) {
            System.out.print(i + "\t");
        }
        System.out.println();
    }

    public LinkedList<Integer> queue() {
        return queue[0].size() != 0 ? queue[0] : queue[1];
    }

    @Override
    public String toString() {
        LinkedList<Integer> q = queue[0].size() != 0 ? queue[0] : queue[1];
        return "" +
                "queue=" + q
                ;
    }
}