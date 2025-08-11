package com.erdjbe26.webservice.mapper;

import com.erdjbe26.webservice.dto.KategoriProdukDto;
import com.erdjbe26.webservice.entity.MstKategoriProduk;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface KategoriProdukMapper {
    KategoriProdukDto toDto(MstKategoriProduk kategoriProduk);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    MstKategoriProduk toEntity(KategoriProdukDto kategoriProdukDto);
}
