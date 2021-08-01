package com.InvoiceApp.InvoiceAppProject.services;

import com.InvoiceApp.InvoiceAppProject.data.entity.Invoice;
import com.InvoiceApp.InvoiceAppProject.data.repository.InvoiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    @MockBean
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Test
    void getInvoicesTest() {
        Invoice testInvoice1 = new Invoice();
        testInvoice1.setId(1);
        testInvoice1.setBuyer("Buyer 1");

        Invoice testInvoice2 = new Invoice();
        testInvoice2.setId(2);
        testInvoice2.setBuyer("Buyer 2");

        Invoice testInvoice3 = new Invoice();
        testInvoice3.setId(3);
        testInvoice3.setBuyer("Buyer 3");

        List<Invoice> invoiceList = new ArrayList<>();

        Mockito.when(invoiceRepository.findAll())
                .thenReturn(invoiceList);

        List<Invoice> invoiceList1 = invoiceService.getInvoices();

        assertThat(invoiceList.size()).isEqualTo(invoiceList1.size());
    }

    @Test
    void getInvoicesByBuyerTest() {
        String buyer = "Test buyer";

        Invoice testInvoice1 = new Invoice();
        testInvoice1.setId(1);
        testInvoice1.setBuyer(buyer);

        List<Invoice> invoiceList = new ArrayList<>();

        Mockito.when(invoiceRepository.findAllByBuyer(buyer))
                .thenReturn(invoiceList);

        List<Invoice> invoiceList1 = invoiceService.getInvoicesByBuyer(buyer);

        assertThat(invoiceList.size()).isEqualTo(invoiceList1.size());
    }

}