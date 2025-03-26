package edu.com.beginnings.map.vinculo;


import edu.com.beginnings.dto.record.vinculo.pedidos.SuministroDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.SuministroRequestDTO;
import edu.com.beginnings.model.vinculo.Suministro;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuministroMapper {

    //dto
    SuministroDTO toSuministroDTO(Suministro suministro);
    Suministro toSuministro(SuministroDTO suministroDTO);

    SuministroRequestDTO toSuministroRequestDTO(Suministro suministro);
    Suministro toSuministro(SuministroRequestDTO suministro);
}
