package com.example.fundmanager.dao;

import com.example.fundmanager.entity.FundManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FundManagerRepository extends JpaRepository<FundManager, Long> {
    @Query("SELECT m FROM FundManager m WHERE m.firstName=?1 AND m.lastName=?2")
    Optional<FundManager> findFundManagerByName(String firstName, String lastName);
}
