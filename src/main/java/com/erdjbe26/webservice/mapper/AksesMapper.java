package com.erdjbe26.webservice.mapper;

import com.erdjbe26.webservice.dto.AksesDto;
import com.erdjbe26.webservice.entity.MstAkses;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AksesMapper {
    AksesDto toDto(MstAkses akses);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    MstAkses toEntity(AksesDto aksesDto);
}
