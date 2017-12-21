package br.com.ifsolutions.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.view.JasperViewer;
import java.util.HashMap;

public class ReportController {

    public void reportGenerate(String fileName, HashMap parameters){
        try{
            JasperPrint print = JasperFillManager.fillReport(fileName, parameters, new JREmptyDataSource());
            print.setOrientation(OrientationEnum.LANDSCAPE);
            //JasperViewer.viewReport(print,false);
            JasperPrintManager.printReport(print,true);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}