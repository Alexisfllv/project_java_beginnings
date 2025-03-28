package edu.com.beginnings.map.cadena;

import edu.com.beginnings.dto.record.cadena.EmpresaDTO;
import edu.com.beginnings.dto.record.cadena.EmpresaRequestDTO;
import edu.com.beginnings.dto.record.cadena.EmpresaResponseDTO;
import edu.com.beginnings.model.cadena.Empresa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {

    EmpresaDTO toEmpresaDTO(Empresa empresa);
    Empresa toEmpresa(EmpresaDTO empresaDTO);

    EmpresaResponseDTO toEmpresaResponseDTO(Empresa empresa);
    Empresa toEmpresa(EmpresaResponseDTO empresaResponseDTO);

    EmpresaRequestDTO toEmpresaRequestDTO(Empresa empresa);
    Empresa toEmpresa(EmpresaRequestDTO empresaRequestDTO);
}
