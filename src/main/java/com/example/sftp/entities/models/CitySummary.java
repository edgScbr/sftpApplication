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
public class CitySummary {

    @Id
    private String city;
    private Long maleCount;
    private Long femaleCount;
    private Long otherCount;
}
