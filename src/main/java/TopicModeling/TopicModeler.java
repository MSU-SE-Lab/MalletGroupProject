package TopicModeling;

import cc.mallet.pipe.*;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.types.*;

import java.net.URISyntaxException;
import java.util.*;

public class TopicModeler extends ParallelTopicModel {
    private InstanceList instances;
    private int numTopics = 5;

    public TopicModeler(int numTopics) throws URISyntaxException {
        super(numTopics);
        instances = buildPipe();
    }

    public TopicModeler model() throws Exception {
        addInstances(instances);
        setNumThreads(4);
        setNumIterations(50);  // TODO: Bump this back to something like 1000 for production
        estimate();
        assignTopicsToIssues();

        return this;
    }

    public void addIssueListThruPipe(List<Issue> issues) {
        issues.forEach(instances::addThruPipe);
    }

    public List<String> getTopicNames() {
        List<String> topicNames = new ArrayList<>(numTopics);
        ArrayList<TreeSet<IDSorter>> topicSortedWords = getSortedWords();

        for (int i = 0; i < numTopics; i++) {
            TreeSet<IDSorter> sortedWords = topicSortedWords.get(i);
            Iterator<IDSorter> iterator = sortedWords.iterator();
            String topic;
            do {
                topic = getAlphabet().lookupObject(iterator.next().getID()).toString();
            } while (topicNames.contains(topic) && iterator.hasNext());
            topicNames.add(topic);
        }

        return topicNames;
    }

    private InstanceList buildPipe() throws URISyntaxException {
        List<Pipe> topicList = new ArrayList<>();
        topicList.add(new CharSequenceLowercase());
        topicList.add(new CharSequence2TokenSequence());
        topicList.add(new TokenSequenceRemoveNonAlpha());
        TokenSequenceRemoveStopwords tsrs = new TokenSequenceRemoveStopwords(false, false);
        String[] stopWords = {"quot"};
        tsrs.addStopWords(stopWords);
        topicList.add(tsrs);
        topicList.add(new TokenSequence2FeatureSequence());
        return new InstanceList(new SerialPipes(topicList));
    }

    private void assignTopicsToIssues() {
        TopicInferencer topicInferencer = getInferencer();
        getData().forEach(p -> {
            double[] distribution = topicInferencer.getSampledDistribution(p.instance, 10, 1, 1);
            int maxIndex = p.topicSequence.getIndexAtPosition(0);
            double maxValue = 0;
            for (int i : p.topicSequence.getFeatures()) {
                if (distribution[i] > maxValue) {
                    maxIndex = i;
                    maxValue = distribution[i];
                }
            }
            ((Issue)p.instance).assignTopic(maxIndex);
        });
    }
}

