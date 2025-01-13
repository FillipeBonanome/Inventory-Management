package com.inventory.Inventory.Management.service;

import com.inventory.Inventory.Management.domain.Product;
import com.inventory.Inventory.Management.domain.StockTransaction;
import com.inventory.Inventory.Management.infra.exceptions.StockTransactionException;
import com.inventory.Inventory.Management.repository.ProductRepository;
import com.inventory.Inventory.Management.repository.StockTransactionRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class SpreadsheetReportService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    public void generateSpreadsheetProductReport(Long id) {
        List<Product> productList = productRepository.findAllByUserId(id);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("NAME");
        header.createCell(2).setCellValue("QUANTITY");
        header.createCell(3).setCellValue("PRICE");

        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 6000);

        int rowStart = 1;

        for(Product product : productList) {
            Row row = sheet.createRow(rowStart);
            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getName());
            row.createCell(2).setCellValue(product.getQuantity());
            row.createCell(3).setCellValue(product.getPrice());
            rowStart++;
        }

        try (FileOutputStream fileOut = new FileOutputStream("example.xlsx")) {
            workbook.write(fileOut);
            workbook.close();
        } catch (IOException e) {
            throw new StockTransactionException("Error saving spreadsheet");
        }

    }

    public void generateSpreadsheetTransactionReport(Long id) {
        List<StockTransaction> stockTransactionList = stockTransactionRepository.findAllByUserId(id);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("NAME");
        header.createCell(2).setCellValue("QUANTITY");
        header.createCell(3).setCellValue("VALUE");
        header.createCell(4).setCellValue("TYPE");

        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 6000);

        int rowStart = 1;

        for(StockTransaction transaction : stockTransactionList) {
            Row row = sheet.createRow(rowStart);
            row.createCell(0).setCellValue(transaction.getId());
            row.createCell(1).setCellValue(transaction.getProduct().getName());
            row.createCell(2).setCellValue(transaction.getQuantity());
            row.createCell(3).setCellValue(transaction.getTransactionValue());
            row.createCell(4).setCellValue(transaction.getTransactionType().toString());
            rowStart++;
        }

        String fileName = "transactions ";
        LocalDate date = LocalDate.now();
        String dateFormat = date.toString();
        try (FileOutputStream fileOut = new FileOutputStream(fileName + dateFormat + ".xlsx")) {
            workbook.write(fileOut);
            workbook.close();
        } catch (IOException e) {
            throw new StockTransactionException("Error saving spreadsheet");
        }
    }
}
