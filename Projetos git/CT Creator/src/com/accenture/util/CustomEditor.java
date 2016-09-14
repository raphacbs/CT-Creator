/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.util;
import java.awt.Component;  
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.EventObject;  
import java.util.Objects;
import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
  
import javax.swing.JScrollPane;  
import javax.swing.JTable;  
import javax.swing.JTextArea;  
import javax.swing.KeyStroke;
import javax.swing.event.CellEditorListener;  
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.table.TableCellEditor;  
/**
 *
 * @author Raphael
 */
public class CustomEditor  extends AbstractCellEditor implements TableCellEditor {
    
    JTextArea textArea;  
    JScrollPane scrollPane;  
    protected EventListenerList listenerList = new EventListenerList();
    transient protected ChangeEvent changeEvent = null;
    private static final String KEY = "Stop-Cell-Editing";
    
    
    
    public CustomEditor()
    {
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
    }
  
    public Component getTableCellEditorComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 int row, int column)
    {
        textArea.setText((String)value);
        return scrollPane;
    }
  
    public void addCellEditorListener(CellEditorListener l) { }
    public void cancelCellEditing() { }
    public Object getCellEditorValue()
    {
        return textArea.getText();
    }
    public boolean isCellEditable(EventObject anEvent)
    {
        return true;
    }
    public void removeCellEditorListener(CellEditorListener l) { }
    public boolean shouldSelectCell(EventObject anEvent)
    {
        return true;
    }
    public boolean stopCellEditing()
    {
        return true;
    }
    
}
