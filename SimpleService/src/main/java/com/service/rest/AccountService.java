/**
 * 
 */
package com.service.rest;

import java.util.List;

import com.service.rest.model.Account;

/**
 * @author Gaurav Nigam
 *
 */
public interface AccountService {

	public List<Account> getAllAccounts();
	
	public Account getAccount(long id);

	public Boolean createAccount(Account acccount);
	
	public Boolean deleteAccount(long id);
	
}
