/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.view;

import com.accenture.bean.SVNPropertiesVOBean;
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
import com.accenture.ts.rn.SavePlanRN;


/**
 *
 * @author Raphael
 */
public class ChoosePlanTsScreenView extends javax.swing.JDialog {

    
    
    
    private TestCaseTSRN testCaseRN;
    private List<TestCaseTSPropertiesBean> listProperties;
    private TableFilterHeader filterHeader;
    private String namePlan = "";
    private String system = "";
    private String fase ;

    /**
     * Creates new form GUISelecionaCT
     */
    public ChoosePlanTsScreenView(InstanceScreenTSView instanceScreenTSView, java.awt.Frame parent, boolean modal,String fase) throws SQLException, ClassNotFoundException {
        super(parent, modal);
      this.fase = fase;
        initComponents();
        tabelaSelecioneCT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setTitle("Copiar CT TS");
        

        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
//                atualizaTabelaCT();
                loadComboTS();
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
        jComboSistemasTS = new javax.swing.JComboBox();
        jLabel33 = new javax.swing.JLabel();
        bntPesquisar = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Selecione o plano");

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
                "Plano", "hashCode"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
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
        tabelaSelecioneCT.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabelaSelecioneCT);
        if (tabelaSelecioneCT.getColumnModel().getColumnCount() > 0) {
            tabelaSelecioneCT.getColumnModel().getColumn(0).setPreferredWidth(170);
            tabelaSelecioneCT.getColumnModel().getColumn(1).setMinWidth(0);
            tabelaSelecioneCT.getColumnModel().getColumn(1).setPreferredWidth(0);
            tabelaSelecioneCT.getColumnModel().getColumn(1).setMaxWidth(0);
        }
        //ordenatabela();
        jScrollPane1.setHorizontalScrollBar(new JScrollBar(0));
        tabelaSelecioneCT.setAutoResizeMode (tabelaSelecioneCT.AUTO_RESIZE_OFF);
        tabelaSelecioneCT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        filterHeader = new TableFilterHeader(tabelaSelecioneCT, AutoChoices.ENABLED);
        filterHeader.setPosition(Position.TOP);

        bntCopiar.setText("Checkout");
        bntCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCopiarActionPerformed(evt);
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

        jLabel2.setText("Nome Plano:");

        jLabel33.setText("Sistema:");

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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 109, Short.MAX_VALUE)
                                .addComponent(bntPesquisar)
                                .addGap(171, 171, 171))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(textPesquisaCT)
                                    .addComponent(jComboSistemasTS, 0, 335, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(196, 196, 196))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bntCopiar)
                .addGap(18, 18, 18)
                .addComponent(bntCancelar)
                .addGap(133, 133, 133))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboSistemasTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textPesquisaCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(bntPesquisar)
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        atualizaTabelaCT();
        getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_bntPesquisarActionPerformed

    private void bntCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCopiarActionPerformed
        getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        InstanceScreenTSView.getInstance(this.fase).recuperaPlano(tabelaSelecioneCT.getValueAt(tabelaSelecioneCT.getSelectedRow(), 0).toString(),system);
        getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
         this.dispose();
    }//GEN-LAST:event_bntCopiarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntCancelar;
    private javax.swing.JButton bntCopiar;
    private javax.swing.JButton bntPesquisar;
    private javax.swing.JComboBox jComboSistemasTS;
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
    
   
    public void atualizaTabelaCT() {
        
        system = jComboSistemasTS.getSelectedItem().toString();
        namePlan = textPesquisaCT.getText();
        
        if(system != null){
        DefaultTableModel model = (DefaultTableModel) tabelaSelecioneCT.getModel();

        if (model.getRowCount() != 0) {
            int numLinhas = model.getRowCount();
            for (int i = 0; i <= model.getRowCount(); i++) {
                //                model.removeRow(0);
                model.setRowCount(0);
            }

        }
        try {

            
            SavePlanRN save = new SavePlanRN();
            List<String> tempList = save.recuperarPlano(system);
            List<String> listName = new ArrayList<String>();
            String nameCT = "";
            String id = "";
            String modifyBy = "";
            int hashCode = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            
            
            for(String s : tempList ){
                if(s.contains(namePlan)){
                    listName.add(s);
                }
            }
            
            for(String s : listName ){
                 hashCode = s.hashCode();
               model.addRow(new String[]{s,hashCode+""});
            }

         

        } catch (SVNException ex) {
           JOptionPane.showMessageDialog(null, ex.getErrorMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        }else{
            JOptionPane.showMessageDialog(null, "Selecione um sistema", "Alerta", JOptionPane.ERROR_MESSAGE);
        }

    }
    RegisterScreenTSView guiJanelaRegCtTs;

    private void criaJanelaTelaCadCT() throws IOException, ClassNotFoundException, SQLException, SVNException {

        guiJanelaRegCtTs = new RegisterScreenTSView(this.fase);
        this.getParent().add(guiJanelaRegCtTs);

        guiJanelaRegCtTs.centralizaJanela();

        guiJanelaRegCtTs.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        guiJanelaRegCtTs.loadFields(downloadCT());
        guiJanelaRegCtTs.setVisible(true);
        this.dispose();

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
        
        new SvnConnectionRN(this.fase).exportFile(properties.getFolderTemplocal(), jComboSistemasTS.getSelectedItem().toString(), listProperties.get(indice).getDirEntry().getName(), this.hashCode(), this.fase);
        System.out.println("Teste posição: "+tabelaSelecioneCT.getValueAt(tabelaSelecioneCT.getSelectedRow(), 0));
        
        
        TesteCaseTSBean tc = new TestCaseTSRN(this.fase).readSheet(properties.getFolderTemplocal() + this.hashCode() +"\\"+ jComboSistemasTS.getSelectedItem().toString()+"\\"+ listProperties.get(indice).getDirEntry().getName()).get(0);
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
            testCaseRN = new TestCaseTSRN(this.fase);
            ArrayList systems = testCaseRN.systemsTestCase();
            ArrayList fases = testCaseRN.faseCRTestCase();
            
            Collections.sort(systems, new Comparator<String>() {
                @Override
                public int compare(String t, String t1) {
                    return t.compareTo(t1);
                }
            });
            
            for (int i = 0; i < systems.size(); i++) {
                jComboSistemasTS.addItem(systems.get(i).toString());
            }

        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void downloadCtSvn() {
        DefaultTableModel modelCT = (DefaultTableModel) tabelaSelecioneCT.getModel();
//        listTestCase = new TestCaseTSRN().readSheet(new SVNPropertiesVOBean().getFolderTemplocal() + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName());
    }
    

}
