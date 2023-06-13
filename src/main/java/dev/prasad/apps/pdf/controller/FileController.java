package dev.prasad.apps.pdf.controller;

import dev.prasad.apps.pdf.service.TableDataService;
import dev.prasad.apps.pdf.util.PDFGenerator;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class FileController {

    private final TableDataService tableDataService;

    public FileController(TableDataService tableDataService) {
        this.tableDataService = tableDataService;
    }

    @GetMapping("/download-pdf")
    public ResponseEntity<byte[]> downloadPDF() throws IOException {
        // Generate the PDF
        byte[] pdfBytes = generatePDF();

        // Set the headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "search-data.pdf");
        headers.setContentLength(pdfBytes.length);

        // Return the PDF as a byte array in the response body
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    public byte[] generatePDF() throws IOException {
        PDFGenerator pdfGenerator = new PDFGenerator();
        pdfGenerator.addHeading("Sample PDF Page", PDType1Font.HELVETICA_BOLD, 20);
        pdfGenerator.addContent("This is a sample PDF page created using Apache PDFBox.");

        // Get table data from the mock service
        List<List<String>> tableData = tableDataService.getMockData();

        float[] columnWidths = {100, 100, 100, 100, 100};
        pdfGenerator.addTable(tableData, columnWidths, PDType1Font.HELVETICA, 12);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        pdfGenerator.save(outputStream);
        return outputStream.toByteArray();
    }
}
