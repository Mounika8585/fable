package com.fable.weatherall.Configs;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		 HttpSession session = request.getSession();
	        session.setAttribute("user", authentication.getPrincipal());
		
		var authourities = authentication.getAuthorities();
		var roles = authourities.stream().map(r -> r.getAuthority()).findFirst();
		
		if (roles.orElse("").equals("admin")) {
			
			response.sendRedirect("/admin/view_adminprofile");
			
		} else if (roles.orElse("").equals("user")) {
			
			response.sendRedirect("/user_dash");
			
		} else {
			
			response.sendRedirect("/error");
		}
		
		
		
	}

}
