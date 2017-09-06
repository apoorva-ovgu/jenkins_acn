import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class XformWithXSL {    
	public static void main(String args[]) {
		
		xsl("menu.xml","menustyle.xsl","menuout.html");
		System.out.println("Done!");
	}
	
    public static void xsl(String xmlFile,  String styleFile, String transformedXml) {
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
        	System.out.println("File unavailable at given location: "+e);
        } catch (TransformerConfigurationException e) {
        	System.out.println("TransformerConfigurationException: "+e);
            // An error occurred in the XSL file
        } catch (TransformerException e) {            
            SourceLocator locator = e.getLocator();
            int col = locator.getColumnNumber();
            int line = locator.getLineNumber();
            String publicId = locator.getPublicId();
            String systemId = locator.getSystemId();
            System.out.println("Error at transforming column "+col+" on line "+line +"\nPublicID "+publicId+" on system "+systemId);
        }
        
        System.out.println("Done");
    }
}