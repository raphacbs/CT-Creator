/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;


/**
 *
 * @author raphael.da.silva
 */
public class Espera extends JDialog implements Runnable {

    private static final long serialVersionUID = 3977298828280279606L;
    private static final int MINIMUM = 0;
    private JPanel painel = null;
    private int qtTotal, qtdeProcessado;
    private JTextField txtQtdeProcessada;
    private javax.swing.Timer activityMonitor;
    JProgressBar pbar = new JProgressBar();

    @Override
    public void run() {

    }

    public Espera() {
        this.pbar = new JProgressBar();
    }

    public Espera(String texto) {
        this();
        this.pbar.setIndeterminate(true);
        this.pbar.setStringPainted(true);
        this.pbar.setString(texto);
        init();
        setVisible(true);

    }

    public Espera(int qtdTotal) {
        this();
        this.qtTotal = qtdTotal;
        this.pbar.setIndeterminate(false);
        this.pbar.setStringPainted(true);
        this.pbar.setMaximum(this.qtTotal);
        init();
        setVisible(true);
    }

    public void setFont(String nomeFonte) {
        this.pbar.setFont(new java.awt.Font(nomeFonte, 0, 12));
    }

    public void setCorFundo(Color corFundo) {
        this.pbar.setBackground(corFundo);
    }

    public void setCorBarra(Color corBarra) {
        this.pbar.setForeground(corBarra);
    }

    public int getQtTotal() {
        return qtTotal;
    }

    public void setQtTotal(int qtTotal) {
        this.qtTotal = qtTotal;
    }

    public int getQtdeProcessado() {
        return qtdeProcessado;
    }

    public void setQtdeProcessado(int qtdeProcessado) {
        this.qtdeProcessado = qtdeProcessado;
        pbar.setValue(qtdeProcessado);
    }

    public void setTexto(String texto) {
        pbar.setString(texto);
    }

    private void init() {
        initialize();
        //Posiciona a janela   
        Toolkit thekit = this.getToolkit();
        Dimension dim = thekit.getScreenSize();
        int hor = (dim.width / 2) - 150;
        int ver = (dim.height / 2) - 100;
        this.setUndecorated(true);
        this.setBounds(hor, ver, 301, 151);
        this.setVisible(true);
        this.requestFocus();
    }

    /**
     * * Construção do painel * @return void
     */
    private void initialize() {
//        painel = new PanelBackground("/img/espera.png");
//        painel.setLayout(null);
//        painel.setBorder(Formato.GRID_BORDER);
//        painel.setBounds(1, 1, 300, 150);
        pbar.setBounds(155, 115, 120, 20);
        pbar.setVisible(true);
        painel.add(pbar);
        this.setContentPane(painel);
    }

}
