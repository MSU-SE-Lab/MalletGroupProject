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
import java.util.List;

/**
 * @author root
 */
public class PersonController {
    private File excelFile;

    @FXML
    public TextField numberOfTopics;

    @FXML
    private LineChart LineChart;

    @FXML
    private TextField fileText;

    @FXML
    private Button browseBtn;

    @FXML
    private Button runModelerBtn;

    @FXML
    private BarChart barChartEnhancements;

    public void runModelerBtn() throws IOException {
        ExcelReader excelReader = new ExcelReader(excelFile);
        try {
            TopicModeler topicModeler = new TopicModeler();
            topicModeler.addIssueListThruPipe((List<Issue>)(List<?>)excelReader.getBugs());
            topicModeler.model();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Parent root = FXMLLoader.load(getClass().getResource("Results.fxml"));
        Stage stage = (Stage) runModelerBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
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