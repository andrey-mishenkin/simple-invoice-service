package com.invoiceservice.services.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import com.invoiceservice.model.config.AppConfiguration;
import com.invoiceservice.model.invoice.Invoice;
import com.invoiceservice.model.invoice.InvoiceItem;
import com.invoiceservice.model.invoice.InvoiceResponse;
import com.invoiceservice.services.Service;
import com.invoiceservice.services.configuration.CategoryRatesConfigService;
import com.invoiceservice.services.monitor.SystemStatusMonitor;

/**
 * Service calculates tax for Invoice items
 */
// TODO: add statistic monitor and expose it via JMX or/and Web service
// TODO: as option we can add configuration: MAX simultaneously processed invoices, to avoid server overload
@org.springframework.stereotype.Service
public class InvoiceTaxService implements Service {
    
    private static final Logger LOG = LoggerFactory.getLogger(InvoiceTaxService.class);
    private static final Logger STAT_LOG = LoggerFactory.getLogger("invoiceTaxStatLogger");

    static final BigDecimal HUNDRED = new BigDecimal("100");
    
    SystemStatusMonitor systemStatusMonitor = new SystemStatusMonitor("invoice-tax-service");
    
    @Autowired
    CategoryRatesConfigService catRateService;
    
    @Autowired
    AppConfiguration appConfiguration;
    
    public InvoiceResponse taxInvoiceItems(Invoice invoiceIn) {
        LOG.debug("Start processing items for invoice.id=" + invoiceIn.getId());
        InvoiceResponse response = new InvoiceResponse();
        Invoice invoiceOut = new Invoice();
        response.setInvoice(invoiceOut);
        invoiceOut.setId(invoiceIn.getId());
        invoiceOut.setTimeStamp(invoiceIn.getTimeStamp());
        
        if (invoiceIn.getItems() == null || invoiceIn.getItems().isEmpty()) {
            response.getErrors().add("'invoice.items' is missed or empty");
            logStat(response);
            return response;
        }
        
        invoiceOut.setItems(new LinkedList<InvoiceItem>());
        for (InvoiceItem item : invoiceIn.getItems()) {
            InvoiceItem itemOut = new InvoiceItem(item);
            invoiceOut.getItems().add(itemOut);
            List<String> errors = new ArrayList<String>();
            if (item.getPreTaxAmount() == null) {
                errors.add("item.id = " + item.getId() + ": 'preTaxAmount' is missed");
            } 
            BigDecimal taxValue = null;
            if (item.getTaxCategory() == null) {
                errors.add("item.id = " + item.getId() + ": 'taxCategory' is missed");
            } else {
                taxValue = catRateService.getRateForCategory(item.getTaxCategory());
                if (taxValue == null) {
                    errors.add("item.id = " + item.getId() + ": unknown tax value for taxCategory='" + item.getTaxCategory() + "'");
                }
            }
            if (errors.isEmpty()) {
                // Calculate tax
                BigDecimal taxAmount = item.getPreTaxAmount().divide(HUNDRED).multiply(taxValue);
                BigDecimal totalAmount = item.getPreTaxAmount().add(taxAmount);
                itemOut.setTotalAmount(totalAmount);
            } else {
                response.getErrors().addAll(errors);
            }
        }
        // TODO: monitor number of errors and provide warning via systemStatusMonitor 
        logStat(response);
        LOG.debug("Finish processing items for invoice.id=" + invoiceIn.getId());
        return response;
    }
    
    private void logStat(InvoiceResponse response) {
        if (appConfiguration.isLogInvoiceProcessingStat()) {
            int itemsProcessed = 0;
            if (response.getInvoice().getItems() != null) {
                itemsProcessed = response.getInvoice().getItems().size();
            }
            STAT_LOG.info("Invoice.id=" + response.getInvoice().getId() + ": Items processed " + itemsProcessed + ", Errors " + response.getErrors().size());
        }
    }

    @Override
    @Bean(name = "invoiceServiceSystemStatusMonitor")
    public SystemStatusMonitor getSystemStatusMonitor() {
        return systemStatusMonitor;
    }

    @Autowired
    @Qualifier("invoiceServiceSystemStatusMonitor")
    public void setSystemStatusMonitor(SystemStatusMonitor systemStatusMonitor) {
        this.systemStatusMonitor = systemStatusMonitor;
    }
}
