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

    public static void generateReceiptFile(final String baseFileName, final Receipt receipt) throws ReceiptGenerationException {
        final String fileName = baseFileName + "_" + receipt.getCastomer().getName() + "_" + receipt.getCastomer().getSurname() + ".txt";

        try {
            final List<String> existingLines = Files.exists(Paths.get(fileName)) ? Files.readAllLines(Paths.get(fileName)) : List.of();

            try (final BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
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
                    } catch (final ReceiptGenerationException e) {
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
                    } catch (final ReceiptGenerationException e) {
                        throw new RuntimeException(e);
                    }
                });

                System.out.println("Receipt file generated successfully: " + fileName);
            } catch (final IOException e) {
                throw new ReceiptGenerationException();
            }
        } catch (final IOException e) {
            throw new ReceiptGenerationException();
        }
    }

    private static void writeLineToFile(final BufferedWriter writer, final String line) throws ReceiptGenerationException {
        try {
            writer.write(line);
            writer.newLine();
        } catch (final IOException e) {
            throw new ReceiptGenerationException();
        }
    }

    private static double calculateTotalPrice(final List<Product> products) {
        return products.stream()
                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .sum();
    }


    public static List<Product> loadProductsFromFile(final String fileName) throws FileLoadException {
        final List<Product> loadedProducts = new ArrayList<>();

        try (final BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                final String[] parts = line.split(",");
                if (parts.length == 3) {
                    final String name = parts[0].trim();
                    final double price = Double.parseDouble(parts[1].trim());
                    final int quantity = Integer.parseInt(parts[2].trim());
                    final Product product = new Product(name, price, quantity);
                    loadedProducts.add(product);
                } else {
                    throw new FileLoadException();
                }
            }
            System.out.println("Products loaded successfully from file: " + fileName);
        } catch (final IOException | NumberFormatException e) {
            throw new FileLoadException();
        }

        return loadedProducts;
    }
}
