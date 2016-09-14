/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

public class CheckTreeManager extends MouseAdapter implements TreeSelectionListener {

    private CheckTreeSelectionModel selectionModel;
    private JTree tree = new JTree();
    int hotspot = new JCheckBox().getPreferredSize().width;
    private CheckTreeCellRenderer checkTreeCellRenderer;

    public CheckTreeManager(JTree tree) {
        this.tree = tree;
        selectionModel = new CheckTreeSelectionModel(tree.getModel());

        checkTreeCellRenderer = new CheckTreeCellRenderer(tree.getCellRenderer(), selectionModel);

        tree.setCellRenderer(checkTreeCellRenderer);
        tree.addMouseListener(this);
        selectionModel.addTreeSelectionListener(this);
    }

    public void mouseClicked(MouseEvent me) {
        TreePath path = tree.getPathForLocation(me.getX(), me.getY());
        if (path == null) {
            return;
        }
        if (me.getX() > tree.getPathBounds(path).x + hotspot) {
            return;
        }

        boolean selected = selectionModel.isPathSelected(path, true);
        selectionModel.removeTreeSelectionListener(this);

        try {
            if (checkTreeCellRenderer.isEnabled()) {
                if (selected) {
                    selectionModel.removeSelectionPath(path);
                } else {
                    selectionModel.addSelectionPath(path);
                }
            }
                
                
            }finally{ 
            selectionModel.addTreeSelectionListener(this); 
            tree.treeDidChange(); 
        }
        
        
    }

    public CheckTreeSelectionModel getSelectionModel() {

        return selectionModel;
    }

    public void setAtiva(boolean b) {
        checkTreeCellRenderer.setEnabled(b);
    }

    public void valueChanged(TreeSelectionEvent e) {
        tree.treeDidChange();
    }
}
