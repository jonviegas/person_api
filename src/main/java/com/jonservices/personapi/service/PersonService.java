package com.jonservices.personapi.service;

import com.jonservices.personapi.data.dto.MessageResponseDTO;
import com.jonservices.personapi.data.dto.PersonDTO;
import com.jonservices.personapi.data.model.Person;
import com.jonservices.personapi.exception.PersonNotFoundException;
import com.jonservices.personapi.mapper.PersonMapper;
import com.jonservices.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonMapper personMapper = PersonMapper.INSTANCE;
    @Autowired
    private PersonRepository personRepository;

    public Page<PersonDTO> findAll(Pageable page) {
        final Page<Person> persons = personRepository.findAll(page);
        return personMapper.toPageDTO(persons);
    }

    public PersonDTO findById(Long id) {
        final Person person = verifyIfExists(id);
        return personMapper.toDTO(person);
    }

    public PersonDTO findByName(String name) {
        final Person person = verifyIfExists(name);
        return personMapper.toDTO(person);
    }

    public MessageResponseDTO save(PersonDTO personDTO) {
        final Person personToSave = personMapper.toModel(personDTO);
        final Person savedPerson = personRepository.save(personToSave);
        final PersonDTO savedPersonDTO = personMapper.toDTO(savedPerson);
        return createMessageResponse(savedPersonDTO.getId(), "Saved person with id ");
    }

    public MessageResponseDTO update(Long id, PersonDTO personDTO) {
        verifyIfExists(id);
        personDTO.setId(id);
        final Person personToBeUpdated = personMapper.toModel(personDTO);
        personRepository.save(personToBeUpdated);
        return createMessageResponse(id, "Updated person with id ");
    }

    public MessageResponseDTO delete(Long id) {
        verifyIfExists(id);
        personRepository.deleteById(id);
        return createMessageResponse(id, "Deleted person with id ");
    }

    private Person verifyIfExists(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person with id " + id + " not found"));
    }

    private Person verifyIfExists(String name) {
        final List<Person> personsList = personRepository.findByName(name);
        if (personsList.isEmpty())
            throw new PersonNotFoundException("Person with name " + name + " not found");
        return personsList.get(0);
    }

    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }

}
