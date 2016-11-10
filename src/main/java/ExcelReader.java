import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JacobAMason on 10/19/16.
 * http://www.codejava.net/coding/how-to-read-excel-files-in-java-using-apache-poi
 */
public class ExcelReader {
    private XSSFWorkbook workbook;
    private FileInputStream inputStream;
    private List<Bug> bugs = new ArrayList<Bug>();
    private List<Enhancement> enhancements = new ArrayList<Enhancement>();

    public ExcelReader(String fileName) throws IOException {
        inputStream = new FileInputStream(new File(fileName));
        workbook = new XSSFWorkbook(inputStream);
        parse_input();
    }

    @Override
    protected void finalize() throws Throwable {
        workbook.close();
        inputStream.close();
        super.finalize();
    }

    public void parse_input() {
        XSSFSheet sheet = workbook.getSheetAt(0);
        for (int rowNum = 1; rowNum < sheet.getPhysicalNumberOfRows(); rowNum++) {
            XSSFRow nextRow = sheet.getRow(rowNum);
            int issueNumber = getIssueNumber(nextRow);
            String issueDescription = getIssueDescription(nextRow);
            String issueTypeOrSeverity = getTypeOrSeverity(nextRow);
            Date issueCreationDate = getIssueCreationDate(nextRow);
            Date issueResolveDate = getIssueResolveDate(nextRow);
            addIssueToList(issueNumber, issueDescription, issueTypeOrSeverity, issueCreationDate, issueResolveDate);
        }
    }

    public List<Bug> getBugs() {
        return bugs;
    }

    public List<Enhancement> getEnhancements() {
        return enhancements;
    }

    private void addIssueToList(int issueNumber, String issueDescription, String issueTypeOrSeverity, Date issueCreationDate, Date issueResolveDate) {
        if (Bug.Severity.getSeverityFromString(issueTypeOrSeverity) == Bug.Severity.enhancement) {
            enhancements.add(new Enhancement(issueNumber, issueDescription, issueCreationDate, issueResolveDate));
        } else {
            bugs.add(new Bug(issueNumber, issueDescription, issueCreationDate, issueResolveDate, issueTypeOrSeverity));
        }
    }

    private Date getIssueResolveDate(XSSFRow nextRow) {
        Date issueResolveDate;
        if (nextRow.getCell(4, XSSFRow.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType() == XSSFCell.CELL_TYPE_STRING) {
            issueResolveDate = new Date(0);
        } else {
            issueResolveDate = nextRow.getCell(4).getDateCellValue();
        }
        return issueResolveDate;
    }

    private Date getIssueCreationDate(XSSFRow nextRow) {
        Date issueCreationDate;
        if (nextRow.getCell(3, XSSFRow.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType() == XSSFCell.CELL_TYPE_STRING) {
            issueCreationDate = new Date(0);
        } else {
            issueCreationDate = nextRow.getCell(3).getDateCellValue();
        }
        return issueCreationDate;
    }

    private String getTypeOrSeverity(XSSFRow nextRow) {
        return nextRow.getCell(2, XSSFRow.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
    }

    private String getIssueDescription(XSSFRow nextRow) {
        return nextRow.getCell(1, XSSFRow.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
    }

    private int getIssueNumber(XSSFRow nextRow) {
        int issueNumber;
        if (nextRow.getCell(0).getCellType() == XSSFCell.CELL_TYPE_STRING) {
            issueNumber = -1;
        } else {
            issueNumber = (int)nextRow.getCell(0).getNumericCellValue();
        }
        return issueNumber;
    }
}
