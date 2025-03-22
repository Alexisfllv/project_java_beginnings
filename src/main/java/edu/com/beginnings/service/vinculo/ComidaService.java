package edu.com.beginnings.service.vinculo;

import edu.com.beginnings.dto.record.vinculo.ComidaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaResponseDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaUpdateDTO;
import edu.com.beginnings.model.vinculo.Comida;

import java.util.List;

public interface ComidaService {

    //listar Comidas con detalles
    List<ComidaResponseDTO> listarComidas();


    //buscar por id de comidas
    ComidaResponseDTO buscarComida(Integer id);

    //metodo de registrar comida
    ComidaResponseDTO registrarComida(ComidaRequestDTO comidaRequestDTO);

    //modificar comida
    ComidaResponseDTO modificarComida(ComidaUpdateDTO comidaUpdateDTO , Integer id);

    //eliminar comiad
    void eliminarComida(Integer id);




}
