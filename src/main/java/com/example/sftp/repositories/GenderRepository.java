package com.example.sftp.repositories;

import com.example.sftp.entities.models.GenderSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<Integer, GenderSummary> {
}
