package ross.palmer.interstellar.simulator.entity;

import ross.palmer.interstellar.simulator.economy.BuyOrder;
import ross.palmer.interstellar.simulator.economy.Product;
import ross.palmer.interstellar.utilities.IdGenerator;

import java.util.HashMap;
import java.util.Map;

public class Entity {

    private final long id;
    private final String name;

    private double credits;
    private Map<Product, Double> productAmounts;
    private Map<Product, Double> productSellingPrices;

    public Entity(String name) {
        id = IdGenerator.getNextId("Entity");
        this.name = name;
        credits = 0;
        productAmounts = new HashMap<>();
        productSellingPrices = new HashMap<>();
    }

    public void addProduct(Product product, double amount) {
        double productAmount = getProductAmount(product);
        productAmount += amount;
        productAmounts.put(product, productAmount);
    }

    public void addCredits(double amount) {
        credits += amount;
    }

    public double getCredits() {
        return credits;
    }

    public double getProductAmount(Product product) {
        double productAmount = 0;
        if (productAmounts.containsKey(product)) {
            productAmount = productAmounts.get(product);
        }
        return productAmount;
    }

    public double getProductSellingPrice(Product product) {
        double sellingPrice = 0;
        if (productSellingPrices.containsKey(product))
            sellingPrice = productSellingPrices.get(product);
        return sellingPrice;
    }

    public void removeProduct(Product product, double amount) {
        double productAmount = getProductAmount(product);
        productAmount -= amount;
        productAmounts.put(product, productAmount);
    }

    public void removeCredits(double amount) {
        credits -= amount;
    }

    public void setProductSellingPrice(Product product, double price) {
        productSellingPrices.put(product, price);
    }

}
