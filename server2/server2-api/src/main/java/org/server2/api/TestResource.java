package org.server2.api;

import org.server2.api.models.UserModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/test")
public interface TestResource {

	@RequestMapping(value="/test",method = RequestMethod.GET)
	public UserModel getUserById(@RequestParam("userId") String userId);

}
