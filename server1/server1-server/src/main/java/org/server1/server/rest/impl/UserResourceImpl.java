package org.server1.server.rest.impl;

import org.server1.api.Server1UserResource;
import org.server1.api.models.UserModel;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResourceImpl implements Server1UserResource {

	@Override
	public UserModel getUserById(String userId) {
		UserModel userModel = new UserModel();
		userModel.setAge(12);
		userModel.setName("小明");
		return userModel;
	}

}
