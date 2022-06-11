package com.jonservices.personapi.service;

import com.jonservices.personapi.builder.PersonDTOBuilder;
import com.jonservices.personapi.data.dto.MessageResponseDTO;
import com.jonservices.personapi.data.dto.PersonDTO;
import com.jonservices.personapi.data.model.Person;
import com.jonservices.personapi.exception.PersonNotFoundException;
import com.jonservices.personapi.pagination.PersonPagination;
import com.jonservices.personapi.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static com.jonservices.personapi.utils.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private PersonService personService;

    @Test // Find All
    @DisplayName("Should return all registered persons")
    void shouldReturnAllRegisteredPersons() {
        // given
        final Pageable page = PersonPagination.getPage(1, "asc", "id");
        final Page<Person> personsPage = new PageImpl<>(Arrays.asList(PERSON_MAPPER.toModel(EXPECTED_PERSON_DTO),
                PERSON_MAPPER.toModel(ANOTHER_PERSON_DTO)), page, 2);
        final Page<PersonDTO> expectedPersonsDTOPage = personsPage.map(PERSON_MAPPER::toDTO);

        // when
        when(personRepository.findAll(page)).thenReturn(personsPage);
        final Page<PersonDTO> returnedPersonsDTOPage = personService.findAll(page);

        // then
        assertThat(returnedPersonsDTOPage).isNotEmpty();
        assertThat(returnedPersonsDTOPage).isEqualTo(expectedPersonsDTOPage);
    }

    @Test // Find by id
    @DisplayName("When registered person searched by its id then it should be returned")
    void whenRegisteredPersonSearchedByItsIdThenItShouldBeReturned() {
        // when
        when(personRepository.findById(VALID_PERSON_ID)).thenReturn(EXPECTED_OPTIONAL_PERSON);
        final PersonDTO returnedPersonDTO = personService.findById(VALID_PERSON_ID);

        // then
        assertThat(returnedPersonDTO).isEqualTo(EXPECTED_PERSON_DTO);
    }

    @Test // Find by name
    @DisplayName("When registered person searched by its name then it should be returned")
    void whenRegisteredPersonSearchedByItsNameThenItShouldBeReturned() {
        // given
        final List<Person> expectedPersonList = Collections.singletonList(PERSON_MAPPER.toModel(EXPECTED_PERSON_DTO));
        final String personName = EXPECTED_PERSON_DTO.getFirstName();

        // when
        when(personRepository.findByName(personName)).thenReturn(expectedPersonList);
        final PersonDTO returnedPersonDTO = personService.findByName(personName);

        // then
        assertThat(returnedPersonDTO).isEqualTo(EXPECTED_PERSON_DTO);
    }

    @Test // Save Person
    @DisplayName("When person informed then it should be created")
    void whenPersonInformedThenItShouldBeCreated() {
        // given
        final Person expectedSavedPerson = PERSON_MAPPER.toModel(EXPECTED_PERSON_DTO);
        final MessageResponseDTO expectedMessageResponseDTO = MessageResponseDTO
                .builder().message("Saved person with id " + EXPECTED_PERSON_DTO.getId()).build();

        // when
        when(personRepository.save(expectedSavedPerson)).thenReturn(expectedSavedPerson);
        final MessageResponseDTO returnedMessageResponseDTO = personService.save(EXPECTED_PERSON_DTO);

        // then
        assertThat(expectedMessageResponseDTO).isEqualTo(returnedMessageResponseDTO);
    }

    @Test // Update Person
    @DisplayName("When person informed then it should be updated")
    void whenPersonInformedThenItShouldBeUpdated() {
        // given
        final PersonDTO expectedUpdatedPersonDTO = PersonDTOBuilder.builder().build().toPersonDTO();
        expectedUpdatedPersonDTO.setFirstName("Anakin");
        final Person expectedUpdatedPerson = PERSON_MAPPER.toModel(expectedUpdatedPersonDTO);
        final MessageResponseDTO expectedMessageResponseDTO = MessageResponseDTO
                .builder().message("Updated person with id " + EXPECTED_PERSON_DTO.getId()).build();

        // when
        when(personRepository.findById(VALID_PERSON_ID)).thenReturn(EXPECTED_OPTIONAL_PERSON);
        when(personRepository.save(expectedUpdatedPerson)).thenReturn(expectedUpdatedPerson);
        final MessageResponseDTO returnedMessageResponseDTO = personService.update(VALID_PERSON_ID, expectedUpdatedPersonDTO);

        // then
        assertThat(expectedMessageResponseDTO).isEqualTo(returnedMessageResponseDTO);
    }

    @Test // Delete Person
    @DisplayName("Should delete person by its id")
    void shouldDeletePersonByItsId() {
        // given
        final MessageResponseDTO expectedMessageResponseDTO = MessageResponseDTO
                .builder().message("Deleted person with id " + EXPECTED_PERSON_DTO.getId()).build();

        // when
        when(personRepository.findById(VALID_PERSON_ID)).thenReturn(EXPECTED_OPTIONAL_PERSON);
        final MessageResponseDTO returnedMessageResponseDTO = personService.delete(VALID_PERSON_ID);

        // then
        assertThat(expectedMessageResponseDTO).isEqualTo(returnedMessageResponseDTO);
    }

    @Test
    @DisplayName("When invalid id informed then it should thrown an exception")
    void whenInvalidIdInformedThenItShouldThrownAnException() {
        // when
        when(personRepository.findById(INVALID_PERSON_ID)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> personService.findById(INVALID_PERSON_ID))
                .isInstanceOf(PersonNotFoundException.class)
                .hasMessageContaining("Person with id %s not found", INVALID_PERSON_ID);
    }

    @Test
    @DisplayName("When invalid name informed then it should thrown an exception")
    void whenInvalidNameInformedThenItShouldThrownAnException() {
        // when
        when(personRepository.findByName(INVALID_PERSON_NAME)).thenReturn(new ArrayList<>());

        // then
        assertThatThrownBy(() -> personService.findByName(INVALID_PERSON_NAME))
                .isInstanceOf(PersonNotFoundException.class)
                .hasMessageContaining("Person with name %s not found", INVALID_PERSON_NAME);
    }
}
