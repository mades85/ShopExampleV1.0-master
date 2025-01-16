package org.example.shop.services;

import org.example.shop.model.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    private ProductService productService;

    public ProductServiceTest(){
        productService = new ProductService();
    }


    @Test
    void readProducts() {
        List<Product> products = productService.readProducts("src/main/resources/data/electronics.csv");
        assertNotNull(products);
        assertEquals(173, products.size());
    }

    @Test
    void getProductById_171() {
        Product xiaomi11 = productService.getProductById(171);
        assertNotNull(xiaomi11);
        assertTrue(xiaomi11.getName().startsWith("Xiaomi 11 Lite"));
    }

    void getProductById_boundary(){
        int size = productService.getProducts().size();
        Product maxProduct = productService.getProducts().get(size-1);
        assertNotNull(maxProduct);
        assertTrue(maxProduct.getName().startsWith("Xifo LYF Earth"));
    }

    void getProductById_non_existant(){
        Product nonExistant = productService.getProductById(99999);
        assertEquals(null, nonExistant);
    }
}
