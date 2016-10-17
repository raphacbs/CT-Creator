/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.reports;


import com.accenture.bean.TesteCaseTSBean;
import com.accenture.util.FunctiosDates;
import java.io.InputStream;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException; 
import net.sf.jasperreports.engine.JasperCompileManager; 
import net.sf.jasperreports.engine.JasperExportManager; 
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint; 
import net.sf.jasperreports.engine.JasperReport; 
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author raphael.da.silva
 */
public class BuildReport {
    public static void geraRelatorio(List<?> listObject, Map<String,Object> parametros, String fileReport) throws JRException{
        System.out.println("Gerando relatório...");
        
       
        InputStream fonte = BuildReport.class.getResourceAsStream("/report/"+fileReport);
        // compilacao do JRXML 
        JasperReport report = JasperCompileManager.compileReport(fonte);
        
        JasperPrint print = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(listObject));
        JasperViewer viewer = new JasperViewer( print , true );
        viewer.setTitle("Relatório CT Creator");        
        viewer.setVisible(true);
        // exportacao do relatorio para outro formato, no caso PDF 
//        JasperExportManager.exportReportToPdfFile(print, dir+".pdf");
        System.out.println("Relatório gerado.");
    }
}
