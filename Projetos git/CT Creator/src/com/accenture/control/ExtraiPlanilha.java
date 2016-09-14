/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.control;

import com.accenture.bean.CasoTesteTemp;
import com.accenture.bean.Plano;
import com.accenture.bean.Step;
import com.accenture.bean.TesteCaseTSBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author raphael.da.silva
 */
public class ExtraiPlanilha {

    static String dir = System.getProperty("user.home");
    ;
    private static String fileName = dir + "C:\\FastPlan\\Cadastro CT - ALM.xlsx";

    public ExtraiPlanilha() {

    }

    public static void setFile(String diretorio) {
        fileName = diretorio;

    }

    public static String getFile() {
        return fileName;
    }

    //método responsável por extrair todos os CTs contidos numa planilha de carga
    public List<CasoTesteTemp> getCTExistentes(String planilha) throws FileNotFoundException, IOException {
        List<CasoTesteTemp> listCT = new ArrayList<CasoTesteTemp>();

        //criando variavel que recebe a planilha  selecionada
        FileInputStream arquivo = new FileInputStream(new File(planilha));
        XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
        XSSFSheet sheetPlano = workbook.getSheetAt(1);

        //número da linha que deve começar a leitura da célula -- primeira linha = 0
        int linha = 7;
        //criando variavel row que receberá o numero da da linha
        Row row = sheetPlano.getRow(linha);
        //Criando varialvel do tipo Cell onde recebe a variavel row 
        Cell celCasoTeste = row.getCell(12);
        //variavel para identificar a posicao da lista onde sera inserido o objeto
        int indice = 0;
        int countAdd = 0;

        //loop onde é verificado se a celula do CT está vazia, caso nao sera armazenada
        while (!celCasoTeste.getStringCellValue().equals("")) {
            //instacia um objeto CasoTesteTemp 
            CasoTesteTemp ct = new CasoTesteTemp();
            //atribui valor da celula para o objeto ct 
            ct.setCasoTeste(celCasoTeste.getStringCellValue());
            //numero da celula recebe valor da linha
            int numeroLinhaCel = linha;
            //atribui valor recbe referencia da celula
            ct.setCelula(numeroLinhaCel);
            //lista de CT recebe novo CT
            if (!testCaseAlreadyExists(listCT, ct)) {
                listCT.add(countAdd, ct);
                countAdd++;
            }

            //linha recebe mais 25 para ir para proximo CT da planilha
            linha = 25 + linha;
            //variavel row recebe nova linha
            row = sheetPlano.getRow(linha);
            //variavel cell recebe nova linha
            celCasoTeste = row.getCell(12);

            //incrementa 1 na variavel 
            indice++;

        }

        return listCT;
    }

    public String extraiPlanilhaPlanos(String planilha) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {

        String msg;

        Plano p = new Plano();

        ManipulaDados md = new ManipulaDados();
        ManipulaDadosSQLite bdLite = new ManipulaDadosSQLite();
        //capturando arquivo recebido 
        FileInputStream arquivo = new FileInputStream(new File(planilha));
        System.out.println(planilha);
        XSSFWorkbook workbook = new XSSFWorkbook(arquivo);

        XSSFSheet sheetAlunos = workbook.getSheetAt(1);
        int linha = 7;
        int numCelula = 1;

        Row row = sheetAlunos.getRow(linha);
        Cell celCadeia = row.getCell(1);
        Cell celSegmento = row.getCell(2);
        Cell ccelProduto = row.getCell(3);
        Cell celFuncionalidade = row.getCell(4);
        Cell celCenarioIntegracao = row.getCell(5);
        Cell celSistemaMaster = row.getCell(6);
        Cell celSistemaEnvolvidos = row.getCell(7);
        Cell celFornecedor = row.getCell(8);
        Cell celTpRequisito = row.getCell(9);
        Cell celRequisito = row.getCell(10);
        Cell celCenario = row.getCell(11);
        Cell celCasoTeste = row.getCell(12);
        Cell celDescricao = row.getCell(13);
        Cell celQtdSistemas = row.getCell(18);
        Cell celCenarioAuto = row.getCell(20);
        Cell celType = row.getCell(21);
        Cell celTrg = row.getCell(22);
        Cell celSubject = row.getCell(23);
        Cell celCriacao = row.getCell(24);

        Cell celStep = row.getCell(15);

//                       Plano p = new Plano();
        Step steps = new Step();

        while (!celCasoTeste.getStringCellValue().equals("")) {
            row = sheetAlunos.getRow(linha);
            celCadeia = row.getCell(1);
            celSegmento = row.getCell(2);
            ccelProduto = row.getCell(3);
            celFuncionalidade = row.getCell(4);
            celCenarioIntegracao = row.getCell(5);
            celSistemaMaster = row.getCell(6);
            celSistemaEnvolvidos = row.getCell(7);
            celFornecedor = row.getCell(8);
            celTpRequisito = row.getCell(9);
            celRequisito = row.getCell(10);
            celCenario = row.getCell(11);
            celCasoTeste = row.getCell(12);
            celDescricao = row.getCell(13);
            celQtdSistemas = row.getCell(18);
            celCenarioAuto = row.getCell(20);
            celType = row.getCell(21);
            celTrg = row.getCell(22);
            celSubject = row.getCell(23);
            celCriacao = row.getCell(24);

            System.out.println("Caso de Teste: " + celCasoTeste.getStringCellValue());

            msg = "Extraindo caso de teste: " + celCasoTeste.getStringCellValue();
//                           form.setAreaTextExtracao(msg);

            p.setCadeia(celCadeia.getStringCellValue());
            p.setSegmento(celSegmento.getStringCellValue());
            p.setProduto(ccelProduto.getStringCellValue());
            p.setFuncionalidade(celFuncionalidade.getStringCellValue());
            p.setCenarioIntegrado(celCenarioIntegracao.getStringCellValue());
            p.setSistemaMaster(celSistemaMaster.getStringCellValue());
            p.setSistemasEnvolvidos(celSistemaEnvolvidos.getStringCellValue());
            p.setFornecedor(celFornecedor.getStringCellValue());
            p.setTpRequisito(celTpRequisito.getStringCellValue());
            p.setRequisito(celRequisito.getStringCellValue());
            p.setCenarioTeste(celCenario.getStringCellValue());
            p.setCasoTeste(celCasoTeste.getStringCellValue());
            p.setDescCasoTeste(celDescricao.getStringCellValue());
            p.setQtdSistemas((int) celQtdSistemas.getNumericCellValue());
            p.setCenarioAutomatizavel(celCenarioAuto.getStringCellValue());
            p.setType(celType.getStringCellValue());
            p.setTrg(celTrg.getStringCellValue());
            p.setSubject(celSubject.getStringCellValue());
            p.setCriacaoAlteracao(celCriacao.getStringCellValue());

            bdLite.insertPlano(p);
            celStep = row.getCell(15);
            int linhaStep = linha;
            linha = 25 + linha;
            row = sheetAlunos.getRow(linha);
            celCasoTeste = row.getCell(12);

            while (!celStep.getStringCellValue().equals("")) {

                row = sheetAlunos.getRow(linhaStep);
                Cell celNomeStep = row.getCell(15);
                Cell celDescStep = row.getCell(16);
                Cell celResultadoStep = row.getCell(17);

//                              steps.setIdPlano(md.getIdPlanoBanco(p));
                steps.setIdPlano(bdLite.getIdPlanoBanco(p));
                steps.setNomeStep(celNomeStep.getStringCellValue());
                steps.setDescStep(celDescStep.getStringCellValue());
                steps.setResultadoStep(celResultadoStep.getStringCellValue());
                p.setStep(steps);
//                              bd.insertStep(steps.getNomeStep(), steps.getDescStep(), steps.getResultadoStep(), steps.getIdPlano());

//                              md.insertStep(steps);
                bdLite.insertStep(p);
                linhaStep = linhaStep + 1;

                row = sheetAlunos.getRow(linhaStep);
                celStep = row.getCell(15);
            }
        }

        arquivo.close();

        msg = "Concluído";
//          form.setAreaTextExtracao(msg);
        return msg;
    }

    public String importaCTNovosPlanilha(String planilha, int linha) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {

        String msg;

        Plano p = new Plano();

        ManipulaDados md = new ManipulaDados();
        ManipulaDadosSQLite bdLite = new ManipulaDadosSQLite();
        //capturando arquivo recebido 
        FileInputStream arquivo = new FileInputStream(new File(planilha));
        System.out.println(planilha);
        XSSFWorkbook workbook = new XSSFWorkbook(arquivo);

        XSSFSheet sheetCTs = workbook.getSheetAt(1);
//        int linha = 7;

        Row row = sheetCTs.getRow(linha);
        Cell celCadeia = row.getCell(1);
        Cell celSegmento = row.getCell(2);
        Cell ccelProduto = row.getCell(3);
        Cell celFuncionalidade = row.getCell(4);
        Cell celCenarioIntegracao = row.getCell(5);
        Cell celSistemaMaster = row.getCell(6);
        Cell celSistemaEnvolvidos = row.getCell(7);
        Cell celFornecedor = row.getCell(8);
        Cell celTpRequisito = row.getCell(9);
        Cell celRequisito = row.getCell(10);
        Cell celCenario = row.getCell(11);
        Cell celCasoTeste = row.getCell(12);
        Cell celDescricao = row.getCell(13);
        Cell celQtdSistemas = row.getCell(19);
        Cell celCenarioAuto = row.getCell(21);
        Cell celType = row.getCell(22);
        Cell celTrg = row.getCell(23);
        Cell celSubject = row.getCell(24);
        Cell celCriacao = row.getCell(25);

        Cell celStep = row.getCell(16);

//                       Plano p = new Plano();
        Step steps = new Step();

        row = sheetCTs.getRow(linha);
        celCadeia = row.getCell(1);
        celSegmento = row.getCell(2);
        ccelProduto = row.getCell(3);
        celFuncionalidade = row.getCell(4);
        celCenarioIntegracao = row.getCell(5);
        celSistemaMaster = row.getCell(6);
        celSistemaEnvolvidos = row.getCell(7);
        celFornecedor = row.getCell(8);
        celTpRequisito = row.getCell(9);
        celRequisito = row.getCell(10);
        celCenario = row.getCell(11);
        celCasoTeste = row.getCell(12);
        celDescricao = row.getCell(13);
        celQtdSistemas = row.getCell(19);
        celCenarioAuto = row.getCell(21);
        celType = row.getCell(22);
        celTrg = row.getCell(23);
        celSubject = row.getCell(24);
        celCriacao = row.getCell(25);

        System.out.println("Caso de Teste: " + celCasoTeste.getStringCellValue());

        msg = "Extraindo caso de teste: " + celCasoTeste.getStringCellValue();
//                           form.setAreaTextExtracao(msg);

        p.setCadeia(celCadeia.getStringCellValue());
        p.setSegmento(celSegmento.getStringCellValue());
        p.setProduto(ccelProduto.getStringCellValue());
        p.setFuncionalidade(celFuncionalidade.getStringCellValue());
        p.setCenarioIntegrado(celCenarioIntegracao.getStringCellValue());
        p.setSistemaMaster(celSistemaMaster.getStringCellValue());
        p.setSistemasEnvolvidos(celSistemaEnvolvidos.getStringCellValue());
        p.setFornecedor(celFornecedor.getStringCellValue());
        p.setTpRequisito(celTpRequisito.getStringCellValue());
        p.setRequisito(celRequisito.getStringCellValue());
        p.setCenarioTeste(celCenario.getStringCellValue());
        p.setCasoTeste(celCasoTeste.getStringCellValue());
        p.setDescCasoTeste(celDescricao.getStringCellValue());
        p.setQtdSistemas((int) celQtdSistemas.getNumericCellValue());
        p.setCenarioAutomatizavel(celCenarioAuto.getStringCellValue());
        p.setType(celType.getStringCellValue());
        p.setTrg(celTrg.getStringCellValue());
        p.setSubject(celSubject.getStringCellValue());
        p.setCriacaoAlteracao(celCriacao.getStringCellValue());

        bdLite.insertPlano(p);
        celStep = row.getCell(16);
        int linhaStep = linha;
        linha = 25 + linha;
        row = sheetCTs.getRow(linha);
        celCasoTeste = row.getCell(12);

        while (!celStep.getStringCellValue().equals("")) {

            row = sheetCTs.getRow(linhaStep);
            Cell celNomeStep = row.getCell(16);
            Cell celDescStep = row.getCell(17);
            Cell celResultadoStep = row.getCell(18);

//                              steps.setIdPlano(md.getIdPlanoBanco(p));
            steps.setIdPlano(bdLite.getIdPlanoBanco(p));
            steps.setNomeStep(celNomeStep.getStringCellValue());
            steps.setDescStep(celDescStep.getStringCellValue());
            steps.setResultadoStep(celResultadoStep.getStringCellValue());
            p.setStep(steps);
//                              bd.insertStep(steps.getNomeStep(), steps.getDescStep(), steps.getResultadoStep(), steps.getIdPlano());

//                              md.insertStep(steps);
            bdLite.insertStep(p);
            linhaStep = linhaStep + 1;

            row = sheetCTs.getRow(linhaStep);
            celStep = row.getCell(16);
        }

        arquivo.close();

        msg = "Concluído";
//          form.setAreaTextExtracao(msg);
        return msg;
    }

    public String importaCTExistentePlanilha(String planilha, int linha, String nomeCT) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {

        String msg;

        Plano p = new Plano();

        ManipulaDadosSQLite bdLite = new ManipulaDadosSQLite();
        //capturando arquivo recebido 
        FileInputStream arquivo = new FileInputStream(new File(planilha));
        System.out.println(planilha);
        XSSFWorkbook workbook = new XSSFWorkbook(arquivo);

        XSSFSheet sheetCTs = workbook.getSheetAt(1);
//        int linha = 7;

        Row row = sheetCTs.getRow(linha);
        Cell celCadeia = row.getCell(1);
        Cell celSegmento = row.getCell(2);
        Cell ccelProduto = row.getCell(3);
        Cell celFuncionalidade = row.getCell(4);
        Cell celCenarioIntegracao = row.getCell(5);
        Cell celSistemaMaster = row.getCell(6);
        Cell celSistemaEnvolvidos = row.getCell(7);
        Cell celFornecedor = row.getCell(8);
        Cell celTpRequisito = row.getCell(9);
        Cell celRequisito = row.getCell(10);
        Cell celCenario = row.getCell(11);
        Cell celCasoTeste = row.getCell(12);
        Cell celDescricao = row.getCell(13);
        Cell celQtdSistemas = row.getCell(19);
        Cell celCenarioAuto = row.getCell(21);
        Cell celType = row.getCell(22);
        Cell celTrg = row.getCell(23);
        Cell celSubject = row.getCell(24);
        Cell celCriacao = row.getCell(25);

        Cell celStep = row.getCell(16);

//                       Plano p = new Plano();
        Step steps = new Step();

        row = sheetCTs.getRow(linha);
        celCadeia = row.getCell(1);
        celSegmento = row.getCell(2);
        ccelProduto = row.getCell(3);
        celFuncionalidade = row.getCell(4);
        celCenarioIntegracao = row.getCell(5);
        celSistemaMaster = row.getCell(6);
        celSistemaEnvolvidos = row.getCell(7);
        celFornecedor = row.getCell(8);
        celTpRequisito = row.getCell(9);
        celRequisito = row.getCell(10);
        celCenario = row.getCell(11);
        celCasoTeste = row.getCell(12);
        celDescricao = row.getCell(13);
        celQtdSistemas = row.getCell(19);
        celCenarioAuto = row.getCell(21);
        celType = row.getCell(22);
        celTrg = row.getCell(23);
        celSubject = row.getCell(24);
        celCriacao = row.getCell(25);

        System.out.println("Caso de Teste: " + celCasoTeste.getStringCellValue());
        p.setCasoTeste(nomeCT);
        p = bdLite.getPorCasoTeste(p);

        p.setCadeia(celCadeia.getStringCellValue());
        p.setSegmento(celSegmento.getStringCellValue());
        p.setProduto(ccelProduto.getStringCellValue());
        p.setFuncionalidade(celFuncionalidade.getStringCellValue());
        p.setCenarioIntegrado(celCenarioIntegracao.getStringCellValue());
        p.setSistemaMaster(celSistemaMaster.getStringCellValue());
        p.setSistemasEnvolvidos(celSistemaEnvolvidos.getStringCellValue());
        p.setFornecedor(celFornecedor.getStringCellValue());
        p.setTpRequisito(celTpRequisito.getStringCellValue());
        p.setRequisito(celRequisito.getStringCellValue());
        p.setCenarioTeste(celCenario.getStringCellValue());
        p.setCasoTeste(celCasoTeste.getStringCellValue());
        p.setDescCasoTeste(celDescricao.getStringCellValue());
        p.setQtdSistemas((int) celQtdSistemas.getNumericCellValue());
        p.setCenarioAutomatizavel(celCenarioAuto.getStringCellValue());
        p.setType(celType.getStringCellValue());
        p.setTrg(celTrg.getStringCellValue());
        p.setSubject(celSubject.getStringCellValue());
        p.setCriacaoAlteracao(celCriacao.getStringCellValue());

        bdLite.updatePlano(p);
        celStep = row.getCell(16);
        int linhaStep = linha;
        linha = 25 + linha;
        row = sheetCTs.getRow(linha);
        celCasoTeste = row.getCell(12);

        System.out.println("ID" + p.getId());
        bdLite.deleteStep(p);

        while (!celStep.getStringCellValue().equals("")) {

            row = sheetCTs.getRow(linhaStep);
            Cell celNomeStep = row.getCell(16);
            Cell celDescStep = row.getCell(17);
            Cell celResultadoStep = row.getCell(18);

//                              steps.setIdPlano(md.getIdPlanoBanco(p));
            steps.setIdPlano(bdLite.getIdPlanoBanco(p));
            steps.setNomeStep(celNomeStep.getStringCellValue());
            steps.setDescStep(celDescStep.getStringCellValue());
            steps.setResultadoStep(celResultadoStep.getStringCellValue());
            p.setStep(steps);
//                              bd.insertStep(steps.getNomeStep(), steps.getDescStep(), steps.getResultadoStep(), steps.getIdPlano());

//                              md.insertStep(steps);
            bdLite.insertStep(p);
            linhaStep = linhaStep + 1;

            row = sheetCTs.getRow(linhaStep);
            celStep = row.getCell(16);
        }

        arquivo.close();

        msg = "Concluído";
//          form.setAreaTextExtracao(msg);
        return msg;
    }

    public List<CasoTesteTemp> verificaCTsPlanilha(String planilha) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {

        List<CasoTesteTemp> listPlano = new ArrayList<CasoTesteTemp>();

        ManipulaDados md = new ManipulaDados();
        ManipulaDadosSQLite bdLite = new ManipulaDadosSQLite();
        //capturando arquivo recebido 
        FileInputStream arquivo = new FileInputStream(new File(planilha));
        System.out.println(planilha);
        XSSFWorkbook workbook = new XSSFWorkbook(arquivo);

        XSSFSheet sheetAlunos = workbook.getSheetAt(1);
        int linha = 7;
        int numCelula = 1;

        Row row = sheetAlunos.getRow(linha);

        Cell celCasoTeste = row.getCell(12);

        while (!celCasoTeste.getStringCellValue().equals("")) {
            Plano p = new Plano();
            row = sheetAlunos.getRow(linha);

            celCasoTeste = row.getCell(12);

            System.out.println("Caso de Teste: " + celCasoTeste.getStringCellValue());

            celCasoTeste = row.getCell(12);

        }

        arquivo.close();

        return listPlano;
    }

//         }
    public void extraiConfPlanilha(String diretorio) throws IOException, ClassNotFoundException, SQLException {

        boolean existedados = false;
        ManipulaDadosSQLite bdLite = new ManipulaDadosSQLite();
        FileInputStream arquivo;

        arquivo = new FileInputStream(new File(diretorio));

        System.out.println(diretorio);
        XSSFWorkbook workbook = new XSSFWorkbook(arquivo);

        XSSFSheet sheetConf = workbook.getSheetAt(2);
        int linha = 7;
        int numCelula = 1;

        //Capturando dados da Complexidade na celula A1
        int i = 1;
        CellReference cellReference = new CellReference("A" + i);
        Row row = sheetConf.getRow(cellReference.getRow());
        Cell cell = row.getCell(cellReference.getCol());
        System.out.println(cell.getStringCellValue());

        i++;
        bdLite.deletaTabelaConf("TB_COMPLEXIDADE");

        while (cell != null) {
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            existedados = true;
            bdLite.insertTabelaConf("TB_COMPLEXIDADE", "DESC_COMPLEXIDADE", cell.getStringCellValue());
            i++;
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());

        }

        bdLite.deletaTabelaConf("TB_AUTOMATIZAVEL");
        //Capturando dados Automatizável na celula A7
        i = 7;
        cellReference = new CellReference("A" + i);
        row = sheetConf.getRow(cellReference.getRow());
        cell = row.getCell(cellReference.getCol());
        System.out.println(cell.getStringCellValue());

        bdLite.deletaTabelaConf("TB_AUTOMATIZAVEL");

        i++;
        while (cell != null) {
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            existedados = true;
            bdLite.insertTabelaConf("TB_AUTOMATIZAVEL", "DESC_AUTOMATIZAVEL", cell.getStringCellValue());
            i++;
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());

        }

        //Capturando dados type
        i = 12;
        cellReference = new CellReference("A" + i);
        row = sheetConf.getRow(cellReference.getRow());
        cell = row.getCell(cellReference.getCol());
        System.out.println(cell.getStringCellValue());

        bdLite.deletaTabelaConf("TB_TYPE");

        i++;
        while (cell != null) {
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            existedados = true;
            bdLite.insertTabelaConf("TB_TYPE", "DESC_TYPE", cell.getStringCellValue());
            i++;
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());

        }

        //Capturando dados TRG
        i = 17;
        cellReference = new CellReference("A" + i);
        row = sheetConf.getRow(cellReference.getRow());
        cell = row.getCell(cellReference.getCol());
        System.out.println(cell.getStringCellValue());

        bdLite.deletaTabelaConf("TB_TRG");

        i++;
        while (cell != null) {
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            existedados = true;
            bdLite.insertTabelaConf("TB_TRG", "DESC_TRG", cell.getStringCellValue());
            i++;
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());

        }

        //Capturando dados CADEIA
        i = 17;
        cellReference = new CellReference("A" + i);
        row = sheetConf.getRow(cellReference.getRow());
        cell = row.getCell(cellReference.getCol());
        System.out.println(cell.getStringCellValue());

        bdLite.deletaTabelaConf("TB_CADEIA");

        i++;
        while (cell != null) {
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            existedados = true;
            bdLite.insertTabelaConf("TB_CADEIA", "DESC_CADEIA", cell.getStringCellValue());
            i++;
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());

        }

        //Capturando dados TP REQUISITO
        i = 32;
        cellReference = new CellReference("A" + i);
        row = sheetConf.getRow(cellReference.getRow());
        cell = row.getCell(cellReference.getCol());
        System.out.println(cell.getStringCellValue());

        bdLite.deletaTabelaConf("TB_TP_REQUISITO");

        i++;
        while (cell != null) {
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            existedados = true;
            bdLite.insertTabelaConf("TB_TP_REQUISITO", "DESC_TP_REQUISITO", cell.getStringCellValue());
            i++;
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());

        }

        //Capturando dados criação/alteração
        i = 27;
        cellReference = new CellReference("A" + i);
        row = sheetConf.getRow(cellReference.getRow());
        cell = row.getCell(cellReference.getCol());
        System.out.println(cell.getStringCellValue());

        bdLite.deletaTabelaConf("TB_CRIACAO");

        i++;
        while (cell != null) {
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            existedados = true;
            bdLite.insertTabelaConf("TB_CRIACAO", "DESC_CRIACAO", cell.getStringCellValue());
            i++;
            cellReference = new CellReference("A" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());

        }

        //Capturando dados SISTEMA MASTER
        i = 1;
        cellReference = new CellReference("C" + i);
        row = sheetConf.getRow(cellReference.getRow());
        cell = row.getCell(cellReference.getCol());
        System.out.println(cell.getStringCellValue());

        bdLite.deletaTabelaConf("TB_SISTEMA_MASTER");

        i++;
        while (cell != null) {
            cellReference = new CellReference("C" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            existedados = true;
            bdLite.insertTabelaConf("TB_SISTEMA_MASTER", "DESC_SISTEMA_MASTER", cell.getStringCellValue());
            i++;
            cellReference = new CellReference("C" + i);
            row = sheetConf.getRow(cellReference.getRow());
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

        bdLite.deletaTabelaConf("TB_FUNCIONALIDADE");

        i++;
        while (cell != null) {
            cellReference = new CellReference("E" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            existedados = true;
            bdLite.insertTabelaConf("TB_FUNCIONALIDADE", "DESC_FUNCIONALIDADE", cell.getStringCellValue());
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

        bdLite.deletaTabelaConf("TB_CENARIO_INTEGRADO");

        i++;
        while (cell != null) {
            cellReference = new CellReference("G" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());
            System.out.println(cell.getStringCellValue());
            existedados = true;
            bdLite.insertTabelaConf("TB_CENARIO_INTEGRADO", "DESC_CENARIO_INTEGRADO", cell.getStringCellValue());
            i++;
            cellReference = new CellReference("G" + i);
            row = sheetConf.getRow(cellReference.getRow());
            cell = row.getCell(cellReference.getCol());

        }

        if (existedados == true) {
            bdLite.insertVersaCarga(sheetConf.getSheetName());
        }

    }

    public static void gravaCTPlanilha(List<Plano> plano, String panilha, int linhaCelula) throws FileNotFoundException, IOException, InvalidFormatException, SQLException, ClassNotFoundException {
        ManipulaDadosSQLite banco = new ManipulaDadosSQLite();
//recebe a planilha e atribui a variavel arquivo
        FileInputStream arquivo = new FileInputStream(new File(panilha));
        System.out.println(panilha);
        //instacia um workbook passando arquivo como paramentro
        XSSFWorkbook workbook = new XSSFWorkbook(arquivo);

        XSSFSheet sheetPlano = workbook.getSheetAt(1);
        String cadeia = "B", segmento = "C", produto = "D", funcionalidade = "E", cenarioItegrado = "F",
                sistemaMaster = "G", sistemasEnvolvidos = "H", fornecedor = "I", tpRequisito = "J",
                requisito = "K", cenarioTeste = "L", casoTeste = "M", descricao = "N", nomeStep = "P",
                descricaoStep = "Q", resultadoEsperado = "R", cenarioAuto = "U", type = "V", trg = "W",
                subject = "X", criacao = "Y";
        CellReference cellReference = new CellReference("B8");
//     Row row = sheetPlano.getRow(cellReference.getRow());
//     Cell cell = row.getCell(cellReference.getCol());

        int tamanhoLista = plano.size();
        int numeroCelula = 8;

        int linha = linhaCelula;
        int celula = 12;

        Row row = sheetPlano.getRow(linha);
        Cell cell;//= row.getCell(celula);
        Cell celCadeia = row.getCell(1);
        Cell celSegmento = row.getCell(2);
        Cell celProduto = row.getCell(3);
        Cell celFuncionalidade = row.getCell(4);
        Cell celCenarioIntegracao = row.getCell(5);
        Cell celSistemaMaster = row.getCell(6);
        Cell celSistemaEnvolvidos = row.getCell(7);
        Cell celFornecedor = row.getCell(8);
        Cell celTpRequisito = row.getCell(9);
        Cell celRequisito = row.getCell(10);
        Cell celCenario = row.getCell(11);
        Cell celCasoTeste = row.getCell(12);
        Cell celDescricao = row.getCell(13);
        Cell celQtdSistemas = row.getCell(19);
        Cell celCenarioAuto = row.getCell(21);
        Cell celType = row.getCell(22);
        Cell celTrg = row.getCell(23);
        Cell celSubject = row.getCell(24);
        Cell celCriacao = row.getCell(25);
        Cell celStep = row.getCell(16);

        Step steps = new Step();

        for (int i = 0; i < tamanhoLista; i++) {

            row = sheetPlano.getRow(linha);

            celCadeia = row.getCell(1);
            celSegmento = row.getCell(2);
            celProduto = row.getCell(3);
            celFuncionalidade = row.getCell(4);
            celCenarioIntegracao = row.getCell(5);
            celSistemaMaster = row.getCell(6);
            celSistemaEnvolvidos = row.getCell(7);
            celFornecedor = row.getCell(8);
            celTpRequisito = row.getCell(9);
            celRequisito = row.getCell(10);
            celCenario = row.getCell(11);
            celCasoTeste = row.getCell(12);
            celDescricao = row.getCell(13);
            Cell celComplexidade = row.getCell(15);
            celQtdSistemas = row.getCell(19);
            Cell celQtdStep = row.getCell(20);
            celCenarioAuto = row.getCell(21);
            celType = row.getCell(22);
            celTrg = row.getCell(23);
            celSubject = row.getCell(24);
            celCriacao = row.getCell(25);

            celCadeia.setCellValue(plano.get(i).getCadeia());
            celSegmento.setCellValue(plano.get(i).getSegmento());
            celProduto.setCellValue(plano.get(i).getProduto());
            celFuncionalidade.setCellValue(plano.get(i).getFuncionalidade());
            celCenarioIntegracao.setCellValue(plano.get(i).getCenarioIntegrado());
            celSistemaMaster.setCellValue(plano.get(i).getSistemaMaster());
            celSistemaEnvolvidos.setCellValue(plano.get(i).getSistemasEnvolvidos());
            celFornecedor.setCellValue(plano.get(i).getFornecedor());
            celTpRequisito.setCellValue(plano.get(i).getTpRequisito());
            celRequisito.setCellValue(plano.get(i).getRequisito());
            celCenario.setCellValue(plano.get(i).getCenarioTeste());
            celCasoTeste.setCellValue(plano.get(i).getCasoTeste());
            celDescricao.setCellValue(plano.get(i).getDescCasoTeste());
            String formulaComplexibilidade = celComplexidade.getCellFormula();
            celComplexidade.setCellFormula(formulaComplexibilidade);
            celQtdSistemas.setCellValue(plano.get(i).getQtdSistemas());
            String formulaQtdStep = celQtdStep.getCellFormula();
            celQtdStep.setCellFormula(formulaQtdStep);
            celCenarioAuto.setCellValue(plano.get(i).getCenarioAutomatizavel());
            celType.setCellValue(plano.get(i).getType());
            celTrg.setCellValue(plano.get(i).getTrg());
            celSubject.setCellValue(plano.get(i).getSubject());
            celCriacao.setCellValue(plano.get(i).getCriacaoAlteracao());

            celStep = row.getCell(16);

            row = sheetPlano.getRow(linha);
//            celCasoTeste = row.getCell(12);
            int linhaStep = linha;

            List<Plano> listPlanos = banco.selectPlanoPorId(plano.get(i));

            List<Step> listStep = banco.getStepPorPlano(plano.get(i));

            int linhaLimpeza = linha;
            //limpa as células de step
            for (int cont = 0; cont <= 24; cont++) {

                Cell celNomeStep = row.getCell(16);
                Cell celDescStep = row.getCell(17);
                Cell celResultadoStep = row.getCell(18);

                String valor = null;
                celNomeStep.setCellValue(valor);
                celDescStep.setCellValue(valor);
                celResultadoStep.setCellValue(valor);

                linhaLimpeza = linhaLimpeza + 1;

                row = sheetPlano.getRow(linhaLimpeza);

            }
            //fim
            row = sheetPlano.getRow(linha);
            int tamanho = listStep.size();
            int idTemp = 0;
            for (int cont = 0; cont < tamanho; cont++) {

                //                   if(idTemp != listPlanos.get(cont).getStep().getId()){
//                    model.addRow(new String[]{String.valueOf(listPlanos.get(cont).getStep().getNomeStep()), listPlanos.get(cont).getStep().getDescStep(),
//                        listPlanos.get(cont).getStep().getResultadoStep(), String.valueOf(listPlanos.get(cont).getStep().getId())});
                //                   }
                Cell celNomeStep = row.getCell(16);
                Cell celDescStep = row.getCell(17);
                Cell celResultadoStep = row.getCell(18);

                celNomeStep.setCellValue(listStep.get(cont).getNomeStep());
                celDescStep.setCellValue(listStep.get(cont).getDescStep());
                celResultadoStep.setCellValue(listStep.get(cont).getResultadoStep());

                //caso o ct seja alteração pinta os steps de amarelo - inicio
                if (plano.get(i).getCriacaoAlteracao().equals("Alteração")) {
                    Color color = new XSSFColor(java.awt.Color.yellow);
//                    XSSFCellStyle style = workbook.createCellStyle();
//                    style.setBorderTop((short) 6); // double lines border
//                    style.setBorderBottom((short) 1); // single line border
//                    style.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
//                    
//                    celNomeStep.setCellStyle(style);
                }
                //fim

                linhaStep = linhaStep + 1;

                row = sheetPlano.getRow(linhaStep);
                celStep = row.getCell(16);

                idTemp = listPlanos.get(cont).getId();
            }

            linha = linha + 25;
            row = sheetPlano.getRow(linha);
            cell = celCadeia;

        }

        FileOutputStream fileOut = new FileOutputStream(new File(panilha));
        workbook.write(fileOut);
        fileOut.close();
    }
    
    
    public  void exportTStoTI(List<TesteCaseTSBean> testCases, String planilha) throws FileNotFoundException, IOException, InvalidFormatException, SQLException, ClassNotFoundException {
       
        copySheet(new File("C:\\FastPlan\\sheets\\TI.xlsx"), new File(planilha));
        
        
        //recebe a planilha e atribui a variavel arquivo
        FileInputStream arquivo = new FileInputStream(new File(planilha));
        System.out.println(planilha);
        //instacia um workbook passando arquivo como paramentro
        XSSFWorkbook workbook = new XSSFWorkbook(arquivo);

        XSSFSheet sheetPlano = workbook.getSheetAt(1);
        
//     Row row = sheetPlano.getRow(cellReference.getRow());
//     Cell cell = row.getCell(cellReference.getCol());

        int tamanhoLista = testCases.size();
        int numeroCelula = 8;

        int linha = 7;
        int celula = 12;

        Row row = sheetPlano.getRow(linha);
        Cell cell;//= row.getCell(celula);
        Cell celCadeia = row.getCell(1);
        Cell celSegmento = row.getCell(2);
        Cell celProduto = row.getCell(3);
        Cell celFuncionalidade = row.getCell(4);
        Cell celCenarioIntegracao = row.getCell(5);
        Cell celSistemaMaster = row.getCell(6);
        Cell celSistemaEnvolvidos = row.getCell(7);
        Cell celFornecedor = row.getCell(8);
        Cell celTpRequisito = row.getCell(9);
        Cell celRequisito = row.getCell(10);
        Cell celCenario = row.getCell(11);
        Cell celCasoTeste = row.getCell(12);
        Cell celDescricao = row.getCell(13);
        Cell celQtdSistemas = row.getCell(19);
        Cell celCenarioAuto = row.getCell(21);
        Cell celType = row.getCell(22);
        Cell celTrg = row.getCell(23);
        Cell celSubject = row.getCell(24);
        Cell celCriacao = row.getCell(25);
        Cell celStep = row.getCell(16);

        Step steps = new Step();

        for (int i = 0; i < tamanhoLista; i++) {
            
            
            testCases.get(i).setTestScriptName(testCases.get(i).getTestScriptName().replaceAll("\\d\\d.\\d\\d-", ""));
            
            for(int j=0; j< testCases.get(i).getParameters().size(); j++){
                   
                if(testCases.get(i).getTestScriptDescription().contains(testCases.get(i).getParameters().get(j).getParameterValue())){
                    testCases.get(i).setTestScriptDescription(testCases.get(i).getTestScriptDescription().replace("<"+testCases.get(i).getParameters().get(j).getParameterValue()+">", "<<<"+testCases.get(i).getParameters().get(j).getParameterName()+">>>"));
                }
            }
            
            

            row = sheetPlano.getRow(linha);

            celCadeia = row.getCell(1);
            celSegmento = row.getCell(2);
            celProduto = row.getCell(3);
            celFuncionalidade = row.getCell(4);
            celCenarioIntegracao = row.getCell(5);
            celSistemaMaster = row.getCell(6);
            celSistemaEnvolvidos = row.getCell(7);
            celFornecedor = row.getCell(8);
            celTpRequisito = row.getCell(9);
            celRequisito = row.getCell(10);
            celCenario = row.getCell(11);
            celCasoTeste = row.getCell(12);
            celDescricao = row.getCell(13);
            Cell celComplexidade = row.getCell(15);
            celQtdSistemas = row.getCell(19);
            Cell celQtdStep = row.getCell(20);
            celCenarioAuto = row.getCell(21);
            celType = row.getCell(22);
            celTrg = row.getCell(23);
            celSubject = row.getCell(24);
            celCriacao = row.getCell(25);

            celCadeia.setCellValue("");
            celSegmento.setCellValue("");
            celProduto.setCellValue("");
            celFuncionalidade.setCellValue("");
            celCenarioIntegracao.setCellValue("");
            celSistemaMaster.setCellValue(testCases.get(i).getProduct());
            celSistemaEnvolvidos.setCellValue(testCases.get(i).getProduct());
            celFornecedor.setCellValue("Accenture");
            celTpRequisito.setCellValue("");
            celRequisito.setCellValue("");
            celCenario.setCellValue(testCases.get(i).getTestScriptName());
            celCasoTeste.setCellValue(testCases.get(i).getTestScriptName());
            celDescricao.setCellValue(testCases.get(i).getTestScriptDescription());
            String formulaComplexibilidade = celComplexidade.getCellFormula();
            celComplexidade.setCellFormula(formulaComplexibilidade);
            celQtdSistemas.setCellValue(1);
            String formulaQtdStep = celQtdStep.getCellFormula();
            celQtdStep.setCellFormula(formulaQtdStep);
            celCenarioAuto.setCellValue("Sim");
            celType.setCellValue("Manual");
            celTrg.setCellValue("Não");
            celSubject.setCellValue("");
            celCriacao.setCellValue("Criação");

            celStep = row.getCell(16);

            row = sheetPlano.getRow(linha);
//            celCasoTeste = row.getCell(12);
            int linhaStep = linha;

           

            
            row = sheetPlano.getRow(linha);
            int tamanho = testCases.get(i).getListStep().size();
            int idTemp = 0;
            for (int cont = 0; cont < tamanho; cont++) {

                //                   if(idTemp != listPlanos.get(cont).getStep().getId()){
//                    model.addRow(new String[]{String.valueOf(listPlanos.get(cont).getStep().getNomeStep()), listPlanos.get(cont).getStep().getDescStep(),
//                        listPlanos.get(cont).getStep().getResultadoStep(), String.valueOf(listPlanos.get(cont).getStep().getId())});
                //                   }
                Cell celNomeStep = row.getCell(16);
                Cell celDescStep = row.getCell(17);
                Cell celResultadoStep = row.getCell(18);
                
                for(int j=0; j< testCases.get(i).getParameters().size(); j++){
                   
                    if(testCases.get(i).getListStep().get(cont).getDescStep().contains(testCases.get(i).getParameters().get(j).getParameterValue())){
                        testCases.get(i).getListStep().get(cont).setDescStep(testCases.get(i).getListStep().get(cont).getDescStep().replace("<"+testCases.get(i).getParameters().get(j).getParameterValue()+">", "<<<"+testCases.get(i).getParameters().get(j).getParameterName()+">>>"));
                    }

                     if(testCases.get(i).getListStep().get(cont).getResultadoStep().contains(testCases.get(i).getParameters().get(j).getParameterValue())){
                        testCases.get(i).getListStep().get(cont).setResultadoStep(testCases.get(i).getListStep().get(cont).getResultadoStep().replace("<"+testCases.get(i).getParameters().get(j).getParameterValue()+">", "<<<"+testCases.get(i).getParameters().get(j).getParameterName()+">>>"));
                    }
                
                
            }
                
                
                testCases.get(i).getListStep().get(cont).setNomeStep("Step "+(cont+1));
                
                
                

                celNomeStep.setCellValue(testCases.get(i).getListStep().get(cont).getNomeStep());
                celDescStep.setCellValue(testCases.get(i).getListStep().get(cont).getDescStep());
                celResultadoStep.setCellValue(testCases.get(i).getListStep().get(cont).getResultadoStep());



                linhaStep = linhaStep + 1;

                row = sheetPlano.getRow(linhaStep);
                celStep = row.getCell(16);

                
            }

            linha = linha + 25;
            row = sheetPlano.getRow(linha);
            cell = celCadeia;

        }

        FileOutputStream fileOut = new FileOutputStream(new File(planilha));
        workbook.write(fileOut);
        fileOut.close();
    }

    /**
     * Método para sobrescrever ct na planilha * com o ct existente em uma
     * determinada linha será substituido *
     *
     * @author Raphael Coelho
     *
     * @param Plano - objeto plano
     * @param String - caminho da planilha
     * @param int - número da linha que está o ct na planilha
     *
     * @return void
     *
     */
    public static void gravaCTPlanilha(Plano plano, String panilha, int linhaCelula) throws FileNotFoundException, IOException, InvalidFormatException, SQLException, ClassNotFoundException {

        /**
         * Exemplo básico de um comentário em JavaDoc
         */
        //recebe a planilha e atribui a variavel arquivo
        FileInputStream arquivo = new FileInputStream(new File(panilha));
        System.out.println(panilha);
        //instacia um workbook passando arquivo como paramentro
        ManipulaDadosSQLite banco = new ManipulaDadosSQLite();
        XSSFWorkbook workbook = new XSSFWorkbook(arquivo);

        XSSFSheet sheetPlano = workbook.getSheetAt(1);

        CellReference cellReference = new CellReference("B8");
//     Row row = sheetPlano.getRow(cellReference.getRow());
//     Cell cell = row.getCell(cellReference.getCol());

//        int tamanhoLista = plano.size();
        int numeroCelula = 8;

        int linha = linhaCelula;
        int celula = 12;

        Row row = sheetPlano.getRow(linha);
        Cell cell;//= row.getCell(celula);
        Cell celCadeia = row.getCell(1);
        Cell celSegmento = row.getCell(2);
        Cell celProduto = row.getCell(3);
        Cell celFuncionalidade = row.getCell(4);
        Cell celCenarioIntegracao = row.getCell(5);
        Cell celSistemaMaster = row.getCell(6);
        Cell celSistemaEnvolvidos = row.getCell(7);
        Cell celFornecedor = row.getCell(8);
        Cell celTpRequisito = row.getCell(9);
        Cell celRequisito = row.getCell(10);
        Cell celCenario = row.getCell(11);
        Cell celCasoTeste = row.getCell(12);
        Cell celDescricao = row.getCell(13);
        Cell celQtdSistemas = row.getCell(19);
        Cell celCenarioAuto = row.getCell(21);
        Cell celType = row.getCell(22);
        Cell celTrg = row.getCell(23);
        Cell celSubject = row.getCell(24);
        Cell celCriacao = row.getCell(25);
        Cell celStep = row.getCell(16);

        Step steps = new Step();

        row = sheetPlano.getRow(linha);

        celCadeia = row.getCell(1);
        celSegmento = row.getCell(2);
        celProduto = row.getCell(3);
        celFuncionalidade = row.getCell(4);
        celCenarioIntegracao = row.getCell(5);
        celSistemaMaster = row.getCell(6);
        celSistemaEnvolvidos = row.getCell(7);
        celFornecedor = row.getCell(8);
        celTpRequisito = row.getCell(9);
        celRequisito = row.getCell(10);
        celCenario = row.getCell(11);
        celCasoTeste = row.getCell(12);
        celDescricao = row.getCell(13);
        Cell celComplexidade = row.getCell(15);
        celQtdSistemas = row.getCell(19);
        Cell celQtdStep = row.getCell(20);
        celCenarioAuto = row.getCell(21);
        celType = row.getCell(22);
        celTrg = row.getCell(23);
        celSubject = row.getCell(24);
        celCriacao = row.getCell(25);

        celCadeia.setCellValue(plano.getCadeia());
        celSegmento.setCellValue(plano.getSegmento());
        celProduto.setCellValue(plano.getProduto());
        celFuncionalidade.setCellValue(plano.getFuncionalidade());
        celCenarioIntegracao.setCellValue(plano.getCenarioIntegrado());
        celSistemaMaster.setCellValue(plano.getSistemaMaster());
        celSistemaEnvolvidos.setCellValue(plano.getSistemasEnvolvidos());
        celFornecedor.setCellValue(plano.getFornecedor());
        celTpRequisito.setCellValue(plano.getTpRequisito());
        celRequisito.setCellValue(plano.getRequisito());
        celCenario.setCellValue(plano.getCenarioTeste());
        celCasoTeste.setCellValue(plano.getCasoTeste());
        celDescricao.setCellValue(plano.getDescCasoTeste());
        String formulaComplexibilidade = celComplexidade.getCellFormula();
        celComplexidade.setCellFormula(formulaComplexibilidade);
        celQtdSistemas.setCellValue(plano.getQtdSistemas());
        String formulaQtdStep = celQtdStep.getCellFormula();
        celQtdStep.setCellFormula(formulaQtdStep);
        celCenarioAuto.setCellValue(plano.getCenarioAutomatizavel());
        celType.setCellValue(plano.getType());
        celTrg.setCellValue(plano.getTrg());
        celSubject.setCellValue(plano.getSubject());
        celCriacao.setCellValue(plano.getCriacaoAlteracao());

        celStep = row.getCell(16);

        row = sheetPlano.getRow(linha);
//            celCasoTeste = row.getCell(12);
        int linhaStep = linha;

        List<Plano> listPlanos = banco.selectPlanoPorId(plano);

        List<Step> listStep = banco.getStepPorPlano(plano);

        int linhaLimpeza = linha;
        //limpa as células de step
        for (int cont = 0; cont <= 24; cont++) {

            Cell celNomeStep = row.getCell(16);
            Cell celDescStep = row.getCell(17);
            Cell celResultadoStep = row.getCell(18);
            String valor = null;
            celNomeStep.setCellValue(valor);
            celDescStep.setCellValue(valor);
            celResultadoStep.setCellValue(valor);

            linhaLimpeza = linhaLimpeza + 1;

            row = sheetPlano.getRow(linhaLimpeza);

        }
        //fim
        row = sheetPlano.getRow(linha);
        int tamanho = listStep.size();
        int idTemp = 0;
        for (int cont = 0; cont < tamanho; cont++) {

            //                   if(idTemp != listPlanos.get(cont).getStep().getId()){
//                    model.addRow(new String[]{String.valueOf(listPlanos.get(cont).getStep().getNomeStep()), listPlanos.get(cont).getStep().getDescStep(),
//                        listPlanos.get(cont).getStep().getResultadoStep(), String.valueOf(listPlanos.get(cont).getStep().getId())});
            //                   }
            Cell celNomeStep = row.getCell(16);
            Cell celDescStep = row.getCell(17);
            Cell celResultadoStep = row.getCell(18);

            celNomeStep.setCellValue(listStep.get(cont).getNomeStep());
            celDescStep.setCellValue(listStep.get(cont).getDescStep());
            celResultadoStep.setCellValue(listStep.get(cont).getResultadoStep());
            //caso o ct seja alteração pinta os steps de amarelo - inicio
            if (plano.getCriacaoAlteracao().equals("Alteração")) {
                Color color = new XSSFColor(java.awt.Color.yellow);
//                    XSSFCellStyle style = workbook.createCellStyle();
//                    style.setBorderTop((short) 6); // double lines border
//                    style.setBorderBottom((short) 1); // single line border
//                    style.setFillBackgroundColor((XSSFColor) color);
//                    
//                    celNomeStep.setCellStyle(style);
            }
            //fim
            linhaStep = linhaStep + 1;

            row = sheetPlano.getRow(linhaStep);
            celStep = row.getCell(16);

            idTemp = listPlanos.get(cont).getId();
        }

        linha = linha + 25;
        row = sheetPlano.getRow(linha);
        cell = celCadeia;

        FileOutputStream fileOut = new FileOutputStream(new File(panilha));
        workbook.write(fileOut);
        fileOut.close();
    }

    public DefaultTableModel obtemModelo(String descricao, String status, String erro) throws SQLException {
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addRow(new String[]{descricao, status,
            erro});

        return modelo;
    }

    public void limpaStepsPlanilha(XSSFSheet sheet, Row row) {
        for (int cont = 0; cont < 24; cont++) {

            Cell celNomeStep = row.getCell(16);
            Cell celDescStep = row.getCell(17);
            Cell celResultadoStep = row.getCell(18);

            celNomeStep.setCellValue("");
            celDescStep.setCellValue("");
            celResultadoStep.setCellValue("");

            cont = cont + 1;

            row = sheet.getRow(cont);

        }

    }

    public boolean testCaseAlreadyExists(List<CasoTesteTemp> listCt, CasoTesteTemp ct) {

        boolean exists = false;

        for (int i = 0; i < listCt.size(); i++) {
            if (listCt.get(i).getCasoTeste().equalsIgnoreCase(ct.getCasoTeste())) {
                exists = true;
            }

        }
        return exists;
    }
    
    
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
}
