package main.java.xsd.type;

public class XSDPrimitiveType implements XSDDataType {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	@Override
//	public String getDDLType() {
////		switch (this.name) {
////			case "xs:string": return "string";
////			case "xs:decimal": return "decimal";
////			case "xs:boolean": return "boolean";
////			case "xs:integer": return "tinyint";	
////			case "xs:double": return "float";
////			case "xs:token": return "string";
////			case "xs:date": return "date";
////			case "xs:unsignedByte": return "character";
////		} 
//		switch (this.name) {
//			case "xs:string": return "string";
//			case "xs:decimal": return "decimal";
//			case "xs:boolean": return "boolean";
//			case "xs:integer": return "tinyint";	
//			case "xs:double": return "float";
//			case "xs:token": return "string";
//			case "xs:date": return "string";
//			case "xs:unsignedByte": return "VARCHAR(1)";
//		} 	
//		return null;
//	}

	@Override
	public String getXPathType() {
		
//		switch (this.name) {
//			case "xs:string": return "text()";
//			case "xs:decimal": return "decimal";
//			case "xs:boolean": return "boolean";
//			case "xs:integer": return "tinyint";	
//			case "xs:double": return "float";
//			case "xs:token": return "string";
//			case "xs:date": return "date";
//			case "xs:unsignedByte": return "character";
//		} 
		return "text()";
	}

	@Override
	public String getPointNameSuffix() {
		return "-"+name.replaceFirst("xs:", "");
	}
}
