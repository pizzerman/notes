package com.ztp.notes.model;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.user",
                joinColumns = @JoinColumn(name = "username")),
        @AssociationOverride(name = "primaryKey.authority",
                joinColumns = @JoinColumn(name = "authority"))
})
public class Authority {

    @EmbeddedId
    private AuthorityId primaryKey;

    public Authority() {}

    public AuthorityId getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(AuthorityId primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Transient
    public User getUser() {
        return getPrimaryKey().getUser();
    }

    public void setUser(User user) {
        getPrimaryKey().setUser(user);
    }

    @Transient
    public String getAuthority() {
        return getPrimaryKey().getAuthority();
    }

    public void setAuthority(String authority) {
        getPrimaryKey().setAuthority(authority);
    }
}
