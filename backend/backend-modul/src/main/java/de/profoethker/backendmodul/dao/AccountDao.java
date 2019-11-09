package de.profoethker.backendmodul.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import de.profoethker.backendmodul.model.Account;

@Repository
public interface AccountDao extends JpaRepository<Account, Integer> {

	@Modifying
	Account save(Account data);
	
	

}
