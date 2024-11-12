package com.springBootFramework.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springBootFramework.repository.StorageRepository;
import com.springBootFramework.service.LibraryServices;

@RestController
public class LibraryController {
	
	@Autowired
	StorageRepository repo;
	
	@Autowired
	LibraryServices services;
	
	private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);
	
	@PostMapping("/addbook")
	public ResponseEntity<Response> addBook(@RequestBody Storage storage) {
		String id = services.buildId(storage.getIsbn(),storage.getAisle());
		storage.setId(id);
		Response addBookResp = new Response();
		addBookResp.setId(id);
		HttpHeaders header = new HttpHeaders();
		header.add("unique", id);
		if(!services.checkIfBookIsPresent(id)) {
			repo.save(storage);
			addBookResp.setMsg("Success Book is Added");
			logger.info("Book does not exist and Book is created");
			return new ResponseEntity<Response>(addBookResp, header, HttpStatus.CREATED);
		}else {
			addBookResp.setMsg("Book already exists");
			logger.info("Book already exists and creation is skipped");
			return new ResponseEntity<Response>(addBookResp, header, HttpStatus.ACCEPTED);
		}
	}
	
	@GetMapping("/getbook/{id}")
	public ResponseEntity<?> getBookById(@PathVariable(value="id")String id) {
		
		try {
			Storage s = repo.findById(id).get();
			logger.info("Book exists");
			return new ResponseEntity<Storage>(s, HttpStatus.ACCEPTED);
		}catch(NoSuchElementException e) {
			Response resp = new Response();
			logger.info("Book does not exist");
			resp.setMsg("Book does not exist");
			return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("/getbook")
	public ResponseEntity<?> getBookByAuthor(@RequestParam(value="author", required=false) String author, 
			@RequestParam(value="isbn", required=false) String isbn, 
			@RequestParam(value="book", required=false) String book, 
			@RequestParam(value="aisle", required=false) Integer aisle) {
		List<Storage> s = null;
		try {
			if(author!=null)
				s = repo.findAllByAuthor(author);
			else if (isbn!=null)
				s = repo.findAllByIsbn(isbn);
			else if (book!=null)
				s = repo.findAllByBook(book);
			else if (aisle!=null)
				s = repo.findAllByAisle(aisle);
			else
				throw new Exception();
			logger.info("Book exists");
			return new ResponseEntity<List<Storage>>(s, HttpStatus.ACCEPTED);
		}catch(NoSuchElementException e) {
			Response resp = new Response();
			logger.info("Book/isbn/author/aisle does not exist");
			resp.setMsg("Book/isbn/author/aisle does not exist");
			return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			Response resp = new Response();
			logger.info("Book/isbn/author/aisle values are empty or check your request");
			resp.setMsg("Bad request");
			return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/updatebook/{id}")
	public ResponseEntity<?> updateBook(@PathVariable(value="id")String id, @RequestBody Storage s) {
		Response resp = new Response();
		try {
			Storage storage = repo.findById(id).get();
			storage.setAuthor(s.getAuthor());
			storage.setBook(s.getBook());
			repo.save(storage);
			logger.info("Book updated successfully");
			return new ResponseEntity<Storage>(storage, HttpStatus.CREATED);
		} catch (NoSuchElementException e) {
			logger.info("Book does not exist");
			resp.setMsg("Book does not exist");
			return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/deletebook/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable(value="id")String id) {
		Response resp = new Response();
		try {
			Storage storage = repo.findById(id).get();
			repo.delete(storage);
			logger.info("Book deleted successfully");
			resp.setMsg("Book "+id+" is deleted successfully");
		} catch (NoSuchElementException e) {
			logger.info("Book does not exist");
			resp.setMsg("Book does not exist");
		}
		return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
	}
}