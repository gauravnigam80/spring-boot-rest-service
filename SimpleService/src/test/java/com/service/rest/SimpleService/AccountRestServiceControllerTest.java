package com.service.rest.SimpleService;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.service.rest.AccountService;
import com.service.rest.controller.AccountRestServiceController;
import com.service.rest.model.Account;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountRestServiceController.class)
public class AccountRestServiceControllerTest {

	public static final String SERVER_URI = "http://localhost:9090/rest/account/json";
	
	@MockBean
	private AccountService accountService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private List<Account> accountList = new ArrayList<Account>();
	
	@Test
	public void testGetAllMethodForAccountRestService() throws Exception {
		
		Account account = new Account(1,"Gaurav_0", "Nigam", "101");
		accountList.add(account);
		account = new Account(2,"Gaurav_1", "Nigam", "102");
		accountList.add(account);
		account = new Account(3,"Gaurav_2", "Nigam", "103");
		accountList.add(account);
		
		Mockito.when(
				accountService.getAllAccounts()).thenReturn(accountList);

		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/rest/account/json").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "[{\"id\":1,\"firstName\":\"Gaurav_0\",\"secondName\":\"Nigam\",\"accountNumber\":\"101\"},"
				+ "{\"id\":2,\"firstName\":\"Gaurav_1\",\"secondName\":\"Nigam\",\"accountNumber\":\"102\"},"
				+ "{\"id\":3,\"firstName\":\"Gaurav_2\",\"secondName\":\"Nigam\",\"accountNumber\":\"103\"}]";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	
	@Test
	public void testGetMethodByParameterForAccountRestService() throws Exception {
		
		Account account = new Account(1,"Gaurav_0", "Nigam", "101");
		
		Mockito.when(
				accountService.getAccount(Mockito.anyLong())).thenReturn(account);

		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/rest/account/json/1").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{\"id\":1,\"firstName\":\"Gaurav_0\",\"secondName\":\"Nigam\",\"accountNumber\":\"101\"}";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}

	@Test
	public void testDeleteMethodForAccountRestService() throws Exception {
		
		Mockito.when(
				accountService.deleteAccount(Mockito.anyLong())).thenReturn(Boolean.TRUE);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
				"/rest/account/json/1").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{\"message\":\"account sucessfully deleted\"}";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	
	@Test
	public void testPostMethodForAccountRestService() throws Exception {
		
		String newAccountJson = "{\"firstName\":\"Gaurav_0\",\"secondName\":\"Nigam\",\"accountNumber\":\"105\"}";
		Mockito.when(accountService.createAccount(Mockito.<Account>any()))
		.thenReturn(Boolean.TRUE);

		
		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders.post("/rest/account/json")
				.accept(MediaType.APPLICATION_JSON)
				.content(newAccountJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{\"message\":\"Account has been successfully added\"}";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
}
