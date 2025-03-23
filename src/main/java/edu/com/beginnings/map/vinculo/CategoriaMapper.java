package edu.com.beginnings.map.vinculo;


import edu.com.beginnings.dto.record.vinculo.CategoriaDTO;
import edu.com.beginnings.dto.record.vinculo.CategoriaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.CategoriaResponseDTO;
import edu.com.beginnings.model.vinculo.Categoria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    // MODEL DTO
    CategoriaDTO categoriaDTO(Categoria categoria);
    Categoria categoria(CategoriaDTO categoriaDTO);

    //req
    CategoriaRequestDTO categoriaRequestDTO(CategoriaRequestDTO categoriaRequestDTO);
    Categoria categoriaReq(CategoriaRequestDTO categoriaRequestDTO);

    //res
    CategoriaResponseDTO categoriaResponseDTO(Categoria categoria);
    Categoria categoriaRes(CategoriaResponseDTO categoriaResponseDTO);
}
