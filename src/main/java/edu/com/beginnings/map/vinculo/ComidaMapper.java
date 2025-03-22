package edu.com.beginnings.map.vinculo;


import edu.com.beginnings.dto.record.vinculo.ComidaDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaResponseDTO;
import edu.com.beginnings.model.vinculo.Comida;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComidaMapper {

    // DTO <- Entidad
    ComidaDTO comidaDTO(Comida comida);
    Comida comida(ComidaDTO comidaDTO);

    //response
    ComidaResponseDTO comidaResponseDTO(Comida comida);
    Comida comidaRes(ComidaResponseDTO comidaResponseDTO);

    //request
    ComidaRequestDTO comidaRequestDTO(Comida comida);

    @Mapping(target = "categoria.id" , source = "categoriaId")
    Comida comidaReq(ComidaRequestDTO comidaRequestDTO);

}
