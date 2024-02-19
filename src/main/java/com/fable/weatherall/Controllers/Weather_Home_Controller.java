package com.fable.weatherall.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fable.weatherall.Admin_User_Entities.ApiKeyUrl;
import com.fable.weatherall.Admin_User_Entities.User;
import com.fable.weatherall.Admin_User_Entities.WeatherApi;
import com.fable.weatherall.Configs.CustomUserDetail;
import com.fable.weatherall.DTOs.UserDTO;
import com.fable.weatherall.FoodEntites.Food;
import com.fable.weatherall.Repos.FoodRepo;
import com.fable.weatherall.Repos.UserRepo;
import com.fable.weatherall.Services.AdminService;
import com.fable.weatherall.Services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class Weather_Home_Controller {

	
//	@Autowired
//    private UserRepo userRepo;
//	
//	@Autowired
//	private FoodRepo foodrepo;

	
	@Autowired
	private UserService userService;
//	
//	 @Autowired
//     private AdminService adminService;

	
	@GetMapping("/home")
    public String displayHome(HttpSession session) {
		
		session.invalidate();
		
        return "Homepage";
    }
	
	@GetMapping("/comlogin")
	public String displayLogin() {
        return "comlogin";
    }
	
	@GetMapping("/signup")
	public String displaySignup() {
        return "signup";
    }
	
	@GetMapping("/user_dash")
	public String displayUserDashboard() {
        return "user";
    }
	
	@GetMapping("/map-google")
	public String displayMap() {
        return "map-google";
    }
	
	@GetMapping("/about")
	public String displayAboutPage() {
        return "about";
    }
	
	@GetMapping("/forgetPassword")
	public String forgetPassword() {
        return "forgetPassword";
    }

	@GetMapping("/feedback")
	public String displayfeedbackPage() {
        return "s_feedback";
    }
	
	@GetMapping("/forecast")
	public String displayForecast() {
        return "Forecast";
    }
	
    @GetMapping("/pages-profile")
	public String adminprofile() {
	    	
    	return "pages-profile";
	 }
    
    @PostMapping("/resetPassword")
    public String resetPassword(@ModelAttribute("detail") User user, Model model) {
        System.out.println("reset password");
        int status = userService.resetPassword(user);
        
        if (status == 1) {
            model.addAttribute("message", "User is not registered");
            return "forgetPassword";
        } else if (status == 3) {
            model.addAttribute("message", "Otp is not matched");
            return "forgetPassword";
        }
        return "redirect:/comlogin";
    }
//  
//	@GetMapping("/Clothtable")
//	public String adminprofilecloth() {
//
//		 return "clothtable";
//	}
//
//	 @GetMapping("/Activetable")
//     public String adminprofileactive() {
//
//		return "activetable";
//	 }
//	      
//	  @GetMapping("/Traveltable")
//	   public String adminprofiletravel() {
//
//		    return "traveltable";
//	 }
   
}