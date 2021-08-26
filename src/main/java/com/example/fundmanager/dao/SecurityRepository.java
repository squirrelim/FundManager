package com.example.fundmanager.dao;

import com.example.fundmanager.entity.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@CrossOrigin("http://localhost:8080")
@Repository
public interface SecurityRepository extends JpaRepository<Security, Long> {
    @Query("SELECT s FROM Security s WHERE s.symbol=?1")
    Optional<Security> findSecurityBySymbol(String symbol);
}
