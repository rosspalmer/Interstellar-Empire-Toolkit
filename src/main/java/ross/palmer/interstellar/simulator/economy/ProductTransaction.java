package ross.palmer.interstellar.simulator.economy;

import ross.palmer.interstellar.simulator.entity.Entity;

public class ProductTransaction {

    private final BuyOrder buyOrder;

    private double initialPayment;
    private boolean finalized;

    private double finalPrice;
    private double finalAmount;
    private double finalValue;

    public ProductTransaction(BuyOrder buyOrder) {
        this.buyOrder = buyOrder;
        finalized = false;
    }

    public void startAsBuyer(Entity entity) {
        initialPayment = buyOrder.getMaxAmount() * buyOrder.getMaxPrice();
        entity.removeCredits(initialPayment);
    }

    public void completeAsSeller(Entity entity) {

        determineFinalAmount(entity, buyOrder);

        if (finalAmount > 0) {
            entity.removeProduct(buyOrder.getProduct(), finalAmount);
            entity.addCredits(finalValue);
            finalized = true;
        }

    }

    public void completeAsBuyer(Entity entity) {
        if (finalized) {
            entity.addProduct(buyOrder.getProduct(), finalAmount);
            double excessCredits = initialPayment - finalValue;
            entity.addCredits(excessCredits);
        } else {
            entity.addCredits(initialPayment);
        }
    }

    private void determineFinalAmount(Entity entity, BuyOrder buyOrder) {

        finalPrice = entity.getProductSellingPrice(buyOrder.getProduct());
        finalAmount = entity.getProductAmount(buyOrder.getProduct());

        if (finalPrice <= buyOrder.getMaxPrice()) {

            if (finalAmount > buyOrder.getMinAmount()) {
                finalAmount = Math.min(finalAmount, buyOrder.getMaxAmount());
            } else {
                finalAmount = 0;
            }

        } else {
            finalAmount = 0;
        }
        finalValue = finalPrice * finalAmount;
    }

    public boolean isFinalized() {
        return finalized;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public double getFinalPrice() {
        return finalPrice;
    }
}
