package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import login.ProfessorLogin;
import login.SelectedUser;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class HHRegister extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JLabel lblRegister;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HHRegister frame = new HHRegister();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HHRegister() {
		setTitle("HandyHomework");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		usernameField = new JTextField();
		usernameField.setBounds(125, 69, 250, 25);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(125, 144, 250, 25);
		contentPane.add(passwordField);
		
		lblUsername = new JLabel("USERNAME");
		lblUsername.setBounds(211, 38, 75, 20);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblUsername);
		
		lblPassword = new JLabel("ENTER PASSWORD\r\n");
		lblPassword.setBounds(181, 113, 136, 19);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblPassword);
		
		JButton btnNewButton = new JButton("Register");
		btnNewButton.setBounds(200, 186, 102, 43);
		JCheckBox chckbxStudent = new JCheckBox("Student");
		chckbxStudent.setBounds(211, 236, 97, 23);
		contentPane.getRootPane().setDefaultButton(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean is_student = chckbxStudent.isSelected();
				String userName = String.valueOf(usernameField.getText());
				String password = String.valueOf(passwordField.getPassword());
				if (password.isEmpty() || userName.isEmpty()) {
					JOptionPane.showMessageDialog(HHRegister.this, "Username and password cannot be empty.");
				}
				else {
					if(is_student) {
						HHLogin frame = new HHLogin();
						frame.setVisible(true);
						frame.setResizable(false);
						if (frame.isShowing()){
							dispose();
						}
					}
					else {
						// need to check to make login
						ProfessorLogin pf =  db.DbConnection.checkUser(userName, password);
						
						if (pf == null) {
							System.out.println("'user':'pass'");
							JOptionPane.showMessageDialog(HHRegister.this, 
									"Username or password for username is incorrect .");
						} else {						
							// Change to superclass
							SelectedUser.setUser(pf);
							
							HHLogin frame = new HHLogin();
							frame.setVisible(true);
							frame.setResizable(false);
							if (frame.isShowing()){
								dispose();
							}
						}
							
						

					}
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(btnNewButton);
		contentPane.add(chckbxStudent);
		
		lblRegister = new JLabel("REGISTER");
		lblRegister.setForeground(Color.GREEN);
		lblRegister.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblRegister.setBounds(200, 5, 125, 30);
		contentPane.add(lblRegister);
	}
}
