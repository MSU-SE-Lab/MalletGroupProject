package UI;

import TopicModeling.Bug;
import TopicModeling.Enhancement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by JacobAMason on 11/21/16.
 */
public class ResultsController implements Initializable {
    @FXML
    private BarChart<String, Integer> barChartBugs;
    @FXML
    private BarChart<String, Integer> barChartEnhancements;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    void setBarChartEnhancements(List<Enhancement> enhancements, int numberOfEnhancements, String[] topicNames) {
        int[] topics = new int[numberOfEnhancements];
        enhancements.forEach(b -> topics[b.getTopic()]++);

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for (int i = 0; i < topics.length; i++) {
            series.getData().add(new XYChart.Data<>(topicNames[i], topics[i]));
        }

        barChartEnhancements.getData().add(series);
    }

    void setBarChartBugs(List<Bug> bugs, int numberOfBugs, String[] topicNames) {
        int[] topics = new int[numberOfBugs];
        bugs.forEach(b -> topics[b.getTopic()]++);

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for (int i = 0; i < topics.length; i++) {
            series.getData().add(new XYChart.Data<>(topicNames[i], topics[i]));
        }

        barChartBugs.getData().add(series);
    }
}
