package com.atguigu.unitofwork.repository;


import com.atguigu.unitofwork.AggrateRoot;
import com.atguigu.unitofwork.UnitOfWork;

import java.sql.Connection;

public abstract class UnitOfWorkRepository {

    public UnitOfWorkRepository setWork(UnitOfWork work) {
        this.work = work;
        return this;
    }

    private UnitOfWork work;

    public void put(AggrateRoot root) {
        work.registerAdd(root, this);
    }

    public void modify(AggrateRoot root) {
        work.registerModify(root, this);
    }

    public void remove(AggrateRoot root) {
        work.registerDelete(root, this);
    }

    // 实现中不能关闭连接---
    public abstract void doPut(Connection conn, AggrateRoot root);

    // 实现中不能关闭连接---
    public abstract void doModify(Connection conn, AggrateRoot root);

    // 实现中不能关闭连接---
    public abstract void doRemove(Connection conn, AggrateRoot root);
}