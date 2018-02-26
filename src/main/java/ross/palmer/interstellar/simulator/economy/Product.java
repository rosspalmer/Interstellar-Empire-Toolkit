package ross.palmer.interstellar.simulator.economy;

import ross.palmer.interstellar.utilities.IdGenerator;

import java.util.Objects;

public class Product {

    private final long id;
    private final String name;

    public Product(String name) {
        id = IdGenerator.getNextId("Product");
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name;
    }
}
