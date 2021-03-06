/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.alm;

import com.accenture.infrastructure.AuthenticateLoginLogoutExample;
import java.util.HashMap;
import java.util.Map;
import com.accenture.infrastructure.Constants;
import com.accenture.infrastructure.Entity;
import com.accenture.infrastructure.EntityMarshallingUtils;
import com.accenture.infrastructure.RestConnector;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author raphael.da.silva
 */
public class ExtraiALM {

    public ExtraiALM() throws IOException {
        
     Properties props = new Properties();
        props.load(new FileInputStream("log4j.properties"));
        PropertyConfigurator.configure(props);

       
    }

    public static void main(String[] args) throws Exception {
        RestConnector con, conStep;

        con = RestConnector.getInstance().init(
                new HashMap<String, String>(),
                Constants.HOST,
                Constants.PORT,
                Constants.DOMAIN,
                Constants.PROJECT);
        conStep = RestConnector.getInstance().init(
                new HashMap<String, String>(),
                Constants.HOST,
                Constants.PORT,
                Constants.DOMAIN,
                Constants.PROJECT);
        AuthenticateLoginLogoutExample login = new AuthenticateLoginLogoutExample();
        login.login(Constants.USERNAME, Constants.PASSWORD);
        String authenticationPoint = login.isAuthenticated();

        System.out.println("cookies after authentication (should contain LWSSO_COOKIE_KEY):"
                + con.getCookieString());

        System.out.println("cookies after authentication (should contain LWSSO_COOKIE_KEY):"
                + conStep.getCookieString());
        //Proof that we are indeed logged in
        if (login.isAuthenticated() == null) {
            System.out.println("we're logged in - no url was returned from isAuthenticated");
        }
 

        String testLabUrl = con.buildEntityCollectionUrl("test-sets");

        String resourceWeWantToRead = con.buildUrl("qcbin/rest/server");


        Map<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Accept", "application/xml");
        String responseStr = con.httpGet(resourceWeWantToRead, null, requestHeaders).toString();
        System.out.println("server properties from rest: " + responseStr.trim());


        //Query a collection of entities:
        StringBuilder b = new StringBuilder();
        //The query: "where field name starts with r"
        b.append("query={name['Janeiro']}");
        //The fields to display: id, name
        b.append("&fields=name");
        //The sort order: descending by ID (highest ID first)
        b.append("&order-by={name[DESC]}");
        //Display 10 results
        b.append("&page-size=1");
        //Counting from the 1st result, inclusive
//        b.append("&start-index=1");

        System.out.println("QUERY: " + b.toString());

        String listFromCollectionAsXmlTests
                = con.httpGet(testLabUrl, b.toString(), requestHeaders).toString();
        
        
         Entity entity = EntityMarshallingUtils.marshal(Entity.class, listFromCollectionAsXmlTests);

  
        System.out.println("Defects : " + listFromCollectionAsXmlTests);
        


        login.logout();

    }

    public String getCTALM(String nomeCT, String usuario, String senha) {

        try {
            Logger logger = Logger.getLogger(ExtraiALM.class);
        

            String xml = null;

            RestConnector con;
            con = RestConnector.getInstance().init(
                    new HashMap<String, String>(),
                    Constants.HOST,
                    Constants.PORT,
                    Constants.DOMAIN,
                    Constants.PROJECT);

            AuthenticateLoginLogoutExample login = new AuthenticateLoginLogoutExample();
            login.login(usuario, senha);
            String authenticationPoint = login.isAuthenticated();

            System.out.println("cookies after authentication (should contain LWSSO_COOKIE_KEY):"
                    + con.getCookieString());
            //Proof that we are indeed logged in
            if (login.isAuthenticated() == null) {
                System.out.println("we're logged in - no url was returned from isAuthenticated");

                String testLabUrl = con.buildEntityCollectionUrl("test");

                String testLab = con.buildEntityCollectionUrl(testLabUrl);

                System.out.println("Projeto: " + con.getProject());
                logger.info("Projeto: " + con.getProject());
                System.out.println("String TestLAb: " + testLabUrl);
                logger.info("String TestLAb: " + testLabUrl);

                //Read a simple resource. This example is not an entity.
                String resourceWeWantToRead = con.buildUrl("qcbin/rest/server");
                Map<String, String> requestHeaders = new HashMap<String, String>();
                requestHeaders.put("Accept", "application/xml");

                String responseStr = con.httpGet(resourceWeWantToRead, null, requestHeaders).toString();
                System.out.println("server properties from rest: " + responseStr.trim());
                logger.info("server properties from rest: " + responseStr.trim());
//        System.out.println("cookies after first service (creates an implicit session on the server, delivered using the cookie QCSession):"
//                           + con.getCookieString());

                //Query a collection of entities:
                StringBuilder b = new StringBuilder();
                //The query: "where field name starts with r"
//        b.append("query={id[5044]}");
                b.append("query={name['" + nomeCT + "']}");
            //The fields to display: id, name
//            b.append("&fields=name");
                //The sort order: descending by ID (highest ID first)
                b.append("&order-by={name[ASC]}");
            //Display 10 results
//            b.append("&page-size=1");
                //Counting from the 1st result, inclusive
                b.append("&start-index=1");

                System.out.println("QUERY: " + b.toString());
                logger.info("QUERY: " + b.toString());

                String listFromCollectionAsXmlTests = con.httpGet(testLabUrl, b.toString(), requestHeaders).toString();

                System.out.println("Lista de CTs : " + listFromCollectionAsXmlTests);
                logger.info("Lista de CTs : " + listFromCollectionAsXmlTests);
//        System.out.println("response for list requirements: " + listFromCollectionAsXml);

            //Read the entity we generated in the above step. Perform a get operation on its URL.
//        String postedEntityReturnedXml =
//                con.httpGet(newCreatedResourceUrl, null, requestHeaders).toString();
//        System.out.println("response for retrieving entity: " + postedEntityReturnedXml.trim());
                //xml -> class instance
//        Entity entity = EntityMarshallingUtils.marshal(Entity.class, listFromCollectionAsXmlTests);
                //Now show that you can do something with that object
//        List<Field> fields = entity.getFields().getField();
//        System.out.print("listing fields from marshalled object: ");
//        for (Field field : fields) {
//            System.out.print(field.getName() + "=" + field.getValue() + ", ");
//        }
//        System.out.println("");
//            cleanupwriteExample.deleteEntity(newCreatedResourceUrl).toString().trim();
                xml = listFromCollectionAsXmlTests;

                login.logout();

                return xml;
            } else {
                JOptionPane.showMessageDialog(null, "O login falhou. Verifique usuário e senha na tela de configuração.", "Erro", JOptionPane.ERROR_MESSAGE);
                logger.info("Lista de CTs : O login falhou. Verifique usuário e senha na tela de configuração.");
                return null;
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return ex.getMessage();
        }

    }

}
