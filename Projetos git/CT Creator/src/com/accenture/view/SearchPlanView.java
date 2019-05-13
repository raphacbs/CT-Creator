/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.view;

import com.accenture.bean.ButtonIconBean;
import com.accenture.bean.SystemBean;
import com.accenture.control.ManipulaDadosSQLite;
import com.accenture.bean.TestCaseTSPropertiesBean;
import com.accenture.bean.TestPlanTSBean;
import com.accenture.bean.TesteCaseTSBean;
import com.accenture.ts.rn.SvnConnectionRN;
import com.accenture.ts.rn.TestCaseTSRN;
import com.accenture.ts.rn.TestPlanTSRN;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author raphael.da.silva
 */
public class SearchPlanView extends java.awt.Dialog {

    private ManipulaDadosSQLite banco = new ManipulaDadosSQLite();
    private InstanceScreenTSView guiInstanceCT;

//    private - guiBaseline;
    private ButtonIconBean icon;
    private static String fase;

    /**
     * Creates new form GUIFiltroCT
     */
    public SearchPlanView(java.awt.Frame parent, boolean modal, String fase) throws SQLException, ClassNotFoundException {
        super(parent, modal);
        this.fase = fase;
        initComponents();

    }

    public SearchPlanView(final InstanceScreenTSView guiInstanceCT, java.awt.Frame parent, boolean modal) throws IOException, ClassNotFoundException, SQLException {
        super(parent, modal);
        this.setResizable(false);
        initComponents();
        loadComboTSBanco();
        this.guiInstanceCT = guiInstanceCT;
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                
                return null;
            }

            @Override
            protected void done() {
            }

        }.execute();

        /*
         **Carregando os ícones dos botões:
         */
        icon = new ButtonIconBean();
        this.addIconInButton();
    }

//    public FilterTestCaseTSForPlanScreenView(final RegisterBaselineScreenTSView guiBaseline, java.awt.Frame parent, boolean modal) throws IOException, ClassNotFoundException, SQLException {
//        super(parent, modal);
//
//        this.setResizable(false);
//        initComponents();
//        loadComboTS();
////        this.guiBaseline = guiBaseline;
//        new SwingWorker() {
//            JDialog aguarde;
//            final Frame GUIPrincipal = new MainScreenView();
//
//            {
//                this.aguarde = new WaitScreenView((JFrame) GUIPrincipal, true);
//            }
//
//            @Override
//            protected Object doInBackground() throws Exception {
//                aguarde.setLocationRelativeTo(GUIPrincipal);
//                aguarde.setVisible(true);
//
//                return null;
//            }
//
//            @Override
//            protected void done() {
//                aguarde.dispose();
//            }
//
//        }.execute();
//
//        /*
//         **Carregando os ícones dos botões:
//         */
//        icon = new ButtonIconBean();
//        this.addIconInButton();
//    }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaSelecionePlano = new javax.swing.JTable();
        bntAbrir = new javax.swing.JButton();

        setTitle("Filtro - Caso de Teste");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(600, 600));

        jLabel50.setForeground(new java.awt.Color(51, 51, 51));
        jLabel50.setText("Nome Plano:");

        textNomeCT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        textNomeCT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textNomeCTFocusLost(evt);
            }
        });

        jLabel33.setText("Sistema");

        jLabel34.setText("Id:");

        textId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textIdFocusLost(evt);
            }
        });
        textId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textIdKeyTyped(evt);
            }
        });

        bntCancelar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        bntCancelar.setText("Cancelar");
        bntCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntCancelarMouseClicked(evt);
            }
        });

        bntPesquisar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        bntPesquisar.setText("Pesquisar");
        bntPesquisar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntPesquisarMouseClicked(evt);
            }
        });
        bntPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntPesquisarActionPerformed(evt);
            }
        });

        tabelaSelecionePlano.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseMoved(MouseEvent e){
                Point p = e.getPoint();
                int row = tabelaSelecionePlano.rowAtPoint(p);
                int column = tabelaSelecionePlano.columnAtPoint(p);
                tabelaSelecionePlano.setToolTipText(String.valueOf(tabelaSelecionePlano.getValueAt(row,column)));
            }//end MouseMoved
        });
        tabelaSelecionePlano.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        loadTablePlano(new ArrayList<TestPlanTSBean>());
        tabelaSelecionePlano.getTableHeader().setReorderingAllowed(false);
        tabelaSelecionePlano.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaSelecionePlanoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaSelecionePlano);

        bntAbrir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        bntAbrir.setText("Abrir Plano");
        bntAbrir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntAbrirMouseClicked(evt);
            }
        });
        bntAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntAbrirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bntPesquisar)
                        .addGap(18, 18, 18)
                        .addComponent(bntCancelar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel50)
                            .addComponent(jLabel1)
                            .addComponent(jLabel33)
                            .addComponent(jLabel34))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textNomeCT)
                            .addComponent(jComboSistemasTS, 0, 382, Short.MAX_VALUE)
                            .addComponent(textId))))
                .addGap(19, 19, 19))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addComponent(bntAbrir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(textNomeCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(textId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboSistemasTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bntCancelar)
                    .addComponent(bntPesquisar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bntAbrir)
                .addContainerGap(71, Short.MAX_VALUE))
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

    private void textNomeCTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textNomeCTFocusLost

    }//GEN-LAST:event_textNomeCTFocusLost

    private void textIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textIdFocusLost


    }//GEN-LAST:event_textIdFocusLost

    private void bntCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntCancelarMouseClicked
        this.dispose();
    }//GEN-LAST:event_bntCancelarMouseClicked

    private void bntPesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntPesquisarMouseClicked


    }//GEN-LAST:event_bntPesquisarMouseClicked

    private void bntPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntPesquisarActionPerformed
        // TODO add your handling code here:
       
        SwingWorker sw = new SwingWorker() {
            
            boolean filtro = false;
           
            @Override
            protected Object doInBackground() throws Exception {
                   
                
                searchCTDB(textNomeCT.getText(), textId.getText());
                return null;

            }

            @Override
            protected void done() {
                guiInstanceCT.setMouseCursorWait(false);
                InstanceScreenTSView.addTextLabelStatus("Pesquisa concluída");
            }
        };
        sw.execute();
        guiInstanceCT.setMouseCursorWait(true);
        InstanceScreenTSView.addTextLabelStatus("Pesquisando planos, favor aguarde...");

    }//GEN-LAST:event_bntPesquisarActionPerformed

    private void textIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textIdKeyTyped
        if (evt.getKeyChar() < '0' || evt.getKeyChar() > '9') {
            evt.consume();//aciona esse propriedade para eliminar a ação do evento
           
        }
    }//GEN-LAST:event_textIdKeyTyped

    private void tabelaSelecionePlanoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaSelecionePlanoMouseClicked
        if (evt.getClickCount() == 2) {
            try {
                abrirPlano();
            } catch (ParseException ex) {
                Logger.getLogger(SearchPlanView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_tabelaSelecionePlanoMouseClicked

    private void bntAbrirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntAbrirMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_bntAbrirMouseClicked

    private void abrirPlano() throws ParseException{
        TestPlanTSRN planTSRN = new TestPlanTSRN();

                        String id = tabelaSelecionePlano.getValueAt(tabelaSelecionePlano.getSelectedRow(), 0).toString();

                        TestPlanTSBean plan = planTSRN.getById(Integer.parseInt(id));
                        guiInstanceCT.setMouseCursorWait(true);
                        guiInstanceCT.loadPlan(plan);

                        dispose();
                        guiInstanceCT.setMouseCursorWait(false);
    }

    private void bntAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntAbrirActionPerformed
          
        
             SwingWorker sw = new SwingWorker() {
            
            @Override
            protected Object doInBackground() throws Exception {
                   
                
                abrirPlano();
                return null;

            }

            @Override
            protected void done() {
                blockedFilds(false);
                guiInstanceCT.setMouseCursorWait(false);
                InstanceScreenTSView.addTextLabelStatus("concluído");
            }
        };
        sw.execute();
        blockedFilds(true);
        guiInstanceCT.setMouseCursorWait(true);
        InstanceScreenTSView.addTextLabelStatus("Carregando plano, favor aguarde...");
            
      
               
        
       
    }//GEN-LAST:event_bntAbrirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SearchPlanView dialog = null;
                try {
                    dialog = new SearchPlanView(new java.awt.Frame(), true, fase);
                } catch (SQLException ex) {
                    Logger.getLogger(SearchPlanView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SearchPlanView.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JButton bntAbrir;
    private javax.swing.JButton bntCancelar;
    private javax.swing.JButton bntPesquisar;
    private javax.swing.JComboBox<SystemBean> jComboSistemasTS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaSelecionePlano;
    private javax.swing.JTextField textId;
    private javax.swing.JTextField textNomeCT;
    // End of variables declaration//GEN-END:variables

    public void centralizaJanelaDialogo(InstanceScreenTSView tela) {
        Dimension d = tela.getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

//    public void centralizaJanelaDialogo(RegisterBaselineScreenTSView tela) {
//        Dimension d = tela.getSize();
//        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
//    }
    public void addIconInButton() {

        bntCancelar.setIcon(icon.getIconBntCacelAction());
        bntPesquisar.setIcon(icon.getIconBntSearchCt());
    }

    public void loadComboTS() {

        try {
            TestCaseTSRN testCaseRN = new TestCaseTSRN(this.fase);
            ArrayList<String> systems = testCaseRN.systemsTestCase();

            Collections.sort(systems, new Comparator<String>() {
                @Override
                public int compare(String t, String t1) {
                    return t.compareTo(t1);
                }
            });

            for (int i = 0; i < systems.size(); i++) {
                //  jComboSistemasTS.addItem(systems.get(i).toString());
                if (systems.get(i).toString().equals("STC DADOS")) {
                    //   jComboSistemasTS.addItem("STC(DADOS/VOZ)");
                }
            }

        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void loadComboTSBanco() {
        try {
            TestCaseTSRN testCaseRN = new TestCaseTSRN();
            List<SystemBean> systems = testCaseRN.getSystemsBD();

            for (int i = 0; i < systems.size(); i++) {
                jComboSistemasTS.addItem(systems.get(i));
            }

            ArrayList fases = testCaseRN.faseCRTestCase();
            ArrayList complexidades = testCaseRN.complexidade();

        } catch (Exception ex) {
            Logger.getLogger(RegisterScreenTSView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao desconhecido, \n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchCTDB(String nameTC, String id) {
        try {
            List<String> ct = new ArrayList<String>();

            TestPlanTSRN caseTSRN = new TestPlanTSRN();
            String system = ((SystemBean) jComboSistemasTS.getSelectedItem()).getDescricao();
            List<TestPlanTSBean> listplan = caseTSRN.getTesteCaseTSBeanBySystemNameBD(system, nameTC, id);

            loadTablePlano(listplan);
            

            //guiEditCt.setFiltro(text);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void loadTablePlano(List<TestPlanTSBean> listPlan) {

        try {
            String[] columns = new String[]{"Id", "Plano", "Sistema", "STI_PRJ", "Modificado por", "Modificado em", "Criado por", "Criado em", "Qtd CTs"};

            DefaultTableModel tableModel = new DefaultTableModel() {

                @Override
                public boolean isCellEditable(int row, int column) {
                    //all cells false
                    return false;
                }

            };

            tabelaSelecionePlano.setModel(tableModel);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            DefaultTableModel model = (DefaultTableModel) tabelaSelecionePlano.getModel();

            model.setRowCount(0);

            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            Vector<String> header = new Vector<String>();

            for (String col : columns) {
                header.add(col);
            }

            for (int i = 0; i < listPlan.size(); i++) {

                String dataFormatada = sdf.format(listPlan.get(i).getModifyDate());

                Vector<Object> row = new Vector<Object>();

                row.add(listPlan.get(i).getId());
                row.add(listPlan.get(i).getName());
                row.add(listPlan.get(i).getProduct());
                row.add(listPlan.get(i).getSti());
                row.add(listPlan.get(i).getModifiedBy());
                row.add(sdf.format(listPlan.get(i).getModifyDate()));
                row.add(listPlan.get(i).getCreatedBy());
                row.add(sdf.format(listPlan.get(i).getCreateDate()));
                row.add(listPlan.get(i).getTestCase().size());

                data.add(row);

            }

            model.setDataVector(data, header);

            Thread.sleep(2000);
            TableColumnModel columnModel = tabelaSelecionePlano.getColumnModel();
            Dimension tableSize = tabelaSelecionePlano.getSize();
            columnModel.getColumn(0).setPreferredWidth((int) (tableSize.getWidth() * 0.10));
            columnModel.getColumn(1).setPreferredWidth((int) (tableSize.getWidth() * 0.70));
            columnModel.getColumn(2).setPreferredWidth((int) (tableSize.getWidth() * 0.50));
            columnModel.getColumn(3).setPreferredWidth((int) (tableSize.getWidth() * 0.50));
            columnModel.getColumn(4).setPreferredWidth((int) (tableSize.getWidth() * 0.50));
            columnModel.getColumn(5).setPreferredWidth((int) (tableSize.getWidth() * 0.50));
            columnModel.getColumn(6).setPreferredWidth((int) (tableSize.getWidth() * 0.50));
            columnModel.getColumn(7).setPreferredWidth((int) (tableSize.getWidth() * 0.50));
            columnModel.getColumn(8).setPreferredWidth((int) (tableSize.getWidth() * 0.20));
        } catch (Exception ex) {
            Logger.getLogger(SearchPlanView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void getPlan(int id) {
        TestPlanTSRN planTSRN = new TestPlanTSRN();
        planTSRN.getById(id);

    }
    
    private void blockedFilds(boolean b){
        textId.setEnabled(b);
        textId.setEditable(b);
        bntCancelar.setEnabled(b);
        bntPesquisar.setEnabled(b);
        tabelaSelecionePlano.setEnabled(b);
        bntAbrir.setEnabled(b);
    }

}
