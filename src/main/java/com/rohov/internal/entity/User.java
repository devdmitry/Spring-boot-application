package com.rohov.internal.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@Entity
public class User implements UserDetails {

    @Id
    Long id = UUID.randomUUID().getMostSignificantBits() & Integer.MAX_VALUE;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = UserProfile.class, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    UserProfile userProfile;

    @Column(name="email")
    String email;

    @Column(name="password")
    String password;

    @Column(name = "reset_token")
    String resetToken;

    @Column(name = "verification_token")
    String verificationToken;

    @Column(name = "is_verified")
    Boolean isVerified;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = { @JoinColumn (name = "user_id")},
            inverseJoinColumns = { @JoinColumn(name = "role_id")}
    )
    Set<Role> roles;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<UserProjectPermission> projectPermissions;

    @Column(name = "created_date")
    Date created_date = Date.from(Instant.now());

    @Column(name = "updated_date")
    Date updated_date = Date.from(Instant.now());

    @Column(name = "is_deleted")
    boolean isDeleted = false;

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId().equals(((User)obj).id);
    }

    @Override
    public int hashCode() {
        if (id != null) return id.hashCode();
        return -1;
    }
}

