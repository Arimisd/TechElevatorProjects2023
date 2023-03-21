package com.techelevator.view;

import com.techelevator.VendingMachine;

import java.math.BigDecimal;

public class Product extends VendingMachine {
    private final String name;
    private final BigDecimal price;
    private final String type;
    private final String slotId;
    private int quantity;


    public Product(String name, BigDecimal price, String type, String id, int quantity) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.slotId = id;
        this.quantity = quantity;

    }

    public boolean isInStock() {
        return this.quantity >= 1;
    }



    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity () {
            return quantity;
        }

        public String getName () {
            return name;
        }

        public BigDecimal getPrice () {
            return price;
        }

        public String getType () {
            return type;
        }

        public String getSlotId () {
            return slotId;
        }

        @Override
    public String toString() {
        String sound;
            switch (this.type) {
                case "Chip":
                    sound = "\n Crunch, Crunch it's Yummy!";
                    break;
                case "Candy":
                    sound = "\n Munch Munch, Mmm Mmm good!";
                    break;
                case "Gum":
                    sound = "\n Chew Chew, pop!";
                    break;
                case "Drink":
                    sound = "\n  Glug Glug, Chug Chug!";
                    break;
                default:
                    sound = "Aw shucks wrappers again";
                    break;
            }
        return sound;
        }
    }

