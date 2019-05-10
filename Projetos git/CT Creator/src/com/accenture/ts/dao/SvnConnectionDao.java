/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.dao;

import com.accenture.bean.SvnConnectionBean;
import com.accenture.bean.TestCaseTSPropertiesBean;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLock;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCopyClient;
import org.tmatesoft.svn.core.wc.SVNCopySource;
import org.tmatesoft.svn.core.wc.SVNMoveClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;
import org.tmatesoft.svn.core.wc2.SvnRemoteDelete;
import org.tmatesoft.svn.core.wc2.SvnTarget;

/**
 *
 * @author brucilin.de.gouveia
 */
public class SvnConnectionDao {

    private SvnConnectionBean connection;
    private String systemFolder;
    private Collection dirEntries;
    private SVNClientManager ourClientManager;
    private SVNURL urlFileSvn;

    public SvnConnectionDao(String fase) throws SVNException, IOException {

        this.connection = new SvnConnectionBean(fase);
        this.connection.toConnect();
        this.systemFolder = connection.getSvnProperties().getDir(fase) ;
        this.ourClientManager = SVNClientManager.newInstance(null, connection.getRepository().getAuthenticationManager());

    }

    public SvnConnectionDao() {
    }
    
    

    public SVNURL getUrlFileSvn() {
        return urlFileSvn;
    }

    public void setUrlFileSvn(SVNURL urlFileSvn) {
        this.urlFileSvn = urlFileSvn;
    }

    public void checkOutEmpytFolder(String systemFolder, String fase) throws SVNException {

        String url = connection.getSvnProperties().getUrl() + connection.getSvnProperties().getDir(fase) + systemFolder;
        SVNURL urlSvn = SVNURL.parseURIDecoded(url);
        File f = new File(connection.getSvnProperties().getFolderTemplocal() + systemFolder);
        SVNClientManager ourClientManager = SVNClientManager.newInstance(null, connection.getRepository().getAuthenticationManager());

        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
        updateClient.setIgnoreExternals(false);
        updateClient.doCheckout(urlSvn, f, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.EMPTY, true);

    }
    //INICIO CR 
    public void checkOutEmpytFolder(String systemFolder, int hasdCode, String fase) throws SVNException {

        String url = connection.getSvnProperties().getUrl() + connection.getSvnProperties().getDir(fase) + systemFolder;
        SVNURL urlSvn = SVNURL.parseURIDecoded(url);
        File f = new File(connection.getSvnProperties().getFolderTemplocal() + hasdCode +"\\" + systemFolder);
        SVNClientManager ourClientManager = SVNClientManager.newInstance(null, connection.getRepository().getAuthenticationManager());

        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
        updateClient.setIgnoreExternals(false);
        updateClient.doCheckout(urlSvn, f, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.EMPTY, true);

    }
    //FIM CR
    public void updateFolder() {

    }

    public void commitFileOrFolder(String systemFolder, String commitMessage) throws SVNException {
        String dir = connection.getSvnProperties().getFolderTemplocal() + systemFolder;
//        unLonckFileOrFolder(getUrlFileSvn());
        File f = new File(dir);
        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
        updateClient.setIgnoreExternals(false);
        ourClientManager.getCommitClient().doCommit(new File[]{f}, true, commitMessage, true, true);
//        ourClientManager.getCommitClient().doCommit(new File[]{f}, true, commitMessage, null, null, true, true, SVNDepth.EMPTY);

    }

    public void exportFileOrFolder(String fileNamme, String dirFile, String systemFolder, String fase) throws SVNException {
        String url = connection.getSvnProperties().getUrl() + connection.getSvnProperties().getDir(fase) + systemFolder + "/" + fileNamme;
        SVNURL urlSvn = SVNURL.parseURIDecoded(url);
        File f = new File(dirFile + "\\" + systemFolder + "\\" + fileNamme);
        SVNClientManager ourClientManager = SVNClientManager.newInstance(null, connection.getRepository().getAuthenticationManager());
        System.out.println("URL - SVN : "+urlSvn.toString());
        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
        updateClient.setIgnoreExternals(false);
//      lockFileOrFolder(urlSvn);
        setUrlFileSvn(urlSvn);
        updateClient.doExport(urlSvn, f, SVNRevision.HEAD, SVNRevision.HEAD, null, true, SVNDepth.FILES);

    }
    
    //INICIO CR PERMITIR TRABALHAR COM VARIAS TELAS AO MESMO TEMPO 
     public void exportFileOrFolder(String fileNamme, String dirFile, String systemFolder, int hashCode, String fase) throws SVNException {
        String url = connection.getSvnProperties().getUrl() + connection.getSvnProperties().getDir(fase) + systemFolder + "/" + fileNamme;
        SVNURL urlSvn = SVNURL.parseURIDecoded(url);
        File f = new File(dirFile + "\\"+ hashCode + "\\" + systemFolder + "\\" + fileNamme);
        SVNClientManager ourClientManager = SVNClientManager.newInstance(null, connection.getRepository().getAuthenticationManager());
        System.out.println("URL - SVN : "+urlSvn.toString());
        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
        updateClient.setIgnoreExternals(false);
//      lockFileOrFolder(urlSvn);
        setUrlFileSvn(urlSvn);
        updateClient.doExport(urlSvn, f, SVNRevision.HEAD, SVNRevision.HEAD, null, true, SVNDepth.FILES);

    }
    //FIM CR

    public void copyFileToLocal(String fileNamme, String dirFile, String systemFolder, String fase) throws SVNException {
        String url = connection.getSvnProperties().getUrl() + connection.getSvnProperties().getDir(fase) + systemFolder + "/" + fileNamme;
        SVNURL urlSvn = SVNURL.parseURIDecoded(url);
        SVNCopySource csrc = new SVNCopySource(SVNRevision.HEAD, SVNRevision.HEAD, urlSvn);
        File f = new File(dirFile + "\\" + systemFolder + "\\" + fileNamme);
        SVNCopyClient svncc = ourClientManager.getCopyClient();
        svncc.doCopy(new SVNCopySource[]{csrc}, f, false, true, true);

    }

    public void addFileOrFolder(String systemFolder, String commitMessage) throws SVNException {
        File f = new File(connection.getSvnProperties().getFolderTemplocal() + systemFolder);
        ourClientManager.getWCClient().doCleanup(f);
        SVNUpdateClient clientUpdate = ourClientManager.getUpdateClient();
        clientUpdate.doUpdate(f, SVNRevision.PREVIOUS, SVNDepth.EMPTY, true, true);
        ourClientManager.getWCClient().doAdd(f, true, true, true, SVNDepth.FILES, true, true, true);

        ourClientManager.getCommitClient().doCommit(new File[]{f}, true, commitMessage, true, true);

    }
    
    //CR PERMITI VARIAS TELAS
    public void addFileOrFolder(String systemFolder, String commitMessage, int hashCode) throws SVNException {
        File f = new File(connection.getSvnProperties().getFolderTemplocal()+hashCode+"\\" + systemFolder);
        ourClientManager.getWCClient().doCleanup(f);
        SVNUpdateClient clientUpdate = ourClientManager.getUpdateClient();
        clientUpdate.doUpdate(f, SVNRevision.PREVIOUS, SVNDepth.EMPTY, true, true);
        ourClientManager.getWCClient().doAdd(f, true, true, true, SVNDepth.FILES, true, true, true);

        ourClientManager.getCommitClient().doCommit(new File[]{f}, true, commitMessage, true, true);

    }
    //CR
    
    public void addFileOrFolderSave(String systemFolder, String commitMessage) throws SVNException {
        File f = new File(connection.getSvnProperties().getFolderTemplocal() + systemFolder);
        ourClientManager.getWCClient().doCleanup(f);
        SVNUpdateClient clientUpdate = ourClientManager.getUpdateClient();
//        clientUpdate.doUpdate(f, SVNRevision.PREVIOUS, SVNDepth.EMPTY, true, true);
        ourClientManager.getWCClient().doAdd(f, true, true, true, SVNDepth.FILES, true, true, true);

        ourClientManager.getCommitClient().doCommit(new File[]{f}, true, commitMessage, true, true);

    }
    
    public void addFileOrFolderSave(String systemFolder, String commitMessage, int hasdCode) throws SVNException {
       
        File f = new File(connection.getSvnProperties().getFolderTemplocal()+ hasdCode + "\\" + systemFolder);
        ourClientManager.getWCClient().doCleanup(f);
        SVNUpdateClient clientUpdate = ourClientManager.getUpdateClient();
//      clientUpdate.doUpdate(f, SVNRevision.PREVIOUS, SVNDepth.EMPTY, true, true);
        ourClientManager.getWCClient().doAdd(f, true, true, true, SVNDepth.FILES, true, true, true);
        ourClientManager.getCommitClient().doCommit(new File[]{f}, true, commitMessage, true, true);
    
    }

    public void addFile(String pathFile, String systemFolder, String fase) throws SVNException {
        String url = connection.getSvnProperties().getUrl() + connection.getSvnProperties().getDir(fase) + systemFolder;
        SVNURL urlSvn = SVNURL.parseURIDecoded(url);
        SVNRepository repository = connection.getRepository();
        repository.setAuthenticationManager(connection.getRepository().getAuthenticationManager());

        ISVNEditor editor = repository.getCommitEditor("ARQUIVO ADICIONADO VIA SISTEMA.", null /*locks*/, true /*keepLocks*/, null /*mediator*/);
        
        //the second and the third parameters are the path and revision respectively 
        //of the item's ancestor if the item is being added with history
        editor.addFile(pathFile, null, -1);
    }

    public void deleteFileSVN(String systemFolder, String fileName, String commitMessage,String fase) throws SVNException {
        final SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
        String url = connection.getSvnProperties().getUrl() + connection.getSvnProperties().getDir(fase) + systemFolder + "/" + fileName;
        SVNURL urlSvn = SVNURL.parseURIDecoded(url);
        unLonckFileOrFolder(urlSvn);
        try {
            svnOperationFactory.setAuthenticationManager(connection.getRepository().getAuthenticationManager());
            final SvnRemoteDelete remoteDelete = svnOperationFactory.createRemoteDelete();
            remoteDelete.setSingleTarget(SvnTarget.fromURL(urlSvn));
            remoteDelete.setCommitMessage(commitMessage);
            final SVNCommitInfo commitInfo = remoteDelete.run();
            if (commitInfo != null) {
                final long newRevision = commitInfo.getNewRevision();
            }
        } finally {
            svnOperationFactory.dispose();
        }
    }

    /*
     **
     **
     */
    public void lockFileOrFolder(SVNURL fileUrl) throws SVNException {
        SVNURL[] arrayURL = new SVNURL[1];
        arrayURL[0] = fileUrl;
        SVNClientManager ourClientManager = SVNClientManager.newInstance(null, connection.getRepository().getAuthenticationManager());
        ourClientManager.getWCClient().doLock(arrayURL, true, "Arquivo Locado pelo sistema");
    }

    /*
     **
     **
     */
    public void unLonckFileOrFolder(SVNURL fileUrl) throws SVNException {
        SVNURL[] arrayURL = new SVNURL[1];
        arrayURL[0] = fileUrl;
        SVNClientManager ourClientManager = SVNClientManager.newInstance(null, connection.getRepository().getAuthenticationManager());
        ourClientManager.getWCClient().doUnlock(arrayURL, true);

    }

    public boolean isLocked(String fileName, String system) throws SVNException {

        SVNClientManager ourClientManager = SVNClientManager.newInstance(null, connection.getRepository().getAuthenticationManager());
        SVNStatus status = ourClientManager.getStatusClient().doStatus(new File(connection.getSvnProperties().getFolderTemplocal() + system + "\\" + fileName), true);

        System.out.println("Locado? - " + status.getRemoteLock());

        if (status.getRemoteLock() == null) {
            return false;
        } else {
            return true;
        }

    }
    
    public boolean isLocked(String fileName, String system, int hashCode) throws SVNException {

        SVNClientManager ourClientManager = SVNClientManager.newInstance(null, connection.getRepository().getAuthenticationManager());
        SVNStatus status = ourClientManager.getStatusClient().doStatus(new File(connection.getSvnProperties().getFolderTemplocal()+ hashCode+ "\\"  + system + "\\" + fileName), true);

        System.out.println("Locado? - " + status.getRemoteLock());

        if (status.getRemoteLock() == null) {
            return false;
        } else {
            return true;
        }

    }

    public SVNLock getLock(String fileName, String system) throws SVNException {
        SVNClientManager ourClientManager = SVNClientManager.newInstance(null, connection.getRepository().getAuthenticationManager());
        SVNStatus status = ourClientManager.getStatusClient().doStatus(new File(connection.getSvnProperties().getFolderTemplocal() + system + "\\" + fileName), true);
        return status.getRemoteLock();
    }
    
    //CR PERMITIR MULTI TELAS
    public SVNLock getLock(String fileName, String system, int hashCode) throws SVNException {
        SVNClientManager ourClientManager = SVNClientManager.newInstance(null, connection.getRepository().getAuthenticationManager());
        SVNStatus status = ourClientManager.getStatusClient().doStatus(new File(connection.getSvnProperties().getFolderTemplocal()+hashCode+"\\"  + system + "\\" + fileName), true);
        return status.getRemoteLock();
    }
    //CR

    public boolean moveToDeletados(String system, String oldName, String newName, String fase) throws SVNException {
        SVNClientManager ourClientManager = SVNClientManager.newInstance(null, connection.getRepository().getAuthenticationManager());

        final File file = new File(connection.getSvnProperties().getFolderTemplocal() + system + "\\" + oldName);
        final File renamedFile = new File(connection.getSvnProperties().getFolderTemplocal() + "deletados" + "\\" + oldName);
        checkOutEmpytFolder("deletados", fase);
        SVNMoveClient moveClient = ourClientManager.getMoveClient();
//        updateFile(connection.getSvnProperties().getFolderTemplocal() + system + "\\" + oldName);
        moveClient.doMove(file, renamedFile);
        addFileOrFolder("deletados", "NOME DO ARQUIVO FOI MODIFICADO VIA SISTEMA");
        return true;

    }

    public void updateFile(String path) throws SVNException {
        File f = new File(path);
        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
        updateClient.setIgnoreExternals(false);
        updateClient.doUpdate(f, SVNRevision.HEAD, SVNDepth.FILES, true, true);

    }


    /*
     **
     **
     */
    public List<TestCaseTSPropertiesBean> listAllTestCase(String systemFolder) throws SVNException {

        String url = this.systemFolder + systemFolder;
        dirEntries = connection.getRepository().getDir(url, -1, null, (Collection) null);

        List<TestCaseTSPropertiesBean> listTestCaseTSProperties = new ArrayList<TestCaseTSPropertiesBean>();

        for (Iterator entries = dirEntries.iterator(); entries.hasNext();) {

            SVNDirEntry dirEntry = (SVNDirEntry) entries.next();

            if (dirEntry.getName().contains("")) {
                TestCaseTSPropertiesBean testCaseProperties = new TestCaseTSPropertiesBean(dirEntry);
                listTestCaseTSProperties.add(testCaseProperties);
            }
        }
        return listTestCaseTSProperties;

    }

    /*
     **
     **
     */
    public List<TestCaseTSPropertiesBean> listTestCaseByName(String systemFolder, String testCaseName) throws SVNException {

        String url = this.systemFolder + systemFolder;
        url = url.replace("//", "/");
        dirEntries = connection.getRepository().getDir(url, -1, null, (Collection) null);

        List<TestCaseTSPropertiesBean> listTestCaseTSProperties = new ArrayList<TestCaseTSPropertiesBean>();

        for (Iterator entries = dirEntries.iterator(); entries.hasNext();) {

            SVNDirEntry dirEntry = (SVNDirEntry) entries.next();

            if (dirEntry.getName().contains(testCaseName)) {
                TestCaseTSPropertiesBean testCaseProperties = new TestCaseTSPropertiesBean(dirEntry);
                listTestCaseTSProperties.add(testCaseProperties);
            }
        }
        return listTestCaseTSProperties;
    }

    /*
     ** Fazer um Export de qualquer arquivo do svn para qualquer pasta local.
     */
//    public void exportFromSvn(String fileNamme, String dirFile) throws SVNException {
//        String url = connection.getSvnProperties().getUrl() + connection.getSvnProperties().getDir() + "/" + fileNamme;
//        SVNURL urlSvn = SVNURL.parseURIDecoded(url);
//        File f = new File(dirFile + "\\" + systemFolder);
//        SVNClientManager ourClientManager = SVNClientManager.newInstance(null, connection.getRepository().getAuthenticationManager());
//
//        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
//        updateClient.setIgnoreExternals(false);
//        lockFileOrFolder(urlSvn);
//
//        updateClient.doExport(urlSvn, f, SVNRevision.HEAD, SVNRevision.HEAD, null, true, null);
//
//    }
}
