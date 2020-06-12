package accountManager.com.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import accountManager.com.model.Account;
import accountManager.com.model.OverdrawException;

public class AccountTest {
	Account account;
	@Before
	public void initialize()
	{
		try {
			account=new Account(35235,"User",3453);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void Test1() throws IllegalArgumentException
	{
		account=new Account(35235,"User",-9.4);
	}
	
	@Test
	public void Test2() throws IllegalAccessException
	{
		assertEquals(account.getBalance(), 3453.0,0.1);
	}
	
	@Test
	public void Test3() throws IllegalAccessException
	{
		assertEquals(account.getId(), 35235);
	}
	
	@Test
	public void Test4() throws IllegalAccessException
	{
		assertEquals(account.getName(), "User");
	}
	
	@Test
	public void Test5() throws IllegalAccessException
	{
		assertEquals(account.toString(), 35235+" : "+"User");
	}
	
	@Test
	public void Test6() throws IllegalAccessException, OverdrawException
	{
		account.deposit(100);
		assertEquals(account.getBalance(), 3553.0,0.1);
		account.withdraw(100);
	}
	
	@Test(expected = OverdrawException.class)
	public void Test7() throws OverdrawException
	{
		account.withdraw(5000.0);
	}
	
	@Test
	public void Test8() throws OverdrawException 
	{
		account.withdraw(100.0);
		assertEquals(account.getBalance(), 3353.0,0.1);
	}
}
