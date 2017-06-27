package main.java.ddl.translator;

import java.util.ArrayList;

import main.java.ddl.TableDataPoint;
import main.java.ddl.typegen.DDLTypeGenerator;
import main.java.exception.PrintException;
import main.java.point.XSDComplexPoint;
import main.java.point.XSDDataPoint;
import main.java.point.XSDFamilyPoint;
import main.java.xsd.properties.XSDProperties;

public interface DDLTranslator {

	public void prettyPrint(XSDComplexPoint point);

	public void ddlPrint(XSDComplexPoint point);
	
	
	public void prettyPrint(XSDDataPoint point);
	
	public void ddlPrint(XSDDataPoint point) throws PrintException;
	
	
	public void prettyPrint(XSDFamilyPoint point);
	
	public void ddlPrint(XSDFamilyPoint point);
	
	
	public boolean isStartElement(String elementName);
	
	public boolean isColumnFamily(String elementName);
	
	
	public void setTableProperies(XSDProperties properties);
	
	public ArrayList<TableDataPoint> getTableDataPoints();
	
	public void setDataTypeGenerator(DDLTypeGenerator typeGenerator);


}
