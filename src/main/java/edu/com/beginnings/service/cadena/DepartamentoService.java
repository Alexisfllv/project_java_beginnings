package edu.com.beginnings.service.cadena;

import edu.com.beginnings.dto.record.cadena.DepartamentoRequestDTO;
import edu.com.beginnings.dto.record.cadena.DepartamentoResponseDTO;
import edu.com.beginnings.dto.record.cadena.DepartamentoUpdateDTO;
import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartamentoService {

    //listar
    List<DepartamentoResponseDTO> listadoDepartamentos();

    //listado con paginador
    PaginaRespuestaDTO<DepartamentoResponseDTO> listadoDepartamentoPaginado(Pageable pageable);

    //buscar x id
    DepartamentoResponseDTO departamentoPorId(Integer id);

    //registrar
    DepartamentoResponseDTO registrarDepartamento(DepartamentoRequestDTO departamentoRequestDTO);

    //registro con mensaje
    RespuestaDTO registrarDepartamentoMensaje(DepartamentoRequestDTO departamentoRequestDTO);

    //modificar
    DepartamentoResponseDTO actualizarDepartamento(DepartamentoUpdateDTO departamentoUpdateDTO, Integer id);

    //modificar con mensaje
    RespuestaDTO actualizarDepartamentoMensaje(DepartamentoUpdateDTO departamentoUpdateDTO, Integer id);

    //eliminar
    void eliminarDepartamento(Integer id);

    //eliminar con mensaje
    RespuestaDTO eliminarDepartamentoMensaje(Integer id);
}
