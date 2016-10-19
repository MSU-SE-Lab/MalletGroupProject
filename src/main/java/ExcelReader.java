import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by JacobAMason on 10/19/16.
 * http://www.codejava.net/coding/how-to-read-excel-files-in-java-using-apache-poi
 */
public class ExcelReader {
    private XSSFWorkbook workbook;
    private FileInputStream inputStream;

    public ExcelReader(String fileName) throws IOException {
        inputStream = new FileInputStream(new File(fileName));
        workbook = new XSSFWorkbook(inputStream);
    }

    @Override
    protected void finalize() throws Throwable {
        workbook.close();
        inputStream.close();
        super.finalize();
    }

    public void print() {
        XSSFSheet sheet = workbook.getSheetAt(0);
        for (int rowNum = 1; rowNum < sheet.getPhysicalNumberOfRows(); rowNum++) {
            XSSFRow nextRow = sheet.getRow(rowNum);
            int issueNumber;
            if (nextRow.getCell(0).getCellType() == XSSFCell.CELL_TYPE_STRING) {
                issueNumber = -1;
            } else {
                issueNumber = (int)nextRow.getCell(0).getNumericCellValue();
            }
            String issueDescription = nextRow.getCell(1, XSSFRow.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            String issueTypeOrSeverity = nextRow.getCell(2, XSSFRow.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            Date issueCreationDate;
            if (nextRow.getCell(3, XSSFRow.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType() == XSSFCell.CELL_TYPE_STRING) {
                issueCreationDate = new Date(0);
            } else {
                issueCreationDate = nextRow.getCell(3).getDateCellValue();
            }
            Date issueResolveDate;
            if (nextRow.getCell(4, XSSFRow.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType() == XSSFCell.CELL_TYPE_STRING) {
                issueResolveDate = new Date(0);
            } else {
                issueResolveDate = nextRow.getCell(4).getDateCellValue();
            }
            System.out.println(String.format("%d - %s - %s - %s - %s",
                    issueNumber, issueDescription, issueTypeOrSeverity, issueCreationDate, issueResolveDate));
        }
    }
}
