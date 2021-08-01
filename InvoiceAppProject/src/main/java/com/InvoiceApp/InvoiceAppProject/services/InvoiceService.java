package com.InvoiceApp.InvoiceAppProject.services;

import com.InvoiceApp.InvoiceAppProject.data.entity.Invoice;
import com.InvoiceApp.InvoiceAppProject.data.entity.Invoices;
import com.InvoiceApp.InvoiceAppProject.data.repository.InvoiceRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    private List<String> buyers = new ArrayList<String>();

    public void readAndProcessDataFromCSV() {
        String fileName = "In Dir\\invoices.csv";

        List<String[]> r = null;
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            r = reader.readAll();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        int listIndex = 0;
        for (String[] arrays : r) {
            if (listIndex > 0) {
                Invoice obj = makeObject(arrays, listIndex);
                saveObjectToDatabase(obj);
            }
            listIndex = listIndex + 1;
        }

    }

    public void saveDataByBuyerstoCSV() throws FileNotFoundException {
        List<Invoice> buyersName = getInvoices();

        for(Invoice invoice : buyersName) {
            if(!buyers.contains(invoice.getBuyer())){
                buyers.add(invoice.getBuyer());
            }
        }

        for (String b : buyers) {
            String header = "buyer,image_name,invoice_image,invoice_due_date,invoice_number,invoice_amount,invoice_currency,invoice_status,supplier";
            String data = "";
            List<Invoice> listInvoices = getInvoicesByBuyer(b);

            DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDateTime = dateFormatter.format(new Date());

            PrintWriter writer = new PrintWriter("Out Dir\\CSVs\\InvoicesForBuyer_" + b + "_" + currentDateTime + ".csv");
            writer.println(header);

            for (Invoice invoice : listInvoices) {
                data = data + invoice.toStringSCV() + "\n";
            }

            writer.println(data);

            writer.close();
        }

    }

    public void saveDataByBuyerstoXML(){
        List<Invoice> buyersName = getInvoices();

        for(Invoice invoice : buyersName) {
            if(!buyers.contains(invoice.getBuyer())){
                buyers.add(invoice.getBuyer());
            }
        }
        for (String b : buyers) {
            try {
                DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String currentDateTime = dateFormatter.format(new Date());

                Invoices invoices = new Invoices();
                invoices.setProducts(new ArrayList<>());
                List<Invoice> listInvoices = getInvoicesByBuyer(b);

                for (Invoice invoice : listInvoices) {
                    invoices.getProducts().add(invoice);
                }

                File file = new File("Out Dir\\XMLs\\InvoicesForBuyer_" + b + "_" + currentDateTime + ".xml");

                JAXBContext context = JAXBContext.newInstance(Invoices.class);

                Marshaller marshaller = context.createMarshaller();

                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                marshaller.marshal(invoices, file);

                for (Invoice invoice : listInvoices) {
                    if (!invoice.getInvoiceImage().isEmpty()) {
                        downloadImageFromBase64(invoice.getInvoiceImage(), invoice.getImageName(), "Out Dir\\Images\\");
                    }
                }

            } catch (JAXBException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void downloadImageFromBase64(String base64, String fileName, String savePath) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateTime = dateFormatter.format(new Date());

        byte[] data = DatatypeConverter.parseBase64Binary(base64);
        String path = savePath + currentDateTime + "_" + fileName;
        File file = new File(path);
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }

    public List<Invoice> getInvoicesByBuyer(String buyer) {
        return invoiceRepository.findAllByBuyer(buyer);
    }

    public void saveObjectToDatabase(Invoice obj) {
        invoiceRepository.save(obj);
    }

    public Invoice makeObject(String[] aArray, Integer id) {
        Invoice invoice = new Invoice(id, aArray[0], aArray[1], aArray[2], aArray[3], aArray[4], aArray[5], aArray[6], aArray[7], aArray[8]);
        return invoice;
    }

    public boolean checkIfDirExists(String dirParh) {
        Path path = Paths.get(dirParh);
        return Files.isDirectory(path);
    }

    public boolean checkIfFileExists(String filePath) {
        Path path = Paths.get(filePath);
        return Files.isRegularFile(path);
    }

    public boolean makeDir(String dirPath) {
        File file = new File(dirPath);
        return file.mkdirs();
    }

    public boolean checkAndMakeOutDirs() {
        boolean flag = true;
        if (!checkIfDirExists("Out Dir")) {
            if (!makeDir("Out Dir")) {
                flag = false;
            }
        }

        if(!checkIfDirExists("Out Dir\\CSVs") && flag) {
            if (!makeDir("Out Dir\\CSVs")) {
                flag = false;
            }
        }

        if(!checkIfDirExists("Out Dir\\Images") && flag) {
            if (!makeDir("Out Dir\\Images")) {
                flag = false;
            }
        }

        if(!checkIfDirExists("Out Dir\\XMLs") && flag) {
            if (!makeDir("Out Dir\\XMLs")) {
                flag = false;
            }
        }

        return flag;
    }

    public boolean checkIfInDirAndFileExist() {
        boolean flag = true;
        if (!checkIfDirExists("In Dir")) {
            if (!makeDir("In Dir")) {
                flag = false;
            }
        }

        if (!checkIfFileExists("In Dir\\invoices.csv") && flag) {
            flag = false;
        }

        return flag;
    }

    public boolean checkIfThereAreRecordsInTheDatabase() {
        if (getInvoices().size() > 0) {
            return true;
        }
        else {
            return false;
        }
    }
}
