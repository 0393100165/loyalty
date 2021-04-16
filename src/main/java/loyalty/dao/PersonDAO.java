package loyalty.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import loyalty.entity.Person;

@Repository
public interface PersonDAO extends JpaRepository<Person, Integer> {
	
	List<Person> findAllByIsDeletedNot(Boolean status);

	List<Person> findAllByPhoneStartsWith(String phone);

	@Query(value = "SELECT * FROM person where id NOT IN (select person_id from user_gift)", nativeQuery = true)
	List<Person> findAllByIdNotInGift();
	
	Optional<Person> findPersonById(Integer id);


}
