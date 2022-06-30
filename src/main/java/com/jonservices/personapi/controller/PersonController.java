package com.jonservices.personapi.controller;

import com.jonservices.personapi.data.dto.MessageResponseDTO;
import com.jonservices.personapi.data.dto.PersonDTO;
import com.jonservices.personapi.docs.PersonControllerDocs;
import com.jonservices.personapi.hateoas.PersonHateoas;
import com.jonservices.personapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/persons")
@CrossOrigin(maxAge = 3600)
public class PersonController implements PersonControllerDocs {

    @Autowired
    private PersonService personService;

    @GetMapping
    public Page<PersonDTO> findAll(Pageable page) {
        final Page<PersonDTO> personsDTO = personService.findAll(page);
        PersonHateoas.addLinkToItself(personsDTO);
        return personsDTO;
    }

    @GetMapping("/{id}")
    public PersonDTO findById(@PathVariable Long id) {
        final PersonDTO personDTO = personService.findById(id);
        PersonHateoas.addLinkToAll(personDTO);
        return personDTO;
    }

    @GetMapping("/search/{name}")
    public PersonDTO findByName(@PathVariable String name) {
        final PersonDTO personDTO = personService.findByName(name);
        PersonHateoas.addLinkToAll(personDTO);
        return personDTO;
    }

    @PostMapping
    public MessageResponseDTO save(@RequestBody @Valid PersonDTO personDTO) {
        return personService.save(personDTO);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO update(@PathVariable Long id, @RequestBody @Valid PersonDTO personDTO) {
        return personService.update(id, personDTO);
    }

    @DeleteMapping("/{id}")
    public MessageResponseDTO delete(@PathVariable Long id) {
        return personService.delete(id);
    }

}
