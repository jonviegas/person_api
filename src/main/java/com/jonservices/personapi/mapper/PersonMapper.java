package com.jonservices.personapi.mapper;

import com.jonservices.personapi.data.dto.PersonDTO;
import com.jonservices.personapi.data.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "birthDate", source = "birthDate", dateFormat = "dd-MM-yyyy")
    Person toModel(PersonDTO personDTO);

    PersonDTO toDTO(Person person);

    default Page<PersonDTO> toPageDTO(Page<Person> persons) {
        return persons.map(this::toDTO);
    }

}
