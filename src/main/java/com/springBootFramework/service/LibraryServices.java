package com.springBootFramework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springBootFramework.repository.StorageRepository;

@Service
public class LibraryServices {
	@Autowired
	StorageRepository repo;
	
	public String buildId(String isbn, int aisle) {
		return isbn+aisle;
	}
	public boolean checkIfBookIsPresent(String id) {
		return repo.findById(id).isPresent();
	}
}
