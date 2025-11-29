import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class InstructionsPage {

    private final Stage stage;
    private static final int GAME_WIDTH = 1000;
    private static final int GAME_HEIGHT = 650;

    public InstructionsPage(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #111; -fx-padding: 30;");

        Label title = new Label("INSTRUCTIONS");
        title.setFont(Font.font("Papyrus", 36));
        title.setTextFill(Color.GOLD);

        Label instructions = new Label(
                "Welcome, brave treasure hunter! 🏴‍☠️\n\n" +
                        "1. Click on the grid cells to search for the hidden treasure.\n" +
                        "2. Avoid traps that will decrease your score.\n" +
                        "3. Keep an eye on the timer — you have only 60 seconds!\n" +
                        "4. Use your hints wisely, advanced hints are limited.\n\n" +
                        "May luck and wisdom guide you to the treasure!"
        );
        instructions.setFont(Font.font("Papyrus", 16));
        instructions.setTextFill(Color.LIGHTYELLOW);
        instructions.setWrapText(true);

        Button backBtn = new Button("Back to Home");
        backBtn.setPrefWidth(200);
        backBtn.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;");
        backBtn.setOnMouseEntered(e -> backBtn.setStyle("-fx-background-color: #63B8FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;"));
        backBtn.setOnMouseExited(e -> backBtn.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;"));
        backBtn.setOnAction(e -> new HomePage(stage).show());

        layout.getChildren().addAll(title, instructions, backBtn);

        Scene scene = new Scene(layout, GAME_WIDTH, GAME_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("Instructions - Treasure Hunt");
        stage.setResizable(false);  // lock window size
        stage.show();
    }
}
