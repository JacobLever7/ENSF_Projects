package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import model.*;

public class BrowseSeatFrame {
    private JFrame frame;
    private JPanel seatPanel;
    private Flight selectedFlight;
    private FlightSystem sys;
    private int userID;
    private static final int SEATS_PER_FLIGHT = 12;


    public BrowseSeatFrame(Flight selectedFlight, int userID) {
        this.selectedFlight = selectedFlight;
        this.userID = userID;
        this.sys = FlightSystem.getInstance();
    }

    public void createUI() { 
        frame = new JFrame();
        frame.setTitle("Browse Seats - " + selectedFlight.getOrigin() + " -> "+ selectedFlight.getDestination() + " : "+ selectedFlight.getDepartureDate());
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(3, 4)); 

        // Calculate the starting seat number based on the flight
        int startingSeatNumber = (selectedFlight.getFlightID() - 1) * SEATS_PER_FLIGHT + 1;

        // Create seats
        for (int row = 1; row <= 3; row++) {
            String seatType;
            if (row == 1) {
                seatType = "Ordinary";
            } else if (row == 2) {
                seatType = "Comfort";
            } else {
                seatType = "Business";
            }

            for (int col = 1; col <= 4; col++) {
                int seatNumber = startingSeatNumber + (row - 1) * 4 + col - 1;
                JButton seatButton = createSeatButton(seatType, seatNumber);
                seatPanel.add(seatButton);
            }
        }

        frame.add(seatPanel);
        frame.setVisible(true);
    }


    private JButton createSeatButton(String seatType, int seatNumber) {
        JButton seatButton = new JButton(seatType + " - Seat " + seatNumber);
        seatButton.addActionListener(new SeatClickListener());

        // Check seat availability and enable/disable the seat accordingly
        if (!isSeatAvailable(seatNumber)) {
            seatButton.setEnabled(false);
        }

        return seatButton;
    }

    private boolean isSeatAvailable(int seatNumber) {

        ArrayList<Seat> seatsList = sys.getSeatList();

        for (Seat seat : seatsList){
            if (seat.getSeatID() == seatNumber){
                if(seat.isAvailable()){
                    return true;
                }
                else return false;
            }
        }
        System.out.println("ERROR: seatNumber " + seatNumber + " not found in seat list");
        return false; 
    }

    private class SeatClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton seatButton = (JButton) e.getSource();
            String seatName = seatButton.getText();
            frame.dispose();
            openInsuranceSelectionFrame(seatName, selectedFlight, userID);
        }
    }

    private void openInsuranceSelectionFrame(String selectedSeat, Flight selectedFlight, int userID) {
        SelectInsuranceFrame insuranceGUI = new SelectInsuranceFrame(selectedSeat, selectedFlight, userID);
        insuranceGUI.createUI();
    }
}