package accountManager.com.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import accountManager.com.model.Account;
import accountManager.com.model.Agent;
import accountManager.com.model.AgentCreator;

public class CreateAgentTest {
	Agent depositAgent,withdrawAgent;
	Account account;
	@Before
	public void initialize()
	{
		account=new Account(434,"u1",34.4);
		depositAgent=AgentCreator.createDepositAgent(account,2.0, 10.0);
		withdrawAgent=AgentCreator.createDepositAgent(account,2.0, 10.0);
	}
	
	@Test
	public void test1()
	{
		assertEquals(depositAgent.getAccount(),account);
	}
	
	@Test
	public void test2()
	{
		assertEquals(withdrawAgent.getAccount(),account);
	}
	
	@Test
	public void test3()
	{
		assertEquals(withdrawAgent.getAccount(),account);
	}
	
	
	
	
}
