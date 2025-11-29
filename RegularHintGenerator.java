import java.util.Random;

public class RegularHintGenerator {

    private final int treasureRow, treasureCol;
    private final boolean[][] isTrap;
    private final Random rand = new Random();

    private final String[] warmPhrases = {
            "You're getting warmer!",
            "Close enough to smell the gold!",
            "The treasure senses your presence!",
            "Almost there — stay sharp!",
            "Your instincts are on fire!",
            "The air feels charged… you’re close!",
            "A faint golden glow shimmers ahead.",
            "You can almost hear the treasure whisper your name."
    };

    private final String[] coldPhrases = {
            "The trail grows cold.",
            "Not quite right… try another path.",
            "You drift away from the treasure’s aura.",
            "Colder… think again.",
            "Nothing but silence that way.",
            "The wind feels empty here.",
            "Your steps echo in the wrong path.",
            "No glimmer in this direction."
    };

    private final String[] trapPhrases = {
            "You sense danger nearby… perhaps a trap lies close.",
            "The ground feels unstable — tread carefully.",
            "A faint clicking sound warns of hidden danger.",
            "That area feels cursed… proceed with caution.",
            "The air smells of metal — a trap may lurk here.",
            "Something feels off beneath your feet.",
            "You feel eyes watching from the shadows.",
            "A strange hum warns you to step lightly."
    };

    private int lastDistance = -1;

    public RegularHintGenerator(int treasureRow, int treasureCol, boolean[][] isTrap) {
        this.treasureRow = treasureRow;
        this.treasureCol = treasureCol;
        this.isTrap = isTrap;
    }

    public String getHint(int playerRow, int playerCol) {
        int distance = Math.abs(treasureRow - playerRow) + Math.abs(treasureCol - playerCol);

        // Warm/cold phrase based on distance change
        String tempPhrase;
        if (lastDistance != -1) {
            if (distance < lastDistance) tempPhrase = warmPhrases[rand.nextInt(warmPhrases.length)];
            else if (distance > lastDistance) tempPhrase = coldPhrases[rand.nextInt(coldPhrases.length)];
            else tempPhrase = "No change in proximity.";
        } else {
            tempPhrase = "Start your quest!";
        }
        lastDistance = distance;

        // Trap nearby check
        boolean trapNearby = false;
        for (int dr = -1; dr <= 1 && !trapNearby; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int nr = playerRow + dr, nc = playerCol + dc;
                if (nr >= 0 && nr < isTrap.length && nc >= 0 && nc < isTrap[0].length && isTrap[nr][nc]) {
                    trapNearby = true;
                    break;
                }
            }
        }
        String trapPhrase = trapNearby ? " ⚠ " + trapPhrases[rand.nextInt(trapPhrases.length)] : "";

        // Combine
        return tempPhrase + trapPhrase;
    }
}
