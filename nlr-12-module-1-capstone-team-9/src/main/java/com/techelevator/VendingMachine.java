package com.techelevator;

import com.techelevator.view.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class VendingMachine {
    private final Scanner scanner = new Scanner(System.in);
    private final Map<String, Product> products = new HashMap<>();
    public int quantity;
    private VendingMachine vendingMachine;
    private BigDecimal balance = BigDecimal.valueOf(0);
    private final double currentTransaction = 0.0;
    private static final ArrayList<String> transactions = new ArrayList<>();
    private String slotId;

    public VendingMachine() {
    }


    public void fillVendingMachine() {
        File productInput = new File("vendingmachine.csv");
        try {
            Scanner scanner = new Scanner(productInput);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                String id = parts[0];
                String name = parts[1];
                BigDecimal price = new BigDecimal(parts[2]);
                String type = parts[3];
                int quantity = 5;

                products.put(id,new Product(name, price, type,id, quantity));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException ex){
            System.out.println("Array index out of bounds: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public void addMoney(BigDecimal moneyInput) {
        balance = balance.add(moneyInput);

    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal dispenseChange() {
        if (getBalance().compareTo(BigDecimal.ZERO) > 0) {
           String formattedChange = String.format("%.2f", balance);
            System.out.println("Change dispensed: $" + formattedChange);
            balance = BigDecimal.ZERO;
        } else {
            System.out.println("No change to dispense");
        }
        return balance;
    }

        public boolean isInStock () {
            return quantity >= 1;
        }


    public void displayProducts () {
       fillVendingMachine();
                System.out.println("---- Products ----");
                for (Map.Entry<String, Product> entry : products.entrySet()) {
                    Product product = entry.getValue();
                    System.out.println(product.getSlotId() + ": " + product.getName() + " - $" + product.getPrice() + " [" + product.getQuantity() + " left]");
                }
            }


            public void displayPurchaseMenu () {
            final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
            final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
            final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
            System.out.println("----Purchase Menu----");
            String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};
            boolean menuShown = true;
            while (menuShown) {
                String choice = Arrays.toString(PURCHASE_MENU_OPTIONS);
                System.out.println("Current Money Provided : $" + currentTransaction);

                switch (choice) {
                    case PURCHASE_MENU_OPTION_FEED_MONEY:
                        double amountToFeed = scanner.nextDouble();
                        feedMoney(amountToFeed);
                        break;
                    case PURCHASE_MENU_OPTION_SELECT_PRODUCT:
                        break;
                    case PURCHASE_MENU_OPTION_FINISH_TRANSACTION:
                        finishTransaction();
                        menuShown = false;
                }

                System.out.println("Enter Your Choice : ");

            }
        }

    private void feedMoney(double amountToFeed) {
    }


    private void finishTransaction () {
            BigDecimal previousBalance = vendingMachine.getBalance();
            System.out.println(vendingMachine.dispenseChange());
            transactions.toArray(new String[]{"CHANGE: $" + previousBalance + " $" + vendingMachine.getBalance()});
        }
        
        public void purchaseProduct (Product product) {
            DecimalFormat df = new DecimalFormat("#.00");
             balance = balance.subtract(new BigDecimal(product.getPrice().doubleValue()));
            product.setQuantity(product.getQuantity() - 1);
            transactions.add("PURCHASE: " + product.getName() + " " + product.getSlotId() + " $" + df.format(currentTransaction));
            System.out.println("You have purchased " + product.getName() + " for $" + df.format(product.getPrice()));
        }


    public void restock() {
    }
}

