package com.jonservices.personapi.builder;

import com.jonservices.personapi.data.dto.PersonDTO;
import com.jonservices.personapi.data.dto.PhoneDTO;
import lombok.Builder;

import java.util.Collections;
import java.util.List;

@Builder
public class PersonDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String firstName = "Luke";

    @Builder.Default
    private String lastName = "Skywalker";

    @Builder.Default
    private String cpf = "784.034.201-96";

    @Builder.Default
    private String birthDate = "20-01-1978";

    @Builder.Default
    private List<PhoneDTO> phones = Collections.singletonList(PhoneDTOBuilder.builder().build().toPhoneDTO());

    public PersonDTO toPersonDTO() {
        return new PersonDTO(id,
                firstName,
                lastName,
                cpf,
                birthDate,
                phones);
    }

    public PersonDTO aDifferentOne() {
        final PersonDTO personDTO = toPersonDTO();
        personDTO.setFirstName("Obi-Wan");
        personDTO.setLastName("Kenobi");
        personDTO.setCpf("171.163.493-08");
        personDTO.setBirthDate("25-01-1978");
        personDTO.setPhones(Collections.singletonList(PhoneDTOBuilder.builder().build().aDifferentOne()));
        return personDTO;
    }

}
