package com.invoiceservice.services.configuration;

import java.io.File;
import java.math.BigDecimal;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invoiceservice.model.config.CategotryRateConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;;

@RunWith(SpringRunner.class)
@ContextConfiguration(value = "classpath:application-test-context.xml")
public class CategoryRatesConfigServiceTest {
    @Autowired
    CategoryRatesConfigService catConfigService;
    @Autowired 
    ObjectMapper objectMapper;

    @Value("${category.rate.config.file}")
    String configFile;
    
    @Test
    public void voidTestAll() throws Exception {
        // Check that default configuration created
        CategotryRateConfig config = catConfigService.getConfig();
        
        checkDefaultConfiguration(config);
        
        // Create new 
        CategotryRateConfig configNew = new CategotryRateConfig();
        configNew.getCategoryRateMap().put("c1", new BigDecimal("1.1"));
        configNew.getCategoryRateMap().put("c2", new BigDecimal("2.2"));
        configNew.getCategoryRateMap().put("c3", new BigDecimal("3.3"));
        
        catConfigService.setConfig(configNew);
        // Make sure that it updated
        assertEquals(configNew, catConfigService.getConfig());
        
        // Make sure that file created 
        File file = new File(configFile);
        CategotryRateConfig configFromFromFile = objectMapper.readValue(file, CategotryRateConfig.class);
        // Compare 
        assertEquals(configFromFromFile.getCategoryRateMap().get("c1"), configNew.getCategoryRateMap().get("c1"));
        assertEquals(configFromFromFile.getCategoryRateMap().get("c2"), configNew.getCategoryRateMap().get("c2"));
        assertEquals(configFromFromFile.getCategoryRateMap().get("c3"), configNew.getCategoryRateMap().get("c3"));

        // Write to file default and check that it updated
        objectMapper.writeValue(file, config);
        catConfigService.update();
        checkDefaultConfiguration(config);
        // Clean up
        String absPath = file.getAbsolutePath();
        File pathToDelete = new File(absPath.substring(0, absPath.lastIndexOf(File.separator)));
        FileUtils.forceDelete(pathToDelete);
    }

    private void checkDefaultConfiguration(CategotryRateConfig config) {
        assertNotNull(config);
        assertEquals(config.getCategoryRateMap().size(), 3);
        assertEquals(catConfigService.getRateForCategory("c10"), new BigDecimal("10"));
        assertEquals(catConfigService.getRateForCategory("c15"), new BigDecimal("15"));
        assertEquals(catConfigService.getRateForCategory("c21.1"), new BigDecimal("21.1"));
    }
}
