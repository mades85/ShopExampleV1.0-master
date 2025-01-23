package org.example.shop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopApplication {
    private final static Logger LOG = LogManager.getLogger(ShopApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
        LOG.info("http://localhost:8080/");
    }

}
