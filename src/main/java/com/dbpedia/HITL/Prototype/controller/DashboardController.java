package com.dbpedia.HITL.Prototype.controller;

import com.dbpedia.HITL.Prototype.service.TripleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private TripleService service;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("triples", service.getPending());
        model.addAttribute("stats", service.getStats());
        return "dashboard";
    }
}