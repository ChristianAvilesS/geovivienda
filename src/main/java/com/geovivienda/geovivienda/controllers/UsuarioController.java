package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.UsuarioLoginDTO;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.exceptions.UsuarioInactivoException;
import org.modelmapper.ModelMapper;
import com.geovivienda.geovivienda.dtos.UsuarioDTO;
import com.geovivienda.geovivienda.entities.Usuario;
import com.geovivienda.geovivienda.services.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("geovivienda/usuarios")
public class UsuarioController {
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IUsuarioService servicio;

    @GetMapping
    public List<UsuarioDTO> obtenerUsuarios() {
        return servicio.listarUsuarios().stream()
                .map(p -> modelM.map(p, UsuarioDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    public UsuarioDTO agregarUsuario(@RequestBody UsuarioDTO dto) {
        return modelM.map(this.servicio.guardarUsuario(modelM.map(dto, Usuario.class)), UsuarioDTO.class);
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

    @GetMapping("/verificar")
    public ResponseEntity<UsuarioDTO> verificarUsuario(@RequestBody UsuarioLoginDTO dto) {
        Usuario usuario = servicio.verificarLogin(modelM.map(dto, Usuario.class));
        if (usuario != null) {
            if (usuario.getInactivo()) {
                throw new UsuarioInactivoException("El usuario ha sido eliminado");
            }
            return ResponseEntity.ok(modelM.map(usuario, UsuarioDTO.class));

        }
        throw new RecursoNoEncontradoException("El usuario o contraseña es incorrecto");
    }


}
