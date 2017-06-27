package main.java.xsd.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import main.java.ddl.translator.DDLTranslator;
import main.java.exception.ParseException;
import main.java.point.XSDFamilyPoint;
import main.java.point.XSDPoint;
import main.java.xsd.type.XSDTypeDictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XSDReader {
	
	private Document xsdDoc;
	private XSDPoint dataPointTree;
	private ArrayList<XSDElement> typeList;
	private XSDElement topElement;
	
	public void setXSDFile (File xsdFile) {

		try {
			xsdDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xsdFile);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		

		
		
	}
	
	public XSDPoint createDataPointTree (DDLTranslator translator) throws ParseException {
		typeList = new ArrayList<XSDElement>();
		topElement = new XSDElement();
		NodeList nodeList = xsdDoc.getChildNodes();
		Node node = null;
		for (int i=0;i<nodeList.getLength();i++) {
			if (nodeList.item(i).getNodeName().equals("xs:schema")) {
				node = nodeList.item(i);
			}
		}
		
		nodeList = node.getChildNodes();
		
		for (int i = 0; i<nodeList.getLength();i++) {
			if (nodeList.item(i).getNodeName().equals("xs:simpleType")
					||nodeList.item(i).getNodeName().equals("xs:complexType")) {
				XSDElement element = new XSDElement();
				element.setNode(nodeList.item(i));
				typeList.add(element);

			} else if (nodeList.item(i).getNodeName().equals("xs:element")) {
				topElement.setNode(nodeList.item(i));
				
			} 
		}	
		
		XSDTypeDictionary.getInstance().createDictionary(typeList);
		
		//TODO assumes top element is XSDFaimlyPoint
		dataPointTree = new XSDFamilyPoint(translator,null);
		dataPointTree.parse(topElement);
		return dataPointTree;
	}
	
	public XSDPoint getDataPointTree () {
		
		
		return dataPointTree;
	}


}
