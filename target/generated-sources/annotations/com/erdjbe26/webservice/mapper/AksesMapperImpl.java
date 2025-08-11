package com.erdjbe26.webservice.mapper;

import com.erdjbe26.webservice.dto.AksesDto;
import com.erdjbe26.webservice.entity.MstAkses;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-11T13:59:20+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Eclipse Adoptium)"
)
@Component
public class AksesMapperImpl implements AksesMapper {

    @Override
    public AksesDto toDto(MstAkses akses) {
        if ( akses == null ) {
            return null;
        }

        AksesDto aksesDto = new AksesDto();

        aksesDto.setId( akses.getId() );
        aksesDto.setNama( akses.getNama() );
        aksesDto.setDeskripsi( akses.getDeskripsi() );

        return aksesDto;
    }

    @Override
    public MstAkses toEntity(AksesDto aksesDto) {
        if ( aksesDto == null ) {
            return null;
        }

        MstAkses mstAkses = new MstAkses();

        mstAkses.setNama( aksesDto.getNama() );
        mstAkses.setDeskripsi( aksesDto.getDeskripsi() );

        return mstAkses;
    }
}
