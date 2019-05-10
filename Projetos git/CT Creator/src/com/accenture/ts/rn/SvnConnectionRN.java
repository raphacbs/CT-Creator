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

    public SvnConnectionRN(String fase) throws SVNException, IOException {
        this.connectionDao = new SvnConnectionDao(fase);
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
    public boolean addTestCaseSVN(TesteCaseTSBean testCase, String mensage,String fase) throws SVNException, IOException {
//        tsDao.deleteDir(new File(SVNPropertiesVOBean.getInstance().getFolderTemplocal()));
//        connectionDao.checkOutEmpytFolder(testCase.getProduct(),fase);
//        new TestCaseTSRN(fase).createSheet(testCase, testCase.getProduct(), fase);
//        connectionDao.addFileOrFolderSave(testCase.getProduct(), mensage);

        return true;
    }
    //INICIO CR 
     public boolean addTestCaseSVN(TesteCaseTSBean testCase, String mensage, int hashCode, String fase) throws SVNException, IOException {
//        tsDao.deleteDir(new File(new SVNPropertiesVOBean().getFolderTemplocal()));
//         try{
//        connectionDao.checkOutEmpytFolder(testCase.getProduct(), hashCode, fase);
//        new TestCaseTSRN(fase).createSheet(testCase, testCase.getProduct(), hashCode, fase);
//        connectionDao.addFileOrFolderSave(testCase.getProduct(), mensage, hashCode);
//        }catch(Exception ex){
//           System.out.printf("Erro ao adicionar o CT {0}, erro:", testCase.getTestScriptName(), ex.getStackTrace());
//           return false;
//       }
        return true;
    }
    //FIM CR
   

    public boolean editTestCaseSVN(TesteCaseTSBean testCase, String mensage, String fase) throws SVNException, IOException {
//        new TestCaseTSRN(fase).createSheet(testCase, testCase.getProduct(),fase);
//        connectionDao.addFileOrFolder(testCase.getProduct(), mensage);

        return true;
    }

    /*
     ** Método para buscar os casos de teste por sistema e/ou sistema/nome do CT
     */
    public List<TestCaseTSPropertiesBean> search(String system, String testCaseName) throws SVNException {
        List<TestCaseTSPropertiesBean> l = new ArrayList<TestCaseTSPropertiesBean>();

//        if (system != null) {
//            if (testCaseName == null) {
//                l = connectionDao.listAllTestCase(system);
//            } else if (testCaseName != null) {
//                l = connectionDao.listTestCaseByName(system, testCaseName.toUpperCase());
//            }
//
//        }
//
//        for (int i = 0; i < l.size(); i++) {
//            l.get(i).setSystem(system);
//        }

        return l;
    }

    /*
     ** Método para modificar um arquivo já existente no repositório
     */
    public void modify(String systemFolder, String fileName, String commitMessage, String fase) throws SVNException {

//        connectionDao.deleteFileSVN(systemFolder, fileName, commitMessage, fase);
//        connectionDao.addFileOrFolder(systemFolder, commitMessage);
    }

    /*
     **Método para deletar um arquivo no repositório.
     */
    public void delete(String systemFolder, String fileName, String commitMessage, String fase) throws SVNException {

//        connectionDao.deleteFileSVN(systemFolder, fileName, commitMessage, fase);
    }

    /*
     **Método para importar plano do repositório para máquina local
     */
    public void importBySvnForLocalFolder(String dir, String folder, String fileName, String fase) throws SVNException {

//        connectionDao.checkOutEmpytFolder(folder, fase);
//
//        connectionDao.exportFileOrFolder(fileName, dir, folder, fase);
//        connectionDao.copyFileToLocal(fileName, dir, folder);

    }
    
    public void importBySvnForLocalFolder(String dir, String folder, String fileName, int hashCode, String fase) throws SVNException {

//        connectionDao.checkOutEmpytFolder(folder, hashCode, fase);
//
//        connectionDao.exportFileOrFolder(fileName, dir, folder, hashCode, fase);
//        connectionDao.copyFileToLocal(fileName, dir, folder);

    }
    
    public void checkOutEmpytFolder(String folder, int hashCode, String fase) throws SVNException {
//        connectionDao.checkOutEmpytFolder(folder, hashCode, fase);
    }
    
    public void exportFile(String dir, String folder, String fileName, String fase) throws SVNException{
//         connectionDao.exportFileOrFolder(fileName, dir, folder, fase);
    }
    
    //INICIO CR PERMITIR TRABALHA COM VARIAS TELAS AO MESMO TEMPO 
     public void exportFile(String dir, String folder, String fileName, int hashCode, String fase) throws SVNException{
//         connectionDao.exportFileOrFolder(fileName, dir, folder, hashCode, fase);
    }
    //FIM CR 

    /*
    ** Método para 
     */
    public void lockFile(boolean lock, SVNURL dirFileSVN) throws SVNException {
//        if (lock) {
//            connectionDao.lockFileOrFolder(dirFileSVN);
//        } else {
//            connectionDao.unLonckFileOrFolder(dirFileSVN);
//        }
    }

    public boolean isLocked(String fileName, String system) throws SVNException {
//        return connectionDao.isLocked(fileName, system);
        return false;
    }
    
     public boolean isLocked(String fileName, String system, int hashCode) throws SVNException {
//        return connectionDao.isLocked(fileName, system, hashCode);
          return false;
    }

    public SVNLock getLock(String fileName, String system) throws SVNException {
//        return connectionDao.getLock(fileName, system);
         return new SVNLock(system, system, system, system, null, null);

    }
    
    //CR PERMITIR MULTI TELAS
    public SVNLock getLock(String fileName, String system, int hashCode) throws SVNException {
//        return connectionDao.getLock(fileName, system, hashCode);
         return new SVNLock(system, system, system, system, null, null);

    }
    //CR

    public void updateCt(String system, String oldName, String newName, TesteCaseTSBean testCase, String fase) throws SVNException, IOException {
//        SvnConnectionBean connection = new SvnConnectionBean(fase);
//        final String tempOldName = testCase.getTestCaseProperties().getDirEntry().getName();
//        testCase.getTestCaseProperties().getDirEntry().setName(tempOldName.replaceAll(oldName, newName));
//        newName = testCase.getTestCaseProperties().getDirEntry().getName();
//        new TestCaseTSRN(fase).writerSheet(system, newName, testCase);
//        if (!tempOldName.equals(newName)) {
////            connectionDao.moveToDeletados(system, tempOldName, newName);
//            delete(system, tempOldName, "EXCLUINDO PARA MODIFICAR CT VIA CT CREATOR.", fase);
////            connectionDao.checkOutEmpytFolder(system);
//            new File(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + system + "\\" + tempOldName).delete();
//            connectionDao.addFileOrFolder(system, "ADD CT MODIFICADO CT CREATOR.");
//            String url = connection.getSvnProperties().getUrl() + connection.getSvnProperties().getDir(fase) + system + "/" + newName;
//            SVNURL urlSvn = SVNURL.parseURIDecoded(url);
//            connectionDao.lockFileOrFolder(urlSvn);
//        } else {
//            delete(system, tempOldName, "EXCLUINDO PARA MODIFICAR(NOME) CT VIA CT CREATOR.", fase);
////            new File(new SVNPropertiesVOBean().getFolderTemplocal()+system+"\\"+tempOldName).delete();
////            connectionDao.checkOutEmpytFolder(system);
//
//            connectionDao.addFileOrFolder(system, "CT FOI MODIFICADO VIA CT CREATOR");
//
//        }
        //        modify(system, tempOldName, "NOME DO ARQUIVO FOI MODIFICADO VIA SISTEMA");

//          String dirFile = new SVNPropertiesVOBean().getFolderTemplocal() +"\\" +system+"\\"+newName;
//          connectionDao.addFile(dirFile, system);
    }
    //CR PERMITIR VARIAS TELAS
    public void updateCt(String system, String oldName, String newName, TesteCaseTSBean testCase, int hashCode, String fase) throws SVNException, IOException {
//        SvnConnectionBean connection = new SvnConnectionBean(fase);
//        final String tempOldName = testCase.getTestCaseProperties().getDirEntry().getName();
//        testCase.getTestCaseProperties().getDirEntry().setName(tempOldName.replaceAll(oldName, newName));
//        newName = testCase.getTestCaseProperties().getDirEntry().getName();
//        new TestCaseTSRN(fase).writerSheet(system, newName, testCase,hashCode);
//        if (!tempOldName.equals(newName)) {
////            connectionDao.moveToDeletados(system, tempOldName, newName);
//            delete(system, tempOldName, "EXCLUINDO PARA MODIFICAR(nome) CT VIA CT CREATOR.", fase);
////            connectionDao.checkOutEmpytFolder(system);
//            new File(SVNPropertiesVOBean.getInstance().getFolderTemplocal() +hashCode+ "\\"+ system + "\\" + tempOldName).delete();
//            connectionDao.addFileOrFolder(system, "ADD CT MODIFICADO CT CREATOR.", hashCode);
//            String url = connection.getSvnProperties().getUrl() + connection.getSvnProperties().getDir(fase) + system + "/" + newName;
//            SVNURL urlSvn = SVNURL.parseURIDecoded(url);
//            connectionDao.lockFileOrFolder(urlSvn);
//        } else {
//            delete(system, tempOldName, "EXCLUINDO PARA MODIFICAR CT VIA CT CREATOR.", fase);
////            new File(new SVNPropertiesVOBean().getFolderTemplocal()+system+"\\"+tempOldName).delete();
////            connectionDao.checkOutEmpytFolder(system);
//
//            connectionDao.addFileOrFolder(system, "CT FOI MODIFICADO VIA CT CREATOR",hashCode);
//
//        }
//        //        modify(system, tempOldName, "NOME DO ARQUIVO FOI MODIFICADO VIA SISTEMA");

//          String dirFile = new SVNPropertiesVOBean().getFolderTemplocal() +"\\" +system+"\\"+newName;
//          connectionDao.addFile(dirFile, system);
    }
    //CR 
    
    public void addFileSVN(String system, int hashCode)throws SVNException{
//         connectionDao.addFileOrFolder(system, "CT FOI MODIFICADO VIA CT CREATOR - 2 TENTATIVA",hashCode);
    }
    
    

}
