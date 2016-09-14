/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.fluxo;

import com.accenture.ts.dao.WorkflowDAO;
import com.accenture.ts.rn.TestCaseTSRN;
import java.io.IOException;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author raphael.da.silva
 */
public class Fluxos {
    public static void main(String[] args) throws IOException, SVNException {
//        WorkflowDAO w = new WorkflowDAO();
//        w.donwloadFluxos();
//        System.out.println(w.getEntriesWorkflow());
//        System.out.println(w.isWorkingCopy("C:\\FastPlan\\fluxos"));
////        w.deleteFile("test - Copy.properties");
//        w.save();
//        w.lockFile("test - Copy.properties");
//        w.unLockFile("test - Copy.properties");

        TestCaseTSRN tcRN = new TestCaseTSRN();
        tcRN.importSheetAllCts("C:\\Users\\raphael.da.silva\\Desktop\\Planos\\TS.xlsx", "Importando planos para o repositorio");
    }
}
