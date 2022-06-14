package com.jonservices.personapi.hateoas;

import com.jonservices.personapi.controller.PersonController;
import com.jonservices.personapi.data.dto.PersonDTO;
import org.springframework.data.domain.Page;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class PersonHateoas {

    public static void addLinkToItself(Page<PersonDTO> personsDTO) {
        for (PersonDTO personDTO : personsDTO)
            personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getId())).withSelfRel());
    }

    public static void addLinkToAll(PersonDTO personDTO) {
        personDTO.add(linkTo(methodOn(PersonController.class).findAll(1, 10, "asc", "id")).withRel("Persons list"));
    }

}
