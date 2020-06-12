package accountManager.com.model;

/**
 * 
 * @author 
 * Agent Interface
 *
 */
public interface Agent extends Model {
	public double getTransferred();
	
	public void setName(String name);
	public String getName();
	public Account getAccount();
	public void setStatus(AgentStatus agSt);
	public AgentStatus getStatus();
	public void finish();
}
