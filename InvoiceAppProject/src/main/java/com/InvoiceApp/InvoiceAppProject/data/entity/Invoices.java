package com.InvoiceApp.InvoiceAppProject.data.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "invoices")
@XmlAccessorType(XmlAccessType.FIELD)
public class Invoices {
    @XmlElement(name = "invoice")
    private List<Invoice> invoices = null;

    public List<Invoice> getProducts() {
        return invoices;
    }

    public void setProducts(List<Invoice> invoices) {
        this.invoices = invoices;
    }
}
