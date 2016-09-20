package com.invoiceservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.invoiceservice.model.config.CategotryRateConfig;
import com.invoiceservice.storage.JsonFileStorage;
import com.invoiceservice.storage.Storage;

@Configuration
public class StorageConfiguration {

    @Value("${category.rate.config.file}")
    private String catRateConfigFile;
    
    @Value("${app.config.file}")
    private String appConfigFile;
    
    @Bean(name = "categotryRateConfigStorage")
    public Storage<CategotryRateConfig> categotryRateConfigStorage() {
        JsonFileStorage<CategotryRateConfig> categotryRateConfig = new JsonFileStorage<CategotryRateConfig>();
        categotryRateConfig.setConfigFile(catRateConfigFile);
        return categotryRateConfig;
    }
}
