package com.springBootFramework.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springBootFramework.controllers.Storage;

public interface StorageRepository extends JpaRepository<Storage, String> {

}
