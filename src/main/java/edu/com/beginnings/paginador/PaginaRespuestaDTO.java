package edu.com.beginnings.paginador;

import org.springframework.data.domain.Page;

import java.util.List;

public record PaginaRespuestaDTO<T>(
        List<T> contenido,
        int pagina,
        int tamano,
        long totalElementos,
        int totalPaginas,
        boolean ultima,
        boolean primera,
        int numeroElementos
) {
    public PaginaRespuestaDTO(Page<T> page) {
        this(page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.isFirst(),
                page.getNumberOfElements());
    }
}
