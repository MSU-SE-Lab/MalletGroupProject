import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by JacobAMason on 10/19/16.
 */
public class ExcelReaderTest {
    @Test
    public void print_all_data() throws Exception {
        ExcelReader reader = new ExcelReader("Firefox_MasterFile_4214Fall2016.xlsx");
        reader.print();
    }
}