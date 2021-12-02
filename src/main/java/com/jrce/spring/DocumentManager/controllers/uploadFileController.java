package com.jrce.spring.DocumentManager.controllers;

import com.jrce.spring.DocumentManager.dao.DocumentRepository;
import com.jrce.spring.DocumentManager.domain.Document;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import static org.hibernate.criterion.Projections.id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class uploadFileController {

    @Autowired
    private DocumentRepository documentRepository;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("document") MultipartFile multipartFile, RedirectAttributes ra) throws IOException, Exception {
        String message = "";
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Document document = new Document();
        document.setName(fileName);
        document.setContent(multipartFile.getBytes());
        document.setSize(multipartFile.getSize());
        document.setUploadTime(new Date());

        try {
            documentRepository.save(document);
            message = "The file was successfully loaded.";
        } catch (Exception e) {
            message = "The file is to large: " + e.getMessage();
        }

        ra.addFlashAttribute("message", message);

        return "redirect:/";
    }

    @GetMapping("/download")
    public void downloadFile(@Param("id") long id, HttpServletResponse response, RedirectAttributes ra) throws Exception {
        Optional<Document> result = documentRepository.findById(id);
        if (!result.isPresent()) {
            throw new Exception("Could not find document with Id: " + id);
        }

        Document document = result.get();

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValues = "attachment; filename=" + document.getName();

        response.setHeader(headerKey, headerValues);

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(document.getContent());
        outputStream.close();
    }

}
