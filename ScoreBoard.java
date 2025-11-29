import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreBoard {

    private final Stage stage;
    private final String FILE_PATH = "topScores.txt";

    public ScoreBoard(Stage stage) {
        this.stage = stage;
    }

    private List<ScoreEntry> loadScores() {
        List<ScoreEntry> scores = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return scores;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int score = Integer.parseInt(parts[0]);
                    int time = Integer.parseInt(parts[1]);
                    scores.add(new ScoreEntry(score, time));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }

    private void clearScores() {
        File file = new File(FILE_PATH);
        if (file.exists() && file.delete()) {
            System.out.println("Score history cleared.");
        }
    }

    public void show() {
        List<ScoreEntry> scores = loadScores();

        // ===== BACKGROUND IMAGE =====
        Image bgImage = new Image(getClass().getResource("/scoreboard_bg.jpg").toExternalForm(), 1000, 650, false, true);
        BackgroundImage bg = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1000, 650, false, false, false, false)
        );

        // ===== FULL WINDOW SEMI-TRANSPARENT PANEL =====
        VBox panel = new VBox(15);
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setPadding(new Insets(30));
        panel.setStyle(
                "-fx-background-color: rgba(0,0,0,0.6);" +
                        "-fx-background-radius: 0;"
        );
        panel.setPrefSize(1000, 650);

        // ===== TITLE =====
        Label title = new Label("🏆 Top 5 Scores");
        title.setFont(Font.font("Papyrus", 36));
        title.setTextFill(Color.GOLD);
        title.setStyle("-fx-font-weight: bold;");
        panel.getChildren().add(title);

        // ===== SCORE LIST =====
        if (scores.isEmpty()) {
            Label emptyMsg = new Label("No scores yet!");
            emptyMsg.setFont(Font.font("Papyrus", 20));
            emptyMsg.setTextFill(Color.LIGHTGOLDENRODYELLOW);
            panel.getChildren().add(emptyMsg);
        } else {
            Collections.sort(scores);
            for (int i = 0; i < 5; i++) {
                String text = (i < scores.size())
                        ? (i + 1) + ". Score: " + scores.get(i).getScore() + " | Time: " + scores.get(i).getTime() + "s"
                        : (i + 1) + ". ---";
                Label lbl = new Label(text);
                lbl.setFont(Font.font("Papyrus", 20));
                lbl.setTextFill(Color.LIGHTGOLDENRODYELLOW);
                panel.getChildren().add(lbl);
            }
        }

        // ===== BACK BUTTON =====
        Button backBtn = new Button("Back to Home");
        backBtn.setPrefWidth(200);
        backBtn.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;");
        backBtn.setOnMouseEntered(e -> backBtn.setStyle("-fx-background-color: #63B8FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;"));
        backBtn.setOnMouseExited(e -> backBtn.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;"));
        backBtn.setOnAction(e -> new HomePage(stage).show());
        panel.getChildren().add(backBtn);

        // ===== CLEAR BUTTON (small, subtle, top-right) =====
        Button clearBtn = new Button("Clear Scores");
        clearBtn.setStyle(
                "-fx-background-color: rgba(0,0,0,0.5);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: normal;"
        );
        clearBtn.setOnAction(e -> {
            clearScores();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Scoreboard Cleared");
            alert.setHeaderText(null);
            alert.setContentText("All scores have been cleared!");
            alert.showAndWait();
            show(); // refresh scoreboard
        });

        StackPane.setAlignment(clearBtn, Pos.TOP_RIGHT);
        StackPane.setMargin(clearBtn, new Insets(10));

        // ===== STACKPANE =====
        StackPane root = new StackPane();
        root.setBackground(new Background(bg));
        root.getChildren().addAll(panel, clearBtn);

        Scene scene = new Scene(root, 1000, 650);
        stage.setScene(scene);
        stage.setTitle("Scoreboard - Treasure Hunt");
        stage.show();
    }
}
