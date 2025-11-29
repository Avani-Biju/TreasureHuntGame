import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
public class CreditsPage {
    private final Stage stage;
    private static final int GAME_WIDTH = 1000;   // fixed size
    private static final int GAME_HEIGHT = 650;
    public CreditsPage(Stage stage) {
        this.stage = stage;
    }
    public void show() {
        // ===== BACKGROUND =====
        Image bgImage = new Image(getClass().getResource("/credits_bg.jpg").toExternalForm(),
                GAME_WIDTH, GAME_HEIGHT, false, true);
        BackgroundImage bg = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(GAME_WIDTH, GAME_HEIGHT, false, false, false, false)
        );

        // ===== PANEL =====
        VBox panel = new VBox(20);
        panel.setAlignment(Pos.CENTER);
        panel.setPrefSize(GAME_WIDTH, GAME_HEIGHT);
        panel.setStyle(
                "-fx-background-color: rgba(0,0,0,0.6);" +  // semi-transparent
                        "-fx-background-radius: 0;" +
                        "-fx-effect: dropshadow(gaussian, black, 5, 0, 0, 0);"
        );

        // ===== TITLE =====
        Label title = new Label("CREDITS");
        title.setFont(Font.font("Papyrus", 42));
        title.setTextFill(Color.GOLD);

        // ===== GAME NAME =====
        Label gameName = new Label("TREASURE HUNT");
        gameName.setFont(Font.font("Papyrus", 38));
        gameName.setTextFill(Color.ORANGE);

        // ===== GROUP NUMBER =====
        Label groupNum = new Label("Group Number: 10");
        groupNum.setFont(Font.font("Papyrus", 24));
        groupNum.setTextFill(Color.LIGHTGOLDENRODYELLOW);

        // ===== MEMBERS =====
        Label members = new Label(
                "Members:\n" +
                        "1. Abhijith Pratheesh (MGP24CS004)\n" +
                        "2. Avani Biju (MGP24CS045)\n" +
                        "3. Dona Mariya (MGP24CS057)\n" +
                        "4. Gaurav S Pillai(MGP24CS068)\n"
        );
        members.setFont(Font.font("Trebuchet MS", 16));
        members.setTextFill(Color.LIGHTYELLOW);
        members.setStyle("-fx-line-spacing: 5px; -fx-text-alignment: center;");
        members.setOnMouseEntered(e -> members.setTextFill(Color.ORANGE));
        members.setOnMouseExited(e -> members.setTextFill(Color.LIGHTYELLOW));

        // ===== GUIDE =====
        Label guide = new Label("Guide: Sanoj C Chacko");
        guide.setFont(Font.font("Lucida Bright", 16));
        guide.setTextFill(Color.LIGHTYELLOW);
        guide.setStyle("-fx-font-style: italic;");

        // ===== FOOTER =====
        Label footer = new Label("Thank you for playing!");
        footer.setFont(Font.font("Papyrus", 14));
        footer.setTextFill(Color.GOLD);

        // ===== BACK BUTTON =====
        Button backBtn = new Button("Back to Home");
        backBtn.setPrefWidth(200);
        backBtn.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;");
        backBtn.setOnMouseEntered(e -> backBtn.setStyle("-fx-background-color: #63B8FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;"));
        backBtn.setOnMouseExited(e -> backBtn.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;"));
        backBtn.setOnAction(e -> new HomePage(stage).show());

        // ===== ADD ALL TO PANEL =====
        panel.getChildren().addAll(title, gameName, groupNum, members, guide, footer, backBtn);

        // ===== SCENE =====
        StackPane root = new StackPane(panel);
        root.setBackground(new Background(bg));
        Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT);

        stage.setScene(scene);
        stage.setTitle("Credits - Treasure Hunt");
        stage.setResizable(false);  // lock size
        stage.show();
    }
}
