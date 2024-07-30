package com.example.sftp.repositories;

import com.example.sftp.entities.models.CitySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<CitySummary, String> {
}
