package org.server2.server.rest.impl;

import org.server2.api.TestResource;
import org.server2.api.models.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestResourceImpl implements TestResource {

	@Override
	public UserModel getUserById(String userId) {
		return new UserModel();
	}

}
