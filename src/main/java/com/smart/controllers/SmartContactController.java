package com.smart.controllers;

import jakarta.validation.Valid;


import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.contactlog.ContactManagerLogger;
import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.helper.SendEmail;

import jakarta.servlet.http.HttpSession;

@Controller
public class SmartContactController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private SendEmail sendEmail;

	@Autowired
	private ContactManagerLogger contactManagerLogger;

	@RequestMapping("/")
	public String landingPage(Model model) throws IOException {
		model.addAttribute("title", "Home - smart contact manager");

		// writing the logs into the files
		String log_message_to_write = " You are accessing the Home page";
		contactManagerLogger.writeContactManagerlog("");
		contactManagerLogger.writeContactManagerlog("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
		contactManagerLogger.writeContactManagerlog(log_message_to_write);
		contactManagerLogger.writeContactManagerlog("");
		


		return "home";
	}

	@RequestMapping("/home")
	public String home(Model model) {
		model.addAttribute("title", "Home - smart contact manager");
		return "home";
	}



	@RequestMapping("/about")
	public String about(Model model) throws IOException {
		model.addAttribute("title", "About - smart contact manager");

		// writing the logs into the files
		String log_message_to_write = " You are accessing the about page";
		contactManagerLogger.writeContactManagerlog(log_message_to_write);
		return "about";
	}

	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "signup - smart contact manager");
		model.addAttribute("user", new User());
		return "signup";
	}

	@PostMapping("/do_register")
	public String doRegister(@Valid @ModelAttribute User user, BindingResult results,
			@RequestParam(defaultValue = "false") boolean agreement, Model model,
			HttpSession session) {

		try {

			if (!agreement) {
				System.out.println("You have not agreed the term and conditions");
				throw new Exception("You have not agreed term and conditions");
			}

			if (results.hasErrors()) {
				System.out.println("error is occured  ---->>>> " + results.toString());
				model.addAttribute("user", user);
				return "signup";
			}
			System.out.println("user " + user);
			System.out.println("agreement " + agreement);

			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageurl("default.png");
			user.setPasswd(passwordEncoder.encode(user.getPasswd()));

			User result = this.userRepository.save(user);
			System.out.println(result);
			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Successfully registered !!", "alert-success"));

			// Email sent to user
			  boolean send_email = this.sendEmail.send_email("You are successfully registered!",
			  user.getEmail(), " Registration Successfully "); if (send_email == true) {
			  System.out.println("Success : email sent successfully"); } else {
			  System.err.println("Error : email not sent"); }
			 

			contactManagerLogger
					.writeContactManagerlog("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
			String log_message_to_write = user.getUsername() + " : " + user.getEmail() + " : "
					+ " You are Successfully sign Up ";
			contactManagerLogger.writeContactManagerlog(log_message_to_write);
			contactManagerLogger.writeContactManagerlog("\n");
			
			//saving the user into the csv file
			contactManagerLogger.writeRegisteredUserToCSVfile(user.getUsername(), user.getEmail());
			

			return "signup";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong " + e.getMessage(), " alert-danger"));
			return "signup";
		}
	}

	@GetMapping("/signin")
	public String signIn(Model model) {
		model.addAttribute("title", "signin - smart contact manager");
		model.addAttribute("user", new User());
		return "signin";
	}

	@RequestMapping("/logout")
	public String logout(Model model) throws IOException {

		contactManagerLogger
				.writeContactManagerlog("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
		String log_message_to_write = " You are logout";
		contactManagerLogger.writeContactManagerlog(log_message_to_write);
		contactManagerLogger.writeContactManagerlog("\n");

		return "about";
	}

	@RequestMapping("/forgot-password")
	public String forgotPassword() {
		System.err.println("Password recovering");
		return "forgotpassword";
	}

	@PostMapping("/forgot-password/send-otp")
	public String forgotPasswordSendOTP(@RequestParam String email, Model model, HttpSession session) {

		System.out.println("EMAIL " + email);
		model.addAttribute("ttile", "verify OTP");
		try {

			Random random = new Random();
			int min = 1000, max = 9999;
			int OTP = random.nextInt(max - min + 1) + min;
			System.out.println("OTP sent to user " + OTP);

			String message = "<div style='border:2px solid #e2e2e2; padding:20px; color:blue;' class='text-center'>"
					+ "<h2 class='text-center'>OTP is valid for 5 minutes "
					+ "<b>"
					+ OTP
					+ "</b>"
					+ " </h2>"
					+ "</div>"
					+ "<div style='margin-top:150px; padding:20px; color:blue;' >"
					+ "<h2>Smartcontactmanager : A steps towards cloud</h2>"
					+ "<p>We provide the cloud storage for saving the contacts and information of the personal one."
					+ "<br>Our services is end to end towards the cloud. Any user can <br>access the information through the mobile"
					+ "</div>"
					+ "<div class='mt-5 text-center' style='padding:20px; color:blue;' >"
					+ "<a href=\"http://localhost:8080/home\">SCM-SmartContactManager</a>"
					+ "<p class='text-center primary' >Thanks for choosing SmartContactManager</p>"
					+ "<p class='text-center primary' >7845124578</p>"
					+ "<p>Was this email helpful <b style='font-size:25px;'>&#128512;  &#128513;  &#128514;  &#128515;  &#128516; &#128517;</b></p>"
					+ "</div>";

			User userByUserName = this.userRepository.getUserByUserName(email);
			if (userByUserName == null) {
				session.setAttribute("message", " User does not exists please check your email");
				return "forgotpassword";
			}
			boolean flag = sendEmail.send_email(message, email, "OTP verification");
			if (flag) {
				session.setAttribute("OTP", OTP);
				session.setAttribute("email", email);
				return "verify_OTP";
			} else {
				return "forgotpassword";
			}

		} catch (Exception e) {
			System.out.println("Error");
		}

		// writing the logs into the files
		String log_message_to_write = userRepository.getUserByUserName(email) + " You are recovering the password ";
		try {
			contactManagerLogger.writeContactManagerlog("");
			contactManagerLogger
					.writeContactManagerlog("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
			contactManagerLogger.writeContactManagerlog(log_message_to_write);
			contactManagerLogger
					.writeContactManagerlog("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHhhhhhhhhhhhhhhhh");
			contactManagerLogger.writeContactManagerlog("");
			System.out.println("\n\n");
		} catch (IOException e) {

			e.printStackTrace();
		}

		return "";
	}

	@PostMapping("/forgot-password/verify_otp")
	public String verify_otp(@RequestParam("otp") String textotp, HttpSession session) {

		// first check for empty string - if otp string is empty then warn and return
		// the same page
		if (session.getAttribute("OTP") == null) {
			session.setAttribute("message",
					new Message("You Session has expired generate the OTP again", "alert-danger"));
			return "verify_OTP";
		}
		if (textotp.length() == 0) {
			session.setAttribute("message",
					new Message("You have't entered the otp please enter OTP", "alert-danger"));
			return "verify_OTP";
		}

		int otp = Integer.parseInt(textotp);
		int otpSetInSession = (int) session.getAttribute("OTP");
		System.out.println("form otp " + otp + " otpsetsession " + otpSetInSession);

		if (otpSetInSession == otp) {
			session.setAttribute("message", new Message("Your OTP is verified", "alert-success"));

			// writing the logs into the files
			String log_message_to_write = " You are verifying the OTP ";
			try {
				contactManagerLogger.writeContactManagerlog("");

				contactManagerLogger
						.writeContactManagerlog("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
				contactManagerLogger.writeContactManagerlog(log_message_to_write);
				contactManagerLogger.writeContactManagerlog("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
				contactManagerLogger.writeContactManagerlog("");
				System.out.println("\n\n");
			} catch (IOException e) {

				e.printStackTrace();
			}
			return "changepassword";
		} else {
			session.setAttribute("message",
					new Message("You entered wrong otp please enter the valid OTP", "alert-danger"));
			return "verify_OTP";
		}
	}

	@PostMapping("/changepassword")
	public String changePassword(Principal principal, @RequestParam String password,
			@RequestParam String cpassword, HttpSession session) throws IOException {

		User user = this.userRepository.getUserByUserName((String) session.getAttribute("email"));
		if (password.equals(cpassword)) {
			user.setPasswd(passwordEncoder.encode(password));
			this.userRepository.save(user);
			System.out.println("Your password is successfully recoverd");
			session.setAttribute("module", "Forgot password?");
			session.setAttribute("PasswdChangemessage",
					user.getUsername() + "! Your password is successfully recovered");

			// writing the logs into the files
			String log_message_to_write = " You are changing the Password";
			contactManagerLogger.writeContactManagerlog("");
			contactManagerLogger
					.writeContactManagerlog("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
			contactManagerLogger.writeContactManagerlog(log_message_to_write);
			contactManagerLogger.writeContactManagerlog("");
			System.out.println("\n\n");

			return "signin";

		} else {
			session.setAttribute("message",
					new Message("Your password does not match please fill correctly", "alert-danger"));
			return "changepassword";
		}

	}

	@RequestMapping("/Support")
	public String supportController(@RequestParam String name, @RequestParam String email,
			@RequestParam String phone,
			@RequestParam String address, @RequestParam String description,
			HttpSession session, Principal principal) throws IOException {

		// User user = this.userRepository.getUserByUserName(principal.getName());
		session.setAttribute("module", "Support ?");
		session.setAttribute("PasswdChangemessage", name + " - Thank You ! we have recieved your query");

		// writing the logs into the files
		String log_message_to_write = name + "," + email + "," + phone + "," + address;
		System.err.println("QUERY \"" + log_message_to_write + "\"");
		contactManagerLogger.writeSupportQueriesToCSVfile(log_message_to_write);
		System.out.println(" your query is successfully saved\n\n");

		// writing access log
		contactManagerLogger.writeContactManagerlog("\n");
		contactManagerLogger.writeContactManagerlog(" You are submitting the support queries");
		contactManagerLogger.writeContactManagerlog("your query is successfully saved");

		return "home";
	}
	
	
	
	
	@GetMapping("/export-contacts")
	public String exportUserData() {
		try {
			//InputStream fp = new FileInputStream("testcases.xlsx");
			//Workbook wb = WorkbookFactory.create(fp);			
			/*Cell cell = row.getCell(0);
			if(cell != null) {
				System.out.println("Cell Data is " + cell);
			}
			else {
				System.out.println("Cell is empty");
			}*/
			
			
			
			
			
			
			Workbook wb = new HSSFWorkbook();
			Sheet sheet = wb.createSheet("Inspector");
			Row row = sheet.createRow(0);
			row.createCell(0).setCellValue("Devendra Kumar");
			row.createCell(1).setCellValue("8630834625");
			
			row = sheet.createRow(1);
			row.createCell(0).setCellValue("Inspector");
			row.createCell(1).setCellValue("In Custom");
			
			row = sheet.createRow(2);
			row.createCell(0).setCellValue("Delhi");
			row.createCell(1).setCellValue("NehruNager");

			row = sheet.createRow(3);
			row.createCell(0).setCellValue("Garhikhanpur");
			row.createCell(1).setCellValue("Budaun");
			
			row = sheet.createRow(4);
			row.createCell(0).setCellValue("BSA");
			row.createCell(1).setCellValue("Sambhal");
			 
			FileOutputStream fp = new FileOutputStream("Inspectors.xlsx");
			wb.write(fp);
			fp.close();
			wb.close();
			
			System.out.println("Inspectors file has been generated successfully.");  
			
			
		}
		catch (Exception e) {
			System.err.println("Error");
			e.printStackTrace();
		}
		return "home";
	}
}
