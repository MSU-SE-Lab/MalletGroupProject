import org.junit.Test;

import java.util.List;

/**
 * Created by JacobAMason on 10/28/16.
 */
public class TopicModelerTest {
    @Test
    public void main() throws Exception {
        TopicModeler topicModeler = new TopicModeler();
        ExcelReader excelReader = new ExcelReader("Firefox_MasterFile_4214Fall2016.xlsx");
        topicModeler.addIssueListThruPipe((List<Issue>)(List<?>) excelReader.getEnhancements());
        topicModeler.model();
    }
}