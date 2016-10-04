/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.view;

import com.accenture.bean.ButtonIconBean;
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
import javax.swing.DefaultListModel;
import javax.swing.JList;


/**
 *
 * @author Raphael
 */
public class ChooseTestCaseTsToFlowScreenView extends javax.swing.JDialog {

    
    
    
    private TestCaseTSRN testCaseRN;
    private List<TestCaseTSPropertiesBean> listProperties;
    private TableFilterHeader filterHeader;
    private String system;
    private ManageflowsScreenView flowView;

    /**
     * Creates new form GUISelecionaCT
     */
    public ChooseTestCaseTsToFlowScreenView(String system,ManageflowsScreenView flowView, java.awt.Frame parent, boolean modal) throws  ClassNotFoundException {
        super(parent, modal);
        initComponents();
//        tabelaSelecioneCT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setTitle("Selecionar Caso de Teste para adiciona-lo no fluxo");
        this.system = system;
        this.flowView = flowView;
        
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                
                filterTable();
                addIcons();
                atualizaTabelaCT(system);
                setVisible(true);
                return null;
            }

            @Override
            protected void done() {

            }

        }.execute();
        

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
        bntAplica = new javax.swing.JButton();
        bntCancelar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        DefaultListModel modelListTestCases =  new DefaultListModel();
        jList1 = new JList(modelListTestCases);
        bntAdd = new javax.swing.JButton();
        bntRemover = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Selecione o CT que deseja adicionar");

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
                "ID", "CT", "Modificado por", "Data de moficação", "hashCode"
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
            tabelaSelecioneCT.getColumnModel().getColumn(1).setPreferredWidth(600);
            tabelaSelecioneCT.getColumnModel().getColumn(3).setResizable(false);
            tabelaSelecioneCT.getColumnModel().getColumn(4).setMinWidth(0);
            tabelaSelecioneCT.getColumnModel().getColumn(4).setPreferredWidth(0);
            tabelaSelecioneCT.getColumnModel().getColumn(4).setMaxWidth(0);
        }
        //ordenatabela();
        jScrollPane1.setHorizontalScrollBar(new JScrollBar(0));
        tabelaSelecioneCT.setAutoResizeMode (tabelaSelecioneCT.AUTO_RESIZE_OFF);
        //tabelaSelecioneCT.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        filterHeader = new TableFilterHeader(tabelaSelecioneCT, AutoChoices.ENABLED);
        filterHeader.setPosition(Position.TOP);

        bntAplica.setText("Aplicar");
        bntAplica.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntAplicaMouseClicked(evt);
            }
        });

        bntCancelar.setText("Cancelar");
        bntCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCancelarActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(jList1);

        bntAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntAddActionPerformed(evt);
            }
        });

        bntRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntRemoverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bntAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bntRemover))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bntAplica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bntCancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {bntAdd, bntRemover});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bntAplica)
                            .addComponent(bntCancelar))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bntAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bntRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {bntAdd, bntRemover});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bntCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_bntCancelarActionPerformed

    private void bntAplicaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntAplicaMouseClicked

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
                DefaultListModel model = (DefaultListModel) jList1.getModel();
                if (model.getSize()<=0) {
                    aguarde.setVisible(false);
                    JOptionPane.showMessageDialog(null, "Favor adicione pelo menos um CT", "Informação", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    
                   addCT();

                }
                return null;
            }

            @Override
            protected void done() {
                aguarde.dispose();
            }

        }.execute();



    }//GEN-LAST:event_bntAplicaMouseClicked

    private void bntAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntAddActionPerformed
       addCTList();
    }//GEN-LAST:event_bntAddActionPerformed

    private void bntRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntRemoverActionPerformed
          new SwingWorker() {
            
            

            @Override
            protected Object doInBackground() throws Exception {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                deleteTestCases();

                
                return null;
            }

            @Override
            protected void done() {
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
            
          

        }.execute();
    }//GEN-LAST:event_bntRemoverActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntAdd;
    private javax.swing.JButton bntAplica;
    private javax.swing.JButton bntCancelar;
    private javax.swing.JButton bntRemover;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabelaSelecioneCT;
    // End of variables declaration//GEN-END:variables
    
     private void deleteTestCases(){
         try {
         DefaultListModel model = (DefaultListModel) jList1.getModel();
         Object [] itens = jList1.getSelectedValues();
         for(int i =0; i < itens.length; i++){
             model.removeElement(itens[i]);
         }
         } catch (Exception ex) {
            Logger.getLogger(ManageflowsScreenView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao desconhecido, \nverifique mais detalhes no botão de log.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();

        }
         
     }
    
    
    private void addCTList() {
        DefaultListModel model = (DefaultListModel) jList1.getModel();

        List<String> cts = new ArrayList<String>();

        int[] selects = tabelaSelecioneCT.getSelectedRows();
        for (int i = 0; i < tabelaSelecioneCT.getSelectedRowCount(); i++) {
            for (TestCaseTSPropertiesBean bean : listProperties) {
                if (bean.getHashCode() == Integer.parseInt(tabelaSelecioneCT.getValueAt(selects[i], 4).toString())) {
                    cts.add(tabelaSelecioneCT.getValueAt(tabelaSelecioneCT.getSelectedRows()[i], 0).toString() + "-" + tabelaSelecioneCT.getValueAt(tabelaSelecioneCT.getSelectedRows()[i], 1).toString());
                }
            }

        }

        for (String ct : cts) {
            model.addElement(ct);
        }
    }
    
    public void filterTable(){
        DefaultTableModel tabelaCT = (DefaultTableModel) tabelaSelecioneCT.getModel();
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabelaCT);
        tabelaSelecioneCT.setRowSorter(sorter);    
        
        ArrayList<RowFilter<Object, Object>> andFilters = new ArrayList<RowFilter<Object, Object>>();

        sorter.setRowFilter(RowFilter.andFilter(andFilters));
        
        tabelaSelecioneCT.setRowSorter(sorter);


    }
    
    public void centralizaJanela() {
        Dimension d = getParent().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    
    
    public void atualizaTabelaCT(String system) {

        DefaultTableModel model = (DefaultTableModel) tabelaSelecioneCT.getModel();

        if (model.getRowCount() != 0) {
            int numLinhas = model.getRowCount();
            for (int i = 0; i <= model.getRowCount(); i++) {
                model.setRowCount(0);
            }

        }
        try {

            SvnConnectionRN svn = new SvnConnectionRN();

            List<TestCaseTSPropertiesBean> listTemp = svn.search(system, "");
            listProperties = listTemp;
            String nameCT = "";
            String id = "";
            String modifyBy = "";
            int hashCode = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            for (int i = 0; i < listTemp.size(); i++) {
                id = listTemp.get(i).getTesteCaseId();
                nameCT = listTemp.get(i).getTestCaseName();
                modifyBy = listTemp.get(i).getDirEntry().getAuthor();
                hashCode = listTemp.get(i).getHashCode();
                
                System.out.println("CAPTURANDO URL: " + listTemp.get(i).getDirEntry().getURL());
                String dataFormatada = sdf.format(listTemp.get(i).getDirEntry().getDate());

                model.addRow(new Object[]{id, nameCT, modifyBy, dataFormatada, hashCode});
            }

        } catch (SVNException ex) {
           JOptionPane.showMessageDialog(null, ex.getErrorMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    
    
    RegisterScreenTSView guiJanelaRegCtTs;

    private void criaJanelaTelaCadCT() throws IOException, ClassNotFoundException, SQLException, SVNException {

        guiJanelaRegCtTs = new RegisterScreenTSView();
        this.getParent().add(guiJanelaRegCtTs);

        guiJanelaRegCtTs.centralizaJanela();

        guiJanelaRegCtTs.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        guiJanelaRegCtTs.loadFields(downloadCT());
        guiJanelaRegCtTs.setVisible(true);
        this.dispose();

    }

    private TesteCaseTSBean downloadCT() throws ClassNotFoundException, IOException, SVNException {
        int indice = 0;
        SVNPropertiesVOBean properties = new SVNPropertiesVOBean();
        int line = Integer.parseInt(tabelaSelecioneCT.getValueAt(tabelaSelecioneCT.getSelectedRow(), 4).toString());
        
        for (TestCaseTSPropertiesBean tcPropertiesBean : listProperties) {
            if (line == tcPropertiesBean.getHashCode()) {
                indice = listProperties.indexOf(tcPropertiesBean);
            }
        }
        
        new SvnConnectionRN().exportFile(properties.getFolderTemplocal(),system, listProperties.get(indice).getDirEntry().getName(), this.hashCode());
        System.out.println("Teste posição: "+tabelaSelecioneCT.getValueAt(tabelaSelecioneCT.getSelectedRow(), 0));
        
        
        TesteCaseTSBean tc = new TestCaseTSRN().readSheet(properties.getFolderTemplocal() + this.hashCode() +"\\"+ system+"\\"+ listProperties.get(indice).getDirEntry().getName()).get(0);
        testCaseRN.deleteDir(this.hashCode()+"");
        return tc;
    }

    public void ordenatabela() {
        DefaultTableModel model = (DefaultTableModel) tabelaSelecioneCT.getModel();
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        tabelaSelecioneCT.setRowSorter(sorter);
    }

    
    
    
    private void downloadCtSvn() {
        DefaultTableModel modelCT = (DefaultTableModel) tabelaSelecioneCT.getModel();
    }
    
    
    private void addCT() {
//        DefaultTableModel model = (DefaultTableModel) tabelaSelecioneCT.getModel();
        List<String> cts = new ArrayList<String>();
//        
//        int [] selects = tabelaSelecioneCT.getSelectedRows();
//        for (int i = 0; i < tabelaSelecioneCT.getSelectedRowCount(); i++) {
//            for(TestCaseTSPropertiesBean bean : listProperties){
//                if(bean.getHashCode() == Integer.parseInt(tabelaSelecioneCT.getValueAt(selects[i],4).toString()))
//                    cts.add(tabelaSelecioneCT.getValueAt(tabelaSelecioneCT.getSelectedRows()[i], 0).toString()+"-"+tabelaSelecioneCT.getValueAt(tabelaSelecioneCT.getSelectedRows()[i], 1).toString());
//            }
//            
//            
//        }
        
//        for (int i = 0; i < tabelaSelecioneCT.getRowCount(); i++) {
//            boolean select = (boolean) tabelaSelecioneCT.getValueAt(i, 5);
//            if(select){
//                cts.add(tabelaSelecioneCT.getValueAt(tabelaSelecioneCT.getSelectedRows()[i], 0).toString()+"-"+tabelaSelecioneCT.getValueAt(tabelaSelecioneCT.getSelectedRows()[i], 1).toString());
//            }
//        }

        DefaultListModel model = (DefaultListModel) jList1.getModel();
        
        for (int i = 0; i <model.getSize(); i++) {
            cts.add(model.getElementAt(i).toString());
        }
        
        
        flowView.addCT(cts);
        this.dispose();
    }
    
    private void addIcons(){
        ButtonIconBean iconBean = new ButtonIconBean();
        bntAdd.setIcon(iconBean.getIconBntAddCtDefault());
        bntRemover.setIcon(iconBean.getIconBntRemoveCt());
    }

}
