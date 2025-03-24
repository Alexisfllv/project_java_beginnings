package edu.com.beginnings.service.vinculo;

import edu.com.beginnings.dto.record.vinculo.ComidaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaResponseDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaUpdateDTO;
import edu.com.beginnings.model.vinculo.Comida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ComidaService {

    //listar Comidas con detalles
    List<ComidaResponseDTO> listarComidas();

    //listar Con paginador //poli
    Page<ComidaResponseDTO> listarComidas(Pageable pageable);


    //buscar por id de comidas
    ComidaResponseDTO buscarComida(Integer id);

    //metodo de registrar comida
    ComidaResponseDTO registrarComida(ComidaRequestDTO comidaRequestDTO);

    //modificar comida
    Map<String, Object>modificarComida(ComidaUpdateDTO comidaUpdateDTO, Integer id);
    //ComidaResponseDTO modificarComida(ComidaUpdateDTO comidaUpdateDTO , Integer id);

    //eliminar comiad
    void eliminarComida(Integer id);




}
