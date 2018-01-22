package org.server2.server.rest.impl;

import org.server1.api.Server1UserResource;
import org.server1.api.models.UserModel;
import org.server2.api.UserResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.server2.service.mapper.UserMapper;
import com.server2.service.model.User;

@RestController
public class UserResourceImpl implements UserResource {

	@Autowired
	private Server1UserResource server1UserResource;

	@Autowired
	private UserMapper userMapper;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public String getUserById(String userId) {
		User user = userMapper.selectById(Integer.parseInt(userId));
		logger.info(user.toString());
		UserModel userById = server1UserResource.getUserById("1");
		return new Gson().toJson(userById);
	}

}
