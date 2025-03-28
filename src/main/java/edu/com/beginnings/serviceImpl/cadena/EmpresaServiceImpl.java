package edu.com.beginnings.serviceImpl.cadena;

import edu.com.beginnings.dto.record.cadena.EmpresaRequestDTO;
import edu.com.beginnings.dto.record.cadena.EmpresaResponseDTO;

import edu.com.beginnings.excepcion.errores.RecursoNoEncontradoException;
import edu.com.beginnings.map.cadena.EmpresaMapper;

import edu.com.beginnings.mensaje.MensajeRespuesta;
import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.model.cadena.Empresa;

import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import edu.com.beginnings.repo.cadena.EmpresaRepo;

import edu.com.beginnings.service.cadena.EmpresaService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {

    //ioc
    private final EmpresaRepo empresaRepo;

    private final EmpresaMapper empresaMapper;


    @Override
    public List<EmpresaResponseDTO> listarEmpresas() {
        List<Empresa> empresas = empresaRepo.findAll();

        return empresas.stream()
                .map(empresa -> empresaMapper.toEmpresaResponseDTO(empresa))
                .collect(Collectors.toList());

    }

    @Override
    public PaginaRespuestaDTO<EmpresaResponseDTO> listarEmpresasPaginados(Pageable pageable) {
        Page<EmpresaResponseDTO> paginar = empresaRepo.findAll(pageable)
                .map(empresa -> empresaMapper.toEmpresaResponseDTO(empresa));
        //
        return new PaginaRespuestaDTO<>(paginar);
    }

    @Override
    public EmpresaResponseDTO buscarEmpresa(Integer id) {
        Empresa empresa = empresaRepo.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("Empresa no encontrado : " + id));

        return empresaMapper.toEmpresaResponseDTO(empresa);
    }

    @Override
    public EmpresaResponseDTO crear(EmpresaRequestDTO empresaRequestDTO) {
        Empresa empresa = empresaMapper.toEmpresa(empresaRequestDTO);

        //logica

        empresa = empresaRepo.save(empresa);
        return empresaMapper.toEmpresaResponseDTO(empresa);
    }

    @Override
    public RespuestaDTO crearRespuesta(EmpresaRequestDTO empresaRequestDTO) {
        Empresa empresa = empresaMapper.toEmpresa(empresaRequestDTO);

        empresa = empresaRepo.save(empresa);

        EmpresaResponseDTO empresaResponseDTO = empresaMapper.toEmpresaResponseDTO(empresa);

        return new RespuestaDTO(MensajeRespuesta.AGREGADO_EXITOSO.getMensaje(),empresaResponseDTO);
    }

    @Override
    public EmpresaResponseDTO actualizar(EmpresaRequestDTO empresaRequestDTO, Integer id) {
        //
        Empresa empresa = empresaRepo.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("Empresa no encontrado : " + id));

        //valores de ingreso
        if(empresaRequestDTO.nombre() != null){
            empresa.setNombre(empresaRequestDTO.nombre());
        }


        empresa = empresaRepo.save(empresa);

        return empresaMapper.toEmpresaResponseDTO(empresa);
    }

    @Override
    public RespuestaDTO actualizarRespuesta(EmpresaRequestDTO empresaRequestDTO, Integer id) {

        Empresa empresa = empresaRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrado : " + id));

        //
        if(empresaRequestDTO.nombre() != null){
            empresa.setNombre(empresaRequestDTO.nombre());
        }


        empresa = empresaRepo.save(empresa);
        EmpresaResponseDTO empresaResponseDTO = empresaMapper.toEmpresaResponseDTO(empresa);

        return new RespuestaDTO(MensajeRespuesta.MODIFICACION_EXITOSA.getMensaje(),empresaResponseDTO);

    }

    @Override
    public void eliminar(Integer id) {

        Empresa empresa = empresaRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrado : " + id));

        empresaRepo.delete(empresa);
    }

    @Override
    public RespuestaDTO eliminarRespuesta(Integer id) {
        Empresa empresa = empresaRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrado : " + id));

        empresaRepo.delete(empresa);

        return new RespuestaDTO(MensajeRespuesta.ELIMINACION_EXITOSA.getMensaje(), "Id empresa eliminado : " +id);
    }


}
