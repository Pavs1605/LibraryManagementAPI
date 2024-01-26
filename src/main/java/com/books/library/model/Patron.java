package com.books.library.model;

import jakarta.persistence.*;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
@Entity
@Table(name = "patrons")
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patronId")
    Long patronId;
    String name;
    @Pattern(regexp = "^\\+?[0-9]+$", message = "Invalid phone number format")
    String contactNo;

    @Email(message = "Invalid email address")
    String emailAddress;

    String address;
    public Patron(){

    }
    public Patron(String name, String contactNo, String emailAddress, String address) {
        this.name = name;
        this.contactNo = contactNo;
        this.emailAddress = emailAddress;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPatronId() {
        return patronId;
    }

    public void setPatronId(Long patronId) {
        this.patronId = patronId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

}
