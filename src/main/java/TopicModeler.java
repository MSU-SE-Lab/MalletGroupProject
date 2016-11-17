import cc.mallet.pipe.*;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.types.*;

import java.net.URISyntaxException;
import java.util.*;

public class TopicModeler {
    private InstanceList instances;
    private int numTopics = 5;

    public TopicModeler() throws URISyntaxException {
        instances = buildPipe();
    }

    public ParallelTopicModel model() throws Exception {
        ParallelTopicModel model = new ParallelTopicModel(numTopics, 1.0, 0.01);
        model.addInstances(instances);
        model.setNumThreads(4);
        model.setNumIterations(100);
        model.estimate();
        assignTopicsToIssues(model);

        return model;
    }

    public void setNumTopics(int numTopics) {
        this.numTopics = numTopics;
    }

    private void testModel(ParallelTopicModel model, ArrayList<TreeSet<IDSorter>> topicSortedWords, Alphabet dataAlphabet) {
        // Create a new instance with high probability of topic 0
        StringBuilder topicZeroText = new StringBuilder();
        Iterator<IDSorter> iterator = topicSortedWords.get(0).iterator();

        int rank = 0;
        while (iterator.hasNext() && rank < 5) {
            IDSorter idCountPair = iterator.next();
            topicZeroText.append(dataAlphabet.lookupObject(idCountPair.getID()) + " ");
            rank++;
        }

        // Create a new instance named "test instance" with empty target and source fields.
        InstanceList testing = new InstanceList(instances.getPipe());
        System.err.println(topicZeroText);
        testing.addThruPipe(new Instance(topicZeroText.toString(), null, "test instance", null));

        TopicInferencer inferencer = model.getInferencer();
        double[] testProbabilities = inferencer.getSampledDistribution(testing.get(0), 10, 1, 5);
        System.out.println("0\t" + testProbabilities[0]);
    }

    public void addIssueListThruPipe(List<Issue> issues) {
        issues.forEach(instances::addThruPipe);
    }

    private InstanceList buildPipe() throws URISyntaxException {
        List<Pipe> topicList = new ArrayList<>();
        //topicList.add(new Target2Label());
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

    public void assignTopicsToIssues(ParallelTopicModel model) {
        TopicInferencer topicInferencer = model.getInferencer();
        model.getData().forEach(p -> {
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

