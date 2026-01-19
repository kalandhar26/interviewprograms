package designpatterns.factory;

public class FactoryMethodPattern {

    // Step 1: Payment Interface
    interface Payment {
        void processPayment(double amount);
        void refundPayment(double amount);
        void getPaymentMethod();
        void getFeePercentage();
    }

    // Step 2: Abstract Factory
    static abstract class PaymentProcessorFactory {
        public abstract Payment createPayment();
    }

    // Step 3: Concrete Payment Implementations
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
        public void getPaymentMethod() {
            System.out.println("Payment method Type is PAYPAL");
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
        public void getPaymentMethod() {
            System.out.println("Payment method Type is STRIPE");
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
        public void getPaymentMethod() {
            System.out.println("payment method Type is BANK");
        }

        @Override
        public void getFeePercentage() {
            System.out.println("Fee percentage for BANK is 20 %");
        }
    }

    // Step 4: Concrete Factories (for Abstract Factory)
    static class PaypalProcessorFactory extends PaymentProcessorFactory {
        @Override
        public Payment createPayment() {
            return new PaypalPayment();
        }
    }

    static class StripeProcessorFactory extends PaymentProcessorFactory {
        @Override
        public Payment createPayment() {
            return new StripePayment();
        }
    }

    static class BankProcessorFactory extends PaymentProcessorFactory {
        @Override
        public Payment createPayment() {
            return new BankPayment();
        }
    }

    // Step 5: Processing Utility
    private static void processPaymentWithFactory(PaymentProcessorFactory factory, double amount) {
        Payment payment = factory.createPayment();
        payment.getPaymentMethod();
        payment.getFeePercentage();
    }

    // Step 6: Entry point
    public static void main(String[] args) {
        processPaymentWithFactory(new PaypalProcessorFactory(), 45);
        processPaymentWithFactory(new StripeProcessorFactory(), 45);
        processPaymentWithFactory(new BankProcessorFactory(), 45);
    }
}
