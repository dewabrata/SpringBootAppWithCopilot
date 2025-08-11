package com.erdjbe26.webservice.mapper;

import com.erdjbe26.webservice.dto.LogProdukDto;
import com.erdjbe26.webservice.entity.LogProduk;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LogProdukMapper {

    LogProdukMapper INSTANCE = Mappers.getMapper(LogProdukMapper.class);

    LogProdukDto toDto(LogProduk logProduk);
}