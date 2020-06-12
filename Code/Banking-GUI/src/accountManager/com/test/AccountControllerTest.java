package accountManager.com.test;

import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;

import accountManager.com.controller.AccountController;
import accountManager.com.model.Account;

import accountManager.com.view.AccountView;

public class AccountControllerTest {
	AccountController ac;
	Account account;
	AccountView app ;
	@Before
	public void initialize()
	{
		account=new Account(434,"u1",34.4);		
		final AccountController contr = new AccountController();
		contr.setModel(account);
		app = new AccountView(account, contr,"Accounts.txt");
		contr.setView(app);	
	}
	
	@Test
	public void test1()
	{
		assertTrue(app.editInDollar.isEnabled());
		assertTrue(app.editInEuros.isEnabled());
		assertTrue(app.editInYen.isEnabled());
		assertTrue(app.exit.isEnabled());
		assertTrue(app.save.isEnabled());
	}
	
	
	
	
	
}
