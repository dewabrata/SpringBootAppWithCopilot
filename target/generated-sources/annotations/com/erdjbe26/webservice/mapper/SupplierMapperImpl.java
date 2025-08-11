package com.erdjbe26.webservice.mapper;

import com.erdjbe26.webservice.dto.SupplierDto;
import com.erdjbe26.webservice.entity.MstSupplier;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-11T13:51:27+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Eclipse Adoptium)"
)
@Component
public class SupplierMapperImpl implements SupplierMapper {

    @Override
    public SupplierDto toDto(MstSupplier supplier) {
        if ( supplier == null ) {
            return null;
        }

        SupplierDto supplierDto = new SupplierDto();

        supplierDto.setId( supplier.getId() );
        supplierDto.setNamaSupplier( supplier.getNamaSupplier() );
        supplierDto.setAlamatSupplier( supplier.getAlamatSupplier() );

        return supplierDto;
    }

    @Override
    public MstSupplier toEntity(SupplierDto supplierDto) {
        if ( supplierDto == null ) {
            return null;
        }

        MstSupplier mstSupplier = new MstSupplier();

        mstSupplier.setNamaSupplier( supplierDto.getNamaSupplier() );
        mstSupplier.setAlamatSupplier( supplierDto.getAlamatSupplier() );

        return mstSupplier;
    }
}
