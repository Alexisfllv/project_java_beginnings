package edu.com.beginnings.serviceImpl.cadena;

import edu.com.beginnings.dto.record.cadena.DepartamentoRequestDTO;
import edu.com.beginnings.dto.record.cadena.DepartamentoResponseDTO;
import edu.com.beginnings.dto.record.cadena.DepartamentoUpdateDTO;

import edu.com.beginnings.excepcion.errores.RecursoNoEncontradoException;
import edu.com.beginnings.map.cadena.DepartamentoMapper;

import edu.com.beginnings.mensaje.MensajeRespuesta;
import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.model.cadena.Departamento;
import edu.com.beginnings.model.cadena.Empresa;

import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import edu.com.beginnings.repo.cadena.DepartamentoRepo;
import edu.com.beginnings.repo.cadena.EmpresaRepo;

import edu.com.beginnings.service.cadena.DepartamentoService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartamentoServiceImpl implements DepartamentoService {

    //ioc
    private final DepartamentoRepo departamentoRepo;

    //fks
    private final EmpresaRepo empresaRepo;


    private final DepartamentoMapper departamentoMapper;


    @Override
    public List<DepartamentoResponseDTO> listadoDepartamentos() {

        List<Departamento> departamentos = departamentoRepo.findAll();

        return departamentos.stream()
                .map(departamento -> departamentoMapper.toDepartamentoResponseDTO(departamento))
                .collect(Collectors.toList());

    }


    @Override
    public PaginaRespuestaDTO<DepartamentoResponseDTO> listadoDepartamentoPaginado(Pageable pageable) {

        Page<DepartamentoResponseDTO> paginar = departamentoRepo.findAll(pageable)
                .map(departamento -> departamentoMapper.toDepartamentoResponseDTO(departamento));

        //
        return new PaginaRespuestaDTO<DepartamentoResponseDTO>(paginar);

    }

    @Override
    public DepartamentoResponseDTO departamentoPorId(Integer id) {
        Departamento departamento =  departamentoRepo.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("Departamento no encontrado : " + id));

        return departamentoMapper.toDepartamentoResponseDTO(departamento);

    }

    @Override
    public DepartamentoResponseDTO registrarDepartamento(DepartamentoRequestDTO departamentoRequestDTO) {
        // confirmar fks
        Empresa empresa = empresaRepo.findById(departamentoRequestDTO.empresaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("id empresa no encontrado : " + departamentoRequestDTO.empresaId()));



        //
        Departamento departamento = departamentoMapper.toDepartamento(departamentoRequestDTO);

        //enviar los registros
        departamento.setEmpresa(empresa);



        departamento = departamentoRepo.save(departamento);

        return departamentoMapper.toDepartamentoResponseDTO(departamento);

    }

    @Override
    public RespuestaDTO registrarDepartamentoMensaje(DepartamentoRequestDTO departamentoRequestDTO) {


        //verificar fks
        Empresa empresa = empresaRepo.findById(departamentoRequestDTO.empresaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("id empresa no encontrado : " + departamentoRequestDTO.empresaId()));


        //Convertir al modelo
        Departamento departamento = departamentoMapper.toDepartamento(departamentoRequestDTO);

        //registrar las fk
        departamento.setEmpresa(empresa);


        //guardar con JPA
        departamento = departamentoRepo.save(departamento);

        //regresar al responseDTO
        DepartamentoResponseDTO registro = departamentoMapper.toDepartamentoResponseDTO(departamento);

        //
        return new RespuestaDTO(MensajeRespuesta.AGREGADO_EXITOSO.getMensaje(), registro);
    }

    @Override
    public DepartamentoResponseDTO actualizarDepartamento(DepartamentoUpdateDTO departamentoUpdateDTO, Integer id) {

        //Buscar duplicado de id departamento
        Departamento departamento =  departamentoRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Departamento no encontrado : " + id));

        //actualizar datos o dejarlos como eran anteriormente
        if (departamentoUpdateDTO.nombre() != null) {
            departamento.setNombre(departamentoUpdateDTO.nombre());
        }


        // ingreso de la fks
        //cliente // suministro
        if (departamentoUpdateDTO.empresaId() != null) {
            Empresa empresa = empresaRepo.findById(departamentoUpdateDTO.empresaId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("id Categoria no encontrado : " + id));
            departamento.setEmpresa(empresa);
        }


        //guardar cambios
        departamento = departamentoRepo.save(departamento);

        return departamentoMapper.toDepartamentoResponseDTO(departamento);

    }

    @Override
    public RespuestaDTO actualizarDepartamentoMensaje(DepartamentoUpdateDTO departamentoUpdateDTO, Integer id) {

        //Buscar duplicado de id departamento
        Departamento departamento =  departamentoRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Departamento no encontrado : " + id));

        //actualizar datos o dejarlos como eran anteriormente
        if (departamentoUpdateDTO.nombre() != null) {
            departamento.setNombre(departamentoUpdateDTO.nombre());
        }


        // ingreso de la fks
        //cliente // suministro
        if (departamentoUpdateDTO.empresaId() != null) {
            Empresa empresa = empresaRepo.findById(departamentoUpdateDTO.empresaId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("id Categoria no encontrado : " + id));
            departamento.setEmpresa(empresa);
        }



        //guardar cambios
        departamento = departamentoRepo.save(departamento);

        //tranformar a DepartamentoResponseDTO
        DepartamentoResponseDTO departamentoResponseDTO = departamentoMapper.toDepartamentoResponseDTO(departamento);

        return new RespuestaDTO(MensajeRespuesta.MODIFICACION_EXITOSA.getMensaje(), departamentoResponseDTO);
    }

    @Override
    public void eliminarDepartamento(Integer id) {

        Departamento departamento = departamentoRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Departamento no encontrado : " + id) );

        departamentoRepo.delete(departamento);
    }

    @Override
    public RespuestaDTO eliminarDepartamentoMensaje(Integer id) {

        Departamento departamento = departamentoRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Departamento no encontrado : " + id) );

        departamentoRepo.delete(departamento);

        //mensaje
        return new RespuestaDTO(MensajeRespuesta.ELIMINACION_EXITOSA.getMensaje(), "id departamento eliminado : " + id);
    }
}
