import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;

public class UILoader extends Application {
    private PieChart pieChartOne;

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

    public void runBtnPressed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) actionEvent.getSource();
        Scene scene = new Scene(new Group());
        stage.setTitle("Imported Fruits");
        stage.setWidth(500);
        stage.setHeight(500);

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Grapefruit", 13),
                        new PieChart.Data("Oranges", 25),
                        new PieChart.Data("Plums", 10),
                        new PieChart.Data("Pears", 22),
                        new PieChart.Data("Apples", 30));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Imported Fruits");

        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();

//        Parent root;
//        try {
//            root = FXMLLoader.load(getClass().getClassLoader().getResource("resultsWindow.fxml"));
//            Stage stage = new Stage();
//            stage.setTitle("Modeler Results");
//            stage.setScene(new Scene(root));
//            stage.show();
//            // Hide this current window (if this is what you want)
//            //((Node)(ActionEvent.getSource())).getScene().getWindow().hide();
//
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // TEST DATA
//        ObservableList<PieChart.Data> pieChartData =
//                FXCollections.observableArrayList(
//                        new PieChart.Data("Grapefruit", 13),
//                        new PieChart.Data("Oranges", 25),
//                        new PieChart.Data("Plums", 10),
//                        new PieChart.Data("Pears", 22),
//                        new PieChart.Data("Apples", 30));
//        pieChartOne.setData(pieChartData);
    }

    public void setPieData(Scene resultsScene, Array nameAry, Array dataAry) {
//        final PieChart pieChartOne = resultsScene.get
    }

    String fullPath;
    File file;

    @FXML
    private TextField num;


    public void runCompileBtn(ActionEvent actionEvent) throws IOException {
//                     prints the number of topics chosen
//                     System.out.println(num.getText());

//                     prints out the escaped path
//                     System.out.println(fullPath.replaceAll("\\\\","\\\\\\\\"));

//                     calls the excell function
        ExcelReader write = new ExcelReader(fullPath.replaceAll("\\\\","\\\\\\\\"));
        write.parse_input();


    }

    public void runBrowseBtn(ActionEvent e) throws IOException {
        JFileChooser chooser = new JFileChooser();
        //need to change this to excell only
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
    }
}