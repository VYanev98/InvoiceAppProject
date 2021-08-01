package com.InvoiceApp.InvoiceAppProject.controller;

import com.InvoiceApp.InvoiceAppProject.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileNotFoundException;

@Controller
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping(value="/")
    public String getInvoices(Model model) {
        if(invoiceService.checkIfInDirAndFileExist()) {
            invoiceService.readAndProcessDataFromCSV();
        }
        model.addAttribute("checkIfInDirAndFileExist", invoiceService.checkIfInDirAndFileExist());
        model.addAttribute("invoiceList", invoiceService.getInvoices());
        return "index";
    }

    @GetMapping(value = "/exportCSV")
    public void exportToCSVByBuyers(Model model) throws FileNotFoundException {
        if(invoiceService.checkAndMakeOutDirs()) {
            invoiceService.saveDataByBuyerstoCSV();
        }
        model.addAttribute("checkAndMakeOutDirs", invoiceService.checkAndMakeOutDirs());
        model.addAttribute("checkIfThereAreRecordsInTheDatabase", invoiceService.checkIfThereAreRecordsInTheDatabase());
    }

    @GetMapping(value = "/exportXML")
    public void exportToXMLByBuyers(Model model) {
        if(invoiceService.checkAndMakeOutDirs()) {
            invoiceService.saveDataByBuyerstoXML();
        }
        model.addAttribute("checkAndMakeOutDirs", invoiceService.checkAndMakeOutDirs());
        model.addAttribute("checkIfThereAreRecordsInTheDatabase", invoiceService.checkIfThereAreRecordsInTheDatabase());
    }

}
