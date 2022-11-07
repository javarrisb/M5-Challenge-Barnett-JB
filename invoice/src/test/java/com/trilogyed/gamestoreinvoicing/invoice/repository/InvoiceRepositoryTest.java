package com.trilogyed.gamestoreinvoicing.invoice.repository;

import com.trilogyed.gamestoreinvoicing.invoice.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest

public class InvoiceRepositoryTest {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    TaxRepository taxRepository;
    @Autowired
    ProcessingFeeRepository processingFeeRepository;

    @Before
    public void setUp() throws Exception {
        invoiceRepository.deleteAll();
        processingFeeRepository.deleteAll();

        ProcessingFee tShirtProcessingFee = new ProcessingFee();
        tShirtProcessingFee.setProductType("T-Shirts");
        tShirtProcessingFee.setFee(new BigDecimal("1.98"));

        ProcessingFee consoleProcessingFee = new ProcessingFee();
        consoleProcessingFee.setProductType("Consoles");
        consoleProcessingFee.setFee(new BigDecimal("14.99"));

        ProcessingFee gameProcessingFee = new ProcessingFee();
        gameProcessingFee.setProductType("Games");
        gameProcessingFee.setFee(new BigDecimal("1.49"));

        processingFeeRepository.save(tShirtProcessingFee);
        processingFeeRepository.save(consoleProcessingFee);
        processingFeeRepository.save(gameProcessingFee);
    }

    // Used this example of shouldAddFindDeleteInvoice in class on 11/03
    @Test
    public void shouldAddFindDeleteInvoice() {
        // Build an invoice
        Invoice invoice = new Invoice();
        invoice.setUnitPrice(new BigDecimal("5.01"));
        invoice.setCity("Houston");
        invoice.setState("WA");
        invoice.setProcessingFee(new BigDecimal("1000"));
        invoice.setItemType("Chocolate Bar");
        invoice.setSubtotal(new BigDecimal("44.41"));
        invoice.setQuantity(3);
        invoice.setTax(new BigDecimal("1.11"));
        invoice.setTotal(new BigDecimal("22.22"));
        invoice.setZipcode("44334");
        invoice.setName("adafasfasdfasdf");


        // save to database
        invoice = invoiceRepository.save(invoice);

        // get it back out of the database
        Invoice invoice2 = invoiceRepository.findById(invoice.getId()).get();

        // confirm that the thing I got back from the database is the thing I wrote the database
        assertEquals(invoice, invoice2);

        // delete it
        invoiceRepository.deleteById(invoice.getId());

        // go try to get it again
        Optional<Invoice> invoice3 = invoiceRepository.findById(invoice.getId());

        // confirm that it's gone
        assertEquals(false, invoice3.isPresent());
    }

    @Test
    public void shouldFindByName() {

        //Arrange


        //The double quotes forces the decimal point.
        //an alternative to set BigDecimal is using:


        Invoice invoice1 = new Invoice();
        invoice1.setName("Joe Black");
        invoice1.setStreet("123 Main St");
        invoice1.setCity("any City");
        invoice1.setState("NY");
        invoice1.setZipcode("10016");
        invoice1.setItemType("T-Shirts");
        invoice1.setQuantity(2);

        Optional<Tax> tax = taxRepository.findById(invoice1.getState());
        assertTrue(tax.isPresent());
        invoice1.setTax(invoice1.getSubtotal().multiply(tax.get().getRate()));

        Optional<ProcessingFee> processingFee = processingFeeRepository.findById(invoice1.getItemType());
        assertTrue(processingFee.isPresent());
        invoice1.setProcessingFee(processingFee.get().getFee());

        invoice1.setTotal(invoice1.getSubtotal().add(invoice1.getTax()).add(invoice1.getProcessingFee()));

        //Act
        invoice1 = invoiceRepository.save(invoice1);

        List<Invoice> foundNoinvoice = invoiceRepository.findByName("invalidValue");

        List<Invoice> foundOneinvoice = invoiceRepository.findByName(invoice1.getName());

        //Assert
        assertEquals(foundOneinvoice.size(),1);

        //Assert
        assertEquals(foundNoinvoice.size(),0);
    }
}
