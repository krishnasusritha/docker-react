package sampleMvnProject.sampleMvnProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class ApachePOISample {
    public static void main(String[] args) throws Exception {
        String inputFile="C:\\Users\\km185264\\OneDrive - NCR Corporation\\Desktop\\KrishnaSusritha_Resume.docx";
        String outputFile="C:\\\\Users\\\\km185264\\\\OneDrive - NCR Corporation\\\\Desktop\\\\KrishnaSusritha_Resume.pdf";
        if (args != null && args.length == 2) {
          inputFile=args[0];
          outputFile=args[1];
        }
        System.out.println("inputFile:" + inputFile + ",outputFile:"+ outputFile);
        FileInputStream in=new FileInputStream(inputFile);
        XWPFDocument document=new XWPFDocument(in);
        File outFile=new File(outputFile);
        OutputStream out=new FileOutputStream(outFile);
        PdfOptions options=null;
        PdfConverter.getInstance().convert(document,out,options);
      }

}
