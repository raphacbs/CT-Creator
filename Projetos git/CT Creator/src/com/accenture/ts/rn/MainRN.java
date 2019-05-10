/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.rn;

import com.accenture.bean.SVNPropertiesVOBean;
import com.accenture.bean.SvnConnectionBean;
import java.io.IOException;
import javax.swing.UIManager;

import java.awt.EventQueue;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;


/**
 *
 * @author automacao
 */
public class MainRN {

    private static final String THEME_NIMBUS = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
    private static final String THEME_MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
    private static final String THEME_WINDOWS = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    private static final String THEME_WINDOWS_CLASSIC = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
    public static final String THEME_NAME_NIMBUS = "NIMBUS";
    public static final String THEME_NAME_MOTIF = "MOTIF";
    public static final String THEME_NAME_WINDOWS = "WINDOWS";
    public static final String THEME_NAME_WINDOWS_CLASSIC = "WINDOWS_CLASSIC";

    public MainRN() {
    }

    public void setThemeNimbus() {
        setTheme(THEME_NIMBUS);
    }

    public void setThemeMotif() {
        setTheme(THEME_MOTIF);
    }

    public void setThemeWindows() {
        setTheme(THEME_WINDOWS);
    }

    public void setThemeWindowsClassic() {
        setTheme(THEME_WINDOWS_CLASSIC);
    }
    
 

    public String loadTheme() {
        try {
            String theme = SVNPropertiesVOBean.getInstance().getTheme();
            switch (theme) {
                case THEME_MOTIF:
                    setTheme(THEME_MOTIF);
                    return THEME_NAME_MOTIF ;
                case THEME_NIMBUS:
                    setTheme(THEME_NIMBUS);
                    return THEME_NAME_NIMBUS;
                case THEME_WINDOWS:
                    setTheme(THEME_WINDOWS);
                    return THEME_NAME_WINDOWS;
                case THEME_WINDOWS_CLASSIC:
                    setTheme(THEME_WINDOWS_CLASSIC);
                    return THEME_NAME_WINDOWS_CLASSIC;
                    default:
                        return THEME_NAME_WINDOWS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return THEME_NAME_WINDOWS;
    }

    private void setTheme(String theme) {
        UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.setLookAndFeel(theme);
            SVNPropertiesVOBean.getInstance().setTheme(theme);
        } catch (Exception e) {
            System.out.println("Look and Feel not set");
        }
    }
}
