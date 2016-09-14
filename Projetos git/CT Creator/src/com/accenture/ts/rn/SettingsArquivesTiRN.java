/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.rn;

import com.accenture.ti.dao.SettingsArquivesTIDao;
import com.accenture.ts.dao.SvnConnectionDao;
import java.io.IOException;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author brucilin.de.gouveia
 */
public class SettingsArquivesTiRN {

    private SettingsArquivesTIDao settings;
    private SvnConnectionDao connection;
            
    public SettingsArquivesTiRN() throws IOException, SVNException {
        settings= new SettingsArquivesTIDao();
        connection = new SvnConnectionDao();
    }

    public SettingsArquivesTIDao getSettings() {
        return settings;
    }

    public void setSettings(SettingsArquivesTIDao settings) {
        this.settings = settings;
    }

    public SvnConnectionDao getConnection() {
        return connection;
    }

    public void setConnection(SvnConnectionDao connection) {
        this.connection = connection;
    }
    
    public void updateSettingsRN () throws SVNException, IOException{
        connection.exportFileOrFolder("remote_svn.properties","C:\\FastPlan\\temp\\", "conf");
        settings.loadSettingsRemoteInLocal();
        settings.updateDataInLocal();
        settings.getSettings().loadFields();
        
    }
}
