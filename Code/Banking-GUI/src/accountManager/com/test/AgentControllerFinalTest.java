package accountManager.com.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.swing.SwingUtilities;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


import accountManager.com.controller.AgentControllerFinal;
import accountManager.com.model.Account;
import accountManager.com.model.Agent;
import accountManager.com.model.AgentCreator;
import accountManager.com.view.AgentView;
import accountManager.com.view.AgentView1;

public class AgentControllerFinalTest {
	AgentControllerFinal agContr1;
	Account account;
	AgentView1 app ;
	Agent ag;
	@Before
	public void test0()
	{
		account=new Account(434,"u1",34.4);		
		agContr1 = new AgentControllerFinal();
		agContr1.setModel(ag);

		app = new AgentView1(account,agContr1,"Deposit Agent for Id :"+account.getId(),10.0,100.0,2+"+1");
		agContr1.setView(app);


	}

	@Test
	public void test1()
	{
		assertTrue(app.stopButton.isEnabled());
		assertFalse(app.dismissButton.isEnabled());

	}

	@Test
	public void test2()
	{
		assertEquals(app.OPCompleted.getText(), "0");

	}

	@Test
	public void test3()
	{
		assertEquals(app.status.getText(), "Running");

	}

	@Test
	public void test4()
	{
		assertEquals(app.transferred.getText(), "0.0");

	}
	@Test
	public void test5()
	{

		try
		{
			agContr1.operation(app.Stop);
		}catch(Exception e) {}
		assertTrue(app.dismissButton.isEnabled());
		assertFalse(app.stopButton.isEnabled());

	}
	
	@Test
	public void test6()
	{
		try
		{
		agContr1.operation(app.Dismiss);
		
	}catch(Exception e) {}
		assertTrue(app.isDisplayable());

	}





}
