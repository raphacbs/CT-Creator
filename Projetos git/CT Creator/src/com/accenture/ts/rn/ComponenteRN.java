/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.ts.rn;

import com.accenture.bean.ComponenteBean;
import com.accenture.ts.dao.ComponenteDAO;
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
public class ComponenteRN {

    private Properties fileProperties = null;
    private FileInputStream file;
    private ComponenteDAO componentDAO;
    private static ComponenteRN componentRN;

    private ComponenteRN() throws IOException, SVNException {
        componentDAO = new ComponenteDAO();
    }

    public static ComponenteRN getInstance() throws IOException, SVNException {
        componentRN = new ComponenteRN();
        return componentRN;
    }

    public ComponenteBean getComponent(String name, String system) throws IOException, SVNException {
        ComponenteBean componente = new ComponenteBean();

        componentDAO.donwloadFiles(system);

        if (!name.contains(".properties")) {
            name += ".properties";
        }

        loadFileProperties(name, system);

        componente.setNameComponent(fileProperties.getProperty(ProjectSettings.PROPERTY_COMPONENT_NAME));
        componente.setDescription(fileProperties.getProperty(ProjectSettings.PROPERTY_DESCRIPTION));
        componente.setScripts(Arrays.asList(fileProperties.getProperty(ProjectSettings.PROPERTY_SCRIPTS).split(ProjectSettings.PROPERTY_SYSTEM_LINE_SEPARATOR)));
        componente.setSystem(fileProperties.getProperty(ProjectSettings.PROPERTY_SYSTEM));
        componente.setDate(FunctiosDates.stringToDate(fileProperties.getProperty(ProjectSettings.PROPERTY_DATE)));

        return componente;
    }

    public List<ComponenteBean> getComponents(List<SVNDirEntry> list, String system) throws IOException, SVNException {

        List<ComponenteBean> componentes = new ArrayList<ComponenteBean>();
        componentDAO.donwloadFiles(system);

        for (SVNDirEntry entry : list) {
            ComponenteBean componente = new ComponenteBean();

            loadFileProperties(entry.getName(), system);

            componente.setNameComponent(fileProperties.getProperty(ProjectSettings.PROPERTY_COMPONENT_NAME));
            componente.setIdComponent(componente.getIdComponentByNameComponent(componente.getNameComponent()));
            componente.setPartNameComponent(componente.getPartNameComponentByNameComponent(componente.getNameComponent()));
            componente.setDescription(fileProperties.getProperty(ProjectSettings.PROPERTY_COMPONENT_DESCRIPTION));
            componente.setScripts(Arrays.asList(fileProperties.getProperty(ProjectSettings.PROPERTY_SCRIPTS).split(ProjectSettings.DELIDELIMITER_COMMA)));
            componente.setSystem(fileProperties.getProperty(ProjectSettings.PROPERTY_SYSTEM));
            componente.setDate(FunctiosDates.stringToDate(fileProperties.getProperty(ProjectSettings.PROPERTY_DATE)));

            componentes.add(componente);
        }

        return componentes;
    }

    private void loadFileProperties(String fileName, String system) throws FileNotFoundException, IOException {
        fileProperties = new Properties();
        file = new FileInputStream(ProjectSettings.PATH_FILE_COMPONENT + "/" + system + "/" + fileName);
        fileProperties.load(file);
        file.close();

    }

//      private void loadFiles() throws SVNException{
//          componentDAO.donwloadFiles();
//      }
    public String saveFile(String fileName, ComponenteBean componente) throws SVNException, IOException {

        //atualiza pasta 
        componentDAO.donwloadFiles(componente.getSystem());

        if (fileName == null) {
            String id = createFile(componente);
            componentDAO.save(componente.getSystem());
//            workflowDAO.lockFile(id+".properties");
            return id + ProjectSettings.EXTENSION_FILE_PROPERTY;
        } else {
            editFile(fileName, componente);
            componentDAO.unLockFile(fileName, componente.getSystem());
            componentDAO.save(componente.getSystem());
//            workflowDAO.lockFile(fileName);
            return fileName;
        }
    }

    private String createFile(ComponenteBean componente) throws FileNotFoundException, IOException, SVNException {
        String id = generateId(componente.getSystem());
        componente.setIdComponent(id);
        //componente.setNameComponent(id + componente.getNameComponent());
        File newFile = new File(ProjectSettings.PATH_FILE_COMPONENT + "/" + componente.getSystem() + "/" + componente.getNameComponent() + ProjectSettings.EXTENSION_FILE_PROPERTY);
        FileOutputStream fileOut = new FileOutputStream(newFile);

        loadFileProperties(componente.getNameComponent() + ProjectSettings.EXTENSION_FILE_PROPERTY, componente.getSystem());

        fileProperties.put(ProjectSettings.PROPERTY_COMPONENT_NAME, componente.getNameComponent());
        fileProperties.put(ProjectSettings.PROPERTY_COMPONENT_DESCRIPTION, componente.getDescription());
        fileProperties.put(ProjectSettings.PROPERTY_DATE, FunctiosDates.dateToString(componente.getDate(), "dd/MM/yyyy HH:mm:ss"));
        fileProperties.put(ProjectSettings.PROPERTY_SYSTEM, componente.getSystem());
        String scripts = "";
        scripts = componente.getScripts().stream().map((script) -> script + ";").reduce(scripts, String::concat);
        fileProperties.put(ProjectSettings.PROPERTY_SCRIPTS, scripts);
        fileProperties.store(fileOut, null);
        return componente.getNameComponent();
    }

    private String editFile(String nameFile, ComponenteBean componente) throws FileNotFoundException, IOException {

        if (!nameFile.contains(ProjectSettings.EXTENSION_FILE_PROPERTY)) {
            nameFile += ProjectSettings.EXTENSION_FILE_PROPERTY;
        }

        File newFile = new File(ProjectSettings.PATH_FILE_COMPONENT + "/" + componente.getSystem() + "/" + nameFile);
        FileOutputStream fileOut = new FileOutputStream(newFile);

        loadFileProperties(nameFile, componente.getSystem());

        fileProperties.put(ProjectSettings.PROPERTY_COMPONENT_NAME, componente.getNameComponent());
        fileProperties.put(ProjectSettings.PROPERTY_COMPONENT_DESCRIPTION, componente.getDescription());
        fileProperties.put(ProjectSettings.PROPERTY_DATE, FunctiosDates.dateToString(componente.getDate(), "dd/MM/yyyy HH:mm:ss"));
        fileProperties.put(ProjectSettings.PROPERTY_SYSTEM, componente.getSystem());
        String scripts = "";
        scripts = componente.getScripts().stream().map((script) -> script + ";").reduce(scripts, String::concat);
        fileProperties.put(ProjectSettings.PROPERTY_SCRIPTS, scripts);

        fileProperties.store(fileOut, null);
        return componente.getNameComponent();
    }

    public boolean lockFile(String nameFile, String system) throws SVNException, IOException {

        if (verifyUserLock(nameFile, system)) {
            componentDAO.lockFile(nameFile, system);
            return true;
        } else {
            return false;
        }

    }

    public boolean unLockFile(String nameFile, String system) throws SVNException {

        if (verifyUserLock(nameFile, system)) {
            componentDAO.unLockFile(nameFile, system);
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyUserLock(String nameFile, String system) throws SVNException {

        if (componentDAO.isLock(nameFile, system)) {
            if (componentDAO.getUserLock(nameFile, system).equalsIgnoreCase(componentDAO.getUsername())) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public String getUserLock(String nameFile, String system) throws SVNException {
        if (componentDAO.isLock(nameFile, system)) {
            return componentDAO.getUserLock(nameFile, system);
        } else {
            return null;
        }

    }

    public String generateId(String system) throws SVNException, IOException {
        //comp_001
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = null;

        List<SVNDirEntry> entries = componentDAO.getEntries(system);
        Integer biggerID = 1;

        if (entries.size() == 0) {
            return "comp_001_";
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
                return "comp_00" + biggerID.toString() + "_";
            case 2:
                return "comp_0" + biggerID.toString() + "_";
            case 3:
                return "comp_" + biggerID.toString() + "_";
            default:
                return "comp_" + biggerID.toString() + "_";

        }

    }

    public List<SVNDirEntry> getEntries(String system) throws SVNException, IOException {
        return componentDAO.getEntries(system);
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

    public String delete(List<ComponenteBean> componentes, String system) throws SVNException, IOException {
        List<String> componenteNames = new ArrayList<String>();

        for (ComponenteBean componente : componentes) {
            if (verifyExistFile(componente.getNameComponent() + ProjectSettings.EXTENSION_FILE_PROPERTY, system)) {
                if (componentDAO.isLock(componente.getNameComponent() + ProjectSettings.EXTENSION_FILE_PROPERTY, system)) {
                    if (unLockFile(componente.getNameComponent() + ProjectSettings.EXTENSION_FILE_PROPERTY, system)) {
                        componenteNames.add(componente.getNameComponent() + ProjectSettings.EXTENSION_FILE_PROPERTY);
                    } else {
                        return "O compoenente " + componente.getNameComponent() + " está bloqueado pelo usuário " + getUserLock(componente.getNameComponent() + ProjectSettings.EXTENSION_FILE_PROPERTY, system);

                    }
                } else {
                    componenteNames.add(componente.getNameComponent() + ProjectSettings.EXTENSION_FILE_PROPERTY);
                }
            } else {
                return "O componente " + componente.getNameComponent() + " não existe, favor atualize a lista.";
            }
        }

        componentDAO.deleteFile(componenteNames, system);
        return null;
    }

    public void renomear(String nameFile, String system, String newName, ComponenteBean componente) throws Exception {
        if (!nameFile.contains(ProjectSettings.EXTENSION_FILE_PROPERTY)) {
            nameFile += ProjectSettings.EXTENSION_FILE_PROPERTY;
        }
        if (!newName.contains(ProjectSettings.EXTENSION_FILE_PROPERTY)) {
            newName += ProjectSettings.EXTENSION_FILE_PROPERTY;
        }

        componentDAO.renomearArquivo(nameFile, newName, system);

        editFile(newName, componente);
//        componentDAO.unLockFile(fileName, componente.getSystem());
        componentDAO.save(componente.getSystem());

    }

}
