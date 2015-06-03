package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class NewUserPass
{

    JFrame frame;
    private JTextField newusertext;
    private JPasswordField newpasswordtext;
    private JTextField admincodetext;

    /**
     * Launch the application.
     */
    //

    /**
     * Create the application.
     */
    public NewUserPass()
    {
	initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
	frame = new JFrame();
	frame.setBounds(100, 100, 450, 300);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().setLayout(null);

	newusertext = new JTextField();
	newusertext.setBounds(138, 100, 116, 22);
	frame.getContentPane().add(newusertext);
	newusertext.setColumns(10);

	newpasswordtext = new JPasswordField(15);
	newpasswordtext.setBounds(138, 135, 116, 22);
	frame.getContentPane().add(newpasswordtext);

	admincodetext = new JTextField();
	admincodetext.setBounds(138, 31, 116, 22);
	frame.getContentPane().add(admincodetext);
	admincodetext.setColumns(10);

	JButton btnNewButton = new JButton("Register New User");
	btnNewButton.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		String admincode = admincodetext.getText();
		String newuser = newusertext.getText();
		String newpass = newpasswordtext.getText();
		try
		{
		    if (admincode.equals("123"))
		    {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("userpass.txt", true)));
			out.println(newuser + "\t" + newpass + "\n");
			out.close();
			admincodetext.setText("");
			newusertext.setText("");
			newpasswordtext.setText("");
		    }
		    else
		    {
			JOptionPane.showMessageDialog(null, "admin code is not correct");
		    }
		    JOptionPane.showMessageDialog(null, " Created ");
		}
		catch (Exception e2)
		{

		}
	    }
	});
	btnNewButton.setBounds(59, 198, 229, 25);
	frame.getContentPane().add(btnNewButton);

	JLabel lblNewLabel = new JLabel("NewUser");
	lblNewLabel.setBounds(33, 109, 56, 16);
	frame.getContentPane().add(lblNewLabel);

	JLabel lblNewLabel_1 = new JLabel("New Password");
	lblNewLabel_1.setBounds(33, 138, 93, 16);
	frame.getContentPane().add(lblNewLabel_1);

	JLabel lblNewLabel_2 = new JLabel("Admin Code");
	lblNewLabel_2.setBounds(33, 34, 77, 16);
	frame.getContentPane().add(lblNewLabel_2);
    }

}
