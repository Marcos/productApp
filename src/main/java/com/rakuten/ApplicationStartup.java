package com.rakuten;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.rakuten.entities.User;
import com.rakuten.repositories.UserRepository;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		User user = userRepository.findByUsername("admin");
		if(user==null)
			userRepository.save(User.builder()
					.username("admin")
					.password("admin")
					.enabled(true)
					.build());
	}
}