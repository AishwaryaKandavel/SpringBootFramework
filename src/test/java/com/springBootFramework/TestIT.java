package com.springBootFramework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.springBootFramework.controllers.Storage;

@SpringBootTest
public class TestIT {

	private int aisle = 12;
	private String author = "Ela";
	private String isbn = "hjbsdc";
	private String book = "Appium";
	private String id = "hjbsdc12";

	@Test
	public void getBooksAuthorTest() throws JSONException {
		String expected = "[\r\n" + "    {\r\n" + "        \"book\": \"SAP\",\r\n" + "        \"id\": \"bjbd7435\",\r\n"
				+ "        \"isbn\": \"3764\",\r\n" + "        \"aisle\": 2,\r\n" + "        \"author\": \"Aish\"\r\n"
				+ "    }\r\n" + "]";
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> resp = restTemplate.getForEntity("http://localhost:8081/getbook?author=Aish",
				String.class);
		assertEquals(HttpStatus.ACCEPTED, resp.getStatusCode());
		JSONAssert.assertEquals(expected, resp.getBody(), false);
	}

	@Test
	public void addBook() throws JSONException {
		String expected = "{\r\n" + "    \"id\": \"hjbsdc12\",\r\n" + "    \"msg\": \"Success Book is Added\"\r\n"
				+ "}";
		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Storage> resquest = new HttpEntity<Storage>(storageObjectCreation(aisle, author, book, isbn, id),
				headers);
		ResponseEntity<String> resp = restTemplate.postForEntity("http://localhost:8081/addbook", resquest,
				String.class);
		assertEquals(HttpStatus.CREATED, resp.getStatusCode());
		JSONAssert.assertEquals(expected, resp.getBody(), false);
	}

	Storage storageObjectCreation(int aisle, String author, String book, String Isbn, String Id) {
		Storage s = new Storage();
		s.setAisle(aisle);
		s.setAuthor(author);
		s.setBook(book);
		s.setIsbn(Isbn);
		s.setId(Id);
		return s;
	}
}
