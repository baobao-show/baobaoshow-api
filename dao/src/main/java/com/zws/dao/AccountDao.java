package com.zws.dao;

import com.zws.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountDao extends JpaRepository<Account, Long>, JpaSpecificationExecutor {
//  https://blog.csdn.net/weixin_40256864/article/details/81092853
}
