package edu.gcu.cst239.kabwe.elijah.lab5.service;

import java.util.ArrayList;
import java.util.List;
import edu.gcu.cst239.kabwe.elijah.lab5.model.Person;

public class DataService {
    private final List<Person> people;

    public DataService(List<Person> people) {
        this.people = new ArrayList<>(people);
    }

    public static DataService createWithSampleData() {
        return new DataService(List.of(
            new Person(1, "Alice", 25),
            new Person(2, "Bob", 30),
            new Person(3, "Charlie", 35)
        ));
    }

    public List<Person> getPeople() {
        return List.copyOf(people);
    }

    public Person getPerson(int id) {
        return people.stream()
            .filter(p -> p.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public boolean addPerson(Person person) {
        if (getPerson(person.getId()) != null) {
            return false;
        }
        return people.add(person);
    }

    public Person deletePerson(int id) {
        Person person = getPerson(id);
        if (person != null) {
            people.remove(person);
        }
        return person;
    }

    public List<Person> findByName(String nameQuery) {
        return people.stream()
            .filter(p -> p.getName().toLowerCase().contains(nameQuery.toLowerCase()))
            .toList();
    }
}
