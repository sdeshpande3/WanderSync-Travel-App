import java.util.List;

public class Order {
    private List<Item> items;
    private String customerName;
    private String customerEmail;

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

        total = reduceGiftCardPrice(total);
    	total = reduceDiscount(total);
        return total;

    }

    public double reduceGiftCardPrice(double total) {
        if (hasGiftCard()) {
            total -= 10.0; // subtract $10 for gift card
        }
        return total;
    }

    public double reduceDiscount(double total) {
        if (total > 100.0) {
            total *= 0.9; // apply 10% discount for orders over $100
        }
        return total;

    }

    private double calculateTax(TaxableItem item) {
        return item.getTaxRate() / 100.0 * item.getPrice();
    }

    private double applyGiftCardDiscount(double total) {
        if (hasGiftCard()) {
            total -= 10.0; // replace with a constant if preferred
        }
        return total;
    }

    private double applyLargeOrderDiscount(double total) {
        if (total > 100.0) { // replace with a constant if preferred
            total *= 0.9; // apply 10% discount for orders over $100
        }
        return total;
    }





    public void sendConfirmationEmail() {
        String message = createOrderMessage();
        EmailSender.sendEmail(customerEmail, "Order Confirmation", message);
    }

    private String createOrderMessage() {
        StringBuilder message = new StringBuilder();
        message.append("Thank you for your order, ").append(customerName).append("!\n\n")
                .append("Your order details:\n");
        for (Item item : items) {
            message.append(item.getName()).append(" - ").append(item.getPrice()).append("\n");
        }
        message.append("Total: ").append(calculateTotalPrice());
        return message.toString();
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
        boolean has_gift_card = false;
        for (Item item : items) {
            if (item instanceof GiftCardItem) {
                has_gift_card = true;
                break;
            }
        }
        return has_gift_card;
    }   

    public void printOrder() {
        System.out.println("Order Details:");
        for (Item item : items) {
            System.out.println(item.getName() + " - " + item.getPrice());
        }

    //orderprinter class
   public void printOrderDetails() {
        public OrderPrinter printer = new OrderPrinter();
        printer.printOrder(items);
    }

    public void addItemsFromAnotherOrder(Order otherOrder) {
        for (Item item : otherOrder.getItems()) {
            items.add(item);
        }
    }

}

