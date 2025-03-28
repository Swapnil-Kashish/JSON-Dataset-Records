package com.json.operator.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Data
@Entity
@Table(name = "json_records")
public class JSONRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String datasetName;

    @Column(columnDefinition = "jsonb") // "jsonb" is used for PostgreSQL DB
    @JdbcTypeCode(SqlTypes.JSON)
    private String jsonData;
}
