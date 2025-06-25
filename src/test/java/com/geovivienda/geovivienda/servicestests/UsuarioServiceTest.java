package com.geovivienda.geovivienda.servicestests;

import com.geovivienda.geovivienda.entities.Usuario;
import com.geovivienda.geovivienda.repositories.IUsuarioRepository;
import com.geovivienda.geovivienda.services.implementations.UsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private IUsuarioRepository repos;

    @InjectMocks
    private UsuarioServiceImpl service;

    @Test
    public void testBuscarUsuarioPorId() {
        // Arrange
        Usuario mockUser = new Usuario();
        mockUser.setIdUsuario(1);
        mockUser.setNombre("Christian Avilés");
        when(repos.findById(1)).thenReturn(Optional.of(mockUser));

        // Act
        String result = service.buscarUsuarioPorId(1).getNombre();

        // Assert
        assertEquals("Christian Avilés", result);
    }
}
