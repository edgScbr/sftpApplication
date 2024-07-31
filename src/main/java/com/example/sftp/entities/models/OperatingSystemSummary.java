package com.example.sftp.entities.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@CsvRecord( separator = "," )
public class OperatingSystemSummary {

    @Id
    @DataField(pos = 1)
    private String os;
    @DataField(pos = 2)
    private Long total;
}
