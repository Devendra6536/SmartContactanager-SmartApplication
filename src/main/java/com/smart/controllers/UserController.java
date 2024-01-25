package com.smart.controllers;

import com.smart.contactlog.ContactManagerLogger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.smart.config.CustomUserDetails;
import com.smart.dao.UserRepository;
import com.smart.dao.ContactRepository;
import com.smart.dao.MyorderRepository;
import com.smart.entities.Contact;
import com.smart.entities.MyOrder;
import com.smart.entities.User;
import com.smart.helper.FileSaver;
import com.smart.helper.Message;
import com.smart.helper.SendEmail;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private ContactManagerLogger contactManagerLogger;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private MyorderRepository myorderRepository;

	@Autowired
	private SendEmail sendEmail;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// respone method for userdata
	@ModelAttribute
	public void getUserForAllhandler(Model model, Principal principal) {
		String userName = principal.getName();
		User user = this.userRepository.getUserByUserName(userName);
		System.out.println(user.getUsername() + " you are successfully loged in\n");
		model.addAttribute("user", user);
		System.out.println("userName --->>> " + principal.getName());

	}

	// dashboard home
	@RequestMapping("/index")
	public String dashboard(Model model, @AuthenticationPrincipal CustomUserDetails userDetails, Principal principal,
			HttpSession session) throws IOException {
		// System.out.println("user " +userDetails.getUsername());
		User user = this.userRepository.getUserByUserName(principal.getName());
		// System.out.println("This is fetched user " + user);
		model.addAttribute("title", "Dashboard - smart contact manager");
		session.setAttribute("message", new Message("You are successfully logged in", "alert-success"));

		// Email sent to user
		/*
		 * boolean send_email =
		 * this.sendEmail.send_email("You are Successfully Loged in verify its you"
		 * ,principal.getName()," Login Successfully ");
		 * if(send_email==true) {
		 * System.out.println("Success : email sent successfully");
		 * }
		 * else {
		 * System.err.println("Error : email not sent");
		 * }
		 */

		// write the log
		contactManagerLogger.writeContactManagerlog("User sigin in +-+-+-+-+-+-+-+-+-");
		String log_message_to_write = user.getUsername() + " : You are successfully loged in";
		contactManagerLogger.writeContactManagerlog("\n");
		contactManagerLogger.writeContactManagerlog(log_message_to_write);
		contactManagerLogger.writeContactManagerlog("                        ");

		return "normal/user_dashboard";
	}

	/* this is for open the contact page(contact form) */
	@RequestMapping("/addcontact")
	public String openContactForm(Model model) {
		model.addAttribute("title", "Add contact");
		model.addAttribute("contact", new Contact());
		return "normal/contactform";
	}

	/* this handler for saving the contact into the database */
	@PostMapping("/savecontact")
	public String addContact(@Valid @ModelAttribute Contact contact, BindingResult result, Model model,
			HttpSession session, Principal principal,
			@RequestParam("myfile") MultipartFile file) {

		try {
			FileSaver f = new FileSaver();
			if (file.isEmpty()) {
				System.out.println("file is empty select another file");
				contact.setImage("contact.png");
			} else {
				contact.setImage(file.getOriginalFilename());
				if (f.saveFile(file) == true) {
					System.out.println("image is successfully saved into the database");
				}

			}
			String name = principal.getName();
			User user = userRepository.getUserByUserName(name);
			contact.setUser(user);
			user.getConatcts().add(contact);
			this.userRepository.save(user);
			System.out.println("Contact is saved");
			session.setAttribute("message", new Message("Contact Added successfully", "alert-success"));
			model.addAttribute("contact", new Contact());
			return "normal/contactform";
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("error" + e.getMessage());
			e.printStackTrace();
			model.addAttribute("contact", contact);
			System.out.println("error is occured");
			session.setAttribute("message",
					new Message("Contact is not added ! Something went werong", "alert-danger"));
			return "normal/contactform";
		}

	
	}

	/* Handler for the purpose of viewing all the saved contacts */
	/* Pagination information */
	/*
	 * No of entry per page = 5[n]
	 * current page = page
	 */
	
	/*this use this method for the purpose of*/ 
	
	@RequestMapping("/view-all-contacts")
	public ResponseEntity<List<Contact>> fetchContactList(Principal principal) throws IOException {
		String name = principal.getName();
		User user = userRepository.getUserByUserName(name);
		List<Contact> contacts = this.contactRepository.getAllContacts();
		
		// write the log
		String log_message_to_write = " You are viewing All the contacts related the user FOR FRONT END ANGULAR PURPOSE ";
		contactManagerLogger.writeContactManagerlog(log_message_to_write);
		System.out.println("Logs write successfully");

		return ResponseEntity.of(Optional.of(contacts));
	}

	
	
	
	@RequestMapping("/view-contacts/{page}")
	public String viewContactList(@PathVariable Integer page, Model model, Principal principal)
			throws IOException {
		String name = principal.getName();
		User user = userRepository.getUserByUserName(name);
		org.springframework.data.domain.Pageable pageable = PageRequest.of(page, 10);

		model.addAttribute("title", "User Contacts");

		Page<Contact> contacts = this.contactRepository.findContactsByUser(user.getId(), pageable);
		System.out.println("contacts " + contacts);
		/*
		 * This pageable have three infromation
		 * The current page
		 * no of total page
		 */

		model.addAttribute("contacts", contacts);
		model.addAttribute("currentpage", page);
		model.addAttribute("totalpage", contacts.getTotalPages());

		System.err.println("These are the TOTAL PAGE " + contacts.getTotalPages());

		// write the log
		String log_message_to_write = " You are viewing the contacts related the user ";
		contactManagerLogger.writeContactManagerlog(log_message_to_write);
		System.out.println("Logs write successfully");

		return "normal/contactList";
	}

	@RequestMapping("/gallery")
	public String openGallery(Model model) {
		List<String> jpgImageList = new ArrayList<>();
		for (int i = 1; i < 19; i++) {
			Integer ii = Integer.valueOf(i);
			jpgImageList.add(ii.toString());
		}
		List<String> webpImageList = new ArrayList<>();
		for (int i = 20; i < 39; i++) {
			Integer ii = Integer.valueOf(i);
			webpImageList.add(ii.toString());
		}

		model.addAttribute("jpgImageList", jpgImageList);
		model.addAttribute("webpImageList", webpImageList);
		return "normal/gallery";
	}

	@GetMapping("/{id}/contact")
	public String getContacts(@PathVariable("id") int contactId, Model model, Principal principal) {
		String username = principal.getName();
		User user = this.userRepository.getUserByUserName(username);

		Optional<Contact> contactoptional = this.contactRepository.findById(contactId);
		Contact contact = contactoptional.get();
		model.addAttribute("title", "contact details");

		if (user.getId() == contact.getUser().getId())
			model.addAttribute("contact", contact);
		System.out.println("Contact details here");
		return "normal/contactdetails";
	}

	@GetMapping("/delete-contact/{cid}")
	@Transactional
	public String deleteContact(@PathVariable("cid") int id, Model model, Principal principal, HttpSession session) {
		Contact contact = this.contactRepository.findById(id).get();

		User user = this.userRepository.getUserByUserName(principal.getName());
		user.getConatcts().remove(contact);
		this.userRepository.save(user);
		session.setAttribute("message", new Message("Contact is deleted successfully", "alert-success"));
		System.err.println("contact is successfully deleted");

		// write the log
		String log_message_to_write = " You are Deleting contacts ";
		try {
			contactManagerLogger.writeContactManagerlog(" : INFO : +-+-+-+-+-+-+-+-+-+-+-+-+-+-");
			contactManagerLogger.writeContactManagerlog(log_message_to_write);
			contactManagerLogger.writeContactManagerlog(": INFO : Contact successfully deleted");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/user/view-contacts/0";
	}

	@PostMapping("/updatecontact/{id}")
	public String updateContactDetails(Model model, @PathVariable("id") int contactId, Principal principal) {
		Contact contact = this.contactRepository.findById(contactId).get();

		model.addAttribute("contact", contact);
		model.addAttribute("title", "Update contacts");
		return "normal/updatecontact";
	}

	@PostMapping("/process-update")
	public String updateHandler(@ModelAttribute Contact contact, @RequestParam("profileimage") MultipartFile file,
			Principal principal, HttpSession session) {
		System.out
				.println("Updated the contact" + contact.getCid() + " +++///+++ " + contact.getEmail() + " +++///+++ " +

						contact.getName());

		User user = this.userRepository.getUserByUserName(principal.getName());
		Contact oldcontactdetails = this.contactRepository.findById(contact.getCid()).get();
		try {

			FileSaver f = new FileSaver();

			if (file.isEmpty()) {
				System.out.println("No file is choosen ! The image is not updated");
				contact.setImage(oldcontactdetails.getImage());
				System.out.println(contact.getImage());
			} else {
				// delete the old image
				File deletefile = new ClassPathResource("static/image").getFile();
				File file1 = new File(deletefile, oldcontactdetails.getImage());
				file1.delete();

				contact.setImage(file.getOriginalFilename());
				if (f.saveFile(file) == true) {
					System.out.println("File is successfully updated and saved in the databases");
				}

			}
			contact.setUser(user);
			System.err.println("Current login user" + user.getUsername());
			this.contactRepository.save(contact);
			session.setAttribute("message", new Message("Contact is Updated successfully ", "alert-success"));
		} catch (Exception e) {
			System.out.println("ERROR+++" + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/user/" + contact.getCid() + "/contact";
	}

	/* Handler for the purpose of viewing profile */
	@RequestMapping("/profile")
	public String userProfile(Model model) throws IOException {

		// write the log
		String log_message_to_write = " You are accesing the user profile ";
		contactManagerLogger.writeContactManagerlog(log_message_to_write);

		return "normal/profile";
	}

	@RequestMapping("/faq")
	public String faq(Model model) {
		return "normal/faq";
	}

	@RequestMapping("/open-setting")
	public String openSetting(Model model) {
		model.addAttribute("title", "Smart contactmanager : Setting");
		return "normal/setting";
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam String oldPassword,
			@RequestParam String newPassword, @RequestParam String confirmPassword,
			Principal principal, Model model, HttpSession session) {

		model.addAttribute("title", "Change Password");

		User current_user = this.userRepository.getUserByUserName(principal.getName());
		System.out.println("OLD PASSWD Saved in DB " + current_user.getPasswd());
		System.out.println("OLD PASSWD Entered By user  " + oldPassword);
		System.out.println("NEW PASSWD Entered By user  " + newPassword);

		if (!confirmPassword.equalsIgnoreCase(newPassword)) {
			session.setAttribute("confrimPasswdmessage", "Your password does not match ");
			return "redirect:/user/open-setting";
		}
		if (oldPassword.equalsIgnoreCase(newPassword)) {
			session.setAttribute("samePasswdCase", "You entered same password as old password");
			session.setAttribute("PasswdChangemessage",
					new Message(current_user.getUsername() + " ! You Entered Same password ", ""));
			return "redirect:/user/open-setting";
		}

		if (this.bCryptPasswordEncoder.matches(oldPassword, current_user.getPasswd())) {
			// change the password
			current_user.setPasswd(this.bCryptPasswordEncoder.encode(newPassword));
			this.userRepository.save(current_user);
			System.out.println("Password changed successully");
			session.setAttribute("PasswdChangemessage",
					new Message(current_user.getUsername() + " ! Your Password is successfully changed.... ", ""));
		} else {
			// error......
			System.err.println("Password error\n");
			session.setAttribute("PasswdChangemessage",
					new Message(current_user.getUsername() + " ! Please Enter correct password.... ", ""));
			return "redirect:/user/open-setting";
		}

		return "redirect:/user/index";
	}

	@PostMapping("/create_order")
	@ResponseBody
	public String createOrder(@RequestBody Map<String, Object> data, Principal principal) throws RazorpayException {
		System.err.println("PAYMENT DATA" + data);

		int amount = Integer.parseInt(data.get("amount").toString());
		var client = new RazorpayClient("rzp_test_8pwtBHd42wsqob", "UFiw1qLBI8HamDUsofukDwHa");		
		
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount",amount);
		orderRequest.put("currency","INR");
		orderRequest.put("receipt", "txn_23545");
		
		
		//creating the payment order
		Order order = client.orders.create(orderRequest);
		System.out.println("ORDER " + order);
		
		//you can save this order in the database
		MyOrder myorder = new MyOrder();
		myorder.setAmount(order.get("amount").toString());
		myorder.setReceipt(order.get("receipt"));
		myorder.setMyOrderId(order.get("order_id"));
		myorder.setPaymentId(null);
		myorder.setStatus("created");
		myorder.setUser(this.userRepository.getUserByUserName(principal.getName()));
		
		this.myorderRepository.save(myorder);
		
		
		
		System.err.println("Payment order created");
		return order.toString();
	}


	@GetMapping("/export-contacts")
	public String ExportTheUserContacts(Principal principal) {
		List<Contact> contacts =  contactRepository.findContactsByUserId(userRepository.getUserByUserName(principal.getName()).getId());

			int num_of_row = contacts.size();
			Workbook wb = new HSSFWorkbook();
			Sheet sheet = wb.createSheet("Inspector");			
			Row row =  sheet.createRow(0);
			
			
			row.createCell(0).setCellValue("ID");
			row.createCell(1).setCellValue("Name");
			row.createCell(2).setCellValue("Secondname");
			row.createCell(3).setCellValue("Phone");
			row.createCell(4).setCellValue("Email");
			row.createCell(5).setCellValue("Work");
			row.createCell(6).setCellValue("Description");
			row.createCell(7).setCellValue("Image");
			try{
				for(int i=0;i<num_of_row;i++){
					row = sheet.createRow(i+1);

					row.createCell(0).setCellValue(contacts.get(i).getCid());
					row.createCell(1).setCellValue(contacts.get(i).getName());
					row.createCell(5).setCellValue(contacts.get(i).getSecondname());
					row.createCell(2).setCellValue(contacts.get(i).getPhone());
					row.createCell(3).setCellValue(contacts.get(i).getEmail());
					row.createCell(4).setCellValue(contacts.get(i).getWork());
					row.createCell(6).setCellValue(contacts.get(i).getDescription());
					row.createCell(6).setCellValue(contacts.get(i).getImage());
				}

				FileOutputStream fp = new FileOutputStream("Inspectors.xlsx");
				wb.write(fp);
				fp.close();
				wb.close();
				System.out.println("Inspectors file has been generated successfully.");

			}catch(Exception e){
				System.err.println("ERROR!");
				e.printStackTrace();
			}

			
			return "redirect:/user/view-contacts/0";
	}
			
	
}
