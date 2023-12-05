package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.*;
import model.users.*;


public class SelectInsuranceFrame {
    private JFrame frame;
    private Flight selectedFlight; 
    private String selectedSeat;
    private FlightSystem sys;
    private Seat seat;
    private Customer customer;

    public SelectInsuranceFrame(String selectedSeat, Flight selectedFlight, int userID) {
        this.selectedSeat = selectedSeat;
        this.selectedFlight = selectedFlight;
        this.sys = FlightSystem.getInstance();

        int seatID = extractSeatNumber(selectedSeat);
        
        ArrayList<Seat> seatList = sys.getSeatList();
        //get seat object
        for(Seat s : seatList){
            if (s.getSeatID() == seatID){
                this.seat = s;   
            }
        }

        ArrayList<Customer> customerList = sys.getCustomerList();
        //get customer object
        for(Customer c : customerList){
            if (c.getUserID() == userID){
                this.customer = c;
            }
        }

    }

    public void createUI() {
        frame = new JFrame();
        frame.setTitle("Insurance Selection");
        JPanel panel = new JPanel();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        JLabel seatLabel = new JLabel("Selected Seat: " + selectedSeat);
        seatLabel.setBounds(30, 10, 500, 40);
        seatLabel.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(seatLabel);

        JLabel promptLabel = new JLabel("Would you like to Opt-In for Ticket Cancellation Insurance? (+$100)");
        promptLabel.setBounds(30, 40, 500, 40);
        panel.add(promptLabel);

        JButton yesButton = new JButton("Yes");
        yesButton.setBounds(30, 80, 80, 40);
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); 
                openPaymentPage(true);
            }
        });
        panel.add(yesButton);

        JButton noButton = new JButton("No");
        noButton.setBounds(120, 80, 80, 40);
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); 
                openPaymentPage(false);
            }
        });
        panel.add(noButton);

        frame.setVisible(true);
    }

    private void openPaymentPage(boolean hasInsurance) {

        if (selectedFlight != null) {
            Ticket ticket = new Ticket(-1, customer, selectedFlight, seat, hasInsurance, false);
            PaymentPageFrame paymentPageGUI = new PaymentPageFrame(ticket);
            paymentPageGUI.createUI();
        } else {
            System.out.println("ERROR: selectedFlight is null");
        }
    }

    // helper to extract seatID
    private static int extractSeatNumber(String seatString) {
        Pattern pattern = Pattern.compile("Seat (\\d+)");
        Matcher matcher = pattern.matcher(seatString);

        // Check if the pattern matches
        if (matcher.find()) {
            // Extract the matched seat number as a string
            String seatNumberStr = matcher.group(1);

            // Convert the string seat number to an integer
            return Integer.parseInt(seatNumberStr);
        }
        return -1;
    }
    
}
