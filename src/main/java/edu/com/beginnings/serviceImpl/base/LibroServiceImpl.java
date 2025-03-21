package edu.com.beginnings.serviceImpl.base;

import edu.com.beginnings.dto.base.LibroDTO;
import edu.com.beginnings.dto.base.LibroResponseDTO;
import edu.com.beginnings.excepcion.base.BadRequestException;
import edu.com.beginnings.excepcion.base.ResourceNotFoundException;
import edu.com.beginnings.map.base.LibroMapper;
import edu.com.beginnings.model.base.Libro;
import edu.com.beginnings.repo.base.LibroRepo;
import edu.com.beginnings.service.base.LibroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
@Slf4j
public class LibroServiceImpl implements LibroService {

    //ioc repo
    private final LibroRepo repo;

    //ioc mapper
    //@Qualifier("libroMapper")
    private final LibroMapper libroMapper;

    @Override
    public List<LibroResponseDTO> listarLibrosdto() {

        log.info("obteniendo lista de libros");
        List<Libro> libros = repo.findAll();

        //mapeo para salida dto
        return libros.stream()
                .map(libroMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LibroResponseDTO buscarDtoResponse(Integer id) {

        log.info("obteniendo libro con id : {}",id);
        Libro libro = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro", "id", id));

        return libroMapper.toResponseDTO(libro); //LibroResponseDTO
    }


    @Override
    public LibroResponseDTO guardarLibrodto(LibroDTO libroDTO) {

        //
        log.info("guardando nuevo libro : {}",libroDTO);

        //validaciones
        validarLibroDTO(libroDTO);

        Libro libro = libroMapper.toLibro(libroDTO);
        Libro save = repo.save(libro);
        return libroMapper.toResponseDTO(save);
    }

    @Override
    public LibroResponseDTO modificarLibrodto(LibroDTO libroDTO , Integer id) {

        log.info("Modificando libro con id: {}", id);
        //recuperar el id

        Libro libroExistente = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro", "id", id));


        //validaciones
        validarLibroDTO(libroDTO);


        libroExistente.setTitulo(libroDTO.getTitulo());
        libroExistente.setAutor(libroDTO.getAutor());
        libroExistente.setFechaPublicacion(libroDTO.getFechaPublicacion());

        //guardar
        Libro actualizado = repo.save(libroExistente);
        //guardar como dto
        return libroMapper.toResponseDTO(actualizado);
    }


    @Override
    public void borrarLibro(Integer id) {
        log.info("Eliminando libro con id: {}", id);

        // Verificar que el libro existe antes de eliminarlo
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Libro", "id", id);
        }

        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("No se puede eliminar el libro porque tiene referencias asociadas");
        }
    }

    @Override
    public LibroDTO buscarDto(Integer id) {
        log.info("Buscando libro completo con id: {}", id);

        Libro libro = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro", "id", id));

        return libroMapper.toDTO(libro);
    }

    /**
     * Método privado para validar los datos del LibroDTO
     */
    private void validarLibroDTO(LibroDTO libroDTO) {
        if (libroDTO.getAutor() == null || libroDTO.getAutor().trim().isEmpty()) {
            throw new BadRequestException("El autor del libro no puede estar vacío");
        }

        if (libroDTO.getFechaPublicacion() == null) {
            throw new BadRequestException("La fecha de publicación es obligatoria");
        }

        if (libroDTO.getFechaPublicacion().isAfter(LocalDate.now())) {
            throw new BadRequestException("La fecha de publicación no puede ser futura");
        }
    }



}
