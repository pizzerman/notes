package com.ztp.notes.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Censorship {

    @Id
    @Column(nullable = false)
    @Size(min = 1, max = 45, message = "Zwrot nie może być pusty/dłuższy niż 45 znaków.")
    private String censoredPhrase;

    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE,
                            CascadeType.PERSIST, CascadeType.REFRESH },
                fetch = FetchType.LAZY)
    @JoinTable(name = "users_censorship",
               joinColumns = @JoinColumn(name = "censored_phrase"),
               inverseJoinColumns = @JoinColumn(name = "username"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<User> users;

    public Censorship () {}

    public Censorship(@Size(max = 45) String censoredPhrase) {
        this.censoredPhrase = censoredPhrase;
    }

    public String getCensoredPhrase() {
        return censoredPhrase;
    }

    public void setCensoredPhrase(String censoredPhrase) {
        this.censoredPhrase = censoredPhrase;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        if(users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }

    public void removeUser(User user) {
        String username = user.getUsername();
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getUsername().equals(username)) {
                users.remove(i);
                break;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Censorship) {
            Censorship otherCensorship = (Censorship) obj;
            return censoredPhrase.equalsIgnoreCase(otherCensorship.getCensoredPhrase());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(censoredPhrase);
    }
}
