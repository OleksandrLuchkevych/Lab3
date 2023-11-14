package Task;

import exceptions.*;
import exceptions.ProductNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ReceiptGenerationException, ProductNotAvailableException, ProductNotFoundException, FileLoadException {
        List<Product> productList = new ArrayList<>();
        List<Product> productList1 = new ArrayList<>();
        FileService fileService = new FileService();

        Product product1 = new Product("Milk", 20.5, 10);
        Product product2 = new Product("Bread", 1.5, 12);
        Product product3 = new Product("Butter", 3.5, 12);
        Product product4 = new Product("Meat", 3.9, 19);

        Market market = new Market();

        List<Product> loadedProducts = FileService.loadProductsFromFile("products.txt");

        market.addProduct(product1);
        market.addProduct(product2);
        market.addProduct(product3);
        Product productFromFile1 = market.addProduct(loadedProducts.get(0));
        Product productFromFile2 = market.addProduct(loadedProducts.get(1));
        Product productFromFile3 = market.addProduct(loadedProducts.get(2));
        market.addProduct(loadedProducts.get(1));
        productList1.add(market.buyProduct(productFromFile1, 3));
        productList1.add(market.buyProduct(productFromFile3, 2));
        productList1.add(market.buyProduct(product2, 9));

        market.editProduct(product3, product4);

        market.printProducts();

        market.averagePrice();

        market.filterByPrice(3.0);

        market.sortByPrice();

        Receipt receipt = new Receipt(new Castomer("Ivan", "Ivanov"), productList, LocalDate.of(2021, 10, 10));
        receipt.printReceipt();

        receipt.payReceipt();
        receipt.editReceipt(new Castomer("Petr", "Ptrov"), productList1, LocalDate.of(2023, 10, 11));
        receipt.payReceipt();
        receipt.printReceipt();

        fileService.generateReceiptFile("receipt", receipt);
    }
}
