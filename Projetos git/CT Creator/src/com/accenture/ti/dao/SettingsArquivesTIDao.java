/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ti.dao;

import com.accenture.bean.SVNPropertiesVOBean;
import com.accenture.ti.bean.SettingsArchivesTIBean;
import java.io.IOException;

/**
 *
 * @author brucilin.de.gouveia
 */
public class SettingsArquivesTIDao {

    private SettingsArchivesTIBean settings;
    private SVNPropertiesVOBean settingsLocal;

    public SettingsArquivesTIDao() throws IOException {
        settings = new SettingsArchivesTIBean();
        settingsLocal = new SVNPropertiesVOBean();

    }

    public void updateDataInLocal() throws IOException {

        settingsLocal.setSystems(settings.getSystem());
        settingsLocal.setUrl(settings.getUrlSvn());
        settingsLocal.setFaseCR(settings.getFaseCr());
        settingsLocal.setDir(settings.getTestCaseDir());
        settingsLocal.setComplexidade(settings.getComplexidade());
        
    }

    public SettingsArchivesTIBean getSettings() {
        return settings;
    }

    public SVNPropertiesVOBean getSettingsLocal() {
        return settingsLocal;
    }

    public void loadSettingsRemoteInLocal() throws IOException {
        settings.loadFileProperties();

    }

}
