package com.jrce.spring.DocumentManager.dao;

import com.jrce.spring.DocumentManager.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>{
    
}
