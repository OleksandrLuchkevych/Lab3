package Task;

public class Product {

    public String name;
    public double price;
    public int quantity;

    public Product(final String name, final double price, final int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

//    public Product() {
//    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product setQuantity(final int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}

