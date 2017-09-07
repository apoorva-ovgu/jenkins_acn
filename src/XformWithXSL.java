import java.io.*;
import java.time.LocalDateTime;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;


public class XformWithXSL {    
	public static void main(String args[]) {
		
		File folder = new File("reports/");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile() && (listOfFiles[i].getName().indexOf(".xml")==listOfFiles[i].getName().length()-4)) {
		        System.out.println("Processing::=> " + "reports/"+ listOfFiles[i].getName());
		        xsl("reports/"+listOfFiles[i].getName(),"menustyle.xsl", "reports/"+listOfFiles[i].getName()+"_transformed.html",(i+1));
		        
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory found instead of XML file: " + listOfFiles[i].getName());
		      }
		    }
	}
	
    public static void xsl(String xmlFile,  String styleFile, String transformedXml, int indexOfXMLfile) {
    	String statusOfOp = "";
    	
        try {        	        	
            // Create transformer factory and templates, and initialize transformer
            TransformerFactory factory = TransformerFactory.newInstance();
            Templates template = factory.newTemplates(new StreamSource(
                new FileInputStream(styleFile)));            
            Transformer xformer = template.newTransformer();
           
            Source source = new StreamSource(new FileInputStream(xmlFile));
            Result result = new StreamResult(new FileOutputStream(transformedXml));            
            xformer.transform(source, result);
            
        } catch (FileNotFoundException e) {
        	statusOfOp = "FAILED TRANSFORMATION: File unavailable at given location: "+e;
        } catch (TransformerConfigurationException e) {
        	statusOfOp = "FAILED TRANSFORMATION: TransformerConfigurationException: "+e;        	
        } catch (TransformerException e) {                       
        	statusOfOp = "Error with XML format/XSL in file: "+xmlFile+"....... Details: "+e;
        }
        if (statusOfOp=="")
        		statusOfOp = "Successful transformation";
        checkResults(indexOfXMLfile, xmlFile, statusOfOp);
    }
    
    public static void checkResults(int indexOfXMLfile, String inputxml, String statusOfOp) {
        PrintWriter pw;
		try {
			String resultFile = "resultOfXSLT.csv"; 
			String shortStatus = "";
			if (statusOfOp == "Successful transformation")
				shortStatus = "Success";
			else
				shortStatus = "FAILED TRANSFORMATION";
			
			boolean fileExists = true;
			
			File f1 = new File(resultFile);
	         if(!f1.exists()) {
	            f1.createNewFile();
	            fileExists = false;
	         } 
	         FileWriter fileWritter = new FileWriter(f1.getName(),true);
	         BufferedWriter bw = new BufferedWriter(fileWritter);
	         
	         if(!fileExists) {
	        	 String header = ("srNo,Timestamp,XML file,Status,Expected Result,Current Result,\n");
		         bw.write(header);
	         }
	         
	         LocalDateTime t = LocalDateTime.now();

		     bw.write(indexOfXMLfile+","+t+","+inputxml+","+shortStatus+",Successful transformation,"+statusOfOp+",\n");	   
		        
	         bw.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}