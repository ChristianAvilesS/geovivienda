package com.geovivienda.geovivienda.controllers;

import com.geovivienda.geovivienda.dtos.CantidadImagenesXInmuebleDTO;
import com.geovivienda.geovivienda.dtos.ImagenDTO;
import com.geovivienda.geovivienda.entities.Imagen;
import com.geovivienda.geovivienda.exceptions.RecursoNoEncontradoException;
import com.geovivienda.geovivienda.services.interfaces.IImagenService;
import com.geovivienda.geovivienda.services.interfaces.IInmuebleService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("geovivienda/imagenes")
public class ImagenController {
    private final ModelMapper modelM = new ModelMapper();

    @Autowired
    private IImagenService servicio;

    @Autowired
    private IInmuebleService inService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ImagenDTO> obtenerImagenes() {
        return servicio.listarImagenes().stream()
                .map(p -> modelM.map(p, ImagenDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('VENDEDOR', 'ADMIN')")
    public ImagenDTO agregarImagen(@RequestBody ImagenDTO dto) {
        Imagen imagen = modelM.map(dto, Imagen.class);
        imagen.setInmueble(inService.buscarInmueblePorId(dto.getInmueble().getIdInmueble()));
        imagen.setIdImagen(null);
        return modelM.map(this.servicio.guardarImagen(imagen), ImagenDTO.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagenDTO> obtenerImagenPorId(@PathVariable int id) {
        Imagen imagen = servicio.buscarImagenPorId(id);
        if (imagen != null) {
            return ResponseEntity.ok(modelM.map(imagen, ImagenDTO.class));
        }
        throw new RecursoNoEncontradoException("No se encontró el id: " + id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('VENDEDOR', 'ADMIN')")
    public ResponseEntity<Map<String, Boolean>> eliminarImagen(@PathVariable int id) {
        var imagen = servicio.buscarImagenPorId(id);
        servicio.eliminarImagen(imagen);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/cantidad")
    public List<CantidadImagenesXInmuebleDTO> obtenerCantidadImagenesXInmueble() {
        List<CantidadImagenesXInmuebleDTO> dtoLista = new ArrayList<>();
        List<String[]> filaLista = servicio.cantidadImagenesXInmueble();
        for (String[] columna : filaLista) {
            CantidadImagenesXInmuebleDTO dto = new CantidadImagenesXInmuebleDTO();
            dto.setNombreInmueble(columna[1]);
            dto.setCantidadImagenes(Integer.parseInt(columna[2]));
            dtoLista.add(dto);
        }
        return dtoLista;

    }

    @GetMapping("/buscarporinmueble/{id}")
    public List<ImagenDTO> obtenerCantidadImagenesXInmueble(@PathVariable int id) {
        return servicio.buscarPorIdInmueble(id).stream().map(imagen -> modelM.map(imagen, ImagenDTO.class))
                .collect(Collectors.toList());
    }

    private final String UPLOAD_DIR = System.getProperty("user.dir")+"/uploads/";

    @PostMapping("/subirimagen")
    public ResponseEntity<?> subirImagen(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Archivo vacío");
        }

        try {
            String nombreArchivo = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR, nombreArchivo);
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String url = baseUrl + "/uploads/" + nombreArchivo;

            return ResponseEntity.ok(Map.of("url", url));
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al guardar el archivo");
        }
    }

}
