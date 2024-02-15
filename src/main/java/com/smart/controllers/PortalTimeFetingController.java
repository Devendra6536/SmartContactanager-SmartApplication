package com.smart.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smart.entities.PortalTime;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PortalTimeFetingController {
	
	@GetMapping("/fetchPortalTime/{employee_id}/{password}")
	public ResponseEntity<List<PortalTime>> fetchPortalTime(@PathVariable("employee_id") String employee_id,@PathVariable("password") String password) {
		
		System.out.println("Employee ID -> " + employee_id + " Password :" + password);
		List<PortalTime> portalTimes = new ArrayList<>();
		try {
			
			ProcessBuilder builder = new ProcessBuilder("python3",System.getProperty("user.dir") + "/python/portalTime.py",employee_id, password);
		    Process process = builder.start();
		   
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader reader1 = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String lines = null;
			System.out.println("\n\n\n\n\n");
			String ss = "";
			int cnt=0;
			while((lines = reader.readLine())!=null) {
				if(cnt>8) {
				ss = lines.replaceAll("\\s+", " ");
				
				String arg[]=ss.split(" ");
			        List<String> TimeField = new ArrayList<String>();
			        for(int i=0;i<arg.length;i++) {
			        	if(i!=2)
			        		TimeField.add(arg[i]);
			        }
			        
			    
			    
			    TimeField.set(4,TimeField.get(4)+" "+TimeField.get(5));
			    TimeField.remove(5);
			    if(TimeField.size()==7){
			    	TimeField.set(5,TimeField.get(5)+" "+TimeField.get(6));
			    	TimeField.remove(6);
			    }
			    System.out.println(TimeField+ " " + TimeField.size());
					    
			    
			    PortalTime portalTime = new PortalTime();
			    	portalTime.setSno(TimeField.get(0));
			    	portalTime.setDate(TimeField.get(1));
			    	portalTime.setTime_spend(TimeField.get(2));
			    	portalTime.setTotal_time(TimeField.get(3));
			    	portalTime.setIn_time(TimeField.get(4));
			    	if(TimeField.size()==6) {
			    		portalTime.setOut_time(TimeField.get(5));
			    	}else {
			    		portalTime.setOut_time("----");
			    	}
	
			 
			    	portalTimes.add(portalTime);
 
			}
			
				cnt++;
			}
			
		} catch (Exception e) {		
			System.out.println("Error");
			System.err.println(e);
		}
		
		return ResponseEntity.of(Optional.of(portalTimes));
	}
}
