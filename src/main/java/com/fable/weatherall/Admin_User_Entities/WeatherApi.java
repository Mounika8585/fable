package com.fable.weatherall.Admin_User_Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="weatherapi", uniqueConstraints = {@UniqueConstraint(columnNames = "wapi_key")})
public class WeatherApi {


	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "wapi_key", length = 40, nullable = false)
	    private String wapikey;

	    @Column(name = "wapi_url", length = 75, nullable = false)
	    private String wapiurl;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getWapikey() {
			return wapikey;
		}

		public void setWapikey(String wapikey) {
			this.wapikey = wapikey;
		}

		public String getWapiurl() {
			return wapiurl;
		}

		public void setWapiurl(String wapiurl) {
			this.wapiurl = wapiurl;
		}


}
