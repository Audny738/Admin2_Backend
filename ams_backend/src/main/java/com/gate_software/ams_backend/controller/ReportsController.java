package com.gate_software.ams_backend.controller;
import com.gate_software.ams_backend.service.ReportsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/reports")
@Tag(name = "Reports")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET})
public class ReportsController {
    @Autowired
    private ReportsService reportsService;

    @GetMapping("/users")
    @Operation(summary = "Generate a report of the controlled users", description = "Generate an Excel report of the active controlled users.")
    public ResponseEntity<String> generateUsersExcelReport(HttpServletResponse response) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currentDate = LocalDate.now().format(formatter);
        String fileName = "Usuarios_" + currentDate + ".xls";

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=" + fileName;
        response.setHeader(headerKey, headerValue);

        reportsService.generateUsersExcel(response);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/registration")
    @Parameter(
            name = "date",
            description = "Date format yyyy-MM-dd",
            in = ParameterIn.QUERY,
            schema = @Schema(type = "string", format = "yyyy-MM-dd"),
            example = "2023-10-15"
    )
    @Operation(summary = "Generate a report of the checkIn and checkOut Records", description = "Generate an Excel report of the checkIn and checkOut Records of the controlled users of a day.")
    public ResponseEntity<T> generateCheckIntOutRegistersReport(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate selectedDate,
            HttpServletResponse response) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = selectedDate.format(formatter);
        String fileName = "RegistrosDeEntradaSalida_" + formattedDate + ".xls";

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=" + fileName;
        response.setHeader(headerKey, headerValue);

        reportsService.generateCheckIntOutRegistersExcel(response, selectedDate);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/payroll")
    @Operation(summary = "Generate a report of work hours and salary", description = "Generate a report of work hours and salary for active controlled users in a date range.")
    public ResponseEntity<T> generateWorkHourReport(
            @Parameter(
                    name = "startDate",
                    description = "Start date in the format yyyy-MM-dd",
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "string", format = "yyyy-MM-dd"),
                    example = "2023-10-15"
            )
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @Parameter(
                    name = "endDate",
                    description = "End date in the format yyyy-MM-dd",
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "string", format = "yyyy-MM-dd"),
                    example = "2023-10-20"
            )
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            HttpServletResponse response) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedStartDate = startDate.format(formatter);
        String formattedEndDate = endDate.format(formatter);
        String fileName = "HorasTrabajadas_" + formattedStartDate + "_al_" + formattedEndDate + ".xls";

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=" + fileName;
        response.setHeader(headerKey, headerValue);

        reportsService.generateWorkHourReport(response, startDate, endDate);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
