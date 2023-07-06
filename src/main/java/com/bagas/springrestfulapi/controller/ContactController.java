package com.bagas.springrestfulapi.controller;

import com.bagas.springrestfulapi.entity.User;
import com.bagas.springrestfulapi.model.*;
import com.bagas.springrestfulapi.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactController {

    @Autowired
    ContactService contactService;

    @PostMapping(
            path = "api/contacts",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> create(User user, @RequestBody CreateContactRequest request){

        ContactResponse contactResponse = contactService.create(user,request);

        return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }

    @PutMapping(
            path = "api/contacts/{idContacts}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> update(User user, @RequestBody UpdateContactRequest request, @PathVariable String idContacts){

        request.setId(idContacts);

        ContactResponse contactResponse = contactService.update(user,request);

        return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }

    @GetMapping(
            path = "api/contacts/{idContacts}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> get(User user, @PathVariable String idContacts){


        ContactResponse contactResponse = contactService.get(user,idContacts);

        return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }

    @GetMapping(
            path = "api/contacts",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ContactResponse>> search(User user,
                                                     @RequestParam(value = "name",required = false)String name,
                                                     @RequestParam(value = "phone",required = false)String phone,
                                                     @RequestParam(value = "email",required = false)String email,
                                                     @RequestParam(value = "page",required = false,defaultValue = "0")Integer page,
                                                     @RequestParam(value = "size",required = false, defaultValue = "10")Integer size){
        SearchContactRequest request = SearchContactRequest.builder()
                .name(name)
                .phone(phone)
                .email(email)
                .page(page)
                .size(size)
                .build();

        Page<ContactResponse>contactResponses = contactService.search(user, request);

        return WebResponse.<List<ContactResponse>>builder()
                .data(contactResponses.getContent())
                .pagingResponse(PagingResponse.builder()
                        .currentPage(contactResponses.getNumber())
                        .totalPage(contactResponses.getTotalPages())
                        .size(contactResponses.getSize())
                        .build())
                .build();
    }

    @DeleteMapping(
            path = "api/contacts/{idContacts}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String > delete(User user, @PathVariable String idContacts){


        contactService.delete(user,idContacts);

        return WebResponse.<String >builder().data("OK").build();
    }



}
