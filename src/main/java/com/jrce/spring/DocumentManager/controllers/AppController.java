package com.jrce.spring.DocumentManager.controllers;

import com.jrce.spring.DocumentManager.dao.DocumentRepository;
import com.jrce.spring.DocumentManager.domain.Document;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @Autowired
    private DocumentRepository documentRepository;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Document> listDocs = documentRepository.findAll();
        model.addAttribute("listDocs", listDocs);
        return "home";
    }
}
