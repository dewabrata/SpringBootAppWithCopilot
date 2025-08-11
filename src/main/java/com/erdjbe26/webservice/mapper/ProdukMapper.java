package com.erdjbe26.webservice.mapper;

import com.erdjbe26.webservice.dto.ProdukDto;
import com.erdjbe26.webservice.entity.MstProduk;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProdukMapper {

    @Mapping(source = "kategoriProduk.id", target = "idKategoriProduk")
    @Mapping(source = "kategoriProduk.namaProduk", target = "namaKategoriProduk")
    ProdukDto toDto(MstProduk produk);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "kategoriProduk", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    MstProduk toEntity(ProdukDto produkDto);
}
