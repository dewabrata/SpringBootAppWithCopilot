package com.erdjbe26.webservice.repository;

import com.erdjbe26.webservice.entity.LogProduk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface LogProdukRepository extends JpaRepository<LogProduk, Long> {
    
    @Query("SELECT l FROM LogProduk l WHERE (:idProduk IS NULL OR l.idProduk = :idProduk) " +
           "AND (:startDate IS NULL OR l.createdAt >= :startDate) " +
           "AND (:endDate IS NULL OR l.createdAt <= :endDate) " +
           "ORDER BY l.createdAt DESC")
    Page<LogProduk> findLogProdukWithFilters(@Param("idProduk") Long idProduk,
                                            @Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate,
                                            Pageable pageable);
}
