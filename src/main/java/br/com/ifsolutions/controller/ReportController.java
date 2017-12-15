package br.com.ifsolutions.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import sun.security.action.OpenFileInputStreamAction;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReportController {

    public void reportGenerate(String fileName, Map fields){
        String sourceFile = fileName;
        String printFileName = null;
        ArrayList<String> value = new ArrayList<String>();
        value.add("Higor");

        try{
            //JasperFillManager.fillReportToFile(sourceFile, fields);
            JRBeanArrayDataSource collectionDataSource = new JRBeanArrayDataSource(new HashMap<>);
            JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFile, null, collectionDataSource);
            JasperViewer.viewReport( jasperPrint, false );
            //JasperPrintManager.printReport( jasperPrint, true);


        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}
