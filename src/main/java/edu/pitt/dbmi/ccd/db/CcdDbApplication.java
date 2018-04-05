package edu.pitt.dbmi.ccd.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * Mar 19, 2017 6:51:58 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class CcdDbApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(CcdDbApplication.class, args);
    }

}
