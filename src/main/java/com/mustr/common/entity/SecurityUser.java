package com.mustr.common.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User {
    private static final long serialVersionUID = 258745813215684366L;
    private final long id;
    private final String name;

    public SecurityUser(long id, String name, String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        this(id, name, username, password, true, true, true, true, authorities);
    }

    public SecurityUser(long id, String name, String username, String password, boolean enabled,
            boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SecurityUser [id=" + id + ", name=" + name + ", getAuthorities()=" + getAuthorities()
                + ", getPassword()=" + getPassword() + ", getUsername()=" + getUsername() + ", isEnabled()="
                + isEnabled() + "]";
    }
    
}
