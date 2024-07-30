package com.example.sftp.entities.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@CsvRecord( separator = "," )
public class AgeRangeSummary {

    @Id
    @DataField(pos = 1)
    private String range;
    @DataField(pos = 2)
    private Integer maleCount;
    @DataField(pos = 3)
    private Integer femaleCount;
    @DataField(pos = 4)
    private Integer otherCount;
}
