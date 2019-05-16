package dao;

import model.Person;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class PersonDao extends GenericDao<Person> {


}
