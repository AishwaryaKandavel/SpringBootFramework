package com.springBootFramework;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import com.springBootFramework.controllers.Storage;
import com.springBootFramework.repository.StorageRepository;

@SpringBootApplication
public class Application/* implements CommandLineRunner */{
	
	@Autowired
	StorageRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/*
	 * @Override public void run(String... args) throws Exception { Storage s =
	 * repo.findById("38746gb").get(); System.out.println(s.getAuthor()); Storage s1
	 * = new Storage(); s1.setBook_name("Mockito"); s1.setId("6734bhb");
	 * s1.setIsbn("62873bjvs"); s1.setAisle(2); s1.setAuthor("Aish"); repo.save(s1);
	 * List<Storage> list = repo.findAll(); for (Storage storage : list) {
	 * System.out.println(storage.getBook_name());
	 * System.out.println(storage.getAisle());
	 * System.out.println(storage.getAuthor()); System.out.println(storage.getId());
	 * System.out.println(storage.getIsbn()); } repo.delete(s1);
	 * System.out.println("After deletion"); list = repo.findAll(); for (Storage
	 * storage : list) { System.out.println(storage.getBook_name());
	 * System.out.println(storage.getAisle());
	 * System.out.println(storage.getAuthor()); System.out.println(storage.getId());
	 * System.out.println(storage.getIsbn()); } }
	 */

}
