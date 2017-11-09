package org.server2.server.service;

import org.server1.api.UserResource;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "com.kdyzm.server1")
public interface Server1Api extends UserResource{
	
}
