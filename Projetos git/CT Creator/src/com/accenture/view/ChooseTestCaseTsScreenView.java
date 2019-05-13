/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.view;

import com.accenture.bean.SVNPropertiesVOBean;
import com.accenture.bean.SystemBean;
import com.accenture.bean.TestCaseTSPropertiesBean;
import com.accenture.bean.TesteCaseTSBean;
import com.accenture.filter.AutoChoices;
import com.accenture.ts.rn.SvnConnectionRN;
import com.accenture.ts.rn.TestCaseTSRN;
import java.awt.Cursor;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SwingWorker;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.tmatesoft.svn.core.SVNException;
import com.accenture.filter.TableFilterHeader;
import com.accenture.filter.TableFilterHeader.Position;


/**
 *
 * @author Raphael
 */
public class ChooseTestCaseTsScreenView extends javax.swing.JInternalFrame {

    
    
    private String faseDe ;
    private String fasePara ;
    private TestCaseTSRN testCaseRN;
    private List<TestCaseTSPropertiesBean> listProperties;
    private TableFilterHeader filterHeader;

    /**
     * Creates new form GUISelecionaCT
     */
    public ChooseTestCaseTsScreenView(String faseDe, String fasePara) throws SQLException, ClassNotFoundException {
        this.faseDe = faseDe;
        this.fasePara = fasePara;
        initComponents();
        tabelaSelecioneCT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setTitle("Copiar CT de "+faseDe+" para "+fasePara);
        

        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
//                atualizaTabelaCT();
               // loadComboTS();
                loadComboTSBanco();
                return null;
            }

            @Override
            protected void done() {

            }

        }.execute();
        
//        TableFilterHeader filterHeader = new TableFilterHeader(tabelaSelecioneCT);
//        filterHeader.setPosition(Position.TOP);

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
        jComboSistemasTS = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        bntPesquisar = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        jLabel1.setText("Selecione o CT que deseja copiar");

        tabelaSelecioneCT.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseMoved(MouseEvent e){
                Point p = e.getPoint();
                int row = tabelaSelecioneCT.rowAtPoint(p);
                int column = tabelaSelecioneCT.columnAtPoint(p);
                tabelaSelecioneCT.setToolTipText(String.valueOf(tabelaSelecioneCT.getValueAt(row,column)));
            }//end MouseMoved
        });
        tabelaSelecioneCT.setFont(new java.awt.Font("Graphik", 0, 12)); // NOI18N
        tabelaSelecioneCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CT", "Modificado por", "Data de moficação", "Order"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaSelecioneCT.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabelaSelecioneCT);
        if (tabelaSelecioneCT.getColumnModel().getColumnCount() > 0) {
            tabelaSelecioneCT.getColumnModel().getColumn(0).setPreferredWidth(60);
            tabelaSelecioneCT.getColumnModel().getColumn(1).setPreferredWidth(295);
            tabelaSelecioneCT.getColumnModel().getColumn(2).setPreferredWidth(140);
            tabelaSelecioneCT.getColumnModel().getColumn(3).setMinWidth(120);
            tabelaSelecioneCT.getColumnModel().getColumn(3).setPreferredWidth(130);
            tabelaSelecioneCT.getColumnModel().getColumn(4).setMinWidth(0);
            tabelaSelecioneCT.getColumnModel().getColumn(4).setPreferredWidth(0);
            tabelaSelecioneCT.getColumnModel().getColumn(4).setMaxWidth(0);
        }
        //ordenatabela();
        jScrollPane1.setHorizontalScrollBar(new JScrollBar(0));
        tabelaSelecioneCT.setAutoResizeMode (tabelaSelecioneCT.AUTO_RESIZE_OFF);
        tabelaSelecioneCT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        filterHeader = new TableFilterHeader(tabelaSelecioneCT, AutoChoices.ENABLED);
        filterHeader.setPosition(Position.TOP);

        bntCopiar.setFont(new java.awt.Font("Graphik", 0, 12)); // NOI18N
        bntCopiar.setText("Selecionar");
        bntCopiar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntCopiarMouseClicked(evt);
            }
        });
        bntCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCopiarActionPerformed(evt);
            }
        });

        bntCancelar.setFont(new java.awt.Font("Graphik", 0, 12)); // NOI18N
        bntCancelar.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\22x22\\cancel.png")); // NOI18N
        bntCancelar.setText("Cancelar");
        bntCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCancelarActionPerformed(evt);
            }
        });

        textPesquisaCT.setFont(new java.awt.Font("Graphik", 0, 12)); // NOI18N
        textPesquisaCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textPesquisaCTActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Graphik", 0, 12)); // NOI18N
        jLabel2.setText("Nome CT:");

        jComboSistemasTS.setFont(new java.awt.Font("Graphik", 0, 12)); // NOI18N

        jLabel33.setFont(new java.awt.Font("Graphik", 0, 12)); // NOI18N
        jLabel33.setText("Sistema:");

        bntPesquisar.setFont(new java.awt.Font("Graphik", 0, 12)); // NOI18N
        bntPesquisar.setText("Pesquisar");
        bntPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntPesquisarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(219, 219, 219)
                .addComponent(jLabel1)
                .addGap(196, 196, 196))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bntPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bntCopiar, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bntCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboSistemasTS, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textPesquisaCT)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(jComboSistemasTS, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textPesquisaCT)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bntPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(bntCopiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bntCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bntCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_bntCancelarActionPerformed

    private void bntCopiarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntCopiarMouseClicked

      

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
//                sorter.setRowFilter(RowFilter.regexFilter(busca, 0));
//            } catch (PatternSyntaxException pse) {
//                System.err.println("Erro");
//            }
//        }
        getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        atualizaTabelaCT();
        getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_textPesquisaCTActionPerformed

    private void bntPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntPesquisarActionPerformed
        getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //atualizaTabelaCT();
        atualizaTabelaCTDB();
        getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_bntPesquisarActionPerformed

    private void bntCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCopiarActionPerformed
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
                    DefaultTableModel model = (DefaultTableModel) tabelaSelecioneCT.getModel();
                    int rowsel = Integer.parseInt(tabelaSelecioneCT.getValueAt(tabelaSelecioneCT.getSelectedRow(), 4).toString()) - 1;
                    String id  = (String) model.getValueAt(rowsel, 0);

                    //criaJanelaTelaCadCT(fasePara);
                    carregaTestCaseTS(Integer.parseInt(id));

                }
                return null;
            }

            @Override
            protected void done() {
                aguarde.dispose();
            }

        }.execute();
    }//GEN-LAST:event_bntCopiarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntCancelar;
    private javax.swing.JButton bntCopiar;
    private javax.swing.JButton bntPesquisar;
    private javax.swing.JComboBox<SystemBean> jComboSistemasTS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaSelecioneCT;
    private javax.swing.JTextField textPesquisaCT;
    // End of variables declaration//GEN-END:variables
    
    
    
    public void filterTable(){
        DefaultTableModel tabelaCT = (DefaultTableModel) tabelaSelecioneCT.getModel();
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabelaCT);
        tabelaSelecioneCT.setRowSorter(sorter);    
        
        ArrayList<RowFilter<Object, Object>> andFilters = new ArrayList<RowFilter<Object, Object>>();
//        andFilters.add(RowFilter.regexFilter(filterID.getText(), 0));
//        andFilters.add(RowFilter.regexFilter(filterCT.getText(), 1));
//        andFilters.add(RowFilter.regexFilter(filterModification.getText(), 2));
//        andFilters.add(RowFilter.regexFilter(filterDate.getText(), 3));
        
        
//        if (text.length() == 0) {
//            sorter.setRowFilter(null);
//        } else {
//            try {
//                sorter.setRowFilter(RowFilter.regexFilter(text, column));
//            } catch (PatternSyntaxException pse) {
//                System.err.println("Erro");
//            }
//        }

        sorter.setRowFilter(RowFilter.andFilter(andFilters));
        
        tabelaSelecioneCT.setRowSorter(sorter);


    }
    
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

            SvnConnectionRN svn = new SvnConnectionRN(this.faseDe);

            List<TestCaseTSPropertiesBean> listTemp = svn.search(jComboSistemasTS.getSelectedItem().toString(), textPesquisaCT.getText());
            listProperties = listTemp;
            String nameCT = "";
            String id = "";
            String modifyBy = "";
            int hashCode = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            for (int i = 0; i < listTemp.size(); i++) {
//            id = listTestCaseTSPropertiesBean.get(i).getDirEntry().getName().replace(".xlsx","").substring(0,listTestCaseTSPropertiesBean.get(i).getDirEntry().getName().indexOf("-"));
//            nameCT = listTestCaseTSPropertiesBean.get(i).getDirEntry().getName().replace(".xlsx","").substring(listTestCaseTSPropertiesBean.get(i).getDirEntry().getName().indexOf("-")+1);
                id = listTemp.get(i).getTesteCaseId();
                nameCT = listTemp.get(i).getTestCaseName();
                modifyBy = listTemp.get(i).getDirEntry().getAuthor();
                hashCode = listTemp.get(i).getHashCode();
                
                System.out.println("CAPTURANDO URL: " + listTemp.get(i).getDirEntry().getURL());
                String dataFormatada = sdf.format(listTemp.get(i).getDirEntry().getDate());

                model.addRow(new String[]{id, nameCT, modifyBy, dataFormatada, hashCode+""});
//            System.out.println(listStep.get(i).getCasoTeste());
            }

        } catch (SVNException ex) {
           JOptionPane.showMessageDialog(null, ex.getErrorMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }
    public void atualizaTabelaCTDB() {

        DefaultTableModel model = (DefaultTableModel) tabelaSelecioneCT.getModel();

        if (model.getRowCount() != 0) {
            int numLinhas = model.getRowCount();
            for (int i = 0; i <= model.getRowCount(); i++) {
                //                model.removeRow(0);
                model.setRowCount(0);
            }

        }
        try {
            String nameCT = "";
            String id = "";
            String modifyBy = "";
            String order = "";
            String data = "";

           List<TesteCaseTSBean> listTemp = testCaseRN.getTesteCaseTSBeanBySystemNameBD(((SystemBean)jComboSistemasTS.getSelectedItem()).getId(),textPesquisaCT.getText(),"");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            for (int i = 0; i < listTemp.size(); i++) {
//            id = listTestCaseTSPropertiesBean.get(i).getDirEntry().getName().replace(".xlsx","").substring(0,listTestCaseTSPropertiesBean.get(i).getDirEntry().getName().indexOf("-"));
//            nameCT = listTestCaseTSPropertiesBean.get(i).getDirEntry().getName().replace(".xlsx","").substring(listTestCaseTSPropertiesBean.get(i).getDirEntry().getName().indexOf("-")+1);
                id = listTemp.get(i).getId()+"";
                nameCT = listTemp.get(i).getTestScriptName();
                modifyBy = listTemp.get(i).getModifiedBy();
                data = listTemp.get(i).getModifyDate() != null ? sdf.format(listTemp.get(i).getModifyDate()) : "";
                order = i+1+ "";
                model.addRow(new String[]{id, nameCT, modifyBy, data, order});
//            System.out.println(listStep.get(i).getCasoTeste());
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }
    RegisterScreenTSView guiJanelaRegCtTs;

    private void criaJanelaTelaCadCT(String fasePara) throws IOException, ClassNotFoundException, SQLException, SVNException {

        guiJanelaRegCtTs = new RegisterScreenTSView(fasePara);
        this.getParent().add(guiJanelaRegCtTs);

        guiJanelaRegCtTs.centralizaJanela();

        guiJanelaRegCtTs.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        guiJanelaRegCtTs.loadFields(downloadCT());
        guiJanelaRegCtTs.setVisible(true);
        this.dispose();

    }
    
    private void carregaTestCaseTS(int id) throws Exception {

        TestCaseTSRN tcRN = new TestCaseTSRN();
        
        TesteCaseTSBean caseTSBean = tcRN.getTesteCaseTSBeanById(id);
        
        if(caseTSBean != null){
            guiJanelaRegCtTs = new RegisterScreenTSView("");
            this.getParent().add(guiJanelaRegCtTs);

            guiJanelaRegCtTs.centralizaJanela();

            guiJanelaRegCtTs.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            guiJanelaRegCtTs.loadFields(caseTSBean);
            guiJanelaRegCtTs.setVisible(true);
            this.dispose();
        }else{
            messageError("Erro ao ao carregar o Caso de Teste");
        }

    }

    private TesteCaseTSBean downloadCT() throws ClassNotFoundException, IOException, SVNException {
        int indice = 0;
        SVNPropertiesVOBean properties = SVNPropertiesVOBean.getInstance();
        int line = Integer.parseInt(tabelaSelecioneCT.getValueAt(tabelaSelecioneCT.getSelectedRow(), 4).toString());
        
        for (TestCaseTSPropertiesBean tcPropertiesBean : listProperties) {
            if (line == tcPropertiesBean.getHashCode()) {
                indice = listProperties.indexOf(tcPropertiesBean);
            }
        }
        
        new SvnConnectionRN(this.faseDe).exportFile(properties.getFolderTemplocal(), jComboSistemasTS.getSelectedItem().toString(), listProperties.get(indice).getDirEntry().getName(), this.hashCode(),this.faseDe);
        System.out.println("Teste posição: "+tabelaSelecioneCT.getValueAt(tabelaSelecioneCT.getSelectedRow(), 0));
        
        
        TesteCaseTSBean tc = new TestCaseTSRN(this.faseDe).readSheet(properties.getFolderTemplocal() + this.hashCode() +"\\"+ jComboSistemasTS.getSelectedItem().toString()+"\\"+ listProperties.get(indice).getDirEntry().getName()).get(0);
//        new File(new SVNPropertiesVOBean().getFolderTemplocal() + tc.getProduct() + "\\" + listProperties.get(tabelaSelecioneCT.getSelectedRow()).getDirEntry().getName()).delete();
        testCaseRN.deleteDir(this.hashCode()+"");
        return tc;
    }

    public void ordenatabela() {
        DefaultTableModel model = (DefaultTableModel) tabelaSelecioneCT.getModel();
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        tabelaSelecioneCT.setRowSorter(sorter);
    }

    
    public void loadComboTS() {

        try {
            testCaseRN = new TestCaseTSRN(this.faseDe);
            ArrayList systems = testCaseRN.systemsTestCase();
            ArrayList fases = testCaseRN.faseCRTestCase();
            
            Collections.sort(systems, new Comparator<String>() {
                @Override
                public int compare(String t, String t1) {
                    return t.compareTo(t1);
                }
            });
            
//            for (int i = 0; i < systems.size(); i++) {
//                jComboSistemasTS.addItem(systems.get(i).toString());
//            }

        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void loadComboTSBanco() {
        try {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            testCaseRN = new TestCaseTSRN();
            List<SystemBean> systems = testCaseRN.getSystemsBD();

            for (int i = 0; i < systems.size(); i++) {
                jComboSistemasTS.addItem(systems.get(i));
            }

            ArrayList fases = testCaseRN.faseCRTestCase();
            ArrayList complexidades = testCaseRN.complexidade();

          getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        } catch (Exception ex) {
            Logger.getLogger(RegisterScreenTSView.class.getName()).log(Level.SEVERE, null, ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    private void downloadCtSvn() {
        DefaultTableModel modelCT = (DefaultTableModel) tabelaSelecioneCT.getModel();
//        listTestCase = new TestCaseTSRN().readSheet(new SVNPropertiesVOBean().getFolderTemplocal() + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName());
    }
    
    public void messageError(String error){
        JOptionPane.showMessageDialog(null,error,"Erro", JOptionPane.ERROR_MESSAGE);
    }
    
    public void messageInfo(String info){
        JOptionPane.showMessageDialog(null,info,"Informação", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void messageWarnnig(String warnning){
        JOptionPane.showMessageDialog(null,warnning,"Alerta", JOptionPane.WARNING_MESSAGE);
    }

}
