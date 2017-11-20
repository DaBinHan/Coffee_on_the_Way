/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Leejinnyeong
 */
public class XMLFavoriteManager {
 
    Document doc;
    private File xmlFile;
    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    
     public HashMap readXML(String filePath, String fileName) throws IOException
   {
       try
       {
           xmlFile = new File(filePath, fileName);
           dbFactory = DocumentBuilderFactory.newInstance(); 
           dBuilder = dbFactory.newDocumentBuilder(); 
           doc = dBuilder.parse(xmlFile);
           doc.getDocumentElement().normalize();
       }
       catch (Exception e) 
       {
           e.printStackTrace();
       }
       
       return dataConvert();
   }
   
   private HashMap dataConvert()
   {
       HashMap xmlDataHashMap = new HashMap();
       HashMap dataMapper;
       
       //<menu id="00011"><name>아메리카노</name><price>3000</price></menu> 
       NodeList nList = doc.getElementsByTagName("select");  //xmlmenumanager와 다르게 "select"다
       if("select".equals(nList.item(0).getNodeName()))
       {
            for(int i = 0; i < nList.getLength(); i++)
            {
                dataMapper = new HashMap();
                Node nNode = nList.item(i);
                if(nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element) nNode;
           
                    dataMapper.put("name", element.getElementsByTagName("name").item(0).getTextContent());
                    
                    xmlDataHashMap.put(element.getAttribute("id"), dataMapper);
                }
            }
       }
       else
       {
           xmlDataHashMap = null;
       }
       
       return xmlDataHashMap;
   }
    
    public void editXML(String filePath, String fileName, HashMap courseInfo) throws IOException, ParserConfigurationException, TransformerConfigurationException, TransformerException, SAXException
    {
        File xmlFiles = new File(filePath, fileName);
        DocumentBuilderFactory dbFactorys = DocumentBuilderFactory.newInstance(); 
        DocumentBuilder dBuilders = dbFactorys.newDocumentBuilder(); 
        Document docs = dBuilders.parse(xmlFiles);
                       
        Node root = docs.getFirstChild();
        if(root.getNodeType() == Node.ELEMENT_NODE)
        {
            Element cafe = docs.createElement("select"); 
            cafe.setAttribute("id", courseInfo.get("id").toString());
            root.appendChild(cafe);
        
            Element name = docs.createElement("name"); 
            name.appendChild(docs.createTextNode(courseInfo.get("name").toString())); 
            cafe.appendChild(name);
                    
        }
                       
        docs.getDocumentElement().normalize();
        
        DOMSource xmlDOM = new DOMSource(docs); 
        StreamResult xmlResultFile = new StreamResult(new File(filePath, fileName)); 
        TransformerFactory.newInstance().newTransformer().transform(xmlDOM, xmlResultFile); 
    }            
}
