package main.java.ddl.translator;

import main.java.ddl.TableDataPoint;
import main.java.point.XSDComplexPoint;
import main.java.point.XSDDataPoint;
import main.java.xsd.type.XSDComplexExtType;

public class DDLArraySingleXTranslator extends DDLArraySingleTranslator implements DDLTranslator {
	
	//TODO this needs updated to deal with xpath suffix
//	@Override
//	public void ddlPrint(XSDComplexPoint point) {	
//		if (point.getXPath().contains(props.getStartElementName())) {
//			if (point.getLevel()==0) {
//				if (point.getType() instanceof XSDComplexExtType) {
//					TableDataPoint tableDataPoint = new TableDataPoint();
//					tableDataPoint.setName(createDdlString(point.getXPath()+"_raw"));
//				    tableDataPoint.setxPath(removeStartElementFromXPath(point.getXPath()));
//				    tableDataPoint.setComplex(false);
//				    tableDataPoint.setType("string");
//					tablePoints.add(tableDataPoint);
//				}
//			} else if (point.getLevel()==1) {
//				TableDataPoint tableDataPoint = new TableDataPoint();
//				tableDataPoint.setName(createDdlString(point.getXPath()+"_rawarray"));
//			    tableDataPoint.setxPath(removeStartElementFromXPath(point.getParent().getXPath()));
//			    tableDataPoint.setComplex(true);
//			    tableDataPoint.setType("Array<String>");
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
