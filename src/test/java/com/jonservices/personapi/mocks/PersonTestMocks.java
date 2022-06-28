package com.jonservices.personapi.mocks;

import com.jonservices.personapi.builder.PersonDTOBuilder;
import com.jonservices.personapi.data.dto.PersonDTO;
import com.jonservices.personapi.data.model.Person;
import com.jonservices.personapi.mapper.PersonMapper;
import com.jonservices.personapi.pagination.PersonPagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

public class PersonTestMocks {
    public static String PERSON_API_URL_PATH = "/persons";
    public static PersonDTO EXPECTED_PERSON_DTO = PersonDTOBuilder.builder().build().toPersonDTO();
    public static PersonDTO ANOTHER_PERSON_DTO = PersonDTOBuilder.builder().build().aDifferentOne();
    public static long VALID_PERSON_ID = EXPECTED_PERSON_DTO.getId();
    public static long INVALID_PERSON_ID = 5L;
    public static String VALID_PERSON_NAME = EXPECTED_PERSON_DTO.getFirstName();
    public static String INVALID_PERSON_NAME = "Yoda";
    public static Pageable DEFAULT_PAGEABLE = PersonPagination.getPage(1, 10, "asc", "id");
    public static Page<PersonDTO> PAGE_PERSON_DTO = new PageImpl<>(Arrays.asList(EXPECTED_PERSON_DTO, ANOTHER_PERSON_DTO), DEFAULT_PAGEABLE, 2);
    public static PersonMapper PERSON_MAPPER = PersonMapper.INSTANCE;
    public static Optional<Person> EXPECTED_OPTIONAL_PERSON = Optional.of(PERSON_MAPPER.toModel(EXPECTED_PERSON_DTO));

}
