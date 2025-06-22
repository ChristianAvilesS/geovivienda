package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.DireccionDTO;
import com.geovivienda.geovivienda.dtos.UsuarioDevueltoDTO;
import com.geovivienda.geovivienda.entities.Direccion;
import com.geovivienda.geovivienda.exceptions.LocationNotFoundException;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.externalapis.GeoapifyConnection;
import com.geovivienda.geovivienda.services.interfaces.IDireccionService;
import org.modelmapper.ModelMapper;
import com.geovivienda.geovivienda.dtos.UsuarioDTO;
import com.geovivienda.geovivienda.entities.Usuario;
import com.geovivienda.geovivienda.services.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UsuarioDevueltoDTO> obtenerUsuarios() {
        return servicio.listarUsuarios().stream()
                .map(p -> modelM.map(p, UsuarioDevueltoDTO.class)).collect(Collectors.toList());
    }

    @PostMapping // Cualquiera puede, sin autenticar
    public UsuarioDevueltoDTO agregarUsuario(@RequestBody UsuarioDTO dto, @RequestParam("rol") int rol) {
        Usuario usuario = modelM.map(dto, Usuario.class);
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setInactivo(false);
        if (dto.getDireccion().getIdDireccion() != null && dto.getDireccion().getIdDireccion() != 0) {
            usuario.setDireccion(dirService.buscarDireccionPorId(dto.getDireccion().getIdDireccion()));
        } else {
            DireccionDTO dtoDir = null;
            try {
                dtoDir = new GeoapifyConnection(modelM.map(dto.getDireccion(), DireccionDTO.class))
                        .getDireccionDTOAsociada();
            } catch (Exception e) {
                throw new LocationNotFoundException("No se encontró la dirección propuesta o el formato es incorrecto");
            }

            usuario.setDireccion(dirService.guardarDireccion(modelM.map(dtoDir, Direccion.class)));
        }

        return modelM.map(servicio.insertarUsuarioYRol(usuario, rol), UsuarioDevueltoDTO.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDevueltoDTO> obtenerUsuarioPorId(@PathVariable int id) {
        Usuario usuario = servicio.buscarUsuarioPorId(id);
        if (usuario != null) {
            return ResponseEntity.ok(modelM.map(usuario, UsuarioDevueltoDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró el id: " + id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDevueltoDTO> actualizarUsuario(@PathVariable int id,
                                                                @RequestBody UsuarioDevueltoDTO usuarioRecibido) {
        Usuario usuario = this.servicio.buscarUsuarioPorId(id);
        usuario.setNombre(usuarioRecibido.getNombre());
        usuario.setTelefono(usuarioRecibido.getTelefono());
        usuario.setDireccion(dirService.buscarDireccionPorId(usuarioRecibido.getDireccion().getIdDireccion()));
        usuario.setEmail(usuarioRecibido.getEmail());

        this.servicio.guardarUsuario(usuario);
        return ResponseEntity.ok(modelM.map(usuario, UsuarioDevueltoDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioDevueltoDTO> eliminarUsuario(@PathVariable int id) {
        var usuario = servicio.buscarUsuarioPorId(id);
        if (usuario == null) {
            throw new RecursoNoEncontradoException("No se encontró el id: " + id);
        }
        return ResponseEntity.ok(modelM.map(servicio.eliminarUsuario(usuario), UsuarioDevueltoDTO.class));
    }

    @GetMapping("/no-eliminados")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UsuarioDevueltoDTO> obtenerUsuariosNoEliminados() {
        return servicio.listarUsuariosNoEliminados().stream()
                .map(p -> modelM.map(p, UsuarioDevueltoDTO.class)).collect(Collectors.toList());
    }
}
