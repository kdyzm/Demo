package org.server1.api;

import org.server1.api.models.UserModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/service1-user")
public interface UserResource {

	@RequestMapping(value="user",method = RequestMethod.GET)
	public UserModel getUserById(@RequestParam("userId") String userId);

}
