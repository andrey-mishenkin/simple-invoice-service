package com.invoiceservice.model.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfiguration {
    @Value("${log.invoice.processing.stat}")
    boolean logInvoiceProcessingStat;

    @Value("${log.all.http.tarffic}")
    boolean logAllHttpTraffic;

    public boolean isLogInvoiceProcessingStat() {
        return logInvoiceProcessingStat;
    }

    public void setLogInvoiceProcessingStat(boolean logInvoiceProcessingStat) {
        this.logInvoiceProcessingStat = logInvoiceProcessingStat;
    }

    public boolean isLogAllHttpTraffic() {
        return logAllHttpTraffic;
    }

    public void setLogAllHttpTraffic(boolean logAllHttpTraffic) {
        this.logAllHttpTraffic = logAllHttpTraffic;
    }
}
