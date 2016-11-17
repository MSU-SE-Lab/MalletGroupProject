import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class UILoader extends Application {

     @Override
    public void start(Stage stage) throws Exception {
         Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));

         Scene scene = new Scene(root);

         stage.setScene(scene);
         stage.setTitle("Topic Modeler");
         stage.show();
     }

    public static void main(String[] args) {
         launch(args);
     }

    public void loadPieChart() throws IOException {
        String nameAry[] = {"search", "bookmark", "tab", "window", "button"};
        int numAry[] = {68, 29, 36, 49, 24};
        Stage stage = new Stage();
        stage.setTitle("Modeler Results");
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (int i =0; i < nameAry.length; i++) {
            pieChartData.add(new PieChart.Data(nameAry[i], numAry[i]));
        }

        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Modeler Results");

        StackPane root = new StackPane();
        root.getChildren().add(chart);
        stage.setScene(new Scene(root, 600, 600));
        stage.show();
    }

    String fullPath;
    File file;

    @FXML
    private TextField num;


    public void runCompileBtn() throws IOException {
        ExcelReader excelReader = new ExcelReader(fullPath.replaceAll("\\\\","\\\\\\\\"));
        try {
            TopicModeler topicModeler = new TopicModeler();
            topicModeler.addIssueListThruPipe((List<Issue>)(List<?>)excelReader.getBugs());
            topicModeler.model();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runBrowseBtn() throws IOException {
        JFileChooser chooser = new JFileChooser();
        //need to change this to excel only
        chooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Browse the folder to process");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            fullPath = file.getAbsolutePath();
        } else {
            System.out.println("No Selection ");
        }
        
        // Test code for pie chart loader
//        String testString[] = {"This", "is", "only", "a", "test."};
//        int testInt[] = {1, 2, 3, 4, 9};
        //loadPieChart(testString, testInt);
    }
}