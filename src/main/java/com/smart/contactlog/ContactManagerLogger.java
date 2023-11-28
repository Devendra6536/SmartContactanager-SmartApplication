package com.smart.contactlog;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class ContactManagerLogger {

	public void writeContactManagerlog(String str) throws IOException {
		try {
			// Writing Date into the log file
			LocalDateTime date = LocalDateTime.now();

			// this is the date formatter
			DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			String formatedDate = date.format(formater);

			System.out.println("Today date is " + formatedDate);
			String filePath = "/home/team/devendra/JavaBackendDevelopment/SpringbootProjects/contactmanager/logs/contactManager.log";
			try (FileWriter logger = new FileWriter(filePath, true);

					PrintWriter printWriter = new PrintWriter(logger);) {
				printWriter.print(formatedDate + " : INFO : " + str + " \n ");
			}

			System.out.println("Successfully appneded in the log");
		} catch (Exception e) {
			System.err.println("ERROR " + e);
			e.printStackTrace();
		}

	}

	public void writeSupportQueriesToCSVfile(String str) throws IOException {
		try {
			// Writing Date into the log file
			LocalDateTime date = LocalDateTime.now();

			// this is the date formatter
			DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			String formatedDate = date.format(formater);

			System.out.println("Today date is " + formatedDate);
			String filePath = "/home/team/devendra/JavaBackendDevelopment/SpringbootProjects/contactmanager/logs/supportQueries.csv";
			try (FileWriter logger = new FileWriter(filePath, true);

					PrintWriter printWriter = new PrintWriter(logger);) {
				printWriter.print(str + " \n ");
			}

			System.out.println("Query saved ");
		} catch (Exception e) {
			System.err.println("ERROR " + e);
			e.printStackTrace();
		}

	}
	
	
	//this method is used for saving the user into a csv files
	public void writeRegisteredUserToCSVfile(String name, String email) throws IOException {
		try {
			// Writing Date into the log file
			LocalDateTime date = LocalDateTime.now();

			// this is the date formatter
			DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			String formatedDate = date.format(formater);

			System.out.println("Today date is " + formatedDate);
			String filePath = "/home/team/devendra/JavaBackendDevelopment/SpringbootProjects/contactmanager/logs/RegisteredUsers.csv";
			try (FileWriter logger = new FileWriter(filePath, true);

					PrintWriter printWriter = new PrintWriter(logger);) {
				printWriter.print(name + "|" + email + " \n ");
			}

			System.out.println(" User is successfully saved into the CSV files ");
		} catch (Exception e) {
			System.err.println("ERROR " + e);
			e.printStackTrace();
		}

	}
}
