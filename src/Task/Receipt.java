package Task;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Receipt {
    private Castomer castomer;
    private List<Product> products;
    boolean isPaid;

    private LocalDate date;

    public Receipt(Castomer castomer, List<Product> products, LocalDate date) {
        this.castomer = castomer;
        this.products = products;
        this.isPaid = false;
        this.date = date;
    }

    public void payReceipt() {
        this.isPaid = true;
    }

    public Castomer getCastomer() {
        return castomer;
    }

    public void setCastomer(Castomer castomer) {
        this.castomer = castomer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void printReceipt() {
        if (isPaid == true) {
            System.out.println("Receipt: " + this.date);
            System.out.println("Castomer: " + this.castomer.getName() + " " + this.castomer.getSurname());
            System.out.println("Products: ");
            this.products.forEach(e -> System.out.println(e.getName() + "," + "quantity " + e.getQuantity()));
            commentToCheck();
        } else {
            System.out.println("Receipt is not paid.To see receipt pay it.");
        }
    }

    public List<String> commentToCheck() {
        return products.stream()
                .filter(e -> (e.getName().equals("Milk") || e.getName().equals("Meat") || e.getName().equals("Butter")))
                .map(e -> "Put " + e.getName() + " in the fridge")
                .collect(Collectors.toList());
    }


    public void editReceipt(Castomer newCustomer, List<Product> newProducts, LocalDate newDate) {
        if (!isPaid) {
            this.castomer = newCustomer;
            this.products = newProducts;
            this.date = newDate;
            System.out.println("Receipt edited successfully.");
        } else {
            System.out.println("Cannot edit a paid receipt.");
        }
    }


}

