class AmountDiscount implements DiscountStrategy {
    private double discountAmount;

    public AmountDiscount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public double applyDiscount(double price) {
        return price - discountAmount;
    }
}
