package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import fileprocessor.WriteToFile;

public class Login
{

    private JFrame frame;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton_1;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
	EventQueue.invokeLater(new Runnable()
	{
	    public void run()
	    {
		try
		{
		    Login window = new Login();
		    window.frame.setVisible(true);
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the application.
     */
    public Login()
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

	textField = new JTextField();
	textField.setBounds(183, 72, 116, 22);
	frame.getContentPane().add(textField);
	textField.setColumns(10);

	JLabel lblNewLabel = new JLabel("User");
	lblNewLabel.setBounds(63, 75, 56, 16);
	frame.getContentPane().add(lblNewLabel);

	JLabel lblNewLabel_1 = new JLabel("Password");
	lblNewLabel_1.setBounds(63, 121, 56, 16);
	frame.getContentPane().add(lblNewLabel_1);

	JButton btnNewButton = new JButton("Login");
	btnNewButton.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {

		String user = textField.getText();
		String pass = passwordField.getText();

		Scanner sc = null;
		File file = new File("userpass.txt");
		try
		{
		    sc = new Scanner(file);

		}
		catch (FileNotFoundException e)
		{

		}
		boolean check = false;

		Map<String, String> accounts = new HashMap<String, String>();
		while (sc.hasNextLine())
		{
		    String line = sc.nextLine();
		    String[] array = line.split("\t");
		    if(array.length ==2)
		    {
			accounts.put(array[0], array[1]);
		    }
		}

		if (accounts.containsKey(user) && accounts.get(user).equals(pass))
		{
		    check = true;
		    System.out.println("login successfull");
		    
		    // login mainwindow
		    MainWindow mw = new MainWindow();
		    mw.setVisible(true);
		    mw.setLocationRelativeTo(null);
		    frame.setVisible(false);

		    WriteToFile.log("Login sucessfull for user: " + user);

		}

		if (check == false)
		{
		    JOptionPane.showMessageDialog(null, "Login Not Successfull");
		}

	    }
	});
	btnNewButton.setBounds(63, 181, 246, 25);
	frame.getContentPane().add(btnNewButton);

	passwordField = new JPasswordField(15);
	passwordField.setBounds(183, 118, 116, 22);
	frame.getContentPane().add(passwordField);

	btnNewButton_1 = new JButton("New User");
	btnNewButton_1.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
		NewUserPass nup = new NewUserPass();
		nup.frame.setVisible(true);
	    }
	});
	btnNewButton_1.setBounds(323, 181, 97, 25);
	frame.getContentPane().add(btnNewButton_1);
    }
}
