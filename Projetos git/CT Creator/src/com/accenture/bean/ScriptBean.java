/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.bean;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sara.patricia.silva
 */
public class ScriptBean implements Comparable<ScriptBean> {

    private String idComponent;
    private String partNameComponent;
    private String nameScript;
    private String description;
    private String system;
    private List<String> components;
    private List<String> testCases;
    private Date date;

    public ScriptBean() {
    }

    public ScriptBean(String nameScript, String description, String system, List<String> components, List<String> testCases, Date date) {

        this.idComponent = idComponent;
        this.partNameComponent = partNameComponent;
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

    public String getIdComponent() {
        return idComponent;
    }

    public String getIdComponentByNameComponent(String nameComponent) {

        String regexComponent = "^comp_\\d+_";
        String retorno = null;

        Pattern p = Pattern.compile(regexComponent);
        Matcher m = p.matcher(nameComponent);

        // if we find a match, get the group 
        if (m.find()) {
            m.group();
            // get the matching group
            //codeGroup = m.group(0);
            retorno = m.group(0);
            // print the group
            //System.out.format(codeGroup);

        }

        return retorno;
    }

    public void setIdComponent(String idComponent) {

        this.idComponent = idComponent;
        if (this.getIdComponent() != null && this.getPartNameComponent() != null) {
            this.setNameComponent(this.getIdComponent() + this.getPartNameComponent());
        }

    }

    public String getPartNameComponent() {
        return partNameComponent;
    }

    public String getPartNameComponentByNameComponent(String nameComponent) {

        String regexComponent = "^comp_\\d+_";
        String retorno = null;

        Pattern p = Pattern.compile(regexComponent);
        Matcher m = p.matcher(nameComponent);

        // if we find a match, get the group 
        if (m.find()) {
            m.group();
            // get the matching group
            //codeGroup = m.group(0);
            retorno = nameComponent.replace(m.group(0), "");
            // print the group
            //System.out.format(codeGroup);

        }

        return retorno;

    }

    public void setPartNameComponent(String partNameComponent) {

        this.partNameComponent = partNameComponent;
        if (this.getIdComponent() != null && this.getPartNameComponent() != null) {
            this.setNameComponent(this.getIdComponent() + this.getPartNameComponent());
        }

    }
    
    @Override
    public int compareTo(ScriptBean script) {

        return this.getNameScript().compareToIgnoreCase(script.getNameScript());

    }

    @Override
    public String toString() {
        return getNameScript();
    }

}
