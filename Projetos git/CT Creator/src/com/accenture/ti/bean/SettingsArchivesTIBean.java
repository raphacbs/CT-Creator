/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ti.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author brucilin.de.gouveia
 */
public class SettingsArchivesTIBean {

    private Map<String, String> mandatoryFields;
    private String urlSvn;
    private String testCaseDir;
    private String system;
    private String faseCr;
    private String localPath;
    private String complexidade;
    private String fileName;
    private final String archivesSettingsLocal;
    private final String localPathArchives;
    private FileInputStream file;
    private Properties fileProperties;

    public SettingsArchivesTIBean() throws IOException {
        this.archivesSettingsLocal = "./res/svn.properties";
        this.localPathArchives = "C:\\FastPlan\\temp\\conf\\remote_svn.properties";
        this.fileName = "remote_svn.properties";
        this.mandatoryFields = new HashMap<>();
        //this.loadFileProperties();
    }

    public Map getMandatoryFields() {
        return mandatoryFields;
    }

//    public void setMandatoryFields(Map mandatoryFields) {
//        this.mandatoryFields = mandatoryFields;
//    }
    public String getUrlSvn() {
        urlSvn = fileProperties.getProperty("url");
        return urlSvn;
    }

//    public void setUrlSvn(String urlSvn) {
//        this.urlSvn = urlSvn;
//    }
    public String getTestCaseDir() {
        testCaseDir = fileProperties.getProperty("caminhoSVN");
        return testCaseDir;
    }

//    public void setTestCaseDir(String testCaseDir) {
//        this.testCaseDir = testCaseDir;
//    }
    public String getSystem() {
        system = fileProperties.getProperty("sistemas");
        return system;
    }

//    public void setSystem(String system) throws FileNotFoundException, IOException {
//        fileOut = new FileOutputStream(remotePath);
//        fileProperties.setProperty("sistemas", system.toString());
//        fileProperties.store(fileOut, null);
//    }
    public String getFaseCr() {
        faseCr = fileProperties.getProperty("faseCR");
        return faseCr;
    }
    
     public String getComplexidade() {
        complexidade = fileProperties.getProperty("complexidadeTS");
        return complexidade;
    }

//    public void setFaseCr(String faseCr) {
//        this.faseCr = faseCr;
//    }
    public String getLocalPath() {
        localPath = fileProperties.getProperty("pastaTemp");
        return localPath;
    }

//    public void setLocalPath(String localPath) {
//        this.localPath = localPath;
//    }
    public String getRemotePath() {
        return localPathArchives;
    }

//    public void setRemotePath(String remotePath) {
//        this.remotePath = remotePath;
//    }
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void loadFileProperties() throws FileNotFoundException, IOException {
        fileProperties = new Properties();
        file = new FileInputStream(localPathArchives);
        fileProperties.load(file);
        file.close();

    }

    public void loadFields() {

        mandatoryFields.put("CASO_TESTE", fileProperties.getProperty("CASO_TESTE"));
        mandatoryFields.put("FUNCIONALIDADE_SERVICO", fileProperties.getProperty("FUNCIONALIDADE_SERVICO"));
        mandatoryFields.put("SISTEMA_MASTER", fileProperties.getProperty("SISTEMA_MASTER"));
        mandatoryFields.put("SISTEMAS_ENVOLVIDOS", fileProperties.getProperty("SISTEMAS_ENVOLVIDOS"));
        mandatoryFields.put("FORNECEDOR", fileProperties.getProperty("FORNECEDOR"));
        mandatoryFields.put("CENARIO_TESTE", fileProperties.getProperty("CENARIO_TESTE"));
        mandatoryFields.put("TP_REQUISITO", fileProperties.getProperty("TP_REQUISITO"));
        mandatoryFields.put("REQUISITO", fileProperties.getProperty("REQUISITO"));
        mandatoryFields.put("CRIACAO_ALTERACAO", fileProperties.getProperty("CRIACAO_ALTERACAO"));
        mandatoryFields.put("CADEIA", fileProperties.getProperty("CADEIA"));
        mandatoryFields.put("SEGMENTO", fileProperties.getProperty("SEGMENTO"));
        mandatoryFields.put("CENARIO_INTEGRADO", fileProperties.getProperty("CENARIO_INTEGRADO"));
        mandatoryFields.put("CENARIO_AUTOMATIZAVEL", fileProperties.getProperty("CENARIO_AUTOMATIZAVEL"));
        mandatoryFields.put("TP_TYPE", fileProperties.getProperty("TP_TYPE"));
        mandatoryFields.put("TRG", fileProperties.getProperty("TRG"));
        mandatoryFields.put("DESCRICAO_CASO_TESTE", fileProperties.getProperty("DESCRICAO_CASO_TESTE"));
        mandatoryFields.put("QTD_SISTEMAS", fileProperties.getProperty("QTD_SISTEMAS"));
        mandatoryFields.put("PRODUTO", fileProperties.getProperty("PRODUTO"));
        mandatoryFields.put("SUBJECT", fileProperties.getProperty("SUBJECT"));

    }
}
