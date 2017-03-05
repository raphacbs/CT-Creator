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
public class ComponenteBean implements Comparable<ComponenteBean> {

    private String idComponent;
    private String partNameComponent;
    private String nameComponent;
    private String description;
    private String system;
    private List<String> scripts;
    private Date date;

    public ComponenteBean() {
    }

    public ComponenteBean(String idComponent, String partNameComponent, String nameComponent, String description, String system, List<String> scripts, Date date) {

        this.idComponent = idComponent;
        this.partNameComponent = partNameComponent;
        this.nameComponent = nameComponent;
        this.description = description;
        this.system = system;
        this.scripts = scripts;
        this.date = date;
    }

    public String getNameComponent() {
        return nameComponent;
    }

    public void setNameComponent(String nameComponent) {
        this.nameComponent = nameComponent;
        //this.setIdComponent(this.getIdComponentByNameComponent(nameComponent));
        //this.setPartNameComponent(this.getPartNameComponentByNameComponent(nameComponent));
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

    public List<String> getScripts() {
        return scripts;
    }

    public void setScripts(List<String> scripts) {
        this.scripts = scripts;
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
    public int compareTo(ComponenteBean componente) {

        return this.getNameComponent().compareToIgnoreCase(componente.getNameComponent());

    }

    @Override
    public String toString() {
        return getNameComponent();
    }

    
      /**
     * Método verifica se existe componentes associados ao script
     *
     * @return
     */
    public boolean existsScriptAssociate() {

        return this.getScripts().isEmpty();

    }

    /**
     * 
     * @param nameScript
     * @return 
     */
    public boolean contaisScript(String nameScript) {

        return this.getScripts().contains(nameScript);

    }
}
