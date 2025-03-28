package com.json.operator.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.json.operator.entity.JSONRecord;
import com.json.operator.repository.JSONRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JSONServiceTest {
    @Mock
    private JSONRecordRepository jsonRecordRepository;

    @InjectMocks
    private JSONService jsonService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInsertRecord() throws Exception {
        String datasetName = "employee_dataset";
        String jsonData = "{\"id\":4, \"name\":\"Amy Brown\", \"age\":31, \"department\":\"Engineering\"}";

        JSONRecord mockRecord = new JSONRecord();
        mockRecord.setId(1L);
        mockRecord.setDatasetName(datasetName);
        mockRecord.setJsonData(jsonData);

        when(jsonRecordRepository.save(any(JSONRecord.class))).thenReturn(mockRecord);

        JSONRecord savedRecord = jsonService.insertRecord(datasetName, jsonData);

        assertNotNull(savedRecord);
        assertEquals(datasetName, savedRecord.getDatasetName());
        assertEquals(jsonData, savedRecord.getJsonData());
    }

    @Test
    void testQueryDataset_GroupBy() throws Exception {
        String datasetName = "employee_dataset";
        JSONRecord record1 = new JSONRecord();
        record1.setDatasetName(datasetName);
        record1.setJsonData("{\"id\":1, \"name\":\"John Doe\", \"age\":30, \"department\":\"Engineering\"}");

        JSONRecord record2 = new JSONRecord();
        record2.setDatasetName(datasetName);
        record2.setJsonData("{\"id\":2, \"name\":\"Jane Smith\", \"age\":25, \"department\":\"Engineering\"}");

        JSONRecord record3 = new JSONRecord();
        record3.setDatasetName(datasetName);
        record3.setJsonData("{\"id\":3, \"name\":\"Alice Brown\", \"age\":28, \"department\":\"Marketing\"}");

        JSONRecord record4 = new JSONRecord();
        record4.setDatasetName(datasetName);
        record4.setJsonData("{\"id\":4, \"name\":\"Amy Brown\", \"age\":31, \"department\":\"Engineering\"}");

        when(jsonRecordRepository.findByDatasetName(datasetName)).thenReturn(List.of(record1, record2, record3, record4));

        Map<String, Object> response = jsonService.queryDataset(datasetName, "department", null, null);

        assertNotNull(response);
        assertTrue(response.containsKey("groupedRecords"));
        assertEquals(2, ((Map<?, ?>) response.get("groupedRecords")).size());
    }
}
