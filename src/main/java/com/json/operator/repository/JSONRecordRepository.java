package com.json.operator.repository;

import com.json.operator.entity.JSONRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JSONRecordRepository extends JpaRepository<JSONRecord, Long> {
    List<JSONRecord> findByDatasetName(String datasetName);
}
