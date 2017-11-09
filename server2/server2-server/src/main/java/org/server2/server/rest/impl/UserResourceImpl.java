package org.server2.server.rest.impl;

import org.server1.api.models.UserModel;
import org.server2.api.UserResource;
import org.server2.server.service.Server1Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class UserResourceImpl implements UserResource {

	@Autowired
	private Server1Api server1Api;

	@Override
	public String getUserById(String userId) {
		UserModel userById = server1Api.getUserById("1");
		return new Gson().toJson(userById);
	}

}
	