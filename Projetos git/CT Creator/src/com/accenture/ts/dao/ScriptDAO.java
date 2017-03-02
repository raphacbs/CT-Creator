/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.dao;

import com.accenture.bean.SVNPropertiesVOBean;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNClientManager;

/**
 *
 * @author Oi_TSS
 */
public class ScriptDAO {
    
    private SVNPropertiesVOBean properties;
    private String url;
    private String username;
    private String password;
    private SVNRepository repo;
    private SVNClientManager clientManager;
    
}
