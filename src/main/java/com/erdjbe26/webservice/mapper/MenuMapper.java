package com.erdjbe26.webservice.mapper;

import com.erdjbe26.webservice.dto.MenuDto;
import com.erdjbe26.webservice.entity.MstMenu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    @Mapping(source = "groupMenu.id", target = "idGroupMenu")
    @Mapping(source = "groupMenu.nama", target = "namaGroupMenu")
    MenuDto toDto(MstMenu menu);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "groupMenu", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    MstMenu toEntity(MenuDto menuDto);
}
