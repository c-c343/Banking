package accountManager.com.model;

/**
 * 
 * @author 
 *
 */
public class ModelEvent {
	/**
	 * Kind of Event
	 */
	private EventKind kind;
	/**
	 * Current Balance
	 */
	private double balance;
	/**
	 * Status
	 */
	private AgentStatus agSt;
	public ModelEvent(EventKind kind, double balance, AgentStatus agSt){
		this.balance = balance;
		this.kind = kind;
		this.agSt = agSt;
	}

	public EventKind getKind(){return kind;}
	public double getBalance(){
		return balance;
	}
	public AgentStatus getAgStatus(){return agSt;}
}
