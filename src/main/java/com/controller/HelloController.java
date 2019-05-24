package com.controller;

import com.model.data.Calculate;
import com.model.data.Holiday;
import com.service.CalculateService;
import com.service.DocumentService;
import com.service.HolidayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.chemistry.opencmis.client.api.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@Validated
@RequestMapping("/api")
public class HelloController {


    private final HolidayService holidayService;

    private final CalculateService calculateService;

    private final DocumentService documentService;

    public HelloController(HolidayService holidayService,
                           CalculateService calculateService, DocumentService documentService) {
        this.holidayService = holidayService;
        this.calculateService = calculateService;
        this.documentService = documentService;
    }


    @GetMapping
    public ResponseEntity index() throws IOException {

        return ResponseEntity.ok(holidayService.getAllHolidays(holidayService.getResponse("/holidays/all")));
    }


    @GetMapping("isholliday/{date}")
    public ResponseEntity isHoliday(@PathVariable String date) throws IOException {
        return ResponseEntity.ok(holidayService.getResponse("/isHoliday/" + date).body().string());
    }

    @GetMapping("calc/{company}/{millis}")
    public ResponseEntity calculate(@PathVariable String company,
                                    @PathVariable String millis) throws IOException {
        return ResponseEntity.ok(calculateService.calculateObj(holidayService.getResponse("/calculate/"
                + company + "/" + millis)));
    }


    @GetMapping("calcjson")
    public ResponseEntity calculateToJson(@RequestParam(name = "companyCode", required = false) String companyCode,
                                          @RequestParam(name = "submissionDate", required = false)
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                  LocalDateTime submissionDate,
                                          @RequestParam(name = "hearingDate", required = false)
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                  LocalDate hearingDate,
                                          @RequestParam(name = "deadline", required = false)
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                  LocalDate deadline
    ) throws IOException {
        Calculate calculate = new Calculate(companyCode, submissionDate, hearingDate, deadline);
        return ResponseEntity.ok(calculateService.calculateJson(calculate));
    }

    @PostMapping("mycalc")
    public String myCalculate(@RequestParam(name = "company") String company,
                              @RequestParam(name = "date")
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                              @NotNull
                                      LocalDate date,
                              Model model)
            throws IOException {
        List<Holiday> holidays = holidayService.getHolidaysList(holidayService.getResponse("/holidays/all"));
        try {
            model.addAttribute("deadline", calculateService.calculateDeadline(date, holidays, company));
        } catch (BadRequestException e) {
            model.addAttribute("errormsg", "bad value");
        }
        return "calculateform";
    }

    @GetMapping("calculateform")
    public String getCalculateForm() {
        return "calculateform";
    }

    @GetMapping("addDocument")
    public String getAddDocumentForm(Model model) {
        model.addAttribute("documents", documentService.getChildren());
        return "addDocument";
    }

    @PostMapping("addDoc")
    public String addDocument(@RequestParam("file") MultipartFile file,
                              Model model) throws IOException {
        Document document = documentService.createDocument(file.getOriginalFilename(), file.getBytes());
        model.addAttribute("newdoc", document.getContentStream().getFileName());
        model.addAttribute("documents", documentService.getChildren());
        return "redirect:addDocument";
    }


    @GetMapping("download/{docId}")
    public ResponseEntity downloadDocument(
            @PathVariable String docId
    ) {
        return documentService.downloadDoc(docId);
    }

    @GetMapping("delete/{docId}")
    public String deleteDocument(
            @PathVariable String docId,
            Model model
    ) {
        documentService.deleteDoc(docId);
        model.addAttribute("documents", documentService.getChildren());
        return "addDocument";
    }
}

//        /api/holidays/all
//        /api/isholliday/2019-01-01
//        /api/calc/0001/1555576817000
//        /api/calcjson/?companyCode=0001&submissionDate=2019-04-18T08:40:17&hearingDate=2019-04-23&deadline=2019-04-30