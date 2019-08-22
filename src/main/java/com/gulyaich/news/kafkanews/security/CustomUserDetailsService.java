package com.gulyaich.news.kafkanews.security;


import com.gulyaich.news.kafkanews.dao.UserRepository;
import com.gulyaich.news.kafkanews.exception.UserNotFoundException;
import com.gulyaich.news.kafkanews.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String loginOrEmail)
            throws UsernameNotFoundException {

        User user = userRepository.findByLoginOrEmail(loginOrEmail, loginOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Не удается найти пользователя ни по логину, ни по почте : " + loginOrEmail)
        );

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new UserNotFoundException(id)
        );

        return UserPrincipal.create(user);
    }
}