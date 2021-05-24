package com.gmail.vladbaransky.service.util;

import com.gmail.vladbaransky.service.model.UrlDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Exporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<UrlDTO> urlList;

    public Exporter(List<UrlDTO> urlList) {
        this.urlList = urlList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Url");
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Url", style);
        createCell(row, 1, "Terms", style);
        createCell(row, 2, "Count", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (UrlDTO urlDTO : urlList) {
            int columnCount = 0;

            for (int i = 0; i < urlDTO.getTermDTOList().size(); i++) {
                Row row = sheet.createRow(rowCount++);
                createCell(row, columnCount++, urlDTO.getUrl(), style);
                createCell(row, columnCount++, urlDTO.getTermDTOList().get(i).getTerm(), style);
                createCell(row, columnCount++, urlDTO.getTermDTOList().get(i).getCount().toString(), style);
                columnCount = 0;
            }
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }
}
