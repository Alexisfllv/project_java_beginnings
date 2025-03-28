package edu.com.beginnings.service.cadena;


import edu.com.beginnings.dto.record.cadena.EmpleadoRequestDTO;
import edu.com.beginnings.dto.record.cadena.EmpleadoResponseDTO;


import edu.com.beginnings.dto.record.cadena.EmpleadoUpdateDTO;
import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import org.springframework.data.domain.Pageable;


import java.util.List;


public interface EmpleadoService{

    //listar
    List<EmpleadoResponseDTO> listadoEmpleados();

    //listado con paginador
    PaginaRespuestaDTO<EmpleadoResponseDTO> listadoEmpleadoPaginado(Pageable pageable);

    //buscar x id
    EmpleadoResponseDTO empleadoPorId(Integer id);

    //registrar
    EmpleadoResponseDTO registrarEmpleado(EmpleadoRequestDTO empleadoRequestDTO);

    //registro con mensaje
    RespuestaDTO registrarEmpleadoMensaje(EmpleadoRequestDTO empleadoRequestDTO);

    //modificar
    EmpleadoResponseDTO actualizarEmpleado(EmpleadoUpdateDTO empleadoUpdateDTO, Integer id);

    //modificar con mensaje
    RespuestaDTO actualizarEmpleadoMensaje(EmpleadoUpdateDTO empleadoUpdateDTO, Integer id);

    //eliminar
    void eliminarEmpleado(Integer id);

    //eliminar con mensaje
    RespuestaDTO eliminarEmpleadoMensaje(Integer id);
}
