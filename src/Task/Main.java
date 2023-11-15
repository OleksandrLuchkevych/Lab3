package Task;

import exceptions.*;
import exceptions.ProductNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(final String[] args) throws ReceiptGenerationException, ProductNotAvailableException, ProductNotFoundException, FileLoadException {
        final List<Product> productList = new ArrayList<>();
        final List<Product> productList1 = new ArrayList<>();
        final FileService fileService = new FileService();

        final Product product1 = new Product("Milk", 20.5, 10);
        final Product product2 = new Product("Bread", 1.5, 12);
        final Product product3 = new Product("Butter", 3.5, 12);
        final Product product4 = new Product("Meat", 3.9, 19);

        final Market market = new Market();

        final List<Product> loadedProducts = FileService.loadProductsFromFile("products.txt");

        market.addProduct(product1);
        market.addProduct(product2);
        market.addProduct(product3);

        final Product product1FromFile = market.addProduct(loadedProducts.get(0));
        final Product product2FromFile = market.addProduct(loadedProducts.get(1));
        final Product product3FromFile = market.addProduct(loadedProducts.get(2));


        productList.add(market.buyProduct(product1FromFile, 3));
        productList.add(market.buyProduct(product2FromFile, 2));
        productList.add(market.buyProduct(product1, 1));

        productList1.add(market.buyProduct(product1FromFile, 3));
        productList1.add(market.buyProduct(product3FromFile, 2));
        productList1.add(market.buyProduct(product2, 9));

        market.editProduct(product3, product4);

        market.printProducts();

        market.averagePrice();

        market.filterByPrice(3.0);

        market.sortByPrice();

        final Receipt receipt = new Receipt(new Castomer("Sasha", "Tkach"), productList, LocalDate.of(2021, 10, 10));
        receipt.printReceipt();

        receipt.payReceipt();
        receipt.editReceipt(new Castomer("Petro", "Rut"), productList, LocalDate.of(2023, 10, 11));
        receipt.payReceipt();
        receipt.printReceipt();

        final Receipt receipt1 = new Receipt(new Castomer("Oleh", "Vop"), productList1, LocalDate.of(2021, 10, 12));

        fileService.generateReceiptFile("receipt", receipt);
        fileService.generateReceiptFile("receipt", receipt1);
    }
}
