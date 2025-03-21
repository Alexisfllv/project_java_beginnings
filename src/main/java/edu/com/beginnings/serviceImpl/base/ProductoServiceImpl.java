package edu.com.beginnings.serviceImpl.base;


import edu.com.beginnings.dto.base.LibroResponseDTO;
import edu.com.beginnings.dto.base.ProductoDTO;
import edu.com.beginnings.dto.base.ProductoRequestDTO;
import edu.com.beginnings.dto.base.ProductoResponseDTO;
import edu.com.beginnings.dto.record.base.ProductoRequestRecordDTO;
import edu.com.beginnings.map.base.ProductoMapper;
import edu.com.beginnings.model.base.Producto;
import edu.com.beginnings.repo.base.ProductoRepo;
import edu.com.beginnings.service.base.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepo productoRepo;

    private final ProductoMapper productoMapper;


    @Override
    public List<ProductoResponseDTO> listarLibros() {
        List<Producto> productos = productoRepo.findAll();
        return productos.stream()
                .map(productoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDTO buscarLibro(Integer id) {
        Producto  producto =  productoRepo.findById(id).get();
        return productoMapper.toResponseDTO(producto);
    }

    @Override
    public ProductoResponseDTO registrar(ProductoRequestRecordDTO productoRequestRecordDTO) {
        Producto producto =  productoMapper.toProducto(productoRequestRecordDTO);
        //registrar la fecha actual si no se envia ninguna fecha
        if (producto.getFechaInicio() != null) {
            producto.setFechaInicio(producto.getFechaInicio());
        }else {
            producto.setFechaInicio(LocalDateTime.now());
        }
        //registrar fecha fin si no se envia ninguna fecha por defecto +3 days
        if (producto.getFechaFin() != null) {
            producto.setFechaFin(producto.getFechaFin());
        }else {
            producto.setFechaFin(producto.getFechaInicio().plusDays(3));
        }
        Producto save = productoRepo.save(producto);
        //mostrar el responseDTO
        return productoMapper.toResponseDTO(save);
    }

    @Override
    public ProductoResponseDTO actualizar(ProductoDTO productoDTO, Integer id) {
        Producto productoExiste = productoRepo.findById(id).get();

        productoExiste.setNombre(productoDTO.getNombre());
        productoExiste.setCantidad(productoDTO.getCantidad());
        productoExiste.setPeso(productoDTO.getPeso());

        // se envia un fechaincio , la actualizamos a lo que se diga
        if (productoDTO.getFechaInicio() != null) {
            productoExiste.setFechaInicio(productoDTO.getFechaInicio());
        }

        // si envia una fechafin , la actualizamos ,si no la dejamos igual
        if (productoDTO.getFechaFin() != null) {
            productoExiste.setFechaFin(productoDTO.getFechaFin());
        }

        productoExiste = productoRepo.save(productoExiste);
        return productoMapper.toResponseDTO(productoExiste);
    }

    @Override
    public void borrarProducto(Integer id) {
        productoRepo.deleteById(id);
    }
}
