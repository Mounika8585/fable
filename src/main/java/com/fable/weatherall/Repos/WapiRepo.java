package com.fable.weatherall.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.fable.weatherall.Admin_User_Entities.WeatherApi;

@EnableJpaRepositories
@Repository
public interface WapiRepo extends JpaRepository<WeatherApi, Long>{
	
    List<WeatherApi> findAll();

	
    WeatherApi findById(long id);

}
