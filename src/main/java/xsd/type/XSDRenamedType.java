package main.java.xsd.type;

public class XSDRenamedType implements XSDDataType {
	
	private String name;
	
	private XSDDataType xsdDataType;

	public XSDDataType getXsdDataType() {
		return xsdDataType;
	}

	public void setXsdDataType(XSDDataType xsdDataType) {
		this.xsdDataType = xsdDataType;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name=name;
	}
//
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
