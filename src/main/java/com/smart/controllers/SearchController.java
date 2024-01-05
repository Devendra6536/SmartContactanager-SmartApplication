package com.smart.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;

@RestController
public class SearchController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContactRepository contactRepository;

	// search controller
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable String query, Principal principal) {
		System.out.println("QUERY ==> " + query);
		// this is we fetched the user who is currently loged in
		User user = this.userRepository.getUserByUserName(principal.getName());
		// search the contact
		List<Contact> contacts = this.contactRepository.findByNameContainingAndUser(query, user);

		return ResponseEntity.ok(contacts);
	}
}
