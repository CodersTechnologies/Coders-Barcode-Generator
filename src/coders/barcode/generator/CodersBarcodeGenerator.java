/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coders.barcode.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
/**
 *
 * @author sunny
 */
public class CodersBarcodeGenerator {
    
    public static final String DEST = "results/barcodes/barcode_in_table.pdf";
   
    public static PdfPCell createBarcode(PdfWriter writer, String code) throws DocumentException, IOException {
        //BarcodeEAN barcode = new BarcodeEAN();
        Barcode39 barcode=new Barcode39();
        //barcode.setCodeType(Barcode.EAN8);//**NOTE: Use this only with BarcodeEAN
        barcode.setCode(code);
        PdfPCell cell = new PdfPCell(barcode.createImageWithBarcode(writer.getDirectContent(), BaseColor.BLACK, BaseColor.GRAY), true);
        cell.setPadding(3);//To set spacing within cell before and after barcode
        return cell;
    }
    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfPTable table = new PdfPTable(6);//To set number of columns in a row
        table.setWidthPercentage(100);
        
        //To set range of barcodes upto which codes are to be generated
        for (int i = 1; i <= 100; i++) {
            //To generate number of single code barcodes
            for(int j=0; j<6;j++){
                table.addCell(createBarcode(writer, String.format("LMS"+"%08d", i)));
            }            
        }
        document.add(table);
        document.close();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, DocumentException {
        // TODO code application logic here
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new CodersBarcodeGenerator().createPdf(DEST);
    }
    
}
