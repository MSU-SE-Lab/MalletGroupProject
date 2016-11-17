import cc.mallet.topics.ParallelTopicModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by JacobAMason on 10/28/16.
 */
public class TopicModelerTest {
    private TopicModeler topicModeler;
    private ExcelReader excelReader;

    @Before
    public void setUp() throws Exception {
        topicModeler = new TopicModeler();
        excelReader = new ExcelReader("Firefox_MasterFile_4214Fall2016.xlsx");
    }

    @Test
    public void testClusteredDominantTopicsForEnhancements() throws Exception {
        topicModeler.addIssueListThruPipe((List<Issue>)(List<?>) excelReader.getEnhancements());
        topicModeler.model().printTopWords(System.out, 5, false);
    }

    @Test
    public void testClusteredDominantTopicsForBugs() throws Exception {
        topicModeler.setNumTopics(6);
        topicModeler.addIssueListThruPipe((List<Issue>)(List<?>) excelReader.getBugs());
        topicModeler.model().printTopWords(System.out, 5, false);
    }
}