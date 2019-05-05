/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.rn;

import com.accenture.bean.SystemBean;
import com.accenture.bean.TesteCaseTSBean;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import static org.tigris.subversion.javahl.NodeKind.dir;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author automacao
 */
public class ExportCTSVN {
    public static void main(String[] agrs) throws Exception{
        convertSheetInObject(new File("C:\\Users\\automacao\\Documents\\CTs\\Siebel 6.3"));
    }
    
    
    private static void convertSheetInObject(File dir) throws SVNException, Exception {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (!file.isDirectory() && file.getName().endsWith("xlsx")) {
					System.out.println("file:" + file.getCanonicalPath());
                                        TestCaseTSRN tcRN = new TestCaseTSRN();
                                        TesteCaseTSBean tc = tcRN.readSheet(file.getAbsolutePath()).get(0);                                        
					tc.setIdSystem(getIdSystem(tc.getProduct()));
                                        tcRN.saveTestCaseTSBD(tc);
				} else {
					System.out.println("directory:" + file.getCanonicalPath());
                                        convertSheetInObject(file);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    private static int getIdSystem(String system) throws Exception{
        TestCaseTSRN tcRN = new TestCaseTSRN();
        List<SystemBean> systems = tcRN.getSystemsBD();
        for(SystemBean sb : systems){
            if(sb.getDescricao().equalsIgnoreCase(system))
                return sb.getId();
    }
        return -1;
    }
}
