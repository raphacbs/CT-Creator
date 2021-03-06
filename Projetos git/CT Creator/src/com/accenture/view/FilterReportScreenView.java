/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.view;

import com.accenture.bean.TestCaseTSPropertiesBean;
import com.accenture.reports.BuildReport;
import com.accenture.ts.rn.SvnConnectionRN;
import com.accenture.ts.rn.TestCaseTSRN;
import com.accenture.util.ProjectSettings;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import net.sf.jasperreports.engine.JRException;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author Raphael
 */
public class FilterReportScreenView extends javax.swing.JInternalFrame {
    private ArrayList system;
    /**
     * Creates new form FilterReportScreenView
     */
    public FilterReportScreenView() {
        initComponents();
        loadComboTS();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBoxSystem = new javax.swing.JComboBox<>();
        jProgressBar1 = new javax.swing.JProgressBar();
        bntCancelar = new javax.swing.JButton();
        bntGerarRelatorio = new javax.swing.JButton();
        textStatus = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Relatório de CTs Cadastrados");

        jLabel1.setText("Sistema: ");

        jComboBoxSystem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos"}));
        jComboBoxSystem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSystemActionPerformed(evt);
            }
        });

        bntCancelar.setText("Cancelar");
        bntCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCancelarActionPerformed(evt);
            }
        });

        bntGerarRelatorio.setText("Gerar");
        bntGerarRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntGerarRelatorioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(bntGerarRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(bntCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxSystem, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxSystem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addComponent(textStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bntGerarRelatorio)
                    .addComponent(bntCancelar))
                .addGap(18, 18, 18)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxSystemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSystemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxSystemActionPerformed

    private void bntCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_bntCancelarActionPerformed

    private void bntGerarRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntGerarRelatorioActionPerformed
       System.setProperty("java.awt.headless", "true");
        new SwingWorker() {

            @Override
            protected Object doInBackground()  {
                setTextLabelStatus("Preparando...");
                try { 
                    geraRelatorio();
                } catch (SVNException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(FilterReportScreenView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(FilterReportScreenView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JRException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(FilterReportScreenView.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }

            @Override
            protected void done() {

            }

        }.run();
    }//GEN-LAST:event_bntGerarRelatorioActionPerformed
    
    private void setTextLabelStatus(String text){
        new SwingWorker() {

            @Override
            protected Object doInBackground() throws IOException, SVNException, JRException {
                
                textStatus.setText(text);
                
                
                return null;
            }

            @Override
            protected void done() {

            }

        }.run();
    }
    
    public void loadComboTS() {

        try {
            TestCaseTSRN testCaseRN = new TestCaseTSRN();
            system = testCaseRN.systemsTestCase();
           
            for (int i = 0; i < system.size(); i++) {
                jComboBoxSystem.addItem(system.get(i).toString());
            }
            
        } catch (SVNException ex) {
           JOptionPane.showMessageDialog(null, ex.getErrorMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    public void geraRelatorio() throws SVNException, IOException, JRException{
        List<TestCaseTSPropertiesBean> listTemp = new ArrayList<TestCaseTSPropertiesBean>();
        SvnConnectionRN svn = new SvnConnectionRN();
        if(jComboBoxSystem.getSelectedItem().toString().equalsIgnoreCase("TODOS")){
            for(int i = 0; i < system.size(); i++ ){
                setTextLabelStatus("Coletando dados do "+system.get(i));
                List<TestCaseTSPropertiesBean> list = svn.search(system.get(i).toString(), "");
                for (TestCaseTSPropertiesBean testCaseTSPropertiesBean : list) {
                    listTemp.add(testCaseTSPropertiesBean);
                }
            }
        }else{
           setTextLabelStatus("Coletando dados do "+jComboBoxSystem.getSelectedItem()); 
          listTemp=  svn.search(jComboBoxSystem.getSelectedItem().toString(), "");
        }
        
        Collections.sort(listTemp);
        
        Map<String,Object> parametros = new HashMap<String,Object>();  
        parametros.put("SISTEMA", jComboBoxSystem.getSelectedItem().toString());
        
        setTextLabelStatus("Prepando para gerar relatório"); 
        BuildReport.geraRelatorio(listTemp, parametros, ProjectSettings.FILE_NAME_REPORT_CTS);
        setTextLabelStatus("Relatório gerado."); 
    }
    
     public void centralizaJanela() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntCancelar;
    private javax.swing.JButton bntGerarRelatorio;
    private javax.swing.JComboBox<String> jComboBoxSystem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel textStatus;
    // End of variables declaration//GEN-END:variables
}
