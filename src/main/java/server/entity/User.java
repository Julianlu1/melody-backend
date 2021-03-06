package server.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name="updated_at")
    private Date updatedAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    Set<PlayedSheetEntity> playedSheetEntities;

    public User() {
    }

    public User(String username, String password) {
        Date date = new Date();
        this.username = username;
        this.password = password;
        this.creationDate = date;
        this.updatedAt = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<PlayedSheetEntity> getPlayedSheetEntities() {
        return playedSheetEntities;
    }

    public void setPlayedSheetEntities(Set<PlayedSheetEntity> playedSheetEntities) {
        this.playedSheetEntities = playedSheetEntities;
    }
}
