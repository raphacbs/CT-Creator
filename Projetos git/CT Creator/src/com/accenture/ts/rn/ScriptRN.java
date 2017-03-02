/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.rn;


import com.accenture.bean.ScriptBean;

import com.accenture.ts.dao.ScriptDAO;
import com.accenture.util.FunctiosDates;
import com.accenture.util.ProjectSettings;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;

/**
 *
 * @author Oi_TSS
 */
public class ScriptRN {
    
    private Properties fileProperties = null;
    private FileInputStream file;
    private ScriptDAO scriptDAO;
    private static ScriptRN scriptRN;
    
    
     private ScriptRN() throws IOException, SVNException {
        scriptDAO = new ScriptDAO();
    }

    public static ScriptRN getInstance() throws IOException, SVNException {
        scriptRN = new ScriptRN();
        return scriptRN;
    }
    
      public ScriptBean getScript(String name, String system) throws IOException, SVNException {
        ScriptBean script = new ScriptBean();

        scriptDAO.donwloadFiles(system);

        if (!name.contains(".properties")) {
            name += ".properties";
        }

        loadFileProperties(name, system);

        script.setNameScript(fileProperties.getProperty(ProjectSettings.PROPERTY_SCRIPT_NAME));
        script.setDescription(fileProperties.getProperty(ProjectSettings.PROPERTY_SCRIPT_DESCRIPTION));
        script.setComponents(Arrays.asList(fileProperties.getProperty(ProjectSettings.PROPERTY_SCRIPTS).split(ProjectSettings.PROPERTY_SYSTEM_LINE_SEPARATOR)));
        script.setSystem(fileProperties.getProperty(ProjectSettings.PROPERTY_SYSTEM));
        script.setDate(FunctiosDates.stringToDate(fileProperties.getProperty(ProjectSettings.PROPERTY_DATE)));
        
        
        

        return script;
    }

    public List<ScriptBean> getScripts(List<SVNDirEntry> list, String system) throws IOException, SVNException {

        List<ScriptBean> scripts = new ArrayList<ScriptBean>();
        scriptDAO.donwloadFiles(system);

        for (SVNDirEntry entry : list) {
            ScriptBean script = new ScriptBean();

            loadFileProperties(entry.getName(), system);

           script.setNameScript(fileProperties.getProperty(ProjectSettings.PROPERTY_SCRIPT_NAME));
        script.setDescription(fileProperties.getProperty(ProjectSettings.PROPERTY_SCRIPT_DESCRIPTION));
        script.setComponents(Arrays.asList(fileProperties.getProperty(ProjectSettings.PROPERTY_SCRIPTS).split(ProjectSettings.PROPERTY_SYSTEM_LINE_SEPARATOR)));
        script.setSystem(fileProperties.getProperty(ProjectSettings.PROPERTY_SYSTEM));
        script.setDate(FunctiosDates.stringToDate(fileProperties.getProperty(ProjectSettings.PROPERTY_DATE)));
       
            scripts.add(script);
        }

        return scripts;
    }

    private void loadFileProperties(String fileName, String system) throws FileNotFoundException, IOException {
        fileProperties = new Properties();
        file = new FileInputStream(ProjectSettings.PATH_FILE_SCRIPT + "/" + system + "/" + fileName);
        fileProperties.load(file);
        file.close();

    }

//      private void loadFiles() throws SVNException{
//          componentDAO.donwloadFiles();
//      }
    public String saveFile(String fileName, ScriptBean script) throws SVNException, IOException {

        //atualiza pasta 
        scriptDAO.donwloadFiles(script.getSystem());

        if (fileName == null) {
            String id = createFile(script);
            scriptDAO.save(script.getSystem());
//            workflowDAO.lockFile(id+".properties");
            return id + ProjectSettings.EXTENSION_FILE_PROPERTY;
        } else {
            editFile(fileName, script);
            scriptDAO.unLockFile(fileName, script.getSystem());
            scriptDAO.save(script.getSystem());
//            workflowDAO.lockFile(fileName);
            return fileName;
        }
    }

    private String createFile(ScriptBean script) throws FileNotFoundException, IOException, SVNException {
        String id = generateId(script.getSystem());
        script.setNameScript(id + script.getNameScript());
        File newFile = new File(ProjectSettings.PATH_FILE_SCRIPT + "/" + script.getSystem() + "/" + script.getNameScript()+ ProjectSettings.EXTENSION_FILE_PROPERTY);
        FileOutputStream fileOut = new FileOutputStream(newFile);

        loadFileProperties(script.getNameScript() + ProjectSettings.EXTENSION_FILE_PROPERTY, script.getSystem());

        fileProperties.put(ProjectSettings.PROPERTY_SCRIPT_NAME, script.getNameScript());
        fileProperties.put(ProjectSettings.PROPERTY_SCRIPT_DESCRIPTION, script.getDescription());
        fileProperties.put(ProjectSettings.PROPERTY_DATE, FunctiosDates.dateToString(script.getDate(), "dd/MM/yyyy HH:mm:ss"));
        fileProperties.put(ProjectSettings.PROPERTY_SYSTEM, script.getSystem());
        String scripts = "";
        scripts = script.getComponents().stream().map((componente) -> componente + ";").reduce(scripts, String::concat);
        fileProperties.put(ProjectSettings.PROPERTY_SCRIPTS, scripts);
        fileProperties.store(fileOut, null);
        return script.getNameScript();
    }

    private String editFile(String nameFile, ScriptBean script) throws FileNotFoundException, IOException {

        if (!nameFile.contains(ProjectSettings.EXTENSION_FILE_PROPERTY)) {
            nameFile += ProjectSettings.EXTENSION_FILE_PROPERTY;
        }

        File newFile = new File(ProjectSettings.PATH_FILE_SCRIPT + "/" + script.getSystem() + "/" + nameFile);
        FileOutputStream fileOut = new FileOutputStream(newFile);

        loadFileProperties(nameFile, script.getSystem());

        fileProperties.put(ProjectSettings.PROPERTY_SCRIPT_NAME, script.getNameScript());
        fileProperties.put(ProjectSettings.PROPERTY_SCRIPT_DESCRIPTION, script.getDescription());
        fileProperties.put(ProjectSettings.PROPERTY_DATE, FunctiosDates.dateToString(script.getDate(), "dd/MM/yyyy HH:mm:ss"));
        fileProperties.put(ProjectSettings.PROPERTY_SYSTEM, script.getSystem());

        String scripts = "";
        scripts = script.getComponents().stream().map((componente) -> componente + ";").reduce(scripts, String::concat);
        fileProperties.put(ProjectSettings.PROPERTY_SCRIPTS, scripts);

        fileProperties.store(fileOut, null);
        return script.getNameScript();
    }

    public boolean lockFile(String nameFile, String system) throws SVNException, IOException {

        if (verifyUserLock(nameFile, system)) {
            scriptDAO.lockFile(nameFile, system);
            return true;
        } else {
            return false;
        }

    }

    public boolean unLockFile(String nameFile, String system) throws SVNException {

        if (verifyUserLock(nameFile, system)) {
            scriptDAO.unLockFile(nameFile, system);
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyUserLock(String nameFile, String system) throws SVNException {

        if (scriptDAO.isLock(nameFile, system)) {
            if (scriptDAO.getUserLock(nameFile, system).equalsIgnoreCase(scriptDAO.getUsername())) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public String getUserLock(String nameFile, String system) throws SVNException {
        if (scriptDAO.isLock(nameFile, system)) {
            return scriptDAO.getUserLock(nameFile, system);
        } else {
            return null;
        }

    }

    public String generateId(String system) throws SVNException, IOException {
        //comp_001
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = null;

        List<SVNDirEntry> entries = scriptDAO.getEntries(system);
        Integer biggerID = 1;

        if (entries.size() == 0) {
            return "Cen_001_";
        }

        for (int i = 0; i < entries.size(); i++) {
            int idTemp = 0;
            matcher = pattern.matcher(entries.get(i).getName());

            if (matcher.find()) {
                idTemp = Integer.parseInt(matcher.group());
            }

            if (biggerID <= idTemp) {
                biggerID = idTemp;
                biggerID++;
            }

        }

        switch (biggerID.toString().length()) {
            case 1:
                return "Cen_00" + biggerID.toString() + "_";
            case 2:
                return "Cen_0" + biggerID.toString() + "_";
            case 3:
                return "Cen_" + biggerID.toString() + "_";
            default:
                return "Cen_" + biggerID.toString() + "_";

        }

    }

    public List<SVNDirEntry> getEntries(String system) throws SVNException, IOException {
        return scriptDAO.getEntries(system);
    }

    public boolean verifyExistFile(String Filename, String system) throws SVNException, IOException {
        boolean exist = false;
        List<SVNDirEntry> entries = getEntries(system);

        for (SVNDirEntry entry : entries) {
            if (entry.getName().equalsIgnoreCase(Filename)) {
                exist = true;
            }
        }

        return exist;
    }

    public String delete(List<ScriptBean> scripts, String system) throws SVNException, IOException {
        List<String> scriptNames = new ArrayList<String>();

        for (ScriptBean script : scripts) {
            if (verifyExistFile(script.getNameScript() + ProjectSettings.EXTENSION_FILE_PROPERTY, system)) {
                if (scriptDAO.isLock(script.getNameScript()+ ProjectSettings.EXTENSION_FILE_PROPERTY, system)) {
                    if (unLockFile(script.getNameScript()+ ProjectSettings.EXTENSION_FILE_PROPERTY, system)) {
                        scriptNames.add(script.getNameScript()+ ProjectSettings.EXTENSION_FILE_PROPERTY);
                    } else {
                        return "O script " + script.getNameScript()+ " está bloqueado pelo usuário " + getUserLock(script.getNameScript()+ ProjectSettings.EXTENSION_FILE_PROPERTY, system);

                    }
                } else {
                    scriptNames.add(script.getNameScript()+ ProjectSettings.EXTENSION_FILE_PROPERTY);
                }
            } else {
                return "O scripts " + script.getNameScript()+ " não existe, favor atualize a lista.";
            }
        }

        scriptDAO.deleteFile(scriptNames, system);
        return null;
    }

    public void renomear(String nameFile, String system, String newName, ScriptBean script) throws Exception {
        if (!nameFile.contains(ProjectSettings.EXTENSION_FILE_PROPERTY)) {
            nameFile += ProjectSettings.EXTENSION_FILE_PROPERTY;
        }
        if (!newName.contains(ProjectSettings.EXTENSION_FILE_PROPERTY)) {
            newName += ProjectSettings.EXTENSION_FILE_PROPERTY;
        }

        scriptDAO.renomearArquivo(nameFile, newName, system);

        editFile(newName, script);
//        componentDAO.unLockFile(fileName, componente.getSystem());
        scriptDAO.save(script.getSystem());

    }

    
}
