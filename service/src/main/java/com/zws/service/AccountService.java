package com.zws.service;

import com.zws.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface AccountService {


/**
* @param id
* @return
*/

Optional<Account> findById(Long id);


/**
* @param account
* @param page
* @param pageSize
* @return
*/

Page<Account> findByPage(Account account , Integer page, Integer pageSize);


/**
* @param account
* @return
*/

Account insertByAccount(Account account);


/**
 *
 * @param account
  * @return
 */
 Optional<Account>  updateByAccount(Long id, Account account);

 /**
  *
  * @param id
  */
 void deleteById(Long id);

}
