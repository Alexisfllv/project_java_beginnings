package edu.com.beginnings.serviceImpl.base;

import edu.com.beginnings.dto.base.LibroDTO;
import edu.com.beginnings.dto.base.LibroResponseDTO;
import edu.com.beginnings.excepcion.base.LibroRecursoNoEncontradoException;
import edu.com.beginnings.map.LibroMapper;
import edu.com.beginnings.model.base.Libro;
import edu.com.beginnings.repo.base.LibroRepo;
import edu.com.beginnings.service.base.LibroService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService {

    //ioc repo
    private final LibroRepo repo;

    //ioc mapper
    @Qualifier("libroMapper")
    private final LibroMapper libroMapper;

    @Override
    public List<LibroResponseDTO> listarLibrosdto() {
        List<Libro> libros = repo.findAll();

        //mapeo para salida dto
        return libros.stream()
                .map(libroMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LibroResponseDTO buscarDtoResponse(Integer id) {
        Libro libro = repo.findById(id).get();
        return libroMapper.toResponseDTO(libro); //LibroResponseDTO
    }


    @Override
    public LibroResponseDTO guardarLibrodto(LibroDTO libroDTO) {
        Libro libro = libroMapper.toLibro(libroDTO);
        Libro save = repo.save(libro);
        return libroMapper.toResponseDTO(save);
    }

    @Override
    public LibroResponseDTO modificarunlibrodto(LibroDTO libroDTO , Integer id) {
        //recuperar el id

        Libro libroExistente = repo.findById(id).get();
        libroExistente.setTitulo(libroDTO.getTitulo());
        libroExistente.setAutor(libroDTO.getAutor());
        libroExistente.setFechaPublicacion(libroDTO.getFechaPublicacion());

        //guardar
        libroExistente = repo.save(libroExistente);
        //guardar como dto
        return libroMapper.toResponseDTO(libroExistente);
    }


    @Override
    public void borrarLibro(Integer id) {
        repo.deleteById(id);
    }

    //metodos heredados por implementes


}
