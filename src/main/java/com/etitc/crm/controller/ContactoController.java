package com.etitc.crm.controller;

import com.etitc.crm.entity.Contacto;
import com.etitc.crm.service.ContactoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // GET - Mostrar formulario para nuevo contacto
    @GetMapping("/contactos/nuevo")
    public String mostrarFormularioNuevo(Model model) {

        model.addAttribute("contacto", new Contacto());

        return "contacto-form";
    }

    // POST - Guardar contacto nuevo
    @PostMapping("/contactos/guardar")
    public String guardarContacto(
            @Valid Contacto contacto,
            BindingResult result) {

        if (result.hasErrors()) {
            return "contacto-form";
        }

        contactoService.guardar(contacto);

        return "redirect:/contactos";
    }

    // GET - Mostrar formulario de edición
    @GetMapping("/contactos/editar/{id}")
    public String mostrarFormularioEditar(
            @PathVariable Long id,
            Model model) {

        Contacto contacto = contactoService.buscarPorId(id)
                .orElse(null);

        if (contacto == null) {
            return "redirect:/contactos";
        }

        model.addAttribute("contacto", contacto);

        return "contacto-form";
    }

    // POST - Actualizar contacto
    @PostMapping("/contactos/actualizar")
    public String actualizarContacto(
            @Valid Contacto contacto,
            BindingResult result) {

        if (result.hasErrors()) {
            return "contacto-form";
        }

        contactoService.guardar(contacto);

        return "redirect:/contactos";
    }

    // GET - Eliminar contacto
    @GetMapping("/contactos/eliminar/{id}")
    public String eliminarContacto(@PathVariable Long id) {

        contactoService.eliminar(id);

        return "redirect:/contactos";
    }
}