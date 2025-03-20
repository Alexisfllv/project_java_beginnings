package edu.com.beginnings.controllermvc.base;


import edu.com.beginnings.dto.base.LibroDTO;
import edu.com.beginnings.dto.base.LibroResponseDTO;
import edu.com.beginnings.service.base.LibroService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/libros")
public class LibroControllerMVC {

    private final LibroService libroService;

    public LibroControllerMVC(LibroService libroService) {
        this.libroService = libroService;
    }

    // Listar libros
//    @GetMapping("/lista")
//    public String listarLibros(Model model) {
//        model.addAttribute("libros", libroService.listarLibrosdto());
//        return "libros/lista";
//    }

    // Controlador - Mostrar lista sin fecha
    @GetMapping("/lista")
    public String listarLibros(Model model) {
        List<LibroResponseDTO> libros = libroService.listarLibrosdto(); // Este DTO no tiene fecha
        model.addAttribute("libros", libros);
        return "libros/lista";
    }


    // Mostrar formulario (agregar o modificar)
    @GetMapping("/formulario")
    public String mostrarFormulario(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            model.addAttribute("libro", libroService.buscarDtoResponse(id));
        } else {
            model.addAttribute("libro", new LibroDTO());
        }
        return "libros/formulario";
    }

    // Guardar libro (nuevo o modificado)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute LibroDTO libroDTO) {
        libroService.guardarLibrodto(libroDTO);
        return "redirect:/libros/lista";
    }

    //
    // Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Integer id, Model model) {
        LibroDTO libro = libroService.buscarDto(id); // Usamos LibroDTO con fecha
        model.addAttribute("libro", libro);
        return "libros/editar";
    }

    // Procesar edición
    @PostMapping("/editar/{id}")
    public String editarLibro(@ModelAttribute LibroDTO libroDTO, @PathVariable Integer id) {
        libroService.modificarunlibrodto(libroDTO, id);
        return "redirect:/libros/lista";
    }

    // Eliminar libro
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        libroService.borrarLibro(id);
        return "redirect:/libros/lista";
    }
}
