package com.restclientservice.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataLoadRestController {
	
	@Autowired
	private EmpService empService;

	@PutMapping("/save")
	public ResponseEntity<Void> startAsyncLoad(@RequestBody List<Employee> emp){
		empService.loadData(emp);
		return ResponseEntity.ok(null);
	}
	
	
}
