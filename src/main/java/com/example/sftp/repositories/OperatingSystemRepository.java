package com.example.sftp.repositories;

import com.example.sftp.entities.models.OperatingSystemSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatingSystemRepository extends JpaRepository<Integer, OperatingSystemSummary> {
}
