import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Start your home or main game directly as usual
        HomePage home = new HomePage(primaryStage);
        home.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
