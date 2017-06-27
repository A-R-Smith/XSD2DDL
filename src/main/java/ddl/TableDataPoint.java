package main.java.ddl;

public class TableDataPoint {
	private String name;
	private String type;
	private String xPath;
	private boolean isComplex;
	
	public boolean isComplex() {
		return isComplex;
	}
	public void setComplex(boolean isComplex) {
		this.isComplex = isComplex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getxPath() {
		return xPath;
	}
	public void setxPath(String xPath) {
		this.xPath = xPath;
	}
}
