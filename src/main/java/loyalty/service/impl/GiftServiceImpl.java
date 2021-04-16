package loyalty.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.dao.GiftDAO;
import loyalty.dao.PersonDAO;
import loyalty.entity.Gift;
import loyalty.entity.Person;
import loyalty.service.GiftService;

@Service
public class GiftServiceImpl implements GiftService {
	
	private static final Random random = new Random();

	@Autowired
	private GiftDAO giftDao;

	@Autowired
	private PersonDAO personDao;
	
	@Override
	public List<Gift> fetchAll() {
		List<Gift> all = giftDao.findAll();
		return all;
	}

	/**
	 * @param String giftCode
	 *	return new Gift with person win the pize
	 */
	@Override
	public Gift randomAndSave(String giftCode) {
		List<Person> listPerson = personDao.findAllByIdNotInGift();
		
		listPerson.forEach(System.out::println);
		
		if(!listPerson.isEmpty() && !giftDao.existsByGiftCode(giftCode)) {
			Person p = listPerson.get(listPerson.size() == 1 ? 0 : random.nextInt(listPerson.size() - 1));
			Gift gift = new Gift(giftCode, p);
			Gift save = giftDao.save(gift);
			return save;
		}
		return null;
	}

}
