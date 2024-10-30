class PercentageDiscount implements DiscountStrategy {
    private double discountRate;

    public PercentageDiscount(double discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public double applyDiscount(double price) {
        return price - (discountRate * price);
    }
}