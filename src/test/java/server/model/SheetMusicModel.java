package server.model;

import server.entity.Comment;

import java.util.ArrayList;
import java.util.List;

public class SheetMusicModel {
    private int id;

    private String title;

    private String componist;

    private String key;

    private List<Comment> comments = new ArrayList<>();

    private String pdf;

    private String instrument_Description;



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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getInstrument_Description() {
        return instrument_Description;
    }

    public void setInstrument_Description(String instrument_Description) {
        this.instrument_Description = instrument_Description;
    }
}
