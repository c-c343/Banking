package accountManager.com.controller;

import accountManager.com.controller.AgentController;
import accountManager.com.model.Account;
import accountManager.com.model.Currency;
import accountManager.com.view.AccountView;
import accountManager.com.view.AgentView;
import accountManager.com.view.WithdrawView;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * 
 * @author 
 * Account Controller To handle all the Main Page functions
 *
 */
public class AccountController extends AbstractController {

	public void operation(String opt) 
	{
		/**
		 * Functions to perform when Edit in Dollar Button is Pressed
		 */
		if(opt == AccountView.editinDollarText) 
		{
			int index=0;
			index=AccountView.accountsCombo.getSelectedIndex();
			/**
			 * if no choice is selected from the combo box
			 */
			if(index==-1)
			{
				JOptionPane.showMessageDialog(null, "Please select an account before proceeding","ERROR",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			Account account = AccountView.accounts.get(index);
			WithdrawController contr = new WithdrawController();
			contr.setModel(account);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					WithdrawView app = new WithdrawView(account, contr,Currency.DOLLAR);
					contr.setView(app);
					app.setVisible(true);
				}
			});
		}
		/**
		 * Functions to perform when Edit in Euros Button is Pressed
		 */
		else if(opt == AccountView.editinEurosText) 
		{
			int index=AccountView.accountsCombo.getSelectedIndex();
			/**
			 * if no choice is selected from the combo box
			 */
			if(index==-1)
			{
				JOptionPane.showMessageDialog(null, "Please select an account before proceeding","ERROR",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			Account account = AccountView.accounts.get(index);
			WithdrawController contr = new WithdrawController();
			contr.setModel(account);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					WithdrawView app = new WithdrawView(account, contr,Currency.EUROS);
					contr.setView(app);
					app.setVisible(true);
				}
			});
		} 
		/**
		 * Functions to perform when Edit in Yen Button is Pressed
		 */
		else if(opt == AccountView.editinYenText) {
			int index=AccountView.accountsCombo.getSelectedIndex();
			/**
			 * if no choice is selected from the combo box
			 */
			if(index==-1)
			{
				JOptionPane.showMessageDialog(null, "Please select an account before proceeding","ERROR",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			Account account = AccountView.accounts.get(index);
			WithdrawController contr = new WithdrawController();
			contr.setModel(account);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					WithdrawView app = new WithdrawView(account, contr,Currency.YEN);
					contr.setView(app);
					app.setVisible(true);
				}
			});
		}
		/**
		 * Functions to perform when Start Deposit Agent Button is Pressed
		 */
		else if(opt == AccountView.DepositAgentText) {
			int index=AccountView.accountsCombo.getSelectedIndex();
			/**
			 * if no choice is selected from the combo box
			 */
			if(index==-1)
			{
				JOptionPane.showMessageDialog(null, "Please select an account before proceeding","ERROR",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			Account account = AccountView.accounts.get(index);
			final AgentController agContr = new AgentController();
			SwingUtilities.invokeLater(new Runnable() {
			      public void run() {
			    	AgentView app = new AgentView(account, agContr,"Start Deposit Agent for " +account.getId());
			  		agContr.setView(app);
			  		AccountView.agentContrs.add(agContr);
			  		app.setVisible(true);
			      }
			    });
		}
		/**
		 * Functions to perform when Start Withdraw Agent Button is Pressed
		 */
		else if(opt == AccountView.WithdrawAgentText)
		{
			int index=AccountView.accountsCombo.getSelectedIndex();
			/**
			 * if no choice is selected from the combo box
			 */
			if(index==-1)
			{
				JOptionPane.showMessageDialog(null, "Please select an account before proceeding","ERROR",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			Account account = AccountView.accounts.get(index);
			final AgentController agContr = new AgentController();
			agContr.setModel(account);
			SwingUtilities.invokeLater(new Runnable() {
			      public void run() {
			    	  AgentView app = new AgentView(account, agContr,"Start Withdraw Agent for " +account.getId());
				  		agContr.setView(app);
				  		AccountView.agentContrs.add(agContr);
				  		app.setVisible(true);
			      }
			    });
		}
		/**
		 * Functions to perform when Exit Button is Pressed
		 * File is saved and the program exits
		 */
		else if(opt == AccountView.exitText) 
		{
			saveFile();
			JOptionPane.showMessageDialog(null, "File successfully Saved. Now Exiting...", "File Saved !",
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		/**
		 * Functions to perform when Save Button is Pressed
		 * File is saved and
		 */
		else if(opt == AccountView.saveText) 
		{
			saveFile();
			JOptionPane.showMessageDialog(null, "File successfully Saved.", "File Saved... !",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
	/**
	 * The file is saved by reading all the accounts in the Accounts arrayList
	 */
	public void saveFile()
	{
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(AccountView.fileName))) {
			StringBuilder sb=new StringBuilder("");
			for(Account account: AccountView.accounts)
			{
				sb.append(account.getId()).append(",");
				sb.append(account.getName()).append(",");
				sb.append(account.getBalance()).append(System.lineSeparator());
			}
			bw.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
