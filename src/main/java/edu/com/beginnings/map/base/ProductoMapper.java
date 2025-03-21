package edu.com.beginnings.map.base;


import edu.com.beginnings.dto.base.ProductoDTO;
import edu.com.beginnings.dto.base.ProductoRequestDTO;
import edu.com.beginnings.dto.base.ProductoResponseDTO;
import edu.com.beginnings.dto.record.base.ProductoRequestRecordDTO;
import edu.com.beginnings.model.base.Producto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    //Entidad > DTO
    ProductoDTO toDTO(Producto producto);

    // DTO > Entidad
    Producto toProducto(ProductoDTO productoDTO);

    //Entidad >DTOResponse
    ProductoResponseDTO toResponseDTO(Producto producto);

    // DTOResponse > Entidad
    Producto toProducto(ProductoResponseDTO productoResponseDTO);

    //Entidad > DTO
    ProductoRequestDTO toRequestDTO(Producto producto);

    Producto toProducto(ProductoRequestDTO productoRequestDTO);

    // record
    ProductoRequestRecordDTO toRequestRecordDTO(Producto producto);
    Producto toProducto(ProductoRequestRecordDTO productoRequestRecordDTO);
}
