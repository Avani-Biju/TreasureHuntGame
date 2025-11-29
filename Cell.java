import javafx.scene.control.Button;

public class Cell {
    public boolean isTreasure = false;
    public boolean isTrap = false;
    public boolean isHint = false;
    public String hint = "";
    public String answer = "";
    public Button button;
    public int row, col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        button = new Button("?");
        button.setPrefSize(60, 60);
    }
}
