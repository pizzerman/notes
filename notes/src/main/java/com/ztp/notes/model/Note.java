package com.ztp.notes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ztp.notes.utility.LocalDateDeserializer;
import com.ztp.notes.utility.LocalDateSerializer;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @Size(min = 1, max = 50, message = "Tytuł nie może być pusty/dłuższy niż 50 znaków.")
    private String title;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @Column(nullable = false)
    @Size(min = 1, max = 255, message = "Treść nie może być pusta/dłuższa niż 255 znaków.")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    public Note() {}

    public Note(@Size(max = 50) String title, LocalDate date, @Size(max = 255) String text) {
        this.title = title;
        this.date = date;
        this.text = text;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Note) {
            Note otherNote = (Note) obj;
            return id == otherNote.getId()
                    && title.equalsIgnoreCase(otherNote.getTitle())
                    && text.equalsIgnoreCase(otherNote.getText())
                    && date.isEqual(otherNote.getDate());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, text);
    }
}
