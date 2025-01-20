package org.example.shop.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.example.shop.model.Product;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    public static final int PAGE_SIZE = 15;
    // TODO: move to application.properties
    private static final String DATA_SOURCE = "src/main/resources/data/electronics.csv";
    private List<Product> products;


    public ProductService() {
        if (products == null) {
            products = readProducts(DATA_SOURCE);
        }
    }

    public List<Product> getProductsRange(int from, int to) {
        if (from >= 0 && to >= 0 && !(to > products.size() - 1)) {
            return products.subList(from, to);
        }
        return null;
    }

    List<Product> readProducts(String fileName) {
        List<Product> products = new ArrayList<>();
        try {
            Reader in = new FileReader(fileName);
            CSVFormat csvFormat = CSVFormat.EXCEL
                    .withFirstRecordAsHeader()
                    .builder()
                    .setDelimiter(';')
                    .build();
            Iterable<CSVRecord> records = csvFormat.parse(in);

            int articleId = 0;
            for (CSVRecord record : records) {
                // fix TradeMark ™ display bug
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

                Product product = new Product(articleId++, name, image, rating, noOfRatings, actualPrice, discountPrice);
                products.add(product);
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    /**
     * Delivers the list of products, i.e. the product range
     * @return the product range
     */

    public List<Product> getProducts() {
        return products;
    }

    /**
     * Gets a product by its id
     * @param id the id of the product to be found
     * @return the found {@link Product} or null , otherwise
     */

    public Product getProductById(long id) {
        if(id > products.size() - 1) return null;
        return products.get((int) id);
        }
    }

