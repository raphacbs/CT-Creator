/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.model;

/**
 *
 * @author Raphael
 */

import com.accenture.control.ExtraiPlanilha;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.hssf.util.CellReference;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class PlanoExcel {
        private static final String fileName = "C:\\FastPlan\\Cadastro CT - ALM_1.xlsx";
    
    
       public static void main(String[] args) throws FileNotFoundException {
           PlanoExcel planilha = new PlanoExcel();
           planilha.extraiPlanilha();

       }
       
       public void extraiPlanilha(){
           try{
               //Leitura
               FileInputStream arquivo = new FileInputStream(new File(fileName));
               
               // Carregando workbook
               XSSFWorkbook wb = new XSSFWorkbook(arquivo);
               // Selecionando a primeira aba
               XSSFSheet s = wb.getSheetAt(1);
               
             // Caso queira pegar valor por referencia
			CellReference cellReference = new CellReference("M8");
                        Row row = s.getRow(cellReference.getRow());
			Cell cell = row.getCell(cellReference.getCol());
			System.out.println("Valor Refe:" + cell.getStringCellValue());

			// Fazendo um loop em todas as linhas
			for (Row rowFor : s) {
				// FAzendo loop em todas as colunas
				for (Cell cellFor : rowFor) {
					try {
						// Verifica o tipo de dado
						if (cellFor.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							// Na coluna 6 tenho um formato de data
							if (cellFor.getColumnIndex() == 6) {
								// Se estiver no formato de data
								if (DateUtil.isCellDateFormatted(cellFor)) {
									// Formatar para o padrao brasileiro
									Date d = cellFor.getDateCellValue();
									DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
									System.out.println(df.format(d));
								}
							} else {
								// Mostrar numerico
								System.out.println(cellFor.getNumericCellValue());
							}
						} else {
							// Mostrar String
							System.out.println(cellFor.getStringCellValue());
						}
					} catch (Exception e) {
						// Mostrar Erro
						System.out.println(e.getMessage());
					}
				}
				// Mostrar pulo de linha
				System.out.println("------------------------");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

       
       
}
