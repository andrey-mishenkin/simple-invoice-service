package com.invoiceservice.model.invoice;

import java.math.BigDecimal;

public class InvoiceItem {
    private int id;
    private String description;
    private BigDecimal preTaxAmount;
    private BigDecimal totalAmount;
    private String taxCategory;
    
    public InvoiceItem() {}; 
    /**
     * Copy constructor
     */
    public InvoiceItem(InvoiceItem copyFrom) {
        this.id = copyFrom.getId();
        this.description = copyFrom.getDescription();
        this.preTaxAmount = copyFrom.getPreTaxAmount();
        this.totalAmount = copyFrom.getTotalAmount();
        this.taxCategory = copyFrom.getTaxCategory();
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public BigDecimal getPreTaxAmount() {
        return preTaxAmount;
    }
    public void setPreTaxAmount(BigDecimal preTaxAmount) {
        this.preTaxAmount = preTaxAmount;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    public String getTaxCategory() {
        return taxCategory;
    }
    public void setTaxCategory(String taxCategory) {
        this.taxCategory = taxCategory;
    }
}
