# :family: Person API
- - -

## :book: Introduction
This **REST API** provides a small system for managing people, offering the functionality to register a person by their first name, last name, CPF (Brazilian individual registry identification), date of birth, and personal phone numbers. In addition, it is also possible to query a record or list all records according to a sorting criterion.

The **API** was developed in *Java* with *Spring Boot*.

- - -
## :cloud: Cloud

The project is also hosted on *Heroku*, [click here](https://person-system-api.herokuapp.com/persons/) to access it.
- - -
## :green_book: Documentation

[Click here](https://person-system-api.herokuapp.com/swagger-ui.html) to access the complete documentation made with *swagger*.
- - -
## :computer: Usage

- #### GET
    ```/persons```
    Finds all registered people.
    \
    ```/persons?sort=firstName```
    Returns all records ordered by name.
    \
    ```/persons?sort=birthDate```
    Returns all records ordered by birthdate.
    \
    ```/persons/{id}```
    Returns a person, if exists, by its id.
    \
    ```/persons/search/{name}```
    Returns a person, if exists, by its name.

- #### POST
    ```/persons```
    Allows you to register a new Person.
    \
    \
    **The request body requires the following properties**: *firstName, lastName, cpf, birthDate and phones(one or more).*
    \
    **Example:**
    ``` JSON
    {
        "firstName": "Elvis",
        "lastName": "Presley",
        "cpf": "123.456.789-00",
        "birthDate": "01-01-1930",
        "phones": [
            {
                "type": "MOBILE",
                "number": "(11)987654321"
            }
        ]
    }
    ```

- #### PUT
     ```/persons/{id}```
    Allows you to update a record, if exists, by its id.
    \
    \
    **The request body requires the following properties**: *id, firstName, lastName, cpf, birthDate and phones(one or more).*
    \
    **Example:**
    ``` JSON
    {
        "firstName": "Elvis",
        "lastName": "Presley",
        "cpf": "123.456.789-00",
        "birthDate": "01-01-1935",
        "phones": [
            {
                "type": "MOBILE",
                "number": "(11)987654321"
            }
        ]
    }
    ```

- #### DELETE
    ```/persons/{id}```
    Removes a person, if exists, by its id.
