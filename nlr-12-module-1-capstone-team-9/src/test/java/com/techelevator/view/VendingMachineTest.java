package com.techelevator.view;

import com.techelevator.VendingMachine;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.*;


public class VendingMachineTest {
    private VendingMachine vendingMachine;
    @Before
    public void setUp(){
        vendingMachine = new VendingMachine();
        vendingMachine.fillVendingMachine();
    }
    @Test
    public void testFillMachine(){
        Map<String, Product> products = vendingMachine.getProducts();
        assertFalse(products.isEmpty());
        assertTrue(products.containsKey("A1"));

    }
    @Test
    public void testAddMoney(){
        BigDecimal moneyInput = BigDecimal.valueOf(5.00);
        vendingMachine.addMoney(moneyInput);
        assertEquals(BigDecimal.valueOf(5.00), vendingMachine.getBalance());

    }
    @Test
    public void dispenseChangeTest(){
        BigDecimal moneyInput = BigDecimal.valueOf(5.00);
        vendingMachine.addMoney(moneyInput);
        BigDecimal dispensedChange = vendingMachine.dispenseChange();
        assertNotEquals(BigDecimal.valueOf(5.00), dispensedChange);
    }

}
