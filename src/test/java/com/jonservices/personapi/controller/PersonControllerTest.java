package com.jonservices.personapi.controller;

import com.jonservices.personapi.builder.PersonDTOBuilder;
import com.jonservices.personapi.data.dto.MessageResponseDTO;
import com.jonservices.personapi.data.dto.PersonDTO;
import com.jonservices.personapi.exception.PersonNotFoundException;
import com.jonservices.personapi.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static com.jonservices.personapi.utils.JSONConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.jonservices.personapi.utils.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    void setupEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test // GET Find All
    @DisplayName("When GET is called then all persons and ok status is returned")
    void whenGETIsCalledThenAllPersonsAndOkStatusIsReturned() throws Exception {
        // when
        when(personService.findAll(DEFAULT_PAGEABLE)).thenReturn(PAGE_PERSON_DTO);

        // then
        mockMvc.perform(get(PERSON_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(PAGE_PERSON_DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName", is(EXPECTED_PERSON_DTO.getFirstName())))
                .andExpect(jsonPath("$.content[0].lastName", is(EXPECTED_PERSON_DTO.getLastName())))
                .andExpect(jsonPath("$.content[1].firstName", is(ANOTHER_PERSON_DTO.getFirstName())))
                .andExpect(jsonPath("$.content[1].lastName", is(ANOTHER_PERSON_DTO.getLastName())));
    }

    @Test // GET Find by Name
    @DisplayName("When GET is called with valid name then ok status is returned")
    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
        // when
        when(personService.findByName(VALID_PERSON_NAME)).thenReturn(EXPECTED_PERSON_DTO);

        // then
        mockMvc.perform(get(PERSON_API_URL_PATH + "/search/" + VALID_PERSON_NAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(EXPECTED_PERSON_DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(EXPECTED_PERSON_DTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(EXPECTED_PERSON_DTO.getLastName())))
                .andExpect(jsonPath("$.cpf", is(EXPECTED_PERSON_DTO.getCpf())));
    }


    @Test // GET Find by id
    @DisplayName("When GET is called with valid id then ok status is returned")
    void whenGETIsCalledWithValidIdThenOkStatusIsReturned() throws Exception {
        // when
        when(personService.findById(VALID_PERSON_ID)).thenReturn(EXPECTED_PERSON_DTO);

        // then
        mockMvc.perform(get(PERSON_API_URL_PATH + "/" + VALID_PERSON_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(EXPECTED_PERSON_DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(EXPECTED_PERSON_DTO.getFirstName())));
    }

    @Test // POST Save Person
    @DisplayName("When POST is called then it should create person")
    void whenPOSTIsCalledThenItShouldCreatePerson() throws Exception {
        // given
        final MessageResponseDTO expectedMessageResponseDTO = MessageResponseDTO
                .builder().message("Saved person with id " + EXPECTED_PERSON_DTO.getId()).build();

        // when
        when(personService.save(EXPECTED_PERSON_DTO)).thenReturn(expectedMessageResponseDTO);

        // then
        mockMvc.perform(post(PERSON_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(EXPECTED_PERSON_DTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(expectedMessageResponseDTO.getMessage())));
    }

    @Test // PUT Update Person by id
    @DisplayName("When PUT is called then it should update person")
    void whenPUTIsCalledThenItShouldUpdatePerson() throws Exception {
        // given
        final MessageResponseDTO expectedMessageResponseDTO = MessageResponseDTO
                .builder().message("Updated person with id " + VALID_PERSON_ID).build();

        final PersonDTO updatedPersonDTO = PersonDTOBuilder.builder().build().toPersonDTO();
        updatedPersonDTO.setFirstName("Anakin");

        // when
        when(personService.update(VALID_PERSON_ID, updatedPersonDTO)).thenReturn(expectedMessageResponseDTO);

        // then
        mockMvc.perform(put(PERSON_API_URL_PATH + "/" + VALID_PERSON_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedPersonDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponseDTO.getMessage())));
    }

    @Test // DELETE Delete by id
    @DisplayName("When DELETE is called then it should delete person")
    void whenPOSTIsCalledThenItShouldDeletePerson() throws Exception {
        // given
        final MessageResponseDTO expectedMessageResponseDTO = MessageResponseDTO
                .builder().message("Deleted person with id " + VALID_PERSON_ID).build();

        // when
        when(personService.delete(VALID_PERSON_ID)).thenReturn(expectedMessageResponseDTO);

        // then
        mockMvc.perform(delete(PERSON_API_URL_PATH + "/" + VALID_PERSON_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponseDTO.getMessage())));
    }

    @Test // GET Find by id PersonNotFoundException
    @DisplayName("When GET is called with an invalid id then it should thrown an error")
    void whenGETIsCalledWithAnInvalidIdThenItShouldThrownAnError() throws Exception {
        // when
        doThrow(PersonNotFoundException.class).when(personService).findById(INVALID_PERSON_ID);

        // then
        mockMvc.perform(get(PERSON_API_URL_PATH + "/" + INVALID_PERSON_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test // GET Find by Name PersonNotFoundException
    @DisplayName("When GET is called with an invalid name then it should thrown an error")
    void whenGETIsCalledWithAnInvalidNameThenItShouldThrownAnError() throws Exception {
        // when
        doThrow(PersonNotFoundException.class).when(personService).findByName(INVALID_PERSON_NAME);

        // then
        mockMvc.perform(get(PERSON_API_URL_PATH + "/search/" + INVALID_PERSON_NAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test // POST Save Person Exception
    @DisplayName("When POST is called without required field then it should thrown an error")
    void whenPOSTIsCalledWithoutRequiredFieldThenItShouldThrownAnError() throws Exception {
        // when
        final PersonDTO personDTO = PersonDTOBuilder.builder().build().toPersonDTO();
        personDTO.setLastName(null);

        // then
        mockMvc.perform(post(PERSON_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(personDTO)))
                .andExpect(status().isBadRequest());
    }
}