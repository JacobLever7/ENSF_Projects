package view;

import javax.swing.*;
import java.awt.*;
import model.Ticket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BookingFrame extends JFrame {
    public BookingFrame(Ticket ticket) {
        setTitle("Booking");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        Font boldFont = new Font("Arial", Font.BOLD, 14);

        panel.add(createBoldLabel("Booking Details:", boldFont));
        panel.add(createLabelWithSpacing(""));
        panel.add(createLabelWithSpacing("Customer First Name: " + ticket.getCustomer().getFirstName()));
        panel.add(createLabelWithSpacing("Customer Last Name: " + ticket.getCustomer().getLastName()));
        panel.add(createLabelWithSpacing("Booking Date: " + getCurrentDate())); 
        panel.add(createLabelWithSpacing("Flight Origin: " + ticket.getFlight().getOrigin()));
        panel.add(createLabelWithSpacing("Flight Destination: " + ticket.getFlight().getDestination()));
        panel.add(createLabelWithSpacing("Flight Departure Date: " + ticket.getFlight().getDepartureDate()));
        panel.add(createLabelWithSpacing("Selected Seat: " + ticket.getSeat().getSeatID() + " - " + ticket.getSeat().getSeatType()));
        panel.add(createLabelWithSpacing("Seat Price: $" + ticket.getSeat().getPrice()));
        panel.add(createLabelWithSpacing("Ticket Cancellation Insurance ($100) Selected: " + (ticket.getInsuranceSelection() ? "Yes" : "No")));
        panel.add(createLabelWithSpacing("Total Price: $" + ticket.getTotalPrice()));
    }

    private JLabel createBoldLabel(String text, Font font) {
        JLabel label = new JLabel("<html><b>" + text + "</b></html>");
        label.setFont(font);
        return label;
    }

    private JLabel createLabelWithSpacing(String text) {
        JLabel label = new JLabel(text);
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        return label;
    }

    private String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}
