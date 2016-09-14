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
/**
 *
 * @author raphael.da.silva
 */
public class ReportPlan {
    public static void geraRelatorio(List<TesteCaseTSBean> listTC, String dir, Map<String,Object> parametros) throws JRException{
        System.out.println("Gerando relatório...");
        
       
        InputStream fonte = ReportPlan.class.getResourceAsStream("/report/reportCT.jrxml");
        // compilacao do JRXML 
        JasperReport report = JasperCompileManager.compileReport(fonte);
        
        JasperPrint print = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(listTC));
        
        // exportacao do relatorio para outro formato, no caso PDF 
        JasperExportManager.exportReportToPdfFile(print, dir+".pdf");
        System.out.println("Relatório gerado.");
    }
}
