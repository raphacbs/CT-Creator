/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.bean;

import com.accenture.util.ProjectSettings;
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
    
    
    
    public SVNPropertiesVOBean() throws IOException {
       loadFileProperties();
        
    }
    
    public double getVersion() {
        return this.version = Double.parseDouble(fileProperties.getProperty("version"));
    }

    public void setVersion(double version) throws FileNotFoundException, IOException {
        fileOut = new FileOutputStream(ProjectSettings.PATH_FILE_SVN_PROPERTIES);
        fileProperties.setProperty("version", version+"");
        fileProperties.store(fileOut, null);
    }

    public String getTestPhaseTS() {
        return this.testPhaseTS = fileProperties.getProperty("testPhase");
    }

    public void setTestPhaseTS(String testPhaseTS) throws FileNotFoundException, IOException {
        fileOut = new FileOutputStream(ProjectSettings.PATH_FILE_SVN_PROPERTIES);
        fileProperties.setProperty("testPhase", faseCR);
        fileProperties.store(fileOut, null);
    }
    
    

    public String getFaseCR() throws IOException {
        return this.faseCR = fileProperties.getProperty("faseCR");
        
    }

    public void setFaseCR(String faseCR) throws FileNotFoundException, IOException {
        fileOut = new FileOutputStream(ProjectSettings.PATH_FILE_SVN_PROPERTIES);
        fileProperties.setProperty("faseCR", faseCR);
        fileProperties.store(fileOut, null);
       
    }
    
    public String getUser() {
        
        this.user = fileProperties.getProperty("usuarioSvn");
        return this.user;
    }

    public void setUser(String user) throws IOException {
        fileOut = new FileOutputStream(ProjectSettings.PATH_FILE_SVN_PROPERTIES);
        fileProperties.setProperty("usuarioSvn", user);
        fileProperties.store(fileOut, null);
    }

    public String getPass() {
        this.pass = fileProperties.getProperty("senhaSvn");
        return this.pass;
    }

    public void setPass(String pass) throws IOException {
        fileOut = new FileOutputStream(ProjectSettings.PATH_FILE_SVN_PROPERTIES);
        fileProperties.setProperty("senhaSvn", pass);
        fileProperties.store(fileOut, null);        
    }

    public String getUrl() {
        this.url = fileProperties.getProperty("url");
        return this.url;
    }

    public void setUrl(String url) throws IOException {
        fileOut = new FileOutputStream(ProjectSettings.PATH_FILE_SVN_PROPERTIES);
        fileProperties.setProperty("url", url);
        fileProperties.store(fileOut, null);
    }

    public String getDir() {
        this.dir = fileProperties.getProperty("caminhoSVN");
        return this.dir;
    }

    public void setDir(String dir) throws IOException {
        fileOut = new FileOutputStream(ProjectSettings.PATH_FILE_SVN_PROPERTIES);
       fileProperties.setProperty("caminhoSVN", dir);
       fileProperties.store(fileOut, null);
    }

    public String getFolderTemplocal() {
        this.folderTemplocal = fileProperties.getProperty("pastaTemp");
        return this.folderTemplocal;
    }

    public void setFolderTemplocal(String folderTemplocal) throws  IOException {
       fileOut = new FileOutputStream(ProjectSettings.PATH_FILE_SVN_PROPERTIES);
       fileProperties.setProperty("pastaTemp", folderTemplocal);
       fileProperties.store(fileOut, null);
    }
    

    public String getSystems() {
        this.systems = fileProperties.getProperty("sistemas");        
        return this.systems;
    }

    public void setSystems(String systems) throws IOException {
        fileOut = new FileOutputStream(ProjectSettings.PATH_FILE_SVN_PROPERTIES);
       fileProperties.setProperty("sistemas",  systems.toString());
       fileProperties.store(fileOut, null);
        fileOut.close();
    }
    
    public String getComplexidade(){
        this.complexidade = fileProperties.getProperty("complexidadeTS");
        return this.complexidade;
    }
    
     public void setComplexidade(String complexidade) throws IOException {
       fileOut = new FileOutputStream(ProjectSettings.PATH_FILE_SVN_PROPERTIES);
       fileProperties.setProperty("complexidadeTS",  complexidade.toString());
       fileProperties.store(fileOut, null);
       fileOut.close();
    }
     
     public String getUrlComponents(){
         this.urlComponents = fileProperties.getProperty("urlComponents");
        return this.urlComponents;
     }
     
     public void setUrlComponents(String urlComponents) throws FileNotFoundException, IOException{
       fileOut = new FileOutputStream(ProjectSettings.PATH_FILE_SVN_PROPERTIES);
       fileProperties.setProperty("urlComponents",  urlComponents.toString());
       fileProperties.store(fileOut, null);
       fileOut.close();
     }
     
     
      public String getUrlScritps(){
         this.urlComponents = fileProperties.getProperty("urlScripts");
        return this.urlComponents;
     }
     
     public void setUrlScripts(String urlScripts) throws FileNotFoundException, IOException{
       fileOut = new FileOutputStream(ProjectSettings.PATH_FILE_SVN_PROPERTIES);
       fileProperties.setProperty("urlScripts",  urlScripts.toString());
       fileProperties.store(fileOut, null);
       fileOut.close();
     }
      public String getUsersAuto(){
         this.urlComponents = fileProperties.getProperty("usuariosAuto");
        return this.urlComponents;
     }
     
     public void setUsersAuto(String urlScripts) throws FileNotFoundException, IOException{
       fileOut = new FileOutputStream(ProjectSettings.PATH_FILE_SVN_PROPERTIES);
       fileProperties.setProperty("usuariosAuto",  urlScripts.toString());
       fileProperties.store(fileOut, null);
       fileOut.close();
     }
    
    public void loadFileProperties() throws FileNotFoundException, IOException{
        fileProperties = new Properties();
        file = new FileInputStream(ProjectSettings.PATH_FILE_SVN_PROPERTIES);
        fileProperties.load(file);
        file.close();
        
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }
    
    
    
}
