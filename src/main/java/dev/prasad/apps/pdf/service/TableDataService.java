package dev.prasad.apps.pdf.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TableDataService {

    public List<List<String>> getMockData() {
        List<List<String>> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<String> row = new ArrayList<>();
            row.add("Cell 1, Row" + i);
            row.add("Cell 2, Row" + i);
            row.add("Cell 3, Row" + i);
            row.add("Cell 4, Row" + i);
            row.add("Cell 5, Row" + i);
            data.add(row);
        }
        return data;
    }
}
