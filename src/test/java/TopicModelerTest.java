import org.junit.Test;

/**
 * Created by JacobAMason on 10/28/16.
 */
public class TopicModelerTest {
    @Test
    public void main() throws Exception {
        TopicModeler topicModeler = new TopicModeler();
        topicModeler.main("Firefox_MasterFile_4214Fall2016.xlsx");
    }

}