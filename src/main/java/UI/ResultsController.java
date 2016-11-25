package UI;

import TopicModeling.Bug;
import TopicModeling.Enhancement;
import TopicModeling.Issue;
import com.pixelduke.javafx.chart.DateAxis;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by JacobAMason on 11/21/16.
 */
public class ResultsController implements Initializable {
    @FXML
    public TabPane tabPane;
    @FXML
    private VBox bceTopicList;
    @FXML
    private VBox bcbTopicList;
    @FXML
    private BarChart<String, Integer> barChartEnhancements;
    @FXML
    private BarChart<String, Integer> barChartBugs;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    void setBarChartEnhancements(List<Enhancement> enhancements, List<String> topicNames) {
        setBarChartData(enhancements, topicNames, barChartEnhancements);
        addTopicButtons(enhancements, topicNames, bceTopicList);
    }

    void setBarChartBugs(List<Bug> bugs, List<String> topicNames) {
        setBarChartData(bugs, topicNames, barChartBugs);
        addTopicButtons(bugs, topicNames, bcbTopicList);
    }

    private void setBarChartData(List<?> issues, List<String> topicNames, BarChart<String, Integer> barChart) {
        int[] topics = new int[topicNames.size()];
        issues.forEach(b -> topics[((Issue) b).getTopic()]++);

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for (int i = 0; i < topics.length; i++) {
            series.getData().add(new XYChart.Data<>(topicNames.get(i), topics[i]));
        }
        barChart.getData().add(series);
    }

    private void addTopicButtons(List<?> issues, List<String> topicNames, VBox vBox) {
        topicNames.forEach(topic -> {
            Button button = new Button(topic);
            button.setOnAction(e -> {
                String newTabText = topic + (vBox == bceTopicList ? " - Enhancements" : " - Bugs");
                tabPane.getSelectionModel().select(
                        tabPane.getTabs().stream().filter(t -> t.getText().equals(newTabText)).findFirst()
                                .orElseGet(() -> {
                                    Tab tab = new Tab(newTabText);
                                    tab.setClosable(true);

                                    LineChart<Long, Number> lineChart;
                                    if (issues.get(0) instanceof Bug) {
                                        lineChart = makeBugsLineChart((List<Bug>) issues, topicNames.indexOf(topic));
                                    } else {
                                        lineChart = makeEnhancementsLineChart((List<Enhancement>) issues, topicNames.indexOf(topic));
                                    }

                                    tab.setContent(lineChart);
                                    tabPane.getTabs().add(tab);
                                    return tab;
                                }));
            });
            vBox.getChildren().add(button);
        });
    }

    private LineChart<Long, Number> makeEnhancementsLineChart(List<Enhancement> enhancements, int topic) {
        DateAxis xAxis = new DateAxis();
        LineChart<Long, Number> lineChart = new LineChart<>(xAxis, new NumberAxis());
        lineChart.setLegendVisible(false);

        XYChart.Series<Long, Number> series = new XYChart.Series<>();
        enhancements.stream()
                .filter(enhancement -> enhancement.getTopic() == topic)
                .collect(Collectors.groupingBy(
                        enhancement -> DateUtils.round(enhancement.getTimeCreated(), Calendar.MONTH),
                        HashMap::new,
                        Collectors.counting()
                ))
                .forEach((date, numEnhancements) ->
                        series.getData().add(new XYChart.Data<>(date.getTime(), numEnhancements))
                );

        lineChart.getData().add(series);
        return lineChart;
    }

    private LineChart<Long, Number> makeBugsLineChart(List<Bug> bugs, int topic) {
        DateAxis xAxis = new DateAxis();
        LineChart<Long, Number> lineChart = new LineChart<>(xAxis, new NumberAxis());

        Map<Bug.Severity, XYChart.Series<Long, Number>> seriesMap = new HashMap<>();
        for (Bug.Severity severity : Bug.Severity.values()) {
            if (severity == Bug.Severity.enhancement) {
                continue;
            }
            XYChart.Series<Long, Number> series = new XYChart.Series<>();
            series.setName(severity.toString());
            seriesMap.put(severity, series);
        }

        bugs.stream()
                .filter(bug -> bug.getTopic() == topic)
                .collect(Collectors.groupingBy(
                        bug -> new ImmutablePair<>(bug.getSeverity(), DateUtils.round(bug.getTimeCreated() == null ? new Date(0) : bug.getTimeCreated(), Calendar.MONTH)),
                        HashMap::new,
                        Collectors.counting()
                ))
                .forEach((pair, numIssues) ->
                        seriesMap.get(pair.left).getData().add(new XYChart.Data<>(pair.right.getTime(), numIssues))
                );

        lineChart.getData().addAll(seriesMap.values());
        return lineChart;
    }
}
