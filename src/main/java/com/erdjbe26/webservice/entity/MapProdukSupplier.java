package com.erdjbe26.webservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
class MapProdukSupplierId implements Serializable {
    @Column(name = "IDProduk")
    private Long idProduk;

    @Column(name = "IDSupplier")
    private Long idSupplier;
}

@Entity
@Table(name = "MapProdukSupplier")
@Getter
@Setter
public class MapProdukSupplier {

    @EmbeddedId
    private MapProdukSupplierId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProduk")
    @JoinColumn(name = "IDProduk")
    private MstProduk produk;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idSupplier")
    @JoinColumn(name = "IDSupplier")
    private MstSupplier supplier;
}
