package de.profoethker.backendmodul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.profoethker.backendmodul.dao.AccountDao;
import de.profoethker.backendmodul.model.Account;

@RestController
public class AccountController {

	private final AccountDao accountDao;

	@Autowired
	public AccountController(AccountDao accountdao) {
		this.accountDao = accountdao;
	}
	
	@PostMapping("/api/register")
	@CrossOrigin
	public boolean register(@RequestBody Account account) {
		// TODO
		return true;
	}

}
