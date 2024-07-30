package com.example.sftp.entities.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AgeRangeSummary {

    @Id
    private String range;
    private Integer maleCount;
    private Integer femaleCount;
    private Integer otherCount;
}
