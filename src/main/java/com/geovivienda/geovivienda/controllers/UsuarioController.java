package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IDireccionService;
import org.modelmapper.ModelMapper;
import com.geovivienda.geovivienda.dtos.UsuarioDTO;
import com.geovivienda.geovivienda.entities.Usuario;
import com.geovivienda.geovivienda.services.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("geovivienda/usuarios")
public class UsuarioController {
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IUsuarioService servicio;

    @Autowired
    private IDireccionService dirService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UsuarioDTO> obtenerUsuarios() {
        return servicio.listarUsuarios().stream()
                .map(p -> modelM.map(p, UsuarioDTO.class)).collect(Collectors.toList());
    }

    @PostMapping // Cualquiera puede, sin autenticar
    public UsuarioDTO agregarUsuario(@RequestBody UsuarioDTO dto) {
        Usuario usuario = modelM.map(dto, Usuario.class);
        if (usuario.getDireccion().getIdDireccion() != 0) {
            usuario.setDireccion(dirService.buscarDireccionPorId(usuario.getDireccion().getIdDireccion()));
        }
        else {
            usuario.setDireccion(dirService.guardarDireccion(usuario.getDireccion()));
        }
        return modelM.map(servicio.guardarUsuario(usuario), UsuarioDTO.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable int id) {
        Usuario usuario = servicio.buscarUsuarioPorId(id);
        if (usuario != null) {
            return ResponseEntity.ok(modelM.map(usuario, UsuarioDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró el id: " + id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable int id, @RequestBody UsuarioDTO usuarioRecibido) {
        Usuario usuario = this.servicio.buscarUsuarioPorId(id);
        usuario.setNombre(usuarioRecibido.getNombre());
        usuario.setTelefono(usuarioRecibido.getTelefono());
        usuario.setDireccion(usuarioRecibido.getDireccion());
        usuario.setEmail(usuarioRecibido.getEmail());

        this.servicio.guardarUsuario(usuario);
        return ResponseEntity.ok(modelM.map(usuario, UsuarioDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioDTO> eliminarUsuario(@PathVariable int id) {
        var usuario = servicio.buscarUsuarioPorId(id);
        if (usuario == null) {
            throw new RecursoNoEncontradoException("No se encontró el id: " + id);
        }
        servicio.eliminarUsuario(usuario);
        return ResponseEntity.ok(modelM.map(usuario, UsuarioDTO.class));
    }


}
