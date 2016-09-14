/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.rn;

import com.accenture.bean.SVNPropertiesVOBean;
import com.accenture.bean.SvnConnectionBean;
import com.accenture.bean.TestCaseTSPropertiesBean;
import com.accenture.bean.TesteCaseTSBean;
import com.accenture.ts.dao.SvnConnectionDao;
import com.accenture.ts.dao.TesteCaseTSDAO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLock;
import org.tmatesoft.svn.core.SVNURL;

/**
 *
 * @author brucilin.de.gouveia
 */
public class SvnConnectionRN {

    private SvnConnectionDao connectionDao;
    private TesteCaseTSDAO tsDao;

    public SvnConnectionRN() throws SVNException, IOException {
        this.connectionDao = new SvnConnectionDao();
        this.tsDao = new TesteCaseTSDAO();

    }

    /**
     * Adiciona um Test Case no SVN
     *
     * @param testCase : Objeto de Test Case mensage : Comentário para ser
     * adicionado no commit
     * @param mensage
     * @return
     * @throws org.tmatesoft.svn.core.SVNException
     * @throws java.io.IOException
     */
    public boolean addTestCaseSVN(TesteCaseTSBean testCase, String mensage) throws SVNException, IOException {
        tsDao.deleteDir(new File(new SVNPropertiesVOBean().getFolderTemplocal()));
        connectionDao.checkOutEmpytFolder(testCase.getProduct());
        new TestCaseTSRN().createSheet(testCase, testCase.getProduct());
        connectionDao.addFileOrFolderSave(testCase.getProduct(), mensage);

        return true;
    }
    //INICIO CR 
     public boolean addTestCaseSVN(TesteCaseTSBean testCase, String mensage, int hashCode) throws SVNException, IOException {
//        tsDao.deleteDir(new File(new SVNPropertiesVOBean().getFolderTemplocal()));
        connectionDao.checkOutEmpytFolder(testCase.getProduct(), hashCode);
        new TestCaseTSRN().createSheet(testCase, testCase.getProduct(), hashCode);
        connectionDao.addFileOrFolderSave(testCase.getProduct(), mensage, hashCode);

        return true;
    }
    //FIM CR
   

    public boolean editTestCaseSVN(TesteCaseTSBean testCase, String mensage) throws SVNException, IOException {
        new TestCaseTSRN().createSheet(testCase, testCase.getProduct());
        connectionDao.addFileOrFolder(testCase.getProduct(), mensage);

        return true;
    }

    /*
     ** Método para buscar os casos de teste por sistema e/ou sistema/nome do CT
     */
    public List<TestCaseTSPropertiesBean> search(String system, String testCaseName) throws SVNException {
        List<TestCaseTSPropertiesBean> l = new ArrayList<TestCaseTSPropertiesBean>();

        if (system != null) {
            if (testCaseName == null) {
                l = connectionDao.listAllTestCase(system);
            } else if (testCaseName != null) {
                l = connectionDao.listTestCaseByName(system, testCaseName.toUpperCase());
            }

        }

        for (int i = 0; i < l.size(); i++) {
            l.get(i).setSystem(system);
        }

        return l;
    }

    /*
     ** Método para modificar um arquivo já existente no repositório
     */
    public void modify(String systemFolder, String fileName, String commitMessage) throws SVNException {

        connectionDao.deleteFileSVN(systemFolder, fileName, commitMessage);
        connectionDao.addFileOrFolder(systemFolder, commitMessage);
    }

    /*
     **Método para deletar um arquivo no repositório.
     */
    public void delete(String systemFolder, String fileName, String commitMessage) throws SVNException {

        connectionDao.deleteFileSVN(systemFolder, fileName, commitMessage);
    }

    /*
     **Método para importar plano do repositório para máquina local
     */
    public void importBySvnForLocalFolder(String dir, String folder, String fileName) throws SVNException {

        connectionDao.checkOutEmpytFolder(folder);

        connectionDao.exportFileOrFolder(fileName, dir, folder);
//        connectionDao.copyFileToLocal(fileName, dir, folder);

    }
    
    public void importBySvnForLocalFolder(String dir, String folder, String fileName, int hashCode) throws SVNException {

        connectionDao.checkOutEmpytFolder(folder, hashCode);

        connectionDao.exportFileOrFolder(fileName, dir, folder, hashCode);
//        connectionDao.copyFileToLocal(fileName, dir, folder);

    }
    
    public void checkOutEmpytFolder(String folder, int hashCode) throws SVNException {
        connectionDao.checkOutEmpytFolder(folder, hashCode);
    }
    
    public void exportFile(String dir, String folder, String fileName) throws SVNException{
         connectionDao.exportFileOrFolder(fileName, dir, folder);
    }
    
    //INICIO CR PERMITIR TRABALHA COM VARIAS TELAS AO MESMO TEMPO 
     public void exportFile(String dir, String folder, String fileName, int hashCode) throws SVNException{
         connectionDao.exportFileOrFolder(fileName, dir, folder, hashCode);
    }
    //FIM CR 

    /*
    ** Método para 
     */
    public void lockFile(boolean lock, SVNURL dirFileSVN) throws SVNException {
        if (lock) {
            connectionDao.lockFileOrFolder(dirFileSVN);
        } else {
            connectionDao.unLonckFileOrFolder(dirFileSVN);
        }
    }

    public boolean isLocked(String fileName, String system) throws SVNException {
        return connectionDao.isLocked(fileName, system);
    }
    
     public boolean isLocked(String fileName, String system, int hashCode) throws SVNException {
        return connectionDao.isLocked(fileName, system, hashCode);
    }

    public SVNLock getLock(String fileName, String system) throws SVNException {
        return connectionDao.getLock(fileName, system);

    }
    
    //CR PERMITIR MULTI TELAS
    public SVNLock getLock(String fileName, String system, int hashCode) throws SVNException {
        return connectionDao.getLock(fileName, system, hashCode);

    }
    //CR

    public void updateCt(String system, String oldName, String newName, TesteCaseTSBean testCase) throws SVNException, IOException {
        SvnConnectionBean connection = new SvnConnectionBean();
        final String tempOldName = testCase.getTestCaseProperties().getDirEntry().getName();
        testCase.getTestCaseProperties().getDirEntry().setName(tempOldName.replaceAll(oldName, newName));
        newName = testCase.getTestCaseProperties().getDirEntry().getName();
        new TestCaseTSRN().writerSheet(system, newName, testCase);
        if (!tempOldName.equals(newName)) {
//            connectionDao.moveToDeletados(system, tempOldName, newName);
            delete(system, tempOldName, "EXCLUINDO PARA MODIFICAR CT VIA CT CREATOR.");
//            connectionDao.checkOutEmpytFolder(system);
            new File(new SVNPropertiesVOBean().getFolderTemplocal() + system + "\\" + tempOldName).delete();
            connectionDao.addFileOrFolder(system, "ADD CT MODIFICADO CT CREATOR.");
            String url = connection.getSvnProperties().getUrl() + connection.getSvnProperties().getDir() + system + "/" + newName;
            SVNURL urlSvn = SVNURL.parseURIDecoded(url);
            connectionDao.lockFileOrFolder(urlSvn);
        } else {
            delete(system, tempOldName, "EXCLUINDO PARA MODIFICAR(NOME) CT VIA CT CREATOR.");
//            new File(new SVNPropertiesVOBean().getFolderTemplocal()+system+"\\"+tempOldName).delete();
//            connectionDao.checkOutEmpytFolder(system);

            connectionDao.addFileOrFolder(system, "CT FOI MODIFICADO VIA CT CREATOR");

        }
        //        modify(system, tempOldName, "NOME DO ARQUIVO FOI MODIFICADO VIA SISTEMA");

//          String dirFile = new SVNPropertiesVOBean().getFolderTemplocal() +"\\" +system+"\\"+newName;
//          connectionDao.addFile(dirFile, system);
    }
    //CR PERMITIR VARIAS TELAS
    public void updateCt(String system, String oldName, String newName, TesteCaseTSBean testCase, int hashCode) throws SVNException, IOException {
        SvnConnectionBean connection = new SvnConnectionBean();
        final String tempOldName = testCase.getTestCaseProperties().getDirEntry().getName();
        testCase.getTestCaseProperties().getDirEntry().setName(tempOldName.replaceAll(oldName, newName));
        newName = testCase.getTestCaseProperties().getDirEntry().getName();
        new TestCaseTSRN().writerSheet(system, newName, testCase,hashCode);
        if (!tempOldName.equals(newName)) {
//            connectionDao.moveToDeletados(system, tempOldName, newName);
            delete(system, tempOldName, "EXCLUINDO PARA MODIFICAR(nome) CT VIA CT CREATOR.");
//            connectionDao.checkOutEmpytFolder(system);
            new File(new SVNPropertiesVOBean().getFolderTemplocal() +hashCode+ "\\"+ system + "\\" + tempOldName).delete();
            connectionDao.addFileOrFolder(system, "ADD CT MODIFICADO CT CREATOR.", hashCode);
            String url = connection.getSvnProperties().getUrl() + connection.getSvnProperties().getDir() + system + "/" + newName;
            SVNURL urlSvn = SVNURL.parseURIDecoded(url);
            connectionDao.lockFileOrFolder(urlSvn);
        } else {
            delete(system, tempOldName, "EXCLUINDO PARA MODIFICAR CT VIA CT CREATOR.");
//            new File(new SVNPropertiesVOBean().getFolderTemplocal()+system+"\\"+tempOldName).delete();
//            connectionDao.checkOutEmpytFolder(system);

            connectionDao.addFileOrFolder(system, "CT FOI MODIFICADO VIA CT CREATOR",hashCode);

        }
        //        modify(system, tempOldName, "NOME DO ARQUIVO FOI MODIFICADO VIA SISTEMA");

//          String dirFile = new SVNPropertiesVOBean().getFolderTemplocal() +"\\" +system+"\\"+newName;
//          connectionDao.addFile(dirFile, system);
    }
    //CR 
    
    public void addFileSVN(String system, int hashCode)throws SVNException{
         connectionDao.addFileOrFolder(system, "CT FOI MODIFICADO VIA CT CREATOR - 2 TENTATIVA",hashCode);
    }
    
    

}
