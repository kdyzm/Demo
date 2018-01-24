package org.server2.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/queue")
public interface QueueResource {

	@RequestMapping(method=RequestMethod.GET,value="/send")
	public void sendMessage();
	
	@RequestMapping(method=RequestMethod.GET,value="/consume")
	public void consumeMessage();
}
