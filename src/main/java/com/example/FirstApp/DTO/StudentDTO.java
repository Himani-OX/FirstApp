package com.example.FirstApp.DTO;


import com.example.FirstApp.entity.Address;
import com.example.FirstApp.entity.Guardian;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private String name;
    private String emailId;
    private Guardian guardian;
    private List<Address> addresses;
    private List<Long> courseIds;
}
