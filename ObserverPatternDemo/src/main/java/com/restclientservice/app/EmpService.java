package com.restclientservice.app;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class EmpService implements Publisher {
	
	private String name = "Emp-Service-Job";

	@Autowired
	private EmpRepo empRepo;

	@Autowired
	private EmpLoadProcessListener empLoadProcessListener;

	@PostConstruct
	public void registerWithListener() {
		empLoadProcessListener.addDataPublisher(this);
	}

	private List<Listener> observers = new LinkedList<Listener>();

	@Async
	public void loadData(List<Employee> empList) {
		observers.add(empLoadProcessListener);
		long totalData = empList.size();
		System.out.println("Total employee to be loaded: "+totalData);
		
		observers.stream().forEach(ob -> ob.initialiseJob(totalData, this));
		System.out.println("Employee loading started.");
		
		for (Employee e : empList) {
			empRepo.save(e);
			observers.stream().forEach(ob -> ob.update(this));
		}
		
		observers.stream().forEach(ob -> ob.completeJob(this));
		System.out.println("Employee loading completed.");

	}
	
	public String getName() {
		return name;
	}

}
