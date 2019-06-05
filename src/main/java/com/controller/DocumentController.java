package com.controller;

import com.model.data.ThreadLocalWithUserContext;
import com.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Controller
@Validated
@RequestMapping("/api")
public class DocumentController {

    public final String ADMIN_ROLE = "admin";

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }


    @GetMapping("documentsForm")
    public String getAddDocumentForm(Model model) {
        log.info("get documentsForm");
        if (ThreadLocalWithUserContext.getUser().hasRole(ADMIN_ROLE)) {
            model.addAttribute("documents", documentService.getAdminDocs());
            model.addAttribute("folders", documentService.getRoot());
        } else {

            model.addAttribute("documents", documentService
                    .getUsersDocuments(documentService
                            .getUsersFolder(ThreadLocalWithUserContext.getUser().getName())));
        }
        model.addAttribute("user", ThreadLocalWithUserContext.getUser().getName());
        return "documentsForm";
    }

    @PostMapping("addDoc")
    public String addDocument(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("add file " + file.getName() + " in document service");
        documentService.createDocument(file.getOriginalFilename(),
                file.getBytes(),
                ThreadLocalWithUserContext.getUser().getName());
        return "redirect:documentsForm";
    }


    @GetMapping("download/{docId}")
    public ResponseEntity downloadDocument(
            @PathVariable String docId
    ) {
        log.info("download file with id = " + docId + " from document service");
        return documentService.downloadDoc(docId);
    }

    @GetMapping("delete/{docId}")
    public String deleteDocument(
            @PathVariable String docId

    ) {
        log.info("delete file with id = " + docId + " from document service");
        documentService.deleteDoc(docId);
        return "redirect:/api/documentsForm";
    }
}

