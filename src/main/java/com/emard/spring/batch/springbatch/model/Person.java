package com.emard.spring.batch.springbatch.model;

import java.io.Serializable;

public class Person implements Serializable{
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;


    public Person() {
    }

    public Person(String firstName, String lastName, String email, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Person firstName(String firstName) {
        setFirstName(firstName);
        return this;
    }

    public Person lastName(String lastName) {
        setLastName(lastName);
        return this;
    }

    public Person email(String email) {
        setEmail(email);
        return this;
    }

    public Person age(Integer age) {
        setAge(age);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", age='" + getAge() + "'" +
            "}";
    }
    

}
