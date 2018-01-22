package org.server1.api;

import org.server1.api.models.UserModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "com.kdyzm.server1")
@RequestMapping("/service1-user")
public interface Server1UserResource {

	@RequestMapping(value="user",method = RequestMethod.GET)
	public UserModel getUserById(@RequestParam("userId") String userId);

}
