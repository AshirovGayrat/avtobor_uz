package com.company.service;

import com.company.config.details.EntityDetails;
import com.company.dto.AttachSimpleDTO;
import com.company.dto.request.AdvertRequestDTO;
import com.company.dto.response.AdvertResponseDTO;
import com.company.entity.AdvertAttachEntity;
import com.company.entity.AdvertEntity;
import com.company.entity.AdvertJoinsEntity;
import com.company.entity.ProfileEntity;
import com.company.repository.AdvertAttachRepository;
import com.company.repository.AdvertJoinsRepository;
import com.company.repository.AdvertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdvertService {
    private final AdvertRepository advertRepository;
    private final ProfileService profileService;
    private final AdvertAttachRepository advertAttachRepository;
    private final AdvertJoinsRepository advertJoinsRepository;
    private final AttachService attachService;

    @Transactional
    public AdvertResponseDTO create(AdvertRequestDTO dto) {
        ProfileEntity profileEntity= EntityDetails.getProfile();

        AdvertEntity entity = toEntity(dto);
        advertRepository.save(entity);

        AdvertJoinsEntity joinsEntity=new AdvertJoinsEntity();
        joinsEntity.setAdvertId(entity.getId());
        joinsEntity.setCategoryId(dto.getCategoryId());
        joinsEntity.setMakeId(dto.getMakeId());
        joinsEntity.setProfileId(profileEntity.getId());
        joinsEntity.setCarTypeId(dto.getCarTypeId());
        advertJoinsRepository.save(joinsEntity);

        dto.getAttachIdList().forEach(id->{
            AdvertAttachEntity advertAttachEntity=new AdvertAttachEntity();
            advertAttachEntity.setAdvertId(entity.getId());
            advertAttachEntity.setAttachId(id);
            advertAttachRepository.save(advertAttachEntity);
        });
        return toDto(entity, joinsEntity, dto.getAttachIdList());
    }

    private AdvertEntity toEntity(AdvertRequestDTO dto) {
        AdvertEntity entity=new AdvertEntity();
        entity.setDescription(dto.getDescription());
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
            List<AttachSimpleDTO> attachList=new ArrayList<>();
            attachIdList.forEach(id -> {
                AttachSimpleDTO simpleDTO=new AttachSimpleDTO();
                simpleDTO.setId(id);
                simpleDTO.setOpenUrl(attachService.toOpenURL(id));
                attachList.add(simpleDTO);
            });
            dto.setAttachList(attachList);
        }
        return dto;
    }
}
