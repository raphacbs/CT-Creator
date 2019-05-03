/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.dao;

import com.accenture.bean.ParameterBean;
import com.accenture.bean.Plano;
import com.accenture.bean.Step;
import com.accenture.bean.SystemBean;
import com.accenture.bean.TestPlanTSBean;
import com.accenture.bean.TesteCaseTSBean;
import com.accenture.connection.ConnectionFactory;
import com.accenture.connection.EnumConnection;
import static com.accenture.connection.EnumConnection.MSSQL;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
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

    private File sourceStheet;
    private File destinationSheet;
    //  private org.apache.log4j.Logger logger;
    private final static Logger logger = Logger.getLogger(TesteCaseTSDAO.class);

    public TesteCaseTSDAO() {
        //sourceStheet = new File(sheetDefault);
        //testCase = new TesteCaseTSBean();
        // logger = org.apache.log4j.Logger.getLogger(TesteCaseTSDAO.class);
        try{
         Properties props = new Properties();
            props.load(new FileInputStream("log4j.properties"));
            PropertyConfigurator.configure(props);
        }catch(Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }
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
        destinationSheet = new File(pathSheet + "\\" + nameSheet);
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
        destinationSheet = new File(pathSheet + "\\" + nameSheet + ".xlsx");
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

    public boolean createSpreadsheetTS(String pathSheet, String nameSheet, TestPlanTSBean testPlan) throws Exception {

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
        Runtime.getRuntime().exec("cmd /c" + cmd);

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

        Cell priority = row.getCell(15);
        Cell data = row.getCell(16);
        Cell rework = row.getCell(17);
        Cell regression = row.getCell(18);

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

            priority = row.getCell(15);
            data = row.getCell(16);
            rework = row.getCell(17);
            regression = row.getCell(18);

            logger.info("Inserindo dados dos TCs");
            System.out.println("com.accenture.ts.dao.TesteCaseTSDAO.createSpreadsheetTS() - " + testPlan.getSti() + " - " + "row:" + linha);
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

            automatizado.setCellValue(yesOrNo(testPlan.getTestCase().get(i).isAutomatizado()));
            priority.setCellValue(yesOrNo(testPlan.getTestCase().get(i).isPriority()));
            data.setCellValue(yesOrNo(testPlan.getTestCase().get(i).isData()));
            rework.setCellValue(yesOrNo(testPlan.getTestCase().get(i).isRework()));
            regression.setCellValue(yesOrNo(testPlan.getTestCase().get(i).isRegression()));

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

            if (testPlan.getTestCase().get(i).isAutomatizado()) {

                for (int j = 0; j < testCasesAutomatizados.size(); j++) {
                    if (testPlan.getTestCase().get(i).equals(testCasesAutomatizados.get(j).getTestScriptName())) {
                        existInList = true;
                    }
                }
                if (!existInList) {
                    testCasesAutomatizados.add(testPlan.getTestCase().get(i));
                    existInList = true;
                }
            }

        }

//        if(testCasesAutomatizados.size()!=0){
//        ExtraiPlanilha extraiPlanilha = new ExtraiPlanilha();
//        extraiPlanilha.exportTStoTI(testCasesAutomatizados, sheetTI);
//        }
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

    public void setColorCells(Cell[] cells, XSSFWorkbook workbook) {
        XSSFCellStyle styleColor = workbook.createCellStyle();
        styleColor.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);

        for (int i = 0; i < cells.length; i++) {
            styleColor = (XSSFCellStyle) cells[i].getCellStyle();
            styleColor.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
            cells[i].setCellStyle(styleColor);
        }

    }

    public boolean newTsSheet(String pathSheet, String nameSheet, TestPlanTSBean testPlan) throws Exception {
        boolean sucess = false;
        destinationSheet = new File(pathSheet);
        destinationSheet.mkdirs();
        destinationSheet = new File(pathSheet + "\\" + nameSheet);
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
        for (int i = 0; i < testPlan.getTestCase().size(); i++) {
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
        destinationSheet = new File(pathSheet + "\\" + nameSheet);
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

    public TestPlanTSBean readPlanSheet(String pathSheetFull) throws FileNotFoundException, IOException {
        TestPlanTSBean plan = new TestPlanTSBean();

        FileInputStream fileSheet = new FileInputStream(new File(pathSheetFull));
        XSSFWorkbook workbook = new XSSFWorkbook(fileSheet);
        XSSFSheet sheetTS = workbook.getSheetAt(0);

        int linha = 2;

        Row row = sheetTS.getRow(linha);

        Cell planName = row.getCell(0);
        Cell release = row.getCell(1);
        Cell sti = row.getCell(2);
        Cell cr = row.getCell(3);
        Cell testPhase = row.getCell(4);

        plan.setName(planName.getStringCellValue());
        plan.setRelease(release.getStringCellValue());
        plan.setSti(sti.getStringCellValue());
        plan.setCrFase(cr.getStringCellValue());
        plan.setTestPhase(testPhase.getStringCellValue());

        return plan;
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
            if (automatizado != null) {
                testCase.setAutomatizado(automatizado.getBooleanCellValue());
            } else {
                testCase.setAutomatizado(false);
            }
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
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // Agora o diretório está vazio, restando apenas deletá-lo.
        return dir.delete();
    }

    public List<TesteCaseTSBean> getAll() {
        try {
            List<TesteCaseTSBean> caseTSBeans = new ArrayList<TesteCaseTSBean>();
            String SQL_SELECT_TC = "SELECT "
                    + "[Id],"
                    + "[TestPlan],"
                    + "[STIPRJ],"
                    + "[Fase],"
                    + "[TestPhase],"
                    + "[TestScriptName],"
                    + "[TestScriptDescription],"
                    + "[StepDescription],"
                    + "[ExpectedResults],"
                    + "[Product],"
                    + "[DataPlanejada],"
                    + "[NumeroCenario],"
                    + "[NumeroCt],"
                    + "[Complexidade],"
                    + "[Automatizado],"
                    + "[Cenario],"
                    + "[Rework],"
                    + "[Priority],"
                    + "[Regression],"
                    + "[Data],"
                    + "[IdSystem],"
                    + "[createdBy],"
                    + "[modifiedBy],"
                    + "[createDate],"
                    + "[modifyDate]"
                    + " FROM [CTCreatorDB].[dbo].[TesteCaseTSBean]";

            ConnectionFactory cf = new ConnectionFactory(MSSQL);
            Statement statement = cf.getConnection().createStatement();

            ResultSet rs = statement.executeQuery(SQL_SELECT_TC);

            while (rs.next()) {
                TesteCaseTSBean tc = new TesteCaseTSBean();
                tc.setId(rs.getInt("Id"));
                tc.setTestPlan(rs.getString("TestPlan"));
                tc.setSTIPRJ(rs.getString("STIPRJ"));
                tc.setFase(rs.getString("Fase"));
                tc.setTestPhase(rs.getString("TestPhase"));
                tc.setTestScriptName(rs.getString("TestScriptName"));
                tc.setTestScriptDescription(rs.getString("TestScriptDescription"));
                tc.setStepDescription(rs.getString("StepDescription"));
                tc.setExpectedResults(rs.getString("ExpectedResults"));
                tc.setProduct(rs.getString("Product"));
                tc.setDataPlanejada(rs.getTimestamp("DataPlanejada"));
                tc.setNumeroCenario(rs.getString("NumeroCenario"));
                tc.setNumeroCt(rs.getString("NumeroCt"));
                tc.setComplexidade(rs.getString("Complexidade"));
                tc.setAutomatizado(rs.getBoolean("Automatizado"));
                tc.setCenario(rs.getString("Cenario"));
                tc.setRework(rs.getBoolean("Rework"));
                tc.setPriority(rs.getBoolean("Priority"));
                tc.setRegression(rs.getBoolean("Regression"));
                tc.setData(rs.getBoolean("Data"));
                tc.setIdSystem(rs.getInt("IdSystem"));
                tc.setCreatedBy(rs.getString("createdBy"));
                tc.setModifiedBy(rs.getString("modifiedBy"));
                tc.setCreateDate(rs.getTimestamp("createDate"));
                tc.setModifyDate(rs.getTimestamp("modifyDate"));

                caseTSBeans.add(tc);
            }
            logger.info(testCase);
            return caseTSBeans;

        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Erro ao recuperar os CTs", ex);
            return null;
        }

    }
    
    public TesteCaseTSBean getById(int id) {
        try {
           
            String SQL_SELECT_TC = "SELECT "
                    + "[Id],"
                    + "[TestPlan],"
                    + "[STIPRJ],"
                    + "[Fase],"
                    + "[TestPhase],"
                    + "[TestScriptName],"
                    + "[TestScriptDescription],"
                    + "[StepDescription],"
                    + "[ExpectedResults],"
                    + "[Product],"
                    + "[DataPlanejada],"
                    + "[NumeroCenario],"
                    + "[NumeroCt],"
                    + "[Complexidade],"
                    + "[Automatizado],"
                    + "[Cenario],"
                    + "[Rework],"
                    + "[Priority],"
                    + "[Regression],"
                    + "[Data],"
                    + "[IdSystem],"
                    + "[createdBy],"
                    + "[modifiedBy],"
                    + "[createDate],"
                    + "[modifyDate]"
                    + " FROM [CTCreatorDB].[dbo].[TesteCaseTSBean] "
                    + "WHERE [Id] = ?";

            ConnectionFactory cf = new ConnectionFactory(MSSQL);
            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_SELECT_TC);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();

            TesteCaseTSBean tc = new TesteCaseTSBean();
            
            while (rs.next()) {
                
                tc.setId(rs.getInt("Id"));
                tc.setTestPlan(rs.getString("TestPlan"));
                tc.setSTIPRJ(rs.getString("STIPRJ"));
                tc.setFase(rs.getString("Fase"));
                tc.setTestPhase(rs.getString("TestPhase"));
                tc.setTestScriptName(rs.getString("TestScriptName"));
                tc.setTestScriptDescription(rs.getString("TestScriptDescription"));
                tc.setStepDescription(rs.getString("StepDescription"));
                tc.setExpectedResults(rs.getString("ExpectedResults"));
                tc.setProduct(rs.getString("Product"));
                tc.setDataPlanejada(rs.getTimestamp("DataPlanejada"));
                tc.setNumeroCenario(rs.getString("NumeroCenario"));
                tc.setNumeroCt(rs.getString("NumeroCt"));
                tc.setComplexidade(rs.getString("Complexidade"));
                tc.setAutomatizado(rs.getBoolean("Automatizado"));
                tc.setCenario(rs.getString("Cenario"));
                tc.setRework(rs.getBoolean("Rework"));
                tc.setPriority(rs.getBoolean("Priority"));
                tc.setRegression(rs.getBoolean("Regression"));
                tc.setData(rs.getBoolean("Data"));
                tc.setIdSystem(rs.getInt("IdSystem"));
                tc.setCreatedBy(rs.getString("createdBy"));
                tc.setModifiedBy(rs.getString("modifiedBy"));
                tc.setCreateDate(rs.getTimestamp("createDate"));
                tc.setModifyDate(rs.getTimestamp("modifyDate"));

             
            }

            return tc;

        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Erro ao recuperar os CTs", ex);
            return null;
        }

    }

    public List<TesteCaseTSBean> getBySystemBean(SystemBean system) {
        try {
            List<TesteCaseTSBean> caseTSBeans = new ArrayList<TesteCaseTSBean>();
            String SQL_SELECT_TC = "SELECT "
                    + "[Id],"
                    + "[TestPlan],"
                    + "[STIPRJ],"
                    + "[Fase],"
                    + "[TestPhase],"
                    + "[TestScriptName],"
                    + "[TestScriptDescription],"
                    + "[StepDescription],"
                    + "[ExpectedResults],"
                    + "[Product],"
                    + "[DataPlanejada],"
                    + "[NumeroCenario],"
                    + "[NumeroCt],"
                    + "[Complexidade],"
                    + "[Automatizado],"
                    + "[Cenario],"
                    + "[Rework],"
                    + "[Priority],"
                    + "[Regression],"
                    + "[Data],"
                    + "[IdSystem],"
                    + "[createdBy],"
                    + "[modifiedBy],"
                    + "[createDate],"
                    + "[modifyDate]"
                    + " FROM [CTCreatorDB].[dbo].[TesteCaseTSBean]"
                    + "WHERE [IdSystem] = ?";

            ConnectionFactory cf = new ConnectionFactory(MSSQL);
            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_SELECT_TC);
            ps.setInt(1, system.getId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TesteCaseTSBean tc = new TesteCaseTSBean();
                tc.setId(rs.getInt("Id"));
                tc.setTestPlan(rs.getString("TestPlan"));
                tc.setSTIPRJ(rs.getString("STIPRJ"));
                tc.setFase(rs.getString("STIPRJ"));
                tc.setTestPhase(rs.getString("TestPhase"));
                tc.setTestScriptName(rs.getString("TestScriptName"));
                tc.setTestScriptDescription(rs.getString("TestScriptDescription"));
                tc.setStepDescription(rs.getString("StepDescription"));
                tc.setExpectedResults(rs.getString("ExpectedResults"));
                tc.setProduct(rs.getString("Product"));
                tc.setDataPlanejada(rs.getTimestamp("DataPlanejada"));
                tc.setNumeroCenario(rs.getString("NumeroCenario"));
                tc.setNumeroCt(rs.getString("NumeroCt"));
                tc.setComplexidade(rs.getString("Complexidade"));
                tc.setAutomatizado(rs.getBoolean("Automatizado"));
                tc.setCenario(rs.getString("Cenario"));
                tc.setRework(rs.getBoolean("Rework"));
                tc.setPriority(rs.getBoolean("Priority"));
                tc.setRegression(rs.getBoolean("Regression"));
                tc.setData(rs.getBoolean("Data"));
                tc.setIdSystem(rs.getInt("IdSystem"));
                tc.setCreatedBy(rs.getString("createdBy"));
                tc.setModifiedBy(rs.getString("modifiedBy"));
                tc.setCreateDate(rs.getTimestamp("createDate"));
                tc.setModifyDate(rs.getTimestamp("modifyDate"));

                caseTSBeans.add(tc);
            }

            return caseTSBeans;

        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Erro ao recuperar os CTs", ex);
            return null;
        }
    }

    public List<TesteCaseTSBean> getByFields(String fields) {
        try {
            List<TesteCaseTSBean> caseTSBeans = new ArrayList<TesteCaseTSBean>();
            String SQL_SELECT_TC = "SELECT "
                    + "[Id],"
                    + "[TestPlan],"
                    + "[STIPRJ],"
                    + "[Fase],"
                    + "[TestPhase],"
                    + "[TestScriptName],"
                    + "[TestScriptDescription],"
                    + "[StepDescription],"
                    + "[ExpectedResults],"
                    + "[Product],"
                    + "[DataPlanejada],"
                    + "[NumeroCenario],"
                    + "[NumeroCt],"
                    + "[Complexidade],"
                    + "[Automatizado],"
                    + "[Cenario],"
                    + "[Rework],"
                    + "[Priority],"
                    + "[Regression],"
                    + "[Data],"
                    + "[IdSystem],"
                    + "[createdBy],"
                    + "[modifiedBy],"
                    + "[createDate],"
                    + "[modifyDate]"
                    + " FROM [CTCreatorDB].[dbo].[TesteCaseTSBean]"
                    + "WHERE " + fields;

            ConnectionFactory cf = new ConnectionFactory(MSSQL);
            Statement statement = cf.getConnection().createStatement();

            ResultSet rs = statement.executeQuery(SQL_SELECT_TC);

            while (rs.next()) {
                TesteCaseTSBean tc = new TesteCaseTSBean();
                tc.setId(rs.getInt("Id"));
                tc.setTestPlan(rs.getString("TestPlan"));
                tc.setSTIPRJ(rs.getString("STIPRJ"));
                tc.setFase(rs.getString("STIPRJ"));
                tc.setTestPhase(rs.getString("TestPhase"));
                tc.setTestScriptName(rs.getString("TestScriptName"));
                tc.setTestScriptDescription(rs.getString("TestScriptDescription"));
                tc.setStepDescription(rs.getString("StepDescription"));
                tc.setExpectedResults(rs.getString("ExpectedResults"));
                tc.setProduct(rs.getString("Product"));
                tc.setDataPlanejada(rs.getTimestamp("DataPlanejada"));
                tc.setNumeroCenario(rs.getString("NumeroCenario"));
                tc.setNumeroCt(rs.getString("NumeroCt"));
                tc.setComplexidade(rs.getString("Complexidade"));
                tc.setAutomatizado(rs.getBoolean("Automatizado"));
                tc.setCenario(rs.getString("Cenario"));
                tc.setRework(rs.getBoolean("Rework"));
                tc.setPriority(rs.getBoolean("Priority"));
                tc.setRegression(rs.getBoolean("Regression"));
                tc.setData(rs.getBoolean("Data"));
                tc.setIdSystem(rs.getInt("IdSystem"));
                tc.setCreatedBy(rs.getString("createdBy"));
                tc.setModifiedBy(rs.getString("modifiedBy"));
                tc.setCreateDate(rs.getTimestamp("createDate"));
                tc.setModifyDate(rs.getTimestamp("modifyDate"));

                caseTSBeans.add(tc);
            }

            return caseTSBeans;

        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Erro ao recuperar os CTs", ex);
            return null;
        }

    }

    public TesteCaseTSBean insert(TesteCaseTSBean testCase) throws SQLException {
        ConnectionFactory cf = new ConnectionFactory(MSSQL);

        String SQL_INSERT_TC = "INSERT INTO [CTCreatorDB].[dbo].[TesteCaseTSBean] ([TestPlan],"
                + "[STIPRJ],"
                + "[Fase],"
                + "[TestPhase],"
                + "[TestScriptName],"
                + "[TestScriptDescription],"
                + "[StepDescription],"
                + "[ExpectedResults],"
                + "[Product],"
                + "[DataPlanejada],"
                + "[NumeroCenario],"
                + "[NumeroCt],"
                + "[Complexidade],"
                + "[Automatizado],"
                + "[Cenario],"
                + "[Rework],"
                + "[Priority],"
                + "[Regression],"
                + "[Data],"
                + "[IdSystem],"
                + "[createdBy],"
                + "[modifiedBy],"
                + "[createDate],"
                + "[modifyDate]"
                + ")"
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {

            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_INSERT_TC, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, testCase.getTestPlan());
            ps.setString(2, testCase.getSTIPRJ());
            ps.setString(3, testCase.getFase());
            ps.setString(4, testCase.getTestPhase());
            ps.setString(5, testCase.getTestScriptName());
            ps.setString(6, testCase.getTestScriptDescription());
            ps.setString(7, testCase.getStepDescription());
            ps.setString(8, testCase.getExpectedResults());
            ps.setString(9, testCase.getProduct());
            ps.setTimestamp(10, new java.sql.Timestamp(testCase.getDataPlanejada().getTime()));
            ps.setString(11, testCase.getNumeroCenario());
            ps.setString(12, testCase.getNumeroCt());
            ps.setString(13, testCase.getComplexidade());
            ps.setBoolean(14, testCase.isAutomatizado());
            ps.setString(15, testCase.getCenario());
            ps.setBoolean(16, testCase.isRework());
            ps.setBoolean(17, testCase.isPriority());
            ps.setBoolean(18, testCase.isRegression());
            ps.setBoolean(19, testCase.isData());
            ps.setInt(20, testCase.getIdSystem());
            ps.setString(21, testCase.getCreatedBy());
            ps.setString(22, testCase.getModifiedBy());
            ps.setTimestamp(23, new java.sql.Timestamp(testCase.getModifyDate().getTime()));
            ps.setTimestamp(24, new java.sql.Timestamp(testCase.getCreateDate().getTime()));

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                return null;
            } else {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    testCase.setId(id);
                    ps.close();
                    generatedKeys.close();
                    return testCase;
                }
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.print(ex.getMessage());
            logger.error("Erro ao salvar CT", ex);
            return null;
       }


    }

    public TesteCaseTSBean update(TesteCaseTSBean testCase) throws Exception {
        ConnectionFactory cf = new ConnectionFactory(MSSQL);

        String SQL_UPDATE_TC = "UPDATE [CTCreatorDB].[dbo].[TesteCaseTSBean] SET "
                + "[TestPlan] = ?,"
                + "[STIPRJ] = ?,"
                + "[Fase] = ?,"
                + "[TestPhase] = ?,"
                + "[TestScriptName] = ?,"
                + "[TestScriptDescription] = ?,"
                + "[StepDescription] = ?,"
                + "[ExpectedResults] = ?,"
                + "[Product] = ?,"
                + "[DataPlanejada] = ?,"
                + "[NumeroCenario] = ?,"
                + "[NumeroCt] = ?,"
                + "[Complexidade] = ?,"
                + "[Automatizado] = ?,"
                + "[Cenario] = ?,"
                + "[Rework] = ?,"
                + "[Priority] = ?,"
                + "[Regression] = ?,"
                + "[Data] = ?,"
                + "[IdSystem] = ?,"
                + "[createdBy] = ?,"
                + "[modifiedBy] = ?,"
                + "[createDate] = ?,"
                + "[modifyDate] = ? "
                + "WHERE [Id] = ?";
        try {

            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_UPDATE_TC);

            ps.setString(1, testCase.getTestPlan());
            ps.setString(2, testCase.getSTIPRJ());
            ps.setString(3, testCase.getFase());
            ps.setString(4, testCase.getTestPhase());
            ps.setString(5, testCase.getTestScriptName());
            ps.setString(6, testCase.getTestScriptDescription());
            ps.setString(7, testCase.getStepDescription());
            ps.setString(8, testCase.getExpectedResults());
            ps.setString(9, testCase.getProduct());
            ps.setTimestamp(10, new java.sql.Timestamp(testCase.getDataPlanejada().getTime()));
            ps.setString(11, testCase.getNumeroCenario());
            ps.setString(12, testCase.getNumeroCt());
            ps.setString(13, testCase.getComplexidade());
            ps.setBoolean(14, testCase.isAutomatizado());
            ps.setString(15, testCase.getCenario());
            ps.setBoolean(16, testCase.isRework());
            ps.setBoolean(17, testCase.isPriority());
            ps.setBoolean(18, testCase.isRegression());
            ps.setBoolean(19, testCase.isData());
            ps.setInt(20, testCase.getIdSystem());
            ps.setString(21, testCase.getCreatedBy());
            ps.setString(22, testCase.getModifiedBy());
            ps.setTimestamp(23, new java.sql.Timestamp(testCase.getModifyDate().getTime()));
            ps.setTimestamp(24, new java.sql.Timestamp(testCase.getCreateDate().getTime()));
            ps.setInt(25, testCase.getId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                return null;
            } else {
                return testCase;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.print(ex.getMessage());
            logger.error("Erro ao salvar CT", ex);
            return null;
       }
    }
    
    public boolean delete(int id){
         String SQL_DELETE_TC = "DELETE FROM [CTCreatorDB].[dbo].[TesteCaseTSBean] WHERE [Id] = ?";
        try {
            ConnectionFactory cf = new ConnectionFactory(MSSQL);
            PreparedStatement ps = cf.getConnection().prepareStatement(SQL_DELETE_TC);
            
            ps.setInt(1, id);
            
            int row = ps.executeUpdate();
            
            if(row > 0 ){
                return true;
            }else{
                return false;
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.print(ex.getMessage());
            logger.error("Erro ao salvar CT", ex);
            return false;
        }
    }

    private String yesOrNo(boolean b) {
        if (b) {
            return "Sim";
        } else {
            return "Não";
        }
    }

    public List<String> getParameter(String descStep) {

        List<String> parameters = new ArrayList<String>();
        String pattern = "<<<(\\w+)>>>";
        Pattern r = Pattern.compile(pattern);  // Now create matcher object.
        Matcher m = r.matcher(descStep);
        while (m.find()) {
            if(!parameters.contains(m.group(1)))
                  parameters.add(m.group(1));
        }
        return parameters;
    }

}
