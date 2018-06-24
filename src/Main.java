import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    static Stage stage;
    static Scene scene;

    static User user;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Database.execute("delete from books");
        stage = primaryStage;
        FXMLLoader view = new FXMLLoader(getClass().getResource("login.fxml"));

        scene = new Scene(view.load());

        stage.setTitle("Library of Alexandria");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }

    public static void setRoot(Parent parent) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        scene.setRoot(parent);
        stage.sizeToScene();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public static void exit() {
        stage.close();
    }
}
