/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.control;

import com.accenture.bean.StepPadrao;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author raphael.da.silva
 */
public class ImportaStepPadrao {
    private static String dir = System.getProperty("user.home");
    
    public ImportaStepPadrao(){
        
    }
    
    public List<StepPadrao> getStepPadraoPlanilha(String planilha) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException{
       int linha = 1;
       List<StepPadrao> listSp = new ArrayList<StepPadrao>();
       

       // fazendo uma instacia do banco
       ManipulaDadosSQLite bdLite = new ManipulaDadosSQLite();
       //Setando caminho da planilha para o arquivo
       FileInputStream arquivo = new FileInputStream(new File(planilha));
       // Instânciando um workbook e passando o arquivo como parametro
       XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
       //Selecionando a segunda planilha
       XSSFSheet aba = workbook.getSheetAt(1);
       //Selecionando a segunda linha (1)
       Row row = aba.getRow(linha);
       //selecionando a celula A2
       Cell celVersao = row.getCell(4);
           
       //Selecionando a segunda linha (1)
       row = aba.getRow(linha);
       //selecionando a celula A2(descrição)
       Cell celDesc = row.getCell(0);
       //selecionando a celula A2(Resultado esperado)
       Cell celResultado = row.getCell(1);
       //selecionando a celula A2(tipo)
       Cell celTipo = row.getCell(2);
       //selecionando a celula A2(sistema)
       Cell celSistema = row.getCell(3);
       
       //loop para capturar todos os steps padrao da planilha
       while (celDesc != null) {
           StepPadrao sp = new StepPadrao();
           row = aba.getRow(linha);
           celDesc = row.getCell(0);
           celResultado = row.getCell(1);
           celTipo = row.getCell(2);
           celSistema = row.getCell(3);
           celVersao = row.getCell(4);
           
           sp.setDescStep(celDesc.getStringCellValue());
           sp.setResultadoStep(celResultado.getStringCellValue());
           sp.setTipoStepPadrao(celTipo.getStringCellValue());
           sp.setSistema(celSistema.getStringCellValue());
           sp.setVersao(celVersao.getNumericCellValue());
           linha ++;
           listSp.add(sp);
           row = aba.getRow(linha);
           celDesc = row.getCell(0);
           celResultado = row.getCell(1);
           celTipo = row.getCell(2);
           celSistema = row.getCell(3);
           celVersao = row.getCell(4);
       }
       
       
        return listSp;
    }
    
    /**
     * Verifica se a versão que existe no banco é maior do que a versão da planilha
     *
     * @return caso a versão da planilha seja maior retorna true, caso não retorna falso
     */
    public boolean verificaVersao(String planilha) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException{
       int linha = 1;
       StepPadrao sp = new StepPadrao();
       double versao = 0;

       // fazendo uma instacia do banco
       ManipulaDadosSQLite bd = new ManipulaDadosSQLite();
       //Setando caminho da planilha para o arquivo
       FileInputStream arquivo = new FileInputStream(new File(planilha));
       // Instânciando um workbook e passando o arquivo como parametro
       XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
       //Selecionando a primeira planilha
       XSSFSheet aba = workbook.getSheetAt(0);
       //Selecionando a segunda linha (1)
       Row row = aba.getRow(linha);
       //selecionando a celula A2
       Cell celVersao = row.getCell(0);
       
       //loop para capturar a última versão da planilha
       while (celVersao.getNumericCellValue() != 0) {
//           double versao = 0;
          
           
           versao = celVersao.getNumericCellValue();
           linha = linha + 1;
           row = aba.getRow(linha);
           celVersao = row.getCell(0);
       }
       
       
       
       if(versao <= bd.getVersaoStepPadrao()){
           return false;
       }else{
           return true;
       }
       
        
    }
    
    /**
     * Verifica se a versão que existe no banco é maior do que a versão da planilha
     *
     * @return caso a versão da planilha seja maior retorna true, caso não retorna falso
     */
    public double getVersaoPlanilha(String planilha) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException{
       int linha = 1;
       StepPadrao sp = new StepPadrao();
       double versao = 0;

       // fazendo uma instacia do banco
       ManipulaDadosSQLite bd = new ManipulaDadosSQLite();
       //Setando caminho da planilha para o arquivo
       FileInputStream arquivo = new FileInputStream(new File(planilha));
       // Instânciando um workbook e passando o arquivo como parametro
       XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
       //Selecionando a primeira planilha
       XSSFSheet aba = workbook.getSheetAt(0);
       //Selecionando a segunda linha (1)
       Row row = aba.getRow(linha);
       //selecionando a celula A2
       Cell celVersao = row.getCell(0);
       
       //loop para capturar a última versão da planilha
       while (celVersao.getNumericCellValue() != 0) {
//           double versao = 0;
          
           
           versao = celVersao.getNumericCellValue();
           linha = linha + 1;
           row = aba.getRow(linha);
           celVersao = row.getCell(0);
       }
       
       return versao;
       
       
        
    }
    
}
