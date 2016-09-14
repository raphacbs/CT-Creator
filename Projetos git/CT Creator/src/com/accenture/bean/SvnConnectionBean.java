/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;

/**
 *
 * @author brucilin.de.gouveia
 */
public class SvnConnectionBean {

    private Properties mainProperties;
    private static SVNRepository repository;
    private static ISVNAuthenticationManager authManager;
    private SVNURL url;
    private SVNPropertiesVOBean svnProperties;

    public SvnConnectionBean() throws SVNException, FileNotFoundException, IOException {

        this.svnProperties = new SVNPropertiesVOBean();
        DAVRepositoryFactory.setup();
        this.authManager = new BasicAuthenticationManager(this.svnProperties.getUser(), this.svnProperties.getPass());
        this.url = SVNURL.parseURIDecoded(this.svnProperties.getUrl() + this.svnProperties.getDir());
        System.out.println("URL BEAN: " + url);
        repository = DAVRepositoryFactory.create(this.url);
    }

    public void toConnect() throws SVNException {

      

            repository.setAuthenticationManager(authManager);
            repository.testConnection();


    }

    public SVNRepository getRepository() {
        return repository;
    }

    public void setRepository(SVNRepository repository) {
        SvnConnectionBean.repository = repository;
    }

    public ISVNAuthenticationManager getAuthManager() {
        return authManager;
    }

    public void setAuthManager(ISVNAuthenticationManager authManager) {
        this.authManager = authManager;
    }

    public SVNURL getUrl() {
        return url;
    }

    public void setUrl(SVNURL url) {
        this.url = url;
    }

    public SVNPropertiesVOBean getSvnProperties() {
        return svnProperties;
    }

}
