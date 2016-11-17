import java.util.Date;

/**
 * Created by JacobAMason on 10/25/16.
 */
public class Enhancement extends Issue {
    public Enhancement(int issueNumber, String description, Date timeCreated, Date timeResolved) {
        super(issueNumber, description, timeCreated, timeResolved);
    }

    @Override
    public String toString() {
        return "Enhancement{" +
                "issueNumber=" + issueNumber +
                " description='" + description +
                "' timeCreated=" + timeCreated +
                " timeResolved=" + timeResolved +
                '}';
    }
}
