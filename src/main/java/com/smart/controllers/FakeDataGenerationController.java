package com.smart.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smart.dao.FakeUserRepository;
import com.smart.dao.TestDataRepository;
import com.smart.entities.FakeUser;
import com.smart.entities.TestData;
import com.smart.entities.TestMetaDataInformation;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class FakeDataGenerationController {
	
	@Autowired
	private TestDataRepository dataRepository;

	
	@PostMapping("/add_test_meta_data")
	public List<TestData> addTestData(@RequestBody List<TestData> testData) {
		TestData testData2 = new TestData();
		System.out.println(testData);
		
		 String pythonScript = System.getProperty("user.dir") + "/python/FakeDataGeneration.py";
		 
	        List<String> pythonArgs = new ArrayList<>();
	        for(int i=0;i<testData.size();i++) {
	        	pythonArgs.add(testData.get(i).getDatatype());
	        }
	        

	        // Create a list to store the command and arguments
	        List<String> command = new ArrayList<>();
	        command.add("python3");  // or "python3" depending on your Python version
	        command.add(pythonScript);
	        command.add("csv");
	        command.add("10");
	        command.addAll(pythonArgs);
		
		try {
			
			
			ProcessBuilder builder = new ProcessBuilder(command);
			Process process = builder.start();
				
			
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				BufferedReader reader1 = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				String lines = null;
				
			
				
				while((lines = reader.readLine())!=null) {
					System.out.println(lines);
				}
				
				while((lines = reader1.readLine())!=null) {
					System.out.println(lines);
				}
				
				
			} catch (Exception e) {		
				System.out.println("Error");
				System.err.println(e);
			}
		
		generateJSON(testData);
		
		return this.dataRepository.saveAll(testData);
	}
	
	@GetMapping("/get_test_meta_data")
	public ResponseEntity<List<TestData>> getTestData() {
		System.out.println("called");
		return ResponseEntity.of(Optional.of(this.dataRepository.findAll()));
	}
	
	
	public static void generateJSON(List<TestData> testData) {
		String ss="[";
		for(int j=0;j<testData.size();j++) {
			ss+="{\"datatype\":" + "\""+testData.get(j).getDatatype()+"\","+ "\"ColumnName\":" + "\""+testData.get(j).getColumnName()+"\","+ "\"ColNo\":"+ "\""+testData.get(j).getColNo()+"\"}";
			if(j!=testData.size()-1) {
				ss+=",";
			}
			else {
				ss+="]";
			}
		}
		System.out.println("\n\nthis is json comes from ui  --->   "+ss);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping("/generate_TestData")
	public ResponseEntity<List<TestMetaDataInformation>> generateTestData(@RequestBody List<TestMetaDataInformation> dataInformation) {
		
		
		String pythonScript = System.getProperty("user.dir") + "/python/FakeDataGeneration.py";
		 
        List<String> pythonArgs = new ArrayList<>();
        int n = dataInformation.get(0).getTestData().size();
        
        
        // Adding all the datatype to pythonargs command
        for(int i=0;i<n;i++) {
        	pythonArgs.add(dataInformation.get(0).getTestData().get(i).getDatatype());
        }
        
        System.out.println("Datatypes for Generating the Data "+pythonArgs);

        // Create a list to store the command and arguments
        List<String> command = new ArrayList<>();
        command.add("python3");  // or "python3" depending on your Python version
        command.add(pythonScript);
        command.add(dataInformation.get(0).getDataTypeFormatMetaInfo().getFileName());
        command.add(dataInformation.get(0).getDataTypeFormatMetaInfo().getFormat());
        command.add(dataInformation.get(0).getDataTypeFormatMetaInfo().getNum_of_rows());
        command.add(dataInformation.get(0).getDataTypeFormatMetaInfo().getDatafileDirectory());
        command.addAll(pythonArgs);
        
	System.out.println(command);
	try {
		
		
		//Starting the python3 peocess
		ProcessBuilder builder = new ProcessBuilder(command);
		Process process = builder.start();
			
		
			//to Read the python output
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader reader1 = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String lines = null;
	
			//print the python output
			while((lines = reader.readLine())!=null) {
				System.out.println(lines);
			}
			
			while((lines = reader1.readLine())!=null) {
				System.out.println(lines);
			}
	
			
		} catch (Exception e) {		
			System.out.println("Opps!Something went wrong");
			System.err.println(e);
		}
		
		return ResponseEntity.of(Optional.of(dataInformation));
	}
	
}
