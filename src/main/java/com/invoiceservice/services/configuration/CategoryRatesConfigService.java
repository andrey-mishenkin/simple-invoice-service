package com.invoiceservice.services.configuration;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;

import com.invoiceservice.model.config.CategotryRateConfig;
import com.invoiceservice.services.Service;
import com.invoiceservice.services.monitor.SystemStatusMonitor;
import com.invoiceservice.storage.Storage;

/** 
 * Service provides rates configuration by category
 *
 */
@org.springframework.stereotype.Service
public class CategoryRatesConfigService extends ConfigBase<CategotryRateConfig> implements Service {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryRatesConfigService.class);

    private SystemStatusMonitor systemStatusMonitor = new SystemStatusMonitor("category-rates-config-service");
    
    @Autowired
    private Storage<CategotryRateConfig> categotryRateConfigStorage;
    
    static final CategotryRateConfig defaultConfig = new CategotryRateConfig();
    static {
        defaultConfig.getCategoryRateMap().put("c10", new BigDecimal("10"));
        defaultConfig.getCategoryRateMap().put("c15", new BigDecimal("15"));
        defaultConfig.getCategoryRateMap().put("c21.1", new BigDecimal("21.1"));
    }
    public BigDecimal getRateForCategory(String category) {
        return getConfig().getCategoryRateMap().get(category);
    }
    
    @Override
    @Scheduled(fixedDelay = 5 * 60 * 1000)  // read every 5 minutes 
    synchronized public void update() {
        try {
            LOG.debug("Reading config for " + configClass);
            config = categotryRateConfigStorage.read(configClass, config);
        } catch (Exception e) {
            String message = "Error while reading config for " + configClass + ": " + e.getMessage();
            systemStatusMonitor.setStatusWarning(message);
            LOG.error(message, e);
        }
    }
    
    synchronized public void setConfig(CategotryRateConfig config) {
        this.config = config;
        try {
            LOG.debug("Saving config for " + configClass);
            categotryRateConfigStorage.write(config);
        } catch (Exception e) {
            String message = "Error while saving config for " + configClass + ": " + e.getMessage();
            systemStatusMonitor.setStatusWarning(message);
            LOG.error(message, e);
        }
    }

    @Override
    public CategotryRateConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    @Bean(name = "categoryRatesConfigSystemStatusMonitor")
    public SystemStatusMonitor getSystemStatusMonitor() {
        return systemStatusMonitor;
    }
    
    @Autowired
    @Qualifier("categoryRatesConfigSystemStatusMonitor")
    public void setSystemStatusMonitor(SystemStatusMonitor systemStatusMonitor) {
        this.systemStatusMonitor = systemStatusMonitor;
    }

    public Storage<CategotryRateConfig> getCatRateConfigStorage() {
        return categotryRateConfigStorage;
    }

    public void setCatRateConfigStorage(Storage<CategotryRateConfig> catRateConfigStorage) {
        this.categotryRateConfigStorage = catRateConfigStorage;
    }
}
