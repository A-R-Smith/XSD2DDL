package main.java.xsd.reader;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XSDElement {
	
	private Node node;
	
	public boolean hasChildren() {
		return node.hasChildNodes();
	}
	
	public ArrayList<XSDElement> getChildren() {
		ArrayList<XSDElement> list = new ArrayList<XSDElement>();
		NodeList nodeList = node.getChildNodes();
		for (int i=0;i<nodeList.getLength();i++) {
			if (!(nodeList.item(i).getNodeName().equals("#text")||
					nodeList.item(i).getNodeName().equals("#comment"))) {
				XSDElement element = new XSDElement();
				element.setNode(nodeList.item(i));
				list.add(element);
			}

		}
		
		return list;
	}
	
	public void setNode(Node node) {
		this.node = node;
	}
	
	public String getName() {
		return node.getNodeName();	
	}
	
	public String getAttribute(String attributeName) {
		return ((Element) node).getAttribute(attributeName);
	}
	
	public boolean hasAttribute(String attributeName) {
		if (node.getNodeName().equals("xs:sequence")) {
			return false;
		}
		return ((Element) node).hasAttribute(attributeName);
	}

}
