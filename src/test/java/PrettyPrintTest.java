package test.java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

import main.java.ddl.TableDataPoint;
import main.java.ddl.translator.DDLArraySingleTranslator;
import main.java.ddl.translator.DDLArraySingleXTranslator;
import main.java.ddl.translator.DDLSimpleTranslator;
import main.java.ddl.translator.DDLSimpleXTranslator;
import main.java.ddl.typegen.DDLHiveTypeGenerator;
import main.java.ddl.typegen.DDLTypeGenerator;
import main.java.exception.ParseException;
import main.java.exception.PrintException;
import main.java.point.XSDPoint;
import main.java.xsd.properties.XSDProperties;
import main.java.xsd.reader.XSDReader;

import org.junit.Before;
import org.junit.Test;

public class PrettyPrintTest {
	private static final String prettyColumns = "%-90.90s %-180.180s";
	XSDProperties properties;
	private Writer writer;
	@Before
	public void setUp() throws Exception {
		
		properties = new XSDProperties();
		
		properties.setStartElementName("InvestmentVehicle");
		ArrayList<String> columnFamilies = new ArrayList<String>();
		columnFamilies.add("Operation");
		columnFamilies.add("TrailingPerformance");
		columnFamilies.add("HistoricalPerformance");
		columnFamilies.add("HistoricalOperation");
		columnFamilies.add("Portfolio");
		columnFamilies.add("SubAccount");
		columnFamilies.add("Strategy");
		columnFamilies.add("CustomPlan");
		columnFamilies.add("International");
		properties.setColumnFamilies(columnFamilies);
		
		
	}
	
	@Test
	public void testSmallDDL() throws ParseException, PrintException {
		System.out.println("SimpleTranslatorTest - Start testSmallXDDL");
		DDLSimpleTranslator translator = new DDLSimpleTranslator();
		DDLTypeGenerator typeGenerator = new DDLHiveTypeGenerator();
		translator.setDataTypeGenerator(typeGenerator);
		translator.setTableProperies(properties);
		XSDReader xsdReader = new XSDReader();
		xsdReader.setXSDFile(new File("src/test/data/DataWarehouse-3.6_test.xsd"));
		
		XSDPoint dataPointTree = xsdReader.createDataPointTree(translator);
		ArrayList<TableDataPoint> points = dataPointTree.getTableDataPoints();
		
		prettyPrint(points, "src/test/out/pretty/pretty_small_simple.txt");

	}


	@Test
	public void testFullDDL() throws ParseException, PrintException {
		System.out.println("SimpleTranslatorTest - Start testFullDDL");
		DDLSimpleTranslator translator = new DDLSimpleTranslator();
		DDLTypeGenerator typeGenerator = new DDLHiveTypeGenerator();
		translator.setDataTypeGenerator(typeGenerator);
		translator.setTableProperies(properties);
		XSDReader xsdReader = new XSDReader();
		xsdReader.setXSDFile(new File("src/test/data/DataWarehouse-3.6.xsd"));
		
		XSDPoint dataPointTree = xsdReader.createDataPointTree(translator);
		ArrayList<TableDataPoint> points = dataPointTree.getTableDataPoints();
		
		prettyPrint(points, "src/test/out/pretty/pretty_big_simple.txt");
	}

	@Test
	public void testFullDDL2() throws ParseException, PrintException {
		System.out.println("ArraySingleTranslatorTest - Start testFullDDL");
		DDLArraySingleTranslator translator = new DDLArraySingleTranslator();
		DDLTypeGenerator typeGenerator = new DDLHiveTypeGenerator();
		translator.setDataTypeGenerator(typeGenerator);
		translator.setTableProperies(properties);
		XSDReader xsdReader = new XSDReader();
		xsdReader.setXSDFile(new File("src/test/data/DataWarehouse-3.6.xsd"));
		
		XSDPoint dataPointTree = xsdReader.createDataPointTree(translator);
		ArrayList<TableDataPoint> points = dataPointTree.getTableDataPoints();
		
		prettyPrint(points, "src/test/out/pretty/pretty_big_array.txt");
	}
	
	@Test
	public void testFullDDL3() throws ParseException, PrintException {
		System.out.println("SimpleXTranslatorTest - Start testFullDDL");
		DDLSimpleXTranslator translator = new DDLSimpleXTranslator();
		DDLTypeGenerator typeGenerator = new DDLHiveTypeGenerator();
		translator.setDataTypeGenerator(typeGenerator);
		translator.setTableProperies(properties);
		XSDReader xsdReader = new XSDReader();
		xsdReader.setXSDFile(new File("src/test/data/DataWarehouse-3.6.xsd"));
		
		XSDPoint dataPointTree = xsdReader.createDataPointTree(translator);
		ArrayList<TableDataPoint> points = dataPointTree.getTableDataPoints();
		
		prettyPrint(points, "src/test/out/pretty/pretty_big_simplex.txt");
	}
	
	@Test
	public void testFullDDL4() throws ParseException, PrintException {
		System.out.println("ArraySingleXTranslatorTest - Start testFullDDL");
		DDLArraySingleXTranslator translator = new DDLArraySingleXTranslator();
		DDLTypeGenerator typeGenerator = new DDLHiveTypeGenerator();
		translator.setDataTypeGenerator(typeGenerator);
		translator.setTableProperies(properties);
		XSDReader xsdReader = new XSDReader();
		xsdReader.setXSDFile(new File("src/test/data/DataWarehouse-3.6.xsd"));
		
		XSDPoint dataPointTree = xsdReader.createDataPointTree(translator);
		ArrayList<TableDataPoint> points = dataPointTree.getTableDataPoints();
		
		prettyPrint(points, "src/test/out/pretty/pretty_big_arrayx.txt");
	}
	
	private void prettyPrint(ArrayList<TableDataPoint> points, String string) {
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(string)), "utf-8"));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (TableDataPoint point : points) {
			try {
				writer.write(String.format(prettyColumns,
						   point.getName(),
				           point.getxPath()) + "\r\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
