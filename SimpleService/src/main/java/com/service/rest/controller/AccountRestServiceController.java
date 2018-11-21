/**
 * 
 */
package com.service.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.service.rest.AccountService;
import com.service.rest.model.Account;
import com.service.rest.model.Message;

/**
 * @author Gaurav Nigam
 *
 */
@RestController
public class AccountRestServiceController {

	@Autowired
	AccountService accountService;
	
	@RequestMapping(value = "/rest/account/json",method=RequestMethod.GET)
    public @ResponseBody List<Account> getAllAccounts() {
		return accountService.getAllAccounts();
	}

	@RequestMapping(value = "/rest/account/json/{id}",method=RequestMethod.GET)
    public @ResponseBody Account getAccount(@PathVariable("id") long id) {
		return accountService.getAccount(id);
	}
	
	@RequestMapping(value = "/rest/account/json",method=RequestMethod.POST)
    public @ResponseBody Message createAccount(@RequestBody Account account) {
		//TODO boolean check
		accountService.createAccount(account);
		return new Message("Account has been successfully added");
	}
	
	@RequestMapping(value="/rest/account/json/{id}",method=RequestMethod.DELETE)
    public @ResponseBody Message deleteAccount(@PathVariable("id") long id) {
		//TODO boolean check2
		accountService.deleteAccount(id);
		return new Message("account sucessfully deleted");
	}

}
