package com.json.operator.controller;


import com.json.operator.entity.JSONRecord;
import com.json.operator.service.JSONService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dataset")
public class JSONController {
    @Autowired
    JSONService jsonService;

    @PostMapping("/{datasetName}/record")
    public ResponseEntity<?> insertRecord(@PathVariable String datasetName, @RequestBody String jsonData) {
        try {
            JSONRecord savedRecord = jsonService.insertRecord(datasetName, jsonData);
            return ResponseEntity.ok(Map.of(
                    "message", "Record added successfully",
                    "dataset", datasetName,
                    "recordId", savedRecord.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{datasetName}/query")
    public ResponseEntity<?> queryDataset(@PathVariable String datasetName,
                                          @RequestParam(required = false) String groupBy,
                                          @RequestParam(required = false) String sortBy,
                                          @RequestParam(required = false) String order) {
        try {
            return ResponseEntity.ok(jsonService.queryDataset(datasetName, groupBy, sortBy, order));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
