package com.invoiceservice.services.configuration;

import java.lang.reflect.ParameterizedType;

import javax.annotation.PostConstruct;

import com.invoiceservice.services.ServiceBase;

/**
 * Base class for configuration service
 * <C> class that represents configuration
 */
public abstract class ConfigBase<C> implements ConfigService<C> {
    protected Class<C> configClass;
    volatile protected C config;

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        configClass = (Class<C>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (config == null) {
           config = getDefaultConfig(); 
        }
        update();
    }

    public abstract C getDefaultConfig();

    public C getConfig() {
        return config;
    }
}
