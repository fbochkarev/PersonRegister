package ru.fbochkarev.personregister.dao;

import ru.fbochkarev.personregister.model.Person;

import java.util.List;

public interface PersonDao {
    public void addPerson(Person person);

    public void updatePerson(Person person);

    public void removePerson(int id);

    public Person getPersonById(int id);

    public List<Person> listPerson();
}
