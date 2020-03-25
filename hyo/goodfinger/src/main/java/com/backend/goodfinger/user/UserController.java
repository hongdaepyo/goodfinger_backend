package com.backend.goodfinger.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    UserRepository userRepository;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody User user) throws Exception {
        logger.debug("signUp started.");

        userRepository.insert(user);

        logger.debug("signUp ended.");

        return new ResponseEntity<>("user added successfully.", HttpStatus.OK);
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody User user) throws Exception {
        //TODO jwt 만들어서 넣어줘
        //TODO 암호화해야하는데 springsecurity 공부해
        return null;
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email) throws Exception {
        logger.debug("getUser started.");

        User user = userRepository.findUserByEmail(email);

        if (user == null) {
            return new ResponseEntity<>("user is not exist.", HttpStatus.NOT_FOUND);
        }

        logger.debug("getUser ended.");

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) throws Exception {
        //TODO 1.pathvariable , object 매핑
        //TODO 2.다른 delete 방식
        //둘중하나 찾아서 ㄱ
        logger.debug("deleteUser started.");

        User user = new User();
        user.setEmail(email);

        userRepository.delete(user);

        logger.debug("deleteUser ended.");

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
