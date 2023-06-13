package dev.prasad.apps.pdf.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {
    private PDDocument document;
    private PDPage currentPage;
    private PDPageContentStream contentStream;
    private float currentPageHeight;
    private float margin = 50;
    private float yPosition;

    public PDFGenerator() {
        document = new PDDocument();
        currentPage = new PDPage(PDRectangle.A4);
        document.addPage(currentPage);
        currentPageHeight = currentPage.getMediaBox().getHeight() - 2 * margin;

        try {
            contentStream = new PDPageContentStream(document, currentPage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        yPosition = currentPageHeight;
    }

    public void addHeading(String headingText, PDType1Font font, int fontSize) {
        try {
            contentStream.beginText(); // Add this line
            contentStream.setFont(font, fontSize);
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText(headingText);
            contentStream.newLine();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.endText(); // Add this line
            yPosition -= 30;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addContent(String contentText) {
        try {
            contentStream.beginText();
            contentStream.newLineAtOffset(10, 10);
            contentStream.showText(contentText);
            contentStream.endText();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTable(List<List<String>> tableData, float[] columnWidths, PDType1Font font, int fontSize) {
        float tableTop = yPosition;
        float cellHeight = 20;
        float rowHeight = cellHeight;
        float tableHeight = cellHeight * tableData.size();

        try {
            contentStream.setFont(font, fontSize);

            // Draw table headers
            float nextX = margin;
            for (int i = 0; i < columnWidths.length; i++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(nextX, yPosition);
                contentStream.showText("Header " + (i + 1));
                contentStream.endText();
                nextX += columnWidths[i];
            }

            yPosition -= rowHeight;

            // Draw table content
            for (List<String> row : tableData) {
                nextX = margin;
                for (String cellData : row) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(nextX, yPosition);
                    contentStream.showText(cellData);
                    contentStream.endText();
                    nextX += columnWidths[row.indexOf(cellData)];
                }
                yPosition -= rowHeight;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(ByteArrayOutputStream outputStream) {
        try {
            contentStream.close();
            document.save(outputStream);
            document.close();
            System.out.println("PDF created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

