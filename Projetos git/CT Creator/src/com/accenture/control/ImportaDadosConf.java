/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.control;


import java.awt.BorderLayout;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author raphael.da.silva
 */
public class ImportaDadosConf extends Thread {

    static String dir = System.getProperty("user.home");

    private static String diretorio = "C:\\FastPlan\\Cadastro CT - ALM_1.xlsx";
    private JLabel texto1 = new JLabel();
    private JTextArea textArea1 = new JTextArea();
    private JDialog janelaImportacao;
    private int qtdConf = 0;
    private String conf;

    public ImportaDadosConf(final Frame frame, boolean b) throws IOException {

        janelaImportacao = new JDialog(frame, b);
        JProgressBar progress = new JProgressBar();
        
       new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {

                janelaImportacao.setTitle("Importando Configurações iniciais");
                texto1.setText("Aguarde...");

                janelaImportacao.setAutoRequestFocus(true);
                janelaImportacao.setLayout(null);
                janelaImportacao.add(texto1, BorderLayout.SOUTH);
                janelaImportacao.setSize(400, 250);
                janelaImportacao.setLayout(new FlowLayout(FlowLayout.CENTER));
                janelaImportacao.setLocationRelativeTo(frame);
                janelaImportacao.setVisible(true);
                 return null;
            }
            @Override
            protected void done() {
                
            }
        }.execute();

        threadQtd();

    }
    
    
    public ImportaDadosConf(final Frame frame, boolean b, String teste) throws IOException {

        janelaImportacao = new JDialog(frame, b);
        final JProgressBar progress = new JProgressBar();
        
       new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {

               janelaImportacao.setTitle("Aguarde...");
                janelaImportacao.setAutoRequestFocus(true);
                
                progress.setIndeterminate(true);
                janelaImportacao.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); 
                janelaImportacao.setLocationRelativeTo(frame);
                janelaImportacao.setSize(50, 50);
                progress.setSize(400, 50);             
                janelaImportacao.getContentPane().add(progress);
                janelaImportacao.pack();
                Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
                janelaImportacao.setLocation((tela.width - janelaImportacao.getSize().width) / 2,
                        (tela.height - janelaImportacao.getSize().height) / 2);
                
                 janelaImportacao.setVisible(true);
                 return null;
            }
            @Override
            protected void done() {
                
            }
        }.execute();

        threadQtd();

    }

    public void threadQtd() {

        new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
                texto1.setText("Importando configuração : " + getQtdDadosConf(diretorio));
                return null;

            }

            @Override
            protected void done() {

            }

        }.execute();

    }

    public void alinhaLabel() {
        texto1.setAlignmentX(CENTER_ALIGNMENT);
        texto1.setAlignmentY(CENTER_ALIGNMENT);
    }

    public JDialog getJanela() {
        return janelaImportacao;
    }

    public void setTexto(String texto) {
        texto1.setText(texto);
    }

    public int getQtdDadosConf(String diretorio) throws FileNotFoundException, IOException {

        FileInputStream arquivo;
        arquivo = new FileInputStream(new File(diretorio));
        //
        XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
        //Selecionando a planilha de configurações
        XSSFSheet sheetConf = workbook.getSheetAt(2);
        int linha = 7;
        int numCelula = 1;
        int i = 1;

        //Caontando COmplexidade
        CellReference cellReference = new CellReference("A" + i);
        Row row = sheetConf.getRow(cellReference.getRow());
        Cell cell = row.getCell(cellReference.getCol());

        while (cell != null) {
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());
            texto1.setText("Importando itens de configuração: " + cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());

            i++;
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());

            qtdConf++;

        }

        i = 7;
        cellReference = new CellReference("A" + i);
        row = sheetConf.getRow(cellReference.getRow());
        cell = row.getCell(cellReference.getCol());
        System.out.println(cell.getStringCellValue());

        i++;
        while (cell != null) {
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());
            texto1.setText("Importando itens de configuração: " + cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());

            i++;
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());

            qtdConf++;

        }

        //Capturando dados type
        i = 12;
        cellReference = new CellReference("A" + i);
        row = sheetConf.getRow(cellReference.getRow());
        cell = row.getCell(cellReference.getCol());
        System.out.println(cell.getStringCellValue());

        i++;
        while (cell != null) {
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());
            texto1.setText("Importando itens de configuração: " + cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());

            i++;
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            qtdConf++;
        }

        //Capturando dados TRG
        i = 17;
        cellReference = new CellReference("A" + i);
        row = sheetConf.getRow(cellReference.getRow());
        cell = row.getCell(cellReference.getCol());
        System.out.println(cell.getStringCellValue());

        i++;
        while (cell != null) {
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());
            texto1.setText("Importando itens de configuração: " + cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());

            i++;
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            qtdConf++;
        }

        //Capturando dados CADEIA
        i = 17;
        cellReference = new CellReference("A" + i);
        row = sheetConf.getRow(cellReference.getRow());
        cell = row.getCell(cellReference.getCol());
        System.out.println(cell.getStringCellValue());

        i++;
        while (cell != null) {
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());
            texto1.setText("Importando itens de configuração: " + cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());

            i++;
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            qtdConf++;
        }

        //Capturando dados TP REQUISITO
        i = 32;
        cellReference = new CellReference("A" + i);
        row = sheetConf.getRow(cellReference.getRow());
        cell = row.getCell(cellReference.getCol());
        System.out.println(cell.getStringCellValue());

        i++;
        while (cell != null) {
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());
            texto1.setText("Importando itens de configuração: " + cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());

            i++;
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            qtdConf++;
        }

        //Capturando dados criação/alteração
        i = 27;
        cellReference = new CellReference("A" + i);
        row = sheetConf.getRow(cellReference.getRow());
        cell = row.getCell(cellReference.getCol());
        System.out.println(cell.getStringCellValue());

        i++;
        while (cell != null) {
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());
            texto1.setText("Importando itens de configuração: " + cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());

            i++;
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            qtdConf++;
        }

        //Capturando dados SISTEMA MASTER
        i = 1;
        cellReference = new CellReference("C" + i);
        row = sheetConf.getRow(cellReference.getRow());
        cell = row.getCell(cellReference.getCol());
        System.out.println(cell.getStringCellValue());

        i++;
        while (cell != null) {
            cellReference = new CellReference("C" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());
            texto1.setText("Importando itens de configuração: " + cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());

            i++;
            cellReference = new CellReference("C" + i);
            row = sheetConf.getRow(cellReference.getRow());
            qtdConf++;

            if (row == null) {
                cell = null;

            } else {
                cell = row.getCell(cellReference.getCol(), row.RETURN_BLANK_AS_NULL);
            }

        }

        //Capturando dados FUNCIONALIDADE
        i = 1;
        cellReference = new CellReference("E" + i);
        row = sheetConf.getRow(cellReference.getRow());
        cell = row.getCell(cellReference.getCol());
        System.out.println(cell.getStringCellValue());

        i++;
        while (cell != null) {
            cellReference = new CellReference("E" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());
            texto1.setText("Importando itens de configuração: " + cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());

            qtdConf++;
            i++;
            cellReference = new CellReference("E" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());

        }

        //Capturando dados CENARIO INTEGRADO
        i = 1;
        cellReference = new CellReference("G" + i);
        row = sheetConf.getRow(cellReference.getRow());
        cell = row.getCell(cellReference.getCol());
        System.out.println(cell.getStringCellValue());

        i++;
        while (cell != null) {
            cellReference = new CellReference("G" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());
            texto1.setText("Importando itens de configuração: " + cell.getStringCellValue());
            texto1.paintAll(texto1.getGraphics());

            i++;
            cellReference = new CellReference("G" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            qtdConf++;
        }

        return qtdConf;
    }

}
