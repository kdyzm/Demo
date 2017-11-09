package org.server2.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/service2-user")
public interface UserResource {

	@RequestMapping(value="user",method = RequestMethod.GET)
	public String getUserById(@RequestParam("userId") String userId);

}
