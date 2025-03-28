package edu.com.beginnings.service.cadena;

import edu.com.beginnings.dto.record.cadena.EmpresaRequestDTO;
import edu.com.beginnings.dto.record.cadena.EmpresaResponseDTO;

import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmpresaService {
    //metodos
    //listar
    List<EmpresaResponseDTO> listarEmpresas();

    //listarpaginador
    PaginaRespuestaDTO<EmpresaResponseDTO> listarEmpresasPaginados(Pageable pageable);

    //buscarxid
    EmpresaResponseDTO buscarEmpresa(Integer id);

    //registrar
    EmpresaResponseDTO crear(EmpresaRequestDTO clienteRequestDTO);

    //registrar con respuesta
    RespuestaDTO crearRespuesta(EmpresaRequestDTO clienteRequestDTO);

    //modificar
    EmpresaResponseDTO actualizar(EmpresaRequestDTO clienteRequestDTO, Integer id);

    //modificar con respuesta
    RespuestaDTO actualizarRespuesta(EmpresaRequestDTO clienteRequestDTO, Integer id);

    //eliminar
    void eliminar(Integer id);

    //eliminar respuesta
    RespuestaDTO eliminarRespuesta(Integer id);
}
