/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.control;

import com.accenture.bean.Plano;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Raphael
 */
public class ExcelDAO {
    
    private static String dir = System.getProperty("user.home");;
    private static final String fileName = "C:\\FastPlan\\Cadastro CT - ALM_1.xlsx";
    private Connection conn;
    private Statement stm;
    
    
    public String[] carregaPlanilhaFuncionalidade() throws IOException, ClassNotFoundException, SQLException{
        
         
         Plano plano = new Plano();
         ManipulaDadosSQLite banco = new ManipulaDadosSQLite();
         String[] funcionalidade = null;
        try {
            FileInputStream arquivo = new FileInputStream(new File(ExcelDAO.fileName));
            XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
           //setado a planilha de configurações
            XSSFSheet sheetPlano = workbook.getSheetAt(2);
            //linha pa
            int linha= 1; 
            int coluna = 4;
          
            funcionalidade = new String[sheetPlano.getLastRowNum()];
            int index = 0;
               for(int count = 1; count < sheetPlano.getLastRowNum(); count++){
				Row row = sheetPlano.getRow(count);
                                for(int countColuna = 0;countColuna < 1;countColuna++){
                                          Cell cell = row.getCell(coluna,Row.CREATE_NULL_AS_BLANK);
                                         System.out.println(cell.getColumnIndex()+"-" + cell.getRowIndex());
                                          if(cell.getCellType()==CELL_TYPE_BLANK){
						System.out.println("Campo vazio");
				          }
				         else if(cell.getCellType()== CELL_TYPE_NUMERIC){
                                              double valor = cell.getNumericCellValue();
                                             
                                              
                                              System.out.println(valor);
				         }else{
                                           String valor = cell.getStringCellValue();
                                           System.out.println(valor);
                                           funcionalidade[index] = valor;
                                           System.out.println(funcionalidade[index]);
                                           banco.insertTabelaConf("TB_FUNCIONALIDADE", "DESC_FUNCIONALIDADE", valor);
                                           index++;
                                         }
                                }
               }
             
             
             
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    return funcionalidade;
        }
    
    
    public String[] carregaPlanilhaSistemaMaster() throws IOException{
        
         
         Plano plano = new Plano();
        
         String[] sistemaMaster = null;
        try {
            FileInputStream arquivo = new FileInputStream(new File(ExcelDAO.fileName));
            XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
           //setado a planilha de configurações
            XSSFSheet sheetPlano = workbook.getSheetAt(2);
            //linha pa
            int linha= 1; 
            int coluna = 2;
          
            sistemaMaster = new String[sheetPlano.getLastRowNum()];
            int index = 0;
               for(int count = 1; count < sheetPlano.getLastRowNum(); count++){
				Row row = sheetPlano.getRow(count);
                                for(int countColuna = 0;countColuna < 1;countColuna++){
                                          Cell cell = row.getCell(coluna,Row.CREATE_NULL_AS_BLANK);
                                         System.out.println(cell.getColumnIndex()+"-" + cell.getRowIndex());
                                          if(cell.getCellType()==CELL_TYPE_BLANK){
						System.out.println("Campo vazio");
				          }
				         else if(cell.getCellType()== CELL_TYPE_NUMERIC){
                                              double valor = cell.getNumericCellValue();
                                              System.out.println(valor);
				         }else{
                                           String valor = cell.getStringCellValue();
                                           System.out.println(valor);
                                           sistemaMaster[index] = valor;
                                           System.out.println(sistemaMaster[index]);
                                           index++;
                                         }
                                }
               }
             
             
             
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    return sistemaMaster;
        }
    
    
     
}