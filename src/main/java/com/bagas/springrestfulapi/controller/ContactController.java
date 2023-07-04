package com.bagas.springrestfulapi.controller;

import com.bagas.springrestfulapi.entity.User;
import com.bagas.springrestfulapi.model.ContactResponse;
import com.bagas.springrestfulapi.model.CreateContactRequest;
import com.bagas.springrestfulapi.model.UpdateContactRequest;
import com.bagas.springrestfulapi.model.WebResponse;
import com.bagas.springrestfulapi.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
}
