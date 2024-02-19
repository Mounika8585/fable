package com.fable.weatherall.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fable.weatherall.Admin_User_Entities.ApiKeyUrl;
import com.fable.weatherall.Admin_User_Entities.User;
import com.fable.weatherall.Admin_User_Entities.WeatherApi;
import com.fable.weatherall.ClothEntites.ClothingItem;
import com.fable.weatherall.ClothEntites.ClothingRecommendation;
import com.fable.weatherall.ClothEntites.ClothingType;
import com.fable.weatherall.ClothEntites.WeatherDescription;
import com.fable.weatherall.Configs.CustomUserDetail;
import com.fable.weatherall.DTOs.UserDTO;
import com.fable.weatherall.FoodEntites.Food;
import com.fable.weatherall.FoodEntites.FoodTemperatureMap;
import com.fable.weatherall.OutEntities.Activity;
import com.fable.weatherall.OutEntities.ActivityRecommendation;
import com.fable.weatherall.OutEntities.RecommendationLevelOut;
import com.fable.weatherall.OutEntities.WeatherDescriptionOut;
import com.fable.weatherall.Repos.FoodRepo;
import com.fable.weatherall.Repos.FoodTempMapRepo;
import com.fable.weatherall.Repos.OutActivityRepo;
import com.fable.weatherall.Repos.OutRepo;
import com.fable.weatherall.Repos.ClothItemRepo;
import com.fable.weatherall.Repos.ClothRepo;
import com.fable.weatherall.Repos.ClothRepo.ClothingRecommendationDetailsProjection;
import com.fable.weatherall.Repos.OutRepo.ActivityRecommendationDetailsProjection;
import com.fable.weatherall.Repos.TravelNameRepo;
import com.fable.weatherall.Repos.TravelRepo;
import com.fable.weatherall.Repos.UserRepo;
import com.fable.weatherall.Repos.TravelRepo.TravelRecommendationDetailsProjection;
import com.fable.weatherall.Services.AdminService;
import com.fable.weatherall.Services.UserService;
import com.fable.weatherall.TravelEntities.RecommendationLevelTravel;
import com.fable.weatherall.TravelEntities.TravelNames;
import com.fable.weatherall.TravelEntities.TravelRecommendation;
import com.fable.weatherall.TravelEntities.WeatherDescriptionTravel;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	 @Autowired
     private AdminService adminService;
	 
	 @Autowired
     private UserService userService;
	 
	 @Autowired
	 private UserRepo userRepo;
    
     @Autowired
     private FoodRepo foodrepo;
     
     @Autowired
     private FoodTempMapRepo ftmrepo;
     
     @Autowired
     private ClothRepo clothrepo;
     
     @Autowired
     private ClothItemRepo cirepo;
     
     @Autowired
     private OutActivityRepo outactrepo;
     
     @Autowired
     private OutRepo outrepo;
     
     @Autowired
     private TravelNameRepo trnmrepo;
     
     @Autowired
     private TravelRepo travelrepo;
     

     
        @GetMapping("/api/config")
		@ResponseBody 
	    public Map<String, String> getApiConfig() {
			
			 Map<String, String> config = adminService.getApiKeyUrl();

	        return config;
	    }
	
		@GetMapping("/api/view")
	    public String viewApiKeyUrl(Model model) {
			
			Map<String, String> config = adminService.getApiKeyUrl();
			
	        model.addAttribute("apiKey", config.get("apiKey"));
	        model.addAttribute("apiUrl", config.get("apiUrl"));
	        
	        return "map-google";
	    }

		@GetMapping("/api/wapiobj")
		@ResponseBody 
	    public Map<String, String> getWapiConfig() {
			
			Map<String, String> config = adminService.getWapiKeyUrl();
			
			System.out.println(config);

	        return config;
	    }
		
		@GetMapping("/api/wview")
	    public String viewWapiKeyUrl(Model model) {
	    	
			
			Map<String, String> config = adminService.getWapiKeyUrl();
			
	        model.addAttribute("wapiKey", config.get("wapiKey"));
	        model.addAttribute("wapiUrl", config.get("wapiUrl"));
	        
	        return "weatherapi";
	    }
		
		
		  @PostMapping("/update_api")
		    public String updateApi(@ModelAttribute("update") ApiKeyUrl apikeyurl) {

	            adminService.update_ApiKeyUrl(apikeyurl);
		            
		        return "redirect:/admin/api/view"; 
		    }
		    
		    
		    @PostMapping("/update_wapi")
		    public String updateWapi(@ModelAttribute("update") WeatherApi wapikeyurl) {
		            
	            adminService.update_WapiKeyUrl(wapikeyurl);
		            
		        return "redirect:/admin/api/wview"; 
		    }

		    @PostMapping("/update_admin")
		    public String updateAdminprofile(@ModelAttribute("update") User update) {
		            
	            adminService.update_Admin(update);
		            
		        return "redirect:/admin/view_adminprofile"; 
		    }

		    @PostMapping("/u_add")
		      public String saveUser(@ModelAttribute("userAdd") UserDTO userDTO) {
	
		              userService.addUserinAdmin(userDTO);
		              
		              return "redirect:/admin/getUsers"; 
		      }
		    
		    
			@GetMapping("/view_adminprofile")
		    public String view_adminprofile(HttpSession session,Model model)
			    {
				
				 CustomUserDetail user = (CustomUserDetail) session.getAttribute("user");
				 
					String email = user.getEmail();
					String name = userRepo.findUsernameByEmail(email);
					
			    	User us = userRepo.findByEmail(email);
   
			    	List<User> user1 = new ArrayList<>();
			    	List<String> user2 = new ArrayList<>();
			    	
			    	user1.add(us);
			    	user2.add(name);

			        model.addAttribute("user1", user1);
			        model.addAttribute("user2", user2);

			        return "pages-profile"; 
			    }

		      @GetMapping("/getUsers")
		      public String getAllUsers(Model model) {
		          List<User> users = userRepo.findAll();
		          model.addAttribute("users", users);

		          return "table-basic"; 
		      }
		  	
		  	  @PostMapping("/deleteUser")
		  	    public String deleteUser(@RequestParam("userId") int userId) {
		  	      
		  	    	userService.deleteUserById(userId);
		  	       
		  	        return "redirect:/admin/getUsers"; 
		  	    }
  
	 @GetMapping("/getAllfood")
	 public String allFoods(Model model){
   	 
   	 List<Food> foods=foodrepo.findAll();
   	 
   	 List<FoodTemperatureMap> ftm=ftmrepo.findAll();
   	 
   	 model.addAttribute("ftm", ftm);

   	 model.addAttribute("foods", foods);
   	 
   	 return "foodtable";
   	 
    }
	 
	 @PostMapping("/addFoods")
	 public String addFoods(@ModelAttribute("foodAdd") Food food) 
	 
	 {
		 foodrepo.save(food);
		 
		 return "redirect:/admin/getAllfood";
	 }
 
	 @PostMapping("/deleteFoods")
	 public String deleteFoods(  @RequestParam("foodId") int foodId ) {
		 
		 adminService.deleteFoodService(foodId);
		
		 
		 return "redirect:/admin/getAllfood";
		 
	 }
	 
	 @PostMapping("/addTempMap")
	 public String addTempMap ( @ModelAttribute("ftempMapAdd") FoodTemperatureMap ftempMapAdd)
	 {
		 ftmrepo.save(ftempMapAdd);
		 
		 return "redirect:/admin/getAllfood";
		 
	 }
	 
	 @PostMapping("/delTempMap")
	 public String delTempMap(  @RequestParam("foodTemperatureId") int foodTemperatureId ) {
		 adminService.delTempMap(foodTemperatureId);
		 
		 return "redirect:/admin/getAllfood";

		 
	 }

	 @GetMapping("/getClothItems")
	 public String allClothItems(Model model){
   	 
		 List<ClothingItem> cis = cirepo.findAll();
		 List<ClothingRecommendationDetailsProjection> crs=clothrepo.findAllClothingRecommendationsWithDetailsSortedByClothingRecommendationId();
		 
		 model.addAttribute("cis", cis);
		 model.addAttribute("crs", crs);

		 return "clothtable";
    }

	 @PostMapping("/addClothItem")
	 public String addClothItems ( @ModelAttribute("clothadd") ClothingItem clothadd)
	 {
		 
		 cirepo.save(clothadd);
		 
		 return "redirect:/admin/getClothItems";
		 
	 }
	 
	 @PostMapping("/delClothItem")
	 public String delClothItem(   @RequestParam("clothingItemId") int clothingItemId) {
		 
		 adminService.delClothItem(clothingItemId);
		 
		 return "redirect:/admin/getClothItems";
		 
	 }

	 @PostMapping("/addClothReco")
	 public String addClothReco ( @RequestParam("clothingItemId")int clothingItemId,
			                      @RequestParam("clothingTypeId")int clothingTypeId,
			                      @RequestParam("weatherDescriptionId")int weatherDescriptionId   )
	 {
		 
		 Integer clothitem_id = Integer.valueOf(clothingItemId);
		 Integer clothtype_id = Integer.valueOf(clothingTypeId);
		 Integer wthr_id = Integer.valueOf(weatherDescriptionId);

		 ClothingRecommendation cr = new ClothingRecommendation();
		 
		 ClothingItem ci = new ClothingItem();
		 
		 ClothingType ct = new ClothingType();
		 
		 WeatherDescription wd = new WeatherDescription();
		 
		 ci.setClothingItemId(clothitem_id);
		 
		 ct.setClothingTypeId(clothtype_id);
		 
		 wd.setWeatherDescriptionId(wthr_id);
		 
		 cr.setClothingItemId(ci);
		 
		 cr.setClothingTypeId(ct);

		 cr.setWeatherDescriptionId(wd);

		 
		 clothrepo.save(cr);
		 
		 return "redirect:/admin/getClothItems";
		 
	 }
 
	 @PostMapping("/delClothReco")
	 public String delClothReco(  @RequestParam("clothingRecoId") int clothingRecoId) {
		 
		 adminService.delClothReco(clothingRecoId);
		 
		 return "redirect:/admin/getClothItems";
	 }

	 //After adding record in recommendations map table and main table first should delete id from map table 
	 
	 @GetMapping("/getOutActs")
	 public String allOutActs(Model model){
   	 
		 List<Activity> acts = outactrepo.findAll();
		 List<ActivityRecommendationDetailsProjection> ors = outrepo.findAllActivityRecommendationsWithDetailsSortedByRecommendationId();
		 
		 model.addAttribute("acts", acts);
		 model.addAttribute("ors", ors);
		 
		 return "activetable";
     }
	 
	 @PostMapping("/addOutActs")
	 public String addOutActs ( @ModelAttribute("actname")Activity actname)
	 {

		 outactrepo.save(actname);
		 return "redirect:/admin/getOutActs";
		 
	 }
	 
	 @PostMapping("/delOutActs")
	 public String delOutActs(  @RequestParam("activityid") int activityid ) {

		 
		 adminService.delOutActs(activityid);
		 return "redirect:/admin/getOutActs";
	 } 
	 
	 @PostMapping("/addOutReco")
	 public String addOutReco (   @RequestParam("activityid")int activityid,
			                    @RequestParam("recommendationLevelId")int recommendationLevelId,
			                    @RequestParam("weatherDescriptionId")int weatherDescriptionId  )
	 {
		 Integer activity_id = Integer.valueOf(activityid);
		 Integer level_id = Integer.valueOf(recommendationLevelId);
		 Integer wthr_id = Integer.valueOf(weatherDescriptionId);
		 
		 ActivityRecommendation ar = new ActivityRecommendation();
		 
		 Activity ac = new Activity();
		 
		 RecommendationLevelOut lvl = new RecommendationLevelOut();
		 
		 WeatherDescriptionOut wd = new WeatherDescriptionOut();
		 
		 ac.setActivityid(activity_id);
		 
		 lvl.setRecommendationLevelId(level_id);
		 
		 wd.setWeatherDescriptionId(wthr_id);
		 
		 ar.setActivity(ac);
		 
		 ar.setRecommendationLevel(lvl);

		 ar.setWeatherDescription(wd);

		 outrepo.save(ar);
		 return "redirect:/admin/getOutActs";
		 
	 }
	 
	 @PostMapping("/delOutReco")
	 public String delOutReco(  @RequestParam("outreid") int outreid ) {
		 
		 adminService.delOutReco(outreid);
		 return "redirect:/admin/getOutActs";
		 
	 }
	 
	 @GetMapping("/getTrNms")
	 public String  allgetTrNms(Model model){
   	 
		 List<TravelNames> trnms = trnmrepo.findAll();
		 List<TravelRecommendationDetailsProjection> trs = travelrepo.findAllTravelRecommendationsWithDetailsSortedByRecommendationId();

		 model.addAttribute("trnms", trnms);
		 model.addAttribute("trs", trs);

		 return "traveltable";
     }
	 
	 @PostMapping("/addTrNms")
	 public String addTrNms ( @ModelAttribute("traname")TravelNames trnms)
	 {
		 trnmrepo.save(trnms);
		 
		 return "redirect:/admin/getTrNms";
		 
	 }
	 
	 @PostMapping("/delTrNms")
	 public String delTrNms(@RequestParam("travelid") int travelid ) {

		 adminService.delTrNms(travelid);
		 return "redirect:/admin/getTrNms";
		 
	 }

	 @PostMapping("/addTraReco")
	 public String addTraReco ( @RequestParam("travelid")int travelid,
			                   @RequestParam("recommendationLevelId")int recommendationLevelId,
			                   @RequestParam("weatherDescriptionId")int weatherDescriptionId  )
	 {
		 Integer travel_id = Integer.valueOf(travelid);
		 Integer level_id = Integer.valueOf(recommendationLevelId);
		 Integer wthr_id = Integer.valueOf(weatherDescriptionId);
		 
		 
		 TravelRecommendation tr = new TravelRecommendation();
		 
		 TravelNames tn = new TravelNames();
		 
		 RecommendationLevelTravel lvl = new RecommendationLevelTravel();
		 
		 WeatherDescriptionTravel wd = new WeatherDescriptionTravel();
		 
		 tn.setTravelid(travel_id);
		 
		 lvl.setRecommendationLevelId(level_id);
		 
		 wd.setWeatherDescriptionId(wthr_id);
		 
		 tr.setTravelnames(tn);
		 
		 tr.setRecommendationLevel(lvl);

		 tr.setWeatherDescription(wd);
 
		 travelrepo.save(tr);
		 
		 return "redirect:/admin/getTrNms";

	 }
	 
	 @PostMapping("/delTraReco")
	 public String delTraReco(  @RequestParam("outreid") int outreid ) {
		 
		 adminService.delTraReco(outreid);
		 
		 return "redirect:/admin/getTrNms";

	 }
}
