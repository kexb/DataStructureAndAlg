package com.atguigu.unitofwork;

import com.atguigu.unitofwork.repository.UnitOfWorkRepository;

public class TranslateAccountService {
    private UnitOfWork work;
    private UnitOfWorkRepository repository;

    public TranslateAccountService(UnitOfWork work, UnitOfWorkRepository repository) {
        this.work = work;
        this.repository = repository;
    }


    public void translate(Account from, Account to, int money) {
        // 省略验证

        from.minus(money);
        to.add(money);

        repository.modify(from);
        repository.modify(to);

        work.commit();
    }
}