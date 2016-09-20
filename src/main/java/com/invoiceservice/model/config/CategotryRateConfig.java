package com.invoiceservice.model.config;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CategotryRateConfig {
    Map<String, BigDecimal> categoryRateMap = new HashMap<String, BigDecimal>();

    public Map<String, BigDecimal> getCategoryRateMap() {
        return categoryRateMap;
    }

    public void setCategoryRateMap(Map<String, BigDecimal> categoryRateMap) {
        this.categoryRateMap = categoryRateMap;
    }
}
