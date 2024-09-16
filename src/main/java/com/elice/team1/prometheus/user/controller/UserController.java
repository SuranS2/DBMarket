package com.elice.team1.prometheus.user.controller;

import com.elice.team1.prometheus.user.entity.User;
import com.elice.team1.prometheus.user.dto.UserDto;
import com.elice.team1.prometheus.config.UserFilter;
import com.elice.team1.prometheus.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final UserFilter userFilter;

    private User userForRedirect;

    // 첫 홈 화면
    @GetMapping("/home")
    public String welcomeHome(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            log.info("로그인 세션 존재 - categories로 이동");
            return "redirect:/categories";
        } else {
           userService.isSessionAvailable(model);
            return "home";
        }
    }

    @GetMapping("/users/join")
    public String joinForm(Model model) {
        log.info("회원가입 페이지 요청 접수");
        userService.isSessionAvailable(model);
        return "users/join";
    }

    @PostMapping("/users/join")
    public String CreateUser(UserDto form, @RequestParam("password_check") String password_check, HttpServletRequest httpServletRequest, Model model) {
        log.info("회원가입 요청 접수");
        userService.isSessionAvailable(model);
        return userService.createUser(form, password_check, httpServletRequest);
    }

    @GetMapping("/users/login")
    public String login(Model model) {
        userService.isSessionAvailable(model);
        return "users/login";
    }

    @PostMapping("/users/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest httpServletRequest,Model model) {
        userService.isSessionAvailable(model);
        return userService.login(username, password, httpServletRequest);
    }

    @GetMapping("/users/logout")
    public String logout(HttpServletRequest httpServletRequest, Model model) {
        userService.isSessionAvailable(model);
        return userService.logout(httpServletRequest, model);
    }

    // 회원 상세 페이지
    @GetMapping("/users/{userid}")
    public String profile(@PathVariable Long userid, Model model) {
        userForRedirect = userService.findUserById(userid);
        return "redirect:/users/profile";

    }

    @GetMapping("/users/profile")
    public String profile(Model model) {
        User user = userForRedirect;
        model.addAttribute("userid", user.getUserId());
        model.addAttribute("username", user.getUserName());

        userService.getItemsByUserId(user.getUserId(), model);
        userForRedirect = null;
        return "users/profile";
    }

    // 마이페이지
    @GetMapping("/users/mypage")
    public String mypage(Model model, HttpServletRequest httpServletRequest) {
        if(userFilter.findUserByFilter(model,httpServletRequest) == null) {
            return "redirect:/users/logout";
        }
        User user = userFilter.findUserByFilter(model,httpServletRequest);
        userService.getItemsByUserId(user.getUserId(), model);

        return "users/mypage";

    }

    @DeleteMapping("/users/delete")
    public String deleteUser(Model model, HttpServletRequest httpServletRequest) {
        User user = userFilter.findUserByFilter(model, httpServletRequest);
        userService.deleteUser(user.getUserId());

        return "redirect:/users/logout";
    }

    @GetMapping("/users/edit")
    public String editUser(Model model, HttpServletRequest httpServletRequest) {
        User user = userFilter.findUserByFilter(model,httpServletRequest);
        model.addAttribute("user", user);

        return "users/edit";
    }

    @PostMapping("/users/editName")
    public String editUserName(Model model, @RequestParam("username") String newUserName,HttpServletRequest request) {
        User user = userFilter.findUserByFilter(model, request);
        return userService.editUserName(user,newUserName,request);
    }

    @PostMapping("/users/editPassword")
    public String editUserPassword(Model model,@RequestParam("password") String newPassword, @RequestParam("password_check") String newPasswordCheck, HttpServletRequest request) {
        User user = userFilter.findUserByFilter(model, request);
        return userService.editUserPassword(user, newPassword, newPasswordCheck);
    }












}
