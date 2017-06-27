package main.java.ddl.translator;

import main.java.ddl.TableDataPoint;
import main.java.exception.PrintException;
import main.java.point.XSDComplexPoint;
import main.java.point.XSDDataPoint;
import main.java.point.XSDFamilyPoint;
import main.java.point.XSDPoint;

public class DDLSimpleTranslator extends DDLAbstractTranslator implements DDLTranslator {

	protected int minCheckLength;
	
	public DDLSimpleTranslator () {
		minCheckLength=7;
	}
	
	@Override
	public void ddlPrint(XSDComplexPoint point) {	
		if (point.getLevel()==0&&
				point.isUnbounded()&&
				point.getXPath().contains(props.getStartElementName())) {
			TableDataPoint tableDataPoint = new TableDataPoint();
			tableDataPoint.setName(createPointName(point)+"_raw");
		    tableDataPoint.setxPath(point.getXPath()+point.getxPathSuffix());
		    tableDataPoint.setComplex(true);
		    tableDataPoint.setType("string");
			tablePoints.add(tableDataPoint);
		}
	}
	
	@Override
	public void ddlPrint(XSDFamilyPoint point) {
		if (point.isUnbounded()&&
					point.getXPath().contains(props.getStartElementName())&&
						!point.getName().equals(props.getStartElementName())) {
			TableDataPoint tableDataPoint = new TableDataPoint();
			tableDataPoint.setName(createPointName(point)+"_raw");
		    tableDataPoint.setxPath(point.getXPath()+point.getxPathSuffix());
		    tableDataPoint.setComplex(true);
		    tableDataPoint.setType("string");
			tablePoints.add(tableDataPoint);
		}
		
	}


	@Override
	public void ddlPrint(XSDDataPoint point) throws PrintException {
		if (point.getLevel()==0&&
				point.getXPath().contains(props.getStartElementName())) {
			TableDataPoint tableDataPoint = new TableDataPoint();
			tableDataPoint.setName(createPointName(point));
		    tableDataPoint.setxPath(point.getXPath()+point.getxPathSuffix());
		    tableDataPoint.setType(typeGenerator.generateDataTypeString(point.getDataType()));
		    tableDataPoint.setComplex(false);
		    tablePoints.add(tableDataPoint);
		}
		
	}

	
	protected String createPointName(XSDPoint point) {
		String printString = point.getXPath();
		
		if (printString.contains(props.getStartElementName())) {
			printString = printString.substring(printString.indexOf(props.getStartElementName()));
		}

		String parseSubString = props.getStartElementName()+"_";
		if (parseSubString.length()<=printString.length()) {
			printString = printString.substring(parseSubString.length());
		}
		printString = printString.replaceAll("/", "_");
		for (String family : props.getColumnFamilies()) {
			if (printString.startsWith(family)) {
				printString = printString.substring((family+"_").length());
			}
		}
		printString = cleanColumnName(printString,minCheckLength);
		
		printString = printString.replaceAll("__", "_");
		printString = printString.replaceAll("__", "_");
		
		if (point.getxPathSuffix().contains("@")) {
			String suffix = point.getxPathSuffix().replace("@", "").replace("/", "");
			if (!suffix.startsWith("_")) {
				suffix = "_" + suffix;
			}
			printString = printString + suffix;
		}
		
		return printString;
	}
//	
//	protected String removeStartElementFromXPath (String xPath) {
//		if (xPath.contains(props.getStartElementName())) {
//			xPath = xPath.substring(xPath.indexOf(props.getStartElementName()));
//		}
//		
//		return xPath;
//	}
}
