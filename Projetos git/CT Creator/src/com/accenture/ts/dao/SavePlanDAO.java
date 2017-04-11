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
import static com.accenture.util.FrameworkSvnManager.createWorkingCopy;
import static com.accenture.util.FrameworkSvnManager.delete;
import static com.accenture.util.FrameworkSvnManager.getListEntries;
import static com.accenture.util.FrameworkSvnManager.isWorkingCopyRoot;
import static com.accenture.util.FrameworkSvnManager.lock;
import static com.accenture.util.FrameworkSvnManager.renameFile;
import static com.accenture.util.FrameworkSvnManager.unLock;
import static com.accenture.util.FrameworkSvnManager.updateWorkingCopy;
import com.accenture.util.FunctiosDates;
import com.accenture.util.ProjectSettings;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.ISVNWorkspaceMediator;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.ISVNAddParameters;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;

/**
 *
 * @author Oi_TSS
 */
public class SavePlanDAO extends FrameworkSvnManager {

    private static SVNPropertiesVOBean properties;
    private static String url;
    private static String username;
    private static String password;
    private static SVNRepository repo;
    private static SVNClientManager clientManager;

    public SavePlanDAO() {
        try {
            properties = SVNPropertiesVOBean.getInstance();//new SVNPropertiesVOBean();
            url = properties.getUrl() + ProjectSettings.URL_PLANO_SVN;
            username = properties.getUser();
            password = properties.getPass();
            repo = getRepository(url, username, password);
            clientManager = createSVNClientManager(repo);
        } catch (SVNException ex) {
            Logger.getLogger(SavePlanDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SavePlanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void importSave(String pathFile, String system) throws IOException, SVNException {
        properties = SVNPropertiesVOBean.getInstance();//new SVNPropertiesVOBean();
        url = properties.getUrl() ;
        username = properties.getUser();
        password = properties.getPass();
        repo = getRepository(url, username, password);
        url = properties.getUrl() + ProjectSettings.URL_PLANO_SVN + "/" + system;
        clientManager = createSVNClientManager(repo);
        File f = new File(pathFile);
        importDirectory(clientManager, f, url+ "/" + f.getName(), "PUBLICANDO PLANO PARA O REPOSITORIO", true);
    }

    public static void exportSave(String nameSave, String system) throws IOException, SVNException {
        properties = SVNPropertiesVOBean.getInstance();//new SVNPropertiesVOBean();
        url = properties.getUrl() + ProjectSettings.URL_PLANO_SVN + "/" + system+"/"+nameSave;
        username = properties.getUser();
        password = properties.getPass();
        repo = getRepository(url, username, password);
        clientManager = createSVNClientManager(repo);
        export(clientManager, url, ProjectSettings.PATH_FILE_SAVE, SVNDepth.FILES);
    }

    public static void delelePlan(String nameSave, String system) throws IOException, SVNException {

        properties = SVNPropertiesVOBean.getInstance();//new SVNPropertiesVOBean();
        url = properties.getUrl() + ProjectSettings.URL_PLANO_SVN + "/" + system;
        username = properties.getUser();
        password = properties.getPass();
        repo = getRepository(url, username, password);
        clientManager = createSVNClientManager(repo);
//        String filename = url+"/"+nameSave;
        ISVNEditor editor = repo.getCommitEditor("Excluindo arquivo antigo", null /*locks*/, true /*keepLocks*/, null /*mediator*/);
        editor.openRoot(-1);
        editor.deleteEntry(nameSave, SVNRevision.HEAD.getNumber());
        editor.closeDir();
        editor.closeEdit();

    }

    public void donwloadFiles(String system) throws SVNException {

        if (isWorkingCopy(ProjectSettings.PATH_FILE_SAVE_SVN + "/" + system)) {
            updateWorkingCopy(clientManager, ProjectSettings.PATH_FILE_SAVE_SVN + "/" + system, SVNRevision.HEAD);
        } else {
            createWorkingCopy(clientManager, url + "/" + system, ProjectSettings.PATH_FILE_SAVE_SVN + "/" + system, SVNDepth.FILES);
        }
    }

    public boolean isWorkingCopy(String pathWC) throws SVNException {
        return isWorkingCopyRoot(pathWC);
    }

    public List<SVNDirEntry> getEntries(String system) throws SVNException, IOException {
        return getListEntries(url + "/" + system, repo);
    }

    public void save(String system) throws SVNException {
        addEntryWorkingCopy(clientManager, ProjectSettings.PATH_FILE_SAVE_SVN + "/" + system);
        commitChanged(clientManager, ProjectSettings.PATH_FILE_SAVE_SVN + "/" + system, true, "Salvando plan via CT CREATOR");
    }

    public void deleteFile(List<String> nameFiles, String system) throws SVNException {

        for (int i = 0; i < nameFiles.size(); i++) {
            delete(clientManager, ProjectSettings.PATH_FILE_SAVE_SVN + "/" + system + "/" + nameFiles.get(i), true);
        }

        commitChanged(clientManager, ProjectSettings.PATH_FILE_SAVE_SVN + "/" + system, true, "Commit realizado pelo método deleteFile()");
    }

    public void lockFile(String nameFile, String system) throws SVNException {
        lock(clientManager, ProjectSettings.PATH_FILE_SAVE_SVN + "/" + system + "/" + nameFile, true, "Arquivo bloqueado às " + FunctiosDates.getDateAcutualString());
    }

    public void unLockFile(String nameFile, String system) throws SVNException {

        if (isLock(clientManager, ProjectSettings.PATH_FILE_SAVE_SVN + "/" + system + "/" + nameFile)) {
            unLock(clientManager, ProjectSettings.PATH_FILE_SAVE_SVN + "/" + system + "/" + nameFile, true);
        }
    }

    public boolean isLock(String nameFile, String system) throws SVNException {
        if (isLock(clientManager, ProjectSettings.PATH_FILE_SAVE_SVN + "/" + system + "/" + nameFile)) {
            return true;
        } else {
            return false;
        }
    }

    public String getUserLock(String nameFile, String system) throws SVNException {
        SVNStatus status = getStatus(clientManager, ProjectSettings.PATH_FILE_SAVE_SVN + "/" + system + "/" + nameFile);
        return status.getRemoteLock().getOwner();
    }

    public String getUsername() {
        return username;
    }

    public void renomearArquivo(String nomeAntigo, String nomeNovo, String sistema) throws Exception {
        renameFile(clientManager, ProjectSettings.PATH_FILE_SAVE_SVN + "/" + sistema + "/" + nomeAntigo, nomeNovo);
    }
    
    public static List<String> getListPlan(String system) throws SVNException, IOException{
        List<String> list = new ArrayList<String>();
         properties = SVNPropertiesVOBean.getInstance();//new SVNPropertiesVOBean();
           url = properties.getUrl();
            username = properties.getUser();
            password = properties.getPass();
            repo = getRepository(url, username, password);
         List<SVNDirEntry> entries =
                 getListEntries( ProjectSettings.URL_PLANO_SVN+"/" + system, repo);
         
           for (int i = 0; i < entries.size(); i++) {
               list.add(entries.get(i).getName().replace(".plan", ""));
           }
    
           return list;
    }

}
