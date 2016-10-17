/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.bean;

import com.accenture.util.ProjectSettings;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
 *
 * @author brucilin.de.gouveia
 */
public class FlowPropertiesBean {

    private String idTestCase;
    private ArrayList<String> idFlows;
    private String system;
    private Properties fileProperties;
    private FileInputStream file;

    public FlowPropertiesBean(String idTestCase, String system) throws IOException {

        this.idTestCase = idTestCase;
        this.system = system;
        idFlows = new ArrayList<>();
        loadFileProperties();

    }

    public void setIdTestCase(String idTestCase) {
        this.idTestCase = idTestCase;
    }

    public void setIdFlows(ArrayList<String> idFlows) {
        this.idFlows = idFlows;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public ArrayList<String> getIdFlows() {

        idFlows.addAll(Arrays.asList(fileProperties.getProperty(idTestCase).split(";")));

        return idFlows;
    }

    public Properties getFileProperties() {
                
        return fileProperties;
    }

    public void loadFileProperties() throws FileNotFoundException, IOException {
        fileProperties = new Properties();
        file = new FileInputStream(ProjectSettings.PATH_FILE_SVN_FLOW_PROPERTIES);
        fileProperties.load(file);
        file.close();

    }
    

}
