package com.erdjbe26.webservice.mapper;

import com.erdjbe26.webservice.dto.UserDto;
import com.erdjbe26.webservice.entity.MstUser;
import com.erdjbe26.webservice.dto.RegisterUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "akses.id", target = "idAkses")
    @Mapping(source = "akses.nama", target = "namaAkses")
    UserDto toDto(MstUser user);

    @Mapping(target = "akses", ignore = true)
    @Mapping(target = "isRegistered", ignore = true)
    @Mapping(target = "otp", ignore = true)
    @Mapping(target = "tokenEstafet", ignore = true)
    @Mapping(target = "linkImage", ignore = true)
    @Mapping(target = "pathImage", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true) // Password will be handled separately
    MstUser toEntity(RegisterUserDto registerUserDto);
}
