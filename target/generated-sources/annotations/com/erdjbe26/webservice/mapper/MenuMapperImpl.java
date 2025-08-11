package com.erdjbe26.webservice.mapper;

import com.erdjbe26.webservice.dto.MenuDto;
import com.erdjbe26.webservice.entity.MstGroupMenu;
import com.erdjbe26.webservice.entity.MstMenu;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-11T13:59:20+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Eclipse Adoptium)"
)
@Component
public class MenuMapperImpl implements MenuMapper {

    @Override
    public MenuDto toDto(MstMenu menu) {
        if ( menu == null ) {
            return null;
        }

        MenuDto menuDto = new MenuDto();

        menuDto.setIdGroupMenu( menuGroupMenuId( menu ) );
        menuDto.setNamaGroupMenu( menuGroupMenuNama( menu ) );
        menuDto.setId( menu.getId() );
        menuDto.setNama( menu.getNama() );
        menuDto.setPath( menu.getPath() );
        menuDto.setDeskripsi( menu.getDeskripsi() );

        return menuDto;
    }

    @Override
    public MstMenu toEntity(MenuDto menuDto) {
        if ( menuDto == null ) {
            return null;
        }

        MstMenu mstMenu = new MstMenu();

        mstMenu.setNama( menuDto.getNama() );
        mstMenu.setPath( menuDto.getPath() );
        mstMenu.setDeskripsi( menuDto.getDeskripsi() );

        return mstMenu;
    }

    private Long menuGroupMenuId(MstMenu mstMenu) {
        if ( mstMenu == null ) {
            return null;
        }
        MstGroupMenu groupMenu = mstMenu.getGroupMenu();
        if ( groupMenu == null ) {
            return null;
        }
        Long id = groupMenu.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String menuGroupMenuNama(MstMenu mstMenu) {
        if ( mstMenu == null ) {
            return null;
        }
        MstGroupMenu groupMenu = mstMenu.getGroupMenu();
        if ( groupMenu == null ) {
            return null;
        }
        String nama = groupMenu.getNama();
        if ( nama == null ) {
            return null;
        }
        return nama;
    }
}
