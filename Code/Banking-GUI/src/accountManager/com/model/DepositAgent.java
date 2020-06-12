package accountManager.com.model;

import javax.swing.SwingUtilities;

/**
 * 
 * @author 
 * Deposit Agent Class
 *
 */
public class DepositAgent extends AbstractModel implements Runnable, Agent 
{
	/**
	 * Lock to be used when no balance is there in account
	 */
	private Object pauseLock;
	/**
	 * Process is paused or running
	 */
	private boolean paused;
	public volatile boolean active;
	/**
	 * Current account being processed
	 */
	private Account account;
	/**
	 * amount of transfer being done
	 */
	private double amount;
	
	/**
	 * Amount transferred till now
	 */
	private double transferred;
	/**
	 * 
	 */
	private String name;
	/**
	 * Status
	 */
	private AgentStatus status;
	/**
	 * Number of Operations per second
	 */
	private Double operations;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		DepositAgent other = (DepositAgent) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public DepositAgent(Account account,Double amount,Double operations){
		this.operations=operations;
		this.amount=amount;
		this.account = account;
		this.transferred +=amount;
		this.status = AgentStatus.Running;
		this.active = true;
		this.paused = false;
		this.pauseLock = new Object();
	}
	/**
	 * Thread run
	 */
	public void run() {
		while(active) {
			synchronized (pauseLock) {
                while (paused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                    	System.out.println("Thread " + Thread.currentThread().getName() + " interrupted");
                    }
                }
            }
			account.autoDeposit(amount, this);
			final ModelEvent me = new ModelEvent(EventKind.AmountTransferredUpdate, transferred, AgentStatus.NA);
			SwingUtilities.invokeLater(
					new Runnable() {
					    public void run() {
					    	notifyChanged(me);
					    }
					});
			try {
				Thread.sleep(1000/new Double(operations).intValue());
			}
			catch(InterruptedException ex){
				System.out.println("Thread " + Thread.currentThread().getName() + " interrupted");
			}
		}
	}
	/**
	 * Amount of balance transferred
	 */
	public double getTransferred(){return transferred;}
	
    public void setStatus(AgentStatus agSt) {
    	status = agSt;
    	final ModelEvent me = new ModelEvent(EventKind.AgentStatusUpdate, 0.0, agSt);
    	SwingUtilities.invokeLater(
				new Runnable() {
				    public void run() {
				    	notifyChanged(me);
				    }
				});
    	
    }
    public AgentStatus getStatus(){return status;}
    public void setName(String name) {this.name = name;}
    public String getName(){return name;}
    public Account getAccount(){return account;}
    public void finish(){
    	active = false;
    	setStatus(AgentStatus.Blocked);
    	
    }
	
}
