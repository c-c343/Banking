package accountManager.com.view;

import accountManager.com.controller.AgentControllerFinal;
import accountManager.com.controller.Controller;
import accountManager.com.model.Account;
import accountManager.com.model.AgentStatus;
import accountManager.com.model.EventKind;
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
 * View when an Withdrawal or Deposit Agent is started
 *
 */
public class AgentView1 extends JFrameView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * **********************************************************************************************
	 * Constants being declared 
	 */
	public final static String Stop = "Stop";
	public final static String Dismiss = "Dismiss";
	/**
	 * **********************************************************************************************
	 */
	
	private JPanel topPanel;
	private JPanel textPanel;
	private JPanel buttonPanel;

	public JTextPane transferred;
	public JTextPane status;
	private JTextPane amount;
	private JTextPane OPPersSecond;
	public JTextPane OPCompleted;
	public JTextPane statusField;
	
	public JButton stopButton;
	public JButton dismissButton;
	private Handler handler = new Handler();
	public Double operations ;
	public Double amt ;
	public String agentid ;
	public String name;
	public AgentView1(Model model,Controller controller, String string, Double operations, Double amt, String id){
		super(model, controller);
		this.name=string;
		this.operations=operations;
		this.amt=amt;
		this.agentid=id;
		this.getContentPane().add(getContent());
		Toolkit toolkit =  Toolkit.getDefaultToolkit();
		Dimension dim = toolkit.getScreenSize();
		int x = (int) ((dim.getWidth() - this.getWidth()) * 0.5f);
	    int y = (int) ((dim.getHeight() - this.getHeight()) * 0.5f);
	    this.setLocation(x - 10, y - 10);
	    
		final Controller contr = controller;
		addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent evt) {
		         ((AgentControllerFinal)contr).operation(Dismiss);
		    }
		});
		this.setPreferredSize(new Dimension(480, 400));
		this.setTitle(string);
		pack();
	}
	
	/**
	 * 
	 * @return panel having all view Components
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
	 * @return panel of all the Text Fields
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
			GridBagConstraints b7 = new GridBagConstraints();
			b7.gridx = 0;
			b7.gridy = 3;
			
			GridBagConstraints b8 = new GridBagConstraints();
			b8.gridx = 1;
			b8.gridy = 3;
			GridBagConstraints b9 = new GridBagConstraints();
			b9.gridx = 0;
			b9.gridy = 4;
			
			GridBagConstraints b10 = new GridBagConstraints();
			b10.gridx = 1;
			b10.gridy = 4;
			
			
			textPanel = new JPanel();
			textPanel.setLayout(new GridBagLayout());
			//textPanel.setPreferredSize(new Dimension(250, 150));
			textPanel.add(new JLabel("Amount in $"), b1);
			amount=new JTextPane();
			amount.setText(String.format("%.2f",((Account)getModel()).getBalance()));
			amount.setSize(200, 25);
			amount.setEditable(false);
			textPanel.add(amount, b2);
			
			textPanel.add(new JLabel("Operations per second"), b3);
			OPPersSecond=new JTextPane();
			OPPersSecond.setText(String.format("%.2f",(operations)));
			OPPersSecond.setSize(200, 25);
			OPPersSecond.setEditable(false);
			textPanel.add(OPPersSecond, b4);
			
			textPanel.add(new JLabel("State"), b5);
			status=new JTextPane();
			status.setText("Running");
			status.setSize(200, 25);
			status.setEditable(false);
			textPanel.add(status, b6);
			
			textPanel.add(new JLabel("Amount in $ Transferred"), b7);
			transferred=new JTextPane();
			transferred.setText("0.0");
			transferred.setSize(200, 25);
			transferred.setEditable(false);
			textPanel.add(transferred, b8);
			
			textPanel.add(new JLabel("Opeartions Completed"), b9);
			OPCompleted=new JTextPane();
			OPCompleted.setText("0");
			OPCompleted.setSize(200, 25);
			OPCompleted.setEditable(false);
			textPanel.add(OPCompleted, b10);
		}
		return textPanel;
	}
	/**
	 * 
	 * @return panel of all the button
	 */
	private JPanel getButtonPanel()
	{
		if(buttonPanel == null){
			GridBagConstraints pauseButtonCtr = new GridBagConstraints();
			pauseButtonCtr.gridx = 0;
			pauseButtonCtr.gridy = 0;
			
			GridBagConstraints resButtonCtr = new GridBagConstraints();
			resButtonCtr.gridx = 1;
			resButtonCtr.gridy = 0;
			
			GridBagConstraints dissButtonCtr = new GridBagConstraints();
			dissButtonCtr.gridx = 2;
			dissButtonCtr.gridy = 0;
			
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			stopButton= new JButton(Stop);
			stopButton.addActionListener(handler);
			dismissButton = new JButton(Dismiss);
			dismissButton.addActionListener(handler);
			buttonPanel.add(stopButton, pauseButtonCtr);
			buttonPanel.add(dismissButton, dissButtonCtr);
			dismissButton.setEnabled(false);
		}
		
		return buttonPanel;
	}
	
	
	
	/**
	 * When any listener returns with a positive change
	 */
	public void modelChanged(ModelEvent me){
		EventKind kind = me.getKind();
		if(kind == EventKind.AmountTransferredUpdate) {
			amount.setText(String.format("%.2f",me.getBalance()));
			transferred.setText(String.format("%.2f",Double.parseDouble(transferred.getText())+amt));
			OPCompleted.setText(Integer.parseInt(OPCompleted.getText())+1+"");
		}
		else if(kind == EventKind.AgentStatusUpdate) {
			AgentStatus agSt = me.getAgStatus();
			statusField.setText(agSt.toString());
		}
		else if(kind == EventKind.BalanceUpdate) {

			amount.setText(String.format("%.2f",me.getBalance()));
			transferred.setText(String.format("%.2f",Double.parseDouble(transferred.getText())+amt));
			OPCompleted.setText(Integer.parseInt(OPCompleted.getText())+1+"");

		}
	}
	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			((AgentControllerFinal)getController()).operation(evt.getActionCommand());
		}
	}
	/**
	 * 
	 * @param b Status that the STopped is yes or not
	 * Disable Stop button and Enable dismiss
	 * Also update the Status
	 */
	public void setStopped(boolean b) {
		dismissButton.setEnabled(true);
		status.setText("Stopped");
		stopButton.setEnabled(false);
		
	}

}
