package edu.com.beginnings.serviceImpl.cadena;

import edu.com.beginnings.dto.record.cadena.*;
import edu.com.beginnings.excepcion.errores.RecursoNoEncontradoException;
import edu.com.beginnings.map.cadena.DepartamentoMapper;
import edu.com.beginnings.map.cadena.EmpleadoMapper;
import edu.com.beginnings.mensaje.MensajeRespuesta;
import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.model.cadena.Departamento;
import edu.com.beginnings.model.cadena.Empleado;
import edu.com.beginnings.model.cadena.Empresa;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import edu.com.beginnings.repo.cadena.DepartamentoRepo;
import edu.com.beginnings.repo.cadena.EmpleadoRepo;
import edu.com.beginnings.repo.cadena.EmpresaRepo;
import edu.com.beginnings.service.cadena.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    //ioc
    private final EmpleadoRepo empleadoRepo;

    private final EmpleadoMapper empleadoMapper;

    //fks
    private final DepartamentoRepo departamentoRepo;


    @Override
    public List<EmpleadoResponseDTO> listadoEmpleados() {

        List<Empleado> empleados = empleadoRepo.findAll();

        return empleados.stream()
                .map(empleado -> empleadoMapper.toEmpleadoResponseDTO(empleado))
                .collect(Collectors.toList());

    }


    @Override
    public PaginaRespuestaDTO<EmpleadoResponseDTO> listadoEmpleadoPaginado(Pageable pageable) {

        Page<EmpleadoResponseDTO> paginar = empleadoRepo.findAll(pageable)
                .map(empleado -> empleadoMapper.toEmpleadoResponseDTO(empleado));

        //
        return new PaginaRespuestaDTO<EmpleadoResponseDTO>(paginar);

    }

    @Override
    public EmpleadoResponseDTO empleadoPorId(Integer id) {
        Empleado empleado =  empleadoRepo.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("Empleado no encontrado : " + id));

        return empleadoMapper.toEmpleadoResponseDTO(empleado);

    }

    @Override
    public EmpleadoResponseDTO registrarEmpleado(EmpleadoRequestDTO empleadoRequestDTO) {
        // confirmar fks
        Departamento departamento = departamentoRepo.findById(empleadoRequestDTO.departamentoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("id departamento no encontrado : " + empleadoRequestDTO.departamentoId()));
        //
        Empleado empleado = empleadoMapper.toEmpleado(empleadoRequestDTO);

        //enviar los registros
        empleado.setDepartamento(departamento);

        empleado = empleadoRepo.save(empleado);

        return empleadoMapper.toEmpleadoResponseDTO(empleado);

    }

    @Override
    public RespuestaDTO registrarEmpleadoMensaje(EmpleadoRequestDTO empleadoRequestDTO) {
        //verificar fks
        Departamento departamento = departamentoRepo.findById(empleadoRequestDTO.departamentoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("id departamento no encontrado : " + empleadoRequestDTO.departamentoId()));

        //Convertir al modelo
        Empleado empleado = empleadoMapper.toEmpleado(empleadoRequestDTO);

        //registrar las fk
        empleado.setDepartamento(departamento);


        //guardar con JPA
        empleado = empleadoRepo.save(empleado);

        //regresar al responseDTO
        EmpleadoResponseDTO registro = empleadoMapper.toEmpleadoResponseDTO(empleado);

        //
        return new RespuestaDTO(MensajeRespuesta.AGREGADO_EXITOSO.getMensaje(), registro);
    }

    @Override
    public EmpleadoResponseDTO actualizarEmpleado(EmpleadoUpdateDTO empleadoUpdateDTO, Integer id) {

        //verificar fks
        Empleado empleado = empleadoRepo.findById(empleadoUpdateDTO.departamentoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("id departamento no encontrado : " + empleadoUpdateDTO.departamentoId()));


        //actualizar datos o dejarlos como eran anteriormente
        if (empleadoUpdateDTO.nombre() != null) {
            empleado.setNombre(empleadoUpdateDTO.nombre());
        }


        // ingreso de la fks
        //cliente // suministro
        if (empleadoUpdateDTO.departamentoId() != null) {
            Departamento departamento = departamentoRepo.findById(empleadoUpdateDTO.departamentoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("id Departamento no encontrado : " + id));
            empleado.setDepartamento(departamento);
        }


        //guardar cambios
        empleado = empleadoRepo.save(empleado);

        return empleadoMapper.toEmpleadoResponseDTO(empleado);

    }

    @Override
    public RespuestaDTO actualizarEmpleadoMensaje(EmpleadoUpdateDTO empleadoUpdateDTO, Integer id) {

        //verificar fks
        Empleado empleado = empleadoRepo.findById(empleadoUpdateDTO.departamentoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("id departamento no encontrado : " + empleadoUpdateDTO.departamentoId()));


        //actualizar datos o dejarlos como eran anteriormente
        if (empleadoUpdateDTO.nombre() != null) {
            empleado.setNombre(empleadoUpdateDTO.nombre());
        }


        // ingreso de la fks
        //cliente // suministro
        if (empleadoUpdateDTO.departamentoId() != null) {
            Departamento departamento = departamentoRepo.findById(empleadoUpdateDTO.departamentoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("id Departamento no encontrado : " + id));
            empleado.setDepartamento(departamento);
        }

        //guardar cambios
        empleado = empleadoRepo.save(empleado);

        //tranformar a DepartamentoResponseDTO
        EmpleadoResponseDTO empleadoResponseDTO = empleadoMapper.toEmpleadoResponseDTO(empleado);

        return new RespuestaDTO(MensajeRespuesta.MODIFICACION_EXITOSA.getMensaje(), empleadoResponseDTO);
    }

    @Override
    public void eliminarEmpleado(Integer id) {

        Empleado empleado = empleadoRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empleado no encontrado : " + id) );

        empleadoRepo.delete(empleado);
    }

    @Override
    public RespuestaDTO eliminarEmpleadoMensaje(Integer id) {

        Empleado empleado = empleadoRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empleado no encontrado : " + id) );

        empleadoRepo.delete(empleado);

        //mensaje
        return new RespuestaDTO(MensajeRespuesta.ELIMINACION_EXITOSA.getMensaje(), "id Empelado eliminado : " + id);
    }
}
