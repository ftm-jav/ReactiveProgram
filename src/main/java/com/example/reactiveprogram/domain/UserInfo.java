package com.example.reactiveprogram.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class UserInfo {

    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
}
