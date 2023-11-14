package exceptions;

import Task.Product;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(final Product product) {
        super("Product " + product.getName() + " not found");
    }
}
