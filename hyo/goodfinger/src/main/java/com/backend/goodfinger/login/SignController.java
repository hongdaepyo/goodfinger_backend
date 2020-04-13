package com.backend.goodfinger.login;

import com.backend.goodfinger.auth.JwtTokenProvider;
import com.backend.goodfinger.user.User;
import com.backend.goodfinger.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SignController {
    private Logger logger = LoggerFactory.getLogger(SignController.class);

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
        //TODO role은??
        User member = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return new ResponseEntity<>(jwtTokenProvider.createToken(member.getEmail(), member.getRoles()), HttpStatus.OK);
    }
}
