package ru.fbochkarev.personregister.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.fbochkarev.personregister.dao.PersonDao;
import ru.fbochkarev.personregister.model.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fbochkarev.personregister.soap.SoapClient;
import ru.fbochkarev.personregister.soap.SoapClientImpl;

import javax.xml.soap.SOAPException;
import java.util.List;
import java.util.Objects;

@Service
public class PersonServiceImpl implements PersonService {
    private PersonDao personDao;

    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    @Transactional
    public void addPerson(Person person) {
        this.personDao.addPerson(person);
    }

    @Override
    @Transactional
    public void updatePerson(Person person) {
        this.personDao.updatePerson(person);
    }

    @Override
    @Transactional
    public void removePerson(int id) {
        this.personDao.removePerson(id);
    }

    @Override
    @Transactional
    public Person getPersonById(int id) {
        return this.personDao.getPersonById(id);
    }

    @Override
    @Transactional
    public List<Person> listPerson() {
        return this.personDao.listPerson();
    }

}
