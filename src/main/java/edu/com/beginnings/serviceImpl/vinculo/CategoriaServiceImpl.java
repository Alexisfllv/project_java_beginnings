package edu.com.beginnings.serviceImpl.vinculo;

import edu.com.beginnings.dto.record.vinculo.CategoriaDTO;
import edu.com.beginnings.dto.record.vinculo.CategoriaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.CategoriaResponseDTO;
import edu.com.beginnings.map.vinculo.CategoriaMapper;
import edu.com.beginnings.model.vinculo.Categoria;
import edu.com.beginnings.repo.vinculo.CategoriaRepo;
import edu.com.beginnings.service.vinculo.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    //ioc
    private final CategoriaRepo categoriaRepo;

    private final CategoriaMapper categoriaMapper;


    @Override
    public List<CategoriaResponseDTO> listados() {
        List<Categoria> categorias = categoriaRepo.findAll();
        return categorias.stream()
                .map(categoria -> categoriaMapper.categoriaResponseDTO(categoria))
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaResponseDTO buscar(Integer id) {
        Categoria categoria = categoriaRepo.findById(id).get();
        return categoriaMapper.categoriaResponseDTO(categoria);

    }

    @Override
    public CategoriaResponseDTO registrar(CategoriaRequestDTO categoriaRequestDTO) {
        Categoria categoria = categoriaMapper.categoriaReq(categoriaRequestDTO);


        categoria = categoriaRepo.save(categoria);

        return categoriaMapper.categoriaResponseDTO(categoria);
    }

    @Override
    public CategoriaResponseDTO modificar(CategoriaDTO categoriaDTO,Integer id) {
        Categoria categiriaexiste = categoriaRepo.findById(id).get();

        //atributos
        if (categoriaDTO.nombre() != null ) {
            categiriaexiste.setNombre(categoriaDTO.nombre());
        }



        categiriaexiste = categoriaRepo.save(categiriaexiste);
        return categoriaMapper.categoriaResponseDTO(categiriaexiste);
    }

    @Override
    public void eliminar(Integer id) {
        categoriaRepo.deleteById(id);
    }
}
