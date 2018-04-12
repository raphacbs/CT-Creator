/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.fluxo;

import com.accenture.bean.FlowBean;
import com.accenture.ts.dao.FlowDAO;
import com.accenture.ts.rn.TestCaseTSRN;
import com.accenture.ts.rn.FlowRN;
import com.accenture.util.FunctiosDates;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author raphael.da.silva
 */
public class Fluxos {
    public static void main(String[] args) throws IOException, SVNException {
//        FlowDAO w = new FlowDAO();
//        w.donwloadFluxos();
//        System.out.println(w.getEntriesWorkflow());
//        System.out.println(w.isWorkingCopy("C:\\FastPlan\\fluxos"));
////        w.deleteFile("test - Copy.properties");
//        w.save();
//        w.lockFile("test - Copy.properties");
//        w.unLockFile("test - Copy.properties");

//        TestCaseTSRN tcRN = new TestCaseTSRN();
//        tcRN.importSheetAllCts("C:\\Users\\raphael.da.silva\\Desktop\\Planos\\TS.xlsx", "Importando planos para o repositorio");

        FlowRN workflowRN = new FlowRN("");
        
//        System.out.println("test.fluxo.Fluxos.main() - "+ workflowRN.getEntries());
        
//        FlowBean wb = workflowRN.getFile(workflowRN.getEntries().get(0).getName());
//        
//        System.out.println("Nome: "+wb.getName()+"\nDescrição: "+wb.getDescription()+"\nData: "+wb.getRegisterDate()+"\nSistema: "+wb.getSystem()
//        +"\nTestCases: "+wb.getTestCases());

          List<String> tcs = new ArrayList<String>();
          tcs.add("0004");
          tcs.add("0008");
          tcs.add("0004");
          tcs.add("0003");
          tcs.add("0002");
          tcs.add("0006");
          
          FlowBean wb = new FlowBean();
          wb.setDescription("Craiado via código");
          wb.setName("Ativa MSISDN");
          wb.setRegisterDate(FunctiosDates.getDateActual());
          wb.setSystem("BLL");
          wb.setTestCases(tcs);
          
          
          workflowRN.saveFile(null, wb,"");
        
    }
}
