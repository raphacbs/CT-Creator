/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.model;

import java.util.List;

/**
 *
 * @author raphael.da.silva
 */
public class Plano {
    private int id;
    private String sistemaMaster;
    private String sistemasEnvolvidos;
    private String fornecedor;
    private String tpRequisito;
    private String requisito;
    private String cenarioTeste;
    private String casoTeste;
    private String descCasoTeste;
    private String funcionalidade;
    private String cadeia;
    private String segmento;
    private String produto;
    private String cenarioIntegrado;
    private int qtdSistemas;
    private String cenarioAutomatizavel;
    private String type;
    private String trg;
    private String subject;
    private String criacaoAlteracao;
    private String nomeArquivo;
    private Step step;
    private boolean selecionado;
    private CasoTesteTemp ct;
    private List<Step> listStep;
    private int qtdStep ;
    private String complexidade;
    

    

    public Plano(int id, String sistemaMaster, String sistemasEnvolvidos, String fornecedor, String tpRequisito, String requisito, String cenarioTeste, String casoTeste, String descCasoTeste, String funcionalidade, String cadeia, String segmento, String produto, String cenarioIntegrado, int qtdSistemas, String cenarioAutomatizavel, String type, String trg, String subject, String criacaoAlteracao, String nomeArquivo, Step step, boolean selecionado, int qtd) {
        this.id = id;
        this.sistemaMaster = sistemaMaster;
        this.sistemasEnvolvidos = sistemasEnvolvidos;
        this.fornecedor = fornecedor;
        this.tpRequisito = tpRequisito;
        this.requisito = requisito;
        this.cenarioTeste = cenarioTeste;
        this.casoTeste = casoTeste;
        this.descCasoTeste = descCasoTeste;
        this.funcionalidade = funcionalidade;
        this.cadeia = cadeia;
        this.segmento = segmento;
        this.produto = produto;
        this.cenarioIntegrado = cenarioIntegrado;
        this.qtdSistemas = qtdSistemas;
        this.cenarioAutomatizavel = cenarioAutomatizavel;
        this.type = type;
        this.trg = trg;
        this.subject = subject;
        this.criacaoAlteracao = criacaoAlteracao;
        this.nomeArquivo = nomeArquivo;
        this.step =  step;
        this.selecionado = selecionado;
        this.qtdStep = qtdStep;
    }
    
    public Plano(){
        
    }

    public int getQtdStep() {
        return qtdStep;
    }

    public void setQtdStep(int qtdStep) {
        this.qtdStep = qtdStep;
    }

    public String getComplexidade() {
        return complexidade;
    }

    public void setComplexidade(String complexidade) {
        this.complexidade = complexidade;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSistemaMaster() {
        return sistemaMaster;
    }

    public void setSistemaMaster(String sistemaMaster) {
        this.sistemaMaster = sistemaMaster;
    }

    public String getSistemasEnvolvidos() {
        return sistemasEnvolvidos;
    }

    public void setSistemasEnvolvidos(String sistemasEnvolvidos) {
        this.sistemasEnvolvidos = sistemasEnvolvidos;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getTpRequisito() {
        return tpRequisito;
    }

    public void setTpRequisito(String tpRequisito) {
        this.tpRequisito = tpRequisito;
    }

    public String getRequisito() {
        return requisito;
    }

    public void setRequisito(String requisito) {
        this.requisito = requisito;
    }

    public String getCenarioTeste() {
        return cenarioTeste;
    }

    public void setCenarioTeste(String cenarioTeste) {
        this.cenarioTeste = cenarioTeste;
    }

    public String getCasoTeste() {
        return casoTeste;
    }

    public void setCasoTeste(String casoTeste) {
        this.casoTeste = casoTeste;
    }

    public String getDescCasoTeste() {
        return descCasoTeste;
    }

    public void setDescCasoTeste(String descCasoTeste) {
        this.descCasoTeste = descCasoTeste;
    }

    public String getFuncionalidade() {
        return funcionalidade;
    }

    public void setFuncionalidade(String funcionalidade) {
        this.funcionalidade = funcionalidade;
    }

    public String getCadeia() {
        return cadeia;
    }

    public void setCadeia(String cadeia) {
        this.cadeia = cadeia;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getCenarioIntegrado() {
        return cenarioIntegrado;
    }

    public void setCenarioIntegrado(String cenarioIntegrado) {
        this.cenarioIntegrado = cenarioIntegrado;
    }

    public int getQtdSistemas() {
        return qtdSistemas;
    }

    public void setQtdSistemas(int qtdSistemas) {
        this.qtdSistemas = qtdSistemas;
    }

    public String getCenarioAutomatizavel() {
        return cenarioAutomatizavel;
    }

    public void setCenarioAutomatizavel(String cenarioAutomatizavel) {
        this.cenarioAutomatizavel = cenarioAutomatizavel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTrg() {
        return trg;
    }

    public void setTrg(String trg) {
        this.trg = trg;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCriacaoAlteracao() {
        return criacaoAlteracao;
    }

    public void setCriacaoAlteracao(String criacaoAlteracao) {
        this.criacaoAlteracao = criacaoAlteracao;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }
    
    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }
    
    public CasoTesteTemp getCt() {
        return ct;
    }

    public void setCt(CasoTesteTemp ct) {
        this.ct = ct;
    }

    public List<Step> getListStep() {
        return listStep;
    }

    public void setListStep(List<Step> listStep) {
        this.listStep = listStep;
    }
    
    

}
