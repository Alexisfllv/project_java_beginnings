package edu.com.beginnings.serviceImpl.vinculo;


import edu.com.beginnings.dto.record.vinculo.ComidaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaResponseDTO;
import edu.com.beginnings.map.vinculo.ComidaMapper;
import edu.com.beginnings.model.vinculo.Categoria;
import edu.com.beginnings.model.vinculo.Comida;
import edu.com.beginnings.repo.vinculo.CategoriaRepo;
import edu.com.beginnings.repo.vinculo.ComidaRepo;
import edu.com.beginnings.service.vinculo.ComidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ComidaServiceImpl implements ComidaService {

    // repo
    private final ComidaRepo comidaRepo;
    private final CategoriaRepo categoriaRepo;

    //mapper
    private final ComidaMapper comidaMapper;

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

    //
}
