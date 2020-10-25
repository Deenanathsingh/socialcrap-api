package com.socialcrap.api.model.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressPojo {
	private String houseNumber;
	private String street;
	private String area;
	private String city;
	private String state;
	private String pinCode;
	private String country;
}
