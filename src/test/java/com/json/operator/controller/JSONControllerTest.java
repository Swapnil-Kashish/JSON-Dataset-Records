package com.json.operator.controller;
import com.json.operator.entity.JSONRecord;
import com.json.operator.service.JSONService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class JSONControllerTest {
    private MockMvc mockMvc;

    @Mock
    private JSONService jsonService;

    @InjectMocks
    private JSONController jsonController;

    @Test
    void testInsertRecord() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jsonController).build();

        String datasetName = "employee_dataset";
        String jsonData = "{\"id\":4, \"name\":\"Amy Brown\", \"age\":31, \"department\":\"Engineering\"}";

        JSONRecord mockRecord = new JSONRecord();
        mockRecord.setId(4L);
        mockRecord.setDatasetName(datasetName);
        mockRecord.setJsonData(jsonData);

        when(jsonService.insertRecord(datasetName, jsonData)).thenReturn(mockRecord);

        mockMvc.perform(post("/api/dataset/{datasetName}/record", datasetName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Record added successfully"));
    }

    @Test
    void testQueryDataset_GroupBy() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jsonController).build();

        String datasetName = "employee_dataset";

        when(jsonService.queryDataset(datasetName, "department", null, null))
                .thenReturn(Map.of("groupedRecords", Map.of("Engineering", 3, "Marketing", 1)));

        mockMvc.perform(get("/api/dataset/{datasetName}/query", datasetName)
                        .param("groupBy", "department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupedRecords.Engineering").value(3));
    }
}
