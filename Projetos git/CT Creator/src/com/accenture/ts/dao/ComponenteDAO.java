/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.dao;

import com.accenture.bean.SVNPropertiesVOBean;
import com.accenture.util.FrameworkSvnManager;
import static com.accenture.util.FrameworkSvnManager.addEntryWorkingCopy;
import static com.accenture.util.FrameworkSvnManager.commitChanged;
import static com.accenture.util.FrameworkSvnManager.createSVNClientManager;
import static com.accenture.util.FrameworkSvnManager.createWorkingCopy;
import static com.accenture.util.FrameworkSvnManager.delete;
import static com.accenture.util.FrameworkSvnManager.getListEntries;
import static com.accenture.util.FrameworkSvnManager.getRepository;
import static com.accenture.util.FrameworkSvnManager.isWorkingCopyRoot;
import static com.accenture.util.FrameworkSvnManager.lock;
import static com.accenture.util.FrameworkSvnManager.unLock;
import static com.accenture.util.FrameworkSvnManager.updateWorkingCopy;
import com.accenture.util.FunctiosDates;
import com.accenture.util.ProjectSettings;
import java.io.IOException;
import java.util.List;
import org.codehaus.groovy.tools.shell.Main;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;

/**
 *
 * @author Oi_TSS
 */
public class ComponenteDAO extends FrameworkSvnManager{
    
    private SVNPropertiesVOBean properties;
    private String url;
    private String username;
    private String password;
    private SVNRepository repo;
    private SVNClientManager clientManager;
    
    
    public ComponenteDAO() throws IOException, SVNException{
         properties = new SVNPropertiesVOBean();
        url = properties.getUrl()+ properties.getUrlComponents();
        username = properties.getUser();
        password = properties.getPass();               
        repo =  getRepository(url, username, password);
        clientManager = createSVNClientManager(repo);
    }
            
    
    public void donwloadFiles(String system) throws SVNException{       
        
        if(isWorkingCopy(ProjectSettings.PATH_FILE_COMPONENT+"/"+system)){
            updateWorkingCopy(clientManager, ProjectSettings.PATH_FILE_COMPONENT+"/"+system, SVNRevision.HEAD);
        }else{
            createWorkingCopy(clientManager, url+"/"+system,ProjectSettings.PATH_FILE_COMPONENT+"/"+system , SVNDepth.FILES);
        }
    }       
    
     public boolean isWorkingCopy(String pathWC) throws SVNException{
        return isWorkingCopyRoot(pathWC);
    }
     
    public List<SVNDirEntry> getEntries(String system) throws SVNException, IOException{   
        return getListEntries(new SVNPropertiesVOBean().getUrlComponents()+"/"+system, repo);       
    }
    
    public void save(String system) throws SVNException{
        addEntryWorkingCopy(clientManager, ProjectSettings.PATH_FILE_COMPONENT+"/"+system);
        commitChanged(clientManager, ProjectSettings.PATH_FILE_COMPONENT+"/"+system, true, "Salvando componente via CT CREATOR");
    }
    
     public void deleteFile(List<String> nameFiles, String system) throws SVNException{
        
        for(int i = 0; i < nameFiles.size(); i++){
             delete(clientManager, ProjectSettings.PATH_FILE_COMPONENT+"/"+system+"/"+nameFiles.get(i), true);
        }
       
        commitChanged(clientManager, ProjectSettings.PATH_FILE_COMPONENT+"/"+system, true, "Commit realizado pelo método deleteFile()");
    }
    
    public void lockFile(String nameFile, String system) throws SVNException{
        lock(clientManager, ProjectSettings.PATH_FILE_COMPONENT+"/"+system+"/"+nameFile, true, "Arquivo bloqueado às "+FunctiosDates.getDateAcutualString());
    }
    
    public void unLockFile(String nameFile, String system) throws SVNException{
        
        if(isLock(clientManager, ProjectSettings.PATH_FILE_COMPONENT+"/"+system+"/"+nameFile)){
            unLock(clientManager, ProjectSettings.PATH_FILE_COMPONENT+"/"+system+"/"+nameFile, true);
        }        
    }
    
    public boolean isLock(String nameFile, String system) throws SVNException{
        if(isLock(clientManager, ProjectSettings.PATH_FILE_COMPONENT+"/"+system+"/"+nameFile)){
            return true;
        }else{
            return false;
        }
    }
    
    public String getUserLock(String nameFile, String system) throws SVNException{
        SVNStatus status = getStatus(clientManager, ProjectSettings.PATH_FILE_COMPONENT+"/"+system+"/"+nameFile);
        return status.getRemoteLock().getOwner();
    }

    public String getUsername() {
        return username;
    }
    
    
    public void renomearArquivo(String nomeAntigo, String nomeNovo, String sistema)throws  Exception{
        renameFile(clientManager, ProjectSettings.PATH_FILE_COMPONENT+"/"+sistema+"/"+nomeAntigo, nomeNovo);
    }
    
            
    
}
