package server.entity;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

//    @ManyToOne
//    private User user;
//
//    public int getUsername(){
//        return user.getUsername();
//    }

    private String description;

    private double score;

    // Een comment hoort maar bij 1 sheetmusic
    // Relatie op basis van het sheet_music_id
    @ManyToOne
    private SheetMusic sheetMusic;

    public Comment() {
    }

    public Comment(SheetMusic sheetMusic, int userId, String description, double score) {
        this.sheetMusic = sheetMusic;
        this.userId = userId;
        this.description = description;
        this.score = score;
    }

    public int getId() {
        return id;
    }


    // Dit laat de sheet music id zien in json
    public int getSheet_music_id(){
        return this.sheetMusic.getId();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
