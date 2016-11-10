import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by JacobAMason on 11/9/16.
 */
public class IssueFilterTest {
    private TopicModeler topicModeler;
    private ExcelReader excelReader;

    @Before
    public void setUp() throws Exception {
        topicModeler = new TopicModeler();
        excelReader = new ExcelReader("Firefox_MasterFile_4214Fall2016.xlsx");
    }

    @Test
    public void created_after_date() throws Exception {
        Date date = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2010");
        List<Issue> list = IssueFilter.created_after_date((excelReader.getEnhancements()).stream(), date).collect(Collectors.toList());
        topicModeler.addIssueListThruPipe(list);
        topicModeler.model();
    }
}