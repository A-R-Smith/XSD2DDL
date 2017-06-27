package main.java.point;

import org.apache.log4j.Logger;

import main.java.ddl.translator.DDLTranslator;
import main.java.ddl.typegen.DDLHiveTypeGenerator;
import main.java.exception.ParseException;
import main.java.exception.PrintException;
import main.java.xsd.reader.XSDElement;
import main.java.xsd.type.XSDDataType;
import main.java.xsd.type.XSDTypeDictionary;

public class XSDDataPoint extends XSDAbstractPoint implements XSDPoint{
	
	private static final Logger LOGGER = Logger.getLogger(XSDDataPoint.class);

	protected XSDDataType dataType;
	protected String ddlDataType;
	
	public XSDDataPoint(DDLTranslator translator, XSDPoint parent) {
		super(translator,parent);
	}
	
	@Override
	public void parse (XSDElement element) throws ParseException {
		if (element.hasAttribute("name")||element.hasAttribute("type")) {
			pointName = element.getAttribute("name");
			xPath = createXPath(pointName);
			
			xPathSuffix = "/text()";
			
			level = createLevel();
			
			dataType = XSDTypeDictionary.getInstance().getXSDType(element.getAttribute("type"));
			
		} else {
			LOGGER.error("XSDDataPoint fail, invalid data type: "+ element.getName());
			throw new ParseException();
		}
	}


	@Override
	public void prettyPrint(String xPath, int level) {
		translator.prettyPrint(this);
	}

	@Override
	public void ddlPrint() throws PrintException {
		translator.ddlPrint(this);		
	}
	
	public XSDDataType getDataType() {
		return dataType;
	}

	public void setDataType(XSDDataType dataType) {
		this.dataType = dataType;
	}	

	@Override
	public XSDDataType getType() {
		return dataType;
	}




}
