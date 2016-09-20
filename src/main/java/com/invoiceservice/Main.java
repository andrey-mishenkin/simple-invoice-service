package com.invoiceservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

/**
 * Main class.
 *
 */
@SuppressWarnings("deprecation")
@SpringBootApplication
@ImportResource({"classpath*:application-context.xml"})
public class Main extends SpringBootServletInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/invoice-service/api/v1/";
    
    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        new Main().configure(new SpringApplicationBuilder(Main.class)).run(args);
        LOG.info(String.format("Invoice Service app started with WADL available at %sapplication.wadl\n", BASE_URI));
        
    }
}

