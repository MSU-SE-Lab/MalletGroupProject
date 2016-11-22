package UI;

import TopicModeling.Bug;
import TopicModeling.ExcelReader;
import TopicModeling.Issue;
import TopicModeling.TopicModeler;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicAssignment;
import cc.mallet.util.CommandOption;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author root
 */
public class MainController {
    private File excelFile;

    @FXML
    public TextField numberOfTopicsText;

    @FXML
    private LineChart LineChart;

    @FXML
    private TextField fileText;

    @FXML
    private Button browseBtn;

    @FXML
    private Button runModelerBtn;

    public void runModelerBtn() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Results.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) runModelerBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        ExcelReader excelReader = new ExcelReader(excelFile);
        try {
            int numberOfTopics = (numberOfTopicsText.getText().isEmpty()) ? 5 : Integer.parseInt(numberOfTopicsText.getText());
            TopicModeler topicModeler = new TopicModeler();
            topicModeler.setNumTopics(numberOfTopics);
            topicModeler.addIssueListThruPipe((List<Issue>) (List<?>) excelReader.getBugs());
            ParallelTopicModel model = topicModeler.model();
            List<Bug> bugs = model.getData().stream().map(p -> (Bug) p.instance).collect(Collectors.toList());

            ResultsController controller = loader.getController();
            controller.setBarChartBugs(bugs, numberOfTopics);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runBrowseBtn() throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        chooser.setInitialDirectory(new File("."));
        chooser.setTitle("Browse the folder to process");
        excelFile = chooser.showOpenDialog(null);

        if (excelFile != null) {
            fileText.setText(excelFile.getAbsolutePath());
        } else {
            fileText.clear();
        }
    }
}