package com.example.FirstApp.entity;


import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  //create constructor for final fields only
@AllArgsConstructor
@NoArgsConstructor
@Embeddable  //don't create table for this entity
public class Guardian {
    private String name;
    private String email;
    private String mobile;

}
