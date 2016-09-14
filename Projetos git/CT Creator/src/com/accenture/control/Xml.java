/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.control;

import com.accenture.bean.CtAlm;
import com.accenture.bean.Plano;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.StringWriter;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.log4j.PropertyConfigurator;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import org.jdom2.*;
import org.jsoup.Jsoup;

/**
 *
 * @author raphael.da.silva
 */
public class Xml {

    private final String FILE = "C:\\FastPlan\\alm.xml";
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Xml.class);

    public Xml() throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream("log4j.properties"));
        PropertyConfigurator.configure(props);
    }

//    public static String identar(String codigo){
//        StringBuffer buffer = new StringBuffer();
//        char c = '\0';
//        for (int i = 0; i < codigo.length(); i++){
//            c = codigo.charAt(i);
//            buffer.append(c);
//            if (c == '>'){
//                buffer.append("\n" + "");
//            }
//            if(c == '<'){
//                buffer.trimToSize();
//            }
//        }
//        return buffer.toString();
//    }
    public void geraArquivoXml(String xml) {
        StringBuffer str = new StringBuffer();
        str.append(xml);
        try {
            FileWriter out = new FileWriter(FILE);
            
//            BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE),"UTF-8"));
//            InputStream inputStream = new FileInputStream(f);
//            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            
            OutputStreamWriter bufferOut = new OutputStreamWriter(new FileOutputStream(FILE));
            bufferOut.append(str.toString());
            bufferOut.close();
//            
//            PrintWriter pw = new PrintWriter (new OutputStreamWriter (new FileOutputStream (FILE), "UTF-8"));
//            pw.append(str.toString());
//            pw.close();

//            out.write(out.toString());
//            out.close();
        } catch (IOException e) {
            logger.error("Erro ao gerar aqruivo", e);
        }
    }

    public String identaXml(String xml) {
        try {
//            xml = convertFromUTF8(xml);
            
            
            logger.info("Inicio da identação do XML");
            logger.info("Lendo Byte a Byte");
            org.w3c.dom.Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new ByteArrayInputStream(xml.getBytes("UTF-8"))));
            logger.info("fim da leitura Byte a Byte");
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']",
                    document,
                    XPathConstants.NODESET);
            logger.info("Tamanho nodeList: " + nodeList.getLength());
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node node = nodeList.item(i);
                node.getParentNode().removeChild(node);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
//            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            StringWriter stringWriter = new StringWriter();
            StreamResult streamResult = new StreamResult(stringWriter);
            
            

            transformer.transform(new DOMSource(document), streamResult);

            System.out.println(stringWriter.toString());
            logger.info("XML identado: " + stringWriter.toString());
            return stringWriter.toString();
        } catch (Exception e) {
            logger.error("ERRO ao identar XML: " + e.getMessage());
            return null;
        }
    }

    public List<Plano> lerXML() {
        List<CtAlm> listCtALM = new ArrayList<CtAlm>();
        List<Plano> listPlano = new ArrayList<Plano>();
        try {

            String valor = null;

            //Aqui voc� informa o nome do arquivo XML.
             logger.info("Inicio método:  new File(FILE);");
            File f = new File(FILE);
            logger.info("Inicio método:  new FileInputStream(f);");
//            logger.info("Inicio m�todo:  new FileInputStream(f);");
//            InputStream inputStream = new FileInputStream(f);
//            logger.info("Inicio m�todo:  new InputStreamReader(inputStream, \"UTF-8\";");
//            Reader reader = new InputStreamReader(inputStream, "UTF-8");
//
//            InputSource is = new InputSource(reader);
            logger.info("Inicio do método:  new BufferedReader(new InputStreamReader(new FileInputStream(f), \"UTF-8\"))"); 
            BufferedReader myBuffer = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            logger.info("Fim do método:  new BufferedReader(new InputStreamReader(new FileInputStream(f), \"UTF-8\"))"); 
            logger.info("Inicio do método: new InputSource(myBuffer)"); 
            InputSource is = new InputSource(myBuffer);
            logger.info("Fim do método: new InputSource(myBuffer)"); 




            logger.info("Arquivo xml: " + FILE);
            

            logger.info("Criando objeto Document doc");
            Document doc = null;
            logger.info("Criado objeto Document doc e setado como null");
            logger.info("Criando objeto SAXBuilder builder");
            SAXBuilder builder = new SAXBuilder();
            logger.info("Criado objeto SAXBuilder builder new SAXBuilder()");
            logger.info("inicio - doc = builder.build(is);");
            doc = builder.build(is);
            logger.info("fim - doc = builder.build(is);");

            logger.info("inicio - org.jdom2.Element ct = doc.getRootElement();");
            org.jdom2.Element ct = doc.getRootElement();
            logger.info("fim - org.jdom2.Element ct = doc.getRootElement();");
//            org.jdom2.Element ct = doc.getRootElement().getChild("Entity").getChild("Fields");

            if (Integer.parseInt(ct.getAttributeValue("TotalResults")) == 0) {
                logger.info("Nenhum CT foi encontrado no arquivo XML");
                System.out.println("A busca n�o retornou resultados");
                JOptionPane.showMessageDialog(null, "Nenhum CT foi encontrado.", "ALM", JOptionPane.INFORMATION_MESSAGE);
                return null;
            } else {

                List<org.jdom2.Element> lista = ct.getChildren();
                logger.info("Tamanho da listaroot do xml: " + lista.size());
                List<org.jdom2.Element> lista2 = ct.getChild("Entity").getChild("Fields").getChildren();
                logger.info("Tamanho da lista de campo do xml: " + lista2.size());
                Iterator i = lista.iterator();

                while (i.hasNext()) {
                    logger.info("Iniciando o loop das entity");
                    CtAlm ctALM = new CtAlm();
                    Plano p = new Plano();
                    org.jdom2.Element element = (org.jdom2.Element) i.next();
                    System.out.println("Type:" + element.getAttributeValue("Type"));

                    Iterator j = lista2.iterator();
                    int cont = 0;

                    while (j.hasNext()) {
                        logger.info("Iniciando o loop dos fields");
                        org.jdom2.Element element2 = (org.jdom2.Element) j.next();
//                System.out.println("Name:" + element2.getAttributeValue("Name"));

                        System.out.println("Name:" + element.getChild("Fields").getChildren().get(cont).getAttributeValue("Name"));
                        ctALM.setCampo(element.getChild("Fields").getChildren().get(cont).getAttributeValue("Name"));
                        System.out.println("Value:" + element.getChild("Fields").getChildren().get(cont).getChildText("Value"));
                        ctALM.setValor(element.getChild("Fields").getChildren().get(cont).getChildText("Value"));
                        String nomeCampo = element.getChild("Fields").getChildren().get(cont).getAttributeValue("Name");
                        String valorCampo = element.getChild("Fields").getChildren().get(cont).getChildText("Value");

                        switch (nomeCampo) {
                            case "user-template-08":
                                p.setQtdSistemas(Integer.parseInt(valorCampo));
                                break;

                            case "user-template-09":
                                p.setQtdStep(Integer.parseInt(valorCampo));
                                break;

                            case "user-template-01":
                                p.setSistemaMaster(valorCampo);
                                break;

                            case "user-template-03":
                                p.setCenarioTeste(valorCampo);
                                break;

                            case "user-template-06":
                                p.setRequisito(valorCampo);
                                break;

                            case "user-template-05":
                                p.setFornecedor(valorCampo);
                                break;

                            case "description":
                                
                                String descricao = valorCampo;
//                                convertFromUTF8(descricao);
                                logger.debug("Campo Descri��o: "+ descricao );
//                                try{
//                                   logger.debug("Parse no campo Descri��o: "+ descricao );
//                                   descricao = Jsoup.parse(descricao).text();
//                                }catch(Exception ex){
//                                     logger.error("Erro parser ",ex);
//                                }
                                
                                
                                logger.debug("Setando no objeto plano: p.setDescCasoTeste(descricao);" );
                                p.setDescCasoTeste(descricao);
                                logger.debug("Setado no objeto plano: p.setDescCasoTeste(descricao);" );
                                break;

                            case "user-template-28":
                                p.setTpRequisito(valorCampo);
                                break;

                            case "user-template-22":
                                p.setSistemasEnvolvidos(valorCampo);
                                break;

                            case "user-template-24":
                                p.setComplexidade(valorCampo);
                                break;

                            case "name":
                                p.setCasoTeste(valorCampo);
                                break;

                            case "subtype-id":
                                p.setType(valorCampo);
                                break;
                        }

                        
                        
                        logger.info("Fim do loop dos fields - nome do campo: "+nomeCampo);
                        logger.info("contador do campo: "+ cont);
                        cont++;
                    }
                    logger.info("Adicionando CT na lista de plano");
                    listPlano.add(p);
                    logger.info("Adicionando " + p.getCasoTeste() + " CT adicoando na lista");
                    logger.info("Fim do loop das entity");
                }

            }

        } catch (JDOMException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getMessage() + ex.getLocalizedMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            logger.error("Erro : ",ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + ex.getMessage() + ex.getLocalizedMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            logger.error("Erro : " , ex);
        }

        logger.info("Tamanho da Lista de CTS extraidos do XML : " + listPlano.size());
        return listPlano;

    }

    // convert from UTF-8 -> internal Java String format
    public static String convertFromUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }

// convert from internal Java String format -> UTF-8
    public static String convertToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }

}
