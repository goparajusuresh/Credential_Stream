package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class WordReader {

	public static void readDocFile(String fileName) throws IOException {
		String filePath = "C:\\Users\\SureshGoparaju\\Downloads\\Listingsdoc.doc";

		/*
		 * String home = System.getProperty("user.home"); File loc = new File(home +
		 * "/Downloads/" + fileName); // ==>gives you user downloads path String path =
		 * loc.toString();
		 */

		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file.getAbsoluteFile());
		try {
			XWPFDocument doc = new XWPFDocument(fis);
			List<XWPFParagraph> paragraphs = doc.getParagraphs();
			System.out.println("Total no.of paragraphs" + paragraphs.size());
			for (XWPFParagraph para : paragraphs) {
				System.out.println(para.getText());
			}
			fis.close();
			/*
			 * XWPFWordExtractor extract = new XWPFWordExtractor(doc);
			 * System.out.println(extract.getText()); extract.close(); //
			 * extract.getText().contains("Suresh");
			 */
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}