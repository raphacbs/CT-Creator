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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.AttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class TextAreaCellRenderer extends JTextArea implements TableCellRenderer {
     private final StyleContext cont = StyleContext.getDefaultStyleContext();
    private final AttributeSet attributeText = cont.addAttribute(cont.getEmptySet(), StyleConstants.ALIGN_JUSTIFIED, true);
    private final AttributeSet atributeColor = cont.addAttribute(cont.getEmptySet(), StyleConstants.Background, new Color(51, 51, 51));
    private final AttributeSet attributeBold = cont.addAttribute(cont.getEmptySet(), StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
    private final AttributeSet atributeColorForeground = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255, 255, 255));
   

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

//         String[] words = value.toString().split(" ");
//         
//          for (int i = 0; i < words.length; i++) {
//                    int ini = value.toString().indexOf(words[i]);
//                    int fim = words[i].length();
//                    
//                    Pattern p = Pattern.compile("<{3}\\w{1,20}>{3}");
//                    Matcher m = p.matcher(words[i]); 
// 
//                    //Ligando o motor 
//                    while (m.find()) {
//                       
//                        setCharacterAttributes(ini, fim, atributeColor, false);
//                        setCharacterAttributes(ini, fim, attributeBold, false);
//                        setCharacterAttributes(ini, fim, attributeText, false);
//                        setCharacterAttributes(ini, fim, atributeColorForeground, false);
//                    }
//          }
        // set color & border here              
        this.setText(value.toString());
        
        
        setText((value == null) ? "" : value.toString());
        setSize(table.getColumnModel().getColumn(column).getWidth(),
                getPreferredSize().height);

        if (table.getRowHeight(row) < getPreferredSize().height) {
            table.setRowHeight(row, getPreferredSize().height);
        }

        return this;
    }
}
