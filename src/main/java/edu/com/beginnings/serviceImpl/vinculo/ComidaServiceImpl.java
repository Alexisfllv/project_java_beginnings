package edu.com.beginnings.serviceImpl.vinculo;


import edu.com.beginnings.dto.record.vinculo.ComidaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaResponseDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaUpdateDTO;
import edu.com.beginnings.map.vinculo.ComidaMapper;
import edu.com.beginnings.model.vinculo.Categoria;
import edu.com.beginnings.model.vinculo.Comida;
import edu.com.beginnings.repo.vinculo.CategoriaRepo;
import edu.com.beginnings.repo.vinculo.ComidaRepo;
import edu.com.beginnings.service.vinculo.ComidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComidaServiceImpl implements ComidaService {

    // repo
    private final ComidaRepo comidaRepo;
    private final CategoriaRepo categoriaRepo;

    //mapper
    private final ComidaMapper comidaMapper;


    //listar comidas con detalles
    @Override
    public List<ComidaResponseDTO> listarComidas() {
        List<Comida> comidas = comidaRepo.findAll();
        return comidas.stream()
                .map(comida -> comidaMapper.comidaResponseDTO(comida))
                .collect(Collectors.toList());

    }

    //listar page / poli
    @Override
    public Page<ComidaResponseDTO> listarComidas(Pageable pageable) {
        return comidaRepo.findAll(pageable).map(comidaMapper::comidaResponseDTO);
    }

    //buscar por id
    @Override
    public ComidaResponseDTO buscarComida(Integer id) {
        Comida comida = comidaRepo.findById(id).get();

        return comidaMapper.comidaResponseDTO(comida);
    }


    //registrar
    @Override
    public ComidaResponseDTO registrarComida(ComidaRequestDTO comidaRequestDTO) {

        //bucar la id para ingresar
        Categoria categoria = categoriaRepo.findById(comidaRequestDTO.categoriaId())
                .orElseThrow(() -> new RuntimeException("Id Categoria no encontrado"));

        Comida comida = comidaMapper.comidaReq(comidaRequestDTO);
        //enviar la categoria registrada
        comida.setCategoria(categoria);

        //gestion de fechas
        if (comida.getFechaInicio() != null) {
            comida.setFechaInicio(comida.getFechaInicio());
        } else {
            comida.setFechaInicio(LocalDateTime.now());
        }

        if (comida.getFechaFin() != null) {
            comida.setFechaFin(comida.getFechaFin());
        } else {
            comida.setFechaFin(LocalDateTime.now().plusDays(3));
        }

        //guardar al modelo comida
        comida = comidaRepo.save(comida);
        return comidaMapper.comidaResponseDTO(comida);
    }

    @Override
    public Map<String,Object> modificarComida(ComidaUpdateDTO comidaUpdateDTO, Integer id) {

        // Buscar la comida en la BD
        Comida comida = comidaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Comida no encontrada"));

        //Actualizar Valores
        if (comidaUpdateDTO.nombre() != null) {
            comida.setNombre(comidaUpdateDTO.nombre());
        }
        if (comidaUpdateDTO.cantidad() != null) {
            comida.setCantidad(comidaUpdateDTO.cantidad());
        }
        if (comidaUpdateDTO.peso() != null) {
            comida.setPeso(comidaUpdateDTO.peso());
        }
        if (comidaUpdateDTO.fechaInicio() != null) {
            comida.setFechaInicio(comidaUpdateDTO.fechaInicio());
        }
        if (comidaUpdateDTO.fechaFin() != null) {
            comida.setFechaFin(comidaUpdateDTO.fechaFin());
        }

        //modificacion de la categoria
        if (comidaUpdateDTO.categoriaId() != null) {
            Categoria categoria = categoriaRepo.findById(comidaUpdateDTO.categoriaId())
                    .orElseThrow(() -> new RuntimeException("Id Categoria no encontrado"));

            comida.setCategoria(categoria);
        }

        //guardar los cambios
        comida = comidaRepo.save(comida);

        //Construir respuesta con mensaje
        Map<String,Object> response = new LinkedHashMap<>();
        response.put("mensaje","Modificacion realizada con exito");
        response.put("data",comidaMapper.comidaResponseDTO(comida));



        return response;
    }

    @Override
    public void eliminarComida(Integer id) {
        comidaRepo.deleteById(id);
    }

    //
}
