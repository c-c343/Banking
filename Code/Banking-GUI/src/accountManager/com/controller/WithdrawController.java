package accountManager.com.controller;

import accountManager.com.model.Account;
import accountManager.com.model.Currency;
import accountManager.com.model.OverdrawException;
import accountManager.com.view.WithdrawView;

import java.text.DecimalFormat;

import javax.swing.SwingUtilities;

/**
 * 
 * @author 
 * Controller to handle the Page where the Start Amount Deposit takes place
 */
public class WithdrawController extends AbstractController {
	/**
	 * To handle the double rounding functionality
	 */
	DecimalFormat df = new DecimalFormat("#.##");      

	public void operation(String opt)
	{
		/**
		 * 
		 */
		if(opt == WithdrawView.Deposit) 
		{
			double amt = ((WithdrawView)getView()).getAmount();
			double amount=0.0;
			/**
			 * Which currency is being used
			 */
			Currency currency=((WithdrawView)getView()).currency;
			if(currency==Currency.DOLLAR)
				amount=amt;
			else if(currency==Currency.EUROS)
				amount=amt / 0.79;
			else if(currency==Currency.YEN)
				amount=amt / 94.1;
			amount=Double.valueOf(df.format(amount));
			((Account)getModel()).deposit(amount);
			
		}
		/**
		 * Withdraw Operation
		 */
		else if(opt == WithdrawView.Withdraw) {

			double amt = ((WithdrawView)getView()).getAmount();
			double amount=0.0;
			Currency currency=((WithdrawView)getView()).currency;
			if(currency==Currency.DOLLAR)
				amount=amt;
			else if(currency==Currency.EUROS)
				amount=amt / 0.79;
			else if(currency==Currency.YEN)
				amount=amt/94.1;
			System.out.println(amount);
			amount=Double.valueOf(df.format(amount));
			try {
				((Account)getModel()).withdraw(amount);
			}
			catch(OverdrawException ex) {
				final String msg = ex.getMessage();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						((WithdrawView)getView()).showError(msg);
					}
				});
			}
		}
		/**
		 * Close button is press
		 */
		else if(opt == WithdrawView.Dismiss) {
			Account ag = (Account)getModel();
			WithdrawView agView = (WithdrawView)getView();
			ag.removeModelListener(agView);
			agView.dispose();
		}
	}





}
