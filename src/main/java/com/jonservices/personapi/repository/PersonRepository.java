package com.jonservices.personapi.repository;

import com.jonservices.personapi.data.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {

    @Query(value = "SELECT p FROM Person p WHERE LOWER(CONCAT (p.firstName, p.lastName)) LIKE LOWER(CONCAT ('%', :name, '%'))")
    List<Person> findByName(@Param("name") String name);
}
