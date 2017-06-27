package main.java.ddl.translator;

import main.java.ddl.TableDataPoint;
import main.java.point.XSDComplexPoint;
import main.java.point.XSDDataPoint;
import main.java.xsd.type.XSDComplexExtType;

public class DDLSimpleXTranslator extends DDLSimpleTranslator implements DDLTranslator {

	
//	//TODO this needs updated to deal with xpathsuffix
//	@Override
//	public void ddlPrint(XSDComplexPoint point) {	
//		if (point.getLevel()==0&&
//				point.getXPath().contains(props.getStartElementName())) {
//			if (point.getType() instanceof XSDComplexExtType) {
//				TableDataPoint tableDataPoint = new TableDataPoint();
//				tableDataPoint.setName(createDdlString(point.getXPath()+"_raw"));
//			    tableDataPoint.setxPath(removeStartElementFromXPath(point.getXPath()));
//			    tableDataPoint.setType("string");
//			    tableDataPoint.setComplex(false);
//				tablePoints.add(tableDataPoint);
//			}
//		}	
//	}
//	
//	@Override
//	public void ddlPrint(XSDDataPoint point) {
////		if (!(point.getParent().getType() instanceof XSDComplexType)) {
//			if (!(point.getParent().getType() instanceof XSDComplexExtType)) {
//				super.ddlPrint(point);
//			}
////		}
//	}
}
