package main.java.point;

import java.util.ArrayList;

import main.java.ddl.TableDataPoint;
import main.java.ddl.translator.DDLTranslator;
import main.java.exception.ParseException;
import main.java.exception.PrintException;
import main.java.xsd.reader.XSDElement;
import main.java.xsd.type.XSDDataType;

public interface XSDPoint {

	public void parse(XSDElement element) throws ParseException;
	public void prettyPrint(String xPath, int level);
	public XSDPoint getParent();
	public XSDDataType getType();
	public void ddlPrint() throws PrintException;
	public String getName();
	public void setName(String name);
	public String getXPath();
	public void setXPath(String xPath);
	public int getLevel();
	public void setLevel(int level);
	public ArrayList<TableDataPoint> getTableDataPoints() throws PrintException;
	public void setIsUnbounded(boolean b);
	public boolean isUnbounded();
	public DDLTranslator getTranslator();
	public void setTranslator(DDLTranslator translator);
	public String getxPathSuffix();
	public void setxPathSuffix(String xPathSuffix);
	
}
