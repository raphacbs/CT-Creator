/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.rn;

import com.accenture.ts.dao.SavePlanDAO;
import com.accenture.util.ProjectSettings;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tmatesoft.svn.core.SVNErrorCode;
import org.tmatesoft.svn.core.SVNErrorMessage;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author Oi_TSS
 */
public class SavePlanRN {
    
    public final static int PLAN_ERRO_EXIST = 1;
    public final static int PLAN_ERRO_ARQUIVO = 2;
    public final static int PLAN_ERRO_ = 2;
    
    
    public int publicarPlano(String saveName, String system){
        int result = 0 ;
        try{
            SavePlanDAO.importSave(saveName, system);
        }catch(SVNException e)   {
            SVNErrorMessage err = e.getErrorMessage();
          
            SVNErrorCode errCode = err.getErrorCode();
            
            if(err.getErrorCode().equals(SVNErrorCode.ENTRY_EXISTS)){
                try {
                    File save = new File(saveName);
                    SavePlanDAO.delelePlan(save.getName(),system);
                    SavePlanDAO.importSave(saveName, system);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SVNException ex) {
                     ex.printStackTrace();
                }
            }
                
        } catch (IOException ex) {
            result = PLAN_ERRO_ARQUIVO;
        }
        
        return result;
   
    }    

    public List<String> recuperarPlano(String system) throws IOException, SVNException{
        
            return SavePlanDAO.getListPlan(system);
    }
    
    public void download(String nameSave, String system) throws IOException, SVNException{
        if(!nameSave.contains(".plan")){
            nameSave = nameSave +".plan";
        }
        SavePlanDAO.exportSave(nameSave, system);
    }
    
   
}
