import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class firstpage extends JFrame implements ActionListener {
          
	JButton b1;
	JPanel newPanel;
	JLabel userLabel, passLabel;
	final JTextField textField1, textField2;
	firstpage(){
		userLabel=new JLabel();
		userLabel.setText("Account Number:");
		
		textField1=new JTextField(15);
		
		passLabel=new JLabel();
		passLabel.setText("Pin:");
		
		textField2=new JTextField(15);
		
		b1=new JButton("SUBMIT");
		
		newPanel=new JPanel(new GridLayout(3,1));
		newPanel.add(userLabel);
		newPanel.add(textField1);
		newPanel.add(passLabel);
		newPanel.add(textField2);
		newPanel.add(b1);
		
		add(newPanel, BorderLayout.CENTER);
		
		b1.addActionListener(this);
		setTitle("LOGIN");
	}	
		public void actionPerformed(ActionEvent ae)
		{
			String accno=textField1.getText().toString();
			String pass=textField2.getText().toString();
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","root");
				PreparedStatement st=(PreparedStatement)con
						.prepareStatement("Select accno,pass from account where accno=? and pass=?");
				
				st.setString(1, accno);
				st.setString(2,pass);
				ResultSet rs=st.executeQuery();
				if(rs.next()) {
					//JOptionPane.showMessageDialog(b1, "you have successfully logged in");
					OptionsPage optionPage=new OptionsPage(accno);
					optionPage.setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(b1,"wrong username and password");
				}
			}  catch(SQLException sqlException) {
				sqlException.printStackTrace();
			} catch(ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		

	public static void main(String[] args) {
		// TODO Auto-generated method stub
     try {
    	 firstpage form=new firstpage();
    	 form.setSize(300,100);
    	 form.setVisible(true);
     } catch(Exception e) {
    	 JOptionPane.showMessageDialog(null, e.getMessage());
     }
	}

}



class OptionsPage extends JFrame implements ActionListener{
	
	JButton withdrawButton, creditButton,balanceButton;
	JLabel accnoLabel;
	String accno;
	
	public OptionsPage(String accno) {
		this.accno=accno;
		accnoLabel=new JLabel("Account Number: "+accno);
		
		withdrawButton=new JButton("Withdraw");
		withdrawButton.addActionListener(this);
		
		creditButton=new JButton("Credit");
		creditButton.addActionListener(this);
		
		balanceButton=new JButton("Check Balance");
		balanceButton.addActionListener(this);
		
		JPanel panel=new JPanel(new GridLayout(4,1));
		panel.add(accnoLabel);
		panel.add(withdrawButton);
		panel.add(creditButton);
		panel.add(balanceButton);
		
		add(panel);
		setTitle("Options");
		setSize(300,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==withdrawButton) {
			WithdrawPage withdrawPage=new WithdrawPage(accno);
			withdrawPage.setVisible(true);
		} else if(ae.getSource()==balanceButton) {
			BalancePage balancePage=new BalancePage(accno);
			balancePage.setVisible(true);
		} else if(ae.getSource()==creditButton) {
			CreditPage creditPage=new CreditPage(accno);
			creditPage.setVisible(true);
		}
	}
	
}

class WithdrawPage extends JFrame implements ActionListener{
	JLabel amountLabel;
	JTextField amountTextField;
	JButton withdrawButton;
	String accno;
	
	public WithdrawPage(String accno) {
		this.accno=accno;
		amountLabel=new JLabel("enter amount to withdraw");
		amountTextField=new JTextField(10);
		withdrawButton=new JButton("Withdraw");
		withdrawButton.addActionListener(this);
		
		JPanel panel=new JPanel(new GridLayout(3,1));
		panel.add(amountLabel);
		panel.add(amountTextField);
		panel.add(withdrawButton);
		
		add(panel);
		setTitle("Withdraw");
		setSize(300,100);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent ae) {
		double amount=Double.parseDouble(amountTextField.getText());
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "root");
		PreparedStatement st = con.prepareStatement("UPDATE transactions SET balance = balance - ? WHERE accno = ?");
        st.setDouble(1, amount);
        st.setString(2, accno);
        int rowsAffected = st.executeUpdate();
        if(rowsAffected >0) {
        	JOptionPane.showMessageDialog(this, "Withdrawal successful");
            dispose();
        } else {
        	JOptionPane.showMessageDialog(this, "Withdrawal failed");
        }
	   }  catch(SQLException | ClassNotFoundException sqlException) {
        sqlException.printStackTrace();
    }
}
}




class CreditPage extends JFrame implements ActionListener {
    JLabel amountLabel;
    JTextField amountTextField;
    JButton creditButton;
    String accno;

    public CreditPage(String accno) {
        this.accno = accno;
        amountLabel = new JLabel("Enter amount to credit:");
        amountTextField = new JTextField(15);
        creditButton = new JButton("Credit");
        creditButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(amountLabel);
        panel.add(amountTextField);
        panel.add(creditButton);

        add(panel);
        setTitle("Credit");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            double amount = Double.parseDouble(amountTextField.getText());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount");
            } else {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "root");
                PreparedStatement st = con.prepareStatement("UPDATE transactions SET balance = balance + ? WHERE accno = ?");
                st.setDouble(1, amount);
                st.setString(2, accno);
                int rowsUpdated = st.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Amount credited successfully");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to credit amount");
                }
            }
        } catch (SQLException | ClassNotFoundException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
}





class BalancePage extends JFrame {
    JLabel balanceLabel;
    JButton closeButton;
    String accno;

    public BalancePage(String accno) {
        this.accno = accno;
        balanceLabel = new JLabel();
        closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(balanceLabel);
        panel.add(closeButton);

        add(panel);
        setTitle("Balance");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        updateBalance();
    }

    private void updateBalance() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "root");
            PreparedStatement st = con.prepareStatement("SELECT balance FROM transactions WHERE accno = ?");
            st.setString(1, accno);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                balanceLabel.setText("Your current balance is: " + balance);
            } else {
                balanceLabel.setText("Failed to retrieve balance");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            balanceLabel.setText("Failed to retrieve balance");
        }
    }
}


