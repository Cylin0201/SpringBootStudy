package com.backend.ureca.cylin0201.startspring.member.controller;

import com.backend.ureca.cylin0201.startspring.member.service.MyPageService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

    @Controller
    @RequiredArgsConstructor
    public class MyPageController {
        private final MyPageService myPageService;

        @GetMapping("/mypage")
        public String getMyPage(@RequestParam(value = "membername", required = false) String memberName, HttpSession session, Model model){
            String loginMemberName = (String) session.getAttribute("memberName");
            if (loginMemberName == null) {
                // 세션에 없으면 로그인 페이지로 리다이렉트
                return "redirect:/login";
            }
            model.addAttribute("loginMemberName", loginMemberName);

            // 2. 조회할 마이페이지 지정 (없으면 내 페이지)
            String targetMemberName = memberName != null ? memberName : loginMemberName;

            // 3. DTO 조회 후 뷰에 전달
            model.addAttribute("myPage", myPageService.getMyPage(targetMemberName));

            return "mypage";
        }
    }
