package server.entity;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sheet_music_id")
    private int sheetMusicId;

    @Column(name = "user_id")
    private int userId;

    private String title;

    private String description;

    private int score;

    public Comment() {
    }

    public Comment(int sheetMusicId, int userId, String title, String description, int score) {
        this.sheetMusicId = sheetMusicId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSheetMusicId() {
        return sheetMusicId;
    }

    public void setSheetMusicId(int sheetMusicId) {
        this.sheetMusicId = sheetMusicId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
