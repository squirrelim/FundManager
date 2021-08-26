package com.example.fundmanager.dao;

import com.example.fundmanager.entity.Position;
import com.example.fundmanager.entity.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.util.Optional;

@CrossOrigin("http://localhost:4200")
@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    @Query("SELECT p FROM Position p WHERE p.securityId=?1 AND p.quantity=?2 AND p.datePurchased=?3")
    Optional<Position> findPositions(Long securityId, Long quantity, LocalDate datePurchased);
}
