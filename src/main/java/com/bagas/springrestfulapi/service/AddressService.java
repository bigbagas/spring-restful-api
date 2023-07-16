package com.bagas.springrestfulapi.service;

import com.bagas.springrestfulapi.entity.Address;
import com.bagas.springrestfulapi.entity.Contact;
import com.bagas.springrestfulapi.entity.User;
import com.bagas.springrestfulapi.model.AddressResponse;
import com.bagas.springrestfulapi.model.CreateAddressRequest;
import com.bagas.springrestfulapi.repository.AddressRepository;
import com.bagas.springrestfulapi.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ValidationService validationService;

    public AddressResponse createAddress (User user,CreateAddressRequest request){
        validationService.validate(request);

        Contact contact = contactRepository.findFirstByUserAndId(user,request.getContactId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Contact Not Found"));

        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setProvince(request.getProvince());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());
        address.setContact(contact);

        addressRepository.save(address);

        return toAddressResponse(address);
    }

    public AddressResponse toAddressResponse (Address address){

        return AddressResponse.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .province(address.getProvince())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .build();
    }
}
