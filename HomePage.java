import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class HomePage {


    private final Stage stage;
    private static final int GAME_WIDTH = 1000;
    private static final int GAME_HEIGHT = 650;


    public HomePage(Stage stage) {
        this.stage = stage;
    }


    public void show() {
        // ===== BACKGROUND IMAGE =====
        Image bgImage = new Image(getClass().getResource("/home_bg.jpg").toExternalForm(),
                GAME_WIDTH, GAME_HEIGHT, false, true);
        BackgroundImage bg = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(GAME_WIDTH, GAME_HEIGHT, false, false, false, false)
        );


        StackPane root = new StackPane();
        root.setBackground(new Background(bg));


        // ===== SEMI-TRANSPARENT PANEL =====
        VBox panel = new VBox(20);
        panel.setAlignment(Pos.CENTER);
        panel.setPrefSize(GAME_WIDTH, GAME_HEIGHT);
        panel.setStyle(
                "-fx-background-color: rgba(0,0,0,0.6);" + // semi-transparent black
                        "-fx-background-radius: 0;"
        );


        // ===== TITLE =====
        Label title = new Label("TREASURE HUNT");
        title.setFont(Font.font("Papyrus", 48));
        title.setTextFill(Color.GOLD);


// ===== TAGLINE =====
        Label tagline = new Label("\"Find it before time runs out!\"");
        tagline.setFont(Font.font("Papyrus", 18));
        tagline.setTextFill(Color.CYAN); // bright pop color
// Removed the glow effect for a clean look


        // ===== BUTTONS =====
        Button startBtn = createButton("Start Game");
        startBtn.setOnAction(e -> {
            InstructionsOverlay.show(stage, () -> new TreasureHuntGame(stage).show());
        });


        Button scoreBtn = createButton("Scoreboard");
        scoreBtn.setOnAction(e -> new ScoreBoard(stage).show());


        Button instrBtn = createButton("Instructions");
        instrBtn.setOnAction(e -> new InstructionsPage(stage).show());


        Button creditsBtn = createButton("Credits");
        creditsBtn.setOnAction(e -> new CreditsPage(stage).show());


        Button exitBtn = createButton("Exit");
        exitBtn.setOnAction(e -> stage.close());


        panel.getChildren().addAll(title, tagline, startBtn, scoreBtn, instrBtn, creditsBtn, exitBtn);


        root.getChildren().add(panel);


        Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("Treasure Hunt Game");
        stage.setResizable(false);
        stage.show();
    }


    private Button createButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(200);
        btn.setStyle(
                "-fx-background-color: rgba(30,144,255,0.7);" +  // semi-transparent blue
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 16px;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: rgba(30,144,255,0.85);" + // less transparent on hover
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 16px;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: rgba(30,144,255,0.7);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 16px;"
        ));
        return btn;
    }
}

