/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.log;

import com.accenture.util.ProjectSettings;
import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Oi_TSS
 */
public class MyLogger {

    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    static private FileHandler fileHTML;
    static private Formatter formatterHTML;
    
    
     static public void setup() throws IOException {

                // get the global logger to configure it
                Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

                // suppress the logging output to the console
                Logger rootLogger = Logger.getLogger("");
                
                ConsoleHandler consoleHandler = new ConsoleHandler();
                consoleHandler.setLevel(Level.ALL);
                
//                Handler[] handlers = rootLogger.getHandlers();
//                if (handlers[0] instanceof ConsoleHandler) {
//                        rootLogger.removeHandler(handlers[0]);
//                }

                logger.setLevel(Level.INFO);
                File folderLog = new File(new File("").getAbsolutePath()+ProjectSettings.PATH_LOG);
                folderLog.mkdir();
                fileTxt = new FileHandler(new File("").getAbsolutePath()+ProjectSettings.PATH_LOG_TXT);
                fileHTML = new FileHandler(new File("").getAbsolutePath()+ProjectSettings.PATH_LOG_HTML);

                // create a TXT formatter
                formatterTxt = new SimpleFormatter();
                fileTxt.setFormatter(formatterTxt);
                logger.addHandler(fileTxt);

                // create an HTML formatter
                formatterHTML = new MyHtmlFormatter();
                fileHTML.setFormatter(formatterHTML);
                logger.addHandler(fileHTML);
        }

}
