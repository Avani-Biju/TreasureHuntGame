import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameOver {
    private final Stage stage;
    private final int score;
    private final String message;

    public GameOver(Stage stage, int score, String message){
        this.stage = stage;
        this.score = score;
        this.message = message;
    }

    public void show(){
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);

        Label msgLabel = new Label(message);
        msgLabel.setTextFill(Color.RED);
        Label scoreLabel = new Label("Your Score: "+score);
        Button backBtn = new Button("Back to Home");

        backBtn.setOnAction(e -> new HomePage(stage).show());

        layout.getChildren().addAll(msgLabel, scoreLabel, backBtn);

        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Game Over");
        stage.show();
    }
}
