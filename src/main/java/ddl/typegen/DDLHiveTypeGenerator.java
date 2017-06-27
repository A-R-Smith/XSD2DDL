package main.java.ddl.typegen;

import org.apache.log4j.Logger;

import main.java.exception.PrintException;
import main.java.xsd.type.XSDDataType;
import main.java.xsd.type.XSDDecimalType;
import main.java.xsd.type.XSDPrimitiveType;
import main.java.xsd.type.XSDStringType;

public class DDLHiveTypeGenerator implements DDLTypeGenerator {
	
	private static final Logger LOGGER = Logger.getLogger(DDLHiveTypeGenerator.class);

	@Override
	public String generateDataTypeString(XSDDataType type) throws PrintException {
		if (type instanceof XSDStringType) {
			return "string";//"VARCHAR("+((XSDStringType)type).getLength()+")";
		} else if (type instanceof XSDDecimalType) {
			return  "DECIMAL";
		} else if (type instanceof XSDPrimitiveType) {
			switch (type.getName()) {
				case "xs:string": return "string";
				case "xs:decimal": return "decimal";
				case "xs:boolean": return "boolean";
				case "xs:integer": return "tinyint";	
				case "xs:double": return "float";
				case "xs:token": return "string";
				case "xs:date": return "string";
//				case "xs:unsignedByte": return "VARCHAR(1)";
				case "xs:unsignedByte": return "string";
			} 	
		} else {
			LOGGER.error("DDLHiveTypeGenerator invalid data type: "+ type);
			throw new PrintException();
		}
		
		
		return null;
	}

}
