package com.erdjbe26.webservice.service.impl;

import com.erdjbe26.webservice.dto.MenuDto;
import com.erdjbe26.webservice.entity.MstGroupMenu;
import com.erdjbe26.webservice.entity.MstMenu;
import com.erdjbe26.webservice.exception.ResourceNotFoundException;
import com.erdjbe26.webservice.mapper.MenuMapper;
import com.erdjbe26.webservice.repository.MstGroupMenuRepository;
import com.erdjbe26.webservice.repository.MstMenuRepository;
import com.erdjbe26.webservice.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    private final MstMenuRepository mstMenuRepository;
    private final MstGroupMenuRepository mstGroupMenuRepository;
    private final MenuMapper menuMapper;

    public MenuServiceImpl(MstMenuRepository mstMenuRepository, MstGroupMenuRepository mstGroupMenuRepository, MenuMapper menuMapper) {
        this.mstMenuRepository = mstMenuRepository;
        this.mstGroupMenuRepository = mstGroupMenuRepository;
        this.menuMapper = menuMapper;
    }

    @Override
    @Transactional
    public MenuDto createMenu(MenuDto menuDto) {
        MstMenu menu = menuMapper.toEntity(menuDto);
        if (menuDto.getIdGroupMenu() != null) {
            MstGroupMenu groupMenu = mstGroupMenuRepository.findById(menuDto.getIdGroupMenu())
                    .orElseThrow(() -> new ResourceNotFoundException("Group Menu not found with id: " + menuDto.getIdGroupMenu()));
            menu.setGroupMenu(groupMenu);
        }
        menu.setCreatedBy(1L); // System user for now
        menu.setCreatedDate(LocalDateTime.now());
        MstMenu savedMenu = mstMenuRepository.save(menu);
        return menuMapper.toDto(savedMenu);
    }

    @Override
    public MenuDto getMenuById(Long id) {
        MstMenu menu = mstMenuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + id));
        return menuMapper.toDto(menu);
    }

    @Override
    public List<MenuDto> getAllMenus() {
        return mstMenuRepository.findAll().stream()
                .map(menuMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MenuDto updateMenu(Long id, MenuDto menuDto) {
        MstMenu existingMenu = mstMenuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + id));

        existingMenu.setNama(menuDto.getNama());
        existingMenu.setPath(menuDto.getPath());
        existingMenu.setDeskripsi(menuDto.getDeskripsi());

        if (menuDto.getIdGroupMenu() != null) {
            MstGroupMenu groupMenu = mstGroupMenuRepository.findById(menuDto.getIdGroupMenu())
                    .orElseThrow(() -> new ResourceNotFoundException("Group Menu not found with id: " + menuDto.getIdGroupMenu()));
            existingMenu.setGroupMenu(groupMenu);
        } else {
            existingMenu.setGroupMenu(null);
        }

        existingMenu.setModifiedBy(1L); // System user for now
        existingMenu.setModifiedDate(LocalDateTime.now());

        MstMenu updatedMenu = mstMenuRepository.save(existingMenu);
        return menuMapper.toDto(updatedMenu);
    }

    @Override
    @Transactional
    public void deleteMenu(Long id) {
        if (!mstMenuRepository.existsById(id)) {
            throw new ResourceNotFoundException("Menu not found with id: " + id);
        }
        mstMenuRepository.deleteById(id);
    }
}
