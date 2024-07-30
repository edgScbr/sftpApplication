package com.example.sftp.repositories;

import com.example.sftp.entities.models.AgeRangeSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgeRangeRepository extends JpaRepository<AgeRangeSummary, String> {
}
