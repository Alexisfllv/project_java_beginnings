package edu.com.beginnings.serviceImpl.base;

import edu.com.beginnings.excepcion.base.LibroRecursoNoEncontradoException;
import edu.com.beginnings.model.base.Libro;
import edu.com.beginnings.repo.base.LibroRepo;
import edu.com.beginnings.service.base.LibroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class LibroServiceImpl implements LibroService {

    //ioc repo
    private final LibroRepo repo;


    //metodos heredados por implementes

    @Override
    public List<Libro> listarLibros() {
        return repo.findAll();
    }

    @Override
    public Libro buscarLibro(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new LibroRecursoNoEncontradoException("Libro no encontrado : "+id));
    }

    @Override
    public Libro guardarLibro(Libro libro) {
        return repo.save(libro);
    }

    @Override
    public void borrarLibro(Integer id) {
        if (!repo.existsById(id)) {
            throw  new LibroRecursoNoEncontradoException("Libro no encontrado : "+id);
        }
        repo.deleteById(id);
    }
}
