package main.java.xsd.type;

import java.util.ArrayList;

import main.java.xsd.reader.XSDElement;

public class XSDTypeDictionary {
	private ArrayList<XSDSimpleType> xsdSimple;
	private ArrayList<XSDComplexType> xsdComplex;
	private ArrayList<XSDPrimitiveType> xsdPrimitiveType;
	private static XSDTypeDictionary instance;

   private XSDTypeDictionary () {
   }
   
   public static XSDTypeDictionary getInstance() {
      if(instance == null) {
         instance = new XSDTypeDictionary();
      }
      return instance;
   }
	
	
   public void createDictionary (ArrayList<XSDElement> typeElementList) {
		xsdSimple = new ArrayList<XSDSimpleType>();
		xsdPrimitiveType = new ArrayList<XSDPrimitiveType>();
		xsdComplex = new ArrayList<XSDComplexType>();
		
		for (XSDElement element : typeElementList) {
			if (element.getName().equals("xs:simpleType")) {
				
				
				parseSimpleType(element);
				
				
			} else if (element.getName().equals("xs:complexType")) {
				
				parseComplexType(element);
				
			} else {
				System.out.println("TypeDictionary: Throwing out node type: " + element.getName());
			}
		}
//		System.out.println("TypeDictionary: created successfully");
   }
   
   private void parseSimpleType(XSDElement parentNode) {
		String typeName = parentNode.getAttribute("name");
//		System.out.println("Parsing simpleType: " +typeName);
		
		String primitiveTypeName = "";
		
		for (XSDElement child : parentNode.getChildren()) {
			if (child.getName().equals("xs:restriction")) {
				primitiveTypeName = child.getAttribute("base");
				
				if (primitiveTypeName.equals("xs:decimal")) {
					XSDDecimalType type = new XSDDecimalType();
					type.setName(typeName);
					type.setPrimitiveType((XSDPrimitiveType) getXSDType(primitiveTypeName));	
					type.setHasMinInclusive(false);
					
					for (XSDElement grandChild : child.getChildren()) {
						if (grandChild.getName().equals("xs:totalDigits")) {
							type.setTotalDigits(Integer.parseInt(grandChild.getAttribute("value")));
							
						} else if (grandChild.getName().equals("xs:fractionDigits")) {
							type.setFractionDigits(Integer.parseInt(grandChild.getAttribute("value")));
							
						} else if (grandChild.getName().equals("xs:minInclusive")) {
							type.setHasMinInclusive(true);
							type.setMinInclusive(Integer.parseInt(grandChild.getAttribute("value")));
						} else {
							System.out.println("TypeDictionary - fail simpleType");
							System.exit(-1);
						}
						
					}
					xsdSimple.add(type);
					
				} else if (primitiveTypeName.equals("xs:string") || primitiveTypeName.equals("xs:token")) {
					XSDStringType type = new XSDStringType();
					type.setName(typeName);
					type.setPrimitiveType((XSDPrimitiveType) getXSDType(primitiveTypeName));	
					
					for (XSDElement grandChild : child.getChildren()) {
						if (grandChild.getName().equals("xs:maxLength")) {
							type.setLength(Integer.parseInt(grandChild.getAttribute("value")));
						} else {
							System.out.println("TypeDictionary - fail simpleType");
							System.exit(-1);
						}	
						
					}
					xsdSimple.add(type);
				}
	
			} else {
				System.out.println("TypeDictionary - fail simpleType");
				System.exit(-1);
			}
			
		
		}
		
	}

	private void parseComplexType(XSDElement parentNode) {
		
		String typeName = parentNode.getAttribute("name");
//		System.out.println("Parsing complexType: " +typeName);	
		for (XSDElement child : parentNode.getChildren()) {
			if (child.getName().equals("xs:simpleContent")) {
				XSDComplexExtType complexType = new XSDComplexExtType();
				complexType.setName(typeName);
				
				for (XSDElement grandChild : child.getChildren()) {
					
					if (grandChild.getName().equals("xs:extension")) {			
						String baseTypeName = grandChild.getAttribute("base");
						complexType.setBaseType(getXSDType(baseTypeName)); 
						for (XSDElement greatGrandChild : grandChild.getChildren()) {
							if (greatGrandChild.getName().equals("xs:attribute")) {
								XSDRenamedType renamedType = new XSDRenamedType();
								renamedType.setName(greatGrandChild.getAttribute("name"));
								renamedType.setXsdDataType(getXSDType(greatGrandChild.getAttribute("type")));
								complexType.addType(renamedType);
							} else {
								System.out.println("TypeDictionary - fail complexType");
								System.exit(-1);
							}
						}
						
					} else {
						System.out.println("TypeDictionary - fail complexType");
						System.exit(-1);
					}
				}
				xsdComplex.add(complexType);
	
			} else if (child.getName().equals("xs:sequence")) {
				XSDComplexType complexType = new XSDComplexType();
				complexType.setName(typeName);
				for (XSDElement grandChild : child.getChildren())  {		
					if (grandChild.getName().equals("xs:element")) {
						XSDRenamedType renamedType = new XSDRenamedType();
						renamedType.setName(grandChild.getAttribute("name"));
						renamedType.setXsdDataType(getXSDType(grandChild.getAttribute("type")));
						complexType.addType(renamedType);
					} else {
						System.out.println("TypeDictionary - fail complexType");
						System.exit(-1);
					}
				}
				xsdComplex.add(complexType);
			} else {
				System.out.println("TypeDictionary - fail complexType");
				System.exit(-1);
			}
		}
		
	}
	
	public XSDDataType getXSDType(String elementType) {
		for (XSDPrimitiveType type : xsdPrimitiveType) {
			if (type.getName().equals(elementType)) {
				return type;
			}	
		}
		
		if (elementType.startsWith("xs:")) {
			XSDPrimitiveType type = new XSDPrimitiveType();
			type.setName(elementType);
			xsdPrimitiveType.add(type);
			return type;
		}
			
		for (XSDSimpleType type : xsdSimple) {
			if (type.getName().equals(elementType)) {
				return type;
			}	
		}
		
		for (XSDComplexType type : xsdComplex) {
			if (type.getName().equals(elementType)) {
				return type;
			}	
		}
		System.out.println("TypeDictionary - fail getXSDType - unknown type: " +elementType);
		System.exit(-1);
		return null;
	}

	public boolean isComplexType(String elementType) {
		for (XSDComplexType type : xsdComplex) {
			if (type.getName().equals(elementType)) {
				return true;
			}	
		}
		return false;
	}

}
