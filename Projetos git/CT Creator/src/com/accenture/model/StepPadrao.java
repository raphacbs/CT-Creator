/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.model;

/**
 *
 * @author Raphael
 */
public class StepPadrao {
    private int id;
    private String nomeStep;
    private String descStep;
    private String resultadoStep;
    private String tipoStepPadrao;
    private String sistema;
    private double versao;
    

    public StepPadrao(int id, String nomeStep, String descStep, String resultadoStep, String tipoStepPadrao) {
        this.id = id;
        this.nomeStep = nomeStep;
        this.descStep = descStep;
        this.resultadoStep = resultadoStep;
        this.tipoStepPadrao = tipoStepPadrao;
        
    }
    
    public StepPadrao(){
        
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

    public String getTipoStepPadrao() {
        return tipoStepPadrao;
    }

    public void setTipoStepPadrao(String tipoStepPadrao) {
        this.tipoStepPadrao = tipoStepPadrao;
    }
    
    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public double getVersao() {
        return versao;
    }

    public void setVersao(double versao) {
        this.versao = versao;
    }

    
    
    
}
