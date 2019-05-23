package service;

import model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.PersonRepository;

@Service("personService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person findById(Long id){
        return personRepository.findById(id);
    }

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public void deleteUserById(Long id){
        personRepository.delete(id);
    }

    public Person updatePerson(Person person){
        return personRepository.save(person);
    }

}
