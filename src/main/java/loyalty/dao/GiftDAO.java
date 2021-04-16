package loyalty.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import loyalty.entity.Gift;

@Repository
public interface GiftDAO extends JpaRepository<Gift, Integer>{

	Boolean existsByGiftCode(String giftCode);
	
}
