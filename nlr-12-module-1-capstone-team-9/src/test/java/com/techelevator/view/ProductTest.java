package com.techelevator.view;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
private Product product;
  @Before

    public void setUp(){

      product = new Product("test product", new BigDecimal(3.05), "Chip", "A1", 5);

  }

  @Test
    public void product_test_is_in_stock_true() {

      assertTrue(product.isInStock());

  }
    @Test
    public void product_test_is_in_stock_false() {

        product.setQuantity(0);

        assertFalse(product.isInStock());

    }

@Test
    public void product_get_name() {

      assertEquals("test product", product.getName());
}

@Test
    public void product_test_get_price() {

      assertEquals(new BigDecimal(3.05), product.getPrice());

}

@Test
    public void product_get_type() {

      assertEquals("Chip", product.getType());
}

@Test
    public void product_get_slot() {

      assertEquals("A1", product.getSlotId());
}

@Test
    public void testToString_Candy() {

    product = new Product("test product", new BigDecimal(3.05), "Candy", "A1", 5);

      assertEquals("\n Munch Munch, Yum!", product.toString());
}

@Test
    public void testToString_Chip() {

    product = new Product("test product", new BigDecimal(3.05), "Chip", "A1", 5);

    assertEquals("\n Crunch, Crunch Yum!", product.toString());
}

@Test
    public void testToString_Gum() {

    product = new Product("test product", new BigDecimal(3.05), "Gum", "A1", 5);

    assertEquals("\n Chew Chew, Yum!", product.toString());
}

@Test
    public void testToString_Drink() {

    product = new Product("test product", new BigDecimal(3.05), "Drink", "A1", 5);

    assertEquals("\n  Glug Glug, Chug Chug!", product.toString());

}



}
