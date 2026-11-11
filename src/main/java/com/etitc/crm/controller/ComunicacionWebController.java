package com.etitc.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ComunicacionWebController {

    @GetMapping("/comunicaciones")
    public String mostrarComunicaciones() {
        return "comunicaciones";
    }
}