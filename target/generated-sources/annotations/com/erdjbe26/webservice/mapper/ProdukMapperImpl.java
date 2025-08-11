package com.erdjbe26.webservice.mapper;

import com.erdjbe26.webservice.dto.ProdukDto;
import com.erdjbe26.webservice.entity.MstKategoriProduk;
import com.erdjbe26.webservice.entity.MstProduk;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-11T13:56:14+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Eclipse Adoptium)"
)
@Component
public class ProdukMapperImpl implements ProdukMapper {

    @Override
    public ProdukDto toDto(MstProduk produk) {
        if ( produk == null ) {
            return null;
        }

        ProdukDto produkDto = new ProdukDto();

        produkDto.setIdKategoriProduk( produkKategoriProdukId( produk ) );
        produkDto.setNamaKategoriProduk( produkKategoriProdukNamaProduk( produk ) );
        produkDto.setId( produk.getId() );
        produkDto.setNamaProduk( produk.getNamaProduk() );
        produkDto.setMerk( produk.getMerk() );
        produkDto.setModel( produk.getModel() );
        produkDto.setWarna( produk.getWarna() );
        produkDto.setDeskripsiProduk( produk.getDeskripsiProduk() );
        produkDto.setStok( produk.getStok() );

        return produkDto;
    }

    @Override
    public MstProduk toEntity(ProdukDto produkDto) {
        if ( produkDto == null ) {
            return null;
        }

        MstProduk mstProduk = new MstProduk();

        mstProduk.setNamaProduk( produkDto.getNamaProduk() );
        mstProduk.setMerk( produkDto.getMerk() );
        mstProduk.setModel( produkDto.getModel() );
        mstProduk.setWarna( produkDto.getWarna() );
        mstProduk.setDeskripsiProduk( produkDto.getDeskripsiProduk() );
        mstProduk.setStok( produkDto.getStok() );

        return mstProduk;
    }

    private Long produkKategoriProdukId(MstProduk mstProduk) {
        if ( mstProduk == null ) {
            return null;
        }
        MstKategoriProduk kategoriProduk = mstProduk.getKategoriProduk();
        if ( kategoriProduk == null ) {
            return null;
        }
        Long id = kategoriProduk.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String produkKategoriProdukNamaProduk(MstProduk mstProduk) {
        if ( mstProduk == null ) {
            return null;
        }
        MstKategoriProduk kategoriProduk = mstProduk.getKategoriProduk();
        if ( kategoriProduk == null ) {
            return null;
        }
        String namaProduk = kategoriProduk.getNamaProduk();
        if ( namaProduk == null ) {
            return null;
        }
        return namaProduk;
    }
}
