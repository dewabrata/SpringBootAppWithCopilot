package com.erdjbe26.webservice.service;

import com.erdjbe26.webservice.dto.AksesDto;
import java.util.List;

public interface AksesService {
    AksesDto createAkses(AksesDto aksesDto);
    AksesDto getAksesById(Long id);
    List<AksesDto> getAllAkses();
    AksesDto updateAkses(Long id, AksesDto aksesDto);
    void deleteAkses(Long id);
}
