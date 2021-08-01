package com.InvoiceApp.InvoiceAppProject.data.repository;

import com.InvoiceApp.InvoiceAppProject.data.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByBuyer(String buyer);
}
