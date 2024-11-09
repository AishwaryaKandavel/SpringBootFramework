package com.springBootFramework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springBootFramework.repository.StorageRepository;

@RestController
public class AddBookController {
	
	@Autowired
	StorageRepository repo;
	
	@PostMapping("/addbook")
	public ResponseEntity<AddBookResponse> addBook(@RequestBody Storage storage) {
		String id = storage.getIsbn()+storage.getAisle();
		storage.setId(id);
		AddBookResponse addBookResp = new AddBookResponse();
		addBookResp.setId(id);
		HttpHeaders header = new HttpHeaders();
		header.add("unique", id);
		if(repo.findById(id).isEmpty()) {
			repo.save(storage);
			addBookResp.setMsg("Success Book is Added");
			return new ResponseEntity<AddBookResponse>(addBookResp, header, HttpStatus.CREATED);
		}else {
			addBookResp.setMsg("Book already exists");
			return new ResponseEntity<AddBookResponse>(addBookResp, header, HttpStatus.ACCEPTED);
		}
	}
}
