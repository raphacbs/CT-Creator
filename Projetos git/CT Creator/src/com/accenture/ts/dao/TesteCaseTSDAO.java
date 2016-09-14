/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.dao;

import com.accenture.bean.Plano;
import com.accenture.bean.TestPlanTSBean;
import com.accenture.bean.TesteCaseTSBean;
import com.accenture.control.ExtraiPlanilha;
import com.accenture.util.FunctiosDates;

//import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author raphael.da.silva
 */
public class TesteCaseTSDAO {

    private TesteCaseTSBean testCase;
    private final String sheetDefault = "C:\\FastPlan\\sheets\\TS.xlsx";
    
    private  File sourceStheet;
    private  File destinationSheet;
    private org.apache.log4j.Logger logger;

    public TesteCaseTSDAO(){
        sourceStheet = new File(sheetDefault);
        testCase = new TesteCaseTSBean();
        logger = org.apache.log4j.Logger.getLogger(TesteCaseTSDAO.class);
    }
    
    
    /*
     * O método copySheet, Recebe dois arquivos como parâmetro e fazer uma cópia do arquivo de origem para o arquivo de destino
     * 
     */
    private void copySheet(File source, File destination) throws FileNotFoundException, IOException {
        InputStream in = new FileInputStream(source);
        OutputStream out = new FileOutputStream(destination);           // Transferindo bytes de entrada para saída
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
    
    /*
     * O método newTsSheet, faz uma cópia da planilha de TS preenchendo com o dados do objeto 
     * 
     */
    public boolean newTsSheet(String pathSheet, String nameSheet, List<TesteCaseTSBean> listTestCase) throws FileNotFoundException, IOException {
        boolean sucess = false;
        destinationSheet = new File(pathSheet);
        destinationSheet.mkdirs();
        destinationSheet = new File(pathSheet+ "\\" + nameSheet);
        sourceStheet = new File(sheetDefault);
        copySheet(sourceStheet, destinationSheet);
        FileInputStream fileSheet = new FileInputStream(destinationSheet);
        

        XSSFWorkbook workbook = new XSSFWorkbook(fileSheet);
        XSSFSheet sheetTS = workbook.getSheetAt(0);

        XSSFDataFormat format = workbook.createDataFormat();
        XSSFCellStyle estilo = workbook.createCellStyle();
//        String formatData = "aaaa-mm-dd\"T12:00:00-03:00\"";

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

        if (listTestCase.size() > 0) {
            descriptionPlan.setCellValue(listTestCase.get(0).getTestPlan());
            prj.setCellValue(listTestCase.get(0).getSTIPRJ());
            fase.setCellValue(listTestCase.get(0).getFASE());
            testPhase.setCellValue(listTestCase.get(0).getTestPhase());
        }

        for (int i = 0; i < listTestCase.size(); i++) {

//            estilo.setDataFormat(format.getFormat(formatData));
//            estilo.setFillBackgroundColor(HSSFColor.GREEN.index);

            row = sheetTS.getRow(linha);

            
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
            
            
            
            testScriptName.setCellValue(listTestCase.get(i).getTestScriptName());
            testScriptDescription.setCellValue(listTestCase.get(i).getTestScriptDescription());
            stepNo.setCellValue(listTestCase.get(i).getSTEP_NUMERO());
            stepDescription.setCellValue(listTestCase.get(i).getStepDescription());
            expectedResults.setCellValue(listTestCase.get(i).getExpectedResults());
            product.setCellValue(listTestCase.get(i).getProduct());
            estilo = (XSSFCellStyle) dataPlanejada.getCellStyle();
            dataPlanejada.setCellValue(listTestCase.get(i).getDataPlanejada());
            dataPlanejada.setCellStyle(estilo);

            linha = linha + 2;

        }
        
        
        

        FileOutputStream fileOut = new FileOutputStream(destinationSheet);
        workbook.write(fileOut);
        fileOut.close();
        fileSheet.close();
        sucess = true;

        return sucess;
    }
    
    //salva TS
    public boolean newTsSheet(String pathSheet, String nameSheet, TesteCaseTSBean testCase) throws FileNotFoundException, IOException {
        boolean sucess = false;
        destinationSheet = new File(pathSheet);
        destinationSheet.mkdirs();
        destinationSheet = new File(pathSheet+ "\\" + nameSheet+".xlsx");
        sourceStheet = new File(sheetDefault);
        copySheet(sourceStheet, destinationSheet);
        FileInputStream fileSheet = new FileInputStream(destinationSheet);
        

        XSSFWorkbook workbook = new XSSFWorkbook(fileSheet);
        XSSFSheet sheetTS = workbook.getSheetAt(0);

        XSSFDataFormat format = workbook.createDataFormat();
        XSSFCellStyle estilo = workbook.createCellStyle();
//        String formatData = "aaaa-mm-dd\"T12:00:00-03:00\"";

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
        Cell complexidade = row.getCell(11);
        Cell automatizado = row.getCell(12);
       
            descriptionPlan.setCellValue(testCase.getTestPlan());
            prj.setCellValue(testCase.getSTIPRJ());
            fase.setCellValue(testCase.getFASE());
            testPhase.setCellValue(testCase.getTestPhase());
       

        

//            estilo.setDataFormat(format.getFormat(formatData));
//            estilo.setFillBackgroundColor(HSSFColor.GREEN.index);

            row = sheetTS.getRow(linha);

            
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
            complexidade = row.getCell(11);
            automatizado = row.getCell(12);
            
            
            testScriptName.setCellValue(testCase.getTestScriptName());
            testScriptDescription.setCellValue(testCase.getTestScriptDescription());
            stepNo.setCellValue(testCase.getSTEP_NUMERO());
            stepDescription.setCellValue(testCase.getStepDescription());
            expectedResults.setCellValue(testCase.getExpectedResults());
            product.setCellValue(testCase.getProduct());
            estilo = (XSSFCellStyle) dataPlanejada.getCellStyle();
            dataPlanejada.setCellValue(FunctiosDates.getDateActual());
            dataPlanejada.setCellStyle(estilo);
            complexidade.setCellValue(testCase.getComplexidade());
            automatizado.setCellValue(testCase.isAutomatizado());
            linha = linha + 2;

        
        
        
        

        FileOutputStream fileOut = new FileOutputStream(destinationSheet);
        workbook.write(fileOut);
        fileOut.close();
        fileSheet.close();
        sucess = true;

        return sucess;
    }
        
    public boolean createSpreadsheetTS(String pathSheet, String nameSheet, TestPlanTSBean testPlan) throws Exception  {
       
        boolean sucess = false;
        destinationSheet = new File(pathSheet);
        destinationSheet.mkdirs();    
        String sheetTI = pathSheet + "\\" + nameSheet;
        nameSheet = nameSheet.replace("xlsx", "xlsm");;
        destinationSheet = new File(pathSheet + "\\" + nameSheet);
        sourceStheet = new File("C:\\FastPlan\\sheets\\TS_NEW.xlsm");
        logger.info("Realizando cópia da planilha");
        boolean existInList = false;
        
        List<TesteCaseTSBean> testCasesAutomatizados = new ArrayList<TesteCaseTSBean>();
        
        //run macro
        String cmd = "C:\\FastPlan\\runMacro.vbs";
        Runtime.getRuntime().exec("cmd /c" +cmd);
        
        Thread.sleep(2000);
        
        copySheet(sourceStheet, destinationSheet);
        logger.info("Planilha copiada");
        FileInputStream fileSheet = new FileInputStream(destinationSheet);
        
        XSSFWorkbook workbook = new XSSFWorkbook(fileSheet);

        XSSFSheet sheetTS = workbook.getSheetAt(0);
        XSSFCellStyle estilo = workbook.createCellStyle();

//        workbook.setSheetName(workbook.getSheetIndex(sheetTS), FunctiosDates.dateToString(FunctiosDates.getDateActual(), "yyyy-MM-dd-HH-mm-ss"));
        int linha = 2;

        Row row = sheetTS.getRow(linha);
        
        Cell descriptionPlan = row.getCell(0);
        Cell release = row.getCell(1);
        Cell prj = row.getCell(2);
        Cell fase = row.getCell(3);
        Cell testPhase = row.getCell(4);
        Cell testScriptName = row.getCell(5);
        Cell testScriptDescription = row.getCell(6);
        Cell stepNo = row.getCell(7);
        Cell stepDescription = row.getCell(8);
        Cell expectedResults = row.getCell(9);
        Cell product = row.getCell(10);
        Cell dataPlanejada = row.getCell(11);
        Cell qtdSteps = row.getCell(12);
        Cell complexidade = row.getCell(13);
        Cell automatizado = row.getCell(14);
        
        logger.info("Inserindo dados do plano");
        descriptionPlan.setCellValue(testPlan.getName());
        release.setCellValue(testPlan.getRelease());

        for (int i = 0; i < testPlan.getTestCase().size(); i++) {
            row = sheetTS.getRow(linha);

            descriptionPlan = row.getCell(0);
            release = row.getCell(1);
            prj = row.getCell(2);
            fase = row.getCell(3);
            testPhase = row.getCell(4);
            testScriptName = row.getCell(5);
            testScriptDescription = row.getCell(6);
            stepNo = row.getCell(7);
            stepDescription = row.getCell(8);
            expectedResults = row.getCell(9);
            product = row.getCell(10);
            dataPlanejada = row.getCell(11);
            qtdSteps = row.getCell(12);
            complexidade = row.getCell(13);
            automatizado = row.getCell(14);

            logger.info("Inserindo dados dos TCs");
            System.out.println("com.accenture.ts.dao.TesteCaseTSDAO.createSpreadsheetTS() - "+testPlan.getSti() + " - " + "row:"+linha );
            prj.setCellValue(testPlan.getSti());
            fase.setCellValue(testPlan.getCrFase());
            testPhase.setCellValue(testPlan.getTestPhase());
            testScriptName.setCellValue(testPlan.getTestCase().get(i).getTestScriptName());
            testScriptDescription.setCellValue(testPlan.getTestCase().get(i).getTestScriptDescription());
            product.setCellValue(testPlan.getTestCase().get(i).getProduct());
            estilo = (XSSFCellStyle) dataPlanejada.getCellStyle();
            dataPlanejada.setCellValue(testPlan.getTestCase().get(i).getDataPlanejada());
            dataPlanejada.setCellStyle(estilo);
            qtdSteps.setCellValue(testPlan.getTestCase().get(i).getListStep().size());
            complexidade.setCellValue(testPlan.getTestCase().get(i).getComplexidade());
           

            //set colors 
            if (i % 2 == 0) {

                System.out.println("com.accenture.ts.dao.TesteCaseTSDAO.createSpreadsheetTS() - entrou");
//                setColorCells(new Cell[]{descriptionPlan, release, prj, fase, testPhase, testScriptName, testScriptDescription, stepNo,
//                    stepDescription, expectedResults, product, dataPlanejada, qtdSteps, complexidade}, workbook);
                
               XSSFCellStyle styleColor = (XSSFCellStyle) product.getCellStyle();
               styleColor.setFillBackgroundColor(HSSFColor.LIGHT_GREEN.index);
               product.setCellStyle(styleColor);

            }

            for (int j = 0; j < testPlan.getTestCase().get(i).getListStep().size(); j++) {

                row = sheetTS.getRow(linha);
                stepNo = row.getCell(7);
                stepDescription = row.getCell(8);
                expectedResults = row.getCell(9);
//                
//                stepNo.setCellValue(testPlan.getTestCase().get(i).getListStep().get(j).getNomeStep());
                logger.info("Inserindo dados dos Steps");
                stepNo.setCellValue(j + 1);
                stepDescription.setCellValue(testPlan.getTestCase().get(i).getListStep().get(j).getDescStep());
                expectedResults.setCellValue(testPlan.getTestCase().get(i).getListStep().get(j).getResultadoStep());

                linha = linha + 1;

                row = sheetTS.getRow(linha);

                stepNo = row.getCell(7);
                stepDescription = row.getCell(8);
                expectedResults = row.getCell(9);

            }

            linha = linha + 1;            
            row = sheetTS.getRow(linha);

            descriptionPlan = row.getCell(0);
            release = row.getCell(1);
            prj = row.getCell(2);
            fase = row.getCell(3);
            testPhase = row.getCell(4);
            testScriptName = row.getCell(5);
            testScriptDescription = row.getCell(6);
            stepNo = row.getCell(7);
            stepDescription = row.getCell(8);
            expectedResults = row.getCell(9);
            product = row.getCell(10);
            dataPlanejada = row.getCell(11);
            qtdSteps = row.getCell(12);
            complexidade = row.getCell(13);
            automatizado = row.getCell(14);
            logger.info("Dados inseridos na planilha");
            
             if(testPlan.getTestCase().get(i).isAutomatizado()){
                
                 
                 for (int j=0; j<testCasesAutomatizados.size();j++) {
                     if(testPlan.getTestCase().get(i).equals(testCasesAutomatizados.get(j).getTestScriptName())){
                        existInList = true;
                     }         
                }
                if(!existInList){ 
                    testCasesAutomatizados.add(testPlan.getTestCase().get(i));
                    existInList=true;
                }
            }
            
        }
        
        
        ExtraiPlanilha extraiPlanilha = new ExtraiPlanilha();
        extraiPlanilha.exportTStoTI(testCasesAutomatizados, sheetTI);
       
       
        
        logger.info("Preparando para salvar planilha");
        FileOutputStream fileOut = new FileOutputStream(destinationSheet);
        logger.info("Fim método - new FileOutputStream(destinationSheet) ");
        logger.info("Tentando gravar na planilha.");
        workbook.write(fileOut);
        logger.info("Fim método - workbook.write(fileOut)");
        fileOut.close();
        fileSheet.close();
        sucess = true;
        logger.info("Planilha gerada.");
        return sucess;
       
        
        
    }
    
    public void setColorCells(Cell [] cells, XSSFWorkbook workbook){
        XSSFCellStyle styleColor = workbook.createCellStyle();
        styleColor.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
        
            for(int i = 0; i < cells.length; i++){
                styleColor = (XSSFCellStyle) cells[i].getCellStyle();
                styleColor.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
                cells[i].setCellStyle(styleColor);
            }   
                
    }
    
    
    
    
    public boolean newTsSheet(String pathSheet, String nameSheet, TestPlanTSBean testPlan) throws Exception {
        boolean sucess = false;
        destinationSheet = new File(pathSheet);
        destinationSheet.mkdirs();
        destinationSheet = new File(pathSheet+ "\\" + nameSheet);
        sourceStheet = new File(sheetDefault);
        copySheet(sourceStheet, destinationSheet);
        FileInputStream fileSheet = new FileInputStream(destinationSheet);
        
        
        

        XSSFWorkbook workbook = new XSSFWorkbook(fileSheet);
        XSSFSheet sheetTS = workbook.getSheetAt(0);

        XSSFDataFormat format = workbook.createDataFormat();
        XSSFCellStyle estilo = workbook.createCellStyle();
//        String formatData = "aaaa-mm-dd\"T12:00:00-03:00\"";

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
        Cell complexidade = row.getCell(11);

       
            descriptionPlan.setCellValue(testPlan.getName());
            prj.setCellValue(testPlan.getSti());
            fase.setCellValue(testPlan.getCrFase());
            testPhase.setCellValue(testPlan.getTestPhase());
       

        

//            estilo.setDataFormat(format.getFormat(formatData));
//            estilo.setFillBackgroundColor(HSSFColor.GREEN.index);
            for(int i = 0 ; i < testPlan.getTestCase().size(); i++ ){
            row = sheetTS.getRow(linha);

            
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
            complexidade = row.getCell(11);
            
            
            
            testScriptName.setCellValue(testPlan.getTestCase().get(i).getTestScriptName());
            testScriptDescription.setCellValue(testPlan.getTestCase().get(i).getTestScriptDescription());
            stepNo.setCellValue(testPlan.getTestCase().get(i).getSTEP_NUMERO());
            stepDescription.setCellValue(testPlan.getTestCase().get(i).getStepDescription());
            expectedResults.setCellValue(testPlan.getTestCase().get(i).getExpectedResults());
            product.setCellValue(testPlan.getTestCase().get(i).getProduct());
            estilo = (XSSFCellStyle) dataPlanejada.getCellStyle();
            dataPlanejada.setCellValue(testPlan.getTestCase().get(i).getDataPlanejada());
            dataPlanejada.setCellStyle(estilo);
            complexidade.setCellValue(testPlan.getTestCase().get(i).getComplexidade());
            
            
            
            
            
            

            linha = linha + 2;
            
             row = sheetTS.getRow(linha);

            
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
            complexidade = row.getCell(11);

            }
        
        
        

        FileOutputStream fileOut = new FileOutputStream(destinationSheet);
        workbook.write(fileOut);
        fileOut.close();
        fileSheet.close();
        sucess = true;

        return sucess;
    }
    
    public boolean updateTsSheet(String pathSheet, String nameSheet, TesteCaseTSBean testCase) throws FileNotFoundException, IOException {
        boolean sucess = false;
        destinationSheet = new File(pathSheet);
        destinationSheet.mkdirs();
        destinationSheet = new File(pathSheet+ "\\" + nameSheet);
        sourceStheet = new File(sheetDefault);
        copySheet(sourceStheet, destinationSheet);
        
        
        FileInputStream fileSheet = new FileInputStream(destinationSheet);
        

        XSSFWorkbook workbook = new XSSFWorkbook(fileSheet);
        XSSFSheet sheetTS = workbook.getSheetAt(0);

        XSSFDataFormat format = workbook.createDataFormat();
        XSSFCellStyle estilo = workbook.createCellStyle();
//        String formatData = "aaaa-mm-dd\"T12:00:00-03:00\"";

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
        Cell complexidade = row.getCell(11);
        Cell automatizado = row.getCell(12);
       
            descriptionPlan.setCellValue(testCase.getTestPlan());
            prj.setCellValue(testCase.getSTIPRJ());
            fase.setCellValue(testCase.getFASE());
            testPhase.setCellValue(testCase.getTestPhase());
       

        

//            estilo.setDataFormat(format.getFormat(formatData));
//            estilo.setFillBackgroundColor(HSSFColor.GREEN.index);

            row = sheetTS.getRow(linha);

            
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
            complexidade = row.getCell(11);
            automatizado = row.getCell(12);
            
            
            
            testScriptName.setCellValue(testCase.getTestScriptName());
            testScriptDescription.setCellValue(testCase.getTestScriptDescription());
            stepNo.setCellValue(testCase.getSTEP_NUMERO());
            stepDescription.setCellValue(testCase.getStepDescription());
            expectedResults.setCellValue(testCase.getExpectedResults());
            product.setCellValue(testCase.getProduct());
            estilo = (XSSFCellStyle) dataPlanejada.getCellStyle();
            dataPlanejada.setCellValue(testCase.getDataPlanejada());
            dataPlanejada.setCellStyle(estilo);
            complexidade.setCellValue(testCase.getComplexidade());
            automatizado.setCellValue(testCase.isAutomatizado());
            linha = linha + 2;

        
        
        
        

        FileOutputStream fileOut = new FileOutputStream(destinationSheet);
        workbook.write(fileOut);
        fileOut.close();
        fileSheet.close();
        sucess = true;

        return sucess;
    }
    
     public List<TesteCaseTSBean> readNewSheetTS(String pathSheetFull){
        return null;
    }
    
    
    /*
     * O método readSheet, lê uma planilha de TS
     * 
     */
    public List<TesteCaseTSBean> readSheet(String pathSheetFull) throws FileNotFoundException, IOException {

        List<TesteCaseTSBean> listTS = new ArrayList<TesteCaseTSBean>();

        FileInputStream fileSheet = new FileInputStream(new File(pathSheetFull));
        XSSFWorkbook workbook = new XSSFWorkbook(fileSheet);
        XSSFSheet sheetTS = workbook.getSheetAt(0);

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
        Cell complexidade = row.getCell(11);
        Cell automatizado = row.getCell(12);

        String descPlan = null;
        String project = null;
        String phase = null;

        if (!testScriptName.equals("")) {
            descPlan = descriptionPlan.getStringCellValue();
            project = prj.getStringCellValue();
            phase = testPhase.getStringCellValue();
        }

        while (!testScriptName.getStringCellValue().equals("") && testScriptName != null) {
            
            
            
            testCase = new TesteCaseTSBean();

            testCase.setTestScriptName(testScriptName.getStringCellValue());
            testCase.setTestScriptDescription(testScriptDescription.getStringCellValue());
            testCase.setStepDescription(stepDescription.getStringCellValue());
            testCase.setExpectedResults(expectedResults.getStringCellValue());
            testCase.setProduct(product.getStringCellValue());
//            testCase.setDataPlanejada(dataPlanejada.getDateCellValue());
            testCase.setFase(fase.getStringCellValue());
            testCase.setTestPlan(descPlan);
            testCase.setSTIPRJ(project);
            testCase.setTestPhase(phase);
            testCase.setComplexidade(complexidade.getStringCellValue());
            testCase.setAutomatizado(automatizado.getBooleanCellValue());
            listTS.add(testCase);

            linha = linha + 2;
            
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
            complexidade = row.getCell(11);
            

        }

        return listTS;
    }

    public TesteCaseTSBean getTestCase() {
        return testCase;
    }

    public void setTestCase(TesteCaseTSBean testCase) {
        this.testCase = testCase;
    }

    public boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) { 
               boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
    
        // Agora o diretório está vazio, restando apenas deletá-lo.
        return dir.delete();
    }
    
    
}
