package com.springBootFramework;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springBootFramework.controllers.LibraryController;
//import com.springBootFramework.controllers.Response;
import com.springBootFramework.controllers.Storage;
import com.springBootFramework.repository.StorageRepository;
import com.springBootFramework.service.LibraryServices;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

	@Autowired
	LibraryController con;

	@MockBean
	LibraryServices services;
	@MockBean
	StorageRepository repo;
	@Autowired
	MockMvc mock;

	private int aisle = 12;
	private String author = "Ela";
	private String isbn = "hjbsdc";
	private String book = "Appium";
	private String id = "hjbsdc12";

	@Test
	void checkBuildIdValid() throws BadRequestException {
		LibraryServices services = new LibraryServices();
		String id = services.buildId("1", 123);
		assertEquals(id, "$1123");
		id = services.buildId("jgskb", 364);
		assertEquals(id, "jgskb364");
	}

	/**@Test
	void addBookTest() {
		Storage storage = storageObjectCreation();
		when(services.buildId(storage.getIsbn(), storage.getAisle())).thenReturn(storage.getId());
		when(services.checkIfBookIsPresent(storage.getId())).thenReturn(false);
		when(repo.save(any())).thenReturn(storage);
		ResponseEntity<Response> resp = con.addBook(storage);
		assertEquals(resp.getStatusCode(), HttpStatus.CREATED);
		Response resBody = resp.getBody();
		assertEquals(resBody.getId(), storage.getId());
		assertEquals(resBody.getMsg(), "Success Book is Added");

		when(services.buildId(storage.getIsbn(), storage.getAisle())).thenReturn(storage.getId());
		when(services.checkIfBookIsPresent(storage.getId())).thenReturn(true);
		when(repo.save(any())).thenReturn(storage);
		resp = con.addBook(storage);
		assertEquals(resp.getStatusCode(), HttpStatus.ACCEPTED);
		resBody = resp.getBody();
		assertEquals(resBody.getId(), storage.getId());
		assertEquals(resBody.getMsg(), "Book already exists");
	}**/

	@Test
	void addBookNewTestUsingMockMVC() throws Exception {
		Storage storage = storageObjectCreation(aisle, author, book, isbn, id);
		ObjectMapper map = new ObjectMapper();
		String value = map.writeValueAsString(storage);

		when(services.buildId(storage.getIsbn(), storage.getAisle())).thenReturn(storage.getId());
		when(services.checkIfBookIsPresent(storage.getId())).thenReturn(false);
		when(repo.save(any())).thenReturn(storage);
		this.mock.perform(post("/addbook").contentType(MediaType.APPLICATION_JSON).content(value)).andDo(print())
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(storage.getId()))
				.andExpect(jsonPath("$.msg").value("Success Book is Added"));
	}

	@Test
	void addBookExistingTestUsingMockMVC() throws Exception {
		Storage storage = storageObjectCreation(aisle, author, book, isbn, id);
		ObjectMapper map = new ObjectMapper();
		String value = map.writeValueAsString(storage);

		when(services.buildId(storage.getIsbn(), storage.getAisle())).thenReturn(storage.getId());
		when(services.checkIfBookIsPresent(storage.getId())).thenReturn(true);
		when(repo.save(any())).thenReturn(storage);
		this.mock.perform(post("/addbook").contentType(MediaType.APPLICATION_JSON).content(value)).andDo(print())
				.andExpect(status().isAccepted()).andExpect(jsonPath("$.id").value(storage.getId()))
				.andExpect(jsonPath("$.msg").value("Book already exists"));
	}

	@Test
	void addBookNewNullTestUsingMockMVC() throws Exception {
		Storage storage = storageObjectCreation(-1, null, null, null, null);
		ObjectMapper map = new ObjectMapper();
		String value = map.writeValueAsString(storage);

		this.mock.perform(post("/addbook").contentType(MediaType.APPLICATION_JSON).content(value)).andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	void addBookBadRequestTestUsingMockMVC() throws Exception {
		this.mock.perform(post("/addbook").contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	void getBookByAuthorTestUsingMockMVC() throws Exception {
		List<Storage> store = new ArrayList<Storage>();
		store.add(storageObjectCreation(aisle, author, book, isbn, id));
		store.add(storageObjectCreation(aisle, author, book, isbn, id));

		for (int i = 0; i < store.size(); i++) {
			when(repo.findAllByAuthor(any())).thenReturn(store);
			this.mock.perform(get("/getbook").param("author", "Ela")).andDo(print()).andExpect(status().isAccepted())
					.andExpect(jsonPath("$.length()").value(2))
					.andExpect(jsonPath("$[" + i + "].author").value(author));
		}
	}

	@Test
	void getBookByISBNTestUsingMockMVC() throws Exception {
		List<Storage> store = new ArrayList<Storage>();
		store.add(storageObjectCreation(aisle, author, book, isbn, id));
		store.add(storageObjectCreation(aisle, author, book, isbn, id));

		for (int i = 0; i < store.size(); i++) {
			when(repo.findAllByIsbn(any())).thenReturn(store);
			this.mock.perform(get("/getbook").param("isbn", "bkdhf")).andDo(print()).andExpect(status().isAccepted())
					.andExpect(jsonPath("$.length()").value(2)).andExpect(jsonPath("$[" + i + "].isbn").value(isbn));
		}
	}

	@Test
	void getBookByAisleTestUsingMockMVC() throws Exception {
		List<Storage> store = new ArrayList<Storage>();
		store.add(storageObjectCreation(aisle, author, book, isbn, id));
		store.add(storageObjectCreation(aisle, author, book, isbn, id));

		for (int i = 0; i < store.size(); i++) {

			when(repo.findAllByAisle(anyInt())).thenReturn(store);
			this.mock.perform(get("/getbook").param("aisle", "12")).andDo(print()).andExpect(status().isAccepted())
					.andExpect(jsonPath("$.length()").value(2)).andExpect(jsonPath("$[" + i + "].aisle").value(aisle));
		}
	}

	@Test
	void getBookByBookTestUsingMockMVC() throws Exception {
		List<Storage> store = new ArrayList<Storage>();
		store.add(storageObjectCreation(aisle, author, book, isbn, id));
		store.add(storageObjectCreation(aisle, author, book, isbn, id));

		for (int i = 0; i < store.size(); i++) {

			when(repo.findAllByBook(any())).thenReturn(store);
			this.mock.perform(get("/getbook").param("book", "Appium")).andDo(print()).andExpect(status().isAccepted())
					.andExpect(jsonPath("$.length()").value(2)).andExpect(jsonPath("$[" + i + "].book").value(book));
		}
	}

	@Test
	void getBookBadRequestTestUsingMockMVC() throws Exception {
		this.mock.perform(get("/getbook/bkhbsd566")).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	void getBookByID() throws Exception {
		Optional<Storage> existingStorage = Optional.of(storageObjectCreation(aisle, author, book, isbn, id));
		when(repo.findById(any())).thenReturn(existingStorage);
		MockHttpServletResponse val = this.mock
				.perform(get("/getbook/" + existingStorage.get().getId())).andDo(print())
				.andExpect(status().isAccepted()).andReturn().getResponse();
		ObjectMapper obj = new ObjectMapper();
		String value = obj.writeValueAsString(existingStorage.get());
		System.out.println(value);
		System.out.println(val.getContentAsString());
		assertEquals(value, val.getContentAsString());
	}

	@Test
	void getBookByNoID() throws Exception {
		this.mock.perform(get("/getbook")).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	void updateBookByID() throws Exception {
		Storage existingStorage = storageObjectCreation(aisle, author, book, isbn, id);
		Storage updateStorage = storageObjectCreation(aisle, "Aish", "Mockito", isbn, id);
		ObjectMapper obj = new ObjectMapper();
		String value = obj.writeValueAsString(updateStorage);
		when(services.getBookById(any())).thenReturn(existingStorage);
		when(repo.save(any())).thenReturn(updateStorage);
		MockHttpServletResponse val = this.mock.perform(
				put("/updatebook/" + existingStorage.getId()).contentType(MediaType.APPLICATION_JSON).content(value))
				.andDo(print()).andExpect(status().isCreated()).andReturn().getResponse();
		assertEquals(value, val.getContentAsString());
	}

	@Test
	void updateBookByUnknownID() throws Exception {
		this.mock.perform(put("/updatebook/bkhbsd566")).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	void updateBookByNoID() throws Exception {
		this.mock.perform(put("/updatebook")).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	void deleteBookByID() throws Exception {
		Storage existingStorage = storageObjectCreation(aisle, author, book, isbn, id);
		when(services.getBookById(any())).thenReturn(existingStorage);
		doNothing().when(repo).delete(existingStorage);		
		this.mock.perform(delete("/deletebook/" + existingStorage.getId())).andDo(print())
				.andExpect(status().isAccepted()).andReturn().getResponse();
	}

	@Test
	void deleteBookByUnknownID() throws Exception {
		this.mock.perform(delete("/deletebook/bkhbsd566")).andDo(print()).andExpect(status().isAccepted());
	}

	@Test
	void deleteBookByNoID() throws Exception {
		this.mock.perform(delete("/deletebook")).andDo(print()).andExpect(status().isNotFound());
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
