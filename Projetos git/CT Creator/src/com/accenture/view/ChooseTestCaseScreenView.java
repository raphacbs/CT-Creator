/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.view;

import com.accenture.control.ManipulaDadosSQLite;
import com.accenture.bean.Plano;
import com.accenture.bean.Step;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import static java.awt.SystemColor.desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.PatternSyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Raphael
 */
public class ChooseTestCaseScreenView extends javax.swing.JInternalFrame {

    private ManipulaDadosSQLite banco = new ManipulaDadosSQLite();
    private MainScreenView gp = new MainScreenView();
    private JDesktopPane desktop = new JDesktopPane();

    /**
     * Creates new form GUISelecionaCT
     */
    public ChooseTestCaseScreenView() throws SQLException, ClassNotFoundException {

        initComponents();
        tabelaSelecioneCT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setTitle("Copiar CT");

        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                atualizaTabelaCT();
                return null;
            }

            @Override
            protected void done() {

            }

        }.execute();

//        labelCarregando.setVerticalAlignment(SwingConstants.CENTER);
//        labelCarregando.setHorizontalAlignment(SwingConstants.CENTER);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaSelecioneCT = new javax.swing.JTable();
        bntCopiar = new javax.swing.JButton();
        bntCancelar = new javax.swing.JButton();
        textPesquisaCT = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Selecione o CT que deseja copiar");

        tabelaSelecioneCT.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseMoved(MouseEvent e){
                Point p = e.getPoint();
                int row = tabelaSelecioneCT.rowAtPoint(p);
                int column = tabelaSelecioneCT.columnAtPoint(p);
                tabelaSelecioneCT.setToolTipText(String.valueOf(tabelaSelecioneCT.getValueAt(row,column)));
            }//end MouseMoved
        });
        tabelaSelecioneCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CT", "Sistema"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaSelecioneCT.setColumnSelectionAllowed(true);
        tabelaSelecioneCT.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabelaSelecioneCT);
        tabelaSelecioneCT.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (tabelaSelecioneCT.getColumnModel().getColumnCount() > 0) {
            tabelaSelecioneCT.getColumnModel().getColumn(0).setResizable(false);
            tabelaSelecioneCT.getColumnModel().getColumn(1).setResizable(false);
        }
        ordenatabela();

        bntCopiar.setText("Copiar");
        bntCopiar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntCopiarMouseClicked(evt);
            }
        });

        bntCancelar.setText("Cancelar");
        bntCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCancelarActionPerformed(evt);
            }
        });

        textPesquisaCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textPesquisaCTActionPerformed(evt);
            }
        });
        textPesquisaCT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textPesquisaCTKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textPesquisaCTKeyReleased(evt);
            }
        });

        jLabel2.setText("FIltro:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(bntCopiar)
                .addGap(18, 18, 18)
                .addComponent(bntCancelar)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textPesquisaCT)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textPesquisaCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bntCopiar)
                    .addComponent(bntCancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bntCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_bntCancelarActionPerformed

    private void textPesquisaCTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textPesquisaCTKeyPressed
        

    }//GEN-LAST:event_textPesquisaCTKeyPressed

    private void bntCopiarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntCopiarMouseClicked

        new SwingWorker() {
            DefaultTableModel tabelaCT = (DefaultTableModel) tabelaSelecioneCT.getModel();
            JDialog aguarde;
            final Frame GUIPrincipal = new MainScreenView();
            {
                this.aguarde = new WaitScreenView((JFrame) GUIPrincipal, true);
            }

            @Override
            protected Object doInBackground() throws Exception {
                aguarde.setLocationRelativeTo(GUIPrincipal);
                aguarde.setVisible(true);
                if (tabelaSelecioneCT.getSelectedRowCount() == 0) {
                    aguarde.setVisible(false);
                    JOptionPane.showMessageDialog(null, "Favor selecione um CT", "Informação", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    System.out.println(tabelaSelecioneCT.getValueAt(tabelaSelecioneCT.getSelectedRow(), 0));
                    ManipulaDadosSQLite bd = new ManipulaDadosSQLite();
                    Plano p = new Plano();
                    bd.getPorCasoTeste(p);

                    criaJanelaTelaCadCT();
                    
                }
                return null;
            }

                @Override
                protected void done(){
                    aguarde.dispose();
                }
                
                
            
        }.execute();

//            DefaultTableModel tabelaCT = (DefaultTableModel) tabelaSelecioneCT.getModel();
//
//            tabelaSelecioneCT.getSelectedRow ();
//            final Frame GUIPrincipal = new GUIPrincipal();
//
//            if (tabelaSelecioneCT.getSelectedRowCount () 
//                == 0) {
//            JOptionPane.showMessageDialog(this, "Favor selecione um CT", "Informação", JOptionPane.INFORMATION_MESSAGE);
//            }
//
//            
//                else {
//            try {
//                    System.out.println(tabelaSelecioneCT.getValueAt(tabelaSelecioneCT.getSelectedRow(), 0));
//                    ManipulaDadosSQLite bd = new ManipulaDadosSQLite();
//                    Plano p = new Plano();
//                    bd.getPorCasoTeste(p);
//
//                    criaJanelaTelaCadCT();
//
////                criaJanelaTelaCadCT();
//                } catch (SQLException ex) {
//                    JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getSQLState(), "ERRO", JOptionPane.ERROR);
//                } catch (ClassNotFoundException ex) {
//                    JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getCause(), "ERRO", JOptionPane.ERROR);
//                } catch (IOException ex) {
//                    Logger.getLogger(GUISelecionaCT.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }

    }//GEN-LAST:event_bntCopiarMouseClicked

    private void textPesquisaCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textPesquisaCTActionPerformed
        //Realiza pesquisa na tabela
//        DefaultTableModel tabelaCT = (DefaultTableModel) tabelaSelecioneCT.getModel();
//        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabelaCT);
//        tabelaSelecioneCT.setRowSorter(sorter);
//        String busca = textPesquisaCT.getText();
//        if (busca.length() == 0) {
//            sorter.setRowFilter(null);
//        } else {
//            try {
//                sorter.setRowFilter(RowFilter.regexFilter(busca.toUpperCase(), 0));
//            } catch (PatternSyntaxException pse) {
//                System.err.println("Erro");
//            }
//        }
    }//GEN-LAST:event_textPesquisaCTActionPerformed

    private void textPesquisaCTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textPesquisaCTKeyReleased
        DefaultTableModel tabelaCT = (DefaultTableModel) tabelaSelecioneCT.getModel();
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabelaCT);
        tabelaSelecioneCT.setRowSorter(sorter);
        String busca = textPesquisaCT.getText();
        if (busca.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            try {
                sorter.setRowFilter(RowFilter.regexFilter(busca.toUpperCase(), 0));
            } catch (PatternSyntaxException pse) {
                System.err.println("Erro");
            }
        }
    }//GEN-LAST:event_textPesquisaCTKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntCancelar;
    private javax.swing.JButton bntCopiar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaSelecioneCT;
    private javax.swing.JTextField textPesquisaCT;
    // End of variables declaration//GEN-END:variables

    public void centralizaJanela() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void atualizaTabelaCT() {

        DefaultTableModel model = (DefaultTableModel) tabelaSelecioneCT.getModel();

        if (model.getRowCount() != 0) {
            int numLinhas = model.getRowCount();
            for (int i = 0; i <= model.getRowCount(); i++) {
                //                model.removeRow(0);
                model.setRowCount(0);
            }

        }
        try {

            //Adiciona os casos de teste na tabela caso de teste
            List<Plano> listPlanos = banco.selectPlanoALL();

            int tamanho = listPlanos.size();
            int idTemp = 0;
            for (int cont = 0; cont < tamanho; cont++) {

                if (idTemp != listPlanos.get(cont).getId()) {
                    model.addRow(new String[]{listPlanos.get(cont).getCasoTeste().toUpperCase(), listPlanos.get(cont).getSistemaMaster().toUpperCase()});
                    System.out.println(listPlanos.get(cont).getCasoTeste());
                }
                idTemp = listPlanos.get(cont).getId();

            }

        } catch (SQLException ex) {
           
        } catch (ClassNotFoundException ex) {
       
        }

    }
    RegisterScreenTIView guiJanelaCadCT;

    private void criaJanelaTelaCadCT() throws IOException, ClassNotFoundException, SQLException {

        guiJanelaCadCT = new RegisterScreenTIView();
        this.getParent().add(guiJanelaCadCT);

        guiJanelaCadCT.centralizaJanela();

        guiJanelaCadCT.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        carregaCamposTelaCTCad(guiJanelaCadCT);
        guiJanelaCadCT.setVisible(true);
        this.dispose();

    }

    private void carregaCamposTelaCTCad(RegisterScreenTIView guiJanelaCadCT) throws SQLException, ClassNotFoundException {
        Plano p = new Plano();
        List<Step> stepList = new ArrayList<Step>();

        p.setCasoTeste(tabelaSelecioneCT.getValueAt(tabelaSelecioneCT.getSelectedRow(), 0).toString());
        p = banco.getPorCasoTeste(p);
        stepList = banco.getStepPorPlano(p);

        guiJanelaCadCT.setValorComboCadeia(p.getCadeia());
        guiJanelaCadCT.setValorComboCenarioAutomatizavel(p.getCenarioAutomatizavel());
        guiJanelaCadCT.setValorComboCenarioIntegrado(p.getCenarioIntegrado());
        guiJanelaCadCT.setValorComboCriacao(p.getCriacaoAlteracao());
        guiJanelaCadCT.setValorComboFuncionalidade(p.getFuncionalidade());
        guiJanelaCadCT.setValorComboSistemaMaster(p.getSistemaMaster());
        guiJanelaCadCT.setValorComboTipoRequisito(p.getTpRequisito());
        guiJanelaCadCT.setValorComboTrg(p.getTrg());
        guiJanelaCadCT.setValorComboType(p.getType());
        guiJanelaCadCT.setValorTextAreaDescricao(p.getDescCasoTeste());
        guiJanelaCadCT.setValorTextCenario(p.getCenarioTeste());
        guiJanelaCadCT.setValorTextFornecedor(p.getFornecedor());
        guiJanelaCadCT.setValorTextNomeCT(p.getCasoTeste() + " - Cópia");
        guiJanelaCadCT.setValorTextProduto(p.getProduto());
        guiJanelaCadCT.setValorTextQtdSistemas(p.getQtdSistemas() + "");
        guiJanelaCadCT.setValorTextRequisito(p.getRequisito());
        guiJanelaCadCT.setValorTextSegmento(p.getSegmento());
        guiJanelaCadCT.setValorTextSistemaEnvolvidos(p.getSistemasEnvolvidos());
        guiJanelaCadCT.setValorTextSubject(p.getSubject());
        guiJanelaCadCT.setTabelaSteps(stepList);

    }
    
     public void ordenatabela(){
            DefaultTableModel model = (DefaultTableModel) tabelaSelecioneCT.getModel();
            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
            tabelaSelecioneCT.setRowSorter(sorter);
        }

}
