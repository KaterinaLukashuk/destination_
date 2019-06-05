package com.controller;

import com.model.data.Calculate;
import com.model.data.Holiday;
import com.service.CalculateService;
import com.service.HolidayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@Validated
@RequestMapping("/api")
public class DestinationController {

    private final HolidayService holidayService;

    private final CalculateService calculateService;


    public DestinationController(HolidayService holidayService,
                                 CalculateService calculateService) {
        this.holidayService = holidayService;
        this.calculateService = calculateService;
    }


    @GetMapping
    public ResponseEntity index() throws IOException {
        log.info("get all holidays from destination");
        return ResponseEntity.ok(holidayService.getAllHolidays(holidayService.getResponse("/holidays/all")));
    }


    @GetMapping("isholliday/{date}")
    public ResponseEntity isHoliday(@PathVariable String date) throws IOException {
        log.info("check is date a holiday using holidays from destination");
        return ResponseEntity.ok(holidayService.getResponse("/isHoliday/" + date).body().string());
    }

    @GetMapping("calc/{company}/{millis}")
    public ResponseEntity calculate(@PathVariable String company,
                                    @PathVariable String millis) throws IOException {
        log.info("get deadline from destination");
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
        log.info("parse Calculate object to json");
        Calculate calculate = new Calculate(companyCode, submissionDate, hearingDate, deadline);
        return ResponseEntity.ok(calculateService.calculateJson(calculate));
    }

    @PostMapping("calcDeadline")
    public String calculateDeadline(@RequestParam(name = "company") String company,
                                    @RequestParam(name = "date")
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                    @NotNull
                                            LocalDate date,
                                    Model  model)
            throws IOException {
        log.info("calculate deadline by company code and date");
        List<Holiday> holidays = holidayService.getHolidaysList(holidayService.getResponse("/holidays/all"));
        model.addAttribute("deadline", calculateService.calculateDeadline(date, holidays, company));
        return  "deadline";
    }

    @GetMapping("calculateForm")
    public String getCalculateForm() {
        log.info("get calculateForm");
        return "calculateForm";
    }

}

//        /api/holidays/all
//        /api/isholliday/2019-01-01
//        /api/calc/0001/1555576817000
//        /api/calcjson/?companyCode=0001&submissionDate=2019-04-18T08:40:17&hearingDate=2019-04-23&deadline=2019-04-30