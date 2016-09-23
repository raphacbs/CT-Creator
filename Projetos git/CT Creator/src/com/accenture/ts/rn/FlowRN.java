/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.rn;


import com.accenture.bean.FlowBean;
import com.accenture.ts.dao.FlowDAO;
import com.accenture.util.FunctiosDates;
import com.accenture.util.ProjectSettings;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author raphael.da.silva
 */
public class FlowRN {
    
    
    private Properties fileProperties = null;
    private FileInputStream file;
    private FlowDAO workflowDAO;

    public FlowRN() throws IOException, SVNException {
       workflowDAO = new FlowDAO();
    }
    
    
    
    private FlowBean getFlowBean(String name) throws IOException, SVNException {
        
        FlowBean flowBean = new FlowBean();
        
        if (!name.contains(".properties")) {
            name += ".properties";
        }

        loadFileProperties(name);
        flowBean.setName(fileProperties.getProperty(ProjectSettings.PROPERTY_NAME));
        flowBean.setDescription(fileProperties.getProperty(ProjectSettings.PROPERTY_DESCRIPTION));
        flowBean.setId(fileProperties.getProperty(ProjectSettings.PROPERTY_ID));
        flowBean.setSystem(fileProperties.getProperty(ProjectSettings.PROPERTY_SYSTEM));
        flowBean.setRegisterDate(FunctiosDates.stringToDate(fileProperties.getProperty(ProjectSettings.PROPERTY_REGISTER_DATE)));
        flowBean.setTestCases(Arrays.asList(fileProperties.getProperty(ProjectSettings.PROPERTY_TEST_CASES).split(ProjectSettings.DELIDELIMITER_COMMA)));

        return flowBean;
    }
    
    public FlowBean readFileFluxo(String name){
        FlowBean workflowBean = new FlowBean();
        
        
        
        return workflowBean;
    }
    
    
    private void loadFileProperties(String fileName) throws FileNotFoundException, IOException{
        fileProperties = new Properties();
        file = new FileInputStream(ProjectSettings.PATH_FILE_FLUXO+"/"+fileName);
        fileProperties.load(file);
        file.close();
        
    }
    
    public FlowBean getFile(String fileName) throws SVNException, IOException{
        //atualiza pasta com o fluxo
        workflowDAO.donwloadFluxos();
//        //verifica se o arquivo está bloqueado
//        if(workflowDAO.isLock(fileName)){
//            return null;
//        }else{
//            workflowDAO.lockFile(fileName);
//        }
        
        return getFlowBean(fileName);
        
    }
    
    public void saveFile(String fileName, FlowBean workflowBean ) throws SVNException, IOException{
        
        //atualiza pasta com o fluxo
        workflowDAO.donwloadFluxos();
        
        if(fileName == null){
            String id = createFile(workflowBean);
            workflowDAO.save();
            workflowDAO.lockFile(id+".properties");
        }else{
            workflowDAO.unLockFile(fileName);
            workflowDAO.save();
            workflowDAO.lockFile(fileName);
        }   
    }
    
    private String  createFile(FlowBean workflowBean) throws FileNotFoundException, IOException, SVNException{
        String id = generateId();
        File newFile = new File(ProjectSettings.PATH_FILE_FLUXO+"/"+id+".properties");
        FileOutputStream fileOut = new FileOutputStream(newFile);
        
        loadFileProperties(id+".properties");
              
        fileProperties.put(ProjectSettings.PROPERTY_ID, id);
        fileProperties.put(ProjectSettings.PROPERTY_DESCRIPTION, workflowBean.getDescription());
        fileProperties.put(ProjectSettings.PROPERTY_NAME, workflowBean.getName());
        fileProperties.put(ProjectSettings.PROPERTY_REGISTER_DATE, FunctiosDates.dateToString(workflowBean.getRegisterDate(),"dd/MM/yyyy HH:mm:ss"));
        fileProperties.put(ProjectSettings.PROPERTY_SYSTEM, workflowBean.getSystem());
        String cts = "";
        cts = workflowBean.getTestCases().stream().map((testCase) -> testCase +";").reduce(cts, String::concat);
        fileProperties.put(ProjectSettings.PROPERTY_TEST_CASES, cts);
        fileProperties.store(fileOut, null);
        return id;
    }
    
    public void lockFile(String nameFile) throws SVNException{
        workflowDAO.lockFile(nameFile);
    }
    
    public void unLockFile(String nameFile) throws SVNException{
        workflowDAO.unLockFile(nameFile);
    }
    
    private String generateId() throws SVNException, IOException{
         List<SVNDirEntry> entries = workflowDAO.getEntriesWorkflow();
         Integer biggerID = 1;
          for (int i = 0; i < entries.size(); i++) {
            if (biggerID <= Integer.parseInt(entries.get(i).getName().replace(".properties", ""))) {
                biggerID = Integer.parseInt((entries.get(i).getName().replace(".properties", "")));
                biggerID++;
            }
            
        }
          
        return biggerID.toString();
        
    }
    
    public List<SVNDirEntry> getEntries() throws SVNException, IOException{
        return workflowDAO.getEntriesWorkflow();
    }
        
    
}
