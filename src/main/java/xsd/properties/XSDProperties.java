package main.java.xsd.properties;

import java.util.ArrayList;

public class XSDProperties {
	
	private String startElementName;
	private ArrayList<String> columnFamilies;

	public String getStartElementName() {
		return startElementName;
	}

	public void setStartElementName(String startElementName) {
		this.startElementName = startElementName;
	}

	public ArrayList<String> getColumnFamilies() {
		return columnFamilies;
	}

	public void setColumnFamilies(ArrayList<String> columnFamilies) {
		this.columnFamilies = columnFamilies;
	}


}
