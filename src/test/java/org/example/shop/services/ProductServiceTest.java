package org.example.shop.services;

import org.example.shop.Constants;
import org.example.shop.enums.Sorting;
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
        Product xgmo = productService.getProductById(171);
        assertNotNull(xgmo);
        assertTrue(xgmo.getName().startsWith("XGMO"));
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
        List<Product> expectet =  productService.getProducts().subList(0, Constants.PAGE_SIZE);

        assertEquals(expectet, productService.getRange(0, Constants.PAGE_SIZE));

    }

    @Test
    void getProductRange_16_to_PAGE_SIZE_x_2() {
        List<Product> expectet =  productService.getProducts().subList(16, Constants.PAGE_SIZE * 2);

        assertEquals(expectet, productService.getRange(16, Constants.PAGE_SIZE * 2));
    }

    @Test
    void getProductRange_negative_from() {
        assertEquals(null, productService.getRange(-1, Constants.PAGE_SIZE));
    }


    @Test
    void getProductRange_to_greater_than_size() {
        assertEquals(null,
                productService.getRange(15, productService.getProducts().size()
                        + Constants.PAGE_SIZE + 1));
    }

    @Test
    void sortArticles_name_desc() {
        List<Product> products = productService.getProducts();
        productService.sortProducts(Sorting.NAME_DESC);

        Product firstProduct = products.get(0);
        assertTrue(firstProduct.getName().startsWith("Xifo LYF Earth"));
    }

    @Test
    void sortArticels_name_asc() {
        List<Product> products = productService.getProducts();
        productService.sortProducts(Sorting.NAME_ASC);

        Product firstProduct = products.get(0);
        assertTrue(firstProduct.getName().startsWith("2.5mm Male"));
    }

    @Test
    void sortArticels_price_desc() {
        List<Product> products = productService.getProducts();
        productService.sortProducts(Sorting.PRICE_DESC);

        Product firstProduct = products.get(0);
        assertTrue(firstProduct.getName().startsWith("Xiaomi 11 Lite"));
    }

    @Test
    void sortArticels_price_asc() {
        List<Product> products = productService.getProducts();
        productService.sortProducts(Sorting.PRICE_ASC);

        Product firstProduct = products.get(0);
        assertTrue(firstProduct.getName().startsWith("Amazon Brand"));
    }

    @Test
    void sortArticles_rating_desc() {
        List<Product> products = productService.getProducts();
        productService.sortProducts(Sorting.RATING_DESC);

        Product firstProduct = products.get(0);
        assertTrue(firstProduct.getName().startsWith("CLAVIER Pulse in-Ear"));
    }

    @Test
    void sortArticles_rating_asc() {
        List<Product> products = productService.getProducts();
        productService.sortProducts(Sorting.RATING_ASC);

        Product firstProduct = products.get(0);
        assertTrue(firstProduct.getName().startsWith("Screen Magnifier for"));
    }

    @Test
    void sortArticles_default() {
        List<Product> products = productService.getProducts();
        productService.sortProducts(null);

        Product firstProduct = products.get(0);
        assertTrue(firstProduct.getName().startsWith("2.5mm Male"));

    }
}
