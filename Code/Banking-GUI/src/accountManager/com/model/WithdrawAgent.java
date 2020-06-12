package accountManager.com.model;

import javax.swing.SwingUtilities;

/**
 * 
 * @author 
 * Withdraw Agent
 *
 */
public class WithdrawAgent extends AbstractModel implements Runnable, Agent {
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
	
	
	private volatile boolean wasBlocked;
	
	public WithdrawAgent(Account account, Double amt, Double operations){
		this.account = account;
		this.amount=amt;
		this.operations=operations;
		this.transferred = 0;
		this.status = AgentStatus.Running;
		this.wasBlocked = false;
		this.active = true;
		this.paused = false;
		this.pauseLock = new Object();
	}
	/**
	 * Run method of the Thread
	 */
	public void run() {
		while(active) {
			synchronized (pauseLock) {
				/**
				 * If thread is paused
				 */
                while (paused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                    	System.out.println("Thread " + Thread.currentThread().getName() + " interrupted");
                    }
                }
                
            }
			account.autoWithdraw(amount, this);
			this.transferred += amount;
			final ModelEvent me = new ModelEvent(EventKind.AmountTransferredUpdate, transferred, AgentStatus.NA);
			SwingUtilities.invokeLater(
					new Runnable() {
					    public void run() {
					    	notifyChanged(me);
					    }
					});
			/**
			 * Number ofoperations per second 
			 */
			try {
				Thread.sleep(1000/new Double(operations).intValue());
			}
			catch(InterruptedException ex){
				System.out.println("Thread " + Thread.currentThread().getName() + " interrupted");
			}
		}
	}
	public double getTransferred(){return transferred;}
	public void onPause() {
        synchronized (pauseLock) {
            paused = true;
            setStatus(AgentStatus.Paused);
        }
    }

    public void onResume() {
        synchronized (pauseLock) {
        	if(wasBlocked) setStatus(AgentStatus.Blocked);
        	else setStatus(AgentStatus.Running);
            paused = false;
            pauseLock.notify();
        }
    }
    public void setStatus(AgentStatus agSt) {
    	status = agSt;
    	if(status == AgentStatus.Blocked) wasBlocked = true;
    	if(status == AgentStatus.Running) wasBlocked = false;
    	final ModelEvent me = new ModelEvent(EventKind.AgentStatusUpdate, 0.0, agSt);
    	System.out.println(me);
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
