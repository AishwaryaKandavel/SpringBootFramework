package com.springBootFramework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AddBookUIController {
    
    @Autowired
    LibraryController libraryController;

    @GetMapping("/add-book")
    public String showAddBookForm() {
        return "add-book"; // This maps to add-book.html
    }

    @PostMapping("/addbook-ui")
    public String handleAddBook(@RequestParam String isbn, 
                                 @RequestParam Integer aisle, 
                                 @RequestParam String author, 
                                 @RequestParam String book, 
                                 Model model) {
        // Create Storage object from the form data
        Storage newBook = new Storage();
        newBook.setIsbn(isbn);
        newBook.setAisle(aisle);
        newBook.setAuthor(author);
        newBook.setBook(book);

        try {
            // Call the REST API to add the book
        	
            ResponseEntity<Response> response = libraryController.addBook(newBook);
            String message = "Book ID: "+response.getBody().getId();
            System.out.println(message);
            model.addAttribute("message1", response.getBody().getMsg());
            model.addAttribute("message2", message);
            System.out.println(model.asMap());
        } catch (Exception e) {
            model.addAttribute("message1", "Error while adding book: " + e.getMessage());
        }

        return "add-book"; // Return to the same page with the message
    }
}
