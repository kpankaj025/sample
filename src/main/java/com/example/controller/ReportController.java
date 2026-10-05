package com.example.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    
    @GetMapping
    public ResponseEntity<byte[]> generateEmployeeReport() {
        
        var tempEmp = new EmployeeReport(
            "John Doe", 
            "Engineering", 
            "p@ssword123", 
            75000.50, 
            "john.doe@company.com", 
            LocalDate.of(1990, 5, 15)
        );
        
        var list = List.of(tempEmp);
        JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(list);
        
        // 1. Correctly read the raw text .jrxml file layout from your resources folder
        try (var inputStream = new ClassPathResource("employee_report.jrxml").getInputStream()) {
        	
        	System.out.println(inputStream);
            
            // 2. CRITICAL STEP: Compile the JRXML stream dynamically into a JasperReport object
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            
            // 3. Pass the compiled 'jasperReport' object here instead of the raw inputStream
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, source);
            
            // 4. Flatten layout down to standard binary PDF array stream
            byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "employee_report.pdf");
            
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
            
        } catch (IOException | JRException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
