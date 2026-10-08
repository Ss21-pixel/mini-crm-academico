package com.etitc.crm.controller;

import com.etitc.crm.modelo.Contacto;
import com.etitc.crm.service.ContactoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/contactos")
public class ContactoController {

    private final ContactoService contactoService;

    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    // ==============================
    // LISTAR + BUSCAR + PAGINAR
    // ==============================

    @GetMapping
    public String listarContactos(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Page<Contacto> paginaContactos =
                contactoService.buscar(q, page, size);

        model.addAttribute("contactos", paginaContactos.getContent());
        model.addAttribute("paginaActual", page);
        model.addAttribute("totalPaginas", paginaContactos.getTotalPages());
        model.addAttribute("totalElementos", paginaContactos.getTotalElements());
        model.addAttribute("q", q);
        model.addAttribute("size", size);

        return "contactos";
    }

    // ==============================
    // NUEVO CONTACTO
    // ==============================

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {

        model.addAttribute("contacto", new Contacto());

        return "contacto-form";
    }

    // ==============================
    // EDITAR CONTACTO
    // ==============================

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes) {

        Contacto contacto = contactoService.buscarPorId(id)
                .orElse(null);

        if (contacto == null) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "El contacto no fue encontrado."
            );

            return "redirect:/contactos";
        }

        model.addAttribute("contacto", contacto);

        return "contacto-form";
    }

    // ==============================
    // GUARDAR / ACTUALIZAR
    // ==============================

    @PostMapping("/guardar")
    public String guardarContacto(
            @Valid Contacto contacto,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "contacto-form";
        }

        if (contactoService.emailDuplicado(contacto)) {

            result.rejectValue(
                    "email",
                    "error.contacto",
                    "Ya existe un contacto registrado con este correo."
            );

            return "contacto-form";
        }

        boolean nuevo = contacto.getId() == null;

        contactoService.guardar(contacto);

        if (nuevo) {
            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    "Contacto creado correctamente."
            );
        } else {
            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    "Contacto actualizado correctamente."
            );
        }

        return "redirect:/contactos";
    }

    // ==============================
    // ELIMINAR
    // ==============================

    @PostMapping("/eliminar/{id}")
    public String eliminarContacto(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {

            contactoService.eliminar(id);

            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    "Contacto eliminado correctamente."
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "No se pudo eliminar el contacto. Puede tener información relacionada."
            );
        }

        return "redirect:/contactos";
    }
}
