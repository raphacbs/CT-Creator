/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.dao;

import com.accenture.bean.SVNPropertiesVOBean;
import com.accenture.bean.TestPlanTSBean;
import com.accenture.bean.TesteCaseTSBean;
import com.accenture.util.FrameworkSvnManager;
import static com.accenture.util.FrameworkSvnManager.getListEntries;
import com.accenture.util.ProjectSettings;
import java.awt.Frame;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author brucilin.de.gouveia
 */
public class TestPlanTSDao implements Serializable {
    
    private TestPlanTSBean testPlan;
    private List<TesteCaseTSBean> listTc;

    public TestPlanTSDao() {
        testPlan = new TestPlanTSBean();
        listTc = new ArrayList<TesteCaseTSBean>();
    }

    public List<TesteCaseTSBean> getListTc() {
        return listTc;
    }

    public void setListTc(List<TesteCaseTSBean> listTc) {
        this.listTc = listTc;
    }
    
    
    
    public void addTestCase(TesteCaseTSBean ct){
        this.testPlan.addTestCase(ct);
    }
    
    public void removeTestCase(int id){
        this.testPlan.removeTestCase(id);
    }

    public TestPlanTSBean getTestPlan() {
        return testPlan;
    }

    public void setTestPlan(TestPlanTSBean testPlan) {
        this.testPlan = testPlan;
    }
    
     public void moveUpTestCase(int index){
        Collections.swap(this.testPlan.getTestCase(), index, index - 1);
    }
    
    public void moveDownTestCase(int index){
        Collections.swap(this.testPlan.getTestCase(), index, index + 1);
    }
    

    public List<SVNDirEntry> getEntries(String system) throws SVNException, IOException {
        
        SVNPropertiesVOBean properties = new SVNPropertiesVOBean();
        String url =ProjectSettings.URL_PLANO_SVN +"/"+system;
        String username = properties.getUser();
        String password = properties.getPass();
        
        
        return getListEntries(new SVNPropertiesVOBean().getUrlScritps() + "/" + system, FrameworkSvnManager.getRepository(url, username, password));
    }
}
