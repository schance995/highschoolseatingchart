package pcage;
/** A tool to read Microsoft Excel(xlsxl) files.
 * @author Skylar Chan
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SheetReader {
	XSSFWorkbook wbk;
	Iterator<Row> rowIterator;
	
	@SuppressWarnings("resource")
	/**
	 * Creates a sheet reader that contains the specified file path to a Microsoft Excel spreadsheet.
	 * @param filePath The location of the spreadsheet
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	SheetReader(String filePath) throws FileNotFoundException, IOException {
		wbk = new XSSFWorkbook(new FileInputStream(new File(filePath)));
		rowIterator = wbk.getSheetAt(0).iterator();
	}
	
	@SuppressWarnings("deprecation")
	/**
	 * Reads and returns the next row of the spreadsheet, advancing one row down.
	 * @return An ArrayList representing the current row
	 */
	public ArrayList<String> next() {
		ArrayList<String> out = new ArrayList<String>();
		Row row = rowIterator.next();
		Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
        	Cell cell = cellIterator.next();
        	switch (cell.getCellType()) {
            	case Cell.CELL_TYPE_STRING:
            		out.add(cell.getStringCellValue());
            		break;
            	case Cell.CELL_TYPE_NUMERIC:
            		out.add(Integer.toString((int) cell.getNumericCellValue()));
            		break;
            	default:
             }
        }
        return out;
	}
	
	/**
	 * Returns true if the spreadsheet has more rows to be read. (In other words, returns true if next() would return an ArrayList rather than throwing an exception.)
	 * @return true if the spreadsheet has more rows to be read
	 */
	public boolean hasNext() {
		return rowIterator.hasNext();
	}
	
	public int size() {
		return wbk.getSheetAt(0).getLastRowNum();
	}
	
}