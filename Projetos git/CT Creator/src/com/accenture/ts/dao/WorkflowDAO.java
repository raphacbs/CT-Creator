/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.dao;

import com.accenture.bean.SVNPropertiesVOBean;
import com.accenture.util.FrameworkSvnManager;
import com.accenture.util.FunctiosDates;
import java.io.IOException;
import java.util.List;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;

/**
 *
 * @author raphael.da.silva
 */
public class WorkflowDAO extends FrameworkSvnManager{
    
    private SVNPropertiesVOBean properties;
    private String url;
    private String username;
    private String password;
    private SVNRepository repo;
    private SVNClientManager clientManager;
    private String pathFluxo;

    public WorkflowDAO() throws IOException, SVNException {
        properties = new SVNPropertiesVOBean();
        url = properties.getUrl()+properties.getDir()+"/fluxos";
        username = properties.getUser();
        password = properties.getPass();               
        repo =  getRepository(url, username, password);
        clientManager = createSVNClientManager(repo);
        pathFluxo = "C:\\FastPlan\\fluxos";
    } 
    
    public void donwloadFluxos() throws SVNException{       
        
        if(isWorkingCopy(pathFluxo)){
            updateWorkingCopy(clientManager, pathFluxo, SVNRevision.HEAD);
        }else{
            createWorkingCopy(clientManager, url,pathFluxo , SVNDepth.FILES);
        }
    }       
    
    public List<SVNDirEntry> getEntriesWorkflow() throws SVNException{   
        return getListEntries("/branches/DEV CT CREATOR/CTs/fluxos", repo);       
    }
    
    public void save() throws SVNException{
        addEntryWorkingCopy(clientManager, pathFluxo);
        commitChanged(clientManager, pathFluxo, true, "Fluxo salvo via CT Creator");
    }
    
    public boolean isWorkingCopy(String pathWC) throws SVNException{
        return isWorkingCopyRoot(pathWC);
    }
    
    public void deleteFile(String nameFile) throws SVNException{
        delete(clientManager, pathFluxo+"/"+nameFile, true);
        commitChanged(clientManager, pathFluxo, true, "Commit realizado pelo método deleteFile()");
    }
    
    public void lockFile(String nameFile) throws SVNException{
        lock(clientManager, pathFluxo+"/"+nameFile, true, "Arquivo bloqueado às "+FunctiosDates.getDateAcutualString());
    }
    
    public void unLockFile(String nameFile) throws SVNException{
        
        if(isLock(clientManager, pathFluxo+"/"+nameFile)){
            unLock(clientManager, pathFluxo+"/"+nameFile, true);
        }
        
    }
    
}
