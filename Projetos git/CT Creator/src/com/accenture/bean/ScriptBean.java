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

    private String idScript;
    private String partNameScript;
    private String nameScript;
    private String description;
    private String system;
    private List<String> components;
    private List<String> testCases;
    private Date date;

    public ScriptBean() {
    }

    public ScriptBean(String idScript, String partNameScript, String nameScript, String description, String system, List<String> components, List<String> testCases, Date date) {

        this.idScript = idScript;
        this.partNameScript = partNameScript;
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

    public String getIdScript() {
        return idScript;
    }

    public String getIdComponentByNameComponent(String nameScript) {

        String regexComponent = "^Cen_\\d+_";
        String retorno = null;

        Pattern p = Pattern.compile(regexComponent);
        Matcher m = p.matcher(nameScript);

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

    public void setIdScript(String idScript) {

        this.idScript = idScript;
        if (this.getIdScript() != null && this.getPartNameScript() != null) {
            this.setNameScript(this.getIdScript() + this.getPartNameScript());
        }

    }

    public String getPartNameScript() {
        return partNameScript;
    }

    /**
     *
     * @param nameScript
     * @return
     */
    public String getPartNameComponentByNameComponent(String nameScript) {

        String regexComponent = "^Cen_\\d+_";
        String retorno = null;

        Pattern p = Pattern.compile(regexComponent);
        Matcher m = p.matcher(nameScript);

        // if we find a match, get the group 
        if (m.find()) {
            m.group();
            // get the matching group
            //codeGroup = m.group(0);
            retorno = nameScript.replace(m.group(0), "");
            // print the group
            //System.out.format(codeGroup);

        }

        return retorno;

    }

    public void setPartNameScript(String partNameScript) {

        this.partNameScript = partNameScript;
        if (this.getIdScript() != null && this.getPartNameScript() != null) {
            this.setNameScript(this.getIdScript() + this.getPartNameScript());
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

    /**
     * Método verifica se existe componentes associados ao script
     *
     * @return
     */
    public boolean existsComponentsAssociate() {

        return this.getComponents().isEmpty();

    }
}
