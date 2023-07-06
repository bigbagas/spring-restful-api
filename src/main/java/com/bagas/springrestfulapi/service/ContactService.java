package com.bagas.springrestfulapi.service;

import com.bagas.springrestfulapi.entity.Contact;
import com.bagas.springrestfulapi.entity.User;
import com.bagas.springrestfulapi.model.ContactResponse;
import com.bagas.springrestfulapi.model.CreateContactRequest;
import com.bagas.springrestfulapi.model.SearchContactRequest;
import com.bagas.springrestfulapi.model.UpdateContactRequest;
import com.bagas.springrestfulapi.repository.ContactRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public ContactResponse create(User user, CreateContactRequest request){
        validationService.validate(request);

        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());

        contactRepository.save(contact);

        return toContactResponse(contact);

    }

    private ContactResponse toContactResponse(Contact contact){
        return ContactResponse.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .build();
    }

    @Transactional
    public ContactResponse update(User user, UpdateContactRequest request){
        validationService.validate(request);

        Contact contact = contactRepository.findFirstByUserAndId(user, request.getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Contact Not Found"));

        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contactRepository.save(contact);

        return toContactResponse(contact);
    }

    @Transactional(readOnly = true)
    public ContactResponse get(User user, String id){
        Contact contact = contactRepository.findFirstByUserAndId(user, id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Contact Not Found"));

        return toContactResponse(contact);
    }


    @Transactional(readOnly = true)
    public Page<ContactResponse> search(User user, SearchContactRequest request){
        Specification<Contact>specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("user"),user));

            if (Objects.nonNull(request.getName())){
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("firstName"),"%"+request.getName()+"%"),
                        criteriaBuilder.like(root.get("lastName"),"%"+request.getName()+"%")
                ));
            }

            if (Objects.nonNull(request.getPhone())){
                predicates.add(criteriaBuilder.like(root.get("phone"),"%"+request.getPhone()+"%"));
            }

            if (Objects.nonNull(request.getEmail())){
                predicates.add(criteriaBuilder.like(root.get("email"),"%"+request.getEmail()+"%"));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Contact>contacts = contactRepository.findAll(specification,pageable);

        List<ContactResponse> contactResponses = contacts.getContent().stream()
                .map(this::toContactResponse)
                .toList();

        return new PageImpl<>(contactResponses,pageable, contacts.getTotalElements());
    }

    @Transactional
    public void delete(User user, String contactid){
        Contact contact = contactRepository.findFirstByUserAndId(user,contactid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "CONTACT NOT FOUND"));

        contactRepository.delete(contact);
    }
}
