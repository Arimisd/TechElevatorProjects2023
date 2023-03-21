package com.techelevator.hr;

import com.techelevator.crm.Pet;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    public void testCustomerWithPhoneNumber() {
        String firstName = "Mary";
        String lastName = "Stevens";
        String phoneNumber = "555-555-5555";

        Customer customer = new Customer("Mary", "Stevens", "555-555-5555");
        Assert.assertEquals(firstName, customer.getFirstName());
        Assert.assertEquals(lastName, customer.getLastName());
        Assert.assertEquals(phoneNumber, customer.getPhoneNumber());
    }

    @Test
    public void testCustomerWithoutPhoneNumber() {
        String firstName = "Mary";
        String lastName = "Stevens";
        String phoneNumber = "";

        Customer customer = new Customer("Mary", "Stevens", "");
        Assert.assertEquals(firstName, customer.getFirstName());
        Assert.assertEquals(lastName, customer.getLastName());
        Assert.assertEquals("", customer.getPhoneNumber());
    }


    @Test
    public void testCustomerGetCorrectBalance() {
        Customer customer = new Customer("Mary", "Stevens", "555-555-5555");
        Map<String, Double> servicesRendered = new HashMap<>();
        servicesRendered.put("Grooming", 10.00);
        servicesRendered.put("Walking", 5.00);
        servicesRendered.put("Sitting", 15.00);
        double expected = 30.00;
        double actual = customer.getBalanceDue(servicesRendered);
        assertEquals(expected, actual, 0.01);
    }
}