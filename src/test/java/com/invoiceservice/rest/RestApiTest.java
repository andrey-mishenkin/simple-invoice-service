package com.invoiceservice.rest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.invoiceservice.model.config.CategotryRateConfig;
import com.invoiceservice.model.invoice.Invoice;
import com.invoiceservice.model.invoice.InvoiceItem;
import com.invoiceservice.model.invoice.InvoiceResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestApiTest extends RestApiTestBase {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void taxItems() {
        Invoice invoice = new Invoice();
        invoice.setId(1);
        invoice.setTimeStamp(new Date());
        List<InvoiceItem> items = new LinkedList<InvoiceItem>();
        invoice.setItems(items);
        InvoiceItem item = new InvoiceItem();
        items.add(item);
        item.setId(11);
        item.setTaxCategory("c10");
        item.setPreTaxAmount(new BigDecimal("100.00"));

        ResponseEntity<InvoiceResponse> entity = this.restTemplate.postForEntity(BASE_CONTEXT_PATH + "/v1/invoice/taxitems", invoice, InvoiceResponse.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(new BigDecimal("110.00"), entity.getBody().getInvoice().getItems().get(0).getTotalAmount());
    }

    @Test
    public void taxItemsBadJson() {
        // TODO: add test
        
    }
    
    @Test
    public void categoryRatesConfigTest() {
        ResponseEntity<CategotryRateConfig> entity = this.restTemplate.getForEntity(BASE_CONTEXT_PATH + "/v1/category/taxrates", CategotryRateConfig.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody().getCategoryRateMap().size() == 3);
        assertEquals(new BigDecimal("10"), entity.getBody().getCategoryRateMap().get("c10"));
    }
}