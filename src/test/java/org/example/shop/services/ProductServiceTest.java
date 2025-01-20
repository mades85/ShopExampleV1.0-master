package org.example.shop.services;

import org.example.shop.model.Product;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    private ProductService productService;

    public ProductServiceTest() {
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

    void getProductById_boundary() {
        int size = productService.getProducts().size();
        Product maxProduct = productService.getProducts().get(size - 1);
        assertNotNull(maxProduct);
        assertTrue(maxProduct.getName().startsWith("Xifo LYF Earth"));
    }

    void getProductById_non_existant() {
        Product nonExistant = productService.getProductById(99999);
        assertEquals(null, nonExistant);
    }

    @Test
    void getProductRange_0_to_PAGE_SIZE() {
        List<Product> expectet =  productService.getProducts().subList(0, productService.PAGE_SIZE);

        assertEquals(expectet, productService.getProductsRange(0, productService.PAGE_SIZE));

    }

    @Test
    void getProductRange_16_to_PAGE_SIZE_x_2() {
        List<Product> expectet =  productService.getProducts().subList(16, productService.PAGE_SIZE * 2);

        assertEquals(expectet, productService.getProductsRange(16, productService.PAGE_SIZE * 2));
    }

    @Test
    void getProductRange_negative_from() {
        assertEquals(null, productService.getProductsRange(-1, productService.PAGE_SIZE));
    }


    @Test
    void getProductRange_to_greater_than_size() {
        assertEquals(null,
                productService.getProductsRange(15, productService.getProducts().size()
                        + productService.PAGE_SIZE + 1));
    }
}
