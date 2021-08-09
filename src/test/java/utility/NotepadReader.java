package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

public class NotepadReader {
	
	//text1 should be display, text2 should not be display in the file
	public static void verifyNotepadText(String fileName, String text1, String text2) throws Exception {

		// String filepath1 = "C:\\Users\\SureshGoparaju\\Downloads\\efd.txt";
		String home = System.getProperty("user.home");
		File file = new File(home + "/Downloads/" + fileName); // ==>gives you user downloads path
		String path = file.toString(); // converting to string
		// String location = path.replace("\\", "//"); //converting \ to //

		FileReader FR = new FileReader(path);
		BufferedReader BR = new BufferedReader(FR);
		//Runtime rs = Runtime.getRuntime();
		
		String readline, value;
		try {
			//rs.exec(path);
			// Loop to read all lines one by one from file and print It.
			while ((readline = BR.readLine()) != null) {
				value = readline;
				System.out.println(value);
				Assert.assertTrue(value.toString().contains(text1));
				System.out.println("The given text " + text1 + " is Found in the File");
				Assert.assertFalse(value.toString().contains(text2));
				System.out.println(text2 + " is not found in the file");
			}
			BR.close();
			//rs.exit(0);
			Files.deleteIfExists(Paths.get(path));
			System.out.println("File is Deleted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}