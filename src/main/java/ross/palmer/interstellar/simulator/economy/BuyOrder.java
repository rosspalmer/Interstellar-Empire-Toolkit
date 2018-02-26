package ross.palmer.interstellar.simulator.economy;

import ross.palmer.interstellar.simulator.entity.Entity;

public class BuyOrder {

    private final Entity buyingEntity;
    private final Entity sellingEntity;
    private final Product product;
    private final double maxPrice;
    private final double minAmount;
    private final double maxAmount;

    public BuyOrder(Entity buyingEntity, Entity sellingEntity, Product product,
                    double maxPrice, double minAmount, double maxAmount) {
        this.buyingEntity = buyingEntity;
        this.sellingEntity = sellingEntity;
        this.product = product;
        this.maxPrice = maxPrice;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    public Entity getBuyingEntity() {
        return buyingEntity;
    }

    public Entity getSellingEntity() {
        return sellingEntity;
    }

    public Product getProduct() {
        return product;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public double getMinAmount() {
        return minAmount;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

}
