/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.bean;

import com.accenture.util.Criptografia;
import com.accenture.util.ProjectSettings;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author raphael.da.silva
 */
public class SVNPropertiesVOBean {

    private String user;
    private String pass;
    private String url;
    private String dir;
    private String systems;
    private Properties fileProperties;
    private FileInputStream file;

    private FileOutputStream fileOut;
    private String folderTemplocal;
    private String faseCR;
    private String testPhaseTS;
    private boolean first;
    private double version;
    private String complexidade;

    private String urlComponents;
    private String urlScripts;

    private String usersAuto;
    private static SVNPropertiesVOBean sVNPropertiesVOBean = null;
    private File fileSVNProperties;

    private String hostBD;
    private String portBD;
    private String databaseNameBD;
    private String userBD;
    private String passwordBD;

    public SVNPropertiesVOBean() throws IOException {
        loadFileProperties();

    }

    public static SVNPropertiesVOBean getInstance() throws IOException {
        if (sVNPropertiesVOBean == null) {
            sVNPropertiesVOBean = new SVNPropertiesVOBean();
        }

        return sVNPropertiesVOBean;
    }

    public String getHostBD() {
        return hostBD = fileProperties.getProperty("host_bd");
    }

    public String getPortBD() {
        return portBD = fileProperties.getProperty("port_bd");
    }

    public String getUserBD() {
        return userBD = fileProperties.getProperty("user_bd");
    }

    public void setUserBD(String userDb) throws FileNotFoundException, IOException {
        fileOut = new FileOutputStream(fileSVNProperties);
        fileProperties.setProperty("password_bd", userDb);
        fileProperties.store(fileOut, null);
    }

    public String getPasswordBD() {
        passwordBD = fileProperties.getProperty("password_bd");
        return Criptografia.Decripto(passwordBD);
    }

    public void setPasswordBD(String passDb) throws FileNotFoundException, IOException {
        fileOut = new FileOutputStream(fileSVNProperties);
        fileProperties.setProperty("password_bd", Criptografia.Cripto(passDb));
        fileProperties.store(fileOut, null);
    }

    public String getDatabaseNameBD() {
        return databaseNameBD = fileProperties.getProperty("databaseName_bd");
    }

    public double getVersion() {
        return this.version = Double.parseDouble(fileProperties.getProperty("version"));
    }

    public void setVersion(double version) throws FileNotFoundException, IOException {
        fileOut = new FileOutputStream(fileSVNProperties);
        fileProperties.setProperty("version", version + "");
        fileProperties.store(fileOut, null);
    }

    public String getTestPhaseTS() {
        return this.testPhaseTS = fileProperties.getProperty("testPhase");
    }

    public void setTestPhaseTS(String testPhaseTS) throws FileNotFoundException, IOException {
        fileOut = new FileOutputStream(fileSVNProperties);
        fileProperties.setProperty("testPhase", faseCR);
        fileProperties.store(fileOut, null);
    }

    public String getFaseCR() throws IOException {
        return this.faseCR = fileProperties.getProperty("faseCR");

    }

    public void setFaseCR(String faseCR) throws FileNotFoundException, IOException {
        fileOut = new FileOutputStream(fileSVNProperties);
        fileProperties.setProperty("faseCR", faseCR);
        fileProperties.store(fileOut, null);

    }

    public String getUser() {

        this.user = fileProperties.getProperty("usuarioSvn");
        return this.user;
    }

    public void setUser(String user) throws IOException {
        fileOut = new FileOutputStream(fileSVNProperties);
        fileProperties.setProperty("usuarioSvn", user);
        fileProperties.store(fileOut, null);
    }

    public String getPass() {
        this.pass = fileProperties.getProperty("senhaSvn");
        return Criptografia.Decripto(this.pass);
    }

    public void setPass(String pass) throws IOException {
        fileOut = new FileOutputStream(fileSVNProperties);
        fileProperties.setProperty("senhaSvn", Criptografia.Cripto(pass));
        fileProperties.store(fileOut, null);
    }

    public String getUrl() {
        this.url = fileProperties.getProperty("url");
        return this.url;
    }

    public void setUrl(String url) throws IOException {
        fileOut = new FileOutputStream(fileSVNProperties);
        fileProperties.setProperty("url", url);
        fileProperties.store(fileOut, null);
    }

    public String getDir(String fase) {
        this.dir = fileProperties.getProperty("caminhoSVN");
        return this.dir + fase + "/";
    }

    public void setDir(String dir) throws IOException {
        fileOut = new FileOutputStream(fileSVNProperties);
        fileProperties.setProperty("caminhoSVN", dir);
        fileProperties.store(fileOut, null);
    }

    public String getFolderTemplocal() {
        this.folderTemplocal = fileProperties.getProperty("pastaTemp");
        return this.folderTemplocal;
    }

    public void setFolderTemplocal(String folderTemplocal) throws IOException {
        fileOut = new FileOutputStream(fileSVNProperties);
        fileProperties.setProperty("pastaTemp", folderTemplocal);
        fileProperties.store(fileOut, null);
    }

    public String getSystems() {
        this.systems = fileProperties.getProperty("sistemas");
        return this.systems;
    }

    public void setSystems(String systems) throws IOException {
        fileOut = new FileOutputStream(fileSVNProperties);
        fileProperties.setProperty("sistemas", systems.toString());
        fileProperties.store(fileOut, null);
        fileOut.close();
    }

    public String getComplexidade() {
        this.complexidade = fileProperties.getProperty("complexidadeTS");
        return this.complexidade;
    }

    public void setComplexidade(String complexidade) throws IOException {
        fileOut = new FileOutputStream(fileSVNProperties);
        fileProperties.setProperty("complexidadeTS", complexidade.toString());
        fileProperties.store(fileOut, null);
        fileOut.close();
    }

    public String getUrlComponents() {
        this.urlComponents = fileProperties.getProperty("urlComponents");
        return this.urlComponents;
    }

    public void setUrlComponents(String urlComponents) throws FileNotFoundException, IOException {
        fileOut = new FileOutputStream(fileSVNProperties);
        fileProperties.setProperty("urlComponents", urlComponents.toString());
        fileProperties.store(fileOut, null);
        fileOut.close();
    }

    public String getUrlScritps() {
        this.urlScripts = fileProperties.getProperty("urlScripts");
        return this.urlScripts;
    }

    public void setUrlScripts(String urlScripts) throws FileNotFoundException, IOException {
        fileOut = new FileOutputStream(fileSVNProperties);
        fileProperties.setProperty("urlScripts", urlScripts.toString());
        fileProperties.store(fileOut, null);
        fileOut.close();
    }

    public String getUsersAuto() {
        this.usersAuto = fileProperties.getProperty("usuariosAuto");
        return this.usersAuto;
    }

    public void setUsersAuto(String urlScripts) throws FileNotFoundException, IOException {
        fileOut = new FileOutputStream(fileSVNProperties);
        fileProperties.setProperty("usuariosAuto", urlScripts.toString());
        fileProperties.store(fileOut, null);
        fileOut.close();
    }

    public void loadFileProperties() throws FileNotFoundException, IOException {
        fileProperties = new Properties();
        if (getFileUpdateSVN().exists()) {
            fileSVNProperties = new File(ProjectSettings.PATH_FILE_SVN_UPDATE_PROPERTIES);
            file = new FileInputStream(fileSVNProperties);

        } else {
            fileSVNProperties = new File(ProjectSettings.PATH_FILE_SVN_PROPERTIES);
            file = new FileInputStream(fileSVNProperties);

        }

        fileProperties.load(file);

        file.close();

    }

//    public void loadFileProperties(String pathFile) throws FileNotFoundException, IOException {
//        fileProperties = new Properties();
//        file = new FileInputStream(pathFile);
//        fileProperties.load(file);
//        file.close();
//
//    }
    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    private File getFileUpdateSVN() {
        return new File(ProjectSettings.PATH_FILE_SVN_UPDATE_PROPERTIES);

    }

}
