package com.atguigu.unitofwork.repository;

import com.atguigu.unitofwork.AggrateRoot;
import com.atguigu.unitofwork.UnitOfWork;

import java.sql.Connection;

public class UnitOfWorkImplRepository extends UnitOfWorkRepository {
    public UnitOfWorkImplRepository(UnitOfWork work) {
        setWork(work);
    }
    public UnitOfWorkImplRepository(){}
    @Override
    public void doPut(Connection conn, AggrateRoot root) {

    }

    @Override
    public void doModify(Connection conn, AggrateRoot root) {

    }

    @Override
    public void doRemove(Connection conn, AggrateRoot root) {

    }
}
