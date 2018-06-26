package ru.fbochkarev.personregister.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.fbochkarev.personregister.model.Person;
import ru.fbochkarev.personregister.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.fbochkarev.personregister.soap.SoapClient;
import ru.fbochkarev.personregister.soap.SoapClientImpl;

import javax.xml.soap.SOAPException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.time.LocalDateTime;

@Controller
public class PersonController {
    private PersonService personService;

    @Autowired(required = true)
    @Qualifier(value = "personService")
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "persons", method = RequestMethod.GET)
    public String listPerson(Model model) {
        model.addAttribute("person", new Person());
        model.addAttribute("listPersons", this.personService.listPerson());

        return "persons";
    }

//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//        sdf.setLenient(true);
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
//    }

    @RequestMapping(value = "/persons/add", method = RequestMethod.POST )
    public String addPerson(@ModelAttribute("person") Person person) {
        if (person.getId() == 0) {
            this.personService.addPerson(person);
        } else {
            this.personService.updatePerson(person);
        }
        return "redirect:/persons";
    }

    @RequestMapping("/remove/{id}")
    public String removePerson(@PathVariable("id") int id) {
        this.personService.removePerson(id);

        return "redirect:/persons";
    }

    @RequestMapping("edit/{id}")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", this.personService.getPersonById(id));
        model.addAttribute("listPersons", this.personService.listPerson());

        return "persons";
    }

    @RequestMapping("persondata/{id}")
    public String personData(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", this.personService.getPersonById(id));
        return "persondata";
    }

    @RequestMapping("inn/{id}")
    public String personInn(@PathVariable("id") int id, Model model) throws SOAPException, ParseException, InterruptedException {
        Person person = this.personService.getPersonById(id);
        SoapClient soapClient = new SoapClientImpl();

        String query = soapClient.queryINNFLFIODR(
                person.getPersonDateBirth(),
                person.getPersonName(),
                person.getPersonSurname(),
                person.getPersonPatronymic());

        person.setPersonIdRequest(query);
        this.personService.updatePerson(person);

//        for (int i = 1; i < 20; i++) {
//            System.out.println(i);
//            TimeUnit.SECONDS.sleep(1);
//        }

        String inn = soapClient.getINNFLFIODR(query);
        model.addAttribute("inn", inn);
        model.addAttribute("listPersons", this.personService.listPerson());
        model.addAttribute("person", new Person());
        model.addAttribute("id", id);

//        person.setPersonInn(inn);
//        this.personService.updatePerson(person);
        return "persons";
    }



}
