package com.fable.weatherall.Controllers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.ConfigurableApplicationContext;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fable.weatherall.Admin_User_Entities.ApiKeyUrl;
import com.fable.weatherall.Admin_User_Entities.User;
import com.fable.weatherall.Configs.CustomUserDetail;
import com.fable.weatherall.DTOs.LoginDTO;
import com.fable.weatherall.DTOs.UserDTO;
import com.fable.weatherall.DTOs.VerifyOtpDTO;
import com.fable.weatherall.Repos.ApiKeyUrlRepo;
import com.fable.weatherall.Repos.UserRepo;
import com.fable.weatherall.Responses.Feedback;
import com.fable.weatherall.Responses.LoginResponse;
import com.fable.weatherall.Services.AdminService;
import com.fable.weatherall.Services.EmailService;
import com.fable.weatherall.Services.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin
public class Weather_Home_RestControllers {

	@Autowired
	private UserService userService;
	
	 @Autowired
	 private EmailService emailService;
	
	@Autowired
    private UserRepo userRepo;

	@PostMapping(path = "/save")
	public String saveUser(@RequestBody UserDTO userDTO) {
		userDTO.setUserType("user");
		userDTO.setUserType(userDTO.getUserType());
		String id = userService.addUser(userDTO);
		return id;
	}
	
	@PostMapping(path = "/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO,HttpSession session) {
		//System.out.println("Working");
		
		session.setAttribute("userEmail", loginDTO.getEmail());
		LoginResponse loginResponse = userService.loginUser(loginDTO);
		return ResponseEntity.ok(loginResponse);
	}
	
	
	@PostMapping("/verify-otp")
	public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpDTO verifyOtpDTO) {
		System.out.println("verify otp");
	    boolean isOtpVerified = userService.verifyOtp(verifyOtpDTO.getEmail(), verifyOtpDTO.getOtp());
	    
	    if (isOtpVerified) {
	        return ResponseEntity.ok("OTP verified successfully");
	    } else {
	        return ResponseEntity.badRequest().body("Invalid OTP or OTP expired");
	    }
	}
	
	@PostMapping("/sendOtp/{email}")
	public String sendOtpToMail(@PathVariable("email") String email) {
		userService.sendOtpService(email);
		return "otp send successfully";
	}
	
	 @PostMapping("/authenticate")
	    public ResponseEntity<?> loginAdmin(@RequestBody User user,HttpSession session) {
	    	session.setAttribute("adminEmail", user.getEmail());
	    	LoginResponse loginResponse = userService.loginAdmin(user);
			return ResponseEntity.ok(loginResponse);
	    }

	 
	 @PostMapping(value = "/edit-profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<String> editProfile(@RequestBody UserDTO userDTO) {
	     try {
	         String username = userService.updateUserProfile(userDTO);
	         return ResponseEntity.ok("{\"url\": \"/user_dashboard\"}");
	     } catch (Exception e) {
	         // Handle exceptions or return an appropriate response
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"An error occurred while updating the profile.\"}");
	     }
	 }
	 

	    @GetMapping("/checkEmailExists/{email}")
	    public ResponseEntity<Map<String, Boolean>> checkEmailExists(@PathVariable String email) {
	        User user = userRepo.findByEmail(email);
	        Map<String, Boolean> response = new HashMap<>();
	        response.put("exists", user != null);
	        return ResponseEntity.ok(response);
	    }
	    
	    @PostMapping("/saveEmailAndOTP")
	    public ResponseEntity<String> saveEmailAndOTP(@RequestBody VerifyOtpDTO verifyOtpDTO) {
	        try {
	            userService.saveEmailAndOTP(verifyOtpDTO);
	            return ResponseEntity.ok("Email and OTP saved successfully!");
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Error saving email and OTP: " + e.getMessage());
	        }
	    }
	   

	    @PostMapping("/submit-feedback")
	    public Map<String, Object> submitFeedback(@RequestBody Feedback feedback,HttpSession session,Model model) {
	    	
	        Map<String, Object> response = new HashMap<>();
	        
	        CustomUserDetail user = (CustomUserDetail) session.getAttribute("user");
	    	  
	    	String email = user.getEmail();

	    	
	        String name = userRepo.findUsernameByEmail(email);
	        


	        try {
	            emailService.sendFeedbackEmail("weatherfable@gmail.com", "Feedback Suggestion", "from, \n"+"User: "+name+"\n"+"Email: "+ email+"\n\n"+ feedback.getSuggestion());

	            response.put("success", true);
	            response.put("message", "Feedback submitted successfully!");
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.put("success", false);
	            response.put("message", "Error submitting feedback");
	        }

	        return response;
	    }
	    


	/*
	
//	    @Value("${openweathermap.api.key}")
//	    private String apiKey;
//
//	    @Value("${openweathermap.api.url}")
//	    private String apiUrl;
	
	      //These values should be stored in database to change them dynamically because with code the values remain same even after changing with setters.
	       
	      @Autowired
	      private ApiKeyUrlRepo apikeyurlrepo;

	      @Autowired
          private AdminService adminService;
	      
//	      Map<String, String> api = adminService.getApiKeyUrl();
	      
//	     String apiKey = apikeyurlrepo.findApikeyById(1);
//	     String apiUrl = apikeyurlrepo.findApiurlById(1);
	      
//	      @Autowired
//	      private ApiKeyUrlRepo apikeyurl;
//	
	
//         private String apiKey="8c8f2a026dd44c7ee20c5a1a657bd2fa";
//         private String apiUrl="https://api.openweathermap.org/data/2.5/weather"; 
         


//	    public String getApiKey() {
//			return apiKey;
//		}
//
//		public void setApiKey(String apiKey) {
//			this.apiKey = apiKey;
//		}
//
//		public String getApiUrl() {
//			return apiUrl;
//		}
//
//		public void setApiUrl(String apiUrl) {
//			this.apiUrl = apiUrl;
//		}
//
		@GetMapping("/api/config")
		@ResponseBody 
	    public Map<String, String> getApiConfig() {
			
			 Map<String, String> config = adminService.getApiKeyUrl();
			
//			 ApiKeyUrl a=apikeyurlrepo.findById(1);
//			
//			 Map<String, String> config = new HashMap<>();
			 
//		     config.put("apiKey", a.getApikey());
//		     config.put("apiUrl", a.getApiurl());
			
	        return config;
	    }
	
		@GetMapping("/api/view")
	    public String viewApiKeyUrl(Model model) {
	    	
//			 ApiKeyUrl a=apikeyurlrepo.findById(1);
			
			Map<String, String> config = adminService.getApiKeyUrl();
			
	        model.addAttribute("apiKey", config.get("apiKey"));
	        model.addAttribute("apiUrl", config.get("apiUrl"));
	        
	        return "map-google";
	    }
		

		@GetMapping("/api/wapiobj")
		@ResponseBody 
	    public Map<String, String> getWapiConfig() {
			
			Map<String, String> config = adminService.getWapiKeyUrl();

	        return config;
	    }
		
		@GetMapping("/api/wview")
	    public String viewWapiKeyUrl(Model model) {
	    	
			
			Map<String, String> config = adminService.getWapiKeyUrl();
			
	        model.addAttribute("wapiKey", config.get("wapiKey"));
	        model.addAttribute("wapiUrl", config.get("wapiUrl"));
	        
	        return "weatherapi";
	    }
	    */

}
