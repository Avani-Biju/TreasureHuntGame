import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.Random;

public class TreasureHuntGame {
    private final Stage stage;
    private final int gridSize = 5;
    private final Button[][] cells = new Button[gridSize][gridSize];
    private final boolean[][] isTrap = new boolean[gridSize][gridSize];
    private int treasureRow, treasureCol;
    private int score;
    private Label scoreLabel;
    private Label timerLabel;
    private Label bottomHintLabel;
    private Label advHintText;
    private Button advHintBtn;
    private Label topScoreLabel;

    private final Random rand = new Random();
    private boolean treasureFound = false;
    private boolean advancedHintUsed = false;
    private GameTimer timer;

    private RegularHintGenerator hintGenerator;

    // Track player's last clicked position
    private int lastClickedRow = -1;
    private int lastClickedCol = -1;

    public TreasureHuntGame(Stage stage) {
        this.stage = stage;
        setupGame();
    }

    private void setupGame() {
        score = 20;
        treasureRow = rand.nextInt(gridSize);
        treasureCol = rand.nextInt(gridSize);

        for (int i = 0; i < gridSize; i++)
            for (int j = 0; j < gridSize; j++)
                if ((i != treasureRow || j != treasureCol) && rand.nextDouble() < 0.15)
                    isTrap[i][j] = true;

        hintGenerator = new RegularHintGenerator(treasureRow, treasureCol, isTrap);
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #222;");

        // ===== GRID CENTER =====
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10));

        for (int r = 0; r < gridSize; r++) {
            for (int c = 0; c < gridSize; c++) {
                Button cell = new Button("?");
                cell.setPrefSize(70, 70);
                cell.setFont(Font.font("Montserrat", 18));
                cell.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-weight: bold;");
                int row = r, col = c;
                cell.setOnAction(e -> handleCellClick(row, col));
                cells[r][c] = cell;
                grid.add(cell, c, r);
            }
        }
        root.setCenter(grid);

        // ===== SIDEBAR RIGHT =====
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(15));
        sidebar.setStyle("-fx-background-color: #111;");
        sidebar.setPrefWidth(250);
        sidebar.setAlignment(Pos.TOP_CENTER);

        scoreLabel = new Label("Score: " + score);
        scoreLabel.setTextFill(Color.GOLD);
        scoreLabel.setFont(Font.font("Montserrat", 18));
        scoreLabel.setStyle("-fx-font-weight: bold;");

        timerLabel = new Label("⏰ Time: 60s");
        timerLabel.setTextFill(Color.LIGHTGOLDENRODYELLOW);
        timerLabel.setFont(Font.font("Montserrat", 18));
        timerLabel.setStyle("-fx-font-weight: bold;");

        advHintBtn = new Button("Use Advanced Hint");
        advHintBtn.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-font-weight: bold;");
        advHintBtn.setOnAction(e -> provideAdvancedHint());
        advHintBtn.setDisable(true);

        advHintText = new Label("");
        advHintText.setTextFill(Color.RED);
        advHintText.setWrapText(true);
        advHintText.setFont(Font.font("Montserrat", 14));
        advHintText.setStyle("-fx-font-weight: bold;");

        topScoreLabel = new Label("Top Score: " + getTopScore());
        topScoreLabel.setTextFill(Color.LIGHTGREEN);
        topScoreLabel.setFont(Font.font("Montserrat", 16));
        topScoreLabel.setStyle("-fx-font-weight: bold;");

        sidebar.getChildren().addAll(scoreLabel, timerLabel, advHintBtn, advHintText, topScoreLabel);
        root.setRight(sidebar);

        // ===== BOTTOM HINT BOX =====
        VBox bottomBox = new VBox(10);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));

        bottomHintLabel = new Label("Find the treasure!");
        bottomHintLabel.setTextFill(Color.ORANGE);
        bottomHintLabel.setFont(Font.font("Montserrat", 16));
        bottomHintLabel.setStyle(
                "-fx-font-weight: bold; " +
                        "-fx-background-color: #333; " +
                        "-fx-padding: 10px; " +
                        "-fx-border-color: gold; " +
                        "-fx-border-width: 3px; " +
                        "-fx-border-radius: 8; " +
                        "-fx-background-radius: 8;"
        );
        bottomHintLabel.setMaxWidth(Double.MAX_VALUE);
        bottomHintLabel.setAlignment(Pos.CENTER);

        Button backBtn = new Button("Back to Home");
        backBtn.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-font-weight: bold;");
        backBtn.setOnAction(e -> {
            treasureFound = true;
            if (timer != null) timer.stop();
            new HomePage(stage).show();
        });

        Button restartBtn = new Button("Start Again");
        restartBtn.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-font-weight: bold;");
        restartBtn.setOnAction(e -> {
            bottomHintLabel.setText("Restarting game...");
            treasureFound = true;
            if (timer != null) timer.stop();
            new TreasureHuntGame(stage).show();
        });

        HBox buttonsBox = new HBox(10, backBtn, restartBtn);
        buttonsBox.setAlignment(Pos.CENTER);

        bottomBox.getChildren().addAll(bottomHintLabel, buttonsBox);
        root.setBottom(bottomBox);

        Scene scene = new Scene(root, 1000, 650);
        stage.setScene(scene);
        stage.setTitle("Treasure Hunt - Game");
        stage.show();

        // ===== START TIMER =====
        timer = new GameTimer(timerLabel, 60, () -> {
            if (!treasureFound) showGameOver("Time's up! You failed!");
        });
        timer.start();
    }

    private void handleCellClick(int row, int col) {
        lastClickedRow = row;
        lastClickedCol = col;

        if (treasureFound) return;

        Button cell = cells[row][col];

        if (row == treasureRow && col == treasureCol) {
            cell.setStyle("-fx-background-color: gold; -fx-text-fill: black; -fx-font-size:18px; -fx-font-weight: bold;");
            cell.setText("💎");
            score += 20;
            scoreLabel.setText("Score: " + score);
            bottomHintLabel.setText("🎉 Congratulations! You found the treasure!");
            treasureFound = true;
            timer.stop();
            disableAllCells();
            updateTopScore();
        } else if (isTrap[row][col]) {
            cell.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size:18px; -fx-font-weight: bold;");
            cell.setText("T");
            score = Math.max(0, score - 5);
            scoreLabel.setText("Score: " + score);
            bottomHintLabel.setText("Oops! You hit a trap! Be careful!");
            cell.setDisable(true);
        } else {
            cell.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-font-size:18px; -fx-font-weight: bold;");
            cell.setText("X");
            score = Math.max(0, score - 1);
            scoreLabel.setText("Score: " + score);

            String hint = hintGenerator.getHint(row, col);
            bottomHintLabel.setText(hint);

            cell.setDisable(true);
        }

        // Enable advanced hint only once
        if (!advancedHintUsed && score <= 5) advHintBtn.setDisable(false);

        if (score <= 0 && !treasureFound) showGameOver("Your score dropped to 0! You failed!");
    }

    private void provideAdvancedHint() {
        if (advancedHintUsed || lastClickedRow == -1) return;

        advancedHintUsed = true;
        advHintBtn.setDisable(true);

        int dr = treasureRow - lastClickedRow;
        int dc = treasureCol - lastClickedCol;

        StringBuilder hint = new StringBuilder("✨ Advanced Hint: ");
        if (dr != 0) hint.append(Math.abs(dr)).append(" step").append(Math.abs(dr) > 1 ? "s" : "").append(" ")
                .append(dr > 0 ? "down" : "up");
        if (dr != 0 && dc != 0) hint.append(" and ");
        if (dc != 0) hint.append(Math.abs(dc)).append(" step").append(Math.abs(dc) > 1 ? "s" : "").append(" ")
                .append(dc > 0 ? "right" : "left");
        if (dr == 0 && dc == 0) hint.append("You are standing on the treasure!");

        hint.append(" — the shadows whisper your path...");

        advHintText.setText(hint.toString());
    }

    private void disableAllCells() {
        for (Button[] row : cells)
            for (Button b : row) b.setDisable(true);
    }

    private void showGameOver(String message) {
        treasureFound = true;
        disableAllCells();
        if (timer != null) timer.stop();

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #222; -fx-padding: 20;");

        Label msg = new Label(message);
        msg.setTextFill(Color.RED);
        msg.setFont(Font.font("Montserrat", 24));
        msg.setStyle("-fx-font-weight: bold;");

        Button retryBtn = new Button("Retry");
        retryBtn.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-font-weight: bold;");
        retryBtn.setOnAction(e -> new TreasureHuntGame(stage).show());

        Button backBtn = new Button("Back to Home");
        backBtn.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-font-weight: bold;");
        backBtn.setOnAction(e -> new HomePage(stage).show());

        layout.getChildren().addAll(msg, retryBtn, backBtn);

        Scene scene = new Scene(layout, 1000, 650);
        stage.setScene(scene);
    }

    private void updateTopScore() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("topScores.txt", true));
            bw.write(score + "," + (60 - timer.seconds));
            bw.newLine();
            bw.close();

            topScoreLabel.setText("Top Score: " + getTopScore());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getTopScore() {
        File file = new File("topScores.txt");
        if (!file.exists()) return 0;

        int maxScore = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1) {
                    int s = Integer.parseInt(parts[0]);
                    if (s > maxScore) maxScore = s;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxScore;
    }

    private static class GameTimer {
        private Label label;
        private int seconds;
        private Runnable onTimeUp;
        private Timeline timeline;

        public GameTimer(Label label, int seconds, Runnable onTimeUp) {
            this.label = label;
            this.seconds = seconds;
            this.onTimeUp = onTimeUp;
        }

        public void start() {
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                seconds--;
                label.setText("⏰ Time: " + seconds + "s");
                if (seconds <= 0) {
                    stop();
                    onTimeUp.run();
                }
            }));
            timeline.setCycleCount(Math.max(1, seconds));
            timeline.play();
        }

        public void stop() {
            if (timeline != null) timeline.stop();
        }
    }
}
