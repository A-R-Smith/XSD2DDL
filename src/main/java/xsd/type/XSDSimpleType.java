package main.java.xsd.type;

public abstract class XSDSimpleType implements XSDDataType {
	protected String name;
	
	private XSDPrimitiveType primitiveType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public XSDPrimitiveType getPrimitiveType() {
		return primitiveType;
	}

	public void setPrimitiveType(XSDPrimitiveType primitiveType) {
		this.primitiveType = primitiveType;
	} 
	
	@Override
	public String getXPathType() {
		return "text()";
	}

	

}
