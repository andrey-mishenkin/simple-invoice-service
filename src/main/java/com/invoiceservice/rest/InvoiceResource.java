package com.invoiceservice.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.invoiceservice.model.invoice.Invoice;
import com.invoiceservice.model.invoice.InvoiceResponse;
import com.invoiceservice.services.invoice.InvoiceTaxService;

/**
 * Invoice resource - provides main tax calc functionality
 */
@Component
@Path("invoice")
public class InvoiceResource {

    @Autowired
    InvoiceTaxService invoiceTaxService;
    
    /**
     * Performs tax amount calculation for Invoice items
     * @return
     */
    @POST
    @Path("/taxitems")
    @Consumes(MediaType.APPLICATION_JSON) 
    @Produces(MediaType.APPLICATION_JSON)
    public InvoiceResponse taxItems(Invoice invoice) {
        return invoiceTaxService.taxInvoiceItems(invoice);
    }

    public InvoiceTaxService getInvoiceTaxService() {
        return invoiceTaxService;
    }

    public void setInvoiceTaxService(InvoiceTaxService invoiceTaxService) {
        this.invoiceTaxService = invoiceTaxService;
    }
}
