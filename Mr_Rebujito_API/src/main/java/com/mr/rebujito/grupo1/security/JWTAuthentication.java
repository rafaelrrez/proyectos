package com.mr.rebujito.grupo1.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mr.rebujito.grupo1.service.MemberService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthentication extends OncePerRequestFilter {
	
	@Autowired
	private JWTGenerator JWTGenerator;

	@Autowired
	private MemberService memberService;

	public String getToken(HttpServletRequest httpRequest) {
		String tokenBearer = httpRequest.getHeader("Authorization");
		if (StringUtils.hasText(tokenBearer) && tokenBearer.startsWith("Bearer ")) {
			return tokenBearer.substring(7, tokenBearer.length());
		}
		return null;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = getToken(request);
		if (StringUtils.hasText(token) && JWTGenerator.validarToken(token)) {
			String username = JWTGenerator.obtenerUsernameJWT(token);
			UserDetails userDetails = memberService.loadUserByUsername(username);

			String rolUser = userDetails.getAuthorities().iterator().next().getAuthority();
			if (rolUser.equals("ADMIN") || rolUser.equals("STAND") || rolUser.equals("PARTNER") || rolUser.equals("TOWNHALL")) {
				UsernamePasswordAuthenticationToken authenticationoken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authenticationoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationoken);
			}
		}
		filterChain.doFilter(request, response);
	}
}
