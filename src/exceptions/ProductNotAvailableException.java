package exceptions;

import Task.Product;

public class ProductNotAvailableException extends Exception {

    public ProductNotAvailableException(final Product product) {
        super("Product " + product.getName() + " not available");
    }
}