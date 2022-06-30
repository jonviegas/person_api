package com.jonservices.personapi.docs;

import com.jonservices.personapi.data.dto.MessageResponseDTO;
import com.jonservices.personapi.data.dto.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@Tag(name = "Person Controller")
public interface PersonControllerDocs {

    @Operation(summary = "Finds all registered people")
    @ApiResponse(responseCode = "200", description = "Returns OK status")
    @ResponseStatus(HttpStatus.OK)
    public Page<PersonDTO> findAll(Pageable page);

    @Operation(summary = "Searches for a person record by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns OK status if the person exists"),
            @ApiResponse(responseCode = "400", description = "Returns BAD REQUEST status when an invalid id format is passed"),
            @ApiResponse(responseCode = "404", description = "Returns NOT FOUND status when the person does not exists")
    })
    @ResponseStatus(HttpStatus.OK)
    public PersonDTO findById(@PathVariable Long id);

    @Operation(summary = "Searches for a person record by its name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns OK status if person exists"),
            @ApiResponse(responseCode = "404", description = "Returns NOT FOUND status when person does not exist")
    })
    @ResponseStatus(HttpStatus.OK)
    public PersonDTO findByName(@PathVariable String name);

    @Operation(summary = "Saves a new person in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns CREATED status when a new person is saved"),
            @ApiResponse(responseCode = "400", description = "Returns BAD REQUEST status when required field is empty or invalid")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO save(@RequestBody @Valid PersonDTO personDTO);

    @Operation(summary = "Updates a person in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns OK status when a person is updated"),
            @ApiResponse(responseCode = "400", description = "Returns BAD REQUEST status when an invalid id format is passed"),
            @ApiResponse(responseCode = "400", description = "Returns BAD REQUEST status when required field is empty or invalid"),
            @ApiResponse(responseCode = "404", description = "Returns NOT FOUND status when the person does not exist")
    })
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDTO update(@PathVariable Long id, @RequestBody @Valid PersonDTO personDTO);

    @Operation(summary = "Removes a person from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns OK status when a person is deleted"),
            @ApiResponse(responseCode = "400", description = "Returns BAD REQUEST status when an invalid id format is passed"),
            @ApiResponse(responseCode = "404", description = "Returns NOT FOUND status when the person does not exists")
    })
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDTO delete(@PathVariable Long id);

}
