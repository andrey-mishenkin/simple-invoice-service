package com.invoiceservice.services.invoice;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.invoiceservice.model.invoice.Invoice;
import com.invoiceservice.model.invoice.InvoiceItem;
import com.invoiceservice.model.invoice.InvoiceResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@ContextConfiguration(value = "classpath:application-test-context.xml")
public class InvoiceTaxServiceTest {

    @Autowired 
    InvoiceTaxService invoiceTaxService;
    
    @Test
    public void testInvoiceTaxService() {
        
        Invoice invoice = new Invoice();
        invoice.setId(1);
        invoice.setTimeStamp(new Date());

        InvoiceResponse response = invoiceTaxService.taxInvoiceItems(invoice);
        assertTrue(response.getErrors().get(0).contains("'invoice.items' is missed or empty"));
        
        List<InvoiceItem> items = new LinkedList<InvoiceItem>();
        invoice.setItems(items);
        InvoiceItem item = new InvoiceItem();
        items.add(item);
        item.setId(11);
        
        response = invoiceTaxService.taxInvoiceItems(invoice);
        assertEquals(2, response.getErrors().size());
        assertTrue(response.isError());

        item.setTaxCategory("c10");
        response = invoiceTaxService.taxInvoiceItems(invoice);
        assertTrue(response.isError());
        assertTrue(response.getInvoice().getItems().size() == 1);
        assertTrue(response.getErrors().get(0).contains("'preTaxAmount' is missed"));

        item.setTaxCategory(null);
        item.setPreTaxAmount(new BigDecimal("100.00"));
        response = invoiceTaxService.taxInvoiceItems(invoice);
        assertTrue(response.isError());
        assertTrue(response.getErrors().get(0).contains("'taxCategory' is missed"));
        
        item.setTaxCategory("c99");
        response = invoiceTaxService.taxInvoiceItems(invoice);
        assertTrue(response.isError());
        assertTrue(response.getErrors().get(0).contains("unknown tax value for taxCategory='c99'"));
        
        item.setTaxCategory("c10");
        response = invoiceTaxService.taxInvoiceItems(invoice);
        assertFalse(response.isError());
        assertEquals(new BigDecimal("110.00"), response.getInvoice().getItems().get(0).getTotalAmount());
        
        /*
        try {
            ObjectMapperProvider.getDefaultObjectMapper().writeValue(new File("invoice.json"), invoice);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        */
    }
}
