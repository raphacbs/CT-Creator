/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.util;

import com.accenture.view.EditScreenTSView;
import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.table.TableCellEditor;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 *
 * @author Raphael
 */
public class TextAreaCellEditor implements TableCellEditor {

    protected EventListenerList listenerList = new EventListenerList();
    private final JScrollPane scrollPane;
    private JTextPane textArea = new JTextPane();
    private UndoManager undoManager;
    private Pattern p;
    private Matcher m;
    private String regex;
    private EditScreenTSView internalFrame;
    //240, 255, 255)
    Color corBrackground = new Color(240, 255, 255);
    private final StyleContext cont = StyleContext.getDefaultStyleContext();
    private final AttributeSet atributeColor = cont.addAttribute(cont.getEmptySet(), StyleConstants.Background, Color.ORANGE);//200, 255, 255
    private final AttributeSet attributeBold = cont.addAttribute(cont.getEmptySet(), StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
    private final AttributeSet attributeNoBold = cont.addAttribute(cont.getEmptySet(), StyleConstants.CharacterConstants.Bold, Boolean.FALSE);
    private final AttributeSet attributeNoParameterColor = cont.addAttribute(cont.getEmptySet(), StyleConstants.Background, corBrackground);
    private final AttributeSet attributeText = cont.addAttribute(cont.getEmptySet(), StyleConstants.ALIGN_JUSTIFIED, true);

    private int findLastNonWordChar(String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

    private int findFirstNonWordChar(String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }

    public TextAreaCellEditor(EditScreenTSView internalFrame) {
        String parametros = "(\\W)*(parametro)";
        this.internalFrame = internalFrame;
          
        
        DefaultStyledDocument docParametro = new DefaultStyledDocument() {
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);
                String text = getText(0, getLength());
                System.out.println("Texto: " + text);
                System.out.println("Parametros: " + getParametros(text));

                int ini = 0;
                int fim = 0;

                String temp = text.replace("\n", " ");

                setCharacterAttributes(ini, getLength(), attributeNoParameterColor, false);
                setCharacterAttributes(ini, getLength(), attributeNoBold, false);

                String[] words = temp.split(" ");
                for (int i = 0; i < words.length; i++) {
                    ini = text.indexOf(words[i]);
                    fim = words[i].length();

                    if (words[i].matches("<{3}\\w{1,20}>{3}")) {
                        setCharacterAttributes(ini, fim, atributeColor, false);
                        setCharacterAttributes(ini, fim, attributeBold, false);
                        setCharacterAttributes(ini, fim, attributeText, false);
                    } else if (words[i].matches("<{3}\\w{1,20}>{3}.")) {
                        setCharacterAttributes(ini, fim - 1, atributeColor, false);
                        setCharacterAttributes(ini, fim - 1, attributeBold, false);
                        setCharacterAttributes(ini, fim - 1, attributeText, false);
                    }

                    
                    

                }
                
                

            }

            public void remove(int offs, int len) throws BadLocationException {
                super.remove(offs, len);
                String text = getText(0, getLength());

                System.out.println("Texto: " + text);
                System.out.println("Parametros: " + getParametros(text));

                int ini = 0;
                int fim = 0;

                String temp = text.replace("\n", " ");

                setCharacterAttributes(ini, getLength(), attributeNoParameterColor, false);
                setCharacterAttributes(ini, getLength(), attributeNoBold, false);

                String[] words = temp.split(" ");
                for (int i = 0; i < words.length; i++) {
                    ini = text.indexOf(words[i]);
                    fim = words[i].length();

                    if (words[i].matches("<{3}\\w{1,20}>{3}")) {
                        setCharacterAttributes(ini, fim, atributeColor, false);
                        setCharacterAttributes(ini, fim, attributeBold, false);
                        setCharacterAttributes(ini, fim, attributeText, false);
                    }

                }
            }
        };

        textArea = new JTextPane(docParametro);

       
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createBevelBorder(2, Color.lightGray, Color.yellow));
//        textArea.setLineWrap(true);
//        textArea.setWrapStyleWord(true);
        textArea.setDragEnabled(true);
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        textArea.setBackground(corBrackground);
        textArea.setSelectionColor(Color.blue);
        textArea.setSelectedTextColor(Color.white);
        
        SpellChecker.setUserDictionaryProvider(new FileUserDictionary());
        SpellChecker.registerDictionaries(null, null);
        SpellChecker.register(textArea);

        //Inicio CTRL Z e CTRL Y
        undoManager = new UndoManager();
        Document doc = textArea.getDocument();
        doc.addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {

                System.out.println("Add edit");
                undoManager.addEdit(e.getEdit());

            }
        });

        InputMap im = textArea.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap am = textArea.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");

        am.put("Undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (undoManager.canUndo()) {
                        undoManager.undo();
                    }
                } catch (CannotUndoException exp) {
                    exp.printStackTrace();
                }
            }
        });
        am.put("Redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (undoManager.canRedo()) {
                        undoManager.redo();
                    }
                } catch (CannotUndoException exp) {
                    exp.printStackTrace();
                }
            }
        });

        //Fim CTRL Z e CTRL Y
        //Exibe quantidade caracteres no texto selecionado
        textArea.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                try {

                    Point p = e.getPoint();
                    int colunaInicio = textArea.getSelectionStart();
                    int colunaFim = textArea.getSelectionEnd();
                    int qtdCaracteres = textArea.getSelectedText().length();
                    if (p.x >= colunaInicio && p.y <= colunaFim && colunaInicio != colunaFim && qtdCaracteres != 0) {

                        textArea.setToolTipText(String.valueOf("Total de caracteres: " + qtdCaracteres));
                    }
                } catch (NullPointerException ex) {
                    System.err.println("Texto não selecionado!");
                }

            }//end MouseMoved

        });

        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                //((AbstractDocument) textArea.getDocument()).setDocumentFilter(new Filter());
            }

            @Override
            public void keyPressed(KeyEvent ke) {

            }

            @Override
            public void keyReleased(KeyEvent ke) {

            }
        });

    }

    public TextAreaCellEditor() {
        
        DefaultStyledDocument docParametro = new DefaultStyledDocument() {
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                 super.insertString(offset, str, a);
                String text = getText(0, getLength());
                System.out.println("Texto: " + text);
                System.out.println("Parametros: " + getParametros(text));

                int ini = 0;
                int fim = 0;

                String temp = text.replace("\n", " ");

                setCharacterAttributes(ini, getLength(), attributeNoParameterColor, false);
                setCharacterAttributes(ini, getLength(), attributeNoBold, false);

                String[] words = temp.split(" ");
                for (int i = 0; i < words.length; i++) {
                    ini = text.indexOf(words[i]);
                    fim = words[i].length();

                    if (words[i].matches("<{3}\\w{1,20}>{3}")) {
                     setCharacterAttributes(ini, fim, atributeColor, false);
                     setCharacterAttributes(ini, fim, attributeBold, false);
                     setCharacterAttributes(ini, fim, attributeText, false);
                    }

                }

                
            }

            public void remove(int offs, int len) throws BadLocationException {
                super.remove(offs, len);

                 String text = getText(0, getLength());

                System.out.println("Texto: " + text);
                System.out.println("Parametros: " + getParametros(text));

                int ini = 0;
                int fim = 0;

                String temp = text.replace("\n", " ");

                setCharacterAttributes(ini, getLength(), attributeNoParameterColor, false);
                setCharacterAttributes(ini, getLength(), attributeNoBold, false);

                String[] words = temp.split(" ");
                for (int i = 0; i < words.length; i++) {
                    ini = text.indexOf(words[i]);
                    fim = words[i].length();

                    if (words[i].matches("<{3}\\w{1,20}>{3}")) {
                        setCharacterAttributes(ini, fim, atributeColor, false);
                        setCharacterAttributes(ini, fim, attributeBold, false);
                        setCharacterAttributes(ini, fim, attributeText, false);
                    }

                }
            }
        };

        textArea = new JTextPane(docParametro);

        
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createBevelBorder(2, Color.lightGray, Color.yellow));
//        textArea.setLineWrap(true);
//        textArea.setWrapStyleWord(true);
        textArea.setDragEnabled(true);
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        textArea.setBackground(corBrackground);
        textArea.setSelectionColor(Color.blue);
        textArea.setSelectedTextColor(Color.white);
        SpellChecker.setUserDictionaryProvider(new FileUserDictionary());
        SpellChecker.registerDictionaries(null, null);
        SpellChecker.register(textArea);

        //Inicio CTRL Z e CTRL Y
        undoManager = new UndoManager();
        Document doc = textArea.getDocument();
        doc.addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {

                System.out.println("Add edit");
                undoManager.addEdit(e.getEdit());

            }
        });

        InputMap im = textArea.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap am = textArea.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");

        am.put("Undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (undoManager.canUndo()) {
                        undoManager.undo();
                    }
                } catch (CannotUndoException exp) {
                    exp.printStackTrace();
                }
            }
        });
        am.put("Redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (undoManager.canRedo()) {
                        undoManager.redo();
                    }
                } catch (CannotUndoException exp) {
                    exp.printStackTrace();
                }
            }
        });

        //Fim CTRL Z e CTRL Y
        //Exibe quantidade caracteres no texto selecionado
        textArea.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                try {

                    Point p = e.getPoint();
                    int colunaInicio = textArea.getSelectionStart();
                    int colunaFim = textArea.getSelectionEnd();
                    int qtdCaracteres = textArea.getSelectedText().length();
                    if (p.x >= colunaInicio && p.y <= colunaFim && colunaInicio != colunaFim && qtdCaracteres != 0) {

                        textArea.setToolTipText(String.valueOf("Total de caracteres: " + qtdCaracteres));
                    }
                } catch (NullPointerException ex) {
                    System.err.println("Texto não selecionado!");
                }

            }//end MouseMoved

        });

        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                //((AbstractDocument) textArea.getDocument()).setDocumentFilter(new Filter());
            }

            @Override
            public void keyPressed(KeyEvent ke) {

            }

            @Override
            public void keyReleased(KeyEvent ke) {

            }
        });

    }

    @Override
    public Object getCellEditorValue() {

        return textArea.getText();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        textArea.setFont(table.getFont());
        textArea.setText((value != null) ? value.toString() : "");
        return scrollPane;
    }

    @Override
    public boolean isCellEditable(final EventObject e) {
        if (e instanceof MouseEvent) {
            return ((MouseEvent) e).getClickCount() >= 2;
        }
        System.out.println("1. isCellEditable");
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (e instanceof KeyEvent) {
                    KeyEvent ke = (KeyEvent) e;
                    char kc = ke.getKeyChar();
                    if (Character.isUnicodeIdentifierStart(kc)) {
                        textArea.setText(textArea.getText() + kc);
                        System.out.println("3. invokeLater: isCellEditable");
                    }
                }
            }
        });
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject e) {
        if(internalFrame!=null)
        internalFrame.setEditing(true);
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        fireEditingStopped();
        return true;
    }

    @Override
    public void cancelCellEditing() {
        fireEditingCanceled();
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {
        listenerList.add(CellEditorListener.class, l);
    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {
        listenerList.remove(CellEditorListener.class, l);

    }

    protected void fireEditingStopped() {

        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i++) {
            if (listeners[i] instanceof CellEditorListener) {
                ((CellEditorListener) listeners[i]).editingStopped(new ChangeEvent(this));

            }
        }
        System.out.println("parou de editar");
        if(internalFrame!=null)
        internalFrame.setEditing(true);

    }

    protected void fireEditingCanceled() {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i++) {
            if (listeners[i] instanceof CellEditorListener) {
                ((CellEditorListener) listeners[i]).editingCanceled(new ChangeEvent(this));
            }
        }
        System.out.println("parou de editar");
        if(internalFrame!=null)
        internalFrame.setEditing(true);
    }

    protected void tiraFoco() {
        textArea.setFocusable(false);
    }

    public String getParametros(String texto) {

        List<String> ListParamentros = new ArrayList<String>();
        String parametros;
        StringBuilder b = new StringBuilder();
        boolean proximo = false;
        regex = "(\\<{3}\\w{1,20}\\>{3})+";
        p = Pattern.compile(regex);
        m = p.matcher(texto);

        while (m.find()) {

            b.append(m.group(1));

            b.append("|");

        }
        System.out.println(b.toString());
        return b.toString();

    }

    public void styleFont(boolean bold, boolean under, boolean italic, String fontFamaly, Color color, int size, int tipo) {
        String textSelected = null;
        int count = 0;
        int start = 0;
        int end = 0;
        try {
            end = textArea.getSelectionEnd();
            start = textArea.getSelectionStart();
            count = textArea.getSelectedText().length();
            textSelected = textArea.getSelectedText();
        } catch (NullPointerException e) {

        }

        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setBold(attributes, bold);
        StyleConstants.setUnderline(attributes, under);
        StyleConstants.setItalic(attributes, italic);
        StyleConstants.setFirstLineIndent(attributes, 400);
        if (tipo == 1) {
            StyleConstants.setFontFamily(attributes, fontFamaly);
            StyleConstants.setForeground(attributes, color);
            StyleConstants.setFontSize(attributes, size);
        }
        if (textSelected != null) {
            try {
                textArea.getStyledDocument().remove(start, count);

            } catch (BadLocationException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                textArea.getStyledDocument().insertString(start, textSelected, attributes);
                textArea.select(start, end);
                textArea.setSelectedTextColor(color);
            } catch (BadLocationException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            try {
                textArea.getStyledDocument().insertString(textArea.getStyledDocument().getLength(), " ", attributes);
            } catch (BadLocationException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
