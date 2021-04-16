package loyalty.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.dao.PersonDAO;
import loyalty.entity.Person;
import loyalty.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	PersonDAO personDAO;

	@Override
	public Person savePerson(Person person) {

		person.setId(0);

		try {
			if (!personDAO.existsById(person.getId())) {
				Person save = personDAO.save(person);
				return save;
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		return null;
	}
	
	@Override
	public Person updatePerson(Person person) {
		try {
			if (personDAO.existsById(person.getId())) {
				Person save = personDAO.save(person);
				return save;
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		return null;

	}

	@Override
	public List<Person> getAllPerson() {

		List<Person> persons = new ArrayList<Person>();

		try {
			persons = personDAO.findAllByIsDeletedNot(true);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		return persons;
	}

	@Override
	public List<Person> searchByPhone(String phone) {
		return personDAO.findAllByPhoneStartsWith(phone);
	}

	@Override
	public Optional<Person> findById(Integer id) {
		return personDAO.findById(id);
	}
	
}
