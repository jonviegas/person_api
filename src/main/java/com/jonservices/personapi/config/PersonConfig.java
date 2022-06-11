package com.jonservices.personapi.config;

import com.jonservices.personapi.data.enums.PhoneType;
import com.jonservices.personapi.data.model.Person;
import com.jonservices.personapi.data.model.Phone;
import com.jonservices.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Configuration
public class PersonConfig implements CommandLineRunner {

    @Autowired
    private PersonRepository repository;

    @Override
    public void run(String... args) throws Exception {
        final Phone phone1 = new Phone(null, PhoneType.MOBILE, "(11) 987654321");
        final Person person1 = new Person(null, "Luke", "Skywalker", "784.034.201-96", LocalDate.ofYearDay(1978, 20), List.of(phone1));
        final Phone phone2 = new Phone(null, PhoneType.COMERCIAL, "(21) 123456789");
        final Person person2 = new Person(null, "Obi-Wan", "Kenobi", "171.163.493-08", LocalDate.ofYearDay(1978, 25), List.of(phone2));
        repository.saveAll(Arrays.asList(person1, person2));
    }

}
