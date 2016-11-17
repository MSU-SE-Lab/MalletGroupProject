import cc.mallet.topics.ParallelTopicModel;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
        List<Issue> list = IssueFilter.createdAfterDate((excelReader.getEnhancements()).stream(), date).collect(Collectors.toList());
        topicModeler.addIssueListThruPipe(list);
        ParallelTopicModel model = topicModeler.model();
        model.printTopWords(System.out, 5, false);

        IssueFilter.hasTopic(model.getData().stream(), 0).forEach(p -> System.out.println(p.instance));
    }

    @Test
    public void testData() throws Exception {
        topicModeler.addIssueListThruPipe(new ArrayList<Issue>(Arrays.asList(
                new Bug(1, "bug alpha test", new Date(), new Date(), "minor"),
                new Bug(2, "bug beta", new Date(), new Date(), "major"),
                new Bug(3, "bug gamma", new Date(), new Date(), "critical"),
                new Bug(4, "bug delta", new Date(), new Date(), "minor"),
                new Enhancement(5, "enhancement eta", new Date(), new Date()),
                new Enhancement(666, "enhancement zeta", new Date(), new Date()),
                new Enhancement(7, "enhancement theta", new Date(), new Date()),
                new Enhancement(8, "enhancement iota", new Date(), new Date())
        )));
        ParallelTopicModel model = topicModeler.model();
        model.printTopWords(System.out, 5, false);

        IssueFilter.hasTopic(model.getData().stream(), 0).forEach(p -> System.out.println(p.instance));
    }
}