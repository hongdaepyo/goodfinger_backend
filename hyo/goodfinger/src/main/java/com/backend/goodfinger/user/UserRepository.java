package com.backend.goodfinger.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<User, Object> {
    User findUserByEmail(String email);
}
