package com.aquarii.Kitab;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 
 * @author Ninja
 *
 */
@Entity
public class Image {
	
	@Id @GeneratedValue
	private Long id;

	private String name;

	public Image() {
	}
	
	public Image(String name) {
		this.name = name;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
