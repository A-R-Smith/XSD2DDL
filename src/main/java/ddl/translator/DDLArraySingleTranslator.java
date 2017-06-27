package main.java.ddl.translator;

import main.java.ddl.TableDataPoint;
import main.java.exception.PrintException;
import main.java.point.XSDComplexPoint;
import main.java.point.XSDDataPoint;
import main.java.point.XSDFamilyPoint;

public class DDLArraySingleTranslator extends DDLSimpleTranslator implements DDLTranslator {
	
	public DDLArraySingleTranslator () {
		minCheckLength=13;
	}
	
	@Override
	public void ddlPrint(XSDComplexPoint point) {	
		if (point.getLevel()==1&&
				point.isUnbounded()&&
					point.getXPath().contains(props.getStartElementName())) {
				TableDataPoint tableDataPoint = new TableDataPoint();
				tableDataPoint.setName(createPointName(point)+"_rawarray");
			    tableDataPoint.setxPath(point.getXPath()+point.getxPathSuffix());
			    tableDataPoint.setType("Array<string>");
			    tableDataPoint.setComplex(true);
			    tablePoints.add(tableDataPoint); 
		}		
	}
	
	@Override
	public void ddlPrint(XSDFamilyPoint point) {
		if (point.getLevel()==1&&
				point.isUnbounded()&&
					point.getXPath().contains(props.getStartElementName())&&
						!point.getName().equals(props.getStartElementName())) {
			TableDataPoint tableDataPoint = new TableDataPoint();
			tableDataPoint.setName(createPointName(point)+"_array");
		    tableDataPoint.setxPath(point.getXPath()+point.getxPathSuffix());
		    tableDataPoint.setType("Array<string>");
		    tableDataPoint.setComplex(true);
			tablePoints.add(tableDataPoint);
		}
	}


	@Override
	public void ddlPrint(XSDDataPoint point) throws PrintException {
		if (point.getXPath().contains(props.getStartElementName())) {
			if (point.getLevel()==0) {
				TableDataPoint tableDataPoint = new TableDataPoint();
				tableDataPoint.setName(createPointName(point));
			    tableDataPoint.setxPath(point.getXPath()+point.getxPathSuffix());
			    tableDataPoint.setType(typeGenerator.generateDataTypeString(point.getDataType()));
			    tableDataPoint.setComplex(false);
			    tablePoints.add(tableDataPoint);
			} else if (point.getLevel()==1) {
				TableDataPoint tableDataPoint = new TableDataPoint();
				tableDataPoint.setName(createPointName(point)+"_array");
			    tableDataPoint.setxPath(point.getXPath()+point.getxPathSuffix());
			    tableDataPoint.setType("Array<"+typeGenerator.generateDataTypeString(point.getDataType())+">");
			    tableDataPoint.setComplex(true);
			    tablePoints.add(tableDataPoint);
			}
		}
		
	}

}
