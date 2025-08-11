package com.erdjbe26.webservice.mapper;

import com.erdjbe26.webservice.dto.RegisterUserDto;
import com.erdjbe26.webservice.dto.UserDto;
import com.erdjbe26.webservice.entity.MstAkses;
import com.erdjbe26.webservice.entity.MstUser;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-11T19:16:15+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(MstUser user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setIdAkses( userAksesId( user ) );
        userDto.setNamaAkses( userAksesNama( user ) );
        userDto.setId( user.getId() );
        userDto.setUsername( user.getUsername() );
        userDto.setNamaLengkap( user.getNamaLengkap() );
        userDto.setEmail( user.getEmail() );
        userDto.setNoHp( user.getNoHp() );
        userDto.setAlamat( user.getAlamat() );
        userDto.setTanggalLahir( user.getTanggalLahir() );
        userDto.setIsRegistered( user.getIsRegistered() );

        return userDto;
    }

    @Override
    public MstUser toEntity(RegisterUserDto registerUserDto) {
        if ( registerUserDto == null ) {
            return null;
        }

        MstUser mstUser = new MstUser();

        mstUser.setUsername( registerUserDto.getUsername() );
        mstUser.setNamaLengkap( registerUserDto.getNamaLengkap() );
        mstUser.setEmail( registerUserDto.getEmail() );
        mstUser.setNoHp( registerUserDto.getNoHp() );
        mstUser.setAlamat( registerUserDto.getAlamat() );
        mstUser.setTanggalLahir( registerUserDto.getTanggalLahir() );

        return mstUser;
    }

    private Long userAksesId(MstUser mstUser) {
        if ( mstUser == null ) {
            return null;
        }
        MstAkses akses = mstUser.getAkses();
        if ( akses == null ) {
            return null;
        }
        Long id = akses.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String userAksesNama(MstUser mstUser) {
        if ( mstUser == null ) {
            return null;
        }
        MstAkses akses = mstUser.getAkses();
        if ( akses == null ) {
            return null;
        }
        String nama = akses.getNama();
        if ( nama == null ) {
            return null;
        }
        return nama;
    }
}
