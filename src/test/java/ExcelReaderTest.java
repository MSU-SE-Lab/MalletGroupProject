import org.junit.Test;

/**
 * Created by JacobAMason on 10/19/16.
 */
public class ExcelReaderTest {
    @Test
    public void read_all_data() throws Exception {
        ExcelReader reader = new ExcelReader("Firefox_MasterFile_4214Fall2016.xlsx");
        assert reader.getEnhancements().size() == 4260;
    }
}