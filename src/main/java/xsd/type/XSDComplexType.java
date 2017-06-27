package main.java.xsd.type;

import java.util.ArrayList;

public class XSDComplexType implements XSDDataType {
	private String name;
	private ArrayList<XSDRenamedType> types;
	
	public XSDComplexType () {
		types = new ArrayList<XSDRenamedType>();
	}
	
	public void addType (XSDRenamedType type) {
		types.add(type);
	}
	
	public ArrayList<XSDRenamedType> getTypes () {
		return types;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name=name;
	}

//	@Override
//	public String getDDLType() {
//		return null;
//	}

	@Override
	public String getXPathType() {
		return null;
	}

	@Override
	public String getPointNameSuffix() {
		return null;
	}
	
	

}
