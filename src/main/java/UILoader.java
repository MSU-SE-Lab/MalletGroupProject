import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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

    public void runBtnPressed(ActionEvent actionEvent) throws IOException {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("resultsWindow.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Modeler Results");
            stage.setScene(new Scene(root));
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node)(ActionEvent.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}