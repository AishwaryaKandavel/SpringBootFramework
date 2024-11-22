package com.springBootFramework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springBootFramework.controllers.Storage;
import com.springBootFramework.repository.StorageRepository;

@Service
public class LibraryServices {
	@Autowired
	StorageRepository repo;
	
	public String buildId(String isbn, int aisle){
		String temp = isbn+aisle;
		if(temp.matches("^[0-9]+.*"))
			temp="$"+temp;
		return temp;
	}
	public boolean checkIfBookIsPresent(String id) {
		return repo.findById(id).isPresent();
	}
	public Storage getBookById(String id) {
		System.out.println(id);
		return repo.findById(id).get();
	}
}
