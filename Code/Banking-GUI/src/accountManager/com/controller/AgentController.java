package accountManager.com.controller;


import accountManager.com.model.Account;
import accountManager.com.model.Agent;
import accountManager.com.model.AgentCreator;
import accountManager.com.view.AgentView;
import accountManager.com.view.AgentView1;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * 
 * @author 
 * Agent Controller class 
 * It handles all the working when the Agent Window opens
 *
 */
public class AgentController extends AbstractController {
	/**
	 * Currently used iDs.
	 * No two agents with same id can exist
	 */
	public static List<String> agentIds=new ArrayList<>();
	/**
	 * 
	 * @param opt : Option selected in the view page
	 */
	public void operation(String opt) 
	{
		/**
		 * If Start Agent Button iS pressed
		 */
		if(opt == AgentView.StartAgentText) {
			final AgentView agView = (AgentView)getView();
			Account account=(Account)agView.getModel();
			String id=agView.agentId.getText();
			/**
			 * Checking fr duplicate Agent Ids
			 */
			if(agentIds.contains(id))
			{
				JOptionPane.showMessageDialog(null, "Duplicate Agent Id.");
				return;
			}
			agentIds.add(id);
			Double amt=0.0;
			/**
			 * Amount Validation
			 */
			try
			{
				amt =Double.parseDouble(agView.amount.getText());
			}catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "Amount can only be a  double value");
				return;
			}
			if(amt<0)
			{
				JOptionPane.showMessageDialog(null, "Amount can only be a  positive value");
				return;
			}
			/**
			 * Number of Operations Validation
			 */
			Double operations=0.0;
			try
			{
				operations=Double.parseDouble(agView.operations.getText());
				if(operations<0.0)
				{
					JOptionPane.showMessageDialog(null, "Number of OPerations must be greater then 0");
					return;
				}

			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "OPerations can only be a  double value");
				return;
			}
			final double oop=operations;
			final double aop=amt;
			/**
			 * If this is for a withdraw agent
			 */
			if(agView.name.contains("Withdraw"))
			{
				final Agent ag = AgentCreator.createWithdrawAgent(account,operations,amt);
				final AgentControllerFinal agContr1 = new AgentControllerFinal();	
				agContr1.setModel(ag);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						AgentView1 app = new AgentView1(account,agContr1,"Withdraw Agent for Id :"+account.getId(),oop,aop,id);
						agContr1.setView(app);
						account.addModelListener(app);
						app.setVisible(true);
					}
				});
			}
			/**
			 * This operation is for deposit agent
			 */
			else
			{
				final Agent ag = AgentCreator.createDepositAgent(account,operations,amt);
				final AgentControllerFinal agContr1 = new AgentControllerFinal();
				agContr1.setModel(ag);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						AgentView1 app = new AgentView1(account,agContr1,"Deposit Agent for Id :"+account.getId(),oop,aop,id);
						agContr1.setView(app);
						app.setVisible(true);
						account.addModelListener(app);
					}
				});
			}
			/**
			 * Dismiss button is pressed.
			 * Close the view
			 */
		} 
		else if(opt == AgentView.DismissText)
		{
			AgentView agView = (AgentView)getView();
			agView.dispose();
		}
	}
}
