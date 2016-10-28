import java.util.Date;

/**
 * Created by JacobAMason on 10/25/16.
 */
public abstract class Issue {
    protected int issueNumber;
    protected String description;
    protected Date timeCreated;
    protected Date timeResolved;

    public Issue(int issueNumber, String description, Date timeCreated, Date timeResolved) {
        this.issueNumber = issueNumber;
        this.description = description;
        this.timeCreated = timeCreated;
        this.timeResolved = timeResolved;
    }
}
