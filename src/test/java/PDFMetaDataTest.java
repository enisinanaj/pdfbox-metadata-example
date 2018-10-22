import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.junit.Test;
import org.sagittarius90.pdf.PDFMetaDataManager;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class PDFMetaDataTest {

    @Test
    public void addMetadataToDocument() throws IOException, TransformerException {
        PDDocument document = new PDDocument();

        PDFMetaDataManager metaDataManager = new PDFMetaDataManager(document);
        metaDataManager.insertMetadata();
    }

    @Test
    public void readCustomMetadata() throws IOException, TransformerException {
        try (PDDocument document = PDDocument.load(new File("/Users/enisinanaj/Documents/tmp/meta-data.pdf"))) {
            PDDocumentInformation info = document.getDocumentInformation();
            System.out.println( "Page Count=" + document.getNumberOfPages());
            System.out.println( "Title=" + info.getTitle());
            System.out.println( "Author=" + info.getAuthor());
            System.out.println( "Subject=" + info.getSubject());
            System.out.println( "Keywords=" + info.getKeywords());
            System.out.println( "Creator=" + info.getCreator());
            System.out.println( "Producer=" + info.getProducer());
            System.out.println( "Trapped=" + info.getTrapped());

            System.out.println("document-id= " + info.getCustomMetadataValue("document-id"));

            PDDocumentCatalog cat = document.getDocumentCatalog();
            PDMetadata metadata = cat.getMetadata();
            if (metadata != null) {

                String string =  new String( metadata.toByteArray(), "ISO-8859-1");
                System.out.println( "Metadata=" + string);
            }
        } catch (IOException e){
            System.err.println("Exception while trying to read pdf document - " + e);
        }

    }
}
