package com.fable.weatherall.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fable.weatherall.Admin_User_Entities.User;
import com.fable.weatherall.Configs.CustomUserDetail;
import com.fable.weatherall.DTOs.LoginDTO;
import com.fable.weatherall.DTOs.UserDTO;
import com.fable.weatherall.DTOs.VerifyOtpDTO;
import com.fable.weatherall.Repos.UserRepo;
import com.fable.weatherall.Responses.LoginResponse;
import com.fable.weatherall.Services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
    private UserRepo userRepo;
	
	@GetMapping("/view_userprofile")
	public String view_userprofile(HttpSession session,Model model)
		    {

	    	  CustomUserDetail user = (CustomUserDetail) session.getAttribute("user");
	    	  
	    	    String email = user.getEmail();
				String name = userRepo.findUsernameByEmail(email);   
				
				int userid = userRepo.findUseridByEmail(email);

		    	
		    	List<String> user1 = new ArrayList<>();
		    	List<String> user2 = new ArrayList<>();
		    	List<Integer> user3 = new ArrayList<>();
		    	
		    	user1.add(email);
		    	user2.add(name);
		    	user3.add(userid);
		    	
		    	model.addAttribute("user1", user1);
		    	model.addAttribute("user2", user2);
		    	model.addAttribute("user3", user3);
		    	
		    	return "userprofile";
		    }
	
	

}
