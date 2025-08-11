package com.erdjbe26.webservice.mapper;

import com.erdjbe26.webservice.dto.LogSupplierDto;
import com.erdjbe26.webservice.entity.LogSupplier;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LogSupplierMapper {

    LogSupplierMapper INSTANCE = Mappers.getMapper(LogSupplierMapper.class);

    LogSupplierDto toDto(LogSupplier logSupplier);
}