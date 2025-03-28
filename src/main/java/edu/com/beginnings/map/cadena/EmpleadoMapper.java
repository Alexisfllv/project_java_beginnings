package edu.com.beginnings.map.cadena;

import edu.com.beginnings.dto.record.cadena.EmpleadoDTO;
import edu.com.beginnings.dto.record.cadena.EmpleadoRequestDTO;
import edu.com.beginnings.dto.record.cadena.EmpleadoResponseDTO;
import edu.com.beginnings.model.cadena.Empleado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {

    EmpleadoDTO toEmpleadoDTO(Empleado empleado);
    Empleado toEmpleado(EmpleadoDTO empleadoDTO);

    //listado full

    EmpleadoResponseDTO toEmpleadoResponseDTO(Empleado empleado);
    // lista de empleadosDTOs =  List Empleado
    List<EmpleadoResponseDTO> toEmpleadoResponseDTOList(List<Empleado> empleados);

    Empleado toEmpleado(EmpleadoResponseDTO empleadoResponseDTO);

    EmpleadoRequestDTO toEmpleadoRequestDTO(Empleado empleado);
    @Mapping(target = "departamento.id" , source = "departamento")
    Empleado toEmpleado(EmpleadoRequestDTO empleadoRequestDTO);
}
