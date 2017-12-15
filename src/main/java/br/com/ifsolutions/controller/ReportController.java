package br.com.ifsolutions.controller;

import br.com.ifsolutions.dao.ProductDao;
import br.com.ifsolutions.entity.Produtos;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportController {

    public void reportGenerate(String fileName, HashMap parameters){

        try{
            JasperPrint print = JasperFillManager.fillReport(fileName, parameters, new JREmptyDataSource());
            JasperViewer.viewReport(print);
            JasperPrintManager.printReport(fileName,true);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}