package com.restclientservice.app;

public interface Listener {

	public void update(Publisher pub);
	public void initialiseJob(Long dataSize, Publisher pub);
	public void completeJob(Publisher pub);

}
