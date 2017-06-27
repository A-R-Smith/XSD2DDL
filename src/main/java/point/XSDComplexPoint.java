package main.java.point;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import main.java.ddl.translator.DDLTranslator;
import main.java.exception.ParseException;
import main.java.exception.PrintException;
import main.java.xsd.reader.XSDElement;
import main.java.xsd.type.XSDComplexExtType;
import main.java.xsd.type.XSDComplexType;
import main.java.xsd.type.XSDDataType;
import main.java.xsd.type.XSDRenamedType;
import main.java.xsd.type.XSDTypeDictionary;

public class XSDComplexPoint extends XSDAbstractPoint implements XSDPoint {
	
	private static final Logger LOGGER = Logger.getLogger(XSDComplexPoint.class);
	
	private XSDComplexType type;

	protected List<XSDPoint> children;
	
	public XSDComplexPoint(DDLTranslator translator, XSDPoint parent) {
		super(translator,parent);
	}
	
	@Override
	public void parse(XSDElement element) throws ParseException {
		children = new ArrayList<XSDPoint>();
		if (element.hasAttribute("name")||element.hasAttribute("type")) {
			pointName = element.getAttribute("name");

			xPath = createXPath(pointName);
			xPathSuffix="";
			level = createLevel();

			type  = (XSDComplexType)XSDTypeDictionary.getInstance().getXSDType(element.getAttribute("type"));
			if (type instanceof XSDComplexExtType) {
				children = processComplexExtType((XSDComplexExtType)type);
			} else if (type instanceof XSDComplexType) {
				children = processComplexType((XSDComplexType)type);
			} else {
				LOGGER.error("XSDComplexPoint fail- expecting type to be complex: " +element.getName());
				throw new ParseException();
			}
		} else {
			LOGGER.error("XSDComplexPoint fail- expecting first element to have name & type: " +element.getName());
			throw new ParseException();
		}
	}
		


	@Override
	public void prettyPrint(String xPath, int level) {
		
		translator.prettyPrint(this);
		for (XSDPoint point : children) {
			point.prettyPrint(this.xPath,this.level);
		}
		
	}

	@Override
	public void ddlPrint() throws PrintException {
		
		translator.ddlPrint(this);
		for (XSDPoint point : children) {
			point.ddlPrint();
		}	
		
	}
	
	//TODO make own class
	protected List<XSDPoint> processComplexType(XSDComplexType complexDataType) throws ParseException {
		List<XSDPoint> childrenOut = new ArrayList<XSDPoint>();
		for (XSDRenamedType renamedType : complexDataType.getTypes()) {
			if (renamedType.getXsdDataType() instanceof XSDComplexExtType) {
				XSDDataPoint dataPoint = new XSDDataPoint(translator,this);	
				XSDComplexExtType renamedDataType = (XSDComplexExtType)renamedType.getXsdDataType();
				
//				dataPoint.setName(renamedType.getName()+renamedDataType.getBaseType().getPointNameSuffix());
				dataPoint.setName(renamedDataType.getBaseType().getPointNameSuffix());
				dataPoint.setXPath(xPath+"/"+renamedType.getName());
				dataPoint.setxPathSuffix("/text()");
				dataPoint.setLevel(createLevelSpecial());
				dataPoint.setDataType(renamedDataType.getBaseType());
				childrenOut.add(dataPoint);
				
				for (XSDRenamedType renamedType2 : (renamedDataType.getTypes())) {
					XSDDataType renamedDataType2 = renamedType2.getXsdDataType();
					if (renamedDataType2 instanceof XSDComplexType) {
						LOGGER.error("XSDDataComplexPoint fail- expecting renamedType to be XSDComplexType");
						throw new ParseException();
						
					} else {
						dataPoint = new XSDDataPoint(translator,this);
						dataPoint.setName(renamedType2.getName());
						dataPoint.setXPath(xPath+"/"+renamedType.getName());
						dataPoint.setxPathSuffix("/@"+dataPoint.getName());
						dataPoint.setLevel(createLevelSpecial());
						dataPoint.setDataType(renamedType2.getXsdDataType());
						childrenOut.add(dataPoint);
					}
				}

			} else {
				XSDDataPoint dataPoint = new XSDDataPoint(translator,this);
				dataPoint.setName(renamedType.getName());
				dataPoint.setXPath(xPath+"/"+dataPoint.getName());
				dataPoint.setxPathSuffix("/text()");
				dataPoint.setLevel(createLevelSpecial());
				dataPoint.setDataType(renamedType.getXsdDataType());
				childrenOut.add(dataPoint);
			}
		}
		return childrenOut;	
	}

	//TODO make own class  too
	protected List<XSDPoint> processComplexExtType(XSDComplexExtType dataType) throws ParseException {
		List<XSDPoint> children = new ArrayList<XSDPoint>();
		if (!( dataType instanceof XSDComplexExtType)) {
			System.out.println("XSDDataComplexPoint fail- expecting XSDComplexExtType");
			LOGGER.error("XSDDataComplexPoint fail- expecting XSDComplexExtType");
			throw new ParseException();
		} 
		XSDDataPoint dataPoint = new XSDDataPoint(translator,this);
		//TODO I dont think we want to put the pointnamesuffix onto the XPATH
		dataPoint.setName(dataType.getBaseType().getPointNameSuffix());
		dataPoint.setXPath(xPath);
		dataPoint.setxPathSuffix("/text()");
		dataPoint.setLevel(createLevelSpecial());
		dataPoint.setDataType(dataType.getBaseType());
		children.add(dataPoint);
		for (XSDRenamedType renamedType : dataType.getTypes()) {
			if (renamedType.getXsdDataType() instanceof XSDComplexType) {
				LOGGER.error("XSDDataComplexPoint fail- expecting renamedType to be XSDComplexType");
				throw new ParseException();
				
			} else {
				dataPoint = new XSDDataPoint(translator,this);
				dataPoint.setName(renamedType.getName());
				dataPoint.setXPath(xPath);
				dataPoint.setxPathSuffix("/@"+dataPoint.getName());
				dataPoint.setLevel(createLevelSpecial());
				dataPoint.setDataType(renamedType.getXsdDataType());
				children.add(dataPoint);
			}
		}
		return children;
	}

	//TODO just terrible
	protected int createLevelSpecial() {

		if (isUnbounded) {
			return level+1;
		} 
		return level;
	}

	@Override
	public XSDDataType getType() {
		return type;
	}
	




}
