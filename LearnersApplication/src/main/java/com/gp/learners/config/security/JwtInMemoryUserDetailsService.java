package com.gp.learners.config.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.User;
import com.gp.learners.repositories.UserRepository;


@Service
public class JwtInMemoryUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	static List<JwtUserDetails> inMemoryUserList = new ArrayList<>();
	static int num=0;

//	static {
//		inMemoryUserList.add(new JwtUserDetails(0L, "admin",
//				"$2a$10$KUY5FZfoUuaOzFKbXZ6gyOk.3ErD4GKdqSuDesQyugMbfHfuZ7i6O", "1"));
//	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if(num==0) {
			setUserInMemory();
		}
		
		Optional<JwtUserDetails> findFirst = inMemoryUserList.stream()
				.filter(user -> user.getUsername().equals(username)).findFirst();

		if (!findFirst.isPresent()) {
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
		}

		return findFirst.get();
	}
	
	public int  setUserInMemory() {
		inMemoryUserList.clear();
		List<User> userList = new ArrayList<User>();
		userList = userRepository.getActiveUsers();
		
		for (User user : userList) {
			inMemoryUserList.add(new JwtUserDetails(user.getUserId().longValue(),user.getEmail(),user.getPassword(),user.getRole().toString()));
		}
		num++;
		
		return 1;
	}
	
	public void addNewUserInMemory(User user) {
		inMemoryUserList.add(new JwtUserDetails(user.getUserId().longValue(),user.getEmail(),user.getPassword(),user.getRole().toString()));
	}
	
	public void updateUserInMemory(User oldUser, User updateUser) {
		
		JwtUserDetails oldUserData = new JwtUserDetails(oldUser.getUserId().longValue(),oldUser.getEmail(),oldUser.getPassword(),oldUser.getRole().toString());
		for(JwtUserDetails userData : inMemoryUserList) {
			if(userData.equals(oldUserData)) {
				JwtUserDetails updateUserData = new JwtUserDetails(updateUser.getUserId().longValue(),updateUser.getEmail(),updateUser.getPassword(),updateUser.getRole().toString());
				userData = updateUserData;
				break;
			}
		}
	}

}
