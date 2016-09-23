/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.view;

import com.accenture.bean.FlowBean;
import javax.swing.DefaultListModel;
import com.accenture.ts.rn.FlowRN;
import com.accenture.ts.rn.TestCaseTSRN;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.SwingWorker;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author raphael.da.silva
 */
public class ManageflowsScreenView extends javax.swing.JInternalFrame {

    /**
     * Creates new form ManageflowsScreenView
     */
    public ManageflowsScreenView() throws IOException, SVNException {
        initComponents();
        new SwingWorker() {

                @Override
                protected Object doInBackground() {
                                      
                    loadComboTS();

                    return null;
                }

                @Override
                protected void done() {

                }

            }.run();
        
        
    }
    
    private void loadFlows(String name, String system){
        try{
            FlowRN flowRN = new FlowRN();
            DefaultListModel modelFlow = (DefaultListModel) listSelectTestCase.getModel();
            List<SVNDirEntry> list = flowRN.getEntries();       
            List<FlowBean> flowBeans = new ArrayList<FlowBean>();
            
            for (SVNDirEntry sVNDirEntry : list) {  
                flowBeans.add(flowRN.getFile(sVNDirEntry.getName()));           
            }
            
            for (int i =0; i < flowBeans.size();i++) {
                if(name.isEmpty() && system.isEmpty()){
                    modelFlow.addElement(flowBeans.get(i));
                }else if(name.isEmpty()){
                    if(flowBeans.get(i).getSystem().equalsIgnoreCase(system)){
                        modelFlow.addElement(flowBeans.get(i));
                    }
                }else if(system.isEmpty()){
                    if(flowBeans.get(i).getName().contains(name)){
                        modelFlow.addElement(flowBeans.get(i));
                    }
                }else{
                    if(flowBeans.get(i).getName().contains(name) && flowBeans.get(i).getSystem().equalsIgnoreCase(system)){
                        modelFlow.addElement(flowBeans.get(i));
                    }
                }
                    
                    
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void loadComboTS() {

        try {
            TestCaseTSRN testCaseRN = new TestCaseTSRN();
            ArrayList systems = testCaseRN.systemsTestCase();
            ArrayList fases = testCaseRN.faseCRTestCase();
            ArrayList complexidades = testCaseRN.complexidade();
            jComboBoxSistemas.addItem("");
            for (int i = 0; i < systems.size(); i++) {
                jComboBoxSistemas.addItem(systems.get(i).toString());
                fieldComboboxFlowSystem.addItem(systems.get(i).toString());
            }
            
            
            
        } catch (SVNException ex) {
           ex.printStackTrace();
        } catch (IOException ex) {
             ex.printStackTrace();
        }

    }
     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        DefaultListModel model =  new DefaultListModel();
        listSelectTestCase =  new JList(model);
        jTextFieldNome = new javax.swing.JTextField();
        jComboBoxSistemas = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        fieldTextFlowName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        fieldTextFlowDescription = new javax.swing.JTextField();
        fieldComboboxFlowSystem = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        fieldTextFlowId = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        DefaultListModel modelListTestCases =  new DefaultListModel();
        listTestCases = new JList(modelListTestCases);
        jLabel7 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Gestão de Funcionalidades");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        /*
        listSelectTestCase.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Teste" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        */
        listSelectTestCase.setToolTipText("");
        listSelectTestCase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                listSelectTestCaseMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(listSelectTestCase);

        jTextFieldNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNomeActionPerformed(evt);
            }
        });

        jLabel1.setText("Nome:");

        jLabel2.setText("Sistema:");

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Editar");

        jButton3.setText("Novo Fluxo");

        jLabel3.setText("Nome:");

        jLabel4.setText("Descrição:");

        jLabel5.setText("Sistema:");

        jLabel6.setText("ID:");

        fieldTextFlowId.setEditable(false);

        jScrollPane3.setViewportView(listTestCases);

        jLabel7.setText("Casos de Teste:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(14, 14, 14))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxSistemas, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(fieldTextFlowDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                                    .addComponent(fieldTextFlowName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fieldComboboxFlowSystem, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(fieldTextFlowId)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE))
                                .addGap(49, 49, 49)))))
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBoxSistemas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)))
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(fieldTextFlowName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(fieldTextFlowId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(fieldTextFlowDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fieldComboboxFlowSystem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(jButton2))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNomeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
        if(listSelectTestCase.getModel().getSize() > 0){
            DefaultListModel modelFlow = (DefaultListModel) listSelectTestCase.getModel();
            modelFlow.clear();
        }
        
        loadFlows(jTextFieldNome.getText(), jComboBoxSistemas.getSelectedItem().toString());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void listSelectTestCaseMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listSelectTestCaseMouseReleased
        loadFielsFlow(listSelectTestCase.getSelectedIndex());
    }//GEN-LAST:event_listSelectTestCaseMouseReleased
    
    private void loadFielsFlow(int i){
        DefaultListModel modelSelectTestCase = (DefaultListModel) listSelectTestCase.getModel();
        DefaultListModel modelFlow = (DefaultListModel) listTestCases.getModel();
        modelFlow.clear();
        
        
        FlowBean fb = (FlowBean) modelSelectTestCase.getElementAt(i);
        fieldTextFlowName.setText(fb.getName());
        fieldComboboxFlowSystem.setSelectedItem(fb.getSystem());
        fieldTextFlowDescription.setText(fb.getDescription());
        fieldTextFlowId.setText(fb.getId());
        for (String testCase : fb.getTestCases()) {
            modelFlow.addElement(testCase);
        }
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> fieldComboboxFlowSystem;
    private javax.swing.JTextField fieldTextFlowDescription;
    private javax.swing.JTextField fieldTextFlowId;
    private javax.swing.JTextField fieldTextFlowName;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBoxSistemas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldNome;
    private javax.swing.JList<String> listSelectTestCase;
    private javax.swing.JList<String> listTestCases;
    // End of variables declaration//GEN-END:variables
public void centralizaJanela() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }


}
