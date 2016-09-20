package com.invoiceservice.model.invoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Invoice {
    private int id;
    private Date timeStamp;
    private List<InvoiceItem> items;
    
    public Invoice() {};
    
    /**
     * Copy constructor
     */
    public Invoice(Invoice copyFrom) {
        this.id = copyFrom.id;
        this.timeStamp = copyFrom.timeStamp;
        this.items = new ArrayList<InvoiceItem>();
        for (InvoiceItem invoiceItem : copyFrom.getItems()) {
            items.add(new InvoiceItem(invoiceItem)); 
        }
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public List<InvoiceItem> getItems() {
        return items;
    }
    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
