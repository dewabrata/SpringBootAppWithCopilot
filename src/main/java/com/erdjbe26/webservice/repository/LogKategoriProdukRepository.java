package com.erdjbe26.webservice.repository;

import com.erdjbe26.webservice.entity.LogKategoriProduk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface LogKategoriProdukRepository extends JpaRepository<LogKategoriProduk, Long> {
    
    @Query("SELECT l FROM LogKategoriProduk l WHERE (:idKategoriProduk IS NULL OR l.idKategoriProduk = :idKategoriProduk) " +
           "AND (:startDate IS NULL OR l.createdAt >= :startDate) " +
           "AND (:endDate IS NULL OR l.createdAt <= :endDate) " +
           "ORDER BY l.createdAt DESC")
    Page<LogKategoriProduk> findLogKategoriProdukWithFilters(@Param("idKategoriProduk") Long idKategoriProduk,
                                                            @Param("startDate") LocalDateTime startDate,
                                                            @Param("endDate") LocalDateTime endDate,
                                                            Pageable pageable);
}
