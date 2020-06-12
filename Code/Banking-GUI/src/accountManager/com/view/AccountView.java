package accountManager.com.view;

import accountManager.com.controller.AccountController;
import accountManager.com.controller.AgentController;
import accountManager.com.controller.Controller;
import accountManager.com.model.Account;
import accountManager.com.model.AgentCreator;
import accountManager.com.model.EventKind;
import accountManager.com.model.Model;
import accountManager.com.model.ModelEvent;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;

import java.util.List;
import java.util.ArrayList;

import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

/**
 * 
 * @author 
 * Account View
 * this is the main window that appears when our application starts
 *
 */
public class AccountView extends JFrameView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * **********************************************************************************************
	 * Constants being declared 
	 */
	public final static String editinDollarText = "        Edit in Dollars      ";
	public final static String editinYenText = "         Edit in Yen       ";
	public final static String editinEurosText = "         Edit in Euros          ";
	public final static String saveText = "              Save              ";
	public final static String exitText = "         Exit         ";
	public final static String DepositAgentText = "Create Deposit Agent";
	public final static String WithdrawAgentText = "Create WithDraw Agent";
	/**
	 * **********************************************************************************************
	 */
	public static String fileName = "";
	private JPanel topPanel;
	private JPanel buttonPanel;

	private JTextPane balanceField;
	public JButton editInDollar;
	public JButton editInEuros;
	public JButton editInYen;
	public JButton save;
	public JButton exit;
	private JButton withdrawAgent;
	private JButton depositAgent;
	public static JComboBox<Account> accountsCombo;
	private Handler handler = new Handler();
	public static List<AgentController> agentContrs = new ArrayList<AgentController>(10);
	public final static List<Account> accounts=new ArrayList<>();


	/**
	 * 
	 * @param FILENAME file to be read
	 * Validation is also performed in this method
	 */
	private void readAccounts(String FILENAME) {
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			int i=0;
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				i++;
				String args[]=sCurrentLine.split(",");
				int id=0;
				try
				{
					id=Integer.parseInt(args[0]);
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Line "+i+": Id can have only numbers","ERROR",
							JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
				String name=args[1];

				double balance=0;
				try
				{
					balance=Double.parseDouble(args[2]);
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Line "+i+": Balance can have only decimal numbers","ERROR",
							JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
				/**
				 * name can contain only spaces and Lettersd
				 */
				if(!name.matches("^[a-zA-Z\\s]*$"))
				{	
					JOptionPane.showMessageDialog(null, "Line "+i+": Name must have only letters","ERROR",
							JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
				/**
				 * Balance cant be less then 0
				 */
				if(balance<0)
				{
					JOptionPane.showMessageDialog(null, "Line "+i+": Balance can not be less then 0","ERROR",
							JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}

				Account account=null;
				try {
					account = new Account(id, name, balance);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/**
				 * Account number already exists
				 */
				if(accounts.contains(account))
				{
					JOptionPane.showMessageDialog(null, "Line "+i+": An account with "+id+" already exists.","ERROR",
							JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
				accounts.add(account);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		/**
		 * Sorting the accounts ArrayList by Id
		 */
		accounts.sort(new Comparator<Account>() {

			@Override
			public int compare(Account o1, Account o2) {
				return o1.getId()-o2.getId();
			}
			
		});
	}
	
	/**
	 * 
	 * @param model 
	 * @param controller
	 * @param FILENAME
	 */
	public AccountView(Model model, Controller controller,String FILENAME){
		super(model, controller);
		AccountView.fileName=FILENAME;
		readAccounts(FILENAME);
		this.getContentPane().add(getContent());
		this.setTitle("Banking Application");
		Toolkit toolkit =  Toolkit.getDefaultToolkit();
		Dimension dim = toolkit.getScreenSize();
		int x = (int) ((dim.getWidth() - this.getWidth()) * 0.3f);
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
		this.setPreferredSize(new Dimension(730, 200));
		pack();
	}
	
	/**
	 * 
	 * @return JPanel having all the content of this view
	 */
	private JPanel getContent() {
		if (topPanel == null) {
			topPanel = new JPanel();
			GridLayout layout = new GridLayout(2, 1);
			topPanel.setLayout(layout);
			GridBagConstraints ps = new GridBagConstraints();
			ps.gridx = 0;
			ps.gridy = 0;
			ps.fill = GridBagConstraints.HORIZONTAL;

			GridBagConstraints bs = new GridBagConstraints();
			bs.gridx = 0;
			bs.gridy = 1;
			accountsCombo = new JComboBox<>(accounts.toArray(new Account[accounts.size()]));
			topPanel.add(accountsCombo, null);
			topPanel.add(getButtonPanel(), null);
		}
		return topPanel;
	}

	/**
	 * 
	 * @return button panel
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

			GridBagConstraints b4 = new GridBagConstraints();
			b4.gridx = 0;
			b4.gridy = 1;

			GridBagConstraints b5 = new GridBagConstraints();
			b5.gridx = 1;
			b5.gridy = 1;

			GridBagConstraints b6 = new GridBagConstraints();
			b6.gridx = 3;
			b6.gridy = 0;

			GridBagConstraints b7 = new GridBagConstraints();
			b7.gridx = 2;
			b7.gridy = 1;

			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			editInDollar= new JButton(editinDollarText);
			editInEuros= new JButton(editinEurosText);
			editInYen= new JButton(editinYenText);
			save= new JButton(saveText);
			exit= new JButton(exitText);
			withdrawAgent= new JButton(WithdrawAgentText);
			depositAgent= new JButton(DepositAgentText);
			withdrawAgent.addActionListener(handler);
			depositAgent.addActionListener(handler);
			editInEuros.addActionListener(handler);
			editInDollar.addActionListener(handler);
			editInYen.addActionListener(handler);
			save.addActionListener(handler);
			exit.addActionListener(handler);
			buttonPanel.add(editInDollar, b1);
			buttonPanel.add(editInEuros, b2);
			buttonPanel.add(editInYen, b3);
			buttonPanel.add(depositAgent, b4);
			buttonPanel.add(withdrawAgent, b5);
			buttonPanel.add(save, b7);
			buttonPanel.add(exit, b6);
		}

		return buttonPanel;
	}

	/**
	 * Changes in this form when any listener notifies
	 */
	public void modelChanged(ModelEvent me){
		if(me.getKind() == EventKind.BalanceUpdate) {
			System.out.println("Balance field to " + me.getBalance());
			balanceField.setText(Double.toString(me.getBalance()));
		}
	}


	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			((AccountController)getController()).operation(evt.getActionCommand());
		}
	}
	
	/**
	 * Main Method
	 * @param args filename only 1 argument expected
	 */
	public static void main(String[] args) {
		/**
		 * if file name is passed as argument
		 */
		if(args.length==0)
		{
			
			JOptionPane.showMessageDialog(null, "Please specify a file as Command Line Argument", "File Not Found !",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		final Account account = new Account();
		final AccountController contr = new AccountController();
		contr.setModel(account);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AccountView app = new AccountView(account, contr,args[0]);
				contr.setView(app);
				app.setVisible(true);
			}
		});
	}
	
	/**
	 * 
	 * @param msg msg to be shown as error
	 *  Displays a Message box with error
	 */
	public void showError(String msg) {
		JOptionPane.showMessageDialog(null, msg);

	}
	
}

