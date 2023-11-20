package com.smart.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import com.smart.contactlog.ContactManagerLogger;


public class FileSaver {
	
	
	/* this method to save the image into the folder */
	public boolean saveFile(MultipartFile file) throws IOException {
		try {
			
		    ContactManagerLogger contactManagerLogger= new ContactManagerLogger();
			
			File savefile = new ClassPathResource("static/image").getFile();
			Path path = Paths.get(savefile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			System.out.println("Here the image is uploaded"+path);
			Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
			
			
			
			
			//writing the file saving log
			contactManagerLogger.writeContactManagerlog("+-+-+-+-+-+-+-+-+-");
			String log_message_to_write = file.getOriginalFilename()+" : saved successfully";
			contactManagerLogger.writeContactManagerlog("\n");
			contactManagerLogger.writeContactManagerlog(log_message_to_write);
			contactManagerLogger.writeContactManagerlog("                        ");
			System.out.println("Logs write successfully");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("uploading error" + e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}	

}