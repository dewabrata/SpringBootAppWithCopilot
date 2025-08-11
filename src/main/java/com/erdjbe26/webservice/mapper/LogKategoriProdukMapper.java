package com.erdjbe26.webservice.mapper;

import com.erdjbe26.webservice.dto.LogKategoriProdukDto;
import com.erdjbe26.webservice.entity.LogKategoriProduk;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LogKategoriProdukMapper {

    LogKategoriProdukMapper INSTANCE = Mappers.getMapper(LogKategoriProdukMapper.class);

    LogKategoriProdukDto toDto(LogKategoriProduk logKategoriProduk);
}