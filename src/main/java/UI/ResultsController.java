package UI;

import TopicModeling.Issue;
import TopicModeling.Bug;
import TopicModeling.Enhancement;
import TopicModeling.IssueFilter;
import com.pixelduke.javafx.chart.DateAxis;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.time.DateUtils;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
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
                Tab tab = new Tab(
                        topic + (vBox == bceTopicList ? " - Enhancements" : " - Bugs")
                );
                tab.setClosable(true);
                LineChart<Long, Number> lineChart = makeLineChart(issues, topicNames.indexOf(topic));

                tab.setContent(lineChart);
                tabPane.getTabs().add(tab);
                tabPane.getSelectionModel().select(tab);
            });
            vBox.getChildren().add(button);
        });
    }

    private LineChart<Long, Number> makeLineChart(List<?> issues, int topic) {
        DateAxis xAxis = new DateAxis();
        LineChart<Long, Number> lineChart = new LineChart<>(xAxis, new NumberAxis());

        XYChart.Series<Long, Number> series = new XYChart.Series<>();
        issues.stream()
                .filter(issue -> ((Issue) issue).getTopic() == topic)
                .collect(Collectors.groupingBy(
                        issue -> DateUtils.round(((Issue) issue).getTimeCreated(), Calendar.MONTH),
                        LinkedHashMap::new,
                        Collectors.counting()
                ))
                .forEach((date, numIssues) -> {
                    series.getData().add(new XYChart.Data<>(date.getTime(), numIssues));
                });

        lineChart.getData().add(series);
        return lineChart;
    }
}
