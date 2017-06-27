package main.java.point;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import main.java.ddl.translator.DDLTranslator;
import main.java.exception.ParseException;
import main.java.exception.PrintException;
import main.java.xsd.reader.XSDElement;
import main.java.xsd.type.XSDDataType;
import main.java.xsd.type.XSDTypeDictionary;

public class XSDFamilyPoint extends XSDAbstractPoint implements XSDPoint {
	
	private static final Logger LOGGER = Logger.getLogger(XSDFamilyPoint.class);

	protected List<XSDPoint> children;
	
	public XSDFamilyPoint(DDLTranslator translator, XSDPoint parent) {
		super(translator,parent);
	}

	@Override
	public void parse (XSDElement element) throws ParseException {
		if (element.hasAttribute("name")) {
			pointName = element.getAttribute("name");

			xPath = createXPath(pointName);
			xPathSuffix="";
			level = createLevel();
			children = new ArrayList<XSDPoint>();

			List<XSDElement> elementList = element.getChildren();
			if (elementList.get(0).getName().equals("xs:complexType")) {
				List<XSDElement> elementList2 = elementList.get(0).getChildren();
				if (elementList2.get(0).getName().equals("xs:sequence")) {

					List<XSDElement> elementList3 = elementList2.get(0).getChildren();
					for (XSDElement childElement : elementList3) {
						XSDPoint point = createPointFromElement(childElement);					
						point.parse(childElement);
						children.add(point);
					}	
				} else if (elementList2.get(0).getName().equals("xs:simpleContent")) {
					children.addAll(parseInLineExtType(elementList2.get(0).getChildren().get(0)));

				} else {
					LOGGER.error("XSDFamilyPoint fail- expecting xs:sequence: "+elementList2.get(0).getName());
					throw new ParseException();
				}	
				
				
				if (elementList2.size()>1) {
					for (int i=1;i<elementList2.size();i++) {
						if (elementList2.get(i).getName().equals("xs:attribute")) {
							children.add(parseInlineAttribute(elementList2.get(i)));
							
						} else {
							LOGGER.error("expecting xs:attribute: "+elementList2.get(i).getName());
							throw new ParseException();
						}
					}
				}
			} else {
				LOGGER.error("expecting xs:complexType: "+elementList.get(0).getName());
				throw new ParseException();
			}
		} else {
			LOGGER.error("expecting first element to have name attribute: "+element.getName());
			throw new ParseException();
		}
	}


	@Override
	public void prettyPrint(String xPath, int level) {
		translator.prettyPrint(this);
		for (XSDPoint point : children) {
			point.prettyPrint(this.xPath,level);
			
		}
		
	}

	@Override
	public void ddlPrint() throws PrintException {
		
		translator.ddlPrint(this);
		for (XSDPoint point : children) {
			point.ddlPrint();
		}	
	}

	
	private XSDPoint createPointFromElement(XSDElement element) {
		XSDPoint point = null;
		if (element.hasAttribute("type")) {

			if (XSDTypeDictionary.getInstance().isComplexType(element.getAttribute("type"))) {
				point = new XSDComplexPoint(translator,this);		
			} else {
				point = new XSDDataPoint(translator,this);
			}			
		} else {
			point = new XSDFamilyPoint(translator,this);
			
		}
		
		if (element.hasAttribute("maxOccurs")) {
			point.setIsUnbounded(true);
		} else {
			point.setIsUnbounded(false);
		}
		return point;
	}
	
	private XSDDataPoint parseInlineAttribute(XSDElement element) {
		XSDDataPoint dataPoint = new XSDDataPoint(translator,this);
		XSDDataType dataType = XSDTypeDictionary.getInstance().getXSDType(element.getAttribute("type"));
		dataPoint.setName(element.getAttribute("name"));
		dataPoint.setXPath(xPath);
		dataPoint.setxPathSuffix("/@"+dataPoint.getName());
		dataPoint.setDataType(dataType);
		
		if (element.hasAttribute("maxOccurs")) {
			dataPoint.setIsUnbounded(true);
		} else {
			dataPoint.setIsUnbounded(false);
		}
		
		return dataPoint;
	}

	
	//TODO make this a new class
	private List<XSDPoint> parseInLineExtType(XSDElement element) throws ParseException {
		List<XSDPoint> children = new ArrayList<XSDPoint>();
		if (element.getName().equals("xs:extension")) {
			XSDDataPoint dataPoint = new XSDDataPoint(translator,this);
			XSDDataType dataType = XSDTypeDictionary.getInstance().getXSDType(element.getAttribute("base"));
			dataPoint.setName(dataType.getPointNameSuffix());
			dataPoint.setXPath(xPath);
			dataPoint.setxPathSuffix("/text()");
			dataPoint.setDataType(dataType);
			children.add(dataPoint);
			List<XSDElement> elementList = element.getChildren();
			for (XSDElement element2 : elementList) {
				if (element2.getName().equals("xs:attribute")) {
					dataPoint = new XSDDataPoint(translator,this);
					dataPoint.setDataType(XSDTypeDictionary.getInstance().getXSDType(element2.getAttribute("type")));
					dataPoint.setName(element2.getAttribute("name"));
					dataPoint.setXPath(xPath);
					dataPoint.setxPathSuffix("/@"+dataPoint.getName());
					children.add(dataPoint);
				} else {
					LOGGER.error("expecting xs:extension"+element2.getName());
					throw new ParseException();
				}
			}
		} else {
			LOGGER.error("expecting xs:extension"+element.getName());
			throw new ParseException();
		}
		return children;
	}
	

	@Override
	public XSDDataType getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
