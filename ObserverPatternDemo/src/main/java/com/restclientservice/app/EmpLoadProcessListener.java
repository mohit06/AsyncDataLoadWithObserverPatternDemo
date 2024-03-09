package com.restclientservice.app;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpLoadProcessListener implements Listener {
	private HashMap<String, Long> job = new HashMap<>();

	@GetMapping("/status")
	public HashMap<String, Long> getAllStatus() {
		return job;
	}

	public void addDataPublisher(Publisher publisher) {
		// 0L here means that there is nothing left to be processed.
		job.put(publisher.getName(), 0L);
	}

	@Override
	public void initialiseJob(Long dataSize, Publisher pub) {
		job.put(pub.getName(), dataSize);
	}

	/*
	 * After processing every record , we will call update method that would
	 * decrease the value and when it becomes 0 then we say that all the records got
	 * processed.
	 */
	@Override
	public void update(Publisher pub) {
		long value = job.get(pub.getName());
		System.out.println("Remaining for processing: "+value);
		value--;
		job.put(pub.getName(), value);
	}

	@Override
	public void completeJob(Publisher pub) {
		job.put(pub.getName(), 0L);
	}

}
