package TopicModeling;

import cc.mallet.types.Instance;

import java.util.Date;

/**
 * Created by JacobAMason on 10/25/16.
 */
public abstract class Issue extends Instance {
    protected int issueNumber;
    protected String description;
    protected Date timeCreated;
    protected Date timeResolved;
    private int topic;


    public Issue(int issueNumber, String description, Date timeCreated, Date timeResolved) {
        super(description, null, null, null);
        this.issueNumber = issueNumber;
        this.description = description;
        this.timeCreated = timeCreated;
        this.timeResolved = timeResolved;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void assignTopic(int topic) {
        this.topic = topic;
    }

    public int getTopic() {
        return topic;
    }
}
