package edu.com.beginnings.serviceImpl.vinculo;

import edu.com.beginnings.dto.record.vinculo.pedidos.SuministroRequestDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.SuministroResponseDTO;
import edu.com.beginnings.excepcion.errores.RecursoNoEncontradoException;
import edu.com.beginnings.map.vinculo.SuministroMapper;
import edu.com.beginnings.mensaje.MensajeRespuesta;
import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.model.vinculo.Suministro;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import edu.com.beginnings.repo.vinculo.SuministroRepo;
import edu.com.beginnings.service.vinculo.SuministroService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuministroServiceImpl implements SuministroService {

    //ioc
    private final SuministroRepo suministroRepo;

    private final SuministroMapper suministroMapper;


    @Override
    public List<SuministroResponseDTO> listarSuministros() {
        List<Suministro> suministros = suministroRepo.findAll();

        return suministros.stream()
                .map(suministro -> suministroMapper.toSuministroResponseDTO(suministro))
                .collect(Collectors.toList());

    }

    @Override
    public PaginaRespuestaDTO<SuministroResponseDTO> listarSuministrosPaginados(Pageable pageable) {
        Page<SuministroResponseDTO> paginar = suministroRepo.findAll(pageable)
                .map(suministro -> suministroMapper.toSuministroResponseDTO(suministro));
        //
        return new PaginaRespuestaDTO<>(paginar);
    }

    @Override
    public SuministroResponseDTO buscarSuministro(Integer id) {
        Suministro suministro = suministroRepo.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("Suministro no encontrado : " + id));

        return suministroMapper.toSuministroResponseDTO(suministro);
    }

    @Override
    public SuministroResponseDTO crear(SuministroRequestDTO suministroRequestDTO) {
        Suministro suministro = suministroMapper.toSuministro(suministroRequestDTO);

        //logica

        suministro = suministroRepo.save(suministro);
        return suministroMapper.toSuministroResponseDTO(suministro);
    }

    @Override
    public RespuestaDTO crearRespuesta(SuministroRequestDTO suministroRequestDTO) {
        Suministro suministro = suministroMapper.toSuministro(suministroRequestDTO);

        suministro = suministroRepo.save(suministro);

        SuministroResponseDTO suministroResponseDTO = suministroMapper.toSuministroResponseDTO(suministro);

        return new RespuestaDTO(MensajeRespuesta.AGREGADO_EXITOSO.getMensaje(),suministroResponseDTO);
    }

    @Override
    public SuministroResponseDTO actualizar(SuministroRequestDTO suministroRequestDTO, Integer id) {
        //
        Suministro suministro = suministroRepo.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("Suministro no encontrado : " + id));

        //valores de ingreso

        if (suministroRequestDTO.nombre() != null){
            suministro.setNombre(suministroRequestDTO.nombre());
        }

        if (suministroRequestDTO.ubicacion() != null){
            suministro.setUbicacion(suministroRequestDTO.ubicacion());
        }

        suministro = suministroRepo.save(suministro);

        return suministroMapper.toSuministroResponseDTO(suministro);
    }

    @Override
    public RespuestaDTO actualizarRespuesta(SuministroRequestDTO suministroRequestDTO, Integer id) {

        Suministro suministro = suministroRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Suministro no encontrado : " + id));

        //
        if(suministroRequestDTO.nombre() != null){
            suministro.setNombre(suministroRequestDTO.nombre());
        }
        if (suministroRequestDTO.ubicacion() != null){
            suministro.setUbicacion(suministroRequestDTO.ubicacion());
        }

        suministro = suministroRepo.save(suministro);
        SuministroResponseDTO suministroResponseDTO = suministroMapper.toSuministroResponseDTO(suministro);

        return new RespuestaDTO(MensajeRespuesta.MODIFICACION_EXITOSA.getMensaje(),suministroResponseDTO);

    }

    @Override
    public void eliminar(Integer id) {

        Suministro suministro = suministroRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Suministro no encontrado : " + id));

        suministroRepo.delete(suministro);
    }

    @Override
    public RespuestaDTO eliminarRespuesta(Integer id) {
        Suministro suministro = suministroRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Suministro no encontrado : " + id));

        suministroRepo.delete(suministro);

        return new RespuestaDTO(MensajeRespuesta.ELIMINACION_EXITOSA.getMensaje(), "Id suministro eliminado : " +id);
    }


}
