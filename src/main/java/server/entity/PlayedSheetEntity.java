package server.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "played_sheet")
public class PlayedSheetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name="sheet_music_id")
    private SheetMusic sheetMusic;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    public PlayedSheetEntity() {
    }

    public PlayedSheetEntity(SheetMusic sheetMusic, User user) {
        this.sheetMusic = sheetMusic;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SheetMusic getSheetMusic() {
        return sheetMusic;
    }

    public void setSheetMusic(SheetMusic sheetMusic) {
        this.sheetMusic = sheetMusic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
