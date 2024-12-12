package com.springBootFramework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

 @GetMapping("/home")
 public String homePage(Model model) {
     return "home"; // Returns the name of the Thymeleaf template (home.html)
 }

 @GetMapping("/get-book")
 public String getBookPage(Model model) {
     return "get-book"; // Template for fetching book details
 }

 @GetMapping("/update-book")
 public String updateBookPage(Model model) {
     return "update-book"; // Template for updating book details
 }

 @GetMapping("/delete-book")
 public String deleteBookPage(Model model) {
     return "delete-book"; // Template for deleting book details
 }
}
