package org.sagittarius90.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class PDFMetaDataManager {

    private final PDDocument document;

    public PDFMetaDataManager(PDDocument pdfDocument) {
        this.document = pdfDocument;
    }

    public void insertMetadata() throws IOException, TransformerException {
        PDDocumentInformation info = new PDDocumentInformation();
        info.setAuthor("Eni Sinanaj");
        info.setCreator("Eni Sinanaj");
        info.setProducer("Eni Sinanaj");
        info.setCreationDate(Calendar.getInstance());
        info.setModificationDate(Calendar.getInstance());

        PDStream stream = new PDStream(document);
        info.setCustomMetadataValue("document-id", md5Java(stream.toByteArray()));

        PDPage page = new PDPage();
        document.addPage(page);

        document.setDocumentInformation(info);
        File file = new File("/Users/enisinanaj/Documents/tmp/meta-data.pdf");
        file.createNewFile();
        document.save(file);
    }

    public static String md5Java(byte[] message){
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message);

            //converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2*hash.length);
            for(byte b : hash){
                sb.append(String.format("%02x", b&0xff));
            }

            digest = sb.toString();
        } catch (NoSuchAlgorithmException ex) {

        }

        return digest;
    }
}
