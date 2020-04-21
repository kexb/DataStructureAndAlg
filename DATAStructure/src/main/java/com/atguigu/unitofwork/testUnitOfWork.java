package com.atguigu.unitofwork;

import com.atguigu.unitofwork.repository.UnitOfWorkImplRepository;
import com.atguigu.unitofwork.repository.UnitOfWorkRepository;

public class testUnitOfWork {

    public static void main(String[] args) {
        UnitOfWorkImpl unitOfWork = new UnitOfWorkImpl();
        UnitOfWorkRepository repository = new UnitOfWorkImplRepository(unitOfWork);
        TranslateAccountService svc = new TranslateAccountService(unitOfWork, repository);
        Account from = new Account(1, 100);
        Account to = new Account(1, 200);
        svc.translate(from, to, 20);
    }
}
