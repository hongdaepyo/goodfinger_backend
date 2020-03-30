package com.backend.goodfinger.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
//Todo SpringBootTest 는 통테? 단위테스트는 어떤걸로하나
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

}
