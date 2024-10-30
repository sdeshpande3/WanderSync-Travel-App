class Item {
    private String name;
    private double price;
    private int quantity;
    private DiscountType discountType;
    private double discountAmount;
    private DiscountStrategy discountStrategy;

    public Item(String name, double price, int quantity, DiscountType discountType, double discountAmount, DiscountStrategy discountStrategy) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.discountType = discountType;
        this.discountAmount = discountAmount;
        this.discountStrategy = discountStrategy;
    }
    public double getPriceWithDiscount() {
        return discountStrategy.applyDiscount(price);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }
}
