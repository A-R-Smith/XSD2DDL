package main.java.ddl.typegen;

import main.java.exception.PrintException;
import main.java.xsd.type.XSDDataType;

public interface DDLTypeGenerator {
	
	public String generateDataTypeString (XSDDataType type) throws PrintException;

}
