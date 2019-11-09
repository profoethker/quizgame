package de.profoethker.backendmodul.controller;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import de.profoethker.backendmodul.cybersecurity.Hash512;
import de.profoethker.backendmodul.dao.AccountDao;
import de.profoethker.backendmodul.model.Account;

@RestController
public class AccountController {

	private final AccountDao accountDao;

	Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	public AccountController(AccountDao accountdao) {
		this.accountDao = accountdao;
	}

	@PostMapping("/api/register")
	@CrossOrigin
	public boolean register(@RequestBody Account account) {
		try {
			Account createAccount = new Account();
			createAccount.setUsername(account.getUsername());
			createAccount.setEmail(account.getEmail());
			// Cipher to encrypt password
			createAccount.setPassword(Hash512.encryptData(account.getPassword()));
			accountDao.save(createAccount);
			logger.info("Account Stored success!");
			return true;
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			e.printStackTrace();
			return false;
		}
	}

	@GetMapping(value = "/api/account/all", produces = "aplication/json")
	@CrossOrigin
	public String getAllAccounts() {
		List<Account> accountsAsList = accountDao.findAll();
		for (int i = 0; i < accountsAsList.size(); i++) {
			accountsAsList.get(i).setPassword(null);
		}
		Gson gson = new Gson();
		String json = gson.toJson(accountsAsList);
		return json;

	}

}
