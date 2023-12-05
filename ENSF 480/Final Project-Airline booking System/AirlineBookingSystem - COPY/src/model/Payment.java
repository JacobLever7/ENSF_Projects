package model;

public class Payment {

    // Payment Class Attributes
    private String cardHolderFirstName;
    private String cardHolderLastName;
    private String cardNumber;
    private String expiryDate;
    private int cvv;
    private double total;

    // Payment Constructors
    public Payment(String cardHolderFirstName, String cardHolderLastName, String cardNumber, String expiryDate, int cvv, double total){
        this.cardHolderFirstName = cardHolderFirstName;
        this.cardHolderLastName = cardHolderLastName;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.total = total;
    }

    public Payment(String cardHolderFirstName, String cardHolderLastName, String cardNumber, String expiryDate, int cvv){
        this.cardHolderFirstName = cardHolderFirstName;
        this.cardHolderLastName = cardHolderLastName;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    // Payment Getters
    public String getCardHolderFirstName(){
        return this.cardHolderFirstName;
    }

     public String getCardHolderLastName(){
        return this.cardHolderLastName;
    }

    public String getCardNumber(){
        return this.cardNumber;
    }

    public String getExpiryDate(){
        return this.expiryDate;
    }

    public int getCvv(){
        return this.cvv;
    }
    
    public double getTotal(){
        return this.total;
    }

    // Payment Setters 

    public void setCardHolderFirstName(String firstName){
        this.cardHolderFirstName = firstName;
    }

     public void setCardHolderLastName(String lastName){
        this.cardHolderFirstName = lastName;
    }

    public void setCardNumber(String cardNumber){
        this.cardNumber = cardNumber;
    }

    public void setExpiryDate(String date){
        this.expiryDate = date;
    }

    public void setCvv(int cvv){
        this.cvv = cvv;
    }
    
    public void setTotal(double total){
        this.total = total;
    }

    // Validate payment information
    public boolean isValid() {
        return isValidCardHolderName() && isValidCardNumber() && isValidExpiryDate() && isValidCvv();
    }

    private boolean isValidCardHolderName() {
        return cardHolderFirstName != null && !cardHolderFirstName.isEmpty()
                && cardHolderLastName != null && !cardHolderLastName.isEmpty();
    }

    private boolean isValidCardNumber() {
        return cardNumber != null && cardNumber.matches("\\d{16}");
    }

    private boolean isValidCvv() {
        return String.valueOf(cvv).length() == 3;
    }

    private boolean isValidExpiryDate() {
        // Check if the expiry date is in the format "dd/mm/yyyy"
    if (expiryDate != null && expiryDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
        String[] parts = expiryDate.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);

        // Check for valid month (1 to 12)
        if (month < 1 || month > 12) {
            return false;
        }

        // Check for valid day based on the month
        int[] daysInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (day < 1 || day > daysInMonth[month]) {
            return false;
        }

        return true;
    }
    return false;
    }
    
}
