package main.java.point;

import java.util.ArrayList;

import main.java.ddl.TableDataPoint;
import main.java.ddl.translator.DDLTranslator;
import main.java.exception.PrintException;

public abstract class XSDAbstractPoint implements XSDPoint {
	protected DDLTranslator translator; 
	protected XSDPoint parent;
	protected String pointName;
	protected int level;
	protected String xPath;
	protected String xPathSuffix;

	protected boolean isUnbounded;
	
	public XSDAbstractPoint (DDLTranslator translator, XSDPoint parent) {
		this.translator =translator;
		this.parent = parent;
	}
	
	
	public ArrayList<TableDataPoint> getTableDataPoints() throws PrintException {
		this.ddlPrint();
		
		return translator.getTableDataPoints();
	}
	
//	//TODO not a real function - LOOK OUT
	protected String createXPath(String name) {
		String out;
		if (parent!=null) {
			out = parent.getXPath()+"/"+name;
		} else {
			out = name;
		}
		return out;
	}
	
	protected int createLevel() {
		if (parent==null||translator.isStartElement(pointName)) {
			return 0;
		}

		if (parent.isUnbounded()&&!translator.isStartElement(parent.getName())) {
			return parent.getLevel()+1;
		} 
		return parent.getLevel();
	}
	
	public XSDPoint getParent() {
		return this.parent;
	}


	@Override
	public String getName() {
		return pointName;
	}
	
	@Override
	public void setName(String name) {
		this.pointName = name;
	}

	@Override
	public String getXPath() {
		return xPath;
	}

	@Override
	public void setXPath(String xPath) {
		this.xPath = xPath;
	}
	
	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public void setLevel(int level) {
		this.level=level;
	}
	
	@Override
	public DDLTranslator getTranslator() {
		return translator;
	}

	@Override
	public void setTranslator(DDLTranslator translator) {
		this.translator = translator;
	}
	
	@Override
	public void setIsUnbounded(boolean b) {
		this.isUnbounded = b;
		
	}
	
	@Override
	public boolean isUnbounded() {
		return this.isUnbounded;
	}
	
	@Override
	public String getxPathSuffix() {
		return xPathSuffix;
	}

	@Override
	public void setxPathSuffix(String xPathSuffix) {
		this.xPathSuffix = xPathSuffix;
	}

}
