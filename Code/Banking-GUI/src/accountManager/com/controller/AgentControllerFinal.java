package accountManager.com.controller;

import accountManager.com.model.Account;
import accountManager.com.model.Agent;
import accountManager.com.model.WithdrawAgent;
import accountManager.com.view.AgentView1;
/**
 * 
 * @author \
 * Class for Controlling the final Running Automated Agent
 *
 */
public class AgentControllerFinal extends AbstractController {
	/**
	 * 
	 * @param opt operation to do
	 */
	public void operation(String opt) {
		/**
		 * if stop button is pressed
		 */
		 if(opt == AgentView1.Stop) 
		 {
			 ((AgentView1)getView()).setStopped(false);
			((Agent)getModel()).finish();
			
		}
		 /**
		  * if Dismiss button is preessed
		  */
		 else if(opt == AgentView1.Dismiss) 
		 {
			Agent ag = (Agent)getModel();
			AgentView1 agView = (AgentView1)getView();
			if(ag instanceof WithdrawAgent) 
			{
				Account account = ag.getAccount();
				account.removeModelListener(agView);
			}
			ag.finish();
			agView.dispose();
		}
	}
}
