package server.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name="sheet_music")
public class SheetMusic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String componist;

    private String key;

    private String instrument;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] pdf;

    public SheetMusic() {
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
}
