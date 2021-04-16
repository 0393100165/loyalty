package loyalty.service;

import java.util.List;
import java.util.Optional;

import loyalty.entity.Person;

public interface PersonService {

	Person savePerson(Person person);
	
	Person updatePerson(Person person);
	
	Optional<Person> findById(Integer id);

	List<Person> getAllPerson();
	
	List<Person> searchByPhone(String phone);
}
