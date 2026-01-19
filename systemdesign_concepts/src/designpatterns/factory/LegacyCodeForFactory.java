package designpatterns.factory;

public class LegacyCodeForFactory {

    // Business method
    public static void processPayment(String paymentType, double amount) {
        switch (paymentType) {
            case "PAYPAL" -> System.out.println("Connecting to PAYPAL api");
            case "STRIPE" -> System.out.println("Connecting to stripe api");
            case "BANK_TRANSFER" -> System.out.println("Connecting to Bank for Transfer");
            case null, default -> System.out.println("Something went wrong..");
        }
    }

    // Entry point
    public static void main(String[] args) {
        String paymentType = "BANK_TRANSFER";
        processPayment(paymentType, 30);
    }
}
