package com.jrce.spring.DocumentManager.controllers;

import com.jrce.spring.DocumentManager.dao.DocumentRepository;
import com.jrce.spring.DocumentManager.domain.Document;
import java.io.IOException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class uploadFileController {
    

    
    @Autowired
    private DocumentRepository documentRepository;
    
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("document") MultipartFile multipartFile,RedirectAttributes ra) throws IOException{
        
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Document document = new Document();
        document.setName(fileName);
        document.setContent(multipartFile.getBytes());
        document.setSize(multipartFile.getSize());
        document.setUploadTime(new Date());
        
        documentRepository.save(document);
        
        ra.addFlashAttribute("message", "The file was successfully loaded.");
        
        return "redirect:/";
    }
    
}
