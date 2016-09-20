package com.invoiceservice.services.configuration;

public interface ConfigService<C> {
    public C getConfig();
    public void update();
    public void setConfig(C config);
}
