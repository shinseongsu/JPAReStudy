package com.example.jpa.mail.repository;

import com.example.jpa.mail.entity.MailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailTemplateRepository extends JpaRepository<MailTemplate, Long> {
}
