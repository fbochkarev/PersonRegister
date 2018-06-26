package ru.fbochkarev.personregister.dao;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.fbochkarev.personregister.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.fbochkarev.personregister.soap.SoapClient;
import ru.fbochkarev.personregister.soap.SoapClientImpl;

import javax.xml.soap.SOAPException;
import java.util.List;

@Repository
public class PersonDaoImpl implements PersonDao {
    private static final Logger logger = LoggerFactory.getLogger(PersonDaoImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addPerson(Person person) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(person);
        logger.info("Person successfully saved. Person details: " + person);
    }

    @Override
    public void updatePerson(Person person) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(person);
        logger.info("Person successfully update. Person details: " + person);
    }

    @Override
    public void removePerson(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Person person = (Person) session.load(Person.class, new Integer(id));

        if (person != null) {
            session.delete(person);
        }
        logger.info("Person successfully remove. Person details: " + person);
    }

    @Override
    public Person getPersonById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Person person = (Person) session.load(Person.class, new Integer(id));
        logger.info("Person successfully loaded. Person details: " + person);

        return person;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Person> listPerson() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Person> personList = session.createQuery("from Person").list();

        for(Person person: personList){
            logger.info("Person list: " + person);
        }

        return personList;
    }
}
