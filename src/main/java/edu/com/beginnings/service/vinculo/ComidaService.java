package edu.com.beginnings.service.vinculo;

import edu.com.beginnings.dto.record.vinculo.ComidaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaResponseDTO;
import edu.com.beginnings.model.vinculo.Comida;

public interface ComidaService {

    //metodo de registrar comida
    ComidaResponseDTO registrarComida(ComidaRequestDTO comidaRequestDTO);
}
