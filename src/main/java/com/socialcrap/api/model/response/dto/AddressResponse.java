package com.socialcrap.api.model.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.socialcrap.api.model.pojo.AddressPojo;

@JsonInclude(Include.NON_NULL)
public class AddressResponse extends AddressPojo {
}
