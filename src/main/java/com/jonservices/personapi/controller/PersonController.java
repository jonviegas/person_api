package com.jonservices.personapi.controller;

import com.jonservices.personapi.data.dto.MessageResponseDTO;
import com.jonservices.personapi.data.dto.PersonDTO;
import com.jonservices.personapi.hateoas.PersonHateoas;
import com.jonservices.personapi.pagination.PersonPagination;
import com.jonservices.personapi.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/persons")
@Tag(name = "Person Controller")
@CrossOrigin(maxAge = 3600)
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Finds all registered people")
    public Page<PersonDTO> findAll(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                   @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                   @RequestParam(name = "orderBy", defaultValue = "id") String criteria) {
        final Pageable page = PersonPagination.getPage(pageNumber, direction, criteria);
        final Page<PersonDTO> personsDTO = personService.findAll(page);
        PersonHateoas.addLinkToItself(personsDTO);
        return personsDTO;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Searches for a person record by its id")
    public PersonDTO findById(@PathVariable Long id) {
        final PersonDTO personDTO = personService.findById(id);
        PersonHateoas.addLinkToAll(personDTO);
        return personDTO;
    }

    @GetMapping("/search/{name}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Searches for a person record by its name")
    public PersonDTO findByName(@PathVariable String name) {
        final PersonDTO personDTO = personService.findByName(name);
        PersonHateoas.addLinkToAll(personDTO);
        return personDTO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Saves a new person record in the database")
    public MessageResponseDTO save(@RequestBody @Valid PersonDTO personDTO) {
        return personService.save(personDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates a person record in the database")
    public MessageResponseDTO update(@PathVariable Long id, @RequestBody @Valid PersonDTO personDTO) {
        return personService.update(id, personDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Removes a person record from the database")
    public MessageResponseDTO delete(@PathVariable Long id) {
        return personService.delete(id);
    }

}
