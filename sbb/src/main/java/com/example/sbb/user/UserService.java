package com.example.sbb.user;

import com.example.sbb.question.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    // principal 객체를 사용하면 이제 로그인한 사용자명을 알 수 있으므로 사용자명으로 SiteUser 객체를 조회 가능
    // getUser() 메서드 : SiteUser 를 조회할 수 있는 메서드
    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if(siteUser.isPresent()) {
            return siteUser.get();
        }else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

}
