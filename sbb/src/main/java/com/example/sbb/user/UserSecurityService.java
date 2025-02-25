package com.example.sbb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
// 스프링 시큐리티가 제공하는 UserDetailsService 인터페이스를 구현해야 한다.
// UserDetailsService 는 loadUserByUsername 메서드를 구현하도록 강제하는 인터페이스
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    // loadUserByUsername 메서드는 사용자명(username)으로 스프링 시큐리티의 사용자(User) 객체를 조회하여 리턴하는 메서드
    // loadUserByUsername 메서드: 사용자명을 통해 사용자 정보를 일관되게 조회할 수 있도록 보장하고, 그 결과로 UserDetails 객체를 반환해야 한다.  
    // UserDetails 는 사용자 정ㅂ조를 담고 있는 핵심 객체
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // _siteUser : 지역 변수 (메서드 내부에서만 사용되며 클래스 필드가 아니기 때문에 구별을 위해 _ 추가)
        Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);
        if(_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        SiteUser siteUser = _siteUser.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        }else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}
