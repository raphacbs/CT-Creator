/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.control;

import com.accenture.bean.TesteCaseTSBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author raphael.da.silva
 */
public class GerenciaPlanilhaTS {

    private final String nameUser = System.getProperty("user.home");

    public GerenciaPlanilhaTS() {
    }

    public void geraNovaPlanilhaTS(String dir, List<TesteCaseTSBean> listTS) throws FileNotFoundException, IOException {

        FileInputStream arquivo = new FileInputStream(new File(dir));
        HSSFWorkbook workbook = new HSSFWorkbook(arquivo);
        HSSFSheet sheetTS = workbook.getSheetAt(0);

        HSSFDataFormat format = workbook.createDataFormat();
        HSSFCellStyle estilo = workbook.createCellStyle();
        String formatData = "aaaa-mm-dd\"T12:00:00-03:00\"";

        int linha = 1;

        Row row = sheetTS.getRow(linha);

        Cell descriptionPlan = row.getCell(0);
        Cell prj = row.getCell(1);
        Cell fase = row.getCell(2);
        Cell testPhase = row.getCell(3);
        Cell testScriptName = row.getCell(4);
        Cell testScriptDescription = row.getCell(5);
        Cell stepNo = row.getCell(6);
        Cell stepDescription = row.getCell(7);
        Cell expectedResults = row.getCell(8);
        Cell product = row.getCell(9);
        Cell dataPlanejada = row.getCell(10);

        for (int i = 0; i < listTS.size(); i++) {
           
            
            estilo.setDataFormat(format.getFormat(formatData));
            estilo.setFillBackgroundColor(HSSFColor.GREEN.index);
            
	    
            
            row = sheetTS.getRow(linha);

            descriptionPlan = row.getCell(0);
            prj = row.getCell(1);
            fase = row.getCell(2);
            testPhase = row.getCell(3);
            testScriptName = row.getCell(4);
            testScriptDescription = row.getCell(5);
            stepNo = row.getCell(6);
            stepDescription = row.getCell(7);
            expectedResults = row.getCell(8);
            product = row.getCell(9);
            dataPlanejada = row.getCell(10);
            
            descriptionPlan.setCellValue(listTS.get(i).getTestPlan());
            prj.setCellValue(listTS.get(i).getSTIPRJ());
            fase.setCellValue(listTS.get(i).getFASE());
            testPhase.setCellValue(listTS.get(i).getTestPhase());
            testScriptName.setCellValue(listTS.get(i).getTestScriptName());
            testScriptDescription.setCellValue(listTS.get(i).getTestScriptDescription());
            stepNo.setCellValue(listTS.get(i).getSTEP_NUMERO());
            stepDescription.setCellValue(listTS.get(i).getStepDescription());
            expectedResults.setCellValue(listTS.get(i).getExpectedResults());
            product.setCellValue(listTS.get(i).getProduct());
            dataPlanejada.setCellValue(listTS.get(i).getDataPlanejada());
            
            dataPlanejada.setCellStyle(estilo);
	    
            
            linha = linha + 2;
            
            
            
        }
        
        FileOutputStream fileOut = new FileOutputStream(new File(dir));
        workbook.write(fileOut);
        fileOut.close();
        arquivo.close();

    }

}
