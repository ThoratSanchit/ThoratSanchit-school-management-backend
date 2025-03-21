package com.jwt.implementation.controller;

import java.util.HashMap;
import java.util.Map;

import com.jwt.implementation.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.jwt.implementation.config.JwtGeneratorValidator;
import com.jwt.implementation.model.User;
import com.jwt.implementation.model.UserDTO;
import com.jwt.implementation.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	JwtGeneratorValidator jwtGenVal;
	
	@Autowired
	BCryptPasswordEncoder bcCryptPasswordEncoder;

	@Autowired
	private ProductRepository productRepository;

	@PostMapping("/registration")
	public ResponseEntity<Object> registerUser(@RequestBody UserDTO userDto) {
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setPassword(bcCryptPasswordEncoder.encode(userDto.getPassword()));
		user.setUserName(userDto.getUserName());
		User users = userRepo.save(user);
		if (users.equals(null))
			return generateRespose("Not able to save user ", HttpStatus.BAD_REQUEST, user);
		else
			return generateRespose("User saved successfully : " + users.getId(), HttpStatus.OK, users);
	}

//	@GetMapping("/genToken")
//	public String generateJwtToken(@RequestBody UserDTO userDto) throws Exception {
//		try {
//			authManager.authenticate(
//					new UsernamePasswordAuthenticationToken(userDto.getUserName(), userDto.getPassword()));
//		} catch (Exception ex) {
//			throw new Exception("inavalid username/password");
//		}
//		return jwtGenVal.generateToken(userDto.getUserName(), String.valueOf(userDto.getId()));
//
//	}

	@PostMapping("/genToken")
	public String generateJwtToken(@RequestBody UserDTO userDto) throws Exception {
		try {
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(userDto.getUserName(), userDto.getPassword())
			);
		} catch (Exception ex) {
			throw new Exception("Invalid username/password");
		}

		// Fetch the correct user ID from the database
		User user = userRepo.findByUserName(userDto.getUserName());
		return jwtGenVal.generateToken(user.getUserName(),user.getEmail(), String.valueOf(user.getId()));
	}



	@GetMapping("/welcome")
	public ResponseEntity<String> welcome(@RequestHeader("Authorization") String token) {
		// Check if token starts with "Bearer "
		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7); // Remove "Bearer " prefix
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
		}

		try {
			// Extract username from token
			String username = jwtGenVal.extractUsername(token);
			String email=jwtGenVal.extractEmail(token);
			String id=jwtGenVal.extractUserId(token);

			return ResponseEntity.ok("Welcome, " + username +" ur email:- "+email+" User id "+id );

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or Expired Token");
		}
	}



	
	public ResponseEntity<Object> generateRespose(String message, HttpStatus st, Object responseobj) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("meaasge", message);
		map.put("Status", st.value());
		map.put("data", responseobj);

		return new ResponseEntity<Object>(map, st);
	}

}
