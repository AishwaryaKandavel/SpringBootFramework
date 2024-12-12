package com.springBootFramework.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class ThymeLeafController {

	@GetMapping("/")
	public String home() {
		return "home"; // Maps to home.html
	}

	@GetMapping("/add-book")
	public String showAddBookPage() {
		return "add-book"; // Maps to add-book.html
	}

	@GetMapping("/get-book")
	public String showGetBookPage() {
		return "get-book"; // Maps to get-book.html
	}

	@GetMapping("/update-book")
	public String showUpdateBookPage() {
		return "update-book"; // Maps to update-book.html
	}

	@GetMapping("/delete-book")
	public String showDeleteBookPage() {
		return "delete-book"; // Maps to delete-book.html
	}

}
