package com.etitc.crm.controller;

import com.etitc.crm.entity.Contacto;
import com.etitc.crm.service.ContactoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ContactoController {

    private final ContactoService contactoService;

    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    // GET - Mostrar listado de contactos
    @GetMapping("/contactos")
    public String listarContactos(Model model) {

        List<Contacto> contactos = contactoService.listarTodos();

        model.addAttribute("contactos", contactos);

        return "contactos";
    }

    // GET - Mostrar formulario
    @GetMapping("/contactos/nuevo")
    public String mostrarFormulario(Model model) {

        model.addAttribute("contacto", new Contacto());

        return "contacto-form";
    }

    // POST - Recibir y procesar formulario
    @PostMapping("/contactos/guardar")
    public String guardarContacto(
            @Valid Contacto contacto,
            BindingResult result,
            Model model) {

        // Si existen errores de validación
        if (result.hasErrors()) {
            return "contacto-form";
        }

        // Si los datos son correctos
        contactoService.guardar(contacto);

        // Redirigir al listado
        return "redirect:/contactos";
    }
}