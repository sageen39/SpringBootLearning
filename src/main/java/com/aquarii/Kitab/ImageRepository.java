package com.aquarii.Kitab;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 
 * @author Ninja
 *
 */

public interface ImageRepository extends PagingAndSortingRepository<Image, Long>{
	
	public Image findByName(String name);

}
