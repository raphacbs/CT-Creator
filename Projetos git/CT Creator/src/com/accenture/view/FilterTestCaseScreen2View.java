/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.view;

import com.accenture.bean.ButtonIconBean;
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
public class FilterTestCaseScreen2View extends javax.swing.JInternalFrame {

    private ManipulaDadosSQLite banco = new ManipulaDadosSQLite();
    private MainScreenView gp = new MainScreenView();
    private JDesktopPane desktop = new JDesktopPane();
    private EditTestCaseScreenTIView guiMantemCt;
    private ButtonIconBean icon;

    /**
     * Creates new form GUISelecionaCT
     */
    public FilterTestCaseScreen2View() throws SQLException, ClassNotFoundException {

        this.setResizable(false);
        initComponents();

        /*
        ** Iniciando a classe que careg os ícones dos botões
        */
        this.icon = new ButtonIconBean();
        this.addIconInButton();
        
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
                // carregando os combobox
                List<String> criacao = banco.selectConfALL("TB_CRIACAO");
                List<String> sistemaMaster = banco.selectConfALL("TB_SISTEMA_MASTER");
                List<String> type = banco.selectConfALL("TB_TYPE");
                List<String> trg = banco.selectConfALL("TB_TRG");

                //Carrega comboBox criação com os dados da tabela
                int tamanhoCriacao = criacao.size();
                for (int cont = 0; cont < tamanhoCriacao; cont++) {
                    comboCriacao.addItem(criacao.get(cont));
                }

                //Carrega comboBox sistema master com os dados da tabela
                int tamanhoSistemaMaster = sistemaMaster.size();
                for (int cont = 0; cont < tamanhoSistemaMaster; cont++) {
                    comboSistemaMaster.addItem(sistemaMaster.get(cont));
                }
                //Carrega comboBox trg com os dados da tabela
                int tamanhoTrg = trg.size();
                for (int cont = 0; cont < tamanhoTrg; cont++) {

                    comboTrg.addItem(trg.get(cont));
                }

                return null;
            }

            @Override
            protected void done() {
                 aguarde.dispose();
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

        bntPesquisar = new javax.swing.JButton();
        bntCancelar = new javax.swing.JButton();
        jLabel53 = new javax.swing.JLabel();
        textNomeCT = new javax.swing.JTextField();
        comboSistemaMaster = new javax.swing.JComboBox();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        textSistemaEnvolvidos = new javax.swing.JTextField();
        comboTrg = new javax.swing.JComboBox();
        jLabel47 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        comboCriacao = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        TextAreaDescricao = new javax.swing.JTextArea();
        jLabel55 = new javax.swing.JLabel();

        setTitle("Filtro");

        bntPesquisar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        bntPesquisar.setText("Pesquisar");
        bntPesquisar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntPesquisarMouseClicked(evt);
            }
        });

        bntCancelar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        bntCancelar.setText("Cancelar");
        bntCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCancelarActionPerformed(evt);
            }
        });

        jLabel53.setForeground(new java.awt.Color(51, 51, 51));
        jLabel53.setText("NOME CT:");

        comboSistemaMaster.setModel(new javax.swing.DefaultComboBoxModel(new String[] { ""}));

        jLabel35.setText("SISTEMA MASTER:");

        jLabel36.setText("SISTEMAS ENVOLVIDOS:");

        comboTrg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));

        jLabel47.setText("TRG:");
        jLabel47.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel47AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jLabel54.setText("CRIAÇÃO/ALTERAÇÃO");

        comboCriacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        TextAreaDescricao.setColumns(20);
        TextAreaDescricao.setRows(5);
        jScrollPane1.setViewportView(TextAreaDescricao);

        jLabel55.setText("DESCRIÇÃO do CT:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(bntPesquisar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bntCancelar)
                .addContainerGap(155, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel53)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36)
                    .addComponent(jLabel54)
                    .addComponent(jLabel55)
                    .addComponent(jLabel47))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addComponent(comboCriacao, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboTrg, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textSistemaEnvolvidos, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textNomeCT, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboSistemaMaster, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textNomeCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboSistemaMaster, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textSistemaEnvolvidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboTrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel47))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel53))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bntPesquisar)
                    .addComponent(bntCancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bntCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_bntCancelarActionPerformed

    private void bntPesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntPesquisarMouseClicked

        new SwingWorker() {

            JDialog aguarde;
            
            final Frame GUIPrincipal = new MainScreenView();
            {
                this.aguarde = new WaitScreenView((JFrame) GUIPrincipal, true);
            }
            
            boolean filtro = false;
            
            List<Plano> listPlano = new ArrayList<Plano>();
            String and = " and ";
            String sql = "SELECT * FROM TB_PLANOS P, TB_STEPS S WHERE P.ID = S.ID_PLANO ";
            String query = "";
            String sqlNomeCT = " AND CASO_TESTE LIKE" + " '%" + textNomeCT.getText() + "%' ";
            String sqlSistemaMaster = " AND SISTEMA_MASTER LIKE" + " '%" + comboSistemaMaster.getSelectedItem().toString() + "%'";
            String sqlSistemaEnvolvidos = " AND SISTEMAS_ENVOLVIDOS LIKE" + " '%" + textSistemaEnvolvidos.getText() + "%' ";
            String sqlTrg = " AND TRG LIKE" + " '%" + comboTrg.getSelectedItem().toString() + "%' ";
            String sqlCriacao = " AND CRIACAO_ALTERACAO LIKE" + " '%" + comboCriacao.getSelectedItem().toString() + "%' ";
            String sqlDescricao = " AND DESCRICAO_CASO_TESTE LIKE" + " '%" + TextAreaDescricao.getText() + "%' ";


            @Override
            protected Object doInBackground() throws Exception {
                aguarde.setLocationRelativeTo(GUIPrincipal);
                aguarde.setVisible(true);
                if (!textNomeCT.getText().equals("")) {
                    sql = sql + sqlNomeCT;
                    filtro = true;
                }

                if (!comboSistemaMaster.getSelectedItem().toString().equals("")) {
                    sql = sql + sqlSistemaMaster;
                    filtro = true;
                }

                if (!textSistemaEnvolvidos.getText().equals("")) {
                    sql = sql + sqlSistemaEnvolvidos;
                    filtro = true;
                }

                if (!comboTrg.getSelectedItem().toString().equals("")) {
                    sql = sql + sqlTrg;
                    filtro = true;
                }

                if (!TextAreaDescricao.getText().equals("")) {
                    sql = sql + sqlDescricao;
                    filtro = true;
                }
                
                if (!comboCriacao.getSelectedItem().toString().equals("")) {
                    sql = sql + sqlCriacao;
                    filtro = true;
                }

                System.out.println(sql);
                listPlano = banco.getCTsPorQuery(sql);
                
                atualizaTabelaPlanosGUIMatem(listPlano);
//                criaJanelaTelaCadCT();

                return null;
            }

            @Override
            protected void done() {
                aguarde.dispose();
            }

        }.execute();


    }//GEN-LAST:event_bntPesquisarMouseClicked

    private void jLabel47AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel47AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel47AncestorAdded


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea TextAreaDescricao;
    private javax.swing.JButton bntCancelar;
    private javax.swing.JButton bntPesquisar;
    private javax.swing.JComboBox comboCriacao;
    private javax.swing.JComboBox comboSistemaMaster;
    private javax.swing.JComboBox comboTrg;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField textNomeCT;
    private javax.swing.JTextField textSistemaEnvolvidos;
    // End of variables declaration//GEN-END:variables

    public void centralizaJanela() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    RegisterScreenTIView guiJanelaCadCT;

    private void criaJanelaTelaCadCT() throws IOException, ClassNotFoundException, SQLException {

        guiMantemCt = new EditTestCaseScreenTIView();
        this.getParent().add(guiMantemCt);

        guiMantemCt.centralizaJanela();

        guiMantemCt.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//        carregaCamposTelaCTCad(guiJanelaCadCT);
        guiMantemCt.setVisible(true);
        this.dispose();

    }
    
    public void atualizaTabelaPlanosGUIMatem(List<Plano> listPlano) throws SQLException, ClassNotFoundException, IOException{
        criaJanelaTelaCadCT();
        guiMantemCt.atualizaTabelaPlano(listPlano);
    }
    
    public void addIconInButton(){
        
        bntPesquisar.setIcon(icon.getIconBntSearchCt());
        bntCancelar.setIcon(icon.getIconBntCacelAction());
    }

}
