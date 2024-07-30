package com.example.sftp.repositories;

import com.example.sftp.entities.models.OperatingSystemSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatingSystemRepository extends JpaRepository<OperatingSystemSummary, Long> {
}
