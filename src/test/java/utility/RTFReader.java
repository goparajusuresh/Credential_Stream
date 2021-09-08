package utility;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import org.testng.Assert;

public class RTFReader {

	public static void RTF(String fileName, String verifyText) {

		String home = System.getProperty("user.home");
		File loc = new File(home + "/Downloads/" + fileName); // ==>gives you user downloads path
		String path = loc.toString();
		try {
			String result = null;
			File file = new File(path);
			DefaultStyledDocument styledDoc = new DefaultStyledDocument();
			InputStream is = new FileInputStream(file);
			new RTFEditorKit().read(is, styledDoc, 0);
			result = new String(styledDoc.getText(0, styledDoc.getLength()).getBytes("ISO8859_1"));
			System.out.println(result);

			// Assert.assertTrue(result.toString().contains(verifyText));

			boolean verify = result.contains(verifyText);
			Assert.assertTrue(verify);
			System.out.println(verify + " The given text " + verifyText + " is found in the file");
			is.close();
			Files.deleteIfExists(Paths.get(path));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

	}

}
