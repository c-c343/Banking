package accountManager.com.model;
import javax.swing.SwingUtilities;

/**
 * 
 * @author 
 * Account Entity
 *
 */
public class Account extends AbstractModel 
{
	/**
	 * id of the account
	 * it is unique for every account
	 */
	private int id;
	/**
	 * name of the account holder
	 */
	private String name; 
	/**
	 * balance of account
	 */
	private double balance;	
	public Account()
	{
		
	}
	
	@Override
	public String toString() {
		return id + " : "+ name;
	}

	/**
	 * @param other Account to be compared
	 * @return id is equal or not
	 * Comparision is based on id
	 */
	public int compareTo(Account other) {
        return (int)id-other.id;
    }
	public Account(int id, String name, double balance) throws IllegalArgumentException
	{
		super();
		if(balance<0)
			throw new IllegalArgumentException("Balance can't be less then 0");
		this.id = id;
		this.name = name;
		this.balance = balance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalance()
	{
		return balance;
	}
	
	/**
	 * 
	 * @param amount amount to be deposited
	 * Add the amount and notify the listeners
	 * 
	 */
	public synchronized void deposit(double amount)
	{
		balance += amount;
		final ModelEvent me = new ModelEvent(EventKind.BalanceUpdate, balance, AgentStatus.NA);
		SwingUtilities.invokeLater(
				new Runnable() {
				    public void run() {
				    	notifyChanged(me);
				    }
				});
		notifyAll();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 * 
	 * @param amount amount to be withdrawn
	 * @throws OverdrawException if balance is less then the amount to be withdrawn
	 * Notification to listeners is also done here
	 */
	public synchronized void withdraw(double amount) throws OverdrawException {
		double newBalance = balance - amount;
		if(newBalance < 0.0)
			throw new OverdrawException(amount,balance);
		balance = newBalance;
		final ModelEvent me = new ModelEvent(EventKind.BalanceUpdate, balance, AgentStatus.NA);
		
		SwingUtilities.invokeLater(
				new Runnable() {
				    public void run() {
				    	notifyChanged(me);
				    }
				});
		notifyAll();
	}
	
	/**
	 * 
	 * @param amount Amount to be withdrawn
	 * @param agent
	 * A event is also being generated and a;ll liseners are also notified
	 */
	public synchronized void autoWithdraw(double amount, Agent agent) {
		try {
			System.out.println("Trying to withdraw " + amount + " from balance " + balance);
			while(this.balance - amount < 0.0) {
				agent.setStatus(AgentStatus.Blocked);	
				wait();
			}
			if(agent.getStatus() == AgentStatus.Paused) return;
			agent.setStatus(AgentStatus.Running);
					
			this.balance -= amount;
			final ModelEvent me = new ModelEvent(EventKind.BalanceUpdate, this.balance, AgentStatus.Running);
			SwingUtilities.invokeLater(
				new Runnable() {
				    public void run() {
				    	notifyChanged(me);
				    }
				});
		}
		catch(InterruptedException ex){
			System.out.println("Thread " + Thread.currentThread().getName() + " interrupted");
		}
	}
	
	/**
	 * 
	 * @param amount Amount to be Deposited
	 * @param agent
	 * A event is also being generated and a;ll liseners are also notified
	 */
	public synchronized void autoDeposit(double amount, Agent agent) {
		
			System.out.println("Trying to Deosit " + amount + " to balance " + balance);
			if(agent.getStatus() == AgentStatus.Paused) return;
			agent.setStatus(AgentStatus.Running);
					
			this.balance += amount;
			final ModelEvent me = new ModelEvent(EventKind.BalanceUpdate, this.balance, AgentStatus.Running);
			SwingUtilities.invokeLater(
				new Runnable() {
				    public void run() {
				    	notifyChanged(me);
				    }
				});
		
		
	}
}

