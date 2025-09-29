package com.backend.todolist.auth.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.todolist.auth.service.UserService;

@RestController
@RequestMapping("/api/auth") // 1. Define a base path for all endpoints in this controller
public class UserController {

	private final UserService userService;

	// 2. Use constructor injection for dependencies - this is a best practice
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	// 3. Use more specific mapping annotations like @PostMapping
	// 4. The @ResponseStatus is redundant when returning a ResponseEntity
	@PostMapping("/signin")
    public ResponseEntity<UserSigninResponse> signin(@Valid @RequestBody UserSigninRequest userSigninRequest) {
		return new ResponseEntity<>(userService.signin(userSigninRequest), HttpStatus.OK);
    }

	@PostMapping("/signup")
    public ResponseEntity<UserSignupResponse> signup(@Valid @RequestBody UserSignupRequest userSignupRequest) {
		// The service layer should handle the logic of checking if a user exists.
		// For a new user, returning HTTP 201 Created is more semantically correct.
		return new ResponseEntity<>(userService.signup(userSignupRequest), HttpStatus.CREATED);
    }
}
