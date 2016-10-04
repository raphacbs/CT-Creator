/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.dao;

import com.accenture.bean.SVNPropertiesVOBean;
import com.accenture.util.FrameworkSvnManager;
import com.accenture.util.FunctiosDates;
import com.accenture.util.ProjectSettings;
import java.io.IOException;
import java.util.List;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;

/**
 *
 * @author raphael.da.silva
 */
public class FlowDAO extends FrameworkSvnManager{
    
    private SVNPropertiesVOBean properties;
    private String url;
    private String username;
    private String password;
    private SVNRepository repo;
    private SVNClientManager clientManager;
    

    public FlowDAO() throws IOException, SVNException {
        properties = new SVNPropertiesVOBean();
        url = properties.getUrl()+properties.getDir()+"/fluxos";
        username = properties.getUser();
        password = properties.getPass();               
        repo =  getRepository(url, username, password);
        clientManager = createSVNClientManager(repo);
        
    } 
    
    public void donwloadFluxos() throws SVNException{       
        
        if(isWorkingCopy(ProjectSettings.PATH_FILE_FLUXO)){
            updateWorkingCopy(clientManager, ProjectSettings.PATH_FILE_FLUXO, SVNRevision.HEAD);
        }else{
            createWorkingCopy(clientManager, url,ProjectSettings.PATH_FILE_FLUXO , SVNDepth.FILES);
        }
    }       
    
    public List<SVNDirEntry> getEntriesWorkflow() throws SVNException, IOException{   
        return getListEntries(new SVNPropertiesVOBean().getDir()+"fluxos", repo);       
    }
    
    public void save() throws SVNException{
        addEntryWorkingCopy(clientManager, ProjectSettings.PATH_FILE_FLUXO);
        commitChanged(clientManager, ProjectSettings.PATH_FILE_FLUXO, true, "Fluxo salvo via CT Creator");
    }
    
    public boolean isWorkingCopy(String pathWC) throws SVNException{
        return isWorkingCopyRoot(pathWC);
    }
    
    public void deleteFile(List<String> nameFiles) throws SVNException{
        
        for(int i = 0; i < nameFiles.size(); i++){
             delete(clientManager, ProjectSettings.PATH_FILE_FLUXO+"/"+nameFiles.get(i), true);
        }
       
        commitChanged(clientManager, ProjectSettings.PATH_FILE_FLUXO, true, "Commit realizado pelo método deleteFile()");
    }
    
    public void lockFile(String nameFile) throws SVNException{
        lock(clientManager, ProjectSettings.PATH_FILE_FLUXO+"/"+nameFile, true, "Arquivo bloqueado às "+FunctiosDates.getDateAcutualString());
    }
    
    public void unLockFile(String nameFile) throws SVNException{
        
        if(isLock(clientManager, ProjectSettings.PATH_FILE_FLUXO+"/"+nameFile)){
            unLock(clientManager, ProjectSettings.PATH_FILE_FLUXO+"/"+nameFile, true);
        }        
    }
    
    public boolean isLock(String nameFile) throws SVNException{
        if(isLock(clientManager, ProjectSettings.PATH_FILE_FLUXO+"/"+nameFile)){
            return true;
        }else{
            return false;
        }
    }
    
    public String getUserLock(String nameFile) throws SVNException{
        SVNStatus status = getStatus(clientManager, ProjectSettings.PATH_FILE_FLUXO+"/"+nameFile);
        return status.getRemoteLock().getOwner();
    }

    public String getUsername() {
        return username;
    }

    
    
        
}
