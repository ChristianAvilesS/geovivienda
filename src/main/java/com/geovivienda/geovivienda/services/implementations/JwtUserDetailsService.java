package com.geovivienda.geovivienda.services.implementations;

import com.geovivienda.geovivienda.entities.Usuario;
import com.geovivienda.geovivienda.repositories.IRolUsuarioRepository;
import com.geovivienda.geovivienda.repositories.IUsuarioRepository;
import com.geovivienda.geovivienda.securities.UserDetailsExtra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


//Clase 2
@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private IUsuarioRepository usuarioRepos;

    @Autowired
    private IRolUsuarioRepository rolUsuarioRepos;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = usuarioRepos.findUsuarioByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User doesn't exist", username));
        }

        List<GrantedAuthority> roles = new ArrayList<>();

        rolUsuarioRepos.buscarRolesPorUsuario(user.getIdUsuario()).forEach(rolUsuario ->
                roles.add(new SimpleGrantedAuthority(rolUsuario.getRol().getRol())));

        return new UserDetailsExtra(user, roles, true, true, true);
    }
}