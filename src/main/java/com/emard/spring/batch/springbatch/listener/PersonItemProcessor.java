package com.emard.spring.batch.springbatch.listener;

import com.emard.spring.batch.springbatch.model.Person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


/**
 * PersonItemProcessor
 */
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Person process(Person person) throws Exception {
        if(person.getAge()==null || person.getEmail()==null|| person.getFirstName()==null || person.getLastName()==null) return null;
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        final Person transfomedPerson = new Person(firstName, lastName, person.getEmail(), person.getAge());

        log.info("Converting [{}] to [{}]", person, transfomedPerson);
        return transfomedPerson;
    }

    
}