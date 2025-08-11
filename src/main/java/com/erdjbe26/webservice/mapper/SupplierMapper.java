package com.erdjbe26.webservice.mapper;

import com.erdjbe26.webservice.dto.SupplierDto;
import com.erdjbe26.webservice.entity.MstSupplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierDto toDto(MstSupplier supplier);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    MstSupplier toEntity(SupplierDto supplierDto);
}
