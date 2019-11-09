package de.profoethker.backendmodul.model;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "personal_q")
@SequenceGenerator(name = "hibernate_sequence")
public class PQ {
	
	@Id
	@GeneratedValue()
	private Integer id;

}
