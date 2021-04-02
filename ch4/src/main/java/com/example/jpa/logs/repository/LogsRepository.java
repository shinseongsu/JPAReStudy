package com.example.jpa.logs.repository;

import com.example.jpa.logs.entity.Logs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogsRepository extends JpaRepository<Logs, Long> {
}
