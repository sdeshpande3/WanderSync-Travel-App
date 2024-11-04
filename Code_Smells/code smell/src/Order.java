import java.util.List;

public class Order {
    private List<Item> items;
    private String customerName;
    private String customerEmail;

    // Constants to replace magic numbers
    private static final double GIFT_CARD_DISCOUNT = 10.0; // Amount to subtract for gift card
    private static final double LARGE_ORDER_THRESHOLD = 100.0; // Minimum order amount for discount
    private static final double LARGE_ORDER_DISCOUNT_RATE = 0.9; // 10% discount for large orders

    public Order(List<Item> items, String customerName, String customerEmail) {
        this.items = items;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }

    public double calculateTotalPrice() {
        double total = calculateItemTotal();
        total = applyGiftCardDiscount(total);
        total = applyLargeOrderDiscount(total);
        return total;
    }

    private double calculateItemTotal() {
        double total = 0.0;
        for (Item item : items) {
            total += item.getPriceWithDiscount() * item.getQuantity();
            if (item instanceof TaxableItem) {
                total += calculateTax((TaxableItem) item);
            }
        }
        return total;
    }

    private double calculateTax(TaxableItem item) {
        return item.getTaxRate() / 100.0 * item.getPrice();
    }

    private double applyGiftCardDiscount(double total) {
        if (hasGiftCard()) {
            total -= GIFT_CARD_DISCOUNT; // use constant for gift card discount
        }
        return total;
    }

    private double applyLargeOrderDiscount(double total) {
        if (total > LARGE_ORDER_THRESHOLD) { // use constant for large order threshold
            total *= LARGE_ORDER_DISCOUNT_RATE; // use constant for large order discount rate
        }
        return total;
    }

    public void sendConfirmationEmail() {
        String message = "Thank you for your order, " + customerName + "!\n\n" +
                "Your order details:\n";
        for (Item item : items) {
            message += item.getName() + " - " + item.getPrice() + "\n";
        }
        message += "Total: " + calculateTotalPrice();
        EmailSender.sendEmail(customerEmail, "Order Confirmation", message);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public boolean hasGiftCard() {
        for (Item item : items) {
            if (item instanceof GiftCardItem) {
                return true; // return immediately upon finding a gift card
            }
        }
        return false; // no gift card found
    }

    // order printer class
    public void printOrderDetails() {
        OrderPrinter printer = new OrderPrinter();
        printer.printOrder(items);
    }

    public void addItemsFromAnotherOrder(Order otherOrder) {
        for (Item item : otherOrder.getItems()) {
            items.add(item);
        }
    }
}