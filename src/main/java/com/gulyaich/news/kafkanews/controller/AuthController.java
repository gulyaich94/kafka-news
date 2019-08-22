package com.gulyaich.news.kafkanews.controller;

import com.gulyaich.news.kafkanews.dao.RoleRepository;
import com.gulyaich.news.kafkanews.dao.UserRepository;
import com.gulyaich.news.kafkanews.exception.CommonServerErrorException;
import com.gulyaich.news.kafkanews.model.user.Role;
import com.gulyaich.news.kafkanews.model.user.RoleType;
import com.gulyaich.news.kafkanews.model.user.User;
import com.gulyaich.news.kafkanews.model.web.request.LoginRequest;
import com.gulyaich.news.kafkanews.model.web.request.SignUpRequest;
import com.gulyaich.news.kafkanews.model.web.response.AuthorizedUserResponse;
import com.gulyaich.news.kafkanews.model.web.response.CommonResponse;
import com.gulyaich.news.kafkanews.model.web.response.JWTTokenResponse;
import com.gulyaich.news.kafkanews.security.JwtTokenUtils;
import com.gulyaich.news.kafkanews.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLoginOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenUtils.generateToken(authentication);

        AuthorizedUserResponse authorizedUserResponse = new AuthorizedUserResponse();
        UserPrincipal up = (UserPrincipal)authentication.getPrincipal();
        authorizedUserResponse.setFullname(up.getFullname());
        authorizedUserResponse.setEmail(up.getEmail());
        authorizedUserResponse.setLogin(up.getLogin());
        authorizedUserResponse.setJwtTokenResponse(new JWTTokenResponse(jwt));
        return ResponseEntity.ok(authorizedUserResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (StringUtils.isEmpty(signUpRequest.getLogin())) {
            return new ResponseEntity(new CommonResponse(false, "Необходимо заполнить логин пользователя"),
                    HttpStatus.BAD_REQUEST);
        }

        if (StringUtils.isEmpty(signUpRequest.getEmail())) {
            return new ResponseEntity(new CommonResponse(false, "Необходимо указать адрес электронной почты пользователя"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByLogin(signUpRequest.getLogin())) {
            return new ResponseEntity(new CommonResponse(false, "Такой логин пользователя уже используется"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new CommonResponse(false, "Такой почтовый адрес уже используется"),
                    HttpStatus.BAD_REQUEST);
        }

        // Создадим профиль нового пользователя
        User user = new User(signUpRequest.getLogin(), signUpRequest.getFullname(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // По умолчанию новый пользователь имеет роль обычного юзера (500 ошибка в ответ)
        Role userRole = roleRepository.findByType(RoleType.ROLE_USER)
                .orElseThrow(() -> new CommonServerErrorException("Не удалось установить роль для нового пользователя."));
        user.setRoles(Collections.singleton(userRole));

        // Сохраняем в БД
        userRepository.save(user);
        CommonResponse successResponce = new CommonResponse(true, "Пользователь успешно зарегистрирован");
        return ResponseEntity.ok(successResponce);
    }
}
