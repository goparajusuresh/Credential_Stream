package utility;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Assert;

public class ExcelReader {

	// public static Workbook workbook;
	// public static Sheet sheet;
	// public static String str;
	// public static Cell cell;
	// public static Double d;
	// public static int a;

	@SuppressWarnings("static-access")
	public static String readExcelFile(String filename, String sh, int x, int y)
			throws EncryptedDocumentException, InvalidFormatException, IOException {

		Workbook workbook = null;
		String str = null;
		try {
			workbook = WorkbookFactory.create(new File(filename));
			Sheet sheet = workbook.getSheet(sh);
			int rowcount = sheet.getLastRowNum();
			for (int i = 1; i <= rowcount; i++) {
				Row row = sheet.getRow(x);
				int cellcount = row.getLastCellNum();
				for (int j = 0; j <= cellcount;) {
					Cell cell = row.getCell(y);
					if (cell.getCellTypeEnum() == CellType.STRING) {
						str = sheet.getRow(x).getCell(y).getStringCellValue();
						return str;
					} else if (cell.getCellTypeEnum() == CellType.NUMERIC
							|| cell.getCellTypeEnum() == CellType.FORMULA) {
						Double d = sheet.getRow(x).getCell(y).getNumericCellValue();
						return String.valueOf(d.intValue());
					} else if (cell.getCellTypeEnum() == CellType.BLANK)
						return "";
					else
						return String.valueOf(sheet.getRow(x).getCell(y).getBooleanCellValue());
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			workbook.close();
		}

		return str;

	}

	@SuppressWarnings({ "static-access", "unused" })
	public static String retesting_MutltipleData(String filename, String sh)
			throws EncryptedDocumentException, InvalidFormatException, Exception {

		Workbook workbook = WorkbookFactory.create(new File(filename));
		Sheet sheet = workbook.getSheet(sh);
		int rowcount = sheet.getLastRowNum();
		String str = null;
		for (int i = 1; i <= rowcount; i++) {
			Row row = sheet.getRow(i);
			int cellcount = row.getLastCellNum();
			for (int j = 0; j <= cellcount; j++) {
				Cell cell = row.getCell(j);
				if (cell.getCellTypeEnum() == CellType.STRING) {
					str = sheet.getRow(i).getCell(j).getStringCellValue();
					return str;
				} else if (cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA) {
					Double d = sheet.getRow(i).getCell(j).getNumericCellValue();
					return String.valueOf(d.intValue());
				} else if (cell.getCellTypeEnum() == CellType.BLANK)
					return "";
				else
					return String.valueOf(sheet.getRow(i).getCell(j).getBooleanCellValue());

			}

		}

		return str;

	}
	
	// Read all excel files including .xls & .xlsx files
	public static void readAllExcelFiles(String filename, String verifyText) {
		Workbook workbook = null;
		try {
			// File file = new File("C:\\Users\\SureshGoparaju\\Downloads\\create - spreadsheet.xlsx"); // creating a new
			// file instance
			// FileInputStream fis = new FileInputStream(file); // obtaining bytes from the
			// file
			// creating Workbook instance that refers to .xlsx file

			workbook = WorkbookFactory.create(new File(filename));
			Sheet sheet = workbook.getSheetAt(0);

			DataFormatter dataFormatter = new DataFormatter();

			//System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
			Iterator<Row> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					String cellValue = dataFormatter.formatCellValue(cell);
					System.out.print(cellValue + "\t");
					if (cellValue.equalsIgnoreCase(verifyText)) {
						
						Assert.assertTrue(cellValue.equalsIgnoreCase(verifyText));
						System.out.println(verifyText +" is present");
					}
				}
				System.out.println();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
