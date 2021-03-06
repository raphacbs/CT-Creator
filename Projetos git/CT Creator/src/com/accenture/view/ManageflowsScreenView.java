/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.view;

import com.accenture.bean.ButtonIconBean;
import com.accenture.bean.FlowBean;
import com.accenture.bean.TestCaseTSPropertiesBean;
import javax.swing.DefaultListModel;
import com.accenture.ts.rn.FlowRN;
import com.accenture.ts.rn.SvnConnectionRN;
import com.accenture.ts.rn.TestCaseTSRN;
import com.accenture.util.ProjectSettings;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import com.accenture.util.FunctiosDates;
import java.awt.Cursor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JDialog;

/**
 *
 * @author raphael.da.silva
 */
public class ManageflowsScreenView extends javax.swing.JInternalFrame {
    //variaveis locais
    private  boolean editing = false;
    /**
     * Creates new form ManageflowsScreenView
     */
    public ManageflowsScreenView() throws IOException, SVNException {
        initComponents();
        
        new SwingWorker() {

                @Override
                protected Object doInBackground() {
                                      
                    loadComboTS();
                    setEnableComponents(false);
                    addIconInButton();
                    
                    
                    return null;
                }

                @Override
                protected void done() {

                }

            }.run();
        
        
    }
    
    public ManageflowsScreenView(List<String> Cts){
        addCT(Cts);
    }
    
    private void loadFlows(String name, String system){
        try{
            labelQtdFluxos.setText("Total de fluxos:  "+0);
            FlowRN flowRN = new FlowRN();
            DefaultListModel modelFlow = (DefaultListModel) listSelectTestCase.getModel();
            List<SVNDirEntry> list = flowRN.getEntries();       
            List<FlowBean> flowBeans = new ArrayList<FlowBean>();
            refreshLabelStatus("Aguarde, atualizando lista de fluxos...");
            
            for (SVNDirEntry sVNDirEntry : list) {  
                flowBeans.add(flowRN.getFile(sVNDirEntry.getName()));
                
            }
            
            Collections.sort(flowBeans);
            
            
            
            for (int i =0; i < flowBeans.size();i++) {
                if(name.isEmpty() && system.isEmpty()){
                    modelFlow.addElement(flowBeans.get(i));
                    refreshLabelStatus("Carregando: "+flowBeans.get(i).getName());
                }else if(name.isEmpty()){
                    if(flowBeans.get(i).getSystem().equalsIgnoreCase(system)){
                        modelFlow.addElement(flowBeans.get(i));
                        refreshLabelStatus("Carregando: "+flowBeans.get(i).getName());
                    }
                }else if(system.isEmpty()){
                    if(flowBeans.get(i).getName().contains(name)){
                        modelFlow.addElement(flowBeans.get(i));
                        refreshLabelStatus("Carregando: "+flowBeans.get(i).getName());
                    }
                }else{
                    if(flowBeans.get(i).getName().contains(name) && flowBeans.get(i).getSystem().equalsIgnoreCase(system)){
                        modelFlow.addElement(flowBeans.get(i));
                        refreshLabelStatus("Carregando: "+flowBeans.get(i).getName());
                    }
                }
                
            }
            listSelectTestCase.repaint();
            labelQtdFluxos.setText("Total de fluxos:  "+modelFlow.size());
            refreshLabelStatus("Lista de fluxos carregada.");
            
        }catch(Exception ex){
            addLogTextArea(ex);
            Logger.getLogger(ManageflowsScreenView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao desconhecido, \nverifique mais detalhes no botão de log.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
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
           addLogTextArea(ex);
        } catch (IOException ex) {
            addLogTextArea(ex);
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        dialogLog = new javax.swing.JDialog();
        jScrollPane5 = new javax.swing.JScrollPane();
        statusTextArea = new javax.swing.JTextArea();
        bntLimpar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        DefaultListModel model =  new DefaultListModel();
        listSelectTestCase =  new JList(model);
        jTextFieldNome = new javax.swing.JTextField();
        jComboBoxSistemas = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        bntSearch = new javax.swing.JButton();
        bntEditOrCancel = new javax.swing.JButton();
        bntNew = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        fieldTextFlowName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        fieldComboboxFlowSystem = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        fieldTextFlowId = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        DefaultListModel modelListTestCases =  new DefaultListModel();
        listTestCases = new JList(modelListTestCases);
        jLabel7 = new javax.swing.JLabel();
        bntDeleteFlow = new javax.swing.JButton();
        bntSubir = new javax.swing.JButton();
        bntDesce = new javax.swing.JButton();
        bntAdd = new javax.swing.JButton();
        bntExcluir = new javax.swing.JButton();
        bntSave = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        fieldTextFlowDescription = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        labelQtdCts = new javax.swing.JLabel();
        labelStatus = new javax.swing.JLabel();
        labelQtdFluxos = new javax.swing.JLabel();

        jMenuItem1.setText("Teste");
        jPopupMenu1.add(jMenuItem1);

        dialogLog.setTitle("Log");
        dialogLog.setSize(new java.awt.Dimension(400, 600));
        dialogLog.setType(java.awt.Window.Type.POPUP);

        statusTextArea.setEditable(false);
        statusTextArea.setColumns(20);
        statusTextArea.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        statusTextArea.setForeground(new java.awt.Color(255, 0, 51));
        statusTextArea.setRows(5);
        jScrollPane5.setViewportView(statusTextArea);

        bntLimpar.setText("Limpar");
        bntLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntLimparActionPerformed(evt);
            }
        });
        bntLimpar.setEnabled(true);
        bntLimpar.setVisible(true);

        javax.swing.GroupLayout dialogLogLayout = new javax.swing.GroupLayout(dialogLog.getContentPane());
        dialogLog.getContentPane().setLayout(dialogLogLayout);
        dialogLogLayout.setHorizontalGroup(
            dialogLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
            .addGroup(dialogLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(dialogLogLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(bntLimpar)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        dialogLogLayout.setVerticalGroup(
            dialogLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
            .addGroup(dialogLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(dialogLogLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(bntLimpar)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setForeground(java.awt.Color.black);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Gestão de Funcionalidades");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

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
        listSelectTestCase.add(jPopupMenu1);

        jTextFieldNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNomeActionPerformed(evt);
            }
        });

        jLabel1.setText("Nome:");

        jLabel2.setText("Sistema:");

        bntSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSearchActionPerformed(evt);
            }
        });

        bntEditOrCancel.setText("Editar");
        bntEditOrCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntEditOrCancelActionPerformed(evt);
            }
        });

        bntNew.setText("Novo");
        bntNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntNewActionPerformed(evt);
            }
        });

        jLabel3.setText("Nome:");

        jLabel4.setText("Descrição:");

        jLabel5.setText("Sistema:");

        jLabel6.setText("ID:");

        fieldTextFlowId.setEditable(false);

        jScrollPane3.setViewportView(listTestCases);

        jLabel7.setText("Casos de Teste:");

        bntDeleteFlow.setText("Excluir");
        bntDeleteFlow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntDeleteFlowActionPerformed(evt);
            }
        });

        bntSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSubirActionPerformed(evt);
            }
        });

        bntDesce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntDesceActionPerformed(evt);
            }
        });

        bntAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntAddActionPerformed(evt);
            }
        });

        bntExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntExcluirActionPerformed(evt);
            }
        });

        bntSave.setText("Salvar");
        bntSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSaveActionPerformed(evt);
            }
        });

        fieldTextFlowDescription.setColumns(20);
        fieldTextFlowDescription.setRows(5);
        jScrollPane2.setViewportView(fieldTextFlowDescription);

        jButton1.setText("LOG");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        labelQtdCts.setText("Total de CTs:");

        labelStatus.setText("Status:");

        labelQtdFluxos.setText("Total de fluxos:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                            .addComponent(bntSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelQtdFluxos, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fieldTextFlowName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fieldTextFlowId)
                            .addComponent(fieldComboboxFlowSystem, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelStatus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelQtdCts, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bntExcluir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bntAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bntDesce, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bntSubir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bntNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bntSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bntEditOrCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bntDeleteFlow)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
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
                            .addComponent(bntSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelQtdFluxos))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(fieldTextFlowName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6)
                                .addComponent(fieldTextFlowId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(fieldComboboxFlowSystem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(labelQtdCts))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bntSubir, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bntDesce, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bntAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bntExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelStatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bntNew)
                            .addComponent(bntEditOrCancel)
                            .addComponent(bntDeleteFlow)
                            .addComponent(bntSave)
                            .addComponent(jButton1))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNomeActionPerformed

    private void bntSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSearchActionPerformed
//        new SwingWorker() {
//
//            @Override
//            protected Object doInBackground() throws Exception {
                refreshLabelStatus("Aguarde, atualizando lista de fluxos...");
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if (listSelectTestCase.getModel().getSize() > 0) {
                    DefaultListModel modelFlow = (DefaultListModel) listSelectTestCase.getModel();
                    modelFlow.clear();
                }
                cleanFilds();
                loadFlows(jTextFieldNome.getText(), jComboBoxSistemas.getSelectedItem().toString());
                listSelectTestCase.repaint();
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

//                return null;
//            }
//
//            @Override
//            protected void done() {
//                listSelectTestCase.repaint();
//                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//            }
//
//        }.execute();
        
        
        
        
       
    }//GEN-LAST:event_bntSearchActionPerformed

    private void listSelectTestCaseMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listSelectTestCaseMouseReleased
        if (listSelectTestCase.getSelectedIndices().length == 1) {
            loadFielsFlow(listSelectTestCase.getSelectedIndex());
            bntDeleteFlow.setEnabled(true);
            bntEditOrCancel.setEnabled(true);
            
        } else {
            cleanFilds();
            bntDeleteFlow.setEnabled(true);
        }
        refreshQTDCTs();
    }//GEN-LAST:event_listSelectTestCaseMouseReleased

    private void bntEditOrCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntEditOrCancelActionPerformed
        new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                if (listSelectTestCase.getSelectedValue() != null) {
                    if (listSelectTestCase.getSelectedIndices().length == 1) {
                        if (bntEditOrCancel.getText().equals("Editar")) {
                            if (modeEdit(true)) {
                                refreshLabelStatus("Editando fluxo...");
                                addLogTextArea("Editando fluxo...");
                                bntNew.setEnabled(false);
                                bntEditOrCancel.setText("Cancelar");
                                bntSave.setEnabled(true);
                                bntDeleteFlow.setEnabled(true);
                                editing = true;
                            }
                        } else if (bntEditOrCancel.getText().equals("Cancelar")) {
                            if (modeEdit(false)) {
                                
                                bntNew.setEnabled(true);
                                bntSave.setEnabled(false);
                                bntEditOrCancel.setText("Editar");
                                bntEditOrCancel.setEnabled(true);
                                bntDeleteFlow.setEnabled(true);
                                editing = false;
                                loadFielsFlow(listSelectTestCase.getSelectedIndex());
                                refreshLabelStatus("Edição cancelada...");
                                addLogTextArea("Edição cancelada...");
                            }
                        }
                    } else {
                        cleanFilds();
                        refreshLabelStatus("Selecione apenas um fluxo");
                        JOptionPane.showMessageDialog(null, "Por favor, selecione apenas uma funcionalidade para edita-lá", "Atenção", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    refreshLabelStatus("Selecione um fluxo");
                    JOptionPane.showMessageDialog(null, "Por favor, selecione uma funcionalidade para edita-lá", "Atenção", JOptionPane.WARNING_MESSAGE);
                }

                return null;
            }

            @Override
            protected void done() {
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }

        }.execute();
    }//GEN-LAST:event_bntEditOrCancelActionPerformed

    private void bntDeleteFlowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntDeleteFlowActionPerformed
               
        try {
            refreshLabelStatus("Aguarde, excluindo fluxo...");
            if (listSelectTestCase.getSelectedIndex() != -1) {
                if(JOptionPane.showConfirmDialog(null, "Deseja excluir o(s) fluxos selecionados?", "Atenção", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                    getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    deleteFlows();
                    DefaultListModel modelSelectTestCase = (DefaultListModel) listSelectTestCase.getModel();
                    modelSelectTestCase.clear();
                    loadFlows(jTextFieldNome.getText(), jComboBoxSistemas.getSelectedItem().toString());
                    cleanFilds();
                    getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    
                }
                
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, selecione uma funcionalidade para exclui-lá", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            addLogTextArea(ex);
            Logger.getLogger(ManageflowsScreenView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao desconhecido, \nverifique mais detalhes no botão de log.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_bntDeleteFlowActionPerformed

    private void bntNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntNewActionPerformed
        newFlow();
    }//GEN-LAST:event_bntNewActionPerformed

    private void bntSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSaveActionPerformed
       
       new SwingWorker() {
            
            

            @Override
            protected Object doInBackground() throws Exception {
               getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
               save();

                
                return null;
            }

            @Override
            protected void done() {
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
            
          

        }.execute();
    }//GEN-LAST:event_bntSaveActionPerformed

    private void bntSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSubirActionPerformed
        moveUpTestCases();
    }//GEN-LAST:event_bntSubirActionPerformed

    private void bntDesceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntDesceActionPerformed
        moveDownTestCases();
    }//GEN-LAST:event_bntDesceActionPerformed

    private void bntExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntExcluirActionPerformed
        
         new SwingWorker() {
            
            

            @Override
            protected Object doInBackground() throws Exception {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                deleteTestCases();
                refreshQTDCTs();

                
                return null;
            }

            @Override
            protected void done() {
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
            
          

        }.execute();
    }//GEN-LAST:event_bntExcluirActionPerformed

    private void bntAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntAddActionPerformed
        
        new SwingWorker() {
            
            

            @Override
            protected Object doInBackground() throws Exception {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            openScreenAddCT();  

                
                return null;
            }

            @Override
            protected void done() {
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
            
          

        }.execute();
        
    }//GEN-LAST:event_bntAddActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if(editing){
            JOptionPane.showMessageDialog(null, "Cancele ou termine a edição para fechar a janela", "Informação", JOptionPane.INFORMATION_MESSAGE);
        }else{
            this.dispose();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       dialogLog.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void bntLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntLimparActionPerformed
        statusTextArea.setText("");
    }//GEN-LAST:event_bntLimparActionPerformed
    
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
    
    private void setEnableButtons(boolean enable){
        bntAdd.setEnabled(enable);
        bntExcluir.setEnabled(enable);
        bntSubir.setEnabled(enable);
        bntDesce.setEnabled(enable);
        bntEditOrCancel.setEnabled(enable);
        bntDeleteFlow.setEnabled(enable);
        bntNew.setEnabled(enable);
        bntSearch.setEnabled(enable);
        
    }
    
    private void setEnableFields(boolean enable){
        jTextFieldNome.setEnabled(enable);
        jComboBoxSistemas.setEnabled(enable);
        listSelectTestCase.setEnabled(enable);
        fieldComboboxFlowSystem.setEnabled(enable);
        fieldTextFlowDescription.setEnabled(enable);
        fieldTextFlowName.setEnabled(enable);
        listTestCases.setEnabled(enable);   
    }
    
    private void setEnableComponentsSearch(boolean enable){
        bntSearch.setEnabled(enable);
        jTextFieldNome.setEnabled(enable);
        jComboBoxSistemas.setEnabled(enable);
        listSelectTestCase.setEnabled(enable);        
    }
    
    private void setEnableComponents(boolean enable){
        fieldComboboxFlowSystem.setEnabled(enable);
        fieldTextFlowDescription.setEnabled(enable);
        fieldTextFlowName.setEnabled(enable);
        listTestCases.setEnabled(enable);
        bntAdd.setEnabled(enable);
        bntExcluir.setEnabled(enable);
        bntSubir.setEnabled(enable);
        bntDesce.setEnabled(enable);
        bntEditOrCancel.setEnabled(enable);
        bntDeleteFlow.setEnabled(enable);
        bntSave.setEnabled(enable);
    }
    
    private boolean modeEdit(boolean active){
        try {
            

            FlowRN flowRN = new FlowRN();
            DefaultListModel modelSelectTestCase = (DefaultListModel) listSelectTestCase.getModel();
            FlowBean fb = (FlowBean) modelSelectTestCase.getElementAt(listSelectTestCase.getSelectedIndex());
            String nameFile = fb.getId()+ProjectSettings.EXTENSION_FILE_PROPERTY;
            
            if(active){
                if(flowRN.verifyExistFile(nameFile)){
                //tenta bloquear o arquivo
                if(flowRN.lockFile(nameFile)){
                    setEnableComponents(active);
                    setEnableComponentsSearch(!active);
                    editing = true;
                    return true;
                }else{
                    JOptionPane.showMessageDialog(null, "A funicionalidade está bloqueada pelo usuário: "+flowRN.getUserLock(fb.getId()+ProjectSettings.EXTENSION_FILE_PROPERTY)+"\n"
                            + "para editar tente novamente mais tarde! ", "Atenção", JOptionPane.WARNING_MESSAGE);
                    editing = false;
                    return false;
                }
                }else{
                    JOptionPane.showMessageDialog(null, "A funicionalidade foi excluída, favor atualize a lista. ", "Atenção", JOptionPane.WARNING_MESSAGE);
                    editing = false;
                    return false;
                }
                
            }else{
                if (flowRN.unLockFile(nameFile)) {
                    setEnableComponents(active);
                    setEnableComponentsSearch(!active);
                    editing = false;
                    return true;
                }else{
                     JOptionPane.showMessageDialog(null, "Não foi possível desbloquear o arquivo: "+flowRN.getUserLock(fb.getId()+ProjectSettings.EXTENSION_FILE_PROPERTY), "Atenção", JOptionPane.WARNING_MESSAGE);
                     editing = true;
                     return false;
                }
            }
                
            
            
        } catch (IOException ex) {
            addLogTextArea(ex);
            Logger.getLogger(ManageflowsScreenView.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao manipular algum arquivo, \nverifique mais detalhes no botão de log.", "Erro", JOptionPane.ERROR_MESSAGE);
            editing = false;
            return false;

        } catch (SVNException ex) {
            addLogTextArea(ex);
            Logger.getLogger(ManageflowsScreenView.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu um erro com o SVN, \nverifique mais detalhes no botão de log.", "Erro", JOptionPane.ERROR_MESSAGE);
            editing = false;
            return false;

        }   catch (Exception ex) {
            addLogTextArea(ex);
            Logger.getLogger(ManageflowsScreenView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao desconhecido, \nverifique mais detalhes no botão de log.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            editing = false;
            return false;
        }       
        
    }
    
    private void cleanFilds(){
        
        fieldTextFlowDescription.setText("");
        fieldTextFlowName.setText("");
        fieldTextFlowId.setText("");
        DefaultListModel model = (DefaultListModel) listTestCases.getModel();
        model.clear();
    }
    
    private boolean save() {
        try {
            DefaultListModel model = (DefaultListModel) listTestCases.getModel();
            if(!fieldTextFlowName.getText().isEmpty() || model.isEmpty() ){
            FlowRN flowRN = new FlowRN();
            FlowBean fb = new FlowBean();
            
            DefaultListModel modelSelectionTestCase = (DefaultListModel) listSelectTestCase.getModel();
            
            List<String> testCases = new ArrayList<String>();

            for (int i = 0; i < model.size(); i++) {
                testCases.add(model.getElementAt(i).toString());
            }
            fb.setId(fieldTextFlowId.getText());
            fb.setDescription(fieldTextFlowDescription.getText());
            fb.setName(fieldTextFlowName.getText());
            fb.setSystem(fieldComboboxFlowSystem.getSelectedItem().toString());
            
            

            fb.setTestCases(testCases);

            if (fieldTextFlowId.getText() == null || fieldTextFlowId.getText().equals("")) {
                refreshLabelStatus("Salvando novo fluxo...");
                fb.setRegisterDate(FunctiosDates.getDateActual());
                String id = flowRN.saveFile(null, fb).replace(ProjectSettings.EXTENSION_FILE_PROPERTY, "");
                if (listSelectTestCase.getModel().getSize() > 0) {
                    modelSelectionTestCase.clear();
                }
                loadFlows(jTextFieldNome.getText(), jComboBoxSistemas.getSelectedItem().toString());
                //Action Item 18037 - Raphael - Inicio
                for (int i = 0; i < modelSelectionTestCase.getSize(); i++) {
                    if (((FlowBean) modelSelectionTestCase.getElementAt(i)).getId().equals(id)) {
                        listSelectTestCase.getSelectionModel().setSelectionInterval(i, i);
                        loadFielsFlow(i);
                    }
                }
//                cleanFilds();
                //Action Item 18037 - Raphael - fim
                setEnableComponents(false);
                setEnableComponentsSearch(true);
                bntNew.setText("Novo");
                editing=false;
                refreshLabelStatus("novo fluxo cadastrado.");
                addLogTextArea("Novo fluxo cadastrado"+fb.toString());
            } else {
                refreshLabelStatus("salvando alterações do fluxo.");
                addLogTextArea("salvando alterações do fluxo."+fb.toString());
                fb.setRegisterDate(((FlowBean)modelSelectionTestCase.getElementAt(listSelectTestCase.getSelectedIndex())).getRegisterDate());
                flowRN.saveFile(fb.getId() + ProjectSettings.EXTENSION_FILE_PROPERTY, fb).replace(ProjectSettings.EXTENSION_FILE_PROPERTY, "");
                if (listSelectTestCase.getModel().getSize() > 0) {
                    modelSelectionTestCase.clear();
                }
                loadFlows(jTextFieldNome.getText(), jComboBoxSistemas.getSelectedItem().toString());
                for (int i = 0; i < modelSelectionTestCase.getSize(); i++) {
                    if (((FlowBean) modelSelectionTestCase.getElementAt(i)).getId().equals(fb.getId())) {
                        listSelectTestCase.getSelectionModel().setSelectionInterval(i, i);
                        loadFielsFlow(i);
                    }
                }
                loadFielsFlow(listSelectTestCase.getSelectedIndex());
                modeEdit(false);
                bntEditOrCancel.setText("Editar");
                refreshLabelStatus("alterações do fluxo concluidas.");
                addLogTextArea("alterações do fluxo concluidas.."+fb.toString());
            }
           
            bntNew.setEnabled(true);
            bntDeleteFlow.setEnabled(true);
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!", "Informação", JOptionPane.INFORMATION_MESSAGE);

            return true;
            }else{
                JOptionPane.showMessageDialog(null, "Campo Obrigatórios não preenchidos, \nverifique o campo nome e o CTs", "Alerta", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (SVNException ex) {
            refreshLabelStatus("Erro na tentativa de salvar o fluxo, verifique detalhes no log");
            addLogTextArea(ex);
            Logger.getLogger(ManageflowsScreenView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocorreu um erro com o SVN, \nverifique mais detalhes no botão de log.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return false;

        } catch (IOException ex) {
            refreshLabelStatus("Erro na tentativa de salvar o fluxo, verifique detalhes no log");
            addLogTextArea(ex);
            Logger.getLogger(ManageflowsScreenView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao manipular algum arquivo, \nverifique mais detalhes no botão de log.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return false;
        } catch (Exception ex) {
            refreshLabelStatus("Erro na tentativa de salvar o fluxo, verifique detalhes no log");
            addLogTextArea(ex);
            Logger.getLogger(ManageflowsScreenView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao desconhecido, \nverifique mais detalhes no botão de log.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return false;
        }
    }
    
    private void newFlow(){
        try {
            
            if (bntNew.getText().equals("Novo")) {
                setEnableFields(true);
                setEnableComponentsSearch(false);
                bntDeleteFlow.setEnabled(false);
                bntEditOrCancel.setEnabled(false);
                bntSave.setEnabled(true);
                bntAdd.setEnabled(true);
                bntExcluir.setEnabled(true);
                bntSubir.setEnabled(true);
                bntDesce.setEnabled(true);
                bntNew.setText("Cancelar");
                cleanFilds();
                addLogTextArea("usuário iniciou cadastro de um novo fluxo.");
                refreshLabelStatus("usuário iniciou cadastro de um novo fluxo.");

            } else {
                setEnableFields(false);
                setEnableComponentsSearch(true);
                bntDeleteFlow.setEnabled(true);
                bntEditOrCancel.setEnabled(true);
                bntSave.setEnabled(false);
                bntAdd.setEnabled(false);
                bntExcluir.setEnabled(false);
                bntSubir.setEnabled(false);
                bntDesce.setEnabled(false);
                bntNew.setText("Novo");
                addLogTextArea("usuário cancelou cadastro de um novo fluxo.");
                refreshLabelStatus("usuário cancelou cadastro de um novo fluxo.");

            }
        } catch (Exception ex) {
            addLogTextArea(ex);
            Logger.getLogger(ManageflowsScreenView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao desconhecido, \nverifique mais detalhes no botão de log.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();

        }
        
    }
    
    private void moveUpTestCases() {
        try {
            DefaultListModel model = (DefaultListModel) listTestCases.getModel();
            Object obj = listTestCases.getSelectedValue();
            if (obj != null) {
                int index = listTestCases.getSelectedIndex();
                if (index != 0) {
                    model.remove(index);
                    model.add(index - 1, obj);
                    listTestCases.setSelectedIndex(index - 1);
                }
            }
        } catch (Exception ex) {
            addLogTextArea(ex);
            Logger.getLogger(ManageflowsScreenView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao desconhecido, \nverifique mais detalhes no botão de log.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();

        }
    }
    
     private void moveDownTestCases() {
        try {
            DefaultListModel model = (DefaultListModel) listTestCases.getModel();
            Object obj = listTestCases.getSelectedValue();
            int size = model.size()-1;
            if (obj != null) {
                int index = listTestCases.getSelectedIndex();
                if (size != index) {
                    model.remove(index);
                    model.add(index + 1, obj);
                    listTestCases.setSelectedIndex(index + 1);
                }
            }
        } catch (Exception ex) {
            addLogTextArea(ex);
            Logger.getLogger(ManageflowsScreenView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao desconhecido, \nverifique mais detalhes no botão de log.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();

        }
    }
     private void deleteTestCases(){
         try {
         DefaultListModel model = (DefaultListModel) listTestCases.getModel();
         Object [] itens = listTestCases.getSelectedValues();
         for(int i =0; i < itens.length; i++){
             model.removeElement(itens[i]);
         }
         } catch (Exception ex) {
             addLogTextArea(ex);
            Logger.getLogger(ManageflowsScreenView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao desconhecido, \nverifique mais detalhes no botão de log.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();

        }
         
     }
     
     private void openScreenAddCT(){
        try {
            refreshLabelStatus("Abrindo janela de cts...");
            ChooseTestCaseTsToFlowScreenView view = new ChooseTestCaseTsToFlowScreenView(fieldComboboxFlowSystem.getSelectedItem().toString(), this,null,true);
//            view.centralizaJanela();
//            view.setVisible(true);
            
        } catch (ClassNotFoundException ex) {
            refreshLabelStatus("Erro na tentativa de abrir janelas cts, verifique detalhes no log");
            addLogTextArea(ex);
            Logger.getLogger(ManageflowsScreenView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao desconhecido, \nverifique mais detalhes no botão de log.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
     }
     
     public void addCT(List<String> cts){
         DefaultListModel model = (DefaultListModel) listTestCases.getModel();
         for (String ct : cts) {
//             fieldTextFlowDescription.setText(fieldTextFlowDescription.getText()+"\n"+ct.substring(ct.indexOf("-")));
             //ct.substring(0,ct.indexOf("-"))
             model.addElement(ct);
         }
         refreshQTDCTs();
     }
     
    private void addLogTextArea(Exception ex){
         StringWriter sw = new StringWriter();
         PrintWriter pw = new PrintWriter(sw);
         ex.printStackTrace(pw);
         String exceptionText = sw.toString();                       
         statusTextArea.setText(statusTextArea.getText()+"\n"+exceptionText);
    }
    
    private void addLogTextArea(String text){
        statusTextArea.setText(statusTextArea.getText()+"\n"+text);
    }
    
    private void addIconInButton() {
        ButtonIconBean iconBean = new ButtonIconBean();
        bntSearch.setIcon(iconBean.getIconBntSearchCt());
        bntSubir.setIcon(iconBean.getIconBntMoveStepTop());
        bntDesce.setIcon(iconBean.getIconBntMoveStepBottom());
        bntSave.setIcon(iconBean.getIconBntSaveMinimum());
        bntNew.setIcon(iconBean.getIconBntAddNewCt());
        bntAdd.setIcon(iconBean.getIconBntAddNewStep());
        bntExcluir.setIcon(iconBean.getIconBntRemoveStep());
        bntDeleteFlow.setIcon(iconBean.getIconBntRemoveCt());
    }
    
    private void refreshQTDCTs(){
        DefaultListModel model = (DefaultListModel) listTestCases.getModel();
        labelQtdCts.setText("Quantidade de CTs: "+model.getSize());
    }
        
    private void deleteFlows() throws IOException, SVNException{
        List<FlowBean> flows = new ArrayList<FlowBean>();
        Object[] objs = listSelectTestCase.getSelectedValues();
        
        for (Object obj : objs) {
            flows.add((FlowBean)obj);
        }    
        
        FlowRN flowRN = new FlowRN();
        
        String retorno = flowRN.deleteFlow(flows);
        if(retorno!=null){
            refreshLabelStatus(retorno);
            addLogTextArea(retorno);
            JOptionPane.showMessageDialog(null, ""+retorno, "Exclusão do fluxo não permitida", JOptionPane.WARNING_MESSAGE);
        }
        
    }
    
    private void refreshLabelStatus(String text){
        new SwingWorker() {
            
            

            @Override
            protected Object doInBackground() throws Exception {
                labelStatus.setText("Status: "+text);   
                
                
                return null;
            }

            @Override
            protected void done() {
                labelStatus.repaint();
            }
            
          

        }.execute();
    }
    
    
        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntAdd;
    private javax.swing.JButton bntDeleteFlow;
    private javax.swing.JButton bntDesce;
    private javax.swing.JButton bntEditOrCancel;
    private javax.swing.JButton bntExcluir;
    private javax.swing.JButton bntLimpar;
    private javax.swing.JButton bntNew;
    private javax.swing.JButton bntSave;
    private javax.swing.JButton bntSearch;
    private javax.swing.JButton bntSubir;
    private javax.swing.JDialog dialogLog;
    private javax.swing.JComboBox<String> fieldComboboxFlowSystem;
    private javax.swing.JTextArea fieldTextFlowDescription;
    private javax.swing.JTextField fieldTextFlowId;
    private javax.swing.JTextField fieldTextFlowName;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBoxSistemas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldNome;
    private javax.swing.JLabel labelQtdCts;
    private javax.swing.JLabel labelQtdFluxos;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JList<String> listSelectTestCase;
    private javax.swing.JList<String> listTestCases;
    private javax.swing.JTextArea statusTextArea;
    // End of variables declaration//GEN-END:variables
public void centralizaJanela() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    


}
