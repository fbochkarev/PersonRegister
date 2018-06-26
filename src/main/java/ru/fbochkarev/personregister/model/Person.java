package ru.fbochkarev.personregister.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "PERSON")
public class Person {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "PERSON_DATEBIRTH")
    private Date personDateBirth;

    @Column(name = "PERSON_NAME")
    private String personName;

    @Column(name = "PERSON_SURNAME")
    private String personSurname;

    @Column(name = "PERSON_PATRONYMIC")
    private String personPatronymic;

    @Column(name = "PERSON_IDREQUEST")

    private String personIdRequest;

    @Column(name = "PERSON_INN")
    private String personInn;

    public Person(Date personDateBirth, String personName, String personSurname, String personPatronymic) {
        this.personDateBirth = personDateBirth;
        this.personName = personName;
        this.personSurname = personSurname;
        this.personPatronymic = personPatronymic;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPersonDateBirth() {
        return personDateBirth;
    }

    public void setPersonDateBirth(Date personDateBirth) {
        this.personDateBirth = personDateBirth;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonSurname() {
        return personSurname;
    }

    public void setPersonSurname(String personSurname) {
        this.personSurname = personSurname;
    }

    public String getPersonPatronymic() {
        return personPatronymic;
    }

    public void setPersonPatronymic(String personPatronymic) {
        this.personPatronymic = personPatronymic;
    }

    public String getPersonIdRequest() {
        return personIdRequest;
    }

    public String getPersonInn() {
        return personInn;
    }

    public void setPersonIdRequest(String personIdRequest) {
        this.personIdRequest = personIdRequest;
    }

    public void setPersonInn(String personInn) {
        this.personInn = personInn;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", personDateBirth=" + personDateBirth +
                ", personName='" + personName + '\'' +
                ", personSurname='" + personSurname + '\'' +
                ", personPatronymic='" + personPatronymic + '\'' +
                ", personIdRequest='" + personIdRequest + '\'' +
                ", personInn='" + personInn + '\'' +
                '}';
    }
}
