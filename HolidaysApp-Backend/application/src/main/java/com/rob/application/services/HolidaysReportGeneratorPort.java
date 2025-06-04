package com.rob.application.services;

import com.rob.application.ports.driving.HolidaysGeneratorPort;
import com.rob.domain.models.Holiday;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class HolidaysReportGeneratorPort implements HolidaysGeneratorPort {

    public ByteArrayResource exportHolidayReport(List<Holiday> holidays) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Vacaciones");
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle bodyStyle = createBodyStyle(workbook);

            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID Vacación", "Nombre", "Apellidos", "Inicio", "Fin", "Tipo", "Estado"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIndex = 1;
            for (Holiday h : holidays) {
                Row row = sheet.createRow(rowIndex++);

                String firstName = h.getUser().getEmployee().getFirstName();
                String lastName = h.getUser().getEmployee().getLastName();

                CellStyle idStyle = createCenteredBodyStyle(workbook);
                createCell(row, 0, h.getId(), idStyle);
                createCell(row, 1, firstName, bodyStyle);
                createCell(row, 2, lastName, bodyStyle);
                createCell(row, 3, h.getHolidayStartDate(), bodyStyle);
                createCell(row, 4, h.getHolidayEndDate(), bodyStyle);
                createCell(row, 5, h.getVacationType(), bodyStyle);
                createCell(row, 6, h.getVacationState(), bodyStyle);
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
                int currentWidth = sheet.getColumnWidth(i);
                sheet.setColumnWidth(i, (int) (currentWidth * 1.2));
            }


            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayResource(out.toByteArray());

        } catch (IOException e) {
            log.error("Error creando el fichero Excel de vacaciones", e);
            throw new RuntimeException("Error creando el fichero Excel Holidays Report", e);
        }
    }

    private CellStyle createHeaderStyle(Workbook wb) {
        XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();

        XSSFColor gftBlue = new XSSFColor(java.awt.Color.decode("#003399"), null);
        style.setFillForegroundColor(gftBlue);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = wb.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);

        setBorders(style);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }

    private CellStyle createBodyStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();

        setBorders(style);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.LEFT);

        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setColor(IndexedColors.BLACK.getIndex());
        style.setFont(font);

        return style;
    }

    private void setBorders(CellStyle style) {
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }

    private void createCell(Row row, int col, Object value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellStyle(style);
        if (value instanceof Number n) {
            cell.setCellValue(n.doubleValue());
        } else if (value instanceof LocalDate date) {
            cell.setCellValue(date.format(DateTimeFormatter.ofPattern("d/M/yyyy")));
        } else {
            cell.setCellValue(value != null ? value.toString() : "");
        }
    }

    private CellStyle createCenteredBodyStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        setBorders(style);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);

        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setColor(IndexedColors.BLACK.getIndex());
        style.setFont(font);

        return style;
    }
}
