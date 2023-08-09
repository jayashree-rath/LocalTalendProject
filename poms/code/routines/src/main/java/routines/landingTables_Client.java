package routines;

/*
 * user specification: the function's comment should contain keys as follows: 1. write about the function's comment.but
 * it must be before the "{talendTypes}" key.
 * 
 * 2. {talendTypes} 's value must be talend Type, it is required . its value should be one of: String, char | Character,
 * long | Long, int | Integer, boolean | Boolean, byte | Byte, Date, double | Double, float | Float, Object, short |
 * Short
 * 
 * 3. {Category} define a category for the Function. it is required. its value is user-defined .
 * 
 * 4. {param} 's format is: {param} <type>[(<default value or closed list values>)] <name>[ : <comment>]
 * 
 * <type> 's value should be one of: string, int, list, double, object, boolean, long, char, date. <name>'s value is the
 * Function's parameter name. the {param} is optional. so if you the Function without the parameters. the {param} don't
 * added. you can have many parameters for the Function.
 * 
 * 5. {example} gives a example for the Function. it is optional.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class landingTables_Client {

 public static String createTableStmt(String filepath,String tableName,String ddlpath,String fileName ) throws IOException {
	  BufferedReader objReader = null;
	  FileWriter writer = new FileWriter(ddlpath+fileName,true);
	  
	  try {
		String strCurrentLine;

	    objReader = new BufferedReader(new FileReader(filepath));

	    strCurrentLine = objReader.readLine();
	   	strCurrentLine = strCurrentLine.substring(0, strCurrentLine.indexOf("@~@"));
		strCurrentLine = strCurrentLine.replace("$|$", "~");
	   	writer.write("IF OBJECT_ID(N'" + tableName + "', N'U') IS NOT NULL"
	   			+ System.lineSeparator() + "DROP TABLE" + tableName + ";" + System.lineSeparator());
	   	writer.write("CREATE TABLE "+ tableName + " (" + System.lineSeparator());
	    ArrayList<String> aList= new ArrayList<String>(Arrays.asList(strCurrentLine.split("~")));
	    for(int i=0;i<aList.size();i++)
	    {
	        if (aList.get(i).substring(0, 4).equals("IND_") && !aList.get(i).substring(0, 4).equals("RTF_") && i == aList.size() - 1) {
	        	writer.write("[" + aList.get(i).replace("IND_", "") + "]" +" BIGINT );" + System.lineSeparator());
	        }
	        else if (!aList.get(i).substring(0, 4).equals("IND_") && !aList.get(i).substring(0, 4).equals("RTF_") && i == aList.size() - 1) {
	        	writer.write("[" + aList.get(i) + "]" +" VARCHAR(255) );" + System.lineSeparator());
	        }
	        else if (!aList.get(i).substring(0, 4).equals("IND_") && !aList.get(i).substring(0, 4).equals("RTF_") && i != aList.size() - 1) {
	        	writer.write("[" + aList.get(i) + "]" +" VARCHAR(255) ," + System.lineSeparator());
	        }
	        else if(aList.get(i).substring(0, 4).equals("IND_") && !aList.get(i).substring(0, 4).equals("RTF_") && i != aList.size()- 1) {
	        	writer.write("[" + aList.get(i).replace("IND_", "") + "]" +" BIGINT ," + System.lineSeparator());
	        }
	        if (aList.get(i).substring(0, 4).equals("RTF_") && i == aList.size() - 1) {
	        	writer.write("[" + aList.get(i).replace("RTF_", "") + "]" +" VARCHAR(MAX) );" + System.lineSeparator());
	        }
	        else if (aList.get(i).substring(0, 4).equals("RTF_") && i != aList.size() - 1) {
	        	writer.write("[" + aList.get(i).replace("RTF_", "") + "]" +" VARCHAR(MAX) ," + System.lineSeparator());
	        }
	    }
     writer.write(System.lineSeparator());
     writer.close();
	  } catch (IOException e) {

	   e.printStackTrace();

	  } finally {

	   try {
	    if (objReader != null)
	     objReader.close();
	   } catch (IOException ex) {
	    ex.printStackTrace();
	    
	   }
	  }
	  return "";
	 }
	}

