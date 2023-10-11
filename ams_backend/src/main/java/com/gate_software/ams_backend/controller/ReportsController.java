package com.gate_software.ams_backend.controller;
import com.gate_software.ams_backend.service.ControlledUserService;
import com.gate_software.ams_backend.service.ReportsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/reports")
@Tag(name = "Reports")
public class ReportsController {
    @Autowired
    private ReportsService reportsService;
    @Autowired
    private ControlledUserService controlledUserService;

    @GetMapping("/users")
    public ResponseEntity<String> generateExcelReport(HttpServletResponse response) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currentDate = LocalDate.now().format(formatter);
        String fileName = "Usuarios_" + currentDate + ".xls";

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=" + fileName;
        response.setHeader(headerKey, headerValue);

        reportsService.generateExcel(response);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
