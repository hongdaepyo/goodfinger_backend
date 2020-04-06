package com.backend.goodfinger.user;

import com.backend.goodfinger.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
//@CrossOrigin
@RequestMapping("/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody User user) throws Exception {
        logger.debug("signUp started.");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.insert(user);

        logger.debug("signUp ended.");

        return new ResponseEntity<>("user added successfully.", HttpStatus.OK);
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody User user) throws Exception {
        /*User member = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return new ResponseEntity<>(jwtTokenProvider.createToken(member.getEmail(), member.getRoles()), HttpStatus.OK);*/
        return null;
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email) throws Exception {
        logger.debug("getUser started.");

        Optional<User> user = userRepository.findByEmail(email);

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
