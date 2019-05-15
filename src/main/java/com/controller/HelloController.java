package com.controller;

import com.model.data.Calculate;
import com.model.data.Holiday;
import com.service.CalculateService;
import com.service.HolidayService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Controller
@Validated
@RequestMapping("/api")
public class HelloController {


    private final HolidayService holidayService;


    private final CalculateService calculateService;

    public HelloController(HolidayService holidayService,
                           CalculateService calculateService) {
        this.holidayService = holidayService;
        this.calculateService = calculateService;
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
    //@GetMapping("mycalc/{company}/{date}")
    public String myCalculate(@RequestParam(name = "company") String company,
                              @RequestParam(name = "date")
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//                              @Valid @NotNull(message = "date is empty" )
                                      LocalDate date,
                              Model model)
            throws IOException {
        if (date == null) {
            model.addAttribute("errordate", "the date is empty");
            return "calculateform";
        }
        List<Holiday> holidays = holidayService.getHolidaysList(holidayService.getResponse("/holidays/all"));
        try {
            Calculate calculate = calculateService.calculateDeadline(date, holidays, company);
            model.addAttribute("deadline", calculate);
            return "calculateform";
        } catch (BadRequestException e) {
            model.addAttribute("errormsg", "bad value");
            return "calculateform";
        }
    }

    @GetMapping("calculateform")
    public String getCalculateForm(Model model) {
        return "calculateform";
    }
}

//        /api/holidays/all
//        /api/isholliday/2019-01-01
//        /api/calc/0001/1555576817000
//        /api/calcjson/?companyCode=0001&submissionDate=2019-04-18T08:40:17&hearingDate=2019-04-23&deadline=2019-04-30