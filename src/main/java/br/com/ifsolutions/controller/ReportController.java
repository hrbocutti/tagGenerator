package br.com.ifsolutions.controller;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

import java.util.HashMap;
import java.util.Map;

public class ReportController {

    public void reportGenerate(String fileName, Map fields){
        String sourceFile = fileName;
        String printFileName = null;

        try{
            JasperFillManager.fillReportToFile(sourceFile, fields);
            if(printFileName != null){
                JasperPrintManager.printReport( printFileName, true);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}
