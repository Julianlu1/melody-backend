package server.entity;

import javax.persistence.*;

@Entity
@Table(name = "played_sheet")
public class PlayedSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "sheet_music_id")
    private int sheetMusicId;

    @Column(name = "user_id")
    private int userId;

    public PlayedSheet() {
    }

    public PlayedSheet(int sheetMusicId, int userId) {
        this.sheetMusicId = sheetMusicId;
        this.userId = userId;
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
}
