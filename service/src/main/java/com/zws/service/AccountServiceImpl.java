package com.zws.service;


import com.zws.dao.AccountDao;
import com.zws.model.Account;

import com.zws.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.data.domain.Sort;


@Service
public class AccountServiceImpl implements AccountService {


    @Autowired
    AccountDao accountDao;

    @Override
    @Cacheable(value = "account", key = "'id_'+#id", unless = "#result eq null")
    public Optional<Account> findById(Long id) {
        return accountDao.findById(id);
    }


    @Override
    public Account insertByAccount(Account account) {
        return accountDao.save(account);
    }


    @Override
    public void deleteById(Long id) {
        accountDao.deleteById(id);
    }


    @Override
    public Optional<Account> updateByAccount(Long id, Account account) {
        accountDao.save(account);
        return accountDao.findById(id);
    }

    @Cacheable(value = "pages", key = "'page_'+#page+'_'+#pageSize", unless = "#result eq null")
    public Page<Account> findByPage(Account account, Integer page, Integer pageSize) {

        Pageable pageable = PageUtil.Pageable(page, pageSize);

        return accountDao.findAll(pageable);
    }


}
