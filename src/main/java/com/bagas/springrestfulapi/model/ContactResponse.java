package com.bagas.springrestfulapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ContactResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
