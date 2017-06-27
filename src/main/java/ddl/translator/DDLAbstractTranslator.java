package main.java.ddl.translator;

import java.util.ArrayList;

import main.java.ddl.TableDataPoint;
import main.java.ddl.typegen.DDLTypeGenerator;
import main.java.point.XSDComplexPoint;
import main.java.point.XSDDataPoint;
import main.java.point.XSDFamilyPoint;
import main.java.xsd.properties.XSDProperties;

public abstract class DDLAbstractTranslator implements DDLTranslator {
	
	protected XSDProperties props;
	protected ArrayList<TableDataPoint> tablePoints;
	protected DDLTypeGenerator typeGenerator;

	public DDLAbstractTranslator () {
		tablePoints = new ArrayList<TableDataPoint>();
	}
	
	@Override
	public ArrayList<TableDataPoint> getTableDataPoints() {
		return tablePoints;
	}

	@Override
	public void prettyPrint(XSDComplexPoint point) {	
	}
	@Override
	public void prettyPrint(XSDDataPoint point) {
	}

	@Override
	public void prettyPrint(XSDFamilyPoint point) {
	}
	
	@Override
	public boolean isStartElement(String elementName) {
		return props.getStartElementName().equals(elementName);
	}
	
	@Override
	public boolean isColumnFamily(String elementName) {
		return props.getColumnFamilies().contains(elementName);
	}
	
	@Override
	public void setTableProperies(XSDProperties properties) {
		this.props=properties;
		
	}
	
	@Override 
	public void setDataTypeGenerator(DDLTypeGenerator typeGenerator) {
		this.typeGenerator=typeGenerator;
	}
	
	protected String cleanColumnName(String string, int minCheckLength) {
		String longLeft = null;
		String longCheck = null;
		String longRight = null;

		int length = 0;
		
		
		
		for (int i = minCheckLength; i<string.length()-minCheckLength; i++){
			for (int j = i; j<string.length()-i;j++) {
				String left  = string.substring(0, j-i);
				String check = string.substring(j-i, j);
				String right = string.substring(j, string.length());
				if (right.contains(check)) {
					if (i>length) {
						longLeft=left;
						longCheck=check;
						longRight=right;
						length = i;
					} 
				}		
			}		
		}
		if (length>=minCheckLength) {
			if (Character.isUpperCase(longCheck.charAt(longCheck.length()-1))) {
				longRight = longCheck.charAt(longCheck.length()-1) + longRight;
				longCheck = longCheck.substring(0,longCheck.length()-1);
				
			}
			
			
			String newString =  
					longLeft
					+ longCheck 
					+ longRight.replaceAll(longCheck, "");

			return cleanColumnName(newString,minCheckLength);
		}
	
		return string;
	}
	
}
