import java.util.Date;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by JacobAMason on 11/9/16.
 */
public class IssueFilter {
    public static Stream<? extends Issue> created_after_date(Stream<? extends Issue> issues, Date date) {
        return issues.filter(p -> p.timeCreated.after(date));
    }
}
