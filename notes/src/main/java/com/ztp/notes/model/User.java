package com.ztp.notes.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false)
    @Size(max = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "tinyint(1)")
    private boolean enabled;

    @OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE,
                           CascadeType.PERSIST, CascadeType.REFRESH },
               fetch = FetchType.LAZY,
               mappedBy = "primaryKey.user")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Authority> authorities;

    @OneToMany(mappedBy = "user",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Note> notes;

    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE,
                            CascadeType.PERSIST, CascadeType.REFRESH },
                fetch = FetchType.LAZY)
    @JoinTable(name = "users_censorship",
               joinColumns = @JoinColumn(name = "username"),
               inverseJoinColumns = @JoinColumn(name = "censored_phrase"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Censorship> censorship;

    public User() {}

    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(Authority authority) {
        if(authorities == null) {
            authorities = new ArrayList<>();
        }

        authorities.add(authority);
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void addNote(Note note) {
        if(notes == null) {
            notes = new ArrayList<>();
        }

        notes.add(note);
    }

    public List<Censorship> getCensorship() {
        return censorship;
    }

    public void setCensorship(List<Censorship> censorship) {
        this.censorship = censorship;
    }

    public void addCensorship(Censorship censorship) {
        if(this.censorship == null) {
            this.censorship = new ArrayList<>();
        }
        this.censorship.add(censorship);
    }

    public void removeCensorship(Censorship censorship) {
        String phrase = censorship.getCensoredPhrase();
        for(int i = 0; i < this.censorship.size(); i++) {
            if(this.censorship.get(i).getCensoredPhrase().equals(phrase)) {
                this.censorship.remove(i);
                break;
            }
        }
    }
}
