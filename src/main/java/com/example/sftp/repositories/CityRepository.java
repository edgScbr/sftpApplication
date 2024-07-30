package com.example.sftp.repositories;

import com.example.sftp.entities.models.CitySummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<String, CitySummary> {
}
