package accountManager.com.view;

import accountManager.com.controller.AgentController;
import accountManager.com.controller.Controller;
import accountManager.com.controller.WithdrawController;
import accountManager.com.model.Account;
import accountManager.com.model.AgentCreator;
import accountManager.com.model.Currency;
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

import java.util.List;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JOptionPane;

/**
 * 
 * @author 
 * Withdraw View : This is shown when the user presses the button to manually transfers 
 * i.e Withdraw or Deposit some Amount
 *
 */
public class WithdrawView extends JFrameView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * **********************************************************************************************
	 * Constants being declared 
	 */
	public final static String Deposit = "Deposit";
	public final static String Withdraw = "Withdraw";
	public final static String Dismiss = "Dismiss";
	/**
	 * **********************************************************************************************
	 */
	private JPanel topPanel;
	private JPanel textPanel;
	private JPanel buttonPanel;
	private JLabel balanceLabel;
	private JLabel amountLabel;
	private JTextPane balanceField;
	private JTextPane amountField;
	public JButton depButton;
	public JButton withdrawButton;
	public JButton dismissButton;
	public  Currency currency;
	private Handler handler = new Handler();
	
	private List<AgentController> agentContrs = new ArrayList<AgentController>(10);
	public WithdrawView(Model model, Controller controller,Currency currency){
		super(model, controller);
		this.currency=currency;
		this.getContentPane().add(getContent());
		System.out.println("Currency "+currency);

		Account account=((Account)model);
		if(currency==Currency.DOLLAR)
			this.setTitle(account.getId()+" Operating in $");
		if(currency==Currency.EUROS)
			this.setTitle(account.getId()+" Operating in EUROS");
		if(currency==Currency.YEN)
			this.setTitle(account.getId()+" Operating in YEN");
		Toolkit toolkit =  Toolkit.getDefaultToolkit();
		Dimension dim = toolkit.getScreenSize();
		int x = (int) ((dim.getWidth() - this.getWidth()) * 0.5f);
		int y = (int) ((dim.getHeight() - this.getHeight()) * 0.5f);
		this.setLocation(x, y);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				for(AgentController agContr : agentContrs) agContr.operation(AgentView.DismissText);
				AgentCreator.finishThreads();
				dispose();
				System.exit(0);
			}
		});
		this.setPreferredSize(new Dimension(350, 200));

		pack();
	}
	
	/**
	 * 
	 * @return panel with all the components
	 */
	private JPanel getContent() {
		if (topPanel == null) {
			topPanel = new JPanel();
			GridLayout layout = new GridLayout(2, 1);
			topPanel.setLayout(layout);
			//topPanel.setPreferredSize(new Dimension(300, 100));
			GridBagConstraints ps = new GridBagConstraints();
			ps.gridx = 0;
			ps.gridy = 0;
			ps.fill = GridBagConstraints.HORIZONTAL;

			GridBagConstraints bs = new GridBagConstraints();
			bs.gridx = 0;
			bs.gridy = 1;
			topPanel.add(getTextFieldPanel(), null);
			topPanel.add(getButtonPanel(), null);
		}
		return topPanel;
	}

	/**
	 * 
	 * @return panel having all the required buttons
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

			GridBagConstraints b3 = new GridBagConstraints();
			b3.gridx = 2;
			b3.gridy = 0;


			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			depButton = new JButton(Deposit);
			withdrawButton = new JButton(Withdraw);
			dismissButton = new JButton(Dismiss);
			depButton.addActionListener(handler);
			withdrawButton.addActionListener(handler);
			dismissButton.addActionListener(handler);
			buttonPanel.add(depButton, b1);
			buttonPanel.add(withdrawButton, b2);
			buttonPanel.add(dismissButton, b3);
		}

		return buttonPanel;
	}
	
	/**
	 * 
	 * @return panel having all the text components
	 */
	private JPanel getTextFieldPanel()
	{
		if(textPanel == null){
			GridBagConstraints bl = new GridBagConstraints();
			bl.gridx = 0;
			bl.gridy = 0;

			GridBagConstraints bf = new GridBagConstraints();
			bf.gridx = 1;
			bf.gridy = 0;

			GridBagConstraints aml = new GridBagConstraints();
			aml.gridx = 0;
			aml.gridy = 1;

			GridBagConstraints amf = new GridBagConstraints();
			amf.gridx = 1;
			amf.gridy = 1;

			textPanel = new JPanel();
			textPanel.setLayout(new GridBagLayout());
			balanceLabel = new JLabel();
			balanceLabel.setText("Available funds:");
			textPanel.add(balanceLabel, bl);
			balanceField = new JTextPane();
			if(currency==Currency.EUROS)
				balanceField.setText(String.format("%.2f",(((Account)getModel()).getBalance() * 0.79)));
			else if(currency==Currency.DOLLAR)
				balanceField.setText(String.format("%.2f",(((Account)getModel()).getBalance())));
			else if(currency==Currency.YEN)
				balanceField.setText(String.format("%.2f",(((Account)getModel()).getBalance() * 94.1)));



			balanceField.setEditable(false);
			textPanel.add(balanceField, bf);
			amountLabel = new JLabel();
			if(currency==Currency.EUROS)
				amountLabel.setText("Enter amount in EUROS:");
			if(currency==Currency.DOLLAR)
				amountLabel.setText("Enter amount in DOLLAR:");
			if(currency==Currency.YEN)
				amountLabel.setText("Enter amount in YEN:");
			amountLabel.setPreferredSize(new Dimension(200, 20));
			textPanel.add(amountLabel, aml);
			amountField = new JTextPane();

			amountField.setText("0.0");

			amountField.setSize(200, 25);
			amountField.setEditable(true);
			textPanel.add(amountField, amf);

		}
		return textPanel;
	}



	/**
	 * 
	 * @return amount present in the Amount textfield
	 */
	public double getAmount() {
		double amount = 0;
		amount = Double.parseDouble(amountField.getText());
		return amount;
	}
	/**
	 * 
	 * @param msg Message to be shown
	 * Show this Message in a dialogue box
	 */
	public void showError(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
	
	/**
	 * Listener notifies that a change has occured
	 * Do the required changes
	 */
	public void modelChanged(ModelEvent me){
		if(me.getKind() == EventKind.BalanceUpdate) {
			System.out.println("Balance field to " + me.getBalance());
			if(currency==Currency.EUROS)
				balanceField.setText(String.format("%.2f",(((Account)getModel()).getBalance() * 0.79)));
			else if(currency==Currency.DOLLAR)
				balanceField.setText(String.format("%.2f",(((Account)getModel()).getBalance())));
			else if(currency==Currency.YEN)
				balanceField.setText(String.format("%.2f",(((Account)getModel()).getBalance() * 94.1)));
			amountField.setText("0.0");
		}
	}
	

	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			((WithdrawController)getController()).operation(evt.getActionCommand());
		}
	}

}
