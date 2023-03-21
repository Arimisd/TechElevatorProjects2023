package com.techelevator.hr;

import com.techelevator.Billable;
import com.techelevator.Person;
import com.techelevator.crm.Pet;

import java.util.List;
import java.util.Map;


public class Customer extends Person implements Billable {

    private String phoneNumber;
    private List<Pet> pets;

    //constructors
    public Customer(String firstName, String lastName, String phoneNumber) {
        super(firstName, lastName);
        this.phoneNumber = phoneNumber;
    }

    public Customer(String firstName, String lastName) {
        super(firstName, lastName);
        this.phoneNumber = "";
    }


    //getters and setters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    @Override
    public double getBalanceDue(Map<String, Double> servicesRendered) {
        double totalDue = 0.0;
        for (Double price : servicesRendered.values()) {
            totalDue += price;
        }
        return totalDue;
    }
}
