package com.company.service;

import com.company.config.details.EntityDetails;
import com.company.dto.AttachSimpleDTO;
import com.company.dto.FilterDTO;
import com.company.dto.request.AdvertRequestDTO;
import com.company.dto.response.AdvertResponseDTO;
import com.company.entity.AdvertAttachEntity;
import com.company.entity.AdvertEntity;
import com.company.entity.AdvertJoinsEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.AppLang;
import com.company.exp.ItemNotFoundException;
import com.company.mapper.AdvertMapper;
import com.company.repository.*;
import com.company.repository.custom.AdvertCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdvertService {
    private final AdvertRepository advertRepository;
    private final ProfileService profileService;
    private final AdvertAttachRepository advertAttachRepository;
    private final AdvertJoinsRepository advertJoinsRepository;
    private final AttachService attachService;
    private final AdvertCustomRepository advertCustomRepository;

    @Transactional
    public AdvertResponseDTO create(AdvertRequestDTO dto) {
        ProfileEntity profileEntity = EntityDetails.getProfile();

        AdvertEntity entity = toEntity(dto);
        advertRepository.save(entity);

        AdvertJoinsEntity joinsEntity = new AdvertJoinsEntity();
        joinsEntity.setAdvertId(entity.getId());
        joinsEntity.setCategoryId(dto.getCategoryId());
        joinsEntity.setMakeId(dto.getMakeId());
        joinsEntity.setProfileId(profileEntity.getId());
        joinsEntity.setCarTypeId(dto.getCarTypeId());
        advertJoinsRepository.save(joinsEntity);

        dto.getAttachIdList().forEach(id -> {
            AdvertAttachEntity advertAttachEntity = new AdvertAttachEntity();
            advertAttachEntity.setAdvertId(entity.getId());
            advertAttachEntity.setAttachId(id);
            advertAttachRepository.save(advertAttachEntity);
        });
        return toDto(entity, joinsEntity, dto.getAttachIdList());
    }


    public AdvertResponseDTO getAdvertById(Long id, AppLang appLanguage) {
        Optional<AdvertMapper> optional = advertRepository.getFullAdvertById(id, true);

        if (optional.isEmpty()) throw new ItemNotFoundException("Advert Not found");

        return toDtoFromMapper(optional.get(), appLanguage);
    }

    @Transactional
    public Boolean delete(Long id) {
        AdvertJoinsEntity entity = advertJoinsRepository.findByAdvertIdAndVisibleTrue(id).
                orElseThrow(() -> {
                    log.warn("Advert not found: {}", id);
                    throw new ItemNotFoundException("Advert not found");
                });
        AdvertAttachEntity advertAttachEntity = advertAttachRepository.findByAdvertIdAndVisibleTrue(id).
                orElseThrow(() -> {
                    log.warn("AdvertAttach not found: {}", id);
                    throw new ItemNotFoundException("AdvertAttach not found");
                });

        //delete advert
        advertRepository.updateVisible(entity.getAdvertId());
        advertJoinsRepository.updateVisible(entity.getId());

        //delete attach
        attachService.delete(advertAttachEntity.getAttachId());
        advertAttachRepository.updateVisible(advertAttachEntity.getId());
        return true;
    }


    public AdvertResponseDTO toDtoFromMapper(AdvertMapper mapper, AppLang appLanguage) {
        AdvertResponseDTO dto = new AdvertResponseDTO();
        dto.setId(mapper.getId());
        dto.setCarTypeId(mapper.getCt_id());
        dto.setMakeId(mapper.getM_id());
        dto.setCategoryId(mapper.getC_id());
        dto.setDescription(mapper.getDescrip());
        dto.setMake(mapper.getMake());
        dto.setProfileId(mapper.getProfileId());
        dto.setWheelDriveType(mapper.getWheelDriveType());
        dto.setValyutaType(mapper.getValyutaType());
        dto.setTransmissionType(mapper.getTransmissionType());
        dto.setTireSize(mapper.getTireSize());
        dto.setRegion(mapper.getRegion());
        dto.setPrice(mapper.getPrice());
        dto.setPaymentType(mapper.getPaymentType());
        dto.setMileage(mapper.getMileage());
        dto.setHorsePower(mapper.getHorsePower());
        dto.setFuelType(mapper.getFuelType());
        dto.setEngineSize(mapper.getEngineSize());
        dto.setContactNumber(mapper.getContactNumber());
        dto.setConditionType(mapper.getConditionType());
        dto.setBodyColor(mapper.getColor());
        dto.setAdvertType(mapper.getAdvertType());
        dto.setCreatedDate(mapper.getDate());

        switch (appLanguage) {
            case uz -> {
                dto.setCategory(mapper.getC_uz());
                dto.setCarType(mapper.getCt_uz());
            }
            case ru -> {
                dto.setCategory(mapper.getC_ru());
                dto.setCarType(mapper.getCt_ru());
            }
            case en -> {
                dto.setCategory(mapper.getC_en());
                dto.setCarType(mapper.getCt_en());
            }
        }
        return dto;
    }

    private AdvertEntity toEntity(AdvertRequestDTO dto) {
        AdvertEntity entity = new AdvertEntity();
        entity.setDescription(dto.getDescription());
        entity.setCarYear(dto.getCarYear());
        entity.setAdvertType(dto.getAdvertType());
        entity.setBodyColor(dto.getBodyColor());
        entity.setConditionType(dto.getConditionType());
        entity.setContactNumber(dto.getContactNumber());
        entity.setEngineSize(dto.getEngineSize());
        entity.setFuelType(dto.getFuelType());
        entity.setHorsePower(dto.getHorsePower());
        entity.setMileage(dto.getMileage());
        entity.setPaymentType(dto.getPaymentType());
        entity.setPrice(dto.getPrice());
        entity.setRegion(dto.getRegion());
        entity.setTireSize(dto.getTireSize());
        entity.setTransmissionType(dto.getTransmissionType());
        entity.setValyutaType(dto.getValyutaType());
        entity.setWheelDriveType(dto.getWheelDriveType());
        return entity;
    }


    public AdvertResponseDTO toDto(AdvertEntity a, AdvertJoinsEntity b, List<String> attachIdList) {
        AdvertResponseDTO dto = new AdvertResponseDTO();
        dto.setId(a.getId());
        dto.setCreatedDate(a.getCreatedDate());
        dto.setDescription(a.getDescription());
        dto.setAdvertType(a.getAdvertType());
        dto.setBodyColor(a.getBodyColor());
        dto.setConditionType(a.getConditionType());
        dto.setContactNumber(a.getContactNumber());
        dto.setEngineSize(a.getEngineSize());
        dto.setFuelType(a.getFuelType());
        dto.setHorsePower(a.getHorsePower());
        dto.setMileage(a.getMileage());
        dto.setPaymentType(a.getPaymentType());
        dto.setPrice(a.getPrice());
        dto.setRegion(a.getRegion());
        dto.setTireSize(a.getTireSize());
        dto.setTransmissionType(a.getTransmissionType());
        dto.setValyutaType(a.getValyutaType());
        dto.setWheelDriveType(a.getWheelDriveType());
        dto.setCategoryId(b.getCategoryId());
        dto.setMakeId(b.getMakeId());
        dto.setProfileId(b.getProfileId());
        dto.setCarTypeId(b.getCarTypeId());
        if (!attachIdList.isEmpty()) {
            List<AttachSimpleDTO> attachList = new ArrayList<>();
            attachIdList.forEach(id -> {
                AttachSimpleDTO simpleDTO = new AttachSimpleDTO();
                simpleDTO.setId(id);
                simpleDTO.setOpenUrl(attachService.toOpenURL(id));
                attachList.add(simpleDTO);
            });
            dto.setAttachList(attachList);
        }
        return dto;
    }


    public List<AdvertResponseDTO> filter(FilterDTO dto, AppLang appLanguage) {
        List<AdvertResponseDTO> dtoList = new LinkedList<>();
        advertCustomRepository.filter(dto).
                forEach(advert -> {
                    dtoList.add(toDtoFromMapper((AdvertMapper) advert, appLanguage));
                });
        return dtoList;
    }

}
