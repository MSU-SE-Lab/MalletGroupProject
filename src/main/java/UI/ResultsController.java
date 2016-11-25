package UI;

import TopicModeling.Issue;
import TopicModeling.Bug;
import TopicModeling.Enhancement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by JacobAMason on 11/21/16.
 */
public class ResultsController implements Initializable {
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
        addTopicButtons(topicNames, bceTopicList);
    }

    void setBarChartBugs(List<Bug> bugs, List<String> topicNames) {
        setBarChartData(bugs, topicNames, barChartBugs);
        addTopicButtons(topicNames, bcbTopicList);
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

    private void addTopicButtons(List<String> topicNames, VBox vBox) {
        topicNames.forEach(topic -> {
            Button button = new Button(topic);
            button.setOnAction(e -> System.out.println(topic));
            vBox.getChildren().add(button);
        });
    }
}
