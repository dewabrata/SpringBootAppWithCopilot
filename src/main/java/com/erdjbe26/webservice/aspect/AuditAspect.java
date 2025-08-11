package com.erdjbe26.webservice.aspect;

import com.erdjbe26.webservice.entity.*;
import com.erdjbe26.webservice.repository.LogKategoriProdukRepository;
import com.erdjbe26.webservice.repository.LogProdukRepository;
import com.erdjbe26.webservice.repository.LogSupplierRepository;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuditAspect {

    private final LogProdukRepository logProdukRepository;
    private final LogKategoriProdukRepository logKategoriProdukRepository;
    private final LogSupplierRepository logSupplierRepository;

    public AuditAspect(LogProdukRepository logProdukRepository, LogKategoriProdukRepository logKategoriProdukRepository, LogSupplierRepository logSupplierRepository) {
        this.logProdukRepository = logProdukRepository;
        this.logKategoriProdukRepository = logKategoriProdukRepository;
        this.logSupplierRepository = logSupplierRepository;
    }

    @AfterReturning(pointcut = "@annotation(auditable)", returning = "result")
    public void audit(Auditable auditable, Object result) {
        // This is a simplified audit implementation. A real-world scenario would be more complex.
        // It would likely involve getting the current user from the security context.

        long createdBy = 1L; // System user for now

        if ("KategoriProduk".equals(auditable.entity())) {
            MstKategoriProduk kategoriProduk = (MstKategoriProduk) result;
            LogKategoriProduk log = new LogKategoriProduk();
            log.setIdKategoriProduk(kategoriProduk.getId());
            log.setNamaProduk(kategoriProduk.getNamaProduk());
            log.setDeskripsi(kategoriProduk.getDeskripsi());
            log.setNotes(kategoriProduk.getNotes());
            log.setFlag(auditable.action().charAt(0));
            log.setCreatedAt(LocalDateTime.now());
            log.setCreatedBy(createdBy);
            logKategoriProdukRepository.save(log);
        } else if ("Produk".equals(auditable.entity())) {
            MstProduk produk = (MstProduk) result;
            LogProduk log = new LogProduk();
            log.setIdProduk(produk.getId());
            log.setIdKategoriProduk(produk.getKategoriProduk().getId());
            log.setNamaProduk(produk.getNamaProduk());
            log.setMerk(produk.getMerk());
            log.setModel(produk.getModel());
            log.setWarna(produk.getWarna());
            log.setDeskripsiProduk(produk.getDeskripsiProduk());
            log.setStok(produk.getStok());
            log.setFlag(auditable.action().charAt(0));
            log.setCreatedAt(LocalDateTime.now());
            log.setCreatedBy(createdBy);
            logProdukRepository.save(log);
        } else if ("Supplier".equals(auditable.entity())) {
            MstSupplier supplier = (MstSupplier) result;
            LogSupplier log = new LogSupplier();
            log.setIdSupplier(supplier.getId());
            log.setNamaSupplier(supplier.getNamaSupplier());
            log.setAlamatSupplier(supplier.getAlamatSupplier());
            log.setFlag(auditable.action().charAt(0));
            log.setCreatedAt(LocalDateTime.now());
            log.setCreatedBy(createdBy);
            logSupplierRepository.save(log);
        }
    }
}
