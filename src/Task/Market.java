package Task;

import exceptions.FileLoadException;
import exceptions.ProductNotAvailableException;
import exceptions.ProductNotFoundException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Market {
    private List<Product> products = new ArrayList<>();
    private List<Receipt> receipts = new ArrayList<>();


    public Product addProduct(final Product product) {
        products.add(product);
        return product;
    }

    public boolean isAvailable(final Product product, final int quantity) {
        return products.stream()
                .anyMatch(e -> e.getName().equals(product.getName()) && e.getQuantity() >= quantity);
    }

    public Product sellProduct(final Product product, final int quantity) throws ProductNotFoundException, ProductNotAvailableException {
        if (isAvailable(product, quantity)) {
            products = products.stream()
                    .map(e -> e.getName().equals(product.getName()) ?
                            new Product(e.getName(), e.getPrice(), e.getQuantity() - quantity) : e)
                    .collect(Collectors.toList());
        } else {
            throw new ProductNotAvailableException(product);
        }

        return product;
    }

    public Product buyProduct(final Product product, final int quantity) throws ProductNotFoundException, ProductNotAvailableException {
        if (isAvailable(product, quantity)) {
            products = products.stream()
                    .map(e -> e.getName().equals(product.getName()) ?
                            new Product(e.getName(), e.getPrice(), quantity) : e)
                    .collect(Collectors.toList());
        } else {
            throw new ProductNotAvailableException(product);
        }

        return product;
    }

    public void editProduct(final Product product1, final Product product2) {
        products.remove(product1);
        products.add(product2);
    }

    public void printProducts() {
        products.forEach(e -> System.out.println(e.getName() + " " + e.getPrice() + " " + e.getQuantity()));
    }

    public int countProducts() {
        return products.stream()
                .mapToInt(Product::getQuantity)
                .sum();
    }

    public void averagePrice() {
        final double averagePrice = products.stream()
                .mapToDouble(e -> e.getPrice() * e.getQuantity())
                .sum() / countProducts();
        System.out.println("Average price: " + averagePrice);
    }

    public void filterByPrice(final double price) {
        products.stream()
                .filter(e -> e.getPrice() < price)
                .forEach(e -> System.out.println(e.getName() + " " + e.getPrice()));
    }

    public void sortByPrice() {
        products.stream()
                .sorted((e1, e2) -> Double.compare(e1.getPrice(), e2.getPrice()))
                .forEach(e -> System.out.println(e.getName() + " " + e.getPrice()));
    }
}
