package com.InvoiceApp.InvoiceAppProject.data.repository;

import com.InvoiceApp.InvoiceAppProject.data.entity.Invoice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class InvoiceRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Test
    void findAllByBuyerTest() {
        String buyer = "Test buyer";

        Invoice testInvoice1 = new Invoice();
        testInvoice1.setId(1);
        testInvoice1.setBuyer(buyer);
        testEntityManager.persistAndFlush(testInvoice1);

        Invoice testInvoice2 = new Invoice();
        testInvoice2.setId(2);
        testInvoice2.setBuyer(buyer);
        testEntityManager.persistAndFlush(testInvoice2);

        Invoice testInvoice3 = new Invoice();
        testInvoice3.setId(3);
        testInvoice3.setBuyer("Test buyer 1");
        testEntityManager.persistAndFlush(testInvoice3);

        assertThat(invoiceRepository.findAllByBuyer(buyer).size()).isEqualTo(2);
    }
}