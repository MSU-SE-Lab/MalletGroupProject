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

    public void setNumTopics(int numTopics) {
        this.numTopics = numTopics;
    }

    public void model() throws Exception {
        ParallelTopicModel model = createTopicModel(5, instances);

        // The data alphabet maps word IDs to strings
        Alphabet dataAlphabet = instances.getDataAlphabet();

        FeatureSequence tokens = (FeatureSequence) model.getData().get(0).instance.getData();
        LabelSequence topics = model.getData().get(0).topicSequence;

        // Estimate the topic distribution of the first instance,
        //  given the current Gibbs state.
        double[] topicDistribution = model.getTopicProbabilities(0);

        // Get an array of sorted sets of word ID/count pairs
        ArrayList<TreeSet<IDSorter>> topicSortedWords = model.getSortedWords();


        // Create a new instance with high probability of topic 0
        StringBuilder topicZeroText = new StringBuilder();
        Iterator<IDSorter> iterator = topicSortedWords.get(0).iterator();


        Formatter out = buildFormatter(tokens, topics, dataAlphabet);
        showTopWords(numTopics,dataAlphabet, topicSortedWords, topicDistribution);

        int rank = 0;
        while (iterator.hasNext() && rank < numTopics) {
            IDSorter idCountPair = iterator.next();
            topicZeroText.append(dataAlphabet.lookupObject(idCountPair.getID()) + " ");
            rank++;
        }

        // Create a new instance named "test instance" with empty target and source fields.
        InstanceList testing = new InstanceList(instances.getPipe());
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

    private ParallelTopicModel createTopicModel(int numTopics, InstanceList instances) throws Exception {
        ParallelTopicModel model = new ParallelTopicModel(numTopics, 1.0, 0.01);
        model.addInstances(instances);
        model.setNumIterations(50);
        model.estimate();

        return model;
    }

    private Formatter buildFormatter(FeatureSequence tokens, LabelSequence topics, Alphabet dataAlphabet) {
        Formatter out = new Formatter(new StringBuilder(), Locale.US);
        for (int position = 0; position < tokens.getLength(); position++) {
            out.format("%s-%d \n", dataAlphabet.lookupObject(tokens.getIndexAtPosition(position)),
                    topics.getIndexAtPosition(position));
        }
        return out;
    }

    private void showTopWords(int numTopics,
                              Alphabet dataAlphabet,
                              ArrayList<TreeSet<IDSorter>> topicSortedWords,
                              double[] topicDistribution) {

        // Show top 5 words in topics with proportions for the first document
        for (int topic = 0; topic < numTopics; topic++) {
            Iterator<IDSorter> iterator = topicSortedWords.get(topic).iterator();

            Formatter out = new Formatter(new StringBuilder(), Locale.US);
            out.format("%d\t%.3f\t", topic, topicDistribution[topic]);
            int rank = 0;
            while (iterator.hasNext() && rank < 5) {
                IDSorter idCountPair = iterator.next();
                out.format("%s (%.0f) ", dataAlphabet.lookupObject(idCountPair.getID()), idCountPair.getWeight());
                rank++;
            }
            System.out.println(out);
        }
    }
}

