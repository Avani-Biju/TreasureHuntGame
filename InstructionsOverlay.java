import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class InstructionsOverlay {

    public static void show(Stage stage, Runnable onContinue) {
        // ===== Create overlay covering full window =====
        StackPane overlay = new StackPane();
        overlay.setPrefSize(1000, 650); // match game window

        // ===== Load background image safely =====
        Image bgImage = null;
        try {
            bgImage = new Image(InstructionsOverlay.class.getResource("/instructions_bg.jpg").toExternalForm(),
                    1000, 650, false, true);
        } catch (Exception e) {
            System.out.println("Warning: Instruction background image not found!");
        }

        if (bgImage != null) {
            BackgroundImage bg = new BackgroundImage(bgImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(1000, 650, false, false, false, false));
            overlay.setBackground(new Background(bg));
        } else {
            overlay.setStyle("-fx-background-color: rgba(0,0,0,0.85);"); // fallback dim
        }

        // ===== Instruction panel with yellow border =====
        VBox panel = new VBox(20);
        panel.setAlignment(Pos.CENTER);
        panel.setPrefSize(900, 500); // slightly smaller than window
        panel.setStyle(
                "-fx-background-color: rgba(0,0,0,0.80);" + // semi-transparent over image
                        "-fx-border-color: black;" +
                        "-fx-border-width: 4;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 20;"
        );

        // ===== Title =====
        Label title = new Label("🏴‍☠️ The Treasure Awaits!");
        title.setTextFill(Color.GOLD);
        title.setFont(Font.font("Papyrus", 32));
        title.setAlignment(Pos.CENTER);

        Label story = new Label(
                "Legends tell of a hidden treasure buried somewhere in this grid.\n" +
                        "Click the cells to search for gold, but beware — traps lie in wait!\n" +
                        "You have only 60 seconds, so move quickly.\n" +
                        "Hints are rare and valuable, so use them wisely.\n\n" +
                        "Trust your instincts, stay brave, and may luck guide you to the treasure!"
        );
        story.setFont(Font.font("Garamond", 18)); // changed from Montserrat to Papyrus
        story.setTextFill(Color.WHITE);
        story.setWrapText(true);
        story.setAlignment(Pos.CENTER);



        // ===== Continue Button =====
        Button continueBtn = new Button("READY 🚀");
        continueBtn.setFont(Font.font("Georgia", 16)); // slightly smaller than before
        continueBtn.setTextFill(Color.BLACK);
        continueBtn.setStyle("-fx-background-color: gold; -fx-font-weight: bold;");
        continueBtn.setPrefWidth(180);  // smaller width
        continueBtn.setPrefHeight(40);  // smaller height
        continueBtn.setAlignment(Pos.CENTER);

// Hover effect (keeps same size)
        continueBtn.setOnMouseEntered(e -> continueBtn.setStyle(
                "-fx-background-color: #FFD700; -fx-font-weight: bold; -fx-text-fill: black;"
        ));
        continueBtn.setOnMouseExited(e -> continueBtn.setStyle(
                "-fx-background-color: gold; -fx-font-weight: bold; -fx-text-fill: black;"
        ));

        // ===== Add elements to panel =====
        panel.getChildren().addAll(title, story, continueBtn);
        overlay.getChildren().add(panel);
        overlay.setAlignment(Pos.CENTER);

        // ===== Add overlay to current HomePage root =====
        StackPane root = (StackPane) stage.getScene().getRoot();
        root.getChildren().add(overlay);

        // ===== Continue button action =====
        continueBtn.setOnAction(e -> {
            root.getChildren().remove(overlay); // remove overlay
            if (onContinue != null) onContinue.run(); // start the game
        });
    }
}
