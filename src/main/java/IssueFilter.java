import cc.mallet.topics.TopicAssignment;

import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by JacobAMason on 11/9/16.
 */
public class IssueFilter {
    private static Stream<? extends Issue> issues;
    private static int topic;

    public static Stream<? extends Issue> isEnhancement(Stream<? extends Issue> issues) {
        return issues.filter(p -> p instanceof Enhancement);
    }

    public static Stream<? extends Issue> isBug(Stream<? extends Issue> issues) {
        return issues.filter(p -> p instanceof Bug);
    }

    public static Stream<? extends Issue> createdAfterDate(Stream<? extends Issue> issues, Date date) {
        return issues.filter(p -> p.timeCreated.after(date));
    }

    public static Stream<? extends Issue> hasSeverity(Stream<? extends Issue> issues, Bug.Severity severity) {
        return issues.filter(p -> p instanceof Bug && ((Bug) p).severityLevel == severity);
    }

    public static Stream<TopicAssignment> hasTopic(Stream<TopicAssignment> issues, int topic) {
        return issues.filter(p -> ((Issue) p.instance).getTopic() == topic);
    }
}
