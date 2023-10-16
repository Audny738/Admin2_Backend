package com.gate_software.ams_backend.service;
import com.gate_software.ams_backend.dto.CheckInOutContainer;
import com.gate_software.ams_backend.entity.CheckInRecords;
import com.gate_software.ams_backend.entity.CheckOutRecords;
import com.gate_software.ams_backend.entity.ControlledUser;
import com.gate_software.ams_backend.repository.ControlledUserRepository;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportsService {
    @Autowired
    private ControlledUserRepository controlledUserRepository;
    @Autowired
    private ControlledUserService controlledUserService;

    public void generateCheckIntOutRegistersExcel(HttpServletResponse response, LocalDate selectedDate) throws IOException {
        List<ControlledUser> controlledUsers = controlledUserRepository.findAll();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Registros de entrada y de salida");
        HSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("Nombre");
        row.createCell(1).setCellValue("Horario de entrada");
        row.createCell(2).setCellValue("Horario de salida");

        int dataRowIndex = 1;
        CheckInOutContainer records;

        for(ControlledUser user : controlledUsers){
            if (!user.isActive()) continue;
            HSSFRow datarow = sheet.createRow(dataRowIndex);
            datarow.createCell(0).setCellValue(user.getName());
            records = controlledUserService.getCheckInOutRecordsForDate(selectedDate, user.getId());
            datarow.createCell(1).setCellValue(
                    records.getCheckInRecord() != null ?
                            dateFormat.format(records.getCheckInRecord().getEntryDatetime()) : " ");
            datarow.createCell(2).setCellValue(
                    records.getCheckOutRecord() != null ?
                            dateFormat.format(records.getCheckOutRecord().getExitDatetime()) : " ");
            dataRowIndex ++;
        }

        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }

    public void generateUsersExcel(HttpServletResponse response) throws IOException {
        List<ControlledUser> controlledUsers = controlledUserRepository.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Usuarios controlados");
        HSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("Nombre");
        row.createCell(1).setCellValue("Correo");
        row.createCell(2).setCellValue("Horario Lunes");
        row.createCell(3).setCellValue("Horario Martes");
        row.createCell(4).setCellValue("Horario Miércoles");
        row.createCell(5).setCellValue("Horario Jueves");
        row.createCell(6).setCellValue("Horario Viernes");
        row.createCell(7).setCellValue("Horario Sábado");
        row.createCell(8).setCellValue("Horario Domingo");

        int dataRowIndex = 1;
        HashMap<Integer, String> schedules;

        for(ControlledUser user : controlledUsers){
            if (!user.isActive()) continue;
            HSSFRow datarow = sheet.createRow(dataRowIndex);
            datarow.createCell(0).setCellValue(user.getName());
            datarow.createCell(1).setCellValue(user.getEmail());
            schedules = controlledUserService.getSchedulesPerDay(user.getId());
            for (int i=1; i<8; i++){
                if(schedules.get(i) == null){
                    datarow.createCell(i +1).setCellValue(" ");
                }
                else{
                    datarow.createCell(i + 1).setCellValue(schedules.get(i));
                }
            }
            dataRowIndex ++;
        }

        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }
}
