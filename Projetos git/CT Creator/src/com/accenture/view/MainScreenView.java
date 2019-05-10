/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.view;

import com.accenture.bean.SVNPropertiesVOBean;
import com.accenture.ts.dao.TesteCaseTSDAO;
import com.accenture.ts.rn.MainRN;
import static com.accenture.ts.rn.MainRN.THEME_NAME_MOTIF;
import static com.accenture.ts.rn.MainRN.THEME_NAME_NIMBUS;
import static com.accenture.ts.rn.MainRN.THEME_NAME_WINDOWS;
import static com.accenture.ts.rn.MainRN.THEME_NAME_WINDOWS_CLASSIC;
import com.accenture.ts.rn.TestCaseTSRN;
import com.accenture.util.ProjectSettings;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import org.tmatesoft.svn.core.SVNException;

class Background extends JPanel {

    private BufferedImage img = null;
    private final int x = 0;
    private final int y = 0;

    public Background(String urlImg) throws IOException {
        this.img = ImageIO.read(new File(urlImg));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics gr = (Graphics2D) g.create();
        gr.drawImage(img, x, y, this.getWidth(), this.getHeight(), this);
        gr.dispose();
    }
}

/**
 *
 * @author raphael.da.silva
 */
public class MainScreenView extends javax.swing.JFrame {

    private JDesktopPane desktop; // = new JDesktopPane();; 
    private JLabel label;

    /**
     * Creates new form Principal
     */
    public MainScreenView() {
        try {
            setTitle("CT Creator - Versão: " + SVNPropertiesVOBean.getInstance().getVersion());
            initComponents();
            //add menu tema
            ButtonGroup groupTema = new ButtonGroup();
            JRadioButtonMenuItem temaWindows = new JRadioButtonMenuItem("Windows");
            JRadioButtonMenuItem temaNimbus = new JRadioButtonMenuItem("Nimbus");
            JRadioButtonMenuItem temaMotif = new JRadioButtonMenuItem("Motif");
            MainRN mainRN = new MainRN();

            //clique no radio button
            temaNimbus.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    mainRN.setThemeNimbus();
                }
            });

            temaWindows.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    mainRN.setThemeWindows();
                }
            });

            temaMotif.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    mainRN.setThemeMotif();
                }
            });

            groupTema.add(temaNimbus);
            groupTema.add(temaMotif);
            groupTema.add(temaWindows);
            jMenuTema.add(temaNimbus);
            jMenuTema.add(temaWindows);
            jMenuTema.add(temaMotif);

            String theme = mainRN.loadTheme();
            
             switch (theme) {
                case THEME_NAME_MOTIF:
                    temaMotif.setSelected(true);
                    break;
                case THEME_NAME_NIMBUS:
                    temaNimbus.setSelected(true);
                case THEME_NAME_WINDOWS:
                    temaWindows.setSelected(true);
                default:
                    temaWindows.setSelected(true);
            }
             
          

            List<String> users = new ArrayList<String>();
            try {
                users = Arrays.asList(SVNPropertiesVOBean.getInstance().getUsersAuto().split(ProjectSettings.DELIDELIMITER_COMMA));
            } catch (NullPointerException e) {

                users = Arrays.asList(SVNPropertiesVOBean.getInstance().getUsersAuto().split(ProjectSettings.DELIDELIMITER_COMMA));

            }
            String userCurrent = SVNPropertiesVOBean.getInstance().getUser();

            if (users.contains(userCurrent)) {
                menuMatrizRastreabilidade.setVisible(true);
            } else {
                menuMatrizRastreabilidade.setVisible(false);
            }

//        URL url = this.getClass().getResource("carregado.gif");
//        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(url);
//        this.setIconImage(null);
            this.setIconImage(ImageIO.read(new File("res/logo_ctcreator.png")));
        } catch (IOException ex) {
            //Nao deve acontecer se o icone estiver no lugar certo.
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        //Background background = new Background("C:\\FastPlan\\res\\bnt\\ic_BACKGROUND.png");
        //background.paint(g);
//        desktop = new JDesktopPane() {
//            Image im = (new ImageIcon("C:\\FastPlan\\res\\bnt\\ic_BACKGROUND.png")).getImage();
//
//            public void paintComponent(Graphics g) {
//                g.drawImage(im, 0, 0, this);
//            }
//        };
        try {
            BufferedImage img = ImageIO.read(new File("C:\\FastPlan\\res\\bnt\\ic_BACKGROUND.png"));

            // A specialized layered pane to be used with JInternalFrames
            desktop = new JDesktopPane() {
                @Override
                protected void paintComponent(Graphics grphcs) {
                    super.paintComponent(grphcs);
                    grphcs.drawImage(img, 0, 0, null);
                }

                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(img.getWidth(), img.getHeight());
                }
            };

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        setDesktop(desktop);
        desktop.setBackground(Color.LIGHT_GRAY);
        setContentPane(desktop);
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

        setExtendedState(MAXIMIZED_BOTH);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuCT = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        itemMenuNovoCT = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        itemMenuCTExistente = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        itemMenuPesquisaCT = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        itemMenuConsultaCTAlm = new javax.swing.JMenuItem();
        menuImportarExportar = new javax.swing.JMenu();
        itemMenuImportaStepPadrao = new javax.swing.JMenuItem();
        itemMenuImportaCT = new javax.swing.JMenuItem();
        itemMenuExportarPlanilhaTI = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItemFuncionalidade = new javax.swing.JMenuItem();
        menuMatrizRastreabilidade = new javax.swing.JMenu();
        itemMenuComponentes = new javax.swing.JMenuItem();
        itemMenuScripts = new javax.swing.JMenuItem();
        menuConfiguracoes = new javax.swing.JMenu();
        itemMenuConfiguracoes = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        menuItemCT = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuTema = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        jToolBar1.setRollover(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setName("principal"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jMenuBar1.setBackground(java.awt.Color.white);
        jMenuBar1.setForeground(java.awt.Color.darkGray);
        jMenuBar1.setPreferredSize(new java.awt.Dimension(440, 55));
        jMenuBar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuBar1MouseClicked(evt);
            }
        });

        menuCT.setBackground(java.awt.Color.white);
        menuCT.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\32x32\\package_editors.png")); // NOI18N
        menuCT.setText("Casos de Teste");
        menuCT.setAutoscrolls(true);
        menuCT.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuCT.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        menuCT.setMaximumSize(new java.awt.Dimension(153, 32767));
        menuCT.setPreferredSize(new java.awt.Dimension(153, 5));
        menuCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCTActionPerformed(evt);
            }
        });

        jMenu1.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\32x32\\folder_new.png")); // NOI18N
        jMenu1.setText("Novo");
        jMenu1.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        jMenu1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        itemMenuNovoCT.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.ALT_MASK));
        itemMenuNovoCT.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        itemMenuNovoCT.setText("TI");
        itemMenuNovoCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuNovoCTActionPerformed(evt);
            }
        });
        jMenu1.add(itemMenuNovoCT);

        jMenuItem2.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        jMenuItem2.setText("TS");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem8.setText("PKE");
        jMenuItem8.setVisible(false);
        jMenuItem8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem8);

        menuCT.add(jMenu1);

        jMenu2.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\32x32\\editcopy.png")); // NOI18N
        jMenu2.setText("Novo a partir de existente");
        jMenu2.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N

        itemMenuCTExistente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        itemMenuCTExistente.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        itemMenuCTExistente.setText("TI");
        itemMenuCTExistente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuCTExistenteActionPerformed(evt);
            }
        });
        jMenu2.add(itemMenuCTExistente);

        jMenuItem3.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        jMenuItem3.setText("TS");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem10.setText("PKE");
        jMenuItem10.setVisible(false);
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem10);

        jMenuItem11.setText("TS -> PKE");
        jMenuItem11.setVisible(false);
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem11);

        menuCT.add(jMenu2);

        jMenu3.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\32x32\\blue_edit.png")); // NOI18N
        jMenu3.setText("Pesquisar/Editar");
        jMenu3.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N

        itemMenuPesquisaCT.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        itemMenuPesquisaCT.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        itemMenuPesquisaCT.setText("TI");
        itemMenuPesquisaCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuPesquisaCTActionPerformed(evt);
            }
        });
        jMenu3.add(itemMenuPesquisaCT);

        jMenuItem4.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        jMenuItem4.setText("TS");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem9.setText("PKE");
        jMenuItem9.setVisible(false);
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        menuCT.add(jMenu3);

        itemMenuConsultaCTAlm.setText("Consultar CT no ALM (beta)");
        itemMenuConsultaCTAlm.setVisible(false);
        itemMenuConsultaCTAlm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuConsultaCTAlmActionPerformed(evt);
            }
        });
        menuCT.add(itemMenuConsultaCTAlm);

        jMenuBar1.add(menuCT);

        menuImportarExportar.setBackground(java.awt.Color.white);
        menuImportarExportar.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\32x32\\folder_open.png")); // NOI18N
        menuImportarExportar.setText("Importar/Exportar");
        menuImportarExportar.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N

        itemMenuImportaStepPadrao.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        itemMenuImportaStepPadrao.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        itemMenuImportaStepPadrao.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\32x32\\folder_font.png")); // NOI18N
        itemMenuImportaStepPadrao.setText("Importar Steps Padrão");
        itemMenuImportaStepPadrao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuImportaStepPadraoActionPerformed(evt);
            }
        });
        menuImportarExportar.add(itemMenuImportaStepPadrao);

        itemMenuImportaCT.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        itemMenuImportaCT.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        itemMenuImportaCT.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\32x32\\folder_download.png")); // NOI18N
        itemMenuImportaCT.setText("Importar CTs / Configurações");
        itemMenuImportaCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuImportaCTActionPerformed(evt);
            }
        });
        menuImportarExportar.add(itemMenuImportaCT);

        itemMenuExportarPlanilhaTI.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        itemMenuExportarPlanilhaTI.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        itemMenuExportarPlanilhaTI.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\32x32\\excel.jpg")); // NOI18N
        itemMenuExportarPlanilhaTI.setText("Exportar para planilha de carga TI");
        itemMenuExportarPlanilhaTI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuExportarPlanilhaTIActionPerformed(evt);
            }
        });
        menuImportarExportar.add(itemMenuExportarPlanilhaTI);

        jMenuItem5.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        jMenuItem5.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\32x32\\spreadsheet.png")); // NOI18N
        jMenuItem5.setText("Instânciar CTs e exportar planilha TS");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        menuImportarExportar.add(jMenuItem5);

        jMenuItem12.setText("Instânciar CTs e exportar planilha PKE");
        jMenuItem12.setVisible(false);
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        menuImportarExportar.add(jMenuItem12);

        jMenuBar1.add(menuImportarExportar);

        jMenu4.setBackground(java.awt.Color.white);
        jMenu4.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\32x32\\view_tree.png")); // NOI18N
        jMenu4.setText("Fluxos");
        jMenu4.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N

        jMenuItemFuncionalidade.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK));
        jMenuItemFuncionalidade.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        jMenuItemFuncionalidade.setText("Gerenciar Fluxos");
        jMenuItemFuncionalidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFuncionalidadeActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItemFuncionalidade);

        jMenuBar1.add(jMenu4);

        menuMatrizRastreabilidade.setBackground(java.awt.Color.white);
        menuMatrizRastreabilidade.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\32x32\\kcmsystem.png")); // NOI18N
        menuMatrizRastreabilidade.setText("Automação");
        menuMatrizRastreabilidade.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N

        itemMenuComponentes.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        itemMenuComponentes.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\32x32\\script.png")); // NOI18N
        itemMenuComponentes.setText("Componentes");
        itemMenuComponentes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuComponentesActionPerformed(evt);
            }
        });
        menuMatrizRastreabilidade.add(itemMenuComponentes);

        itemMenuScripts.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        itemMenuScripts.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\32x32\\script.png")); // NOI18N
        itemMenuScripts.setText("Scripts(Cenários)");
        itemMenuScripts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuScriptsActionPerformed(evt);
            }
        });
        menuMatrizRastreabilidade.add(itemMenuScripts);

        jMenuBar1.add(menuMatrizRastreabilidade);

        menuConfiguracoes.setBackground(java.awt.Color.white);
        menuConfiguracoes.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\32x32\\kcontrol.png")); // NOI18N
        menuConfiguracoes.setText("Configurações");
        menuConfiguracoes.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        menuConfiguracoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConfiguracoesActionPerformed(evt);
            }
        });

        itemMenuConfiguracoes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_MASK));
        itemMenuConfiguracoes.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        itemMenuConfiguracoes.setText("Opções");
        itemMenuConfiguracoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuConfiguracoesActionPerformed(evt);
            }
        });
        menuConfiguracoes.add(itemMenuConfiguracoes);

        jMenuBar1.add(menuConfiguracoes);

        jMenu5.setBackground(java.awt.Color.white);
        jMenu5.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\32x32\\Pie Chart.png")); // NOI18N
        jMenu5.setText("Relatórios");
        jMenu5.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        jMenu5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu5ActionPerformed(evt);
            }
        });

        menuItemCT.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        menuItemCT.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\32x32\\Line Chart.png")); // NOI18N
        menuItemCT.setText("Caso de Testes");
        menuItemCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCTActionPerformed(evt);
            }
        });
        jMenu5.add(menuItemCT);

        jMenuItem6.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        jMenuItem6.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\32x32\\Bar Chart.png")); // NOI18N
        jMenuItem6.setText("De x Para");
        jMenu5.add(jMenuItem6);

        jMenuItem7.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        jMenuItem7.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\32x32\\log.png")); // NOI18N
        jMenuItem7.setText("Log");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem7);

        jMenuBar1.add(jMenu5);

        jMenuTema.setBackground(java.awt.Color.white);
        jMenuTema.setIcon(new javax.swing.ImageIcon("C:\\FastPlan\\res\\Icones 2.0\\32x32\\Pie Chart.png")); // NOI18N
        jMenuTema.setText("Temas");
        jMenuTema.setFont(new java.awt.Font("Graphik", 0, 14)); // NOI18N
        jMenuTema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuTemaActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenuTema);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 643, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 361, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCTActionPerformed

    }//GEN-LAST:event_menuCTActionPerformed

    private void itemMenuNovoCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuNovoCTActionPerformed
        // TODO add your handling code here:
        final JFrame GUIPrincipal = this;
        new SwingWorker() {
            JDialog aguarde = new WaitScreenView(GUIPrincipal, true);

            @Override
            protected Object doInBackground() throws Exception {
                aguarde.setLocationRelativeTo(GUIPrincipal);
                aguarde.setVisible(true);
                aguarde.setModal(true);
                criaJanelaTelaCadCT();
                return null;
            }

            @Override
            protected void done() {
//                aguarde.setModal(false);
                aguarde.dispose();
            }

        }.execute();


    }//GEN-LAST:event_itemMenuNovoCTActionPerformed

    private void itemMenuCTExistenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuCTExistenteActionPerformed
        try {
            criaJanelaSelecionaCT();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itemMenuCTExistenteActionPerformed

    private void itemMenuImportaStepPadraoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuImportaStepPadraoActionPerformed
        try {
            criaJanelaTelaImportaStepPadrao();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itemMenuImportaStepPadraoActionPerformed

    private void itemMenuPesquisaCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuPesquisaCTActionPerformed
        try {
            // TODO add your handling code here:
//            criaJanelaMantemCT();
            criaJanelaFiltro2();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itemMenuPesquisaCTActionPerformed

    private void itemMenuImportaCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuImportaCTActionPerformed
        try {
            criaJanelaImportaPlanilha();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itemMenuImportaCTActionPerformed

    private void itemMenuExportarPlanilhaTIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuExportarPlanilhaTIActionPerformed
        try {
            criaJanelaTelaExportaPlanilha();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itemMenuExportarPlanilhaTIActionPerformed

    private void itemMenuConfiguracoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuConfiguracoesActionPerformed
        try {
            criaJanelaTelaConfiguracoes(ProjectSettings.FASE_TS);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itemMenuConfiguracoesActionPerformed

    private void itemMenuConsultaCTAlmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuConsultaCTAlmActionPerformed
        try {
            // TODO add your handling code here:
            criaJanelaConsultaCTALM();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itemMenuConsultaCTAlmActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        try {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            criaJanelaEditScreenTSView(ProjectSettings.FASE_TS);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        final JFrame GUIPrincipal = this;
        new SwingWorker() {
            JDialog aguarde = new WaitScreenView(GUIPrincipal, true);

            @Override
            protected Object doInBackground() throws Exception {
                aguarde.setLocationRelativeTo(GUIPrincipal);
                aguarde.setVisible(true);
                aguarde.setModal(true);
                criaJanelaRegisterScreenTSView(ProjectSettings.FASE_TS);
                return null;
            }

            @Override
            protected void done() {
//                aguarde.setModal(false);
                aguarde.dispose();
            }

        }.execute();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void menuConfiguracoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConfiguracoesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuConfiguracoesActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        try {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            criaJanelaSelecionaCtTs(ProjectSettings.FASE_TS, ProjectSettings.FASE_TS);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        try {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            criaJanelaExportarTs(ProjectSettings.FASE_TS);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuBar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuBar1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuBar1MouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        JInternalFrame[] j = desktop.getAllFrames();

        if (j.length > 0) {
            JOptionPane.showMessageDialog(null, "Favor fechar todas as janelas internas. ", "Alerta", JOptionPane.WARNING_MESSAGE);
            repaint();

        } else {
            try {
                new TestCaseTSRN(ProjectSettings.FASE_TS).deleteDir();
            } catch (IOException ex) {
                System.out.println("com.accenture.view.MainScreenView.formWindowClosing() - " + ex.toString());
                dispose();
                System.exit(0); //calling the method is a must
            } catch (SVNException ex) {
                System.out.println("com.accenture.view.MainScreenView.formWindowClosing() - " + ex.toString());
                dispose();
                System.exit(0); //calling the method is a must
            }
            dispose();
            System.exit(0); //calling the method is a must
        }
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItemFuncionalidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemFuncionalidadeActionPerformed
        try {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            criaJanelaFuncionalidade(ProjectSettings.FASE_TS);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItemFuncionalidadeActionPerformed

    private void menuItemCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCTActionPerformed
        try {
            criaJanelaReport("");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_menuItemCTActionPerformed

    private void itemMenuComponentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuComponentesActionPerformed
        try {
            criaJanelaComponentes();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itemMenuComponentesActionPerformed

    private void itemMenuScriptsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuScriptsActionPerformed
        try {
            criaJanelaScripts();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_itemMenuScriptsActionPerformed

    /**
     * Relatorio de De x Para
     *
     * @param evt
     */
    private void jMenu5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu5ActionPerformed

        // TODO add your handling code here:
//      try {
//            criaJanelaReportDePara();
//        } catch (IOException ex) {
//            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//        } catch (ClassNotFoundException ex) {
//            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//        } catch (SVNException ex) {
//            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//        }

    }//GEN-LAST:event_jMenu5ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        try {
            File log = new File(ProjectSettings.PATH_LOG_HTML);
            if (log.exists()) {
                java.awt.Desktop.getDesktop().open(log);
            } else {
                JOptionPane.showMessageDialog(null, "Log ainda não foi gerado.", "Log", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            Logger.getLogger(MainScreenView.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        final JFrame GUIPrincipal = this;
        new SwingWorker() {
            JDialog aguarde = new WaitScreenView(GUIPrincipal, true);

            @Override
            protected Object doInBackground() throws Exception {
                aguarde.setLocationRelativeTo(GUIPrincipal);
                aguarde.setVisible(true);
                aguarde.setModal(true);
                criaJanelaRegisterScreenTSView(ProjectSettings.FASE_PKE);
                return null;
            }

            @Override
            protected void done() {
//                aguarde.setModal(false);
                aguarde.dispose();
            }

        }.execute();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        try {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            criaJanelaEditScreenTSView(ProjectSettings.FASE_PKE);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        try {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            criaJanelaSelecionaCtTs(ProjectSettings.FASE_PKE, ProjectSettings.FASE_PKE);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        try {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            criaJanelaSelecionaCtTs(ProjectSettings.FASE_TS, ProjectSettings.FASE_PKE);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        try {
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            criaJanelaExportarTs(ProjectSettings.FASE_PKE);
            getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SVNException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o seguinte erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuTemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuTemaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuTemaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainScreenView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainScreenView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainScreenView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainScreenView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainScreenView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem itemMenuCTExistente;
    private javax.swing.JMenuItem itemMenuComponentes;
    private javax.swing.JMenuItem itemMenuConfiguracoes;
    private javax.swing.JMenuItem itemMenuConsultaCTAlm;
    private javax.swing.JMenuItem itemMenuExportarPlanilhaTI;
    private javax.swing.JMenuItem itemMenuImportaCT;
    private javax.swing.JMenuItem itemMenuImportaStepPadrao;
    private javax.swing.JMenuItem itemMenuNovoCT;
    private javax.swing.JMenuItem itemMenuPesquisaCT;
    private javax.swing.JMenuItem itemMenuScripts;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem jMenuItemFuncionalidade;
    private javax.swing.JMenu jMenuTema;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenu menuCT;
    private javax.swing.JMenu menuConfiguracoes;
    private javax.swing.JMenu menuImportarExportar;
    private javax.swing.JMenuItem menuItemCT;
    private javax.swing.JMenu menuMatrizRastreabilidade;
    // End of variables declaration//GEN-END:variables

    RegisterScreenTIView guiJanelaCadCT;

    ChooseTestCaseScreenView guiJanelaSelecionaCT;
    EditStepDefaultScreenView guiJanelaManterStepPadrao;
    ImportStepDefaultScreenView guiImportaStepPadrao;
    EditTestCaseScreenTIView guiMantemCt;
    ImportSheetScreenView guiImportaPlanilha;
    ExportSheetScreenTIView guiExportaPlanilha;
    SettingsScreenView guiConfiguracoes;
    FilterTestCaseScreen2View guiFiltroCT2;
    SearchAlmScreenView guiConsultaALM;
    RegisterScreenTSView guiCadTS;
    EditScreenTSView guiEdit;
    ChooseTestCaseTsScreenView guiChooseTestCaseTsScreenView;
    InstanceScreenTSView guiInstaceTs;
    ManageflowsScreenView guiManageflowsScreenView;
    FilterReportScreenView guiFilterReportScreenView;
//    FilterReporDeParatScreenView guiFilterReportDeParaScreenView;;
    ManageComponentsScreenView guiComponentsScreenView;
    ManageScriptsScreenView guiManageScriptsScreenView;

    public void criaJanelaReport(String fase) throws IOException, ClassNotFoundException, SQLException, SVNException {
        guiFilterReportScreenView = new FilterReportScreenView(fase);
        desktop.add(guiFilterReportScreenView);
        guiFilterReportScreenView.centralizaJanela();
        guiFilterReportScreenView.setVisible(true);
    }

//      public void criaJanelaReportDePara() throws IOException, ClassNotFoundException, SQLException, SVNException {
//        guiFilterReportDeParaScreenView = new FilterReporDeParatScreenView();
//        desktop.add(guiFilterReportDeParaScreenView);
//        //guiFilterReportDeParaScreenView.centralizaJanela();
//        guiFilterReportDeParaScreenView.setVisible(true);
//    }
    public void criaJanelaExportarTs(String fase) throws IOException, ClassNotFoundException, SQLException, SVNException {
        guiInstaceTs = InstanceScreenTSView.getInstance(fase);
        desktop.add(guiInstaceTs);
        guiInstaceTs.centralizaJanela();
        guiInstaceTs.setVisible(true);
    }

    public void criaJanelaEditScreenTSView(String fase) throws IOException, ClassNotFoundException, SQLException, SVNException {

        guiEdit = new EditScreenTSView(fase);
        desktop.add(guiEdit);
        guiEdit.centralizaJanela();
        guiEdit.setVisible(true);
    }

    public void criaJanelaRegisterScreenTSView(String fase) throws IOException, ClassNotFoundException, SQLException, SVNException {

        guiCadTS = new RegisterScreenTSView(fase);
        desktop.add(guiCadTS);
        guiCadTS.centralizaJanela();
        guiCadTS.setVisible(true);
    }

    public void criaJanelaTelaConfiguracoes(String fase) throws IOException, ClassNotFoundException, SQLException {

        guiConfiguracoes = new SettingsScreenView(fase);
        guiConfiguracoes.setDesktop(this);
        desktop.add(guiConfiguracoes);
        guiConfiguracoes.centralizaJanela();
        guiConfiguracoes.setVisible(true);

    }

    public void criaJanelaTelaExportaPlanilha() throws IOException, ClassNotFoundException, SQLException {

        guiExportaPlanilha = new ExportSheetScreenTIView();
        desktop.add(guiExportaPlanilha);
        guiExportaPlanilha.centralizaJanela();
        guiExportaPlanilha.setVisible(true);
    }

    public void criaJanelaTelaImportaStepPadrao() throws IOException, ClassNotFoundException, SQLException {

        guiImportaStepPadrao = new ImportStepDefaultScreenView();
        desktop.add(guiImportaStepPadrao);
        guiImportaStepPadrao.centralizaJanela();

        guiImportaStepPadrao.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        guiImportaStepPadrao.setVisible(true);
        System.out.println("-**-criou-**-");

    }

    public void criaJanelaTelaCadCT() throws IOException, ClassNotFoundException, SQLException {

        guiJanelaCadCT = new RegisterScreenTIView();
        desktop.add(guiJanelaCadCT);
        guiJanelaCadCT.centralizaJanela();

        guiJanelaCadCT.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        guiJanelaCadCT.setVisible(true);
        System.out.println("-**-criou-**-");

    }

//    private void criaJanelaTelaImportacao() {
//
//        if (guiJanelaImportacao == null) { 
//            guiJanelaImportacao = new GUIImportacao();
//            desktop.add(guiJanelaImportacao);
//            guiJanelaImportacao.setVisible(true);
//
//            guiJanelaImportacao.setDefaultCloseOperation(HIDE_ON_CLOSE);
//            guiJanelaImportacao.setVisible(true);
//
//        }
//    }
    private void criaJanelaSelecionaCT() throws SQLException, ClassNotFoundException {

        guiJanelaSelecionaCT = new ChooseTestCaseScreenView();
        desktop.add(guiJanelaSelecionaCT);
        guiJanelaSelecionaCT.centralizaJanela();
        guiJanelaSelecionaCT.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        guiJanelaSelecionaCT.setVisible(true);

    }

    private void criaJanelaSelecionaCtTs(String faseDe, String fasePara) throws SQLException, ClassNotFoundException {

        guiChooseTestCaseTsScreenView = new ChooseTestCaseTsScreenView(faseDe, fasePara);
        desktop.add(guiChooseTestCaseTsScreenView);
        guiChooseTestCaseTsScreenView.centralizaJanela();
        guiChooseTestCaseTsScreenView.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        guiChooseTestCaseTsScreenView.setVisible(true);

    }

    private void criaJanelaStepPadrao() throws SQLException, ClassNotFoundException, IOException {

        guiJanelaManterStepPadrao = new EditStepDefaultScreenView();
        desktop.add(guiJanelaManterStepPadrao);
        guiJanelaManterStepPadrao.centralizaJanela();
        guiJanelaManterStepPadrao.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        guiJanelaManterStepPadrao.setVisible(true);

    }

    private void criaJanelaMantemCT() throws SQLException, ClassNotFoundException, IOException {

        guiMantemCt = new EditTestCaseScreenTIView();
        desktop.add(guiMantemCt);
        guiMantemCt.centralizaJanela();
        guiMantemCt.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        guiMantemCt.setVisible(true);

    }

    private void criaJanelaFiltro2() throws SQLException, ClassNotFoundException, IOException {

        guiFiltroCT2 = new FilterTestCaseScreen2View();
        desktop.add(guiFiltroCT2);
        guiFiltroCT2.centralizaJanela();

        guiFiltroCT2.setVisible(true);

    }

    private void criaJanelaImportaPlanilha() throws SQLException, ClassNotFoundException, IOException {
        guiImportaPlanilha = new ImportSheetScreenView();
        desktop.add(guiImportaPlanilha);
        guiImportaPlanilha.centralizaJanela();
        guiImportaPlanilha.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        guiImportaPlanilha.setVisible(true);

    }

    private void criaJanelaConsultaCTALM() throws SQLException, ClassNotFoundException, IOException {
        guiConsultaALM = new SearchAlmScreenView();
        desktop.add(guiConsultaALM);
        guiConsultaALM.centralizaJanela();

        guiConsultaALM.setVisible(true);

    }

    private void criaJanelaFuncionalidade(String fase) throws SQLException, ClassNotFoundException, IOException, SVNException {
        guiManageflowsScreenView = new ManageflowsScreenView(fase);
        desktop.add(guiManageflowsScreenView);
        guiManageflowsScreenView.centralizaJanela();

        guiManageflowsScreenView.setVisible(true);

    }

    private void criaJanelaComponentes() throws SQLException, ClassNotFoundException, IOException, SVNException {
        guiComponentsScreenView = new ManageComponentsScreenView("");
        desktop.add(guiComponentsScreenView);
        guiComponentsScreenView.centralizaJanela();
        guiComponentsScreenView.setVisible(true);

    }

    private void criaJanelaScripts() throws SQLException, ClassNotFoundException, IOException, SVNException {
        guiManageScriptsScreenView = new ManageScriptsScreenView();
        desktop.add(guiManageScriptsScreenView);
        guiManageScriptsScreenView.centralizaJanela();
        guiManageScriptsScreenView.setVisible(true);

    }

    public JDesktopPane getDesktop() {
        return desktop;
    }

    public void setDesktop(JDesktopPane desktop) {
        this.desktop = desktop;
    }

    public MainScreenView getPrincipal() {
        return this;
    }

    public void removeTela(JInternalFrame intFrame) {
        intFrame.setVisible(false);
        desktop.remove(intFrame);
    }

    /*
     @Override
     public synchronized void addWindowListener(WindowListener wl) {
     // super.addWindowListener(wl); //To change body of generated methods, choose Tools | Templates.
     JInternalFrame [] j = desktop.getAllFrames();
           
     if(j.length > 0 ){
     JOptionPane.showMessageDialog(null, "Favor fechar todas as janelas internas. ", "Alerta", JOptionPane.WARNING_MESSAGE);
     }else{
     System.out.println(".windowClosing()");
     dispose();
     System.exit(0); //calling the method is a must
     }
     }
     */
}
