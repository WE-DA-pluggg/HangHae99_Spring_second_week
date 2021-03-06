package com.sparta.myblog.controller;

import com.sparta.myblog.domain.SignupRequestDto;
import com.sparta.myblog.domain.UserInfoDto;
import com.sparta.myblog.model.User;
import com.sparta.myblog.repository.UserRepository;
import com.sparta.myblog.security.UserDetailsImpl;
import com.sparta.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Controller
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;


    @Autowired // DI 디펜던시 인젝션
    public UserController(UserService userService,UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // 회원 로그인 페이지
    @GetMapping("/user/loginView")//동적 리스폰스로 프론트에서 타임리프로 받는다는 가정
    public String login(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception, Model model,@AuthenticationPrincipal UserDetailsImpl userDetails ) {
        if(error != null && exception!=null){
            model.addAttribute("error", error);
            model.addAttribute("exception", exception);
        }

        return "login";
    }

    // 회원 관련 정보 받기
    @PostMapping("/user/userinfo")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();

        return new UserInfoDto(username);
    }


    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";//회원가입페이지
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(SignupRequestDto requestDto, Model model) {
        System.out.println("good");
        String finished = userService.registerUser(requestDto);
        model.addAttribute("회원가입 결과",finished);
        Optional<User> found = userRepository.findByUsername(requestDto.getUsername());
        if (Objects.requireNonNull(model.getAttribute("회원가입 결과")).equals("성공입니다.")) {
            return "redirect:/user/loginView";//로그인 페이지로 리다이렉트
        }
        return "signup";
    }



}

