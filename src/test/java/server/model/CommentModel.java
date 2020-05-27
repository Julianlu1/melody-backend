package server.model;

public class CommentModel {
    private int sheetId;

    private String description;

    private double score;

    public CommentModel(int sheetMusicId, String description, double score) {
        this.sheetId = sheetMusicId;
        this.description = description;
        this.score = score;
    }

    public int getSheetMusicId() {
        return sheetId;
    }

    public void setSheetMusicId(int sheetMusicId) {
        this.sheetId = sheetMusicId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
