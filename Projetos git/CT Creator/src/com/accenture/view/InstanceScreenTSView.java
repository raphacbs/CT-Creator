/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.view;

import com.accenture.bean.ButtonIconBean;
import com.accenture.bean.ParameterBean;
import com.accenture.bean.SVNPropertiesVOBean;
import com.accenture.bean.Step;
import com.accenture.bean.TestCaseTSPropertiesBean;
import com.accenture.bean.TesteCaseTSBean;
import com.accenture.control.Validacao;
import com.accenture.filter.AutoChoices;
import com.accenture.filter.TableFilterHeader;
import com.accenture.filter.TableFilterHeader.Position;
import com.accenture.log.MyLogger;
import com.accenture.reports.BuildReport;
import com.accenture.ts.dao.TestPlanTSDao;
import com.accenture.ts.dao.TesteCaseTSDAO;
import com.accenture.ts.rn.ParameterRN;
import com.accenture.ts.rn.SavePlanRN;
import com.accenture.ts.rn.SvnConnectionRN;
import com.accenture.ts.rn.TestCaseTSRN;
import com.accenture.util.CabecalhoJTableCheckBox;
import com.accenture.util.FunctiosDates;
import com.accenture.util.JDateChooserRenderer;
import com.accenture.util.TableRowTransferHandler;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.tmatesoft.svn.core.SVNException;
import com.accenture.util.TextAreaCellRenderer;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import com.accenture.util.JDateChooserCellEditor;
import com.accenture.util.ProjectSettings;
import java.awt.Point;
import java.awt.event.MouseMotionAdapter;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.tmatesoft.svn.core.SVNLock;


/**
 *
 * @author raphael.da.silva
 */
public class InstanceScreenTSView extends javax.swing.JInternalFrame {

    private SvnConnectionRN svnRN;
    private TestCaseTSRN testCaseRN;
    private int numeroStep;
    private List<Step> listSteps;
    private int lineSelect = -1;
    private boolean headerColumnAll = false;
    private List<TestCaseTSPropertiesBean> listTestCaseTSPropertiesBean;
    private List<TesteCaseTSBean> listTestCase;
    private List<TesteCaseTSBean> listTestCasePlan;
    private String ctbefore = "";
    private List<String> ctBaixados;
    private int lineSelectTableCt = -1;
    private TestPlanTSDao testPlan;
    private String lastSystem = "";
    private List<Integer> listcodeObject;
    private TableFilterHeader filterHeader;
    private boolean savePlan, isFirst = true;
    private Timer timer = new Timer();
    private final long tempo = (1000 * 60);
    private TimerTask timerTask;
    private org.apache.log4j.Logger logger;
    private SwingWorker task;
    private final String NAME_ITEM_CTS_PRIORITARIOS = "CTs Prioritários";
    private final String NAME_ITEM_MASSA_DADOS = "Massa de Dados";
    private final String NAME_ITEM_RETRABALHO = "Passível de Re-Trabalho";
    private final String NAME_ITEM_REGRESSAO = "Regressão";
    private final static Logger Log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static String NAME_PLAN_SVN = "";
    public static InstanceScreenTSView screen;
    private String fase;

    /**
     * Creates new form guiCadTS
     */
    private InstanceScreenTSView(String fase) {

        try {
            this.fase = fase;
            initComponents();
            this.setTitle("Edição de Plano de Teste " + fase);
//            MyLogger.setup();
            Log.setLevel(Level.INFO);
            logger = org.apache.log4j.Logger.getLogger(TesteCaseTSDAO.class);
//            timerTask = new TimerTask() {
//
//                @Override
//                public void run() {
//
//                    testPlan.getTestPlan().setCrFase(jComboBoxCR.getSelectedItem().toString());
//                    testPlan.getTestPlan().setTestPhase(jComboBoxTestFase.getSelectedItem().toString());
//
//                    salvaPlanoFile(testPlan, true);
//
//                }
//            };

//            timer.scheduleAtFixedRate(timerTask, tempo, tempo);

            listSteps = new ArrayList<Step>();
            listTestCaseTSPropertiesBean = new ArrayList<TestCaseTSPropertiesBean>();
            listTestCase = new ArrayList<TesteCaseTSBean>();
            ctBaixados = new ArrayList<String>();
            svnRN = new SvnConnectionRN(this.fase);
            listTestCasePlan = new ArrayList<TesteCaseTSBean>();
            listcodeObject = new ArrayList<Integer>();

//            blockedFieldBnt(false);
            bntCancelar.setEnabled(true);
            bntFiltrar.setEnabled(true);
            tabelaCt.setEnabled(false);
            bntDuplicate.setEnabled(false);
            bntMudaStepDescer.setEnabled(false);
            bntMudaStepSubir.setEnabled(false);
            bntEditParameter.setEnabled(false);
            bntReplace.setEnabled(false);
            this.setMaximizable(true);
            loadComboTS();

            radioGrupo.add(radioAntiga);
            radioGrupo.add(radioNova);
            radioGrupo.setSelected(radioNova.getModel(), true);

            jComboBoxTestFase.setSelectedItem("TS");
//            addIconInButton();

            new SwingWorker() {

                @Override
                protected Object doInBackground() {
                    renderTableStep();

                    return null;
                }

                @Override
                protected void done() {

                }

            }.execute();
        } catch (SVNException ex) {
            Logger.getLogger(InstanceScreenTSView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InstanceScreenTSView.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        testPlan = new TestPlanTSDao();
        addIconInButton();

//        tabelaSteps.addMouseMotionListener(new MouseMotionListener() {
//            public void mouseDragged(MouseEvent e) {
//                e.consume();
//                JComponent c = (JComponent) e.getSource();
//                TransferHandler handler = c.getTransferHandler();
//                handler.exportAsDrag(c, e, TransferHandler.MOVE);
//            }
//
//            public void mouseMoved(MouseEvent e) {
//            }
//        });
    }

    public static InstanceScreenTSView getInstance(String fase) {
        if (screen == null) {
            screen = new InstanceScreenTSView(fase);
        }

        return screen;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupTabelaStep = new javax.swing.JPopupMenu();
        radioGrupo = new javax.swing.ButtonGroup();
        dialogLog = new javax.swing.JDialog();
        jScrollPane5 = new javax.swing.JScrollPane();
        statusTextArea = new javax.swing.JTextArea();
        bntAddCTInPlan = new javax.swing.JButton();
        bntDeleteCt = new javax.swing.JButton();
        bntMudaStepSubir = new javax.swing.JButton();
        bntMudaStepDescer = new javax.swing.JButton();
        bntExportar = new javax.swing.JButton();
        bntCancelar = new javax.swing.JButton();
        bntOrdenar = new javax.swing.JButton();
        bntDuplicate = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaCt = new javax.swing.JTable();
        bntFiltrar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelaInstancia = new javax.swing.JTable();
        testPlanName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxTestFase = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxCR = new javax.swing.JComboBox();
        testPlanSTI = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        testPlanSystem = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaSteps = new javax.swing.JTable();
        bntEditParameter = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        labelQtdCTs = new javax.swing.JLabel();
        labelQtdCTs1 = new javax.swing.JLabel();
        bntReplace = new javax.swing.JButton();
        bntResetFiltro = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jDateChooserInicio = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jDateChooserFim = new com.toedter.calendar.JDateChooser();
        bntGenereteDate = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        radioAntiga = new javax.swing.JRadioButton();
        radioNova = new javax.swing.JRadioButton();
        jMonthChooser1 = new com.toedter.calendar.JMonthChooser();
        jDateChooserRelease = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        LabelStatus = new javax.swing.JLabel();
        bntStatus = new javax.swing.JButton();
        ProgressAguarde = new javax.swing.JProgressBar();
        bntAddFluxosInPlan = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        btnPublicarPlano = new javax.swing.JButton();
        btnCheckout = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuItemAbrirPlano = new javax.swing.JMenuItem();
        menuItemSalvarPlano = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuItemExportarPlano = new javax.swing.JMenuItem();

        JMenuItem menuItemCopyAll = new JMenuItem("Copiar para clipboard");
        menuItemCopyAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                copyClipboardStep();
            }
        });
        jPopupTabelaStep.add(menuItemCopyAll);
        JMenuItem menuItemCopySelected = new JMenuItem("Copiar selecinado para clipboard");
        menuItemCopySelected.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                copyClipboardStep();
            }
        });
        jPopupTabelaStep.add(menuItemCopySelected);

        dialogLog.setTitle("Log");
        dialogLog.setSize(new java.awt.Dimension(400, 600));
        dialogLog.setType(java.awt.Window.Type.POPUP);

        statusTextArea.setEditable(false);
        statusTextArea.setColumns(20);
        statusTextArea.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        statusTextArea.setForeground(new java.awt.Color(255, 0, 51));
        statusTextArea.setRows(5);
        jScrollPane5.setViewportView(statusTextArea);

        javax.swing.GroupLayout dialogLogLayout = new javax.swing.GroupLayout(dialogLog.getContentPane());
        dialogLog.getContentPane().setLayout(dialogLogLayout);
        dialogLogLayout.setHorizontalGroup(
            dialogLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
        );
        dialogLogLayout.setVerticalGroup(
            dialogLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Edição de Plano de Teste");
        setMinimumSize(new java.awt.Dimension(1150, 33));
        setPreferredSize(new java.awt.Dimension(1200, 650));
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

        bntAddCTInPlan.setEnabled(false);
        bntAddCTInPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntAddCTInPlanActionPerformed(evt);
            }
        });

        bntDeleteCt.setEnabled(false);
        bntDeleteCt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntDeleteCtActionPerformed(evt);
            }
        });

        bntMudaStepSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntMudaStepSubirActionPerformed(evt);
            }
        });

        bntMudaStepDescer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntMudaStepDescerActionPerformed(evt);
            }
        });

        bntExportar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        bntExportar.setText("Exportar");
        bntExportar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntExportarMouseClicked(evt);
            }
        });
        bntExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntExportarActionPerformed(evt);
            }
        });

        bntCancelar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        bntCancelar.setText("Cancelar");
        bntCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntCancelarMouseClicked(evt);
            }
        });
        bntCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCancelarActionPerformed(evt);
            }
        });

        bntOrdenar.setEnabled(false);
        bntOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntOrdenarActionPerformed(evt);
            }
        });

        bntDuplicate.setEnabled(false);
        bntDuplicate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntDuplicateActionPerformed(evt);
            }
        });

        tabelaCt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome CT", "Modificado por", "Data Modificação", "Sistema"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        tabelaCt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaCtMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tabelaCtMouseReleased(evt);
            }
        });
        tabelaCt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelaCtKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tabelaCt);
        if (tabelaCt.getColumnModel().getColumnCount() > 0) {
            tabelaCt.getColumnModel().getColumn(0).setMinWidth(20);
            tabelaCt.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabelaCt.getColumnModel().getColumn(0).setMaxWidth(60);
            tabelaCt.getColumnModel().getColumn(1).setMinWidth(50);
            tabelaCt.getColumnModel().getColumn(1).setPreferredWidth(200);
            tabelaCt.getColumnModel().getColumn(1).setMaxWidth(280);
            tabelaCt.getColumnModel().getColumn(2).setMinWidth(60);
            tabelaCt.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabelaCt.getColumnModel().getColumn(2).setMaxWidth(120);
            tabelaCt.getColumnModel().getColumn(3).setMinWidth(102);
            tabelaCt.getColumnModel().getColumn(3).setPreferredWidth(102);
            tabelaCt.getColumnModel().getColumn(3).setMaxWidth(120);
            tabelaCt.getColumnModel().getColumn(4).setMinWidth(80);
            tabelaCt.getColumnModel().getColumn(4).setPreferredWidth(80);
            tabelaCt.getColumnModel().getColumn(4).setMaxWidth(80);
        }
        //tabelaCt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaCt.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseMoved(MouseEvent e){
                Point p = e.getPoint();
                int row = tabelaCt.rowAtPoint(p);
                int column = tabelaCt.columnAtPoint(p);
                tabelaCt.setToolTipText(String.valueOf(tabelaCt.getValueAt(row,column)));
            }//end MouseMoved
        });

        bntFiltrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntFiltrarMouseClicked(evt);
            }
        });
        bntFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntFiltrarActionPerformed(evt);
            }
        });

        tabelaInstancia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nº Cenário", "Nº Caso de Teste", "Test Script Name", "hashCode", "Data Planejada", "CTs Prioritários", "Massa de Dados", "Passível de Re-Trabalho", "Regressão"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false, false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaInstancia.getTableHeader().setReorderingAllowed(false);
        tabelaInstancia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaInstanciaMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tabelaInstanciaMouseReleased(evt);
            }
        });
        tabelaInstancia.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                tabelaInstanciaInputMethodTextChanged(evt);
            }
        });
        tabelaInstancia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelaInstanciaKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tabelaInstancia);
        if (tabelaInstancia.getColumnModel().getColumnCount() > 0) {
            tabelaInstancia.getColumnModel().getColumn(0).setMinWidth(30);
            tabelaInstancia.getColumnModel().getColumn(0).setPreferredWidth(70);
            tabelaInstancia.getColumnModel().getColumn(0).setMaxWidth(100);
            tabelaInstancia.getColumnModel().getColumn(1).setMinWidth(60);
            tabelaInstancia.getColumnModel().getColumn(1).setPreferredWidth(100);
            tabelaInstancia.getColumnModel().getColumn(1).setMaxWidth(120);
            tabelaInstancia.getColumnModel().getColumn(2).setMinWidth(450);
            tabelaInstancia.getColumnModel().getColumn(2).setPreferredWidth(450);
            tabelaInstancia.getColumnModel().getColumn(2).setMaxWidth(500);
            tabelaInstancia.getColumnModel().getColumn(3).setMinWidth(0);
            tabelaInstancia.getColumnModel().getColumn(3).setPreferredWidth(0);
            tabelaInstancia.getColumnModel().getColumn(3).setMaxWidth(0);
            tabelaInstancia.getColumnModel().getColumn(4).setMinWidth(100);
            tabelaInstancia.getColumnModel().getColumn(4).setPreferredWidth(100);
            tabelaInstancia.getColumnModel().getColumn(4).setMaxWidth(100);
            tabelaInstancia.getColumnModel().getColumn(4).setCellEditor(new JDateChooserCellEditor());
            tabelaInstancia.getColumnModel().getColumn(4).setCellRenderer(new JDateChooserRenderer());
            tabelaInstancia.getColumnModel().getColumn(5).setMinWidth(30);
            tabelaInstancia.getColumnModel().getColumn(5).setPreferredWidth(70);
            tabelaInstancia.getColumnModel().getColumn(5).setMaxWidth(100);
            tabelaInstancia.getColumnModel().getColumn(6).setMinWidth(30);
            tabelaInstancia.getColumnModel().getColumn(6).setPreferredWidth(70);
            tabelaInstancia.getColumnModel().getColumn(6).setMaxWidth(100);
            tabelaInstancia.getColumnModel().getColumn(7).setMinWidth(30);
            tabelaInstancia.getColumnModel().getColumn(7).setPreferredWidth(70);
            tabelaInstancia.getColumnModel().getColumn(7).setMaxWidth(100);
            tabelaInstancia.getColumnModel().getColumn(8).setMinWidth(30);
            tabelaInstancia.getColumnModel().getColumn(8).setPreferredWidth(70);
            tabelaInstancia.getColumnModel().getColumn(8).setMaxWidth(100);
        }
        tabelaInstancia.setFocusable(false);
        jScrollPane4.setHorizontalScrollBar(new JScrollBar(0));
        tabelaInstancia.setAutoResizeMode (tabelaInstancia.AUTO_RESIZE_OFF);
        //tabelaInstancia.setDragEnabled(true);
        //  tabelaInstancia.setDropMode(DropMode.INSERT_ROWS);
        tabelaInstancia.setTransferHandler(new TableRowTransferHandler(tabelaInstancia));
        addTableListener();
        validNumCenario();
        filterHeader = new TableFilterHeader(tabelaInstancia, AutoChoices.ENABLED);
        //filterHeader = new TableFilterHeader(tabelaInstancia);
        filterHeader.setPosition(Position.TOP);

        JTableHeader header = tabelaInstancia.getTableHeader();
        //header.addMouseListener(new ColumnHeaderListener());
        tabelaInstancia.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseMoved(MouseEvent e){
                Point p = e.getPoint();
                int row = tabelaInstancia.rowAtPoint(p);
                int column = tabelaInstancia.columnAtPoint(p);
                tabelaInstancia.setToolTipText(String.valueOf(tabelaInstancia.getValueAt(row,column)));
            }//end MouseMoved
        });

        testPlanName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testPlanNameActionPerformed(evt);
            }
        });
        testPlanName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                testPlanNameKeyReleased(evt);
            }
        });

        jLabel2.setText("Título");

        jLabel3.setText("STI");

        jLabel4.setText("Test/Fase");

        jComboBoxTestFase.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxTestFaseItemStateChanged(evt);
            }
        });

        jLabel5.setText("CR/Fase");

        jComboBoxCR.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxCRItemStateChanged(evt);
            }
        });

        testPlanSTI.setName(""); // NOI18N
        testPlanSTI.setPreferredSize(new java.awt.Dimension(6, 19));
        testPlanSTI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                testPlanSTIKeyReleased(evt);
            }
        });

        jLabel6.setText("Product/Application");

        testPlanSystem.setEditable(false);
        testPlanSystem.setName(""); // NOI18N
        testPlanSystem.setPreferredSize(new java.awt.Dimension(6, 19));
        testPlanSystem.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                testPlanSystemInputMethodTextChanged(evt);
            }
        });

        tabelaSteps.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Step", "Descrição ", "Resultado esperado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabelaSteps);
        if (tabelaSteps.getColumnModel().getColumnCount() > 0) {
            tabelaSteps.getColumnModel().getColumn(0).setMinWidth(50);
            tabelaSteps.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabelaSteps.getColumnModel().getColumn(0).setMaxWidth(60);
            tabelaSteps.getColumnModel().getColumn(1).setMinWidth(200);
            tabelaSteps.getColumnModel().getColumn(1).setPreferredWidth(300);
            tabelaSteps.getColumnModel().getColumn(1).setMaxWidth(400);
            tabelaSteps.getColumnModel().getColumn(2).setMinWidth(100);
            tabelaSteps.getColumnModel().getColumn(2).setPreferredWidth(200);
            tabelaSteps.getColumnModel().getColumn(2).setMaxWidth(250);
        }

        bntEditParameter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntEditParameterActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setText("*");

        jLabel7.setForeground(new java.awt.Color(255, 0, 51));
        jLabel7.setText("*");

        jLabel8.setForeground(new java.awt.Color(255, 0, 51));
        jLabel8.setText("*");

        jLabel9.setForeground(new java.awt.Color(255, 0, 51));
        jLabel9.setText("*");

        labelQtdCTs.setText("Qtd CTs: ");

        labelQtdCTs1.setText("Qtd CTs: ");

        bntReplace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntReplaceActionPerformed(evt);
            }
        });

        bntResetFiltro.setText("Reset Filtro");
        bntResetFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntResetFiltroActionPerformed(evt);
            }
        });

        jLabel10.setText("Data Inicio:");

        jLabel11.setText("Data Fim:");

        bntGenereteDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntGenereteDateActionPerformed(evt);
            }
        });

        jLabel12.setText("Exportar para planilha :");

        radioAntiga.setText("Antiga");

        radioNova.setText("Nova");

        jDateChooserRelease.setDateFormatString("yyyy-MM");

        jLabel13.setText("Release:");

        LabelStatus.setText("Status: ");
        LabelStatus.setToolTipText(LabelStatus.getText());
        LabelStatus.setAutoscrolls(true);

        bntStatus.setText("...");
        bntStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntStatusActionPerformed(evt);
            }
        });

        ProgressAguarde.setToolTipText("Processando");
        ProgressAguarde.setBorderPainted(false);
        ProgressAguarde.setEnabled(false);
        ProgressAguarde.setFocusTraversalPolicyProvider(true);

        bntAddFluxosInPlan.setText("Fluxos");
        bntAddFluxosInPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntAddFluxosInPlanActionPerformed(evt);
            }
        });

        jLabel15.setForeground(new java.awt.Color(255, 0, 51));
        jLabel15.setText("*");

        btnPublicarPlano.setText("Publicar Plano");
        btnPublicarPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPublicarPlanoActionPerformed(evt);
            }
        });

        btnCheckout.setText("Checkout Plano");
        btnCheckout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckoutActionPerformed(evt);
            }
        });

        jMenu1.setText("Plano");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        menuItemAbrirPlano.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuItemAbrirPlano.setText("Abrir Plano");
        menuItemAbrirPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAbrirPlanoActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemAbrirPlano);

        menuItemSalvarPlano.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuItemSalvarPlano.setText("Salvar Plano");
        menuItemSalvarPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSalvarPlanoActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemSalvarPlano);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Importar Arquivo .plan");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        menuItemExportarPlano.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        menuItemExportarPlano.setText("Exportar Plano");
        menuItemExportarPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemExportarPlanoActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemExportarPlano);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelQtdCTs1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ProgressAguarde, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(LabelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bntStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(128, 128, 128)
                        .addComponent(bntExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(bntCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(44, 44, 44))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                    .addComponent(jLabel3)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel7))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                    .addComponent(jLabel2)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel1)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel8)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jComboBoxTestFase, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(testPlanSTI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(50, 50, 50)
                                                        .addComponent(jLabel5)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel9))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel6)))
                                                .addGap(10, 10, 10)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(testPlanSystem, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                                                    .addComponent(jComboBoxCR, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                            .addComponent(testPlanName)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jDateChooserInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel12)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(radioAntiga)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(radioNova)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addGap(2, 2, 2)
                                                .addComponent(jLabel15))
                                            .addComponent(jLabel11))
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jDateChooserFim, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                                            .addComponent(jDateChooserRelease, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(bntMudaStepDescer, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bntMudaStepSubir, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bntDuplicate, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bntOrdenar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bntEditParameter, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bntReplace, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bntGenereteDate, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bntResetFiltro)))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bntFiltrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bntAddCTInPlan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bntAddFluxosInPlan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bntDeleteCt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnPublicarPlano)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCheckout)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelQtdCTs)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPane3)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {bntAddCTInPlan, bntAddFluxosInPlan, bntDeleteCt, bntFiltrar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelQtdCTs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(testPlanName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(testPlanSTI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(testPlanSystem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jComboBoxTestFase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jComboBoxCR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jDateChooserInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jDateChooserFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12)
                                .addComponent(radioAntiga)
                                .addComponent(radioNova)
                                .addComponent(jLabel13)
                                .addComponent(jLabel15))
                            .addComponent(jDateChooserRelease, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPublicarPlano)
                            .addComponent(btnCheckout))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bntFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(bntMudaStepDescer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(bntDuplicate, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(bntOrdenar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(bntMudaStepSubir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bntEditParameter, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(bntReplace, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(bntResetFiltro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bntGenereteDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bntAddCTInPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bntDeleteCt, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bntAddFluxosInPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 245, Short.MAX_VALUE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelQtdCTs1)
                            .addComponent(ProgressAguarde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bntStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelStatus)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(bntExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bntCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {bntCancelar, bntExportar});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {bntAddCTInPlan, bntAddFluxosInPlan, bntDeleteCt, bntFiltrar});

        bntAddCTInPlan.setToolTipText("Instancia CTs selecionados");
        bntDeleteCt.setToolTipText("Delete CTs selecionados");
        bntMudaStepSubir.setToolTipText("Move para baixo CTs selecionados");
        bntMudaStepDescer.setToolTipText("Move para cima CTs selecionados");
        bntExportar.setToolTipText("Exporta plano para uma planilha");
        bntCancelar.setToolTipText("Cancela instância");
        bntOrdenar.setToolTipText("Ordena CTs");
        bntDuplicate.setToolTipText("Duplica CTs selecionados");
        bntFiltrar.setToolTipText("Seleciona filtro");
        testPlanName.setEditable(true);
        bntEditParameter.setToolTipText("Edita parâmetros");
        bntEditParameter.setToolTipText("Edita parâmetros");
        ProgressAguarde.setVisible(false);
        bntAddCTInPlan.setToolTipText("Instancia CTs selecionados");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bntExportarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntExportarMouseClicked


    }//GEN-LAST:event_bntExportarMouseClicked

    private void bntCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntCancelarMouseClicked
        if (JOptionPane.showConfirmDialog(this, "Ao sair da tela seus dados serão perdidos, deseja realmente sair?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (lineSelectTableCt != -1) {
                try {
                    svnRN = new SvnConnectionRN(this.fase);
                    svnRN.lockFile(false, listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getURL());
                } catch (SVNException ex) {
                    exceptionSVN(ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

            dispose();
        }
    }//GEN-LAST:event_bntCancelarMouseClicked

    private void tabelaCtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaCtMouseClicked
//        if (evt.getClickCount() == 1 && getContentPane().getCursor().getType() != Cursor.WAIT_CURSOR) {
//            loadCT2();
//        }

    }//GEN-LAST:event_tabelaCtMouseClicked

    private void tabelaCtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaCtKeyReleased
        if ((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN && getContentPane().getCursor().getType() != Cursor.WAIT_CURSOR)) {

            // tira a seleção dos steps
            loadCT2();

        }

    }//GEN-LAST:event_tabelaCtKeyReleased

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
//        if (lineSelectTableCt != -1) {
        try {
            TestCaseTSRN testCaseRN = new TestCaseTSRN(this.fase);
            testCaseRN.deleteDir(this.hashCode() + "");
        } catch (Exception ex) {
            addExceptionTextArea(ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
//        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void bntCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCancelarActionPerformed
        try {

            TestCaseTSRN testCaseRN = new TestCaseTSRN(this.fase);
            testCaseRN.deleteDir(this.hashCode() + "");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bntCancelarActionPerformed

    private void bntFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntFiltrarActionPerformed
        try {
            progress(true);
            callFilter();
            progress(false);
        } catch (Exception ex) {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            addExceptionTextArea(ex);
            logger.error("Erro ao abrir o arquivo: ", ex);
            ex.printStackTrace();
            progress(false);

        }
    }//GEN-LAST:event_bntFiltrarActionPerformed

    private void bntFiltrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntFiltrarMouseClicked

    }//GEN-LAST:event_bntFiltrarMouseClicked

    private void bntMudaStepSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntMudaStepSubirActionPerformed
        try {
            progress(true);
            if (tabelaInstancia.getSelectedRows().length > 0) {
                tabelaInstancia.editingStopped(null);
                moveUp();

            }
            progress(false);
        } catch (Exception ex) {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            addExceptionTextArea(ex);
            logger.error("Erro ao abrir o arquivo: ", ex);
            ex.printStackTrace();
            progress(false);

        }
    }//GEN-LAST:event_bntMudaStepSubirActionPerformed

    private void tabelaInstanciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaInstanciaMouseClicked
        if (evt.getClickCount() == 2) {
            callViewParameter();
        }

        atualizaContagemLabelTabelaInstancia();
        testPlan.getTestPlan().getTestCase().get(tabelaInstancia.getSelectedRow()).setPriority((Boolean) tabelaInstancia.getValueAt(tabelaInstancia.getSelectedRow(), 5));
        testPlan.getTestPlan().getTestCase().get(tabelaInstancia.getSelectedRow()).setData((Boolean) tabelaInstancia.getValueAt(tabelaInstancia.getSelectedRow(), 6));
        testPlan.getTestPlan().getTestCase().get(tabelaInstancia.getSelectedRow()).setRework((Boolean) tabelaInstancia.getValueAt(tabelaInstancia.getSelectedRow(), 7));
        testPlan.getTestPlan().getTestCase().get(tabelaInstancia.getSelectedRow()).setRegression((Boolean) tabelaInstancia.getValueAt(tabelaInstancia.getSelectedRow(), 8));
    }//GEN-LAST:event_tabelaInstanciaMouseClicked

    private void bntExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntExportarActionPerformed
        exportPlan(testPlan);
    }//GEN-LAST:event_bntExportarActionPerformed

    private void testPlanNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testPlanNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_testPlanNameActionPerformed

    private void bntAddCTInPlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntAddCTInPlanActionPerformed
        try {
            progress(true);
            if (tabelaCt.getSelectedRowCount() != 0) {
//            addCtInTestPlan();
//        }else{

               // loadListCT();
                addCTList();
            }

            atualizaContagemLabelTabelaInstancia();
            savePlan = false;
            progress(false);
        } catch (Exception ex) {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            addExceptionTextArea(ex);
            logger.error("Erro ao abrir o arquivo: ", ex);
            ex.printStackTrace();
            progress(false);

        }

    }//GEN-LAST:event_bntAddCTInPlanActionPerformed

    private void bntDeleteCtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntDeleteCtActionPerformed
        try {
            progress(true);
            tabelaInstancia.editingStopped(null);
            DefaultTableModel modelPlan = (DefaultTableModel) tabelaInstancia.getModel();
            int[] qtdRowsSelects = tabelaInstancia.getSelectedRows();
            int tamanho = qtdRowsSelects.length - 1;

            for (int i = tamanho; i >= 0; i--) {

//            testPlan.removeTestCase(qtdRowsSelects[i]);
//            listTestCasePlan.remove(qtdRowsSelects[i]);
                int line = Integer.parseInt(tabelaInstancia.getValueAt(qtdRowsSelects[i], 3).toString());

                for (int t = 0; t < modelPlan.getRowCount(); t++) {
                    if (line == Integer.parseInt(modelPlan.getValueAt(t, 3).toString())) {
                        modelPlan.removeRow(t);
                    }
                }

                for (int j = 0; j < testPlan.getTestPlan().getTestCase().size(); j++) {
                    if (line == testPlan.getTestPlan().getTestCase().get(j).getHashCode()) {
                        testPlan.removeTestCase(j);
                    }
                }

                savePlan = false;

            }

//        if (IsEmptyTablePlan(modelPlan)) {
//
//            bntDeleteCt.setEnabled(false);
//            bntOrdenar.setEnabled(false);
//            bntDuplicate.setEnabled(false);
//            bntMudaStepDescer.setEnabled(false);
//            bntMudaStepSubir.setEnabled(false);
//            bntEditParameter.setEnabled(false);
//
//        }
            if (modelPlan.getRowCount() == 0) {

                bntDeleteCt.setEnabled(false);
                bntOrdenar.setEnabled(false);
                bntDuplicate.setEnabled(false);
                bntMudaStepDescer.setEnabled(false);
                bntMudaStepSubir.setEnabled(false);
                bntEditParameter.setEnabled(false);
                bntGenereteDate.setEnabled(false);

            }

            atualizaContagemLabelTabelaInstancia();

            progress(false);

        } catch (Exception ex) {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            addExceptionTextArea(ex);
            logger.error("Erro ao abrir o arquivo: ", ex);
            ex.printStackTrace();
            progress(false);

        }

    }//GEN-LAST:event_bntDeleteCtActionPerformed

    private void tabelaCtMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaCtMouseReleased
        try {
            progress(true);
            DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();
            if (evt.getClickCount() == 2 && getContentPane().getCursor().getType() != Cursor.WAIT_CURSOR) {
                //loadCT2();
                showSteps();
            } else {

//            if(tabelaCt.getSelectedRowCount() > 1){
                //apaga linhas da tabela steps
                while (modelStep.getRowCount() > 0) {
                    modelStep.removeRow(0);
                }
//            }
            }
            atualizaLabelTabelaCt();
            progress(false);
        } catch (Exception ex) {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            addExceptionTextArea(ex);
            logger.error("erro ", ex);
            ex.printStackTrace();
            progress(false);

        }

    }//GEN-LAST:event_tabelaCtMouseReleased

    private void bntMudaStepDescerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntMudaStepDescerActionPerformed
        try {
            progress(true);
            if (tabelaInstancia.getSelectedRows().length > 0) {
                tabelaInstancia.editingStopped(null);
                moveDown();
                progress(false);

            }
        } catch (Exception ex) {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            addExceptionTextArea(ex);
            logger.error("Erro ao abrir o arquivo: ", ex);
            ex.printStackTrace();
            progress(false);

        }


    }//GEN-LAST:event_bntMudaStepDescerActionPerformed

    private void bntEditParameterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntEditParameterActionPerformed

        try {
            tabelaInstancia.editingStopped(new ChangeEvent(tabelaInstancia));
            progress(true);
            if (tabelaInstancia.getSelectedRow() != -1) {
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                callViewParameter();
                progress(false);
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } else {
                JOptionPane.showMessageDialog(null, "Favor selecioanar um CT!", "Instância", JOptionPane.WARNING_MESSAGE);
                progress(false);
            }
        } catch (Exception ex) {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            addExceptionTextArea(ex);
            logger.error("Erro ao abrir o arquivo: ", ex);
            ex.printStackTrace();
            progress(false);
        }

    }//GEN-LAST:event_bntEditParameterActionPerformed

    private void tabelaInstanciaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaInstanciaKeyReleased
        int lineSelect = tabelaInstancia.getSelectedRow();
        testPlan.getTestPlan().getTestCase().get(lineSelect).setNumeroCenario(tabelaInstancia.getValueAt(lineSelect, 0).toString());
        testPlan.getTestPlan().getTestCase().get(lineSelect).setNumeroCt(tabelaInstancia.getValueAt(lineSelect, 1).toString());
        testPlan.getTestPlan().getTestCase().get(lineSelect).setDataPlanejada((Date) tabelaInstancia.getValueAt(lineSelect, 4));

        System.out.println("com.accenture.view.InstanceScreenTSView.tabelaInstanciaKeyReleased()" + "data" + (Date) tabelaInstancia.getValueAt(lineSelect, 4));
    }//GEN-LAST:event_tabelaInstanciaKeyReleased

    private void tabelaInstanciaInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_tabelaInstanciaInputMethodTextChanged
        int lineSelect = tabelaInstancia.getSelectedRow();
        testPlan.getTestPlan().getTestCase().get(lineSelect).setNumeroCenario(tabelaInstancia.getValueAt(lineSelect, 0).toString());
        testPlan.getTestPlan().getTestCase().get(lineSelect).setNumeroCt(tabelaInstancia.getValueAt(lineSelect, 1).toString());
        testPlan.getTestPlan().getTestCase().get(lineSelect).setDataPlanejada((Date) tabelaInstancia.getValueAt(lineSelect, 4));
        System.out.println("com.accenture.view.InstanceScreenTSView.tabelaInstanciaKeyReleased()" + "data" + (Date) tabelaInstancia.getValueAt(lineSelect, 4));
    }//GEN-LAST:event_tabelaInstanciaInputMethodTextChanged

    private void bntOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntOrdenarActionPerformed

        try {
            progress(true);
            ordenarCTs();
            progress(false);
        } catch (Exception ex) {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            addExceptionTextArea(ex);
            logger.error("Erro ao abrir o arquivo: ", ex);
            ex.printStackTrace();
            progress(false);

        }


    }//GEN-LAST:event_bntOrdenarActionPerformed

    private void bntDuplicateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntDuplicateActionPerformed
        boolean numeroValido = false;
        int qtdLoop = 0;
        while (!numeroValido) {
            try {
                qtdLoop = Integer.parseInt(JOptionPane.showInternalInputDialog(this, "Quantas vezes?"));
                numeroValido = true;
            } catch (NumberFormatException e) {
                numeroValido = false;
                JOptionPane.showInternalMessageDialog(this, "Número informado inválido, favor verificar!", "Número Inválido", JOptionPane.WARNING_MESSAGE);
            }
        }
        try {
            progress(true);

            for (int j = 0; j < qtdLoop; j++) {

                if (tabelaInstancia.getSelectedRows().length > 0) {

                    DefaultTableModel model = (DefaultTableModel) tabelaInstancia.getModel();
                    int[] countRow = tabelaInstancia.getSelectedRows();

                    tabelaInstancia.editingStopped(null);

                    for (int i = 0; i < countRow.length; i++) {
                        TesteCaseTSBean tc = new TesteCaseTSBean();

                        tc.setExpectedResults(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).getExpectedResults());
                        tc.setStepDescription(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).getStepDescription());
                        tc.setDataPlanejada(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).getDataPlanejada());
                        tc.setFase(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).getFase());
                        tc.setListStep(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).getListStep());
                        tc.setProduct(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).getProduct());
                        tc.setSTIPRJ(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).getSTIPRJ());
                        tc.setTestCaseProperties(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).getTestCaseProperties());
                        tc.setTestPhase(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).getTestPhase());
                        tc.setTestPlan(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).getTestPlan());
                        tc.setTestScriptDescription(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).getTestScriptDescription());
                        tc.setTestScriptName(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).getTestScriptName());
                        tc.setNumeroCenario(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).getNumeroCenario());
                        tc.setNumeroCt(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).getNumeroCt());
                        tc.setHashCode(tc.hashCode());
                        tc.setComplexidade(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).getComplexidade());
                        tc.setParameters(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).getParameters());
                        tc.setAutomatizado(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).isAutomatizado());
                        tc.setHashCode(tc.hashCode());
                        tc.setData(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).isData());
                        tc.setPriority(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).isPriority());
                        tc.setRegression(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).isRegression());
                        tc.setRework(this.testPlan.getTestPlan().getTestCase().get(countRow[i]).isRework());

                        this.testPlan.getTestPlan().addTestCase(tc);
                        
                        this.testPlan.getTestPlan().addTestCase(this.testPlan.getTestPlan().getTestCase().get(countRow[i]));

                        model.addRow(new Object[]{tc.getNumeroCenario(), tc.getNumeroCt(), tc.getTestScriptName(), tc.getHashCode(), tc.getDataPlanejada(), tc.isPriority(), tc.isData(), tc.isRework(), tc.isRegression()});

                        atualizaContagemLabelTabelaInstancia();
                        progress(false);

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Selecione pelo menos um CT para duplicar", "CT Creator", JOptionPane.WARNING_MESSAGE);
                    progress(false);
                }
//        for (int i = 0; i < countRow.length; i++) {
//            tabelaInstancia.editingStopped(null);
//            model.setValueAt(0, countRow[i], 0);
//            tabelaInstancia.editingStopped(null);
//            model.setValueAt(0, countRow[i], 1);
//        }

            }

        } catch (Exception ex) {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            addExceptionTextArea(ex);
            logger.error("Erro ao abrir o arquivo: ", ex);
            ex.printStackTrace();
            progress(false);

        }
    }//GEN-LAST:event_bntDuplicateActionPerformed

    private void bntReplaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntReplaceActionPerformed
        progress(true);
        tabelaInstancia.editingStopped(null);
        callViewReplace();
        progress(false);
    }//GEN-LAST:event_bntReplaceActionPerformed

    private void menuItemSalvarPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSalvarPlanoActionPerformed
        try {
            ProgressAguarde.setIndeterminate(true);
            filterHeader.resetFilter();
            testPlan.getTestPlan().setName(testPlanName.getText());
            testPlan.getTestPlan().setSti(testPlanSTI.getText());
            testPlan.getTestPlan().setCrFase(jComboBoxCR.getSelectedItem().toString());
            testPlan.getTestPlan().setTestPhase(jComboBoxTestFase.getSelectedItem().toString());

//        testPlan.getTestPlan().setSti(testPlanSystem.getText());
            if (salvaPlanoFile(testPlan, false)) {
                savePlan = true;
                JOptionPane.showMessageDialog(null, "Plano salvo com sucesso!", "Mensagem ao usuário", JOptionPane.INFORMATION_MESSAGE);
                ProgressAguarde.setIndeterminate(false);
            }
            ProgressAguarde.setIndeterminate(false);
        } catch (Exception ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            addExceptionTextArea(ex);
            logger.error("Erro ao abrir o arquivo: ", ex);
            ex.printStackTrace();
            ProgressAguarde.setIndeterminate(false);

        }

    }//GEN-LAST:event_menuItemSalvarPlanoActionPerformed

    private void menuItemAbrirPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAbrirPlanoActionPerformed
        try {
            progress(true);
            ChoosePlanView dialog = new ChoosePlanView(null,true);
            dialog.setTitle("Abrir plano");
            dialog.setVisible(true);
            progress(false);
               
              
            
        } catch (Exception ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            addExceptionTextArea(ex);
            logger.error("Erro ao abrir o arquivo: ", ex);
            ex.printStackTrace();
            ProgressAguarde.setIndeterminate(false);

        }
    }//GEN-LAST:event_menuItemAbrirPlanoActionPerformed

    private void testPlanNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_testPlanNameKeyReleased
        savePlan = false;
    }//GEN-LAST:event_testPlanNameKeyReleased

    private void testPlanSTIKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_testPlanSTIKeyReleased
        savePlan = false;
    }//GEN-LAST:event_testPlanSTIKeyReleased

    private void jComboBoxTestFaseItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxTestFaseItemStateChanged
        savePlan = false;
    }//GEN-LAST:event_jComboBoxTestFaseItemStateChanged

    private void jComboBoxCRItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxCRItemStateChanged
        savePlan = false;
    }//GEN-LAST:event_jComboBoxCRItemStateChanged

    private void testPlanSystemInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_testPlanSystemInputMethodTextChanged
        savePlan = false;
    }//GEN-LAST:event_testPlanSystemInputMethodTextChanged

    private void tabelaInstanciaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaInstanciaMouseReleased
        atualizaContagemLabelTabelaInstancia();

    }//GEN-LAST:event_tabelaInstanciaMouseReleased

    private void menuItemExportarPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemExportarPlanoActionPerformed
        exportPlan(testPlan);
    }//GEN-LAST:event_menuItemExportarPlanoActionPerformed

    private void bntResetFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntResetFiltroActionPerformed
        filterHeader.resetFilter();
    }//GEN-LAST:event_bntResetFiltroActionPerformed

    private void bntGenereteDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntGenereteDateActionPerformed
        try {
            progress(true);
            if (tabelaInstancia.getRowCount() > 0) {
                if (jDateChooserInicio.getDate() == null || jDateChooserFim.getDate() == null) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencher os campos 'Data Inicio' e 'Data fim'.", "CT Creator", JOptionPane.WARNING_MESSAGE);
                } else if (FunctiosDates.finishDateBiggerThenStartDate(jDateChooserInicio.getDate(), jDateChooserFim.getDate())) {
                    List<Date> listDate = FunctiosDates.getWorkingDays(jDateChooserInicio.getDate(), jDateChooserFim.getDate());
                    int qtdTestCase = testPlan.getTestPlan().getTestCase().size();

                    if (qtdTestCase > listDate.size()) {
                        //quantidade de vezes que a data vai ser repetida
                        int qtdRepiticoes = qtdTestCase / listDate.size();
                        int mod = qtdTestCase % listDate.size();

                        //verifica mod
                        if (mod != 0) {
                            List<Date> tempList = new ArrayList<Date>();

                            int cont = 0;
                            for (int i = 0; i < listDate.size(); i++) {
                                for (int j = 0; j < qtdRepiticoes; j++) {
//                                testPlan.getTestPlan().getTestCase().get(j + cont).setDataPlanejada(listDate.get(i));
                                    tempList.add(listDate.get(i));
                                }
                                cont += qtdRepiticoes;
                            }

                            int i = 0;

                            while (mod > 0) {

                                tempList.add(listDate.get(i));

                                mod--;
                                i++;
                            }

                            Collections.sort(tempList, new Comparator<Date>() {
                                @Override
                                public int compare(Date t, Date t1) {
                                    return t.compareTo(t1);
                                }
                            });

                            for (int j = 0; j < tempList.size(); j++) {
                                testPlan.getTestPlan().getTestCase().get(j).setDataPlanejada(tempList.get(j));

                            }

                        } else {

                            int cont = 0;
                            for (int i = 0; i < listDate.size(); i++) {
                                for (int j = 0; j < qtdRepiticoes; j++) {
                                    testPlan.getTestPlan().getTestCase().get(j + cont).setDataPlanejada(listDate.get(i));
                                }
                                cont += qtdRepiticoes;
                            }

                        }
                    } else if (qtdTestCase == listDate.size()) {
                        for (int j = 0; j < listDate.size(); j++) {
                            testPlan.getTestPlan().getTestCase().get(j).setDataPlanejada(listDate.get(j));

                        }

                    } else if (qtdTestCase < listDate.size()) {
                        for (int j = 0; j < qtdTestCase; j++) {
                            testPlan.getTestPlan().getTestCase().get(j).setDataPlanejada(listDate.get(j));

                        }
                    }
                    upddateTableInstance();
                    progress(false);
                } else {
                    JOptionPane.showMessageDialog(null, "A data de inicio não pode ser maior que a data fim, favor verificar!", "CT Creator", JOptionPane.WARNING_MESSAGE);
                    progress(false);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, adcione pelo menos um CT para setar as datas.", "CT Creator", JOptionPane.WARNING_MESSAGE);
                progress(false);
            }
        } catch (Exception ex) {
            Log.log(Level.SEVERE, "ERROR AO ABRIR ARQUIVO", ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            addExceptionTextArea(ex);
            logger.error("Erro ao abrir o arquivo: ", ex);
            ex.printStackTrace();
            progress(false);

        }
    }//GEN-LAST:event_bntGenereteDateActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void bntStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntStatusActionPerformed
//        statusTextArea.setText(LabelStatus.getText());

        dialogLog.setVisible(true);

    }//GEN-LAST:event_bntStatusActionPerformed

    private void bntAddFluxosInPlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntAddFluxosInPlanActionPerformed
        callFlows();
    }//GEN-LAST:event_bntAddFluxosInPlanActionPerformed

    private void btnPublicarPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPublicarPlanoActionPerformed

        try {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            ProgressAguarde.setIndeterminate(true);
            filterHeader.resetFilter();
            testPlan.getTestPlan().setName(testPlanName.getText());
            testPlan.getTestPlan().setSti(testPlanSTI.getText());
            testPlan.getTestPlan().setCrFase(jComboBoxCR.getSelectedItem().toString());
            testPlan.getTestPlan().setTestPhase(jComboBoxTestFase.getSelectedItem().toString());

//        testPlan.getTestPlan().setSti(testPlanSystem.getText());
            if (salvaPlanoFile(testPlan, false)) {
                savePlan = true;
                SavePlanRN save = new SavePlanRN();
                String nomeArquivo = testPlanName.getText().replace(" ", "_") + ".plan";
                String system = "";
                if (testPlanSystem.getText().contains("STC")) {
                    system = "STC";
                } else {
                    system = testPlanSystem.getText();
                }

                String pathFile = ProjectSettings.PATH_FILE_SAVE + "/" + nomeArquivo;

                save.publicarPlano(pathFile, system);
                JOptionPane.showMessageDialog(null, "Plano publicado com sucesso!", "Mensagem ao usuário", JOptionPane.INFORMATION_MESSAGE);
                ProgressAguarde.setIndeterminate(false);
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
            ProgressAguarde.setIndeterminate(false);
        } catch (Exception ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            addExceptionTextArea(ex);
            logger.error("Erro ao abrir o arquivo: ", ex);
            ex.printStackTrace();
            ProgressAguarde.setIndeterminate(false);

        }


    }//GEN-LAST:event_btnPublicarPlanoActionPerformed

    private void btnCheckoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckoutActionPerformed
        try {
            ChoosePlanTsScreenView view = new ChoosePlanTsScreenView(this, null, true, this.fase);
//            view.centralizaJanela();            
            view.setVisible(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCheckoutActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
//       try {
//            
//                ProgressAguarde.setIndeterminate(true);
//                filterHeader.resetFilter();
//                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//                tabelaInstancia.editingStopped(null);
//                
//                //carrega arquivo
//                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//                TestCaseTSRN testCaseRN = new TestCaseTSRN(this.fase);
//                testCaseRN.importPlanSheet(getFileSheetPlan().getPath());
//                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//                
//                
//                bntDeleteCt.setEnabled(true);
//                bntOrdenar.setEnabled(true);
//                bntDuplicate.setEnabled(true);
//                bntMudaStepDescer.setEnabled(true);
//                bntMudaStepSubir.setEnabled(true);
//                bntEditParameter.setEnabled(true);
//                bntReplace.setEnabled(true);
//                isFirst = false;
//                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//                savePlan = true;
//            
//        } catch (Exception ex) {
//            Log.log(Level.SEVERE, "ERROR", ex);
//            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//            addExceptionTextArea(ex);
//            logger.error("Erro ao abrir o arquivo: ", ex);
//            ex.printStackTrace();
//            ProgressAguarde.setIndeterminate(false);
//
//        }

        try {
            if (savePlan || isFirst) {
                ProgressAguarde.setIndeterminate(true);
                filterHeader.resetFilter();
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                tabelaInstancia.editingStopped(null);
                loadFilePlan();
                bntDeleteCt.setEnabled(true);
                bntOrdenar.setEnabled(true);
                bntDuplicate.setEnabled(true);
                bntMudaStepDescer.setEnabled(true);
                bntMudaStepSubir.setEnabled(true);
                bntEditParameter.setEnabled(true);
                bntReplace.setEnabled(true);
                isFirst = false;
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                savePlan = true;
            } else if (JOptionPane.showConfirmDialog(this, "Você ainda não salvou o plano atual, suas alterações serão perdidas, deseja continuar?", "Mensagem ao usuário", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                filterHeader.resetFilter();
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                tabelaInstancia.editingStopped(null);
                loadFilePlan();
                bntDeleteCt.setEnabled(true);
                bntOrdenar.setEnabled(true);
                bntDuplicate.setEnabled(true);
                bntMudaStepDescer.setEnabled(true);
                bntMudaStepSubir.setEnabled(true);
                bntEditParameter.setEnabled(true);
                bntReplace.setEnabled(true);

                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                savePlan = true;
                ProgressAguarde.setIndeterminate(false);
            }
        } catch (Exception ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            addExceptionTextArea(ex);
            logger.error("Erro ao abrir o arquivo: ", ex);
            ex.printStackTrace();
            ProgressAguarde.setIndeterminate(false);

        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    public void centralizaJanela() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void loadComboTS() {

        try {
            testCaseRN = new TestCaseTSRN(this.fase);
            ArrayList testFase = testCaseRN.testPhase();
            ArrayList fases = testCaseRN.faseCRTestCase();
            for (int i = 0; i < testFase.size(); i++) {
                jComboBoxTestFase.addItem(testFase.get(i).toString());
            }
            for (int i = 0; i < fases.size(); i++) {
                jComboBoxCR.addItem(fases.get(i).toString());
            }
        } catch (SVNException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            exceptionSVN(ex.getMessage());
        } catch (IOException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void organizaNumeracaoStep(int numLinhas, DefaultTableModel model) {
        for (int j = 0; j < numLinhas; j++) {
            numeroStep = j + 1;
            System.out.println("Antes: " + model.getValueAt(j, 0));
            model.setValueAt("Step " + numeroStep, j, 0);
            System.out.println("Depois: " + model.getValueAt(j, 0));
            //                   numeroStep = Integer.parseInt(numStepTemp.substring(5,6));

        }
    }

    public File selecionarPlanilha() {
        FileFilter extensao = new FileNameExtensionFilter("Planilhas de Carga (*.xlsx, *.xlsm)", "xlsx, xlsm");
        JFileChooser file = new JFileChooser();
        file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        file.setDialogTitle("Selecione o diretório:");
        File diretorio = null;

        file.setFileFilter(extensao);
        file.setAcceptAllFileFilterUsed(false);
        file.setMultiSelectionEnabled(false);
        int i = file.showSaveDialog(null);

        if (i == 1) {
            JOptionPane.showMessageDialog(null, "Nenhum diretório foi selecionado! ", "Exportação de Carga", JOptionPane.INFORMATION_MESSAGE);
        } else {

            diretorio = file.getSelectedFile();

            System.out.println(diretorio.getPath());
        }

        return diretorio;
    }

    public File getFilePlan() {
        FileFilter extensao = new FileNameExtensionFilter(" (*.plan)", "plan");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Selecione o plano:");
        fileChooser.setCurrentDirectory(new File("C:\\FastPlan\\saves\\"));
        fileChooser.setApproveButtonText("Open");

        BorderLayout chooserLayout = (BorderLayout) fileChooser.getLayout();
        chooserLayout.getLayoutComponent(BorderLayout.NORTH).setVisible(false);
        //aqui está o X da questão ;D
        fileChooser.getComponent(0).setVisible(false);

        File diretorio = null;

        fileChooser.setFileFilter(extensao);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setMultiSelectionEnabled(false);

        int i = fileChooser.showOpenDialog(this);

        if (i == 1) {
            JOptionPane.showMessageDialog(null, "Nenhum arquivo foi selecionado! ", "Mensagem ao usuário", JOptionPane.WARNING_MESSAGE);
            return null;
        } else {

            diretorio = fileChooser.getSelectedFile();
            return diretorio;

        }

    }

    public File getFileSheetPlan() {
        FileFilter extensao = new FileNameExtensionFilter(" (*.xlsm)", "xlsm");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Selecione a planilha:");
        fileChooser.setCurrentDirectory(new File("C:"));
        fileChooser.setApproveButtonText("Open");

        BorderLayout chooserLayout = (BorderLayout) fileChooser.getLayout();
        chooserLayout.getLayoutComponent(BorderLayout.NORTH).setVisible(true);
        //aqui está o X da questão ;D
        //fileChooser.getComponent(0).setVisible(false);

        File diretorio = null;

        fileChooser.setFileFilter(extensao);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setMultiSelectionEnabled(false);

        int i = fileChooser.showOpenDialog(this);

        if (i == 1) {
            JOptionPane.showMessageDialog(null, "Nenhum arquivo foi selecionado! ", "Mensagem ao usuário", JOptionPane.WARNING_MESSAGE);
            return null;
        } else {

            diretorio = fileChooser.getSelectedFile();
            return diretorio;

        }

    }

//    private void addTCSVN() {
//        try {
//            boolean existeCT = false;
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            Date d = new Date(System.currentTimeMillis());
//
//            List<Step> listStep = new ArrayList<Step>();
//
//            testCaseRN = new TestCaseTSRN();
//            svnRN = new SvnConnectionRN();
//            testCaseRN.getTsDao().getTestCase().setTestScriptName(jTextNameTS.getText());
//            testCaseRN.getTsDao().getTestCase().setProduct(jComboSistemasTS.getSelectedItem().toString());
//            testCaseRN.getTsDao().getTestCase().setTestScriptDescription(jTextAreaDescriptionTS.getText() + "\n\nPré-Requisito: <<<pre_requisito>>>\n\nPós-Requisito:<<<pos_requisito>>>\n\nObservações:<<<observacoes>>>");
//            testCaseRN.getTsDao().getTestCase().setDataPlanejada(d);
//            tabelaSteps.editingStopped(null);
//            for (int cont = 0; cont < tabelaSteps.getRowCount(); cont++) {
//                Step step = new Step();
//                step.setNomeStep((tabelaSteps.getValueAt(cont, 0)).toString());
//                step.setDescStep((tabelaSteps.getValueAt(cont, 1)).toString());
//                step.setResultadoStep((tabelaSteps.getValueAt(cont, 2)).toString());
//                listStep.add(step);
//            }
//            testCaseRN.getTsDao().getTestCase().setListStep(listStep);
//            List<TestCaseTSPropertiesBean> listProperties = new SvnConnectionRN().search(testCaseRN.getTsDao().getTestCase().getProduct(), jTextNameTS.getText());
//            if (listProperties.isEmpty()) {
//                svnRN.addTestCaseSVN(testCaseRN.getTsDao().getTestCase(), "Cadastrando primeiro CT via Tela");
//                JOptionPane.showMessageDialog(null, "Caso de teste salvo com sucesso!", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
//                this.dispose();
//            } else {
//                for (int i = 0; i < listProperties.size(); i++) {
//                    String s = listProperties.get(i).getTestCaseName();
//                    if (s.equalsIgnoreCase(jTextNameTS.getText())) {
//                        JOptionPane.showMessageDialog(null, "Mensagem: Não é possível salvar o Caso de teste. \nCausa: Já existe um caso de teste com o mesmo nome na base de dados."
//                                + "\nSolução: Altere o nome caso de teste ou vá para a tela edição para editar-lo.", "Infomarção", JOptionPane.WARNING_MESSAGE);
//                        blockedFieldBnt(true);
//                        getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//                    }
//                }
//            }
//
//        } catch (SVNException ex) {
//            exceptionSVN(ex.getMessage());
//            blockedFieldBnt(true);
//            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//        } catch (IOException ex) {
//            exceptionSVN(ex.getMessage());
//            blockedFieldBnt(true);
//            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//        } catch (Exception ex) {
//            exceptionSVN(ex.getMessage());
//            blockedFieldBnt(true);
//            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//        }
//    }
    public void exceptionSVN(String exception) {

        switch (exception.substring(5, 12)) {
            case "E175005": //execeção arquivo já existe no diretório
                JOptionPane.showMessageDialog(null, "SVN: Erro ao salvar o Caso de teste. \nCausa: Já existe um caso de teste com o mesmo nome na base de dados."
                        + "\nSolução: Altere o nome caso de teste ou vá para a tela edição para editar-lo.", "SVN Erro", JOptionPane.ERROR_MESSAGE);
                break;

            case "E204899":
                JOptionPane.showMessageDialog(null, "SVN: Erro na tentativa de exclusão do arquivo. \nCausa: Provávelmente o arquivo está bloqueado ."
                        + "\nSolução: Desbloquei o arquivo via SVN e tente novamente.", "SVN Erro", JOptionPane.ERROR_MESSAGE);
                break;

            case "E170001":// Falha na autenticação
                JOptionPane.showMessageDialog(null, "SVN: Falha na autenticação do SVN \nCausa: Usuário ou senha incorretos."
                        + "\nSolução: Verifique usuário e senha na tela de configuração.", "SVN Erro", JOptionPane.ERROR_MESSAGE);
                break;

            case "E160013":// Falha na autenticação
                JOptionPane.showMessageDialog(null, "SVN: Diretório não encontrado no SVN.  \nCausa: Pasta do sistema não foi criada."
                        + "\nSolução: Solicite a criação da pasta do sistema.", "SVN Erro", JOptionPane.ERROR_MESSAGE);
                break;

            default:
                JOptionPane.showMessageDialog(null, "Geral: " + exception + " \nCausa: desconhecida"
                        + "\nSolução: Desconhecida.", "SVN Erro", JOptionPane.ERROR_MESSAGE);
                break;
        }

    }

    public void renderTableStep() {
        TableColumn col;
        for (int i = 0; i < tabelaSteps.getColumnCount() - 1; i++) {
            col = tabelaSteps.getColumnModel().getColumn(i);
            col.setCellRenderer(new TextAreaCellRenderer());
        }

        final DefaultTableModel modelo = (DefaultTableModel) tabelaSteps.getModel();

        class MyItemListener implements ItemListener {

            public void itemStateChanged(ItemEvent e) {
                Object source = e.getSource();
                if (source instanceof AbstractButton == false) {
                    return;
                }
                boolean checked = e.getStateChange() == ItemEvent.SELECTED;
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    modelo.setValueAt(checked, i, 3);
                    headerColumnAll = checked;
                }
            }
        }

        TableColumnModel modeloColuna = tabelaSteps.getColumnModel();
        modeloColuna.getColumn(3).setHeaderRenderer(new CabecalhoJTableCheckBox(new MyItemListener()));

    }

//    public void addIconInButton() {
//        ButtonIconBean iconBean = new ButtonIconBean();
//        bntAddStep.setIcon(iconBean.getIconBntAddNewStep());
//        bntDeleteStep.setIcon(iconBean.getIconBntRemoveStep());
//        bntMudaStepSubir.setIcon(iconBean.getIconBntMoveStepTop());
//        bntMudaStepDescer.setIcon(iconBean.getIconBntMoveStepBottom());
//        bntCancelar.setIcon(iconBean.getIconBntCacelAction());
//        bntSalvar.setIcon(iconBean.getIconBntSaveMinimum());
//        bntFiltrar.setIcon(iconBean.getIconBntFilter());
//        bntExcluirCT.setIcon(iconBean.getIconBntRemoveCt());
//        bntSalvarCT.setIcon(iconBean.getIconBntSaveMinimum());
//    }
//    public void blockedFieldBnt(boolean b) {
////        jComboFaseCR.setEnabled(b);
////        jComboSistemasTS.setEnabled(b);
//        jTextNameTS.setEnabled(b);
//        jTextAreaDescriptionTS.setEnabled(b);
//        bntAddStep.setEnabled(b);
//        bntCancelar.setEnabled(b);
//        bntDeleteStep.setEnabled(b);
//        bntMudaStepDescer.setEnabled(b);
//        bntMudaStepSubir.setEnabled(b);
//        bntSalvar.setEnabled(b);
//        tabelaSteps.setEnabled(b);
//        bntCopiar.setEnabled(b);
//        bntColar.setEnabled(b);
//        bntFiltrar.setEnabled(b);
//        bntExcluirCT.setEnabled(b);
//        bntSalvarCT.setEnabled(b);
//    }
    private void moveUp() {
        boolean fisrt = false;
        DefaultTableModel model = (DefaultTableModel) tabelaInstancia.getModel();
        int[] rows = tabelaInstancia.getSelectedRows();
//        model.moveRow(rows[0], rows[rows.length - 1], rows[0] - 1);

        ListSelectionModel selectionModel = tabelaInstancia.getSelectionModel();
        for (int i = 0; i < rows.length; i++) {
            if (rows[i] != 0) {
                model.moveRow(rows[i], rows[i], rows[i] - 1);
                testPlan.moveUpTestCase(rows[i]);
//                    Collections.swap(listTestCasePlan, rows[i], rows[i] - 1);
            }
            if (rows[i] != 0) {
                if (!fisrt) {
                    selectionModel.setSelectionInterval(rows[i] - 1, rows[i] - 1);
                    fisrt = true;
                } else {
                    selectionModel.addSelectionInterval(rows[i] - 1, rows[i] - 1);
                }
            } else {
                selectionModel.addSelectionInterval(rows[i], rows[i]);
            }
        }

//        tabelaInstacia.setRowSelectionInterval(rows[0] - 1, rows[rows.length - 1] - 1);
    }

    private void moveDown() {
        boolean fisrt = false;
        DefaultTableModel model = (DefaultTableModel) tabelaInstancia.getModel();
        int[] rows = tabelaInstancia.getSelectedRows();
//        model.moveRow(rows[0], rows[rows.length - 1], rows[0] + 1);
//        tabelaInstacia.setRowSelectionInterval(rows[0] + 1, rows[rows.length - 1] + 1);
        ListSelectionModel selectionModel = tabelaInstancia.getSelectionModel();
        for (int i = 0; i < rows.length; i++) {
            if (rows[i] != tabelaInstancia.getRowCount() - 1) {
                model.moveRow(rows[i], rows[i], rows[i] + 1);
                testPlan.moveDownTestCase(rows[i]);

//                         Collections.swap(listTestCasePlan, rows[i], rows[i] + 1);
            }
            if (rows[i] != tabelaInstancia.getRowCount() - 1) {
                if (!fisrt) {
                    selectionModel.setSelectionInterval(rows[i] + 1, rows[i] + 1);
                    fisrt = true;
                } else {
                    selectionModel.addSelectionInterval(rows[i] + 1, rows[i] + 1);
                }
            } else {
                selectionModel.addSelectionInterval(rows[i], rows[i]);
            }
        }

    }

    private void isExistsCT(String system, String nameCT) {
        boolean exists = false;
        try {
            svnRN = new SvnConnectionRN(this.fase);
            List<TestCaseTSPropertiesBean> listCtProperties = svnRN.search(system, nameCT);

            for (int i = 0; i < listCtProperties.size(); i++) {
                if (listCtProperties.get(i).getTestCaseName().equalsIgnoreCase(nameCT)) {
                    exists = true;
                }

            }
            if (exists) {
                JOptionPane.showMessageDialog(this, "O CT já existe na base de dados! Deseja edita-lo?", "SVN INFO", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SVNException ex) {
            exceptionSVN(ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void copyClipboardStep() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        int qtdSelecionados = 0;
        int countRow = tabelaSteps.getRowCount();

        StringBuilder sb = new StringBuilder();
        String space = " ";
        Step s = new Step();

        for (int i = 0; i < tabelaSteps.getRowCount(); i++) {
            sb.append("| " + tabelaSteps.getModel().getValueAt(i, 0) + " | " + tabelaSteps.getModel().getValueAt(i, 1) + " | " + tabelaSteps.getModel().getValueAt(i, 1) + " |");

        }

        StringSelection selection = new StringSelection(sb.toString());
        clipboard.setContents(selection, null);
    }

    public void loadTableCt(List<TestCaseTSPropertiesBean> listPropertiesBean, boolean sort) {
        DefaultTableModel model = (DefaultTableModel) tabelaCt.getModel();
        DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();
        listTestCaseTSPropertiesBean = listPropertiesBean;

        if (sort) {
            Collections.sort(listTestCaseTSPropertiesBean, new Comparator<TestCaseTSPropertiesBean>() {
                @Override
                public int compare(TestCaseTSPropertiesBean t, TestCaseTSPropertiesBean t1) {
                    return t.getDirEntry().getName().compareTo(t1.getDirEntry().getName());
                }
            });
        }

        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }

        if (listTestCaseTSPropertiesBean.size() == 0) {
//            blockedFieldBnt(false);
//            limpaCampos();
            while (modelStep.getRowCount() > 0) {
                modelStep.removeRow(0);
            }
        }

        String nameCT = "";
        String id = "";
        String modifyBy = "";
        Date dateModify;
        SimpleDateFormat sdf;

        for (int i = 0; i < listTestCaseTSPropertiesBean.size(); i++) {
            id = listPropertiesBean.get(i).getTesteCaseId();
            nameCT = listPropertiesBean.get(i).getTestCaseName();
            modifyBy = listTestCaseTSPropertiesBean.get(i).getDirEntry().getAuthor();
            System.out.println("CAPTURANDO URL: " + listTestCaseTSPropertiesBean.get(i).getDirEntry().getURL());
            dateModify = listTestCaseTSPropertiesBean.get(i).getDirEntry().getDate();
            sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String dataFormatada = sdf.format(dateModify);
            String system = listTestCaseTSPropertiesBean.get(i).getSystem();
            model.addRow(new String[]{id, nameCT, modifyBy, dataFormatada, system});
//            System.out.println(listStep.get(i).getCasoTeste());
        }
        tabelaCt.setEnabled(true);

        if (!isEmptyTableTastCase(model)) {
            bntAddCTInPlan.setEnabled(true);
        } else {
            bntAddCTInPlan.setEnabled(false);
        }

        labelQtdCTs.setText("Qtd CTs: " + tabelaCt.getRowCount());

    }
    
     public void loadTableCtBD(List<TesteCaseTSBean> listTesteCaseTSBean) {
        DefaultTableModel model = (DefaultTableModel) tabelaCt.getModel();
        DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();
        
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }

        if (listTesteCaseTSBean.size() == 0) {
//            blockedFieldBnt(false);
//            limpaCampos();
            while (modelStep.getRowCount() > 0) {
                modelStep.removeRow(0);
            }
        }

        String nameCT = "";
        String id = "";
        String modifyBy = "";
        Date dateModify;
        SimpleDateFormat sdf;

        for (int i = 0; i < listTesteCaseTSBean.size(); i++) {
            id = listTesteCaseTSBean.get(i).getId()+"";
            nameCT = listTesteCaseTSBean.get(i).getTestScriptName();
            modifyBy = listTesteCaseTSBean.get(i).getModifiedBy();
            dateModify = listTesteCaseTSBean.get(i).getModifyDate();
            sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String dataFormatada = sdf.format(dateModify);
            String system = listTesteCaseTSBean.get(i).getProduct();
            model.addRow(new String[]{id, nameCT, modifyBy, dataFormatada, system});
//            System.out.println(listStep.get(i).getCasoTeste());
        }
        tabelaCt.setEnabled(true);

        if (!isEmptyTableTastCase(model)) {
            bntAddCTInPlan.setEnabled(true);
        } else {
            bntAddCTInPlan.setEnabled(false);
        }

        labelQtdCTs.setText("Qtd CTs: " + tabelaCt.getRowCount());

    }

    public void loadFields(TesteCaseTSBean testCase) {
        DefaultTableModel model = (DefaultTableModel) tabelaSteps.getModel();
//        jTextNameTS.setText(testCase.getTestScriptName());
//        jComboFaseCR.setSelectedItem(testCase.getFase());
//        jComboSistemasTS.setSelectedItem(testCase.getProduct());
//        jTextAreaDescriptionTS.setText(testCase.getTestScriptDescription());

        for (int i = 0; i < testCase.getListStep().size(); i++) {
            model.addRow(new String[]{"Step " + 1, testCase.getListStep().get(i).getDescStep(), testCase.getListStep().get(i).getResultadoStep()});
            System.out.print(model.getValueAt(i, 1));
        }

        //ordena a numeração dos steps
        int numLinhas = model.getRowCount();
        for (int j = 0; j < numLinhas; j++) {
            numeroStep = j + 1;
            model.setValueAt("Step " + numeroStep, j, 0);
        }

    }

    private void loadCT() {
        try {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DefaultTableModel modelCT = (DefaultTableModel) tabelaCt.getModel();
            DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();
            // verifica se a linha clicada é a que está selecionada
            if (!ctbefore.equals(modelCT.getValueAt(tabelaCt.getSelectedRow(), 1).toString())) {

                if (tabelaSteps.getRowCount() > 0) {
                    // tira a seleção dos steps
                    tabelaSteps.editingStopped(new ChangeEvent(tabelaSteps));
                }
                //apaga linhas da tabela steps
                while (modelStep.getRowCount() > 0) {
                    modelStep.removeRow(0);
                }

//                if (lineSelectTableCt != -1 && !isCtLocked()) {
//                    svnRN.lockFile(false, listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getURL());
//                }
                //captra linha selecioanda
                lineSelectTableCt = tabelaCt.getSelectedRow();

                //Verifica se é o primeiro click na tabela e se o ct selecionado está bloqueado
                if (ctbefore.equals("")) {

                    svnRN.importBySvnForLocalFolder(SVNPropertiesVOBean.getInstance().getFolderTemplocal(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), this.fase);
                    svnRN.lockFile(true, listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getURL());
                    listTestCase = new TestCaseTSRN(this.fase).readSheet(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName());
                    loadFields(listTestCase.get(0));
//                    blockedFieldBnt(true);
                    ctbefore = modelCT.getValueAt(tabelaCt.getSelectedRow(), 1).toString();
                    ctBaixados.add(ctbefore);
                } else if (isDonwloadCt(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getTestCaseName())) {
                    svnRN.lockFile(true, listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getURL());
                    listTestCase = new TestCaseTSRN(this.fase).readSheet(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName());
                    loadFields(listTestCase.get(0));
//                    blockedFieldBnt(true);
                    ctbefore = modelCT.getValueAt(tabelaCt.getSelectedRow(), 1).toString();
                    ctBaixados.add(ctbefore);
                } else {
                    svnRN.importBySvnForLocalFolder(SVNPropertiesVOBean.getInstance().getFolderTemplocal(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), this.fase);
//                    svnRN.lockFile(true, listTestCaseTSPropertiesBean.get(lineSelect).getDirEntry().getURL());
//                    listTestCase = new TestCaseTSRN().readSheet(new SVNPropertiesVOBean().getFolderTemplocal() + listTestCaseTSPropertiesBean.get(lineSelect).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelect).getDirEntry().getName());
                    if (svnRN.isLocked(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem())) {
                        SVNLock lock = svnRN.getLock(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem());

                        if (lock.getOwner().equals(SVNPropertiesVOBean.getInstance().getUser())) {
                            listTestCase = new TestCaseTSRN(this.fase).readSheet(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName());
                            loadFields(listTestCase.get(0));
//                            blockedFieldBnt(true);
                            ctbefore = modelCT.getValueAt(tabelaCt.getSelectedRow(), 1).toString();
                            ctBaixados.add(ctbefore);
                        } else {
                            JOptionPane.showMessageDialog(null, "O CT selecionado está bloqueado pelo usuário " + lock.getOwner(), "SVN", JOptionPane.WARNING_MESSAGE);
                        }

                    } else {
                        svnRN.lockFile(true, listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getURL());
                        listTestCase = new TestCaseTSRN(this.fase).readSheet(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName());
                        loadFields(listTestCase.get(0));
//                        blockedFieldBnt(true);
                        ctbefore = modelCT.getValueAt(tabelaCt.getSelectedRow(), 1).toString();
                        ctBaixados.add(ctbefore);

                    }

                }

                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } catch (SVNException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        } catch (IOException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        } catch (Exception ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void loadCT2() {
        try {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DefaultTableModel modelCT = (DefaultTableModel) tabelaCt.getModel();
            DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();
            // verifica se a linha clicada é a que está selecionada
            if (!ctbefore.equals(modelCT.getValueAt(tabelaCt.getSelectedRow(), 1).toString())) {

                if (tabelaSteps.getRowCount() > 0) {
                    // tira a seleção dos steps
                    tabelaSteps.editingStopped(new ChangeEvent(tabelaSteps));
                }
                //apaga linhas da tabela steps
                while (modelStep.getRowCount() > 0) {
                    modelStep.removeRow(0);
                }

//                if (lineSelectTableCt != -1 && !isCtLocked()) {
//                    svnRN.lockFile(false, listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getURL());
//                }
                //captra linha selecioanda
                lineSelectTableCt = tabelaCt.getSelectedRow();
                if (isDonwloadCt(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getTestCaseName())) {
                    downloadCtSvn();
                } else {
                    svnRN.importBySvnForLocalFolder(SVNPropertiesVOBean.getInstance().getFolderTemplocal(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), this.hashCode(), this.fase);
                    downloadCtSvn();
                }

                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } catch (SVNException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        } catch (IOException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        } catch (Exception ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void loadListCT() {
        try {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DefaultTableModel modelCT = (DefaultTableModel) tabelaCt.getModel();
            DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();

            int[] selecionados = tabelaCt.getSelectedRows();
            // verifica se a linha clicada é a que está selecionada

            //apaga linhas da tabela steps
            while (modelStep.getRowCount() > 0) {
                modelStep.removeRow(0);
            }

            //captra linha selecioanda
            List<TesteCaseTSBean> listTemp = new ArrayList<TesteCaseTSBean>();

            for (int i = 0; i < selecionados.length; i++) {
                svnRN.importBySvnForLocalFolder(SVNPropertiesVOBean.getInstance().getFolderTemplocal(), listTestCaseTSPropertiesBean.get(selecionados[i]).getSystem(), listTestCaseTSPropertiesBean.get(selecionados[i]).getDirEntry().getName(), this.hashCode(), this.fase);
                listTemp.add(new TestCaseTSRN(this.fase).readSheet(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + this.hashCode() + "\\" + listTestCaseTSPropertiesBean.get(selecionados[i]).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(selecionados[i]).getDirEntry().getName()).get(0));

            }
            addCtInTestPlan(listTemp);

            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } catch (SVNException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        } catch (IOException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        } catch (Exception ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void loadListFluxo(String system, List<TestCaseTSPropertiesBean> listTestCaseTSPropertiesBean) {
        try {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DefaultTableModel modelCT = (DefaultTableModel) tabelaCt.getModel();
            DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();

//            int [] selecionados = tabelaCt.getSelectedRows();
            // verifica se a linha clicada é a que está selecionada
            //apaga linhas da tabela steps
            while (modelStep.getRowCount() > 0) {
                modelStep.removeRow(0);
            }

            while (modelCT.getRowCount() > 0) {
                modelCT.removeRow(0);
            }

            //captra linha selecioanda
            List<TesteCaseTSBean> listTemp = new ArrayList<TesteCaseTSBean>();

            loadTableCt(listTestCaseTSPropertiesBean, false);
            tabelaCt.getSelectionModel().setSelectionInterval(0, (listTestCaseTSPropertiesBean.size() - 1));

            for (int i = 0; i < listTestCaseTSPropertiesBean.size(); i++) {
                svnRN.importBySvnForLocalFolder(SVNPropertiesVOBean.getInstance().getFolderTemplocal(), system, listTestCaseTSPropertiesBean.get(i).getDirEntry().getName(), this.hashCode(), this.fase);
                listTemp.add(new TestCaseTSRN(this.fase).readSheet(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + this.hashCode() + "\\" + listTestCaseTSPropertiesBean.get(i).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(i).getDirEntry().getName()).get(0));

            }

            addCtInTestPlan(listTemp);

            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } catch (SVNException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            addExceptionTextArea(ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        } catch (IOException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            addExceptionTextArea(ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        } catch (Exception ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            addExceptionTextArea(ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        }
    }

    private boolean isDonwloadCt(String fileName) {
        boolean donwload = false;

        for (int i = 0; i < ctBaixados.size(); i++) {
            if (fileName.equals(ctBaixados.get(i))) {
                donwload = true;
            }
        }

        return donwload;
    }

    public void cleanFields() {
        DefaultTableModel modelCT = (DefaultTableModel) tabelaCt.getModel();
        DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();
        if (tabelaSteps.getRowCount() > 0) {
            // tira a seleção dos steps
            tabelaSteps.editingStopped(new ChangeEvent(tabelaSteps));
        }

        while (modelStep.getRowCount() > 0) {
            modelStep.removeRow(0);
        }
//        jTextNameTS.setText("");
//        jComboFaseCR.setSelectedItem("");
//        jComboSistemasTS.setSelectedItem("");
//        jTextAreaDescriptionTS.setText("");
    }

    private boolean isCtLocked() {
        boolean locked = false;
        DefaultTableModel modelCT = (DefaultTableModel) tabelaCt.getModel();
        try {
            if (svnRN.isLocked(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem())) {
                SVNLock lock = svnRN.getLock(listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName(), listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem());

                if (lock.getOwner().equals(SVNPropertiesVOBean.getInstance().getUser())) {
                    locked = false;
                } else {
                    locked = true;
//                    JOptionPane.showMessageDialog(null, "O CT selecionado está bloqueado pelo usuário " + lock.getOwner(), "SVN", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                locked = false;

            }
// Variables declaration - do not modify
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro " + ex.getMessage(), "SVN", JOptionPane.ERROR_MESSAGE);
        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, "Erro " + ex.getMessage(), "SVN", JOptionPane.ERROR_MESSAGE);
        }

        return locked;
    }

    private void downloadCtSvn() {
        DefaultTableModel modelCT = (DefaultTableModel) tabelaCt.getModel();
        try {
            listTestCase = new TestCaseTSRN(this.fase).readSheet(SVNPropertiesVOBean.getInstance().getFolderTemplocal() + this.hashCode() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getSystem() + "\\" + listTestCaseTSPropertiesBean.get(lineSelectTableCt).getDirEntry().getName());
            loadFields(listTestCase.get(0));
//                    blockedFieldBnt(true);
            ctbefore = modelCT.getValueAt(tabelaCt.getSelectedRow(), 1).toString();
            ctBaixados.add(ctbefore);

        } catch (IOException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            JOptionPane.showMessageDialog(null, "Erro " + ex.getMessage(), "SVN", JOptionPane.ERROR_MESSAGE);
        } catch (SVNException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            JOptionPane.showMessageDialog(null, "Erro " + ex.getMessage(), "SVN", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void callFilter() {
        FilterTestCaseTSForPlanScreenView dialogFiltro = null;
        try {
            dialogFiltro = new FilterTestCaseTSForPlanScreenView(this, null, true, this.fase);
        } catch (IOException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        dialogFiltro.centralizaJanelaDialogo(this);
        dialogFiltro.setVisible(true);

    }

    public void callFlows() {
        ChooseFlowScreenView dialogFlow = null;
        try {
            dialogFlow = new ChooseFlowScreenView(this, null, true, this.fase);
        } catch (Exception ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
//        dialogFlow.centralizaJanelaDialogo(this);
        dialogFlow.setVisible(true);
    }

    public void callViewReplace() {
        ReplaceScreenView dialogReplace = null;
        List<TesteCaseTSBean> listTc = new ArrayList<TesteCaseTSBean>();

        int[] qtdRowsSelects = tabelaInstancia.getSelectedRows();
        for (int i = 0; i < tabelaInstancia.getRowCount(); i++) {
            for (int j = 0; j < testPlan.getTestPlan().getTestCase().size(); j++) {
                if (Integer.parseInt(tabelaInstancia.getValueAt(i, 3).toString()) == testPlan.getTestPlan().getTestCase().get(j).getHashCode()) {
                    listTc.add(testPlan.getTestPlan().getTestCase().get(j));
                }
            }
        }

        try {
            dialogReplace = new ReplaceScreenView(this, null, true, listTc, this.fase);
        } catch (IOException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        dialogReplace.centralizaJanelaDialogo(this);
        dialogReplace.setVisible(true);

    }

    public void callViewParameter() {
        ParameterEditView dialogParameter = null;
        boolean first = true;

        dialogParameter = new ParameterEditView(this, null, true);
        dialogParameter.centralizaJanelaDialogo();
//            
//            for(int i =0; i < listcodeObject.size(); i++){
//                if(testPlan.getTestPlan().getTestCase().get(tabelaInstancia.getSelectedRow()).hashCode() == listcodeObject.get(i)){
//                    first = false;
//                }
//            }
//            
//            if(first){
//                listcodeObject.add(testPlan.getTestPlan().getTestCase().get(tabelaInstancia.getSelectedRow()).hashCode());
//                dialogParameter.loadParameterTable(testPlan, tabelaInstancia.getSelectedRow());
//            }else{
//                dialogParameter.loadParameterTable(testPlan, tabelaInstancia.getSelectedRow());
//            }
        //dialogParameter.loadParameterTable(testPlan, tabelaInstancia.getSelectedRow());
        dialogParameter.loadParameterTableDB(testPlan, tabelaInstancia.getSelectedRow());

        dialogParameter.setVisible(true);

    }

    public boolean validSystem() {
        boolean valid = false;
        valid = listTestCase.get(0).getProduct().equals(testPlanSystem.getText());
        if ((testPlanSystem.getText().equals("STC DADOS") || testPlanSystem.getText().equals("STC VOZ")) && (listTestCase.get(0).getProduct().equals("STC VOZ") || listTestCase.get(0).getProduct().equals("STC DADOS"))) {
            valid = true;
        }

        return valid;
    }

    public boolean validSystem(TesteCaseTSBean TestTemp) {
        boolean valid = false;
        valid = TestTemp.getProduct().equals(testPlanSystem.getText());
        if ((testPlanSystem.getText().equals("STC DADOS") || testPlanSystem.getText().equals("STC VOZ")) && (TestTemp.getProduct().equals("STC VOZ") || TestTemp.getProduct().equals("STC DADOS"))) {
            valid = true;
        }

        return valid;
    }

    public void addCtInTestPlan() {

        DefaultTableModel model = (DefaultTableModel) tabelaInstancia.getModel();
        DefaultTableModel modelCt = (DefaultTableModel) tabelaCt.getModel();

        if (!listTestCase.isEmpty()) {
            if (IsEmptyTablePlan(model) || validSystem()) {
                int cont = Integer.parseInt(JOptionPane.showInputDialog(this, "Digite a quantidade: "));
                int[] cenario = new int[cont];

                int qtd = 1;
                if (model.getRowCount() != 0) {
                    qtd = Integer.parseInt(model.getValueAt(model.getRowCount() - 1, 1).toString());
                }

                for (int i = 0; i < cont; i++) {
//                    testPlan.getTestPlan().addTestCase(listTestCase.get(0));
                    TesteCaseTSBean tc = new TesteCaseTSBean();

                    tc.setExpectedResults(listTestCase.get(0).getExpectedResults());
                    tc.setStepDescription(listTestCase.get(0).getStepDescription());
                    tc.setDataPlanejada(listTestCase.get(0).getDataPlanejada());
                    tc.setFase(listTestCase.get(0).getFase());
                    tc.setListStep(listTestCase.get(0).getListStep());
                    tc.setProduct(listTestCase.get(0).getProduct());
                    tc.setSTIPRJ(listTestCase.get(0).getSTIPRJ());
                    tc.setTestCaseProperties(listTestCase.get(0).getTestCaseProperties());
                    tc.setTestPhase(listTestCase.get(0).getTestPhase());
                    tc.setTestPlan(listTestCase.get(0).getTestPlan());
                    tc.setTestScriptDescription(listTestCase.get(0).getTestScriptDescription());
                    tc.setTestScriptName(listTestCase.get(0).getTestScriptName());

                    System.out.println("hashCode: " + tc.hashCode());

                    testPlan.getTestPlan().addTestCase(tc);
//                    listTestCasePlan.add(tc);

                    model.addRow(new String[]{qtd + "", qtd + "", listTestCase.get(0).getTestScriptName()});
                    qtd++;
                }
                if (!IsEmptyTablePlan(model)) {
                    bntDeleteCt.setEnabled(true);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Você está tentando inserir CTs de mais de um sistema no Plano de teste.\n"
                        + "Favor esvazie o plano de teste antes de inseri-los.", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } else {

            JOptionPane.showMessageDialog(null, "Por favor selecione um Caso de teste à "
                    + "ser inserido no Plano de teste", "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }

    public boolean isDigit(String s) {
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch < 48 || ch > 57) {
                return false;
            }
        }
        return true;
    }

    public void addCtInTestPlan(List<TesteCaseTSBean> list) {
        try {
            boolean first = true;
            int cont = 0;
            Validacao v = new Validacao();
            DefaultTableModel model = (DefaultTableModel) tabelaInstancia.getModel();
            DefaultTableModel modelCt = (DefaultTableModel) tabelaCt.getModel();

            while (first) {

                String qtd = "";
                qtd = JOptionPane.showInputDialog(this, "Digite a quantidade: ", "Quantidade de CTs", JOptionPane.CLOSED_OPTION);

                if (qtd == null || qtd.isEmpty()) {
                    cont = 0;
                    break;
                }

                if (isDigit(qtd)) {
                    cont = Integer.parseInt(qtd);
                    first = false;
                }
            }
            for (int i = 0; i < cont; i++) {

                String testCase = "";
                for (int j = 0; j < list.size(); j++) {
                    testPlanSystem.setText(listTestCaseTSPropertiesBean.get(tabelaCt.getSelectedRows()[j]).getSystem());

                    if (!list.isEmpty()) {
                        list.get(j).setProduct(listTestCaseTSPropertiesBean.get(tabelaCt.getSelectedRows()[j]).getSystem());
                        if (IsEmptyTablePlan(model, list.get(j)) || validSystem(list.get(j))) {

                            int[] cenario = new int[cont];

                            int qtd = 1;
                            if (model.getRowCount() != 0) {
                                qtd = Integer.parseInt(model.getValueAt(model.getRowCount() - 1, 1).toString());
                            }
                            if (!list.get(j).getTestScriptName().equals(testCase)) {
//                            callViewParameter(list.get(j).getStepDescription(),list.get(j).getExpectedResults(),list.get(j).getTestScriptName());
                                testCase = list.get(j).getTestScriptName();
                            }

//                    testPlan.getTestPlan().addTestCase(listTestCase.get(0));
                            TesteCaseTSBean tc = new TesteCaseTSBean();

                            tc.setExpectedResults(list.get(j).getExpectedResults());
                            tc.setStepDescription(list.get(j).getStepDescription());
                            tc.setDataPlanejada(list.get(j).getDataPlanejada());
                            tc.setFase(list.get(j).getFase());
                            tc.setListStep(list.get(j).getListStep());
//                            tc.setProduct(list.get(j).getProduct());
                            tc.setProduct(listTestCaseTSPropertiesBean.get(tabelaCt.getSelectedRows()[j]).getSystem());
                            tc.setSTIPRJ(list.get(j).getSTIPRJ());
                            tc.setTestCaseProperties(list.get(j).getTestCaseProperties());
                            tc.setTestPhase(list.get(j).getTestPhase());
                            tc.setTestPlan(list.get(j).getTestPlan());
                            tc.setTestScriptDescription(list.get(j).getTestScriptDescription());
                            tc.setTestScriptName(list.get(j).getTestScriptName());
                            tc.setNumeroCenario("00");
                            tc.setNumeroCt("00");
                            tc.setParameters(addParameter(tc));
                            tc.setHashCode(tc.hashCode());
                            tc.setDataPlanejada(new Date(System.currentTimeMillis()));
                            tc.setComplexidade(list.get(j).getComplexidade());
                            tc.setAutomatizado(list.get(j).isAutomatizado());

                            tc.setPriority(false);
                            tc.setData(false);
                            tc.setRework(false);
                            tc.setRegression(false);

                            switch (list.get(j).getComplexidade()) {

                                case "Muito Alta":
                                    tc.setComplexidade("Muito Alto");
                                    break;

                                case "Alto":
                                    tc.setComplexidade("Alto");
                                    break;

                                case "Media":
                                    tc.setComplexidade("Medio");
                                    break;

                                case "M\\u00E9dia":
                                    tc.setComplexidade("Medio");
                                    break;

                                case "Medio":
                                    tc.setComplexidade("Medio");
                                    break;

                                case "Baixa":
                                    tc.setComplexidade("Baixo");
                                    break;
                                case "Muito Baixa":
                                    tc.setComplexidade("Muito Baixo");
                                    break;

                                default:
                                    tc.setComplexidade(list.get(j).getComplexidade());
                            }

                            System.out.println("hashCode: " + tc.hashCode());

                            testPlan.getTestPlan().addTestCase(tc);
//                    listTestCasePlan.add(tc);

//                            model.addRow(new String[]{"00", "00", list.get(j).getTestScriptName(),tc.getHashCode()+""});
                            model.addRow(new Object[]{"00", "00", list.get(j).getTestScriptName(), tc.getHashCode(), tc.getDataPlanejada(), tc.isPriority(), tc.isData(), tc.isRework(), tc.isRegression()});

//                            model.setValueAt(tc.getDataPlanejada(), i, 4);
                            qtd++;

                            if (!IsEmptyTablePlan(model)) {
                                bntDeleteCt.setEnabled(true);
                                bntOrdenar.setEnabled(true);
                                bntDuplicate.setEnabled(true);
                                bntMudaStepDescer.setEnabled(true);
                                bntMudaStepSubir.setEnabled(true);
                                bntEditParameter.setEnabled(true);
                                bntReplace.setEnabled(true);
                                bntGenereteDate.setEnabled(true);
//                            for(int i = 0; i < model.getRowCount(); i++){
//                            tabelaInstancia.editingStopped(null);
//                            tabelaInstancia.setValueAt("0", i, 0);
//                            tabelaInstancia.editingStopped(null);
//                            tabelaInstancia.setValueAt("0", i, 1);
//                            }
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Você está tentando inserir CTs de mais de um sistema no Plano de teste.\n"
                                    + "Favor esvazie o plano de teste antes de inseri-los.", "Erro", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {

                        JOptionPane.showMessageDialog(null, "Por favor selecione um Caso de teste à "
                                + "ser inserido no Plano de teste", "Erro", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
            first = true;
        } catch (SQLException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            Logger.getLogger(InstanceScreenTSView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            Logger.getLogger(InstanceScreenTSView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addCtInTestPlanDB(List<TesteCaseTSBean> list) {
        try {
            boolean first = true;
            int cont = 0;
            Validacao v = new Validacao();
            DefaultTableModel model = (DefaultTableModel) tabelaInstancia.getModel();
            DefaultTableModel modelCt = (DefaultTableModel) tabelaCt.getModel();

            if (!list.isEmpty()) {
                while (first) {

                    String qtd = "";
                    qtd = JOptionPane.showInputDialog(this, "Digite a quantidade: ", "Quantidade de CTs", JOptionPane.CLOSED_OPTION);

                    if (qtd == null || qtd.isEmpty()) {
                        cont = 0;
                        break;
                    }

                    if (isDigit(qtd)) {
                        cont = Integer.parseInt(qtd);
                        first = false;
                    }
                }

                for (int i = 0; i < cont; i++) {
                    for (int j = 0; j < list.size(); j++) {

                        list.get(j).setNumeroCenario("00");
                        list.get(j).setNumeroCt("00");
                        TesteCaseTSBean tc = new TesteCaseTSBean();
                        tc.setExpectedResults(list.get(j).getExpectedResults());
                            tc.setStepDescription(list.get(j).getStepDescription());
                            tc.setDataPlanejada(list.get(j).getDataPlanejada());
                            tc.setFase(list.get(j).getFase());
                            
                            ///steps
                            List<Step> steps = new ArrayList<>();
                            
                            for(int s = 0; s < list.get(j).getListStep().size(); s++){
                                steps.add(new Step(list.get(j).getListStep().get(s).getId(), list.get(j).getListStep().get(s).getNomeStep(), list.get(j).getListStep().get(s).getDescStep(), list.get(j).getListStep().get(s).getResultadoStep(),list.get(j).getId()));
                            }
                            tc.setListStep(steps);
                            tc.setProduct(list.get(j).getProduct());
                            tc.setProduct(list.get(j).getProduct());
                            tc.setSTIPRJ(list.get(j).getSTIPRJ());
                            tc.setTestCaseProperties(list.get(j).getTestCaseProperties());
                            tc.setTestPhase(list.get(j).getTestPhase());
                            tc.setTestPlan(list.get(j).getTestPlan());
                            tc.setTestScriptDescription(list.get(j).getTestScriptDescription());
                            tc.setTestScriptName(list.get(j).getTestScriptName());
                            tc.setNumeroCenario("00");
                            tc.setNumeroCt("00");
                            
                            tc.setParameters(getParameters(tc));
                            
                          //  tc.setParameters(addParameter(tc));
                            tc.setHashCode(tc.hashCode());
                            tc.setDataPlanejada(new Date(System.currentTimeMillis()));
                            tc.setComplexidade(list.get(j).getComplexidade());
                            tc.setAutomatizado(list.get(j).isAutomatizado());

                            tc.setPriority(false);
                            tc.setData(false);
                            tc.setRework(false);
                            tc.setRegression(false);
                        
                            testPlan.getTestPlan().addTestCase(tc);

                        model.addRow(new Object[]{list.get(j).getNumeroCenario(), list.get(j).getNumeroCt(), list.get(j).getTestScriptName(), list.get(j).getHashCode(), list.get(j).getDataPlanejada(), list.get(j).isPriority(), list.get(j).isData(), list.get(j).isRework(), list.get(j).isRegression()});

                    }
                }

                if (!IsEmptyTablePlan(model)) {
                    bntDeleteCt.setEnabled(true);
                    bntOrdenar.setEnabled(true);
                    bntDuplicate.setEnabled(true);
                    bntMudaStepDescer.setEnabled(true);
                    bntMudaStepSubir.setEnabled(true);
                    bntEditParameter.setEnabled(true);
                    bntReplace.setEnabled(true);
                    bntGenereteDate.setEnabled(true);
//                            for(int i = 0; i < model.getRowCount(); i++){
//                            tabelaInstancia.editingStopped(null);
//                            tabelaInstancia.setValueAt("0", i, 0);
//                            tabelaInstancia.editingStopped(null);
//                            tabelaInstancia.setValueAt("0", i, 1);
//                            }
                }

            }
        } catch (Exception ex) {
            logger.error(ex);
            ex.printStackTrace();
        }
    }
            
//            for (int i = 0; i < cont; i++) {
//
//                String testCase = "";
//                for (int j = 0; j < list.size(); j++) {
//                    testPlanSystem.setText(listTestCaseTSPropertiesBean.get(tabelaCt.getSelectedRows()[j]).getSystem());
//
//                    if (!list.isEmpty()) {
//                        list.get(j).setProduct(listTestCaseTSPropertiesBean.get(tabelaCt.getSelectedRows()[j]).getSystem());
//                        if (IsEmptyTablePlan(model, list.get(j)) || validSystem(list.get(j))) {
//
//                            int[] cenario = new int[cont];
//
//                            int qtd = 1;
//                            if (model.getRowCount() != 0) {
//                                qtd = Integer.parseInt(model.getValueAt(model.getRowCount() - 1, 1).toString());
//                            }
//                            if (!list.get(j).getTestScriptName().equals(testCase)) {
////                            callViewParameter(list.get(j).getStepDescription(),list.get(j).getExpectedResults(),list.get(j).getTestScriptName());
//                                testCase = list.get(j).getTestScriptName();
//                            }
//
////                    testPlan.getTestPlan().addTestCase(listTestCase.get(0));
//                            TesteCaseTSBean tc = new TesteCaseTSBean();
//
//                            tc.setExpectedResults(list.get(j).getExpectedResults());
//                            tc.setStepDescription(list.get(j).getStepDescription());
//                            tc.setDataPlanejada(list.get(j).getDataPlanejada());
//                            tc.setFase(list.get(j).getFase());
//                            tc.setListStep(list.get(j).getListStep());
////                            tc.setProduct(list.get(j).getProduct());
//                            tc.setProduct(listTestCaseTSPropertiesBean.get(tabelaCt.getSelectedRows()[j]).getSystem());
//                            tc.setSTIPRJ(list.get(j).getSTIPRJ());
//                            tc.setTestCaseProperties(list.get(j).getTestCaseProperties());
//                            tc.setTestPhase(list.get(j).getTestPhase());
//                            tc.setTestPlan(list.get(j).getTestPlan());
//                            tc.setTestScriptDescription(list.get(j).getTestScriptDescription());
//                            tc.setTestScriptName(list.get(j).getTestScriptName());
//                            tc.setNumeroCenario("00");
//                            tc.setNumeroCt("00");
//                            tc.setParameters(addParameter(tc));
//                            tc.setHashCode(tc.hashCode());
//                            tc.setDataPlanejada(new Date(System.currentTimeMillis()));
//                            tc.setComplexidade(list.get(j).getComplexidade());
//                            tc.setAutomatizado(list.get(j).isAutomatizado());
//
//                            tc.setPriority(false);
//                            tc.setData(false);
//                            tc.setRework(false);
//                            tc.setRegression(false);
//
//                            switch (list.get(j).getComplexidade()) {
//
//                                case "Muito Alta":
//                                    tc.setComplexidade("Muito Alto");
//                                    break;
//
//                                case "Alto":
//                                    tc.setComplexidade("Alto");
//                                    break;
//
//                                case "Media":
//                                    tc.setComplexidade("Medio");
//                                    break;
//
//                                case "M\\u00E9dia":
//                                    tc.setComplexidade("Medio");
//                                    break;
//
//                                case "Medio":
//                                    tc.setComplexidade("Medio");
//                                    break;
//
//                                case "Baixa":
//                                    tc.setComplexidade("Baixo");
//                                    break;
//                                case "Muito Baixa":
//                                    tc.setComplexidade("Muito Baixo");
//                                    break;
//
//                                default:
//                                    tc.setComplexidade(list.get(j).getComplexidade());
//                            }
//
//                            System.out.println("hashCode: " + tc.hashCode());
//
//                            testPlan.getTestPlan().addTestCase(tc);
////                    listTestCasePlan.add(tc);
//
////                            model.addRow(new String[]{"00", "00", list.get(j).getTestScriptName(),tc.getHashCode()+""});
//                            model.addRow(new Object[]{"00", "00", list.get(j).getTestScriptName(), tc.getHashCode(), tc.getDataPlanejada(), tc.isPriority(), tc.isData(), tc.isRework(), tc.isRegression()});
//
////                            model.setValueAt(tc.getDataPlanejada(), i, 4);
//                            qtd++;
//
//                            if (!IsEmptyTablePlan(model)) {
//                                bntDeleteCt.setEnabled(true);
//                                bntOrdenar.setEnabled(true);
//                                bntDuplicate.setEnabled(true);
//                                bntMudaStepDescer.setEnabled(true);
//                                bntMudaStepSubir.setEnabled(true);
//                                bntEditParameter.setEnabled(true);
//                                bntReplace.setEnabled(true);
//                                bntGenereteDate.setEnabled(true);
////                            for(int i = 0; i < model.getRowCount(); i++){
////                            tabelaInstancia.editingStopped(null);
////                            tabelaInstancia.setValueAt("0", i, 0);
////                            tabelaInstancia.editingStopped(null);
////                            tabelaInstancia.setValueAt("0", i, 1);
////                            }
//                            }
//
//                        } else {
//                            JOptionPane.showMessageDialog(null, "Você está tentando inserir CTs de mais de um sistema no Plano de teste.\n"
//                                    + "Favor esvazie o plano de teste antes de inseri-los.", "Erro", JOptionPane.WARNING_MESSAGE);
//                        }
//                    } else {
//
//                        JOptionPane.showMessageDialog(null, "Por favor selecione um Caso de teste à "
//                                + "ser inserido no Plano de teste", "Erro", JOptionPane.WARNING_MESSAGE);
//                    }
//                }
//            }
//            first = true;
//        } catch (SQLException ex) {
//            Log.log(Level.SEVERE, "ERROR", ex);
//            Logger.getLogger(InstanceScreenTSView.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Log.log(Level.SEVERE, "ERROR", ex);
//            Logger.getLogger(InstanceScreenTSView.class.getName()).log(Level.SEVERE, null, ex);
//        }
    
    
    
    public boolean IsEmptyTablePlan(DefaultTableModel table) {

        boolean result = false;

        if (table.getRowCount() == 0) {
            result = true;
            if (listTestCase.get(0).getProduct().equals("STC DADOS") || listTestCase.get(0).getProduct().equals("STC VOZ")) {
                testPlanSystem.setText("STC");
            } else {
//                testPlanSystem.setText(listTestCase.get(0).getProduct());
                testPlanSystem.setText(listTestCaseTSPropertiesBean.get(0).getSystem());
            }

        }

        return result;
    }
    
       public boolean IsEmptyTablePlanDB(DefaultTableModel table) {

        return table.getRowCount() == 0;
    }

    public boolean IsEmptyTablePlan(DefaultTableModel table, TesteCaseTSBean test) {

        boolean result = false;

        String system = "";

        if (table.getRowCount() == 0) {

            switch (test.getProduct()) {
                case "STC DADOS":
                    result = true;
                    testPlanSystem.setText("STC");
                    lastSystem = "STC";
                    break;

                case "STC VOZ":
                    result = true;
                    testPlanSystem.setText("STC");
                    lastSystem = "STC";
                    break;

                case "Siebel 6.3":
                    result = true;
                    testPlanSystem.setText("Siebel");
                    lastSystem = "Siebel";
                    break;

                case "Siebel 8":
                    result = true;
                    testPlanSystem.setText("Siebel");
                    lastSystem = "Siebel";
                    break;

                default:
                    result = false;
                    testPlanSystem.setText(listTestCaseTSPropertiesBean.get(0).getSystem());
                    lastSystem = test.getProduct();
                    break;

            }

        } else {
            switch (test.getProduct()) {
                case "STC DADOS":
                    result = true;
                    testPlanSystem.setText("STC");
                    lastSystem = "STC";
                    break;

                case "STC VOZ":
                    result = true;
                    testPlanSystem.setText("STC");
                    lastSystem = "STC";
                    break;
            }

            if (lastSystem.equals(test.getProduct())) {
                result = true;
            }
        }

        return result;
    }

    public boolean isEmptyTableTastCase(DefaultTableModel table) {

        boolean result = false;

        if (table.getRowCount() == 0) {
            result = true;
        }

        return result;
    }

    public void addIconInButton() {
        ButtonIconBean iconBean = new ButtonIconBean();
        bntAddCTInPlan.setIcon(iconBean.getIconBntAddNewStep());
        bntDeleteCt.setIcon(iconBean.getIconBntRemoveStep());
        bntFiltrar.setIcon(iconBean.getIconBntFilter());
        bntMudaStepDescer.setIcon(iconBean.getIconBntMoveStepBottom());
        bntMudaStepSubir.setIcon(iconBean.getIconBntMoveStepTop());
        bntEditParameter.setIcon(iconBean.getIconParameter());
        bntOrdenar.setIcon(iconBean.getIconOrdem());
        bntDuplicate.setIcon(iconBean.getIconDuplicate());
        bntReplace.setIcon(iconBean.getIconReplace());
        bntGenereteDate.setIcon(iconBean.getIconBntDate());
    }

    public void sortAllRowsBy(DefaultTableModel model, int colIndex, boolean ascending) {
        Vector data = model.getDataVector();
        Collections.sort(data, new ColumnSorter(colIndex, ascending));
        model.fireTableStructureChanged();
    }        // Este comparador é usado classificar vetores dos dados

    private boolean salvaPlanoFile(TestPlanTSDao testPlan, boolean autoSave) {
        try {

            if (autoSave) {
                String nomeArquivo = "AUTOSAVE";
                FileOutputStream saveFile = new FileOutputStream("C:\\FastPlan\\saves\\" + nomeArquivo + ".plan");
                ObjectOutputStream stream = new ObjectOutputStream(saveFile);

                try {
                    FileWriter fw = new FileWriter("C:\\FastPlan\\saves\\" + nomeArquivo + ".plan");
                } catch (IOException e) {
                    System.out.println("File is open");
                }

                // salva o objeto
                stream.writeObject(this.testPlan);

                stream.close();
                return true;
            } else {

                if (!testPlanName.getText().equals("")) {
                    filterHeader.resetFilter();
                    String nomeArquivo = testPlanName.getText().replace(" ", "_");
                    FileOutputStream saveFile = new FileOutputStream("C:\\FastPlan\\saves\\" + nomeArquivo + ".plan");
                    ObjectOutputStream stream = new ObjectOutputStream(saveFile);

                    // salva o objeto
                    stream.writeObject(this.testPlan);

                    stream.close();
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Favor preencher o campo Título do plano", "Mensagem ao usuário", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            }
        } catch (Exception ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private TestPlanTSDao openPlanFile(String dirFile) throws FileNotFoundException, IOException, ClassNotFoundException {
        TestPlanTSDao plan = null;
        Object objeto = null;
        if (dirFile != null) {

            FileInputStream restFile = new FileInputStream(dirFile);
            ObjectInputStream stream = new ObjectInputStream(restFile);

            // recupera o objeto
            objeto = stream.readObject();
            plan = (TestPlanTSDao) objeto;
            stream.close();

            return plan;
        } else {
            return null;
        }

    }

    private void loadFilePlan() throws IOException, IOException, ClassNotFoundException {
        TestPlanTSDao temp = openPlanFile(getFilePlan().getPath());

        if (temp != null) {

            this.testPlan = temp;

            testPlanName.setText(this.testPlan.getTestPlan().getName());
            testPlanSystem.setText(this.testPlan.getTestPlan().getTestCase().get(0).getProduct());
            testPlanSTI.setText(this.testPlan.getTestPlan().getSti());
            jComboBoxCR.setSelectedItem(this.testPlan.getTestPlan().getCrFase());
            jComboBoxTestFase.setSelectedItem(this.testPlan.getTestPlan().getTestPhase());
            System.out.println("Valor p " + this.testPlan.getTestPlan().getTestCase().get(0).getParameters().get(0).getParameterValue());
            DefaultTableModel modelInstancia = (DefaultTableModel) tabelaInstancia.getModel();
            while (modelInstancia.getRowCount() > 0) {
                modelInstancia.removeRow(0);
            }

            for (int i = 0; i < this.testPlan.getTestPlan().getTestCase().size(); i++) {

                String name = this.testPlan.getTestPlan().getTestCase().get(i).getTestScriptName().replaceAll("\\d{2}\\.\\d{2}-", "");

                this.testPlan.getTestPlan().getTestCase().get(i).setTestScriptName(name);

                String desc = this.testPlan.getTestPlan().getTestCase().get(i).getTestScriptDescription();
                if (!desc.contains("<<<pre_requisito>>>")) {
                    desc = desc.replace("Pré-Requisito:", "Pré-Requisito: <<<pre_requisito>>>");
                    desc = desc.replace("Pós-Requisito:", "Pré-Requisito: <<<pos_requisito>>>");
                    desc = desc.replace("Observações:", "Observações: <<<observacoes>>>");
                    this.testPlan.getTestPlan().getTestCase().get(i).setTestScriptDescription(desc);
                }

                modelInstancia.addRow(new Object[]{this.testPlan.getTestPlan().getTestCase().get(i).getNumeroCenario(), this.testPlan.getTestPlan().getTestCase().get(i).getNumeroCt(), this.testPlan.getTestPlan().getTestCase().get(i).getTestScriptName(), this.testPlan.getTestPlan().getTestCase().get(i).getHashCode(), this.testPlan.getTestPlan().getTestCase().get(i).getDataPlanejada(), this.testPlan.getTestPlan().getTestCase().get(i).isPriority(), this.testPlan.getTestPlan().getTestCase().get(i).isData(), this.testPlan.getTestPlan().getTestCase().get(i).isRework(), this.testPlan.getTestPlan().getTestCase().get(i).isRegression()});
//            modelInstancia.setValueAt(this.testPlan.getTestPlan().getTestCase().get(i).getNumeroCt(), i, 1);
//            modelInstancia.setValueAt(this.testPlan.getTestPlan().getTestCase().get(i).getTestScriptName(), i, 2);
//            modelInstancia.setValueAt(this.testPlan.getTestPlan().getTestCase().get(i).getHashCode(), i, 3);
            }
        }

    }

    public class ColumnSorter implements Comparator {

        int colIndex;
        boolean ascending;

        ColumnSorter(int colIndex, boolean ascending) {
            this.colIndex = colIndex;
            this.ascending = ascending;
        }

        public int compare(Object a, Object b) {
            Vector v1 = (Vector) a;
            Vector v2 = (Vector) b;
            Object o1 = v1.get(colIndex);
            Object o2 = v2.get(colIndex);
            if (o1 instanceof String && ((String) o1).length() == 0) {
                o1 = null;
            }
            if (o2 instanceof String && ((String) o2).length() == 0) {
                o2 = null;
            }
            if (o1 == null && o2 == null) {
                return 0;
            } else if (o1 == null) {
                return 1;
            } else if (o2 == null) {
                return -1;
            } else if (o1 instanceof Comparable) {
                if (ascending) {
                    return ((Comparable) o1).compareTo(o2);
                } else {
                    return ((Comparable) o2).compareTo(o1);
                }
            } else if (ascending) {
                return o1.toString().compareTo(o2.toString());
            } else {
                return o2.toString().compareTo(o1.toString());
            }
        }
    }

    public void abreJanelaEmail(String dirPlanilha) {
        geraArquivoTemp(dirPlanilha);
        System.out.println("Planilha email: " + dirPlanilha);
        try {
            String arquivo = "c://Fastplan//outlook_ts.vbs";
            Runtime.getRuntime().exec("cmd /c" + arquivo);

        } catch (IOException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void geraArquivoTemp(String dir) {
        FileWriter arq = null;
        try {
            arq = new FileWriter("c:\\Fastplan\\tempDirPlanilha.txt");
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.printf(dir);
            arq.close();

        } catch (IOException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        }

    }

    public void abrePlanilhaGerada(String planilha) {
        try {
            Desktop desktop = null;
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
            }
            desktop.open(new File(planilha));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setPlanInstance(TestPlanTSDao plan) {
        this.testPlan = plan;
    }

    public List<ParameterBean> getParameters(TesteCaseTSBean tc) {

        List<ParameterBean> listParameters = new ArrayList<>();
        TesteCaseTSDAO tsDao = new TesteCaseTSDAO();
        for (int i = 0; i < tc.getListStep().size(); i++) {
            List<String> parameterNames = tsDao.getParameter(tc.getListStep().get(i).getDescStep());
            parameterNames.addAll(tsDao.getParameter(tc.getListStep().get(i).getResultadoStep()));
            for (int j = 0; j < parameterNames.size(); j++) {

                ParameterBean pb = new ParameterBean();
                pb.setApllyToAll(false);
                pb.setParameterName(parameterNames.get(j));
                pb.setParameterValue("");
                pb.setIdStep(tc.getListStep().get(i).getId());
                
                if (!listParameters.stream().anyMatch(p -> p.getParameterName().equals(pb.getParameterName()))) {
                    listParameters.add(pb);
                }

            }
        }
        
         List<String> parameterNames = tsDao.getParameter(tc.getTestScriptDescription());
        for (int j = 0; j < parameterNames.size(); j++) {

                ParameterBean pb = new ParameterBean();
                pb.setApllyToAll(false);
                pb.setParameterName(parameterNames.get(j));
                pb.setParameterValue("");
                pb.setIdStep(0);
                if (!listParameters.stream().anyMatch(p -> p.getParameterName().equals(pb.getParameterName()))) {
                    listParameters.add(pb);
                }

            }
        
        
        return listParameters;
    }
    
    public List<ParameterBean> addParameter(TesteCaseTSBean tc) {
        ParameterRN rn = new ParameterRN();
        List<ParameterBean> parameters = new ArrayList<ParameterBean>();
        DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();
        List<String> listDescriptionStep = rn.searchParameters(tc.getStepDescription());
        List<String> listExpectedResults = rn.searchParameters(tc.getExpectedResults());
        List<String> listDescriptionCT = rn.searchParameters(tc.getTestScriptDescription());

        String nameTemp = "";

        for (int i = 0; i < listDescriptionStep.size(); i++) {
            ParameterRN parameterRN = new ParameterRN();
            if (!listDescriptionStep.get(i).equals(nameTemp)) {
                parameterRN.addParameter(listDescriptionStep.get(i), "", false);
                nameTemp = listDescriptionStep.get(i);
                parameters.add(parameterRN.getParameter());
            }
        }

        for (int i = 0; i < listExpectedResults.size(); i++) {

            if (listDescriptionCT.size() > 0) {  //CR 15119
                for (int j = 0; j < listDescriptionStep.size(); j++) {
                    ParameterRN parameterRN = new ParameterRN();

                    if (!listExpectedResults.get(i).equals(nameTemp) && !listExpectedResults.get(i).equals(listDescriptionStep.get(j))) {
                        parameterRN.addParameter(listExpectedResults.get(i), "", false);
                        nameTemp = listExpectedResults.get(i);
                        parameters.add(parameterRN.getParameter());
                    }
                }
            } else {  //CR 15119
                ParameterRN parameterRN = new ParameterRN();
                if (!listExpectedResults.get(i).equals(nameTemp)) {
                    parameterRN.addParameter(listExpectedResults.get(i), "", false);
                    nameTemp = listExpectedResults.get(i);
                    parameters.add(parameterRN.getParameter());
                }
            }
        }

        for (int i = 0; i < listDescriptionCT.size(); i++) {
            ParameterRN parameterRN = new ParameterRN();
            String nameParameter = "";
            if (parameters.size() > 0) { //CR 15119
                for (int j = 0; j < parameters.size(); j++) {
                    if (!listDescriptionCT.get(i).equals(parameters.get(j).getParameterName()) && !listDescriptionCT.get(i).equals(nameParameter)) {
                        parameterRN.addParameter(listDescriptionCT.get(i), "", false);
                        parameters.add(parameterRN.getParameter());
                        nameParameter = listDescriptionCT.get(i);
                    }
                }
            } else { //CR 15119
                if (!listDescriptionCT.get(i).equals(nameParameter)) {
                    parameterRN.addParameter(listDescriptionCT.get(i), "", false);
                    parameters.add(parameterRN.getParameter());
                    nameParameter = listDescriptionCT.get(i);
                }
            }
        }

        return parameters;

    }

    public boolean validFiledsExport() {
        boolean b = false;
        if (!testPlanName.getText().equals("") && !testPlanSTI.getText().equals("") && !jComboBoxTestFase.getSelectedItem().toString().equals("") && !jComboBoxCR.getSelectedItem().toString().equals("") && jDateChooserRelease.getDate() != null) {
            b = true;
        }

        return b;

    }

    private void addTableListener() {
        tabelaInstancia.editingStopped(new ChangeEvent(tabelaInstancia));
        DefaultTableModel model = (DefaultTableModel) tabelaInstancia.getModel();
        model.addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent tme) {
//                tabelaInstancia.editingStopped(new ChangeEvent(tabelaInstancia));
                int lineSelect = tabelaInstancia.getSelectedRow();
                if (tme.getType() == TableModelEvent.UPDATE && lineSelect != -1) {

                    if ((tabelaInstancia.getValueAt(lineSelect, 0) != null || tabelaInstancia.getValueAt(lineSelect, 1) != null) && (tabelaInstancia.getValueAt(lineSelect, 0).toString().equals("") || tabelaInstancia.getValueAt(lineSelect, 1).toString().equals(""))) {
                        testPlan.getTestPlan().getTestCase().get(lineSelect).setNumeroCenario("00");
                        testPlan.getTestPlan().getTestCase().get(lineSelect).setNumeroCt("00");

                    } else {
                        testPlan.getTestPlan().getTestCase().get(lineSelect).setNumeroCenario(tabelaInstancia.getValueAt(lineSelect, 0).toString());
                        testPlan.getTestPlan().getTestCase().get(lineSelect).setNumeroCt(tabelaInstancia.getValueAt(lineSelect, 1).toString());
                        testPlan.getTestPlan().getTestCase().get(lineSelect).setDataPlanejada((Date) tabelaInstancia.getValueAt(lineSelect, 4));
                        System.out.println("com.accenture.view.InstanceScreenTSView.tabelaInstanciaKeyReleased()" + "data" + (Date) tabelaInstancia.getValueAt(lineSelect, 4));
                    }

                }
            }
        });

    }

    public void validNumCenario() {
//        if (tabelaInstancia.getColumnModel().getColumnCount() > 0) 
        TableColumn col = tabelaInstancia.getColumnModel().getColumn(0);
        TableColumn col1 = tabelaInstancia.getColumnModel().getColumn(1);
        class MyTableCellEditor extends AbstractCellEditor
                implements TableCellEditor {

            private JTextField text;
            JComponent component = new JTextField();

            MyTableCellEditor() {
                ((JTextField) component).addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyTyped(java.awt.event.KeyEvent e) {
                        keyTypedJText(e);
                    }
                });
                //CR 15112 - INICIO
                ((JTextField) component).addFocusListener(new java.awt.event.FocusAdapter() {
                    public void focusGained(java.awt.event.FocusEvent evt) {
                        ((JTextField) component).selectAll();
                    }

                    public void focusLost(java.awt.event.FocusEvent evt) {

                    }
                });
                //CR 15112 - FIM

            }

            public boolean stopCellEditing() {
                String s = (String) getCellEditorValue();
                //CR 15112 - inicio
                if (s.length() < 2) {
                    ((JTextField) component).setText("0" + s);
                } else {
                    ((JTextField) component).setText(s);
                }
                //CR 15112 - fim

                savePlan = false;

                return super.stopCellEditing();
            }

            public Component getTableCellEditorComponent(
                    JTable table, Object value,
                    boolean isSelected, int rowIndex, int vColIndex) {
                if (isSelected) {
                    //   
                }
                //CR 15112 - inicio

                ((JTextField) component).selectAll();

                if (((String) value).length() < 2) {
                    ((JTextField) component).setText("0" + (String) value);
                } else {
                    ((JTextField) component).setText((String) value);
                }
                ((JTextField) component).selectAll();
                //CR 15112 - fim
                return component;
            }

            public String getCellEditorValue() {
                String s = ((JTextField) component).getText();
//             for(int i = 0; i < s.length(); i++){   
//              Character caractere = s.charAt(i);   
//              if(!Character.isDigit(caractere)){   
//                  s.replace(caractere.toString(), "");
//              }  
//             }

                return s;

            }

            public void keyTypedJText(KeyEvent e) {
//        if (e.getSource()==((JTextField)component).getText()){
                if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
                    e.consume();//aciona esse propriedade para eliminar a ação do evento
                }

//        }
            }

            public void keyPressed(KeyEvent e) {
                // Evento não utilizado, porém precisa ser declarado
            }

            public void keyReleased(KeyEvent e) {
                // Evento não utilizado, porém precisa ser declarado
            }

            public void mouseClicked(MouseEvent evt) {
                ((JTextField) component).selectAll();
            }

        }
        col.setCellEditor(new MyTableCellEditor());
        col1.setCellEditor(new MyTableCellEditor());

    }

    public void upddateTableInstance() {
        DefaultTableModel modelCT = (DefaultTableModel) tabelaInstancia.getModel();

        tabelaInstancia.getSelectionModel().clearSelection();

        for (int i = 0; i < this.testPlan.getTestPlan().getTestCase().size(); i++) {
            modelCT.setValueAt(this.testPlan.getTestPlan().getTestCase().get(i).getDataPlanejada(), i, 4);
        }

//                //apaga linhas da tabela steps
//                while (modelCT.getRowCount() > 0) {
//                    modelCT.removeRow(0);
//                }
//                
//                for(int i = 0; i < this.testPlan.getTestPlan().getTestCase().size(); i++){
//                     modelCT.addRow(new Object[]{this.testPlan.getTestPlan().getTestCase().get(i).getNumeroCenario()+"", this.testPlan.getTestPlan().getTestCase().get(i).getNumeroCt()+"", this.testPlan.getTestPlan().getTestCase().get(i).getTestScriptName(), this.testPlan.getTestPlan().getTestCase().get(i).getHashCode()+"", this.testPlan.getTestPlan().getTestCase().get(i).getDataPlanejada()});
//                }
    }

    public void selectLineTableInstance(int line) {
        ListSelectionModel selectionModel = tabelaInstancia.getSelectionModel();
        selectionModel.addSelectionInterval(line, line);
    }

    class ColumnHeaderListener extends MouseAdapter {

        public void mouseClicked(MouseEvent evt) {
            JTable table = ((JTableHeader) evt.getSource()).getTable();
            TableColumnModel colModel = table.getColumnModel();

            // índice da coluna cujo titulo foi clicado
            int vColIndex = colModel.getColumnIndexAtX(evt.getX());
            int mColIndex = table.convertColumnIndexToModel(vColIndex);

            if (vColIndex == -1) {
                return;
            }

            System.out.println("O clique ocorreu no titulo da coluna com indice " + mColIndex);
        }
    }

    private void atualizaContagemLabelTabelaInstancia() {
        int ctSelect = tabelaInstancia.getSelectedRow() + 1;
        if (tabelaInstancia.getSelectedRowCount() > 1) {
            labelQtdCTs1.setText(tabelaInstancia.getSelectedRowCount() + " selecionados." + "Total: " + tabelaInstancia.getRowCount());
        } else {
            if (tabelaInstancia.getSelectedRow() == 0) {
                labelQtdCTs1.setText("Total: " + tabelaInstancia.getRowCount());
            } else {
                labelQtdCTs1.setText(ctSelect + " de " + tabelaInstancia.getRowCount());
            }

        }
    }

    private void atualizaLabelTabelaCt() {
        int ctSelect = tabelaCt.getSelectedRow() + 1;
        if (tabelaCt.getSelectedRowCount() > 1) {
            labelQtdCTs.setText(tabelaCt.getSelectedRowCount() + " selecionados.");
        } else {
            labelQtdCTs.setText(ctSelect + " de " + tabelaCt.getRowCount());
        }
    }

    public void atualizaDados(int hashCode, String valorNumeroCenario, String valorNumeroCT) {

        //atualiza objeto
        for (int j = 0; j < testPlan.getTestPlan().getTestCase().size(); j++) {
            if (hashCode == testPlan.getTestPlan().getTestCase().get(j).getHashCode()) {
                testPlan.getTestPlan().getTestCase().get(j).setNumeroCenario(valorNumeroCenario);
                testPlan.getTestPlan().getTestCase().get(j).setNumeroCt(valorNumeroCT);
            }
        }

        //atualiza tabela
        for (int j = 0; j < tabelaInstancia.getRowCount(); j++) {
            if (hashCode == Integer.parseInt(tabelaInstancia.getValueAt(j, 3).toString())) {
                tabelaInstancia.setRowSelectionInterval(j, j);
                System.out.println("Linha " + j + ": " + tabelaInstancia.getValueAt(j, 0));
                tabelaInstancia.setValueAt(valorNumeroCT, j, 1);
                tabelaInstancia.setValueAt(valorNumeroCenario, j, 0);
            }
        }
        DefaultTableModel modelPlan = (DefaultTableModel) tabelaInstancia.getModel();

        //atualiza model
        for (int j = 0; j < modelPlan.getRowCount(); j++) {
            if (hashCode == Integer.parseInt(modelPlan.getValueAt(j, 3).toString())) {
                modelPlan.setValueAt(valorNumeroCenario, j, 0);
                modelPlan.setValueAt(valorNumeroCenario, j, 0);
            }
        }
    }

    public void atualizaDadosBoleanos(int hashCode, boolean valor, String campo) {
        DefaultTableModel modelPlan = (DefaultTableModel) tabelaInstancia.getModel();
        switch (campo) {
            case NAME_ITEM_CTS_PRIORITARIOS:
                //atualiza objeto
                for (int j = 0; j < testPlan.getTestPlan().getTestCase().size(); j++) {
                    if (hashCode == testPlan.getTestPlan().getTestCase().get(j).getHashCode()) {
                        testPlan.getTestPlan().getTestCase().get(j).setPriority(valor);
                    }
                }

                //atualiza tabela
                for (int j = 0; j < tabelaInstancia.getRowCount(); j++) {
                    if (hashCode == Integer.parseInt(tabelaInstancia.getValueAt(j, 3).toString())) {
                        tabelaInstancia.setRowSelectionInterval(j, j);
                        tabelaInstancia.setValueAt(valor, j, 5);

                    }
                }

                //atualiza model
                for (int j = 0; j < modelPlan.getRowCount(); j++) {
                    if (hashCode == Integer.parseInt(modelPlan.getValueAt(j, 3).toString())) {
                        modelPlan.setValueAt(valor, j, 5);

                    }
                }

                break;

            case NAME_ITEM_MASSA_DADOS:
                //atualiza objeto
                for (int j = 0; j < testPlan.getTestPlan().getTestCase().size(); j++) {
                    if (hashCode == testPlan.getTestPlan().getTestCase().get(j).getHashCode()) {
                        testPlan.getTestPlan().getTestCase().get(j).setData(valor);
                    }
                }

                //atualiza tabela
                for (int j = 0; j < tabelaInstancia.getRowCount(); j++) {
                    if (hashCode == Integer.parseInt(tabelaInstancia.getValueAt(j, 3).toString())) {
                        tabelaInstancia.setRowSelectionInterval(j, j);
                        tabelaInstancia.setValueAt(valor, j, 6);

                    }
                }

                //atualiza model
                for (int j = 0; j < modelPlan.getRowCount(); j++) {
                    if (hashCode == Integer.parseInt(modelPlan.getValueAt(j, 3).toString())) {
                        modelPlan.setValueAt(valor, j, 6);

                    }
                }

                break;

            case NAME_ITEM_RETRABALHO:
                //atualiza objeto
                for (int j = 0; j < testPlan.getTestPlan().getTestCase().size(); j++) {
                    if (hashCode == testPlan.getTestPlan().getTestCase().get(j).getHashCode()) {
                        testPlan.getTestPlan().getTestCase().get(j).setRework(valor);
                    }
                }

                //atualiza tabela
                for (int j = 0; j < tabelaInstancia.getRowCount(); j++) {
                    if (hashCode == Integer.parseInt(tabelaInstancia.getValueAt(j, 3).toString())) {
                        tabelaInstancia.setRowSelectionInterval(j, j);
                        tabelaInstancia.setValueAt(valor, j, 7);

                    }
                }

                //atualiza model
                for (int j = 0; j < modelPlan.getRowCount(); j++) {
                    if (hashCode == Integer.parseInt(modelPlan.getValueAt(j, 3).toString())) {
                        modelPlan.setValueAt(valor, j, 7);

                    }
                }

                break;

            case NAME_ITEM_REGRESSAO:
                //atualiza objeto
                for (int j = 0; j < testPlan.getTestPlan().getTestCase().size(); j++) {
                    if (hashCode == testPlan.getTestPlan().getTestCase().get(j).getHashCode()) {
                        testPlan.getTestPlan().getTestCase().get(j).setRegression(valor);
                    }
                }

                //atualiza tabela
                for (int j = 0; j < tabelaInstancia.getRowCount(); j++) {
                    if (hashCode == Integer.parseInt(tabelaInstancia.getValueAt(j, 3).toString())) {
                        tabelaInstancia.setRowSelectionInterval(j, j);
                        tabelaInstancia.setValueAt(valor, j, 8);

                    }
                }

                //atualiza model
                for (int j = 0; j < modelPlan.getRowCount(); j++) {
                    if (hashCode == Integer.parseInt(modelPlan.getValueAt(j, 3).toString())) {
                        modelPlan.setValueAt(valor, j, 8);

                    }
                }

                break;
        }

    }

    public void ordenarCTs() {
        if (tabelaInstancia.getRowCount() > 0) {
            tabelaInstancia.editingStopped(new ChangeEvent(tabelaInstancia));
            DefaultTableModel model = (DefaultTableModel) tabelaInstancia.getModel();
            int num1 = 0; //alterado de double para int para solução da CR 15112
            int num2 = 0; //alterado de double para int para solução da CR 15112

            for (int j = 0; j < tabelaInstancia.getRowCount(); j++) {
                for (int i = 0; i < tabelaInstancia.getRowCount(); i++) {
                    //CR 15112
                    String concatena = tabelaInstancia.getValueAt(i, 0).toString() + tabelaInstancia.getValueAt(i, 1).toString();
                    num1 = Integer.parseInt(concatena);

                    if (tabelaInstancia.getRowCount() - 1 != i) {
                        //CR 15112
                        concatena = tabelaInstancia.getValueAt(i + 1, 0).toString() + tabelaInstancia.getValueAt(i + 1, 1).toString();
                        num2 = Integer.parseInt(concatena);
                    }

                    if ((tabelaInstancia.getRowCount() - 1 != i) && num1 > num2) {
                        tabelaInstancia.setRowSelectionInterval(i, i);
                        model.moveRow(i + 1, i + 1, i);
                        testPlan.getTestPlan().moveUpTestCase(i + 1);
                    }
                }
            }

            for (int j = 0; j < tabelaInstancia.getRowCount(); j++) {
                //Alterado para String para aceita 01, 00... CR 15112
                testPlan.getTestPlan().getTestCase().get(j).setNumeroCenario(tabelaInstancia.getValueAt(j, 0).toString());
                testPlan.getTestPlan().getTestCase().get(j).setNumeroCt(tabelaInstancia.getValueAt(j, 1).toString());
            }
        }
    }

    private void addTextLabelStatus(String status) {

        new SwingWorker() {

            @Override
            protected Object doInBackground() {
                LabelStatus.setText("Status:" + " " + status);

                return null;
            }

            @Override
            protected void done() {

            }

        }.execute();
    }

    private void exportPlan(TestPlanTSDao planTemp) {
        String nomeOriginalCT = "";
        DefaultTableModel modelPlan = (DefaultTableModel) tabelaInstancia.getModel();

        try {
//            testPlan.getTestPlan().setTestCase(listTestCasePlan);
            filterHeader.resetFilter();
            progress(true);
            if (validFiledsExport()) {
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                planTemp.getTestPlan().setName("[PLAN-" + testPlanSystem.getText() + "-" + jComboBoxTestFase.getSelectedItem().toString() + "-" + testPlanSTI.getText() + "] - " + testPlanName.getText());
                planTemp.getTestPlan().setSti(testPlanSTI.getText());
                planTemp.getTestPlan().setCrFase(jComboBoxCR.getSelectedItem().toString());
                planTemp.getTestPlan().setTestPhase(jComboBoxTestFase.getSelectedItem().toString());
                planTemp.getTestPlan().setRelease(FunctiosDates.dateToString(jDateChooserRelease.getDate(), "yyyy-MM"));
                addTextLabelStatus("Setando valores do plano");
//            String scriptName = "";

                for (int i = 0; i < planTemp.getTestPlan().getTestCase().size(); i++) {
                    for (int j = 0; j < planTemp.getTestPlan().getTestCase().get(i).getParameters().size(); j++) {
                        //verifica se o valor do parâmetro está null; 
                        addTextLabelStatus("Setando valores dos parâmetros " + j);
                        if (!planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterValue().equals("")) {
                            String parameterDesc = planTemp.getTestPlan().getTestCase().get(i).getStepDescription().replace("<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>", "<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterValue() + ">");
                            planTemp.getTestPlan().getTestCase().get(i).setStepDescription(parameterDesc);

                            String parameterResult = planTemp.getTestPlan().getTestCase().get(i).getExpectedResults().replace("<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>", "<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterValue() + ">");
                            planTemp.getTestPlan().getTestCase().get(i).setExpectedResults(parameterResult);

                            String parameterDescCt = planTemp.getTestPlan().getTestCase().get(i).getTestScriptDescription().replace("<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>", "<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterValue() + ">");
                            planTemp.getTestPlan().getTestCase().get(i).setTestScriptDescription(parameterDescCt);

//                            for(int loopStep = 0 ; loopStep < planTemp.getTestPlan().getTestCase().get(i).getListStep().size(); loopStep++){
//                                String descStep = planTemp.getTestPlan().getTestCase().get(i).getListStep().get(loopStep).getDescStep().replace("<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>", "<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterValue() + ">");
//                                String resultStep = planTemp.getTestPlan().getTestCase().get(i).getListStep().get(loopStep).getResultadoStep().replace("<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>", "<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterValue() + ">");
//                                
//                                planTemp.getTestPlan().getTestCase().get(i).getListStep().get(loopStep).setDescStep(descStep);
//                                planTemp.getTestPlan().getTestCase().get(i).getListStep().get(loopStep).setResultadoStep(resultStep);
//                                
//                            }
                        }
//                        else{
//                            if(planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName().equals("pre_requisito") || planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName().equals("pos_requisito") || planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName().equals("observacoes")){
//                                String parameterDescCt = planTemp.getTestPlan().getTestCase().get(i).getTestScriptDescription().replace("<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>", "<"+planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterValue()+">");
//                                planTemp.getTestPlan().getTestCase().get(i).setTestScriptDescription(parameterDescCt);
//                            }
//                        }
                    }
                }
                addTextLabelStatus("Parâmetros setados");

                for (int i = 0; i < modelPlan.getRowCount(); i++) {
                    String scriptName = "";
//                String nameTC = testPlan.getTestPlan().getTestCase().get(i).getTestScriptName();
                    String nameTC = modelPlan.getValueAt(i, 2).toString();
                    nomeOriginalCT
                            = //                if(nameTC.indexOf("-") == -1){
                            //                     scriptName = modelPlan.getValueAt(i, 0).toString()+"."+modelPlan.getValueAt(i, 1).toString()+"-"+nameTC;
                            //                }else{
                            //                     scriptName = modelPlan.getValueAt(i, 0).toString()+"."+modelPlan.getValueAt(i, 1).toString()+"-"+nameTC.substring(nameTC.indexOf("-")+1);
                            //                }
                            scriptName = modelPlan.getValueAt(i, 0).toString() + "." + modelPlan.getValueAt(i, 1).toString() + "-" + nameTC;
                    planTemp.getTestPlan().getTestCase().get(i).setTestScriptName(scriptName);

//                listTestCasePlan.get(i).setTestScriptName(scriptName);
                }

                //ordenando lista conforme tabela
                tabelaInstancia.editingStopped(null);
//        DefaultTableModel modelPlan = (DefaultTableModel) tabelaInstancia.getModel();

                for (int i = 0; i < tabelaInstancia.getRowCount(); i++) {
                    System.out.println("tabelaInstancia.getRowCount(); " + tabelaInstancia.getRowCount());
                    int line = Integer.parseInt(tabelaInstancia.getValueAt(i, 3).toString());
                    System.out.println("CT na tabela: " + tabelaInstancia.getValueAt(i, 0).toString() + "." + tabelaInstancia.getValueAt(i, 1).toString() + "-" + tabelaInstancia.getValueAt(i, 2).toString() + " posição: " + i);
                    for (int j = 0; j < planTemp.getTestPlan().getTestCase().size(); j++) {
                        if (line == planTemp.getTestPlan().getTestCase().get(j).getHashCode()) {

                            Collections.swap(planTemp.getTestPlan().getTestCase(), j, i);

                        }
                    }

                }

                String nomePlanilha = "PLAN-" + testPlanSystem.getText() + "-" + jComboBoxTestFase.getSelectedItem().toString() + "-" + testPlanSTI.getText() + "_" + "V1.xlsx";
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                final File destino = new File(selecionarPlanilha().getPath());
                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                if (radioAntiga.isSelected()) {
                    addTextLabelStatus("Gerando planilha antiga");
                    testCaseRN.createSheetExport(destino.getPath(), nomePlanilha, planTemp.getTestPlan());
                } else {
                    addTextLabelStatus("Gerando planilha nova");
                    testCaseRN.createSpreadsheetTSNew(destino.getPath(), nomePlanilha, planTemp.getTestPlan());

                }
                addTextLabelStatus("Planilha gerada");

                //voltando valores dos parâmetros nos steps
                for (int i = 0; i < planTemp.getTestPlan().getTestCase().size(); i++) {
                    for (int j = 0; j < planTemp.getTestPlan().getTestCase().get(i).getParameters().size(); j++) {

                        String parameterDesc = planTemp.getTestPlan().getTestCase().get(i).getStepDescription().replace("<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterValue() + ">", "<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>");
                        planTemp.getTestPlan().getTestCase().get(i).setStepDescription(parameterDesc);

                        String parameterResult = planTemp.getTestPlan().getTestCase().get(i).getExpectedResults().replace("<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterValue() + ">", "<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>");
                        planTemp.getTestPlan().getTestCase().get(i).setExpectedResults(parameterResult);

                        String parameterDescCt = planTemp.getTestPlan().getTestCase().get(i).getTestScriptDescription().replace("<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterValue() + ">", "<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>");
                        planTemp.getTestPlan().getTestCase().get(i).setTestScriptDescription(parameterDescCt);

//                             for(int loopStep = 0 ; loopStep < planTemp.getTestPlan().getTestCase().get(i).getListStep().size(); loopStep++){
//                                String descStep = planTemp.getTestPlan().getTestCase().get(i).getListStep().get(loopStep).getDescStep().replace("<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>", "<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>");
//                                String resultStep = planTemp.getTestPlan().getTestCase().get(i).getListStep().get(loopStep).getResultadoStep().replace("<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>", "<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>");
//                                
//                                planTemp.getTestPlan().getTestCase().get(i).getListStep().get(loopStep).setDescStep(descStep);
//                                planTemp.getTestPlan().getTestCase().get(i).getListStep().get(loopStep).setResultadoStep(resultStep);
//                                
//                 }
                    }

                }

                for (int i = 0; i < modelPlan.getRowCount(); i++) {
                    planTemp.getTestPlan().getTestCase().get(i).setTestScriptName(modelPlan.getValueAt(i, 2).toString());
                }

                getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                if (JOptionPane.showConfirmDialog(this, "Gerar email com a planilha em anexo?", "CT Creator", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    abreJanelaEmail(destino.getPath() + "\\" + nomePlanilha);
                    getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    addTextLabelStatus("Gerando email");
                }
                JOptionPane.showMessageDialog(this, "Plano criado com sucesso!", "CT Creator", JOptionPane.INFORMATION_MESSAGE);
//            testPlan.getTestPlan().getTestCase().clear();

                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("NOME_PLANO", planTemp.getTestPlan().getName());
                parametros.put("STI_PLANO", testPlanSTI.getText());
                parametros.put("PLANO_SISTEMA", testPlanSystem.getText());
                if (JOptionPane.showConfirmDialog(this, "Gerar relatório do plano?", "CT Creator", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    addTextLabelStatus("Gerando relatório");
                    BuildReport.geraRelatorio(planTemp.getTestPlan().getTestCase(), parametros, ProjectSettings.FILE_NAME_REPORT_PLANO);
                    addTextLabelStatus("Relatório gerado");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios!", "CT Creator", JOptionPane.INFORMATION_MESSAGE);
                progress(false);
            }
//            for (int i = 0; i < tabelaInstancia.getRowCount(); i++) {
//                String name = planTemp.getTestPlan().getTestCase().get(i).getTestScriptName();
//                 planTemp.getTestPlan().getTestCase().get(i).setTestScriptName(name.replace(name, name.substring(name.indexOf("-") + 1)));
//            }
            progress(false);

        } catch (IOException ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            returValues(planTemp, modelPlan);
            addTextLabelStatus("Erro: " + ex.toString());
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            logger.error("Erro ao exportar planilha", ex);
            addExceptionTextArea(ex);
            JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.ERROR_MESSAGE);
            progress(false);

        } catch (Exception ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            addTextLabelStatus("Erro: " + ex.toString());

            addExceptionTextArea(ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            returValues(planTemp, modelPlan);

            logger.error("Erro ao exportar planilha", ex);
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.ERROR_MESSAGE);
            progress(false);
        }

    }

    private void returValues(TestPlanTSDao planTemp, DefaultTableModel modelPlan) {
        //voltando valores dos parâmetros nos steps
        for (int i = 0; i < planTemp.getTestPlan().getTestCase().size(); i++) {
            for (int j = 0; j < planTemp.getTestPlan().getTestCase().get(i).getParameters().size(); j++) {

                String parameterDesc = planTemp.getTestPlan().getTestCase().get(i).getStepDescription().replace("<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterValue() + ">", "<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>");
                planTemp.getTestPlan().getTestCase().get(i).setStepDescription(parameterDesc);

                String parameterResult = planTemp.getTestPlan().getTestCase().get(i).getExpectedResults().replace("<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterValue() + ">", "<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>");
                planTemp.getTestPlan().getTestCase().get(i).setExpectedResults(parameterResult);

                String parameterDescCt = planTemp.getTestPlan().getTestCase().get(i).getTestScriptDescription().replace("<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterValue() + ">", "<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>");
                planTemp.getTestPlan().getTestCase().get(i).setTestScriptDescription(parameterDescCt);

//                             for(int loopStep = 0 ; loopStep < planTemp.getTestPlan().getTestCase().get(i).getListStep().size(); loopStep++){
//                                String descStep = planTemp.getTestPlan().getTestCase().get(i).getListStep().get(loopStep).getDescStep().replace("<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>", "<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>");
//                                String resultStep = planTemp.getTestPlan().getTestCase().get(i).getListStep().get(loopStep).getResultadoStep().replace("<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>", "<<<" + planTemp.getTestPlan().getTestCase().get(i).getParameters().get(j).getParameterName() + ">>>");
//                                
//                                planTemp.getTestPlan().getTestCase().get(i).getListStep().get(loopStep).setDescStep(descStep);
//                                planTemp.getTestPlan().getTestCase().get(i).getListStep().get(loopStep).setResultadoStep(resultStep);
//                                
//                 }
            }

        }

        for (int i = 0; i < modelPlan.getRowCount(); i++) {
            planTemp.getTestPlan().getTestCase().get(i).setTestScriptName(modelPlan.getValueAt(i, 2).toString());
        }
    }

    public void addExceptionTextArea(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();
        statusTextArea.setText(exceptionText);
    }

    public void progress(boolean run) {

//        if (run) {
//            task = new SwingWorker() {
//                @Override
//                protected Object doInBackground() throws Exception {
//                    ProgressAguarde.setIndeterminate(true);
//                    System.out.println("rodando");
//                    return null;
//                }
//
//                @Override
//                protected void done() {
//                   ProgressAguarde.printAll(ProgressAguarde.getGraphics());
//                }
//            };
//        } else {
//            task = new SwingWorker() {
//                @Override
//                protected Object doInBackground() throws Exception {
//                    ProgressAguarde.setIndeterminate(false);
//                    return null;
//                }
//
//                @Override
//                protected void done() {
//
//                }
//            };
//        }
//        
//        task.execute();
    }

    public void recuperaPlano(String name, String system) {
        try {

            if (!name.contains(".plan")) {
                name = name + ".plan";
            }

            SavePlanRN save = new SavePlanRN();
            save.download(name, system);

            TestPlanTSDao temp = null;

            FileInputStream restFile = new FileInputStream(ProjectSettings.PATH_FILE_SAVE + "/" + name);
            ObjectInputStream stream = new ObjectInputStream(restFile);

            // recupera o objeto
            Object objeto = stream.readObject();
            temp = (TestPlanTSDao) objeto;
            stream.close();

            this.testPlan = temp;

            testPlanName.setText(this.testPlan.getTestPlan().getName());
            testPlanSystem.setText(this.testPlan.getTestPlan().getTestCase().get(0).getProduct());
            testPlanSTI.setText(this.testPlan.getTestPlan().getSti());
            jComboBoxCR.setSelectedItem(this.testPlan.getTestPlan().getCrFase());
            jComboBoxTestFase.setSelectedItem(this.testPlan.getTestPlan().getTestPhase());
            System.out.println("Valor p " + this.testPlan.getTestPlan().getTestCase().get(0).getParameters().get(0).getParameterValue());
            DefaultTableModel modelInstancia = (DefaultTableModel) tabelaInstancia.getModel();
            while (modelInstancia.getRowCount() > 0) {
                modelInstancia.removeRow(0);
            }

            for (int i = 0; i < this.testPlan.getTestPlan().getTestCase().size(); i++) {

                String namePlan = this.testPlan.getTestPlan().getTestCase().get(i).getTestScriptName().replaceAll("\\d{2}\\.\\d{2}-", "");

                this.testPlan.getTestPlan().getTestCase().get(i).setTestScriptName(namePlan);

                String desc = this.testPlan.getTestPlan().getTestCase().get(i).getTestScriptDescription();
                if (!desc.contains("<<<pre_requisito>>>")) {
                    desc = desc.replace("Pré-Requisito:", "Pré-Requisito: <<<pre_requisito>>>");
                    desc = desc.replace("Pós-Requisito:", "Pré-Requisito: <<<pos_requisito>>>");
                    desc = desc.replace("Observações:", "Observações: <<<observacoes>>>");
                    this.testPlan.getTestPlan().getTestCase().get(i).setTestScriptDescription(desc);
                }

                modelInstancia.addRow(new Object[]{this.testPlan.getTestPlan().getTestCase().get(i).getNumeroCenario(), this.testPlan.getTestPlan().getTestCase().get(i).getNumeroCt(), this.testPlan.getTestPlan().getTestCase().get(i).getTestScriptName(), this.testPlan.getTestPlan().getTestCase().get(i).getHashCode(), this.testPlan.getTestPlan().getTestCase().get(i).getDataPlanejada(), this.testPlan.getTestPlan().getTestCase().get(i).isPriority(), this.testPlan.getTestPlan().getTestCase().get(i).isData(), this.testPlan.getTestPlan().getTestCase().get(i).isRework(), this.testPlan.getTestPlan().getTestCase().get(i).isRegression()});

            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void loadCTDB() {
        
        try {     
            
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            tabelaSteps.editingStopped(new ChangeEvent(tabelaSteps));
            
            //compare cts
            DefaultTableModel model = (DefaultTableModel) tabelaCt.getModel();

            DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();
            
            while (modelStep.getRowCount() > 0) {
                modelStep.removeRow(0);
            }
            int row = tabelaCt.getSelectedRow();

            int id = Integer.parseInt((String) model.getValueAt(row, 0));

            TesteCaseTSBean testeCaseSelect = testCaseRN.getTesteCaseTSBeanById(id);
            
            
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
           

            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

    }
    
    private void showSteps() {
        DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();
        while (modelStep.getRowCount() > 0) {
            modelStep.removeRow(0);
        }
        int id = Integer.parseInt(tabelaCt.getValueAt(tabelaCt.getSelectedRow(),0).toString());
        TestCaseTSRN tcrn = new TestCaseTSRN();
        TesteCaseTSBean tc = tcrn.getTesteCaseTSBeanById(id);
        
        loadFields(tc);
    }
    
    private void addCTList() {
        try {
            TestCaseTSRN tcrn = new TestCaseTSRN();
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DefaultTableModel modelCT = (DefaultTableModel) tabelaCt.getModel();
            DefaultTableModel modelStep = (DefaultTableModel) tabelaSteps.getModel();

            int[] selecionados = tabelaCt.getSelectedRows();
            int [] ids  = new  int [selecionados.length];
            for(int i =0; i<selecionados.length; i++){
                ids[i] = Integer.parseInt(modelCT.getValueAt(selecionados[i], 0).toString());
            }
            // verifica se a linha clicada é a que está selecionada

            //apaga linhas da tabela steps
            while (modelStep.getRowCount() > 0) {
                modelStep.removeRow(0);
            }

            //captra linha selecioanda
            List<TesteCaseTSBean> listTemp = tcrn.getTesteCaseTSBeanById(ids);

            
            //addCtInTestPlan(listTemp);
            addCtInTestPlanDB(listTemp);

            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
       
        } catch (Exception ex) {
            Log.log(Level.SEVERE, "ERROR", ex);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelStatus;
    private javax.swing.JProgressBar ProgressAguarde;
    private javax.swing.JButton bntAddCTInPlan;
    private javax.swing.JButton bntAddFluxosInPlan;
    private javax.swing.JButton bntCancelar;
    private javax.swing.JButton bntDeleteCt;
    private javax.swing.JButton bntDuplicate;
    private javax.swing.JButton bntEditParameter;
    private javax.swing.JButton bntExportar;
    private javax.swing.JButton bntFiltrar;
    private javax.swing.JButton bntGenereteDate;
    private javax.swing.JButton bntMudaStepDescer;
    private javax.swing.JButton bntMudaStepSubir;
    private javax.swing.JButton bntOrdenar;
    private javax.swing.JButton bntReplace;
    private javax.swing.JButton bntResetFiltro;
    private javax.swing.JButton bntStatus;
    private javax.swing.JButton btnCheckout;
    private javax.swing.JButton btnPublicarPlano;
    private javax.swing.JDialog dialogLog;
    private javax.swing.JComboBox jComboBoxCR;
    private javax.swing.JComboBox jComboBoxTestFase;
    private com.toedter.calendar.JDateChooser jDateChooserFim;
    private com.toedter.calendar.JDateChooser jDateChooserInicio;
    private com.toedter.calendar.JDateChooser jDateChooserRelease;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private com.toedter.calendar.JMonthChooser jMonthChooser1;
    private javax.swing.JPopupMenu jPopupTabelaStep;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel labelQtdCTs;
    private javax.swing.JLabel labelQtdCTs1;
    private javax.swing.JMenuItem menuItemAbrirPlano;
    private javax.swing.JMenuItem menuItemExportarPlano;
    private javax.swing.JMenuItem menuItemSalvarPlano;
    private javax.swing.JRadioButton radioAntiga;
    private javax.swing.ButtonGroup radioGrupo;
    private javax.swing.JRadioButton radioNova;
    private javax.swing.JTextArea statusTextArea;
    private javax.swing.JTable tabelaCt;
    private javax.swing.JTable tabelaInstancia;
    private javax.swing.JTable tabelaSteps;
    private javax.swing.JTextField testPlanName;
    private javax.swing.JTextField testPlanSTI;
    private javax.swing.JTextField testPlanSystem;
    // End of variables declaration//GEN-END:variables

}
