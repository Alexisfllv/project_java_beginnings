package edu.com.beginnings.service.vinculo;

import edu.com.beginnings.dto.record.vinculo.CategoriaDTO;
import edu.com.beginnings.dto.record.vinculo.CategoriaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.CategoriaResponseDTO;
import edu.com.beginnings.model.vinculo.Categoria;

import java.util.List;

public interface CategoriaService {

    //listar
    List<CategoriaResponseDTO> listados();

    //buscar
    CategoriaResponseDTO buscar(Integer id);

    //agregar
    CategoriaResponseDTO registrar(CategoriaRequestDTO categoriaRequestDTO);

    //modificar
    CategoriaResponseDTO modificar(CategoriaDTO categoriaDTO,Integer id);

    //eliminar
    void eliminar(Integer id);


}
