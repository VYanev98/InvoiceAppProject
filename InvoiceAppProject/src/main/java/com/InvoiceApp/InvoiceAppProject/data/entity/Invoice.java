package com.InvoiceApp.InvoiceAppProject.data.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

import com.opencsv.bean.CsvBindByPosition;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "invoice")
@XmlRootElement(name = "invoice")
@XmlAccessorType (XmlAccessType.NONE)
public class Invoice {

    @Id
    private int id;

    @CsvBindByPosition(position = 0)
    @Column(length = 10000, name = "buyer")
    @XmlElement
    private String buyer;

    @CsvBindByPosition(position = 1)
    @Column(length = 10000)
    @XmlElement
    private String imageName;

    @CsvBindByPosition(position = 2)
    @Column(length = 10000)
    private String invoiceImage;

    @CsvBindByPosition(position = 3)
    @Column(length = 10000)
    @XmlElement
    private String invoiceDueDate;

    @CsvBindByPosition(position = 4)
    @Column(length = 10000)
    @XmlElement
    private String invoiceNumber;

    @CsvBindByPosition(position = 5)
    @Column(length = 10000)
    @XmlElement
    private String invoiceAmount;

    @CsvBindByPosition(position = 6)
    @Column(length = 10000)
    @XmlElement
    private String invoiceCurrency;

    @CsvBindByPosition(position = 7)
    @Column(length = 10000)
    private String invoiceStatus;

    @CsvBindByPosition(position = 8)
    @Column(length = 10000)
    @XmlElement
    private String supplier;

    public String toStringSCV() {
        StringBuilder dataBuilder = new StringBuilder();
        String data = "";
        appendFieldValue(dataBuilder, buyer);
        appendFieldValue(dataBuilder, imageName);
        appendFieldValue(dataBuilder, invoiceImage);
        appendFieldValue(dataBuilder, invoiceDueDate);
        appendFieldValue(dataBuilder, invoiceNumber);
        appendFieldValue(dataBuilder, invoiceAmount);
        appendFieldValue(dataBuilder, invoiceCurrency);
        appendFieldValue(dataBuilder, invoiceStatus);
        appendFieldValue(dataBuilder, supplier);

        data = dataBuilder.toString();
        data = data.substring(0, data.length() - 1);

        return data;
    }

    private void appendFieldValue(StringBuilder dataBuilder, String fieldValue) {
        if(fieldValue != null) {
            dataBuilder.append(fieldValue).append(",");
        } else {
            dataBuilder.append("").append(",");
        }
    }

}

