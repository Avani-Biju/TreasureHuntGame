import java.io.Serializable;

public class ScoreEntry implements Comparable<ScoreEntry>, Serializable {

    private int score;
    private int time; // time taken in seconds

    public ScoreEntry(int score, int time) {
        this.score = score;
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public int getTime() {
        return time;
    }

    @Override
    public int compareTo(ScoreEntry other) {
        // Higher score comes first
        if (this.score != other.score)
            return other.score - this.score;
        // If scores are equal, smaller time comes first
        return this.time - other.time;
    }

    @Override
    public String toString() {
        return score + "," + time;
    }
}
