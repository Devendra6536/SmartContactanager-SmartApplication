package com.smart.dao;


import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.Contact;
import com.smart.entities.User;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
	
	
	@Query(value = "Select * from contacts", nativeQuery = true)
	public List<Contact> getAllContacts();
	
	/* Using the HQL hive query language */
	  @Query("from Contact as c where c.user.id =:userId")
	  public Page<Contact> findContactsByUser(@Param("userId") int userId, Pageable pageable);

	  @Query("from Contact as c where c.user.id =:userId")
	  public List<Contact> findContactsByUserId(@Param("userId") int userId);
	  
	  
	//using native sql
		/*
		 * @Query(value = "Select * from contacts as c where user_id =:userId",
		 * nativeQuery =true) public List<Contact> findContactsByUser(@Param("userId")
		 * int userId);
		 */
	  //this method for searching the contact using the keyword
	  public List<Contact> findByNameContainingAndUser(String keyword,User user);
}
