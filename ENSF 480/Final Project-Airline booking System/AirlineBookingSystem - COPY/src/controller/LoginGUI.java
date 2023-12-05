package controller;
import javax.swing.*;

import view.*;

import java.awt.event.*;
import java.sql.*;
import java.util.Properties;

public class LoginGUI implements ActionListener {
	JTextField userText;
    JPasswordField passwordText;
    JLabel success;

    public static void main(String[] args) {
        LoginGUI gui = new LoginGUI();
        gui.createUI();
    }

    public void openUserGui(String type, int userID) {
        // Valid credentials, check account type
        if (type.equals("admin")) {
            // Open System Admin GUI
            new SystemAdminGUI().createUI();
        } else if (type.equals("customer")) {
            // Open Customer GUI
            new CustomerGUI(userID).createUI();
        } else if (type.equals("airline-agent")) {
            // Open Airline Agent GUI
            new AirlineAgentGUI(userID).createUI();
        } else if (type.equals("flight-attendant")) {
            // Open Flight Attendant GUI
            new FlightAttendantGUI(userID).createUI();
        } else if (type.equals("tourism-agent")) {
            // Open Tourism Agent GUI
            new TourismAgentGUI(userID).createUI();
        } else {
            success.setText("Unknown account type");
        }
    }

    public void createUI() {
        JFrame frame = new JFrame();
        frame.setTitle("Flight Reservation Application");
        JPanel login = new JPanel();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(login);

        login.setLayout(null);

        JLabel userLabel = new JLabel("UserName:");
        userLabel.setBounds(20, 20, 80, 25);
        login.add(userLabel);

        userText = new JTextField();
        userText.setBounds(100, 20, 165, 25);
        login.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 60, 80, 25);
        login.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(100, 60, 165, 25);
        login.add(passwordText);

        JButton logButton = new JButton("Login");
        logButton.setBounds(20, 100, 80, 25);
        logButton.addActionListener(this);
        login.add(logButton);

        JButton guestButton = new JButton("Guest Login");
        guestButton.setBounds(120, 100, 120, 25);
        guestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GuestGUI().createUI();
            }
        });
        login.add(guestButton);

        JButton createButton = new JButton("Create New User");
        createButton.setBounds(250, 100, 150, 25);
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a dialog for selecting user type
                openUserTypeDialog();
            }
        });
        login.add(createButton);

        success = new JLabel("");
        success.setBounds(20, 130, 300, 25);
        login.add(success);

        frame.setVisible(true);
    }

    private void openUserTypeDialog() {
        JFrame dialogFrame = new JFrame();
        dialogFrame.setTitle("User Registration");
        JPanel panel = new JPanel();
        dialogFrame.setSize(800, 600);

        panel.setLayout(null);

        JLabel label = new JLabel("Select User Type:");
        label.setBounds(20, 20, 150, 25);
        panel.add(label);

        String[] userTypes = {"Customer", "Administrator", "Tourism-Agent", "Flight-Attendant", "Airline-Agent"};
        JComboBox<String> typeDropdown = new JComboBox<>(userTypes);
        typeDropdown.setBounds(150, 20, 120, 25);
        panel.add(typeDropdown);

        JButton okButton = new JButton("OK");
        okButton.setBounds(20, 60, 80, 25);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogFrame.dispose(); // Close the dialog
                String selectedType = (String) typeDropdown.getSelectedItem();
                // Open the RegisterNewUserGUI with the selected user type
                new RegisterNewUserFrame(selectedType.toLowerCase()).createUI();
            }
        });
        panel.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(120, 60, 80, 25);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogFrame.dispose(); // Close the dialog
            }
        });
        panel.add(cancelButton);

        JLabel noteLabel = new JLabel("<html><div style='text-align: left;'>Note for Customers:<br><br>" +
        "Registered customers will be able to: <br><br> " + 
        "* Sign up for company credit card<br>" +
        "* Receive monthly promotion news<br>" +
        "* Receive an annual free companion ticket<br>" +
        "* Use airport lounges with a discount price</div></html>");
        noteLabel.setBounds(20, 100, 300, 300);
        panel.add(noteLabel);

        dialogFrame.add(panel);
        dialogFrame.setVisible(true);

    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Login")) {
			String username = userText.getText();
            String password = String.valueOf(passwordText.getPassword());
        
            // Load database properties
            Properties properties = DBUtils.loadProperties("AirlineBookingSystem/config/database.properties");
            if (properties == null) {
                // Handle the error appropriately
                return;
            }

            String url = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");

            try {
                Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            
                PreparedStatement credentials = connection.prepareStatement("SELECT * FROM users WHERE UserName=? AND UserPassword=?");
                credentials.setString(1, username);
                credentials.setString(2, password);
            
                ResultSet resultSet = credentials.executeQuery();
            
                if (resultSet.next()) {
                    // Valid credentials
                    String accountType = resultSet.getString("AccountType");
                    int userID = resultSet.getInt("UserID");
                    success.setText("Login Successful");
            
                    Timer timer = new Timer(2000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            openUserGui(accountType, userID);
                        }
                    });
                    timer.setRepeats(false); // Set to run only once
                    timer.start();
                } else {
                    // Invalid credentials
                    success.setText("Login Failed");
                }
            
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
        }
    }
}
