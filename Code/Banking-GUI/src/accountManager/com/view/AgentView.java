package accountManager.com.view;

import accountManager.com.controller.AgentController;
import accountManager.com.controller.Controller;
import accountManager.com.model.Account;
import accountManager.com.model.Model;
import accountManager.com.model.ModelEvent;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
/**
 * 
 * @author 
 * Agent View Class to perform the functionality of starting an agent or
 * dismissing theis choice
 *
 */
public class AgentView extends JFrameView 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * **********************************************************************************************
	 * Constants being declared 
	 */
	public final static String StartAgentText = "Start Agent";
	public final static String DismissText = "Dismiss";
	/**
	 * **********************************************************************************************
	 */
	private JPanel topPanel;
	private JPanel textPanel;
	private JPanel buttonPanel;
	
	
	public  JTextPane agentId;
	public  JTextPane amount;
	public JTextPane operations;
	
	public JButton startAgent;
	public JButton Dismiss;
	private Handler handler = new Handler();
	private Account account;
	public String name;
	public AgentView(Model model, Controller controller, String string){
		super(model, controller);
		this.name=string;
		this.getContentPane().add(getContent());
		this.getContentPane().setSize(700, 500);
		Toolkit toolkit =  Toolkit.getDefaultToolkit();
		Dimension dim = toolkit.getScreenSize();
		int x = (int) ((dim.getWidth() - this.getWidth()) * 0.5f);
	    int y = (int) ((dim.getHeight() - this.getHeight()) * 0.5f);
	    this.setLocation(x - 10, y - 10);
	    
		final Controller contr = controller;
		addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent evt) {
		         ((AgentController)contr).operation(DismissText);
		    }
		});
		this.setTitle(string);
		this.setPreferredSize(new Dimension(380, 200));
		pack();
	}
	/**
	 * 
	 * @return JPanel containing the Contents of the Frame
	 */
	private JPanel getContent() {
		if (topPanel == null) {
			topPanel = new JPanel();
			GridLayout layout = new GridLayout(2, 1);
			topPanel.setLayout(layout);
			topPanel.add(getTextFieldPanel(), null);
			topPanel.add(getButtonPanel(), null);
		}
		return topPanel;
	}
	
	/**
	 * 
	 * @return a jpanel containing all the Text Fields
	 */
	private JPanel getTextFieldPanel()
	{
		if(textPanel == null){
			GridBagConstraints b1 = new GridBagConstraints();
			b1.gridx = 0;
			b1.gridy = 0;
			
			GridBagConstraints b2 = new GridBagConstraints();
			b2.gridx = 1;
			b2.gridy = 0;
			
			GridBagConstraints b3 = new GridBagConstraints();
			b3.gridx = 0;
			b3.gridy = 1;
			
			GridBagConstraints b4 = new GridBagConstraints();
			b4.gridx = 1;
			b4.gridy = 1;
			
			GridBagConstraints b5 = new GridBagConstraints();
			b5.gridx = 0;
			b5.gridy = 2;
			
			GridBagConstraints b6 = new GridBagConstraints();
			b6.gridx = 1;
			b6.gridy = 2;
			
			textPanel = new JPanel();
			textPanel.setLayout(new GridBagLayout());
			textPanel.add(new JLabel("Agent Id"), b1);
			agentId = new JTextPane();
			agentId.setText("");
			agentId.setPreferredSize(new Dimension(50, 20));
			agentId.setEditable(true);
			textPanel.add(agentId, b2);
			textPanel.add(new JLabel("Amount in $ "), b3);
			amount = new JTextPane();
			amount.setText("");
			amount.setPreferredSize(new Dimension(50, 20));
			amount.setEditable(true);
			textPanel.add(amount, b4);
			textPanel.add(new JLabel("Opeartions per Second "), b5);
			 operations= new JTextPane();
			 operations.setText("0.0");
			 operations.setPreferredSize(new Dimension(50, 20));
			 operations.setEditable(true);
			textPanel.add(operations, b6);
		}
		return textPanel;
	}
	
	/**
	 * 
	 * @return Panel having all the buttons
	 */
	private JPanel getButtonPanel()
	{
		if(buttonPanel == null){
			GridBagConstraints b1 = new GridBagConstraints();
			b1.gridx = 0;
			b1.gridy = 0;
			
			GridBagConstraints b2 = new GridBagConstraints();
			b2.gridx = 1;
			b2.gridy = 0;
			
			
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			startAgent= new JButton(StartAgentText);
			startAgent.addActionListener(handler);
			buttonPanel.add(startAgent, b1);
			Dismiss= new JButton(DismissText);
			Dismiss.addActionListener(handler);
			buttonPanel.add(Dismiss, b2);
		}
		
		return buttonPanel;
	}
	
	public void modelChanged(ModelEvent me){
		
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			((AgentController)getController()).operation(evt.getActionCommand());
		}
	}
	
}
