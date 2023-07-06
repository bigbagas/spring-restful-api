package com.bagas.springrestfulapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SearchContactRequest {

    private String name;
    private String email;
    private String phone;

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;

}
