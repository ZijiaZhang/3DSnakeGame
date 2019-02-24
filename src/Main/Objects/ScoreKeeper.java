package Objects;

public class ScoreKeeper {
    private int score=0;

    public int getScore() {
        return score;
    }

    public void addScore() {
        score++;
    }
    public void resetScore(){
        this.score=0;
    }
}
