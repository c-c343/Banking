package accountManager.com.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import accountManager.com.controller.AccountController;
import accountManager.com.controller.AgentController;
import accountManager.com.model.Account;

import accountManager.com.view.AccountView;
import accountManager.com.view.AgentView;

public class AgentControllerTest {
	AgentController ac;
	Account account;
	AgentView app ;
	@Before
	public void initialize()
	{
		account=new Account(434,"u1",34.4);		
		ac = new AgentController();
		app = new AgentView(account, ac,"Start Withdraw Agent for " +account.getId());
  		ac.setView(app);
  			
	}
	
	@Test
	public void test1()
	{
		assertTrue(app.Dismiss.isEnabled());
		assertTrue(app.startAgent.isEnabled());

	}
	
	@Test
	public void test2()
	{
		try
		{
		ac.operation(AgentView.DismissText);
		}catch(Exception e) {}
		assertFalse(app.isDisplayable());

	}
	
	
	
	
	
}
