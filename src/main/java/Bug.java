import java.util.Date;

/**
 * Created by JacobAMason on 10/25/16.
 */
public class Bug extends Issue {
    public enum Severity {
        enhancement, trivial, minor, normal, major, critical
    }
    Severity severityLevel;

    public Bug(int issueNumber, String description, Date timeCreated, Date timeResolved, String severityLevel) {
        super(issueNumber, description, timeCreated, timeResolved);
        this.severityLevel = Severity.valueOf(severityLevel);
    }

    @Override
    public String toString() {
        return "Bug{" +
                "issueNumber=" + issueNumber +
                " description=" + description +
                " timeCreated=" + timeCreated +
                " timeResolved=" + timeResolved +
                " severityLevel=" + severityLevel +
                '}';
    }
}

