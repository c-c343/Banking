package accountManager.com.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;

import accountManager.com.controller.WithdrawController;
import accountManager.com.model.Account;
import accountManager.com.model.Currency;
import accountManager.com.view.WithdrawView;

public class WithDrawControllerTest {
	WithdrawController contr;
	Account account;
	WithdrawView app ;
	@Before
	public void initialize()
	{
		account=new Account(434,"u1",34.4);		
		contr = new WithdrawController();
		contr.setModel(account);

		app = new WithdrawView(account, contr,Currency.DOLLAR);
		contr.setView(app);

	}

	@Test
	public void test1()
	{
		assertTrue(app.depButton.isEnabled());
		assertTrue(app.dismissButton.isEnabled());
		assertTrue(app.withdrawButton.isEnabled());
	}
	@Test
	public void test2()
	{
		assertTrue(app.isDisplayable());
	}
	
	@Test
	public void test3()
	{
		contr.operation(app.Dismiss);
		assertFalse(app.isDisplayable());
	}





}
