package org.example.shop.services;

import org.example.shop.Constants;
import org.example.shop.enums.Category;
import org.example.shop.enums.Sorting;
import org.example.shop.model.Product;
import org.example.shop.services.Pagination;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.shop.Constants.PAGE_SIZE;

/**
 * Handles interaction with the product range
 *
 * @author Matthias Wenning
 * @version 1.7
 * @since 1.2
 */
@Service
public class ProductService {
    private final static Logger LOG = LogManager.getLogger(ProductService.class);
    private List<Product> products;
    private List<Product> allProducts;
    private Pagination pagination;

    public ProductService() {
        if (products == null) {
            products = readProducts(Constants.DATA_SOURCE);
            allProducts = new ArrayList<>(products);
            pagination = new Pagination(products.size(), PAGE_SIZE, 1);
        }
    }

    /**
     * Reads the product range from a CSV file
     *
     * @param fileName of the CSV file containing the products
     * @return the {@link Product} list
     */
    List<Product> readProducts(String fileName) {
        List<Product> products = new ArrayList<>();
        try {
            Reader in = new FileReader(fileName);
            LOG.debug("Reading product from '{}'", fileName);

            CSVFormat csvFormat = CSVFormat.EXCEL
                    .withFirstRecordAsHeader()
                    .builder()
                    .setDelimiter(';')
                    .build();
            Iterable<CSVRecord> records = csvFormat.parse(in);

            int articleId = 1;
            for (CSVRecord record : records) {
                // fixes TradeMark ™ display bug
                String name = record.get(0).replace("™", "&trade;");

                String image = record.get("image");
                if (image.isBlank()) image = "/images/book-placeholder.png";

                String strRating = record.get("rating");
                double rating = (!strRating.isBlank()) ? Double.parseDouble(strRating) : 0.0;

                String strNoOfRatings = record.get("no_of_ratings");
                int noOfRatings = (!strNoOfRatings.isBlank()) ? Integer.parseInt(strNoOfRatings) : 0;

                String strActualPrice = record.get("actual_price");
                double actualPrice = (!strActualPrice.isBlank()) ? Double.parseDouble(strActualPrice) : 0.0;

                String strDiscountPrice = record.get("discount_price");
                double discountPrice = (!strDiscountPrice.isBlank()) ? Double.parseDouble(strDiscountPrice) : 0.0;

                // added "category" field to model
                String categoryLabel = record.get("category");
                Category category = Category.fromLabel(categoryLabel);
                Product product = new Product(articleId++, name, category, image, rating, noOfRatings, actualPrice, discountPrice);

                products.add(product);
            }

            LOG.debug("Loaded {} products", products.size());
            in.close();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return products;
    }

    /**
     * Delivers the list of products, i.e. the product range.
     *
     * @return the product range
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Returns the actual number of products in the range of products
     *
     * @return the number of products in the range
     */
    public int getNumberOfProducts() {
        return products.size();
    }

    /**
     * Returns the current {@link Pagination}
     *
     * @return
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * Delivers a range of products, limited by <code>from</code> and <code>to</code>
     *
     * @param from start of range
     * @param to   end of range
     * @return a range of products
     */
    public List<Product> getRange(int from, int to) {
        // avoid negative "from" and "to"
        if (from < 0 || to < 0) {
            from = 0;
            to = PAGE_SIZE;
        }
        // avoid to > size - 1
        to = Math.min(getNumberOfProducts(), to);
        // deliver subList(from, to)
        return products.subList(from, to);
    }

    /**
     * Returns categorized, sorted range of products
     *
     * @param category
     * @param sorting
     * @param page
     * @return the list of products
     */
    public List<Product> getCategorizedSorted(Category category, Sorting sorting, int page) {
        categorizeProducts(category);
        sortProducts(sorting);
        pagination = new Pagination(getNumberOfProducts(), PAGE_SIZE, page);
        return getRange(pagination.getFrom(), pagination.getTo());
    }

    /**
     * Filters products by a given category
     *
     * @param category
     * @return the filtered products
     */
    void categorizeProducts(Category category) {
        // re-read products from data source
        if (category == null || category == Category.ALL) {
            products = allProducts;
        } else {
            products = allProducts.stream()
                    .filter(product -> product.getCategory().equals(category))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Sorts the product list by name, discount price or rating
     *
     * @param sorting {@link Sorting}
     */
    void sortProducts(Sorting sorting) {
        if (sorting != null) {
            switch (sorting) {
                case NAME_ASC:
                    products.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
                    break;
                case NAME_DESC:
                    products.sort((a, b) -> b.getName().compareToIgnoreCase(a.getName()));
                    break;

                case PRICE_ASC:
                    products.sort((a, b) -> (int) (a.getDiscountPrice() * 100 - b.getDiscountPrice() * 100));
                    break;
                case PRICE_DESC:
                    products.sort((a, b) -> (int) (b.getDiscountPrice() * 100 - a.getDiscountPrice() * 100));
                    break;

                case RATING_ASC:
                    products.sort((a, b) -> (int) (a.getRating() * 10 - b.getRating() * 10));
                    break;
                case RATING_DESC:
                    products.sort((a, b) -> (int) (b.getRating() * 10 - a.getRating() * 10));
                    break;
            }
        }
    }

    /**
     * Gets a product by its id
     *
     * @param id the id of the product to be found
     * @return the found {@link Product} or null, otherwise
     */
    public Product getProductById(long id) {
        for (Product product : allProducts) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
}