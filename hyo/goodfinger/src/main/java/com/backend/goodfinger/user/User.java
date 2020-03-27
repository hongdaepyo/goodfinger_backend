package com.backend.goodfinger.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User")
@Getter
@Setter
@ToString
public class User {
    private String id;
    private String email;
    private String password;
    private String name;
    private String birth;
    private String sex;
    /**
     * true: Boss , false: applicant
     */
    private boolean isBoss;


}
