package com.geovivienda.geovivienda.securities;

import com.geovivienda.geovivienda.entities.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsExtra implements UserDetails {
    @Getter
    private final Integer idUsuario;
    private final String username;
    private final String password;
    private final boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean credentialsNonExpired;
    private final boolean accountNonLocked;


    public UserDetailsExtra(Usuario usuario, List<GrantedAuthority> authorities, boolean accountNonExpired
            , boolean credentialsNonExpired, boolean accountNonLocked) {
        this.idUsuario = usuario.getIdUsuario();
        this.username = usuario.getUsername();
        this.password = usuario.getPassword();
        this.enabled = !usuario.getInactivo();
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}