package com.jonservices.personapi.builder;

import com.jonservices.personapi.data.dto.PhoneDTO;
import com.jonservices.personapi.data.enums.PhoneType;
import lombok.Builder;

@Builder
public class PhoneDTOBuilder {

    @Builder.Default
    private PhoneType type = PhoneType.MOBILE;

    @Builder.Default
    private String number = "(11) 987654321";

    public PhoneDTO toPhoneDTO() {
        return new PhoneDTO(type, number);
    }

    public PhoneDTO aDifferentOne() {
        final PhoneDTO phoneDTO = toPhoneDTO();
        phoneDTO.setType(PhoneType.COMERCIAL);
        phoneDTO.setNumber("(21) 123456789");
        return phoneDTO;
    }
}
