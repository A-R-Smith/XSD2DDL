package main.java.xsd.type;

public class XSDStringType extends XSDSimpleType {
	private int length;

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

//	@Override
//	public String getDDLType() {
//		return "VARCHAR("+length+")";
//	}

	@Override
	public String getPointNameSuffix() {
		return "-string";
	}

}
