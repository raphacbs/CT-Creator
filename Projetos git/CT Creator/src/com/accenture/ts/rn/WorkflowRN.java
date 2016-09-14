/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.rn;

import com.accenture.bean.WorkflowBean;
import com.accenture.ts.dao.WorkflowDAO;
import java.io.IOException;
import java.util.List;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author raphael.da.silva
 */
public class WorkflowRN {
    
    private final String PATH_FILE_FLUXO = "C:\\FastPlan\\fluxos";
    
    public WorkflowBean getWorkflowBean(String name) throws IOException, SVNException{
        WorkflowDAO workflowDAO = new WorkflowDAO();
        WorkflowBean workflowBean = new WorkflowBean();
        List<SVNDirEntry> entries =  workflowDAO.getEntriesWorkflow();
        
        for (SVNDirEntry entry : entries) {
            if(entry.getName().equalsIgnoreCase(name)){
                
            }
        }
        
        return workflowBean;
    }
    
    public WorkflowBean readFileFluxo(String name){
        WorkflowBean workflowBean = new WorkflowBean();
        
        
        
        return workflowBean;
    }
    
}
