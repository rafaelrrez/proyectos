package com.mr.rebujito.grupo1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mr.rebujito.grupo1.entity.MemberLogin;
import com.mr.rebujito.grupo1.entity.MemberToken;
import com.mr.rebujito.grupo1.security.JWTGenerator;

@RestController
@RequestMapping("")
public class MemberController {

	@Autowired
	private JWTGenerator JWTGenerator;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<MemberToken> login(@RequestBody MemberLogin m) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(m.getUsername(), m.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = JWTGenerator.generatorToken(authentication);
		
		MemberToken memberToken = new MemberToken(token);

		return new ResponseEntity<MemberToken>(memberToken, HttpStatus.OK);
	}
}
