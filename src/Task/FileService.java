package Task;

import exceptions.FileLoadException;
import exceptions.ProductNotFoundException;
import exceptions.ReceiptGenerationException;
import exceptions.InvalidProductDataException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class FileService {

    public static void generateReceiptFile(String baseFileName, Receipt receipt) throws ReceiptGenerationException {
        String fileName = baseFileName + "_" + receipt.getCastomer().getName() + "_" + receipt.getCastomer().getSurname() + ".txt";

        try {
            List<String> existingLines = Files.exists(Paths.get(fileName)) ? Files.readAllLines(Paths.get(fileName)) : List.of();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writeLineToFile(writer, "Receipt: " + receipt.getDate());
                writeLineToFile(writer, "Customer: " + receipt.getCastomer().getName() + " " + receipt.getCastomer().getSurname());
                writeLineToFile(writer, "Products:");

                receipt.getProducts().stream()
                        .map(product -> product.getName() + ", quantity " + product.getQuantity())
                        .forEach(line -> {
                            try {
                                writeLineToFile(writer, line);
                            } catch (ReceiptGenerationException e) {
                                throw new RuntimeException(e);
                            }
                        });

                writeLineToFile(writer, "Comments:");
                receipt.commentToCheck().forEach(comment -> {
                    try {
                        writeLineToFile(writer, comment);
                    } catch (ReceiptGenerationException e) {
                        throw new RuntimeException(e);
                    }
                });

                writeLineToFile(writer, "Total price: " + calculateTotalPrice(receipt.getProducts()));
                writeLineToFile(writer, "Paid: " + receipt.isPaid());
                writeLineToFile(writer, "------------------------------");

                // Додати існуючі лінії
                existingLines.forEach(line -> {
                    try {
                        writeLineToFile(writer, line);
                    } catch (ReceiptGenerationException e) {
                        throw new RuntimeException(e);
                    }
                });

                System.out.println("Receipt file generated successfully: " + fileName);
            } catch (IOException e) {
                throw new ReceiptGenerationException();
            }
        } catch (IOException e) {
            throw new ReceiptGenerationException();
        }
    }

    private static void writeLineToFile(BufferedWriter writer, String line) throws ReceiptGenerationException {
        try {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            throw new ReceiptGenerationException();
        }
    }

    private static double calculateTotalPrice(List<Product> products) {
        return products.stream()
                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .sum();
    }


    public static List<Product> loadProductsFromFile(String fileName) throws FileLoadException {
        List<Product> loadedProducts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    double price = Double.parseDouble(parts[1].trim());
                    int quantity = Integer.parseInt(parts[2].trim());
                    Product product = new Product(name, price, quantity);
                    loadedProducts.add(product);
                } else {
                    throw new FileLoadException();
                }
            }
            System.out.println("Products loaded successfully from file: " + fileName);
        } catch (IOException | NumberFormatException e) {
            throw new FileLoadException();
        }

        return loadedProducts;
    }
}
