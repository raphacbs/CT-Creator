/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.util;

import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

public class TextAreaCellRenderer extends JTextArea implements TableCellRenderer {

        public TextAreaCellRenderer() {

            setLineWrap(true);
            setWrapStyleWord(true);
            setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
            setSelectionColor(Color.black);
            setBackground(Color.white);
            setMargin(new java.awt.Insets(5, 5, 5, 5));
               SpellChecker.setUserDictionaryProvider(new FileUserDictionary());
        SpellChecker.registerDictionaries(null, null);
        SpellChecker.register(this);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            // set color & border here              
            this.setText(value.toString());

            setText((value == null) ? "" : value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(),
                    getPreferredSize().height);
           
            if (table.getRowHeight(row) < getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height );
            }

            return this;
        }
    }