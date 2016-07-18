package api.gateway.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("simpleserver")
public interface FeignUserService {
	
	@RequestMapping(method = RequestMethod.GET,value = "/user")
	 public String readUserInfo();
}
