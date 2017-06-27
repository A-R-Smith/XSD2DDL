package main.java.ddl.typegen;

import org.apache.log4j.Logger;

import main.java.exception.PrintException;
import main.java.xsd.type.XSDDataType;
import main.java.xsd.type.XSDDecimalType;
import main.java.xsd.type.XSDPrimitiveType;
import main.java.xsd.type.XSDStringType;

public class DDLBigSQLTypeGenerator implements DDLTypeGenerator {
	
	private static final Logger LOGGER = Logger.getLogger(DDLBigSQLTypeGenerator.class);

	@Override
	public String generateDataTypeString(XSDDataType type) throws PrintException {
		
		if (type instanceof XSDStringType) {
			return "VARCHAR("+((XSDStringType)type).getLength()+")";
		} else if (type instanceof XSDDecimalType) {
			return  "DECIMAL("
			+((XSDDecimalType)type).getTotalDigits()+","
			+((XSDDecimalType)type).getFractionDigits()+")";
		} else if (type instanceof XSDPrimitiveType) {
			switch (type.getName()) {
				case "xs:string": return "string";
				case "xs:decimal": return "decimal";
				case "xs:boolean": return "boolean";
				case "xs:integer": return "tinyint";	
				case "xs:double": return "float";
				case "xs:token": return "string";
				case "xs:date": return "string";
				case "xs:unsignedByte": return "VARCHAR(1)";
			} 	
		} else {
			LOGGER.error("DDLBigSQLTypeGenerator invalid data type: "+ type);
			throw new PrintException();
		}
		
		
		return null;
	}

}
