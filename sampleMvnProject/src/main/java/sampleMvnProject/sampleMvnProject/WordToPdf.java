package sampleMvnProject.sampleMvnProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;



public class WordToPdf {
    public static void main(String[] args) {

    Document document = new Document();

     try {  
         System.out.println("Starting the test");  

         XWPFDocument doc = new XWPFDocument(new FileInputStream("C:\\Users\\km185264\\OneDrive - NCR Corporation\\Desktop\\KrishnaSusritha_Resume.docx"));  
         // using XWPFWordExtractor Class
         XWPFWordExtractor we = new XWPFWordExtractor(doc);
         String k = we.getText();

          OutputStream fileForPdf = new FileOutputStream(new File("C:\\Users\\km185264\\OneDrive - NCR Corporation\\Desktop\\KrishnaSusritha_Resume.pdf"));
          

          PdfWriter.getInstance(document, fileForPdf);

          document.open();

          document.add(new Paragraph(k));

          we.close();
          document.close();
          fileForPdf.close();


         System.out.println("Document testing completed");  
     } catch (Exception e) {  
         System.out.println("Exception during test");  
         e.printStackTrace();  
     } finally {  
                     // close the document  
        document.close();  
        
                 }  
     }  

}
