package de.profoethker.backendmodul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.profoethker.backendmodul.dao.QADao;

@Service
public class QAService {

	@Autowired
	private QADao qaDao;
	
	
}
