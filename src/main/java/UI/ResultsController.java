package UI;

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

    void setBarChartEnhancements(List<Enhancement> enhancements, int numberOfEnhancements, List<String> topicNames) {
        int[] topics = new int[numberOfEnhancements];
        enhancements.forEach(b -> topics[b.getTopic()]++);

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for (int i = 0; i < topicNames.size(); i++) {
            series.getData().add(new XYChart.Data<>(topicNames.get(i), topics[i]));
        }
        barChartEnhancements.getData().add(series);

        addTopicButtons(topicNames, bceTopicList);
    }

    void setBarChartBugs(List<Bug> bugs, int numberOfBugs, List<String> topicNames) {
        int[] topics = new int[numberOfBugs];
        bugs.forEach(b -> topics[b.getTopic()]++);

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for (int i = 0; i < topics.length; i++) {
            series.getData().add(new XYChart.Data<>(topicNames.get(i), topics[i]));
        }
        barChartBugs.getData().add(series);

        addTopicButtons(topicNames, bcbTopicList);
    }

    private void addTopicButtons(List<String> topicNames, VBox vBox) {
        topicNames.forEach(topic -> {
            Button button = new Button(topic);
            button.setOnAction(e -> System.out.println(topic));
            vBox.getChildren().add(button);
        });
    }
}
