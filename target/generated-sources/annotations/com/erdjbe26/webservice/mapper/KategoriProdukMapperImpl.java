package com.erdjbe26.webservice.mapper;

import com.erdjbe26.webservice.dto.KategoriProdukDto;
import com.erdjbe26.webservice.entity.MstKategoriProduk;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-11T13:56:14+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Eclipse Adoptium)"
)
@Component
public class KategoriProdukMapperImpl implements KategoriProdukMapper {

    @Override
    public KategoriProdukDto toDto(MstKategoriProduk kategoriProduk) {
        if ( kategoriProduk == null ) {
            return null;
        }

        KategoriProdukDto kategoriProdukDto = new KategoriProdukDto();

        kategoriProdukDto.setId( kategoriProduk.getId() );
        kategoriProdukDto.setNamaProduk( kategoriProduk.getNamaProduk() );
        kategoriProdukDto.setDeskripsi( kategoriProduk.getDeskripsi() );
        kategoriProdukDto.setNotes( kategoriProduk.getNotes() );

        return kategoriProdukDto;
    }

    @Override
    public MstKategoriProduk toEntity(KategoriProdukDto kategoriProdukDto) {
        if ( kategoriProdukDto == null ) {
            return null;
        }

        MstKategoriProduk mstKategoriProduk = new MstKategoriProduk();

        mstKategoriProduk.setNamaProduk( kategoriProdukDto.getNamaProduk() );
        mstKategoriProduk.setDeskripsi( kategoriProdukDto.getDeskripsi() );
        mstKategoriProduk.setNotes( kategoriProdukDto.getNotes() );

        return mstKategoriProduk;
    }
}
