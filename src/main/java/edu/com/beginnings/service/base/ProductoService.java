package edu.com.beginnings.service.base;

import edu.com.beginnings.dto.base.LibroResponseDTO;
import edu.com.beginnings.dto.base.ProductoDTO;
import edu.com.beginnings.dto.base.ProductoRequestDTO;
import edu.com.beginnings.dto.base.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {

    //listado total
    List<ProductoResponseDTO> listarLibros();

    //buscar producto
    ProductoResponseDTO buscarLibro(Integer id);

    //registrar producto
    ProductoResponseDTO registrar(ProductoRequestDTO productoRequestDTO);

    //modificar producto
    ProductoResponseDTO actualizar(ProductoDTO productoDTO ,Integer id);

    //Eliminar
    void borrarProducto(Integer id);
}
