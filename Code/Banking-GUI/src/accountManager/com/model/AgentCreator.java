package accountManager.com.model;

import java.util.List;

import java.util.ArrayList;
/**
 * 
 * @author 
 * Creation of agents is performed in this method
 *
 */
public class AgentCreator {
	/**
	 * Number of agent Threads simultaneously performing
	 */
	private static List<Thread> agentThreads = new ArrayList<Thread>(10);
	
	
	/**
	 * 
	 * @param account account on which operation is to be performed
	 * @param operations number of operations per second
	 * @param amt Amount to be Withdrawn
	 * @return Agent
	 */
	public static Agent createWithdrawAgent(Account account, Double operations, Double amt) {
		WithdrawAgent wAg = new WithdrawAgent(account,amt,operations);
		Thread wAgT = new Thread(wAg);
		String name = "Start Withdraw Agent for " +account.getId();
		wAg.setName(name);
		wAgT.setName(name);
		agentThreads.add(wAgT);
		wAgT.start();
		return wAg;
	}
	/**
	 * Complete all pending threads
	 */
	public static void finishThreads(){
		for(Thread t: agentThreads) {
				System.out.println("Finishing thread " + Thread.currentThread().getName());
				t.interrupt();
			}
	}
	
	/**
	 * 
	 * @param account account on which operation is to be performed
	 * @param operations number of operations per second
	 * @param amt Amount to be Deposited
	 * @return Agent
	 */
	public static Agent createDepositAgent(Account account, Double operations, Double amt) {
		
		DepositAgent depAg = new DepositAgent(account,amt,operations);
		Thread depAgT = new Thread(depAg);
		String name = "Start Deposit Agent for " +account.getId();
		depAg.setName(name);
		depAgT.setName(name);
		agentThreads.add(depAgT);
		depAgT.start();
		return depAg;
	}
}
