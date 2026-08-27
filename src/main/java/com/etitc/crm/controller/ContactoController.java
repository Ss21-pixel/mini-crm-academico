package com.etitc.crm.controller;

import com.etitc.crm.entity.Contacto;
import com.etitc.crm.service.ContactoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ContactoController {

    private final ContactoService contactoService;

    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    @GetMapping("/contactos")
    public String listarContactos(Model model) {

        List<Contacto> contactos = contactoService.listarTodos();

        model.addAttribute("contactos", contactos);

        return "contactos";
    }
}