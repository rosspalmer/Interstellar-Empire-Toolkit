package ross.palmer.interstellar.simulator.economy;

import org.junit.Before;
import org.junit.Test;
import ross.palmer.interstellar.simulator.entity.Entity;

import static org.junit.Assert.*;

public class ProductTransactionTest {

    private Product product;
    private Entity buyingEntity;
    private Entity sellingEntity;

    @Before
    public void setup() {

        product = new Product("Product");
        buyingEntity = new Entity("Buyer");
        sellingEntity = new Entity("Seller");

        buyingEntity.addCredits(10000);
        sellingEntity.addCredits(10000);

    }

    @Test
    public void startAsBuyer() {

        BuyOrder buyOrder = new BuyOrder(buyingEntity, sellingEntity, product,
                10, 100, 200);
        ProductTransaction transaction = new ProductTransaction(buyOrder);

        assertFalse(transaction.isFinalized());
        assertEquals(0, transaction.getFinalPrice(), 0.000001);
        assertEquals(0, transaction.getFinalAmount(), 0.000001);

        assertEquals(10000, buyingEntity.getCredits(), 0.000001);
        assertEquals(0, buyingEntity.getProductAmount(product), 0.000001);
        assertEquals(10000, sellingEntity.getCredits(), 0.000001);
        assertEquals(0, sellingEntity.getProductAmount(product), 0.000001);

        transaction.startAsBuyer(buyingEntity);

        assertFalse(transaction.isFinalized());
        assertEquals(0, transaction.getFinalPrice(), 0.000001);
        assertEquals(0, transaction.getFinalAmount(), 0.000001);

        assertEquals(10000 - (200 * 10), buyingEntity.getCredits(), 0.000001);
        assertEquals(0, buyingEntity.getProductAmount(product), 0.000001);
        assertEquals(10000, sellingEntity.getCredits(), 0.000001);
        assertEquals(0, sellingEntity.getProductAmount(product), 0.000001);

    }

    @Test
    public void completeAsSeller_OverMaxAmount() {

        BuyOrder buyOrder = new BuyOrder(buyingEntity, sellingEntity, product,
                10, 100, 200);
        ProductTransaction transaction = new ProductTransaction(buyOrder);
        transaction.startAsBuyer(buyingEntity);

        sellingEntity.addProduct(product, 300);
        sellingEntity.setProductSellingPrice(product, 8);

        assertEquals(10000 - (200 * 10), buyingEntity.getCredits(), 0.000001);
        assertEquals(0, buyingEntity.getProductAmount(product), 0.000001);
        assertEquals(10000, sellingEntity.getCredits(), 0.000001);
        assertEquals(300, sellingEntity.getProductAmount(product), 0.000001);

        transaction.completeAsSeller(sellingEntity);

        assertTrue(transaction.isFinalized());
        assertEquals(8, transaction.getFinalPrice(), 0.000001);
        assertEquals(200, transaction.getFinalAmount(), 0.000001);

        assertEquals(10000 - (200 * 10), buyingEntity.getCredits(), 0.000001);
        assertEquals(0, buyingEntity.getProductAmount(product), 0.000001);
        assertEquals(10000 + (200 * 8), sellingEntity.getCredits(), 0.000001);
        assertEquals(100, sellingEntity.getProductAmount(product), 0.000001);

    }

    @Test
    public void completeAsSeller_UnderMaxAmount() {

        BuyOrder buyOrder = new BuyOrder(buyingEntity, sellingEntity, product,
                10, 100, 200);
        ProductTransaction transaction = new ProductTransaction(buyOrder);
        transaction.startAsBuyer(buyingEntity);

        sellingEntity.addProduct(product, 175);
        sellingEntity.setProductSellingPrice(product, 8);

        assertFalse(transaction.isFinalized());
        assertEquals(0, transaction.getFinalPrice(), 0.000001);
        assertEquals(0, transaction.getFinalAmount(), 0.000001);

        assertEquals(10000 - (200 * 10), buyingEntity.getCredits(), 0.000001);
        assertEquals(0, buyingEntity.getProductAmount(product), 0.000001);
        assertEquals(10000, sellingEntity.getCredits(), 0.000001);
        assertEquals(175, sellingEntity.getProductAmount(product), 0.000001);

        transaction.completeAsSeller(sellingEntity);

        assertTrue(transaction.isFinalized());
        assertEquals(8, transaction.getFinalPrice(), 0.000001);
        assertEquals(175, transaction.getFinalAmount(), 0.000001);

        assertEquals(10000 - (200 * 10), buyingEntity.getCredits(), 0.000001);
        assertEquals(0, buyingEntity.getProductAmount(product), 0.000001);
        assertEquals(10000 + (175 * 8), sellingEntity.getCredits(), 0.000001);
        assertEquals(0, sellingEntity.getProductAmount(product), 0.000001);

    }

    @Test
    public void completeAsSeller_UnderMinAmount() {

        BuyOrder buyOrder = new BuyOrder(buyingEntity, sellingEntity, product,
                10, 100, 200);
        ProductTransaction transaction = new ProductTransaction(buyOrder);
        transaction.startAsBuyer(buyingEntity);

        sellingEntity.addProduct(product, 75);
        sellingEntity.setProductSellingPrice(product, 8);

        assertFalse(transaction.isFinalized());
        assertEquals(0, transaction.getFinalPrice(), 0.000001);
        assertEquals(0, transaction.getFinalAmount(), 0.000001);

        assertEquals(10000 - (200 * 10), buyingEntity.getCredits(), 0.000001);
        assertEquals(0, buyingEntity.getProductAmount(product), 0.000001);
        assertEquals(10000, sellingEntity.getCredits(), 0.000001);
        assertEquals(75, sellingEntity.getProductAmount(product), 0.000001);

        transaction.completeAsSeller(sellingEntity);

        assertFalse(transaction.isFinalized());
        assertEquals(8, transaction.getFinalPrice(), 0.000001);
        assertEquals(0, transaction.getFinalAmount(), 0.000001);

        assertEquals(10000 - (200 * 10), buyingEntity.getCredits(), 0.000001);
        assertEquals(0, buyingEntity.getProductAmount(product), 0.000001);
        assertEquals(10000, sellingEntity.getCredits(), 0.000001);
        assertEquals(75, sellingEntity.getProductAmount(product), 0.000001);

    }

    @Test
    public void completeAsSeller_OverMaxPrice() {

        BuyOrder buyOrder = new BuyOrder(buyingEntity, sellingEntity, product,
                10, 100, 200);
        ProductTransaction transaction = new ProductTransaction(buyOrder);
        transaction.startAsBuyer(buyingEntity);

        sellingEntity.addProduct(product, 175);
        sellingEntity.setProductSellingPrice(product, 12);

        assertFalse(transaction.isFinalized());
        assertEquals(0, transaction.getFinalPrice(), 0.000001);
        assertEquals(0, transaction.getFinalAmount(), 0.000001);

        assertEquals(10000 - (200 * 10), buyingEntity.getCredits(), 0.000001);
        assertEquals(0, buyingEntity.getProductAmount(product), 0.000001);
        assertEquals(10000, sellingEntity.getCredits(), 0.000001);
        assertEquals(175, sellingEntity.getProductAmount(product), 0.000001);

        transaction.completeAsSeller(sellingEntity);

        assertFalse(transaction.isFinalized());
        assertEquals(12, transaction.getFinalPrice(), 0.000001);
        assertEquals(0, transaction.getFinalAmount(), 0.000001);

        assertEquals(10000 - (200 * 10), buyingEntity.getCredits(), 0.000001);
        assertEquals(0, buyingEntity.getProductAmount(product), 0.000001);
        assertEquals(10000, sellingEntity.getCredits(), 0.000001);
        assertEquals(175, sellingEntity.getProductAmount(product), 0.000001);

    }

    @Test
    public void completeAsBuyer_Finalized() {

        BuyOrder buyOrder = new BuyOrder(buyingEntity, sellingEntity, product,
                10, 100, 200);
        ProductTransaction transaction = new ProductTransaction(buyOrder);
        transaction.startAsBuyer(buyingEntity);

        sellingEntity.addProduct(product, 175);
        sellingEntity.setProductSellingPrice(product, 8);

        transaction.completeAsSeller(sellingEntity);

        assertTrue(transaction.isFinalized());
        assertEquals(8, transaction.getFinalPrice(), 0.000001);
        assertEquals(175, transaction.getFinalAmount(), 0.000001);

        assertEquals(10000 - (200 * 10), buyingEntity.getCredits(), 0.000001);
        assertEquals(0, buyingEntity.getProductAmount(product), 0.000001);
        assertEquals(10000 + (175 * 8), sellingEntity.getCredits(), 0.000001);
        assertEquals(0, sellingEntity.getProductAmount(product), 0.000001);

        transaction.completeAsBuyer(buyingEntity);

        assertTrue(transaction.isFinalized());
        assertEquals(8, transaction.getFinalPrice(), 0.000001);
        assertEquals(175, transaction.getFinalAmount(), 0.000001);

        assertEquals(10000 - (175 * 8), buyingEntity.getCredits(), 0.000001);
        assertEquals(175, buyingEntity.getProductAmount(product), 0.000001);
        assertEquals(10000 + (175 * 8), sellingEntity.getCredits(), 0.000001);
        assertEquals(0, sellingEntity.getProductAmount(product), 0.000001);

    }

    @Test
    public void completeAsBuyer_NotFinalized() {

        BuyOrder buyOrder = new BuyOrder(buyingEntity, sellingEntity, product,
                10, 100, 200);
        ProductTransaction transaction = new ProductTransaction(buyOrder);
        transaction.startAsBuyer(buyingEntity);

        sellingEntity.addProduct(product, 175);
        sellingEntity.setProductSellingPrice(product, 14);

        transaction.completeAsSeller(sellingEntity);

        assertFalse(transaction.isFinalized());
        assertEquals(14, transaction.getFinalPrice(), 0.000001);
        assertEquals(0, transaction.getFinalAmount(), 0.000001);

        assertEquals(10000 - (200 * 10), buyingEntity.getCredits(), 0.000001);
        assertEquals(0, buyingEntity.getProductAmount(product), 0.000001);
        assertEquals(10000, sellingEntity.getCredits(), 0.000001);
        assertEquals(175, sellingEntity.getProductAmount(product), 0.000001);

        transaction.completeAsBuyer(buyingEntity);

        assertFalse(transaction.isFinalized());
        assertEquals(14, transaction.getFinalPrice(), 0.000001);
        assertEquals(0, transaction.getFinalAmount(), 0.000001);

        assertEquals(10000, buyingEntity.getCredits(), 0.000001);
        assertEquals(0, buyingEntity.getProductAmount(product), 0.000001);
        assertEquals(10000, sellingEntity.getCredits(), 0.000001);
        assertEquals(175, sellingEntity.getProductAmount(product), 0.000001);

    }

}