package com.edacy.edacy.services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.edacy.edacy.entities.User;

public class UserDetailsImpl implements UserDetails{

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Ici on retourne les rôles, si tu les gères plus tard
        return Collections.emptyList(); // ou List.of(new SimpleGrantedAuthority("ROLE_USER"))
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // retourne le mot de passe hashé
    }

    @Override
    public String getUsername() {
        // Utilisez le même champ que celui utilisé pour l'authentification
        return user.getUsername(); // ou user.getEmail() selon votre choix
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // à personnaliser selon ton besoin
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
        return true; // tu peux ajouter un champ `active` dans User si besoin
    }
}
