package com.json.operator.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.json.operator.entity.JSONRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.json.operator.repository.JSONRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class JSONService {
    @Autowired
    JSONRecordRepository jsonRecordRepository;
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    public JSONRecord insertRecord(String datasetName, String jsonData) throws Exception {
        JSONRecord record = new JSONRecord();
        record.setDatasetName(datasetName);
        record.setJsonData(jsonData);
        return jsonRecordRepository.save(record);
    }

    public Map<String, Object> queryDataset(String datasetName, String groupBy, String sortBy, String order) throws Exception {
        List<JSONRecord> records = jsonRecordRepository.findByDatasetName(datasetName);
        List<JsonNode> jsonRecords = records.stream()
                .map(r -> {
                    try {
                        return objectMapper.readTree(r.getJsonData());
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        Map<String, Object> response = new HashMap<>();

        // Group By Operation
        if (groupBy != null) {
            Map<String, List<JsonNode>> groupedRecords = jsonRecords.stream()
                    .collect(Collectors.groupingBy(node -> node.get(groupBy).asText()));
            response.put("groupedRecords", groupedRecords);
        }
        // Sort By Operation
        else if (sortBy != null) {
            Comparator<JsonNode> comparator = Comparator.comparing(node -> node.get(sortBy).asText());
            if ("desc".equalsIgnoreCase(order)) {
                comparator = comparator.reversed();
            }
            List<JsonNode> sortedRecords = jsonRecords.stream().sorted(comparator).toList();
            response.put("sortedRecords", sortedRecords);
        }

        return response;
    }
}
