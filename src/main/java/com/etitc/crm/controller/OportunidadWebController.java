package com.etitc.crm.controller;

import com.etitc.crm.modelo.Contacto;
import com.etitc.crm.modelo.Oportunidad;
import com.etitc.crm.service.ContactoService;
import com.etitc.crm.service.OportunidadService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/oportunidades")
public class OportunidadWebController {

    private final OportunidadService oportunidadService;
    private final ContactoService contactoService;

    public OportunidadWebController(
            OportunidadService oportunidadService,
            ContactoService contactoService) {
        this.oportunidadService = oportunidadService;
        this.contactoService = contactoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute(
                "oportunidades",
                oportunidadService.listarTodos()
        );
        return "oportunidades";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("oportunidad", new Oportunidad());
        model.addAttribute("contactos", contactoService.listarTodos());
        return "oportunidad-form";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes) {

        Oportunidad oportunidad = oportunidadService
                .buscarPorId(id)
                .orElse(null);

        if (oportunidad == null) {
            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    "No se encontró la oportunidad."
            );
            return "redirect:/oportunidades";
        }

        model.addAttribute("oportunidad", oportunidad);
        model.addAttribute("contactos", contactoService.listarTodos());

        return "oportunidad-form";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute("oportunidad")
            Oportunidad oportunidad,
            BindingResult resultado,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (resultado.hasErrors()) {
            model.addAttribute("contactos", contactoService.listarTodos());
            return "oportunidad-form";
        }

        if (oportunidad.getContacto() == null
                || oportunidad.getContacto().getId() == null) {
            resultado.rejectValue(
                    "contacto",
                    "error.contacto",
                    "Debes seleccionar un contacto."
            );
            model.addAttribute("contactos", contactoService.listarTodos());
            return "oportunidad-form";
        }

        Contacto contacto = contactoService
                .buscarPorId(oportunidad.getContacto().getId())
                .orElse(null);

        if (contacto == null) {
            resultado.rejectValue(
                    "contacto",
                    "error.contacto",
                    "El contacto seleccionado no existe."
            );
            model.addAttribute("contactos", contactoService.listarTodos());
            return "oportunidad-form";
        }

        oportunidad.setContacto(contacto);
        oportunidadService.guardar(oportunidad);

        redirectAttributes.addFlashAttribute(
                "mensaje",
                "Oportunidad guardada correctamente."
        );

        return "redirect:/oportunidades";
    }
}