package server.entity;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    private User user;
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

    public Comment(SheetMusic sheetMusic, User user, String description, double score) {
        this.sheetMusic = sheetMusic;
        this.user = user;
        this.description = description;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getUsername(){
        return this.user.getUsername();
    }

    // Dit laat de sheet music id zien in json
    public int getSheet_music_id(){
        return this.sheetMusic.getId();
    }

    public void setId(int id) {
        this.id = id;
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
