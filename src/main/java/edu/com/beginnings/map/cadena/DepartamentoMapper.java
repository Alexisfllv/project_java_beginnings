package edu.com.beginnings.map.cadena;

import edu.com.beginnings.dto.record.cadena.DepartamentoDTO;
import edu.com.beginnings.dto.record.cadena.DepartamentoRequestDTO;
import edu.com.beginnings.dto.record.cadena.DepartamentoResponseDTO;
import edu.com.beginnings.model.cadena.Departamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepartamentoMapper {

    //
    DepartamentoDTO toDepartamentoDTO(Departamento departamento);
    Departamento toDepartamento(DepartamentoDTO departamentoDTO);

    DepartamentoResponseDTO toDepartamentoResponseDTO(Departamento departamento);
    Departamento toDepartamento(DepartamentoResponseDTO departamentoResponseDTO);

    DepartamentoRequestDTO toDepartamentoRequestDTO(Departamento departamento);

    @Mapping(target = "empresa.id" , source = "empresaId")
    Departamento toDepartamento(DepartamentoRequestDTO departamentoRequestDTO);


}
