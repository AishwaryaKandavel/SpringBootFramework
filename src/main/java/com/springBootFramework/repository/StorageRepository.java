package com.springBootFramework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springBootFramework.controllers.Storage;

public interface StorageRepository extends JpaRepository<Storage, String> {
	public List<Storage> findAllByAuthor(String author);
	public List<Storage> findAllByBook(String book);
	public List<Storage> findAllByIsbn(String isbn);
	public List<Storage> findAllByAisle(int aisle);
}
