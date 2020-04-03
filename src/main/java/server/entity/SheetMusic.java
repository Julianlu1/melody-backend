package server.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="sheet_music")
public class SheetMusic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String title;

    private String componist;

    private String key;

    private String instrument;

    // Een sheetmusic heeft meerdere comments
    // mappedBy = "sheetMusic" is de variabele naam in de Comment entity (mappedBy = "sheetMusic")
    @OneToMany
    @JoinColumn(name="sheet_music_id")
    List<Comment> comments = new ArrayList<>();

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] pdf;

    public SheetMusic() {
    }

    // Aparate constructor voor het filteren
    // Filteren kan op componist, key en instrument
    public SheetMusic(String componist, String key, String instrument){
        this.componist = componist;
        this.key = key;
        this.instrument = instrument;
    }

    public SheetMusic(String title, String componist, String key, String instrument, byte[] pdf) {
        this.title = title;
        this.componist = componist;
        this.key = key;
        this.instrument = instrument;
        this.pdf = pdf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComponist() {
        return componist;
    }

    public void setComponist(String componist) {
        this.componist = componist;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
