/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.view;

import com.accenture.bean.ButtonIconBean;
import com.accenture.control.ManipulaDadosSQLite;
import com.accenture.bean.Plano;
import com.accenture.bean.SystemBean;
import com.accenture.bean.TestCaseTSPropertiesBean;
import com.accenture.bean.TesteCaseTSBean;
import com.accenture.ts.rn.SvnConnectionRN;
import com.accenture.ts.rn.TestCaseTSRN;
import com.accenture.view.EditScreenTSView;
import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;
import java.awt.Dimension;
import java.awt.Frame;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author raphael.da.silva
 */
public class FilterTestCaseScreenTSView1 extends java.awt.Dialog {

    private ManipulaDadosSQLite banco = new ManipulaDadosSQLite();
    private EditScreenTSView guiEditCt;
    private ButtonIconBean icon;
    private static String fase ;

    /**
     * Creates new form GUIFiltroCT
     */
    public FilterTestCaseScreenTSView1(java.awt.Frame parent, boolean modal, String fase) throws SQLException, ClassNotFoundException {
        
        super(parent, modal);
        this.fase = fase;
        initComponents();

    }

    public FilterTestCaseScreenTSView1(final EditScreenTSView guiEditCt, java.awt.Frame parent, boolean modal,String fase, boolean first) throws IOException, ClassNotFoundException, SQLException {
        super(parent, modal);
            this.fase = fase;
        this.setResizable(false);
        initComponents();
        //loadComboTS();
        if(first)
            loadFieldsTSDB("","",0);
        else
            loadFieldsTSDB(guiEditCt.getFilter().getId()+"", guiEditCt.getFilter().getName(), guiEditCt.getFilter().getIdSystem());
        this.guiEditCt = guiEditCt;
        new SwingWorker() {
            JDialog aguarde;
            final Frame GUIPrincipal = new MainScreenView();

            {
                this.aguarde = new WaitScreenView((JFrame) GUIPrincipal, true);
            }

            @Override
            protected Object doInBackground() throws Exception {
                aguarde.setLocationRelativeTo(GUIPrincipal);
                aguarde.setVisible(true);

                return null;
            }

            @Override
            protected void done() {
                aguarde.dispose();
            }

        }.execute();

        /*
        **Carregando os ícones dos botões:
         */
        icon = new ButtonIconBean();
        this.addIconInButton();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        textNomeCT = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jComboSistemasTS = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        textId = new javax.swing.JTextField();
        bntCancelar = new javax.swing.JButton();
        bntPesquisar = new javax.swing.JButton();

        setTitle("Filtro - Caso de Teste");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(415, 300));

        jLabel50.setFont(new java.awt.Font("Graphik", 0, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(51, 51, 51));
        jLabel50.setText("NOME CT:");

        textNomeCT.setFont(new java.awt.Font("Graphik", 0, 12)); // NOI18N
        textNomeCT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        textNomeCT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textNomeCTFocusLost(evt);
            }
        });
        textNomeCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNomeCTActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Graphik", 0, 12)); // NOI18N
        jLabel33.setText("SISTEMA:");

        jComboSistemasTS.setFont(new java.awt.Font("Graphik", 0, 12)); // NOI18N
        jComboSistemasTS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboSistemasTSActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Graphik", 0, 12)); // NOI18N
        jLabel34.setText("ID:");

        textId.setFont(new java.awt.Font("Graphik", 0, 12)); // NOI18N
        textId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textIdFocusLost(evt);
            }
        });
        textId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textIdActionPerformed(evt);
            }
        });

        bntCancelar.setFont(new java.awt.Font("Graphik", 0, 12)); // NOI18N
        bntCancelar.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\22x22\\cancel.png")); // NOI18N
        bntCancelar.setText("Cancelar");
        bntCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntCancelarMouseClicked(evt);
            }
        });

        bntPesquisar.setFont(new java.awt.Font("Graphik", 0, 12)); // NOI18N
        bntPesquisar.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\24x24\\Search.png")); // NOI18N
        bntPesquisar.setText("Pesquisar");
        bntPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntPesquisarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50)
                    .addComponent(jLabel1)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(bntPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bntCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
                    .addComponent(textNomeCT, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboSistemasTS, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textId, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(25, 25, 25))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel50)
                    .addComponent(textNomeCT, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(textId, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(jComboSistemasTS, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bntCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntPesquisar))
                .addGap(195, 195, 195))
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void bntPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntPesquisarActionPerformed
        // TODO add your handling code here:
        SwingWorker sw = new SwingWorker() {
            JDialog aguarde;
            final Frame GUIPrincipal = new MainScreenView();

            {
                this.aguarde = new WaitScreenView((JFrame) GUIPrincipal, true);
            }

            boolean filtro = false;

            @Override
            protected Object doInBackground() throws Exception {
                aguarde.setLocationRelativeTo(GUIPrincipal);
                aguarde.setVisible(true);
                
                //searchCT(textId.getText(), textNomeCT.getText());
                searchCTDB(textId.getText(), textNomeCT.getText());

                return null;

            }

            @Override
            protected void done() {
                aguarde.dispose();
            }
        };
        sw.execute();
        this.dispose();
    }//GEN-LAST:event_bntPesquisarActionPerformed

    private void bntCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntCancelarMouseClicked
        this.dispose();
    }//GEN-LAST:event_bntCancelarMouseClicked

    private void textIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textIdActionPerformed
        // TODO add your handling code here:
        SwingWorker sw = new SwingWorker() {
            JDialog aguarde;
            final Frame GUIPrincipal = new MainScreenView();

            {
                this.aguarde = new WaitScreenView((JFrame) GUIPrincipal, true);
            }

            boolean filtro = false;

            @Override
            protected Object doInBackground() throws Exception {
                aguarde.setLocationRelativeTo(GUIPrincipal);
                aguarde.setVisible(true);
                searchCT(textId.getText(), textNomeCT.getText());

                return null;

            }

            @Override
            protected void done() {
                aguarde.dispose();
            }
        };
        sw.execute();
        this.dispose();
    }//GEN-LAST:event_textIdActionPerformed

    private void textIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textIdFocusLost

    }//GEN-LAST:event_textIdFocusLost

    private void jComboSistemasTSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboSistemasTSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboSistemasTSActionPerformed

    private void textNomeCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNomeCTActionPerformed
        SwingWorker sw = new SwingWorker() {
            JDialog aguarde;
            final Frame GUIPrincipal = new MainScreenView();

            {
                this.aguarde = new WaitScreenView((JFrame) GUIPrincipal, true);
            }

            boolean filtro = false;

            @Override
            protected Object doInBackground() throws Exception {
                aguarde.setLocationRelativeTo(GUIPrincipal);
                aguarde.setVisible(true);
                searchCT(textId.getText(), textNomeCT.getText());

                return null;

            }

            @Override
            protected void done() {
                aguarde.dispose();
            }
        };
        sw.execute();
        this.dispose();
    }//GEN-LAST:event_textNomeCTActionPerformed

    private void textNomeCTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textNomeCTFocusLost

    }//GEN-LAST:event_textNomeCTFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FilterTestCaseScreenTSView1 dialog = null;
                try {
                    dialog = new FilterTestCaseScreenTSView1(new java.awt.Frame(), true, fase);
                } catch (SQLException ex) {
                    Logger.getLogger(FilterTestCaseScreenTSView1.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(FilterTestCaseScreenTSView1.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntCancelar;
    private javax.swing.JButton bntPesquisar;
    private javax.swing.JComboBox<SystemBean> jComboSistemasTS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField textId;
    private javax.swing.JTextField textNomeCT;
    // End of variables declaration//GEN-END:variables

    public void centralizaJanelaDialogo(EditScreenTSView tela) {
        Dimension d = tela.getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void addIconInButton() {

//        bntCancelar.setIcon(icon.getIconBntCacelAction());
//        bntPesquisar.setIcon(icon.getIconBntSearchCt());
    }

    public void loadComboTS() {

        try {
            TestCaseTSRN testCaseRN = new TestCaseTSRN(this.fase);
            ArrayList systems = testCaseRN.systemsTestCase();

            for (int i = 0; i < systems.size(); i++) {
         //       jComboSistemasTS.addItem(systems.get(i).toString());
            }
        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    
        public void loadFieldsTSDB(String id, String name, int IdSystem) {

        try {
            TestCaseTSRN testCaseRN = new TestCaseTSRN();
            List<SystemBean> systems = testCaseRN.getSystemsBD();

            for (int i = 0; i < systems.size(); i++) {
                jComboSistemasTS.addItem(systems.get(i));
                if(IdSystem == systems.get(i).getId()){
                    jComboSistemasTS.setSelectedIndex(i);
                }
            }
            
            textId.setText(id.equals("0")?"":id);
            textNomeCT.setText(name);
       
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void searchCT(String id, String nameTC) {
        try {
            List<String> ct = new ArrayList<String>();
            SvnConnectionRN svn = new SvnConnectionRN(this.fase);
            String text = "";
            boolean isId = false;

            if (!id.equals("") && !nameTC.equals("")) {
                text = id;
                isId = true;
            } else if (!id.equals("") && nameTC.equals("")) {
                text = id;
                isId = true;
            } else if (id.equals("") && !nameTC.equals("")) {
                text = nameTC;
            }

            List<TestCaseTSPropertiesBean> listTestCaseTSPropertiesBean = svn.search(jComboSistemasTS.getSelectedItem().toString(), text);
            if (isId && listTestCaseTSPropertiesBean.size()!=0) {
                for (int i = 0; i < listTestCaseTSPropertiesBean.size(); i++) {
                    if (!listTestCaseTSPropertiesBean.get(i).getTesteCaseId().contains(id)) {
                        listTestCaseTSPropertiesBean.remove(i);
                    }
                }
            }
            guiEditCt.cleanFields();
            
            guiEditCt.loadTableCt(listTestCaseTSPropertiesBean);
            guiEditCt.setFiltro(text);
        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    private void searchCTDB(String id, String nameTC) {
        try {
            List<String> ct = new ArrayList<String>();
            
           TestCaseTSRN caseTSRN = new TestCaseTSRN();
           int IdSystem = ((SystemBean) jComboSistemasTS.getSelectedItem()).getId();
           
            List<TesteCaseTSBean> listTestCaseTSPropertiesBean = caseTSRN.getTesteCaseTSBeanBySystemNameBD(IdSystem, nameTC, id);
          
            //guiEditCt.cleanFields();            
            //guiEditCt.loadTableCt(listTestCaseTSPropertiesBean);
           
            
            guiEditCt.setRowAfter(0);
            guiEditCt.setRowBefore(0);
            guiEditCt.loadTableCtDB(listTestCaseTSPropertiesBean);
            guiEditCt.setIdSistema(IdSystem);
            int idCT = id.equals("") ? 0 : Integer.parseInt(id);
            guiEditCt.setFilter(idCT, IdSystem, nameTC);
            
            //guiEditCt.setFiltro(text);
       
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    
}
