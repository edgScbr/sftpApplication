package com.example.sftp.repositories;

import com.example.sftp.entities.models.GenderSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<GenderSummary, Long> {
}
