package accountManager.com.model;

/**
 * 
 * @author 
 * Overdraw Exception
 * This exception occurs when the User is trying to withdraw more
 * amount then the amount he has in account
 *
 */
public class OverdrawException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2411947209616579891L;

	public OverdrawException( double amount1,double amount2)
    {
        super("Insufficient funds: amount to withdraw is "+amount1+", it is greater than available funds: "+String.format("%.2f", amount2));     
    }
}
