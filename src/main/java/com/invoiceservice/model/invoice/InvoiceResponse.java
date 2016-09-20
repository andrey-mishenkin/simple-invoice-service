package com.invoiceservice.model.invoice;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Invoice response 
 * 
 */
public class InvoiceResponse {
    private Invoice invoice;
    private List<String> errors =  new ArrayList<String>();
    
    public Invoice getInvoice() {
        return invoice;
    }
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
    public List<String> getErrors() {
        return errors;
    }
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    public boolean isError() {
        if (errors == null || errors.isEmpty()) {
           return false;
        }
        return true;
    }
}
