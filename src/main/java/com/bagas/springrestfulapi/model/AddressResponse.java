package com.bagas.springrestfulapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddressResponse {

    private String id;
    private String street;
    private String city;
    private String province;
    private String country;
    private String postalCode;

}
