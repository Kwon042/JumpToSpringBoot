package com.example.sbb.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
// /user/signup Url 이 Get 으로 요청되면 회원 가입을 위한 템플릿을 렌더링하고, POST 로 요청되면 회원가입을 진행하도록
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "signup_form";
        }
        // bingingResult.rejectValue 를 사용 >> 입력 받은 2개의 비밀번호가 일치하지 않는다는 오류가 발생하게
        // bingingResult.rejectValue(필드명, 오류 코드, 오류 메시지)
        // 오류 코드: 임의로 passwordInCorrect (대형 프로젝트에서는 번역과 관리를 위해 오류 코드를 잘 정의하여 사용해야 함)
        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }
        try {
            // 사용자로부터 전달받은 데이터를 저장
            userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
        }catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            // bindingResult.reject(오류 코드, 오류 메세지)
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        }catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }
}
