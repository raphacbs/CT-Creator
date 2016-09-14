/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.bean;

import java.io.Serializable;

/**
 *
 * @author Raphael
 */
public class Step implements Serializable {
    private int id;
    private String nomeStep;
    private String descStep;
    private String resultadoStep;
    private int idPlano;

    public Step(int id, String nomeStep, String descStep, String resultadoStep, int idPlano) {
        this.id = id;
        this.nomeStep = nomeStep;
        this.descStep = descStep;
        this.resultadoStep = resultadoStep;
        this.idPlano = idPlano;
    }
    
    public Step(){
        
    }

    public int getId() {
        return id;
    }
    


    public void setId(int id) {
        this.id = id;
    }

    public String getNomeStep() {
        return nomeStep;
    }

    public void setNomeStep(String nomeStep) {
        this.nomeStep = nomeStep;
    }

    public String getDescStep() {
        return descStep;
    }

    public void setDescStep(String descStep) {
        this.descStep = descStep;
    }

    public String getResultadoStep() {
        return resultadoStep;
    }

    public void setResultadoStep(String resultadoStep) {
        this.resultadoStep = resultadoStep;
    }

    public int getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(int idPlano) {
        this.idPlano = idPlano;
    }
    
    
}
