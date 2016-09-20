package com.invoiceservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.invoiceservice.model.config.CategotryRateConfig;
import com.invoiceservice.services.configuration.CategoryRatesConfigService;

/**
 * CategoryRatesResource resource - provides configuration for tax rates by category
 */
@Component
@Path("category/taxrates")
public class CategoryRatesResource {

    @Autowired
    CategoryRatesConfigService categoryRatesConfigService;
    
    /**
     * 
     * @return category/tax rates
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CategotryRateConfig getCategoryRates() {
        return categoryRatesConfigService.getConfig();
    }

    public CategoryRatesConfigService getCategoryRatesConfigService() {
        return categoryRatesConfigService;
    }

    public void setCategoryRatesConfigService(CategoryRatesConfigService categoryRatesConfigService) {
        this.categoryRatesConfigService = categoryRatesConfigService;
    }
    
}
