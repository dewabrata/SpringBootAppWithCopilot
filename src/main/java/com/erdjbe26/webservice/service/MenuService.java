package com.erdjbe26.webservice.service;

import com.erdjbe26.webservice.dto.MenuDto;
import java.util.List;

public interface MenuService {
    MenuDto createMenu(MenuDto menuDto);
    MenuDto getMenuById(Long id);
    List<MenuDto> getAllMenus();
    MenuDto updateMenu(Long id, MenuDto menuDto);
    void deleteMenu(Long id);
}
