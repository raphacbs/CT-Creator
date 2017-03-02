/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.bean;

import java.util.Date;
import java.util.List;

/**
 *
 * @author sara.patricia.silva
 */
public class ScriptBean implements Comparable<ScriptBean>{
    
    private String nameScript;
    private String description;
    private String system;
    private List<String> components;
    private List<String> testCases;
    private Date date;

    public ScriptBean() {
    }

    public ScriptBean(String nameScript, String description, String system, List<String> components, List<String> testCases, Date date) {
        this.nameScript = nameScript;
        this.description = description;
        this.system = system;
        this.components = components;
        this.testCases = testCases;
        this.date = date;
    }

    public String getNameScript() {
        return nameScript;
    }

    public void setNameScript(String nameScript) {
        this.nameScript = nameScript;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public List<String> getComponents() {
        return components;
    }

    public void setComponents(List<String> components) {
        this.components = components;
    }

    public List<String> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<String> testCases) {
        this.testCases = testCases;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
     @Override
    public int compareTo(ScriptBean script) {
                
         return this.getNameScript().compareToIgnoreCase(script.getNameScript());
         
         
      }
    
    @Override
    public String toString(){
        return getNameScript();
    }

    
}
