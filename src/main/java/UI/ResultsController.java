package UI;

import TopicModeling.Bug;
import TopicModeling.Enhancement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
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
    @FXML
    private CategoryAxis xAxisBarChart;
    @FXML
    private CategoryAxis xAxisEnhancementChart;

    private ObservableList<String> topics = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setBarChartBugs(List<Bug> bugs, int numberOfBugs) {
        int[] topics = new int[numberOfBugs];
        bugs.forEach(b -> topics[b.getTopic()]++);

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        for (int i = 0; i < topics.length; i++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(i), topics[i]));
        }

        barChartBugs.getData().add(series);
    }

    public void setEnhancementChartBugs(List<Enhancement> enhancements, int numberOfEnhancements) {
        int[] topics = new int[numberOfEnhancements];
        for (Enhancement b : enhancements) {
            topics[b.getTopic()]++;
        }
    }
}
