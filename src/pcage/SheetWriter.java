package pcage;
/**
 * A class to write and save Excel files
 * @author Skylar Chan
 */
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SheetWriter {

	private FileOutputStream fileOut;
	private XSSFWorkbook workbook = new XSSFWorkbook();
	private XSSFSheet sheet;
	private XSSFRow row;
	private int rowNum = 0;
	private int colNum = 0;
	
	/**
	 * Constructs an Excel file at the specified file path.
	 * @param filePath
	 * @throws FileNotFoundException
	 */
	public SheetWriter(String filePath) throws FileNotFoundException {
		fileOut = new FileOutputStream(filePath);
	}
	
	/**
	 * Adds a sheet with the specified name. If there were sheets previously added, they will no longer be written to when this method is called.
	 * @param name
	 */
	public void addSheet(String name) {
		for(int i=0; i<colNum; i++) {
			sheet.autoSizeColumn(i);
		}
		sheet = workbook.createSheet(name);
		rowNum = 0;
	}
	
	/**
	 * Adds a new row to the current sheet. If there were rows previously added, they will no longer be written to when this method is called.
	 */
	public void addRow() {
		row = sheet.createRow(rowNum);
		rowNum++;
		colNum = 0;
	}
	
	/**
	 * Adds a new cell with the specified contents to the current row. If there were cells previously added, they will no longer be written to when this method is called.
	 * @param contents
	 */
	public void addCell(String contents) {
		XSSFCell cell = row.createCell(colNum);
		cell.setCellValue(contents);
		colNum++;
	}
	
	/**
	 * Saves the Excel file
	 * @throws IOException
	 */
	public void save() throws IOException {
		for(int i=0; i<colNum; i++) {
			sheet.autoSizeColumn(i);
		}
		workbook.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
}