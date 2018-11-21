/**
 * 
 */
package com.service.rest.SimpleService;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.service.rest.AccountService;
import com.service.rest.AccountServiceImpl;
import com.service.rest.model.Account;

/**
 * @author Gaurav Nigam
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

	@TestConfiguration
	static class config{
		@Bean
		public AccountService accountService() {
			return new AccountServiceImpl();
		}
	}

	@Autowired
	AccountService accountService;
	
	private List<Account> accountList = new ArrayList<Account>();


	/**
	 * Test method for {@link com.service.rest.AccountServiceImpl#getAllAccounts()}.
	 */
	@Test
	public final void testGetAllAccounts() {
		Account account = new Account("Gaurav_0", "Nigam", "101");
		accountService.createAccount(account);
		accountList.add(account);
		
		account = new Account("Gaurav_1", "Nigam", "102");
		accountService.createAccount(account);
		accountList.add(account);
		
		account = new Account("Gaurav_2", "Nigam", "103");
		accountService.createAccount(account);
		accountList.add(account);
		
		List<Account> accListActual = accountService.getAllAccounts();
		
		accountList.forEach(acc -> assertTrue("Account does not match ", Boolean.TRUE == checkIfAccountExists(acc,accListActual)));
	}

	/**
	 * Test method for {@link com.service.rest.AccountServiceImpl#getAccount(long)}.
	 */
	@Test
	public final void testGetAccount() {
		Account account = new Account("Gaurav_01", "Nigam", "101");
		accountService.createAccount(account);
		
		assertTrue("Account does not match", Boolean.TRUE == checkIfAccountExists(account,accountService.getAllAccounts()));
	}

	/**
	 * Test method for {@link com.service.rest.AccountServiceImpl#createAccount(com.service.model.Account)}.
	 */
	@Test
	public final void testCreateAccount() {
		Account account = new Account("Gaurav", "Nigam", "101");
		assertTrue(Boolean.TRUE == accountService.createAccount(account));

		assertTrue("Account does not match", Boolean.TRUE == checkIfAccountExists(account,accountService.getAllAccounts()));
	}

	/**
	 * Test method for {@link com.service.rest.AccountServiceImpl#deleteAccount(long)}.
	 */
	@Test
	public final void testDeleteAccount() {
		Account account = new Account("Gaurav", "Nigam", "101");
		assertTrue(Boolean.TRUE == accountService.createAccount(account));

		assertTrue(Boolean.TRUE == accountService.deleteAccount(account.getId()));
		assertTrue("Account does not match", null == accountService.getAccount(account.getId()) );
	}


	@Test
	public final void testAccountCRUDOperationTogether() {
		Account account = new Account("Gaurav_01", "Nigam", "101");
		accountService.createAccount(account);

		account = new Account("Gaurav_02", "Nigam", "102");
		accountService.createAccount(account);

		account = new Account("Gaurav_03", "Nigam", "103");
		accountService.createAccount(account);
		
		accountService.deleteAccount(2);

		account = new Account("Gaurav_04", "Nigam", "104");
		accountService.createAccount(account);

		assertTrue("Account does not match", account.equals(accountService.getAccount(account.getId())));


	}

	private Boolean checkIfAccountExists(Account account, List<Account> allAccounts) {
		Optional<Account> accountFound = allAccounts.stream().filter(acc -> 
			acc.getFirstName().equals(account.getFirstName()) 
			   && acc.getSecondName().equals(account.getSecondName())
			   && acc.getAccountNumber().equals(account.getAccountNumber())
		  ).findFirst();
		
		return accountFound.isPresent() ? Boolean.TRUE: Boolean.FALSE;
	}


}

