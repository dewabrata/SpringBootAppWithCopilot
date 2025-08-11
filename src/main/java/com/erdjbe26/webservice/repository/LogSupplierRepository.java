package com.erdjbe26.webservice.repository;

import com.erdjbe26.webservice.entity.LogSupplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface LogSupplierRepository extends JpaRepository<LogSupplier, Long> {
    
    @Query("SELECT l FROM LogSupplier l WHERE (:idSupplier IS NULL OR l.idSupplier = :idSupplier) " +
           "AND (:startDate IS NULL OR l.createdAt >= :startDate) " +
           "AND (:endDate IS NULL OR l.createdAt <= :endDate) " +
           "ORDER BY l.createdAt DESC")
    Page<LogSupplier> findLogSupplierWithFilters(@Param("idSupplier") Long idSupplier,
                                                @Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate,
                                                Pageable pageable);
}
