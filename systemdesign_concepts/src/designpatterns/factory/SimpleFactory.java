package designpatterns.factory;

public class SimpleFactory {

    // Step 1: Payment Interface
    interface Payment {
        void processPayment(double amount);
        void refundPayment(double amount);
        String getPaymentMethod();
        void getFeePercentage();
    }

    // Step 2: Concrete Classes
    static class PaypalPayment implements Payment {
        @Override
        public void processPayment(double amount) {
            System.out.println("Connecting to PAYPAL api and Processing the Payment");
        }

        @Override
        public void refundPayment(double amount) {
            System.out.println("Paypal is refunding the payment");
        }

        @Override
        public String getPaymentMethod() {
            System.out.println("Payment method Type is PAYPAL");
            return "PAYPAL";
        }

        @Override
        public void getFeePercentage() {
            System.out.println("Fee percentage for PAYPAL is 10 %");
        }
    }

    static class StripePayment implements Payment {
        @Override
        public void processPayment(double amount) {
            System.out.println("Connecting to STRIPE api and Processing the Payment");
        }

        @Override
        public void refundPayment(double amount) {
            System.out.println("STRIPE is refunding the payment");
        }

        @Override
        public String getPaymentMethod() {
            System.out.println("Payment method Type is STRIPE");
            return "STRIPE";
        }

        @Override
        public void getFeePercentage() {
            System.out.println("Fee percentage for STRIPE is 15 %");
        }
    }

    static class BankPayment implements Payment {
        @Override
        public void processPayment(double amount) {
            System.out.println("Connecting to BANK api and Processing the Payment");
        }

        @Override
        public void refundPayment(double amount) {
            System.out.println("BANK is refunding the payment");
        }

        @Override
        public String getPaymentMethod() {
            System.out.println("payment method Type is BANK");
            return "BANK";
        }

        @Override
        public void getFeePercentage() {
            System.out.println("Fee percentage for BANK is 20 %");
        }
    }

    // Step 3: Factory
    static class SimplePaymentFactory {
        public static Payment createPayment(String paymentType) {
            return switch (paymentType) {
                case "PAYPAL" -> new PaypalPayment();
                case "STRIPE" -> new StripePayment();
                case "BANK_TRANSFER" -> new BankPayment();
                case null, default -> throw new IllegalArgumentException("Unknown Payment Type");
            };
        }
    }

    // Step 4: Processing Utility
    private static void processPaymentWithFactory(String type, double amount) {
        Payment payment = SimplePaymentFactory.createPayment(type);
        payment.processPayment(amount);
        payment.getFeePercentage();
    }

    // Step 5: Entry point
    public static void main(String[] args) {
        processPaymentWithFactory("PAYPAL", 45);
    }
}
