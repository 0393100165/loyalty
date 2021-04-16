package loyalty.service;

import java.util.List;

import loyalty.entity.Gift;

public interface GiftService {

	List<Gift> fetchAll();
	
	Gift randomAndSave(String giftCode);
	
	
}
