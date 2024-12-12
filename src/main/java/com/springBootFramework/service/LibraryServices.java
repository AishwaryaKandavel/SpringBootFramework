package com.springBootFramework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.springBootFramework.controllers.Response;
import com.springBootFramework.controllers.Storage;
import com.springBootFramework.repository.StorageRepository;

@Service
public class LibraryServices {
	@Autowired
	StorageRepository repo;
	private final RestTemplate restTemplate;
	
	public LibraryServices(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${backend.api.url}")
    private String apiUrl; // e.g., http://localhost:8080
	
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

    public String addBook(Storage book) {
        ResponseEntity<Response> response = restTemplate.postForEntity(apiUrl + "/addbook", book, Response.class);
        return response.getBody().getMsg();
    }

    public List<Storage> searchBooks(String author, String isbn, String book, Integer aisle) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl + "/getbook")
                .queryParam("author", author)
                .queryParam("isbn", isbn)
                .queryParam("book", book)
                .queryParam("aisle", aisle);

        ResponseEntity<List<Storage>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Storage>>() {}
        );
        return response.getBody();
    }
}
