/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.bean;

import groovy.transform.ToString;
import java.util.Date;
import java.util.List;

/**
 *
 * @author sara.patricia.silva
 */
public class ComponenteBean implements Comparable<ComponenteBean>{
    
     private String nameComponent;
     private String description;
     private String system;
     private List<String> scripts;
     private Date date;
     
    public ComponenteBean() {
    }

    public ComponenteBean(String nameComponent, String description, String system, List<String> scripts, Date date) {
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
    
  

    @Override
    public int compareTo(ComponenteBean componente) {
                
         return this.getNameComponent().compareToIgnoreCase(componente.getNameComponent());
         
         
      }
    
    @Override
    public String toString(){
        return getNameComponent();
    }
 
    
}
