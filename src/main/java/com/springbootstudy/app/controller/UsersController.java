package com.springbootstudy.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springbootstudy.app.domain.Users;
import com.springbootstudy.app.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;



@Controller
@Slf4j
public class UsersController {
	
	 private final UserService userService;
	 
	 private final PasswordEncoder passwordEncoder;
	 
	 @PostMapping("/checkUserForReset")
	 @ResponseBody
	 public String checkUserForReset(@RequestParam("loginId") String loginId, 
	                                 @RequestParam("phone") String phone,
	                                 HttpSession session) {
		 
	     boolean exists = userService.checkUserByPhone(loginId, phone);
	     
	     if(exists) {

	         session.setAttribute("resetTargetId", loginId);
	         session.setMaxInactiveInterval(180); 
	         return "success";
	     }
	     return "fail";
	 }

	 @PostMapping("/resetPassword")
	 @ResponseBody
	 public String resetPassword(@RequestParam("password") String password, HttpSession session) {

	     String loginId = (String) session.getAttribute("resetTargetId");
	     
	     if(loginId == null) {
	         return "fail"; 
	     }
	     
	     userService.resetPassword(loginId, passwordEncoder.encode(password));
	     
	     session.removeAttribute("resetTargetId");
	     
	     return "success";
	 }
	 
	 
	 @PostMapping("/findId")
	 @ResponseBody
	 public String findId(@RequestParam("username") String username, 
	                      @RequestParam("phone") String phone) {
	     
	     String loginId = userService.findLoginId(username, phone);
	     
	     if(loginId == null) {
	         return "fail"; 
	     }
	     
	     return loginId; 
	 }
	 
	 
	 @PostMapping("/userDelete")
	 @ResponseBody
	 public String userDelete(@RequestParam("password") String password, HttpSession session) {
	     Users user = (Users) session.getAttribute("user");
	     if(user == null) return "fail";

	     if(!passwordEncoder.matches(password, user.getPassword())) {
	         return "wrong"; 
	     }

	     userService.deleteUser(user.getLoginId());
	     session.invalidate(); 

	     return "success";
	 }
	 
	 @Autowired
	    public UsersController(UserService userService, PasswordEncoder passwordEncoder) {
	        this.userService = userService;
	        this.passwordEncoder = passwordEncoder;
	    }
	 
	 @PostMapping("/userPhoneUpdate")
	 @ResponseBody
	 public String userPhoneUpdate(@RequestParam("phone") String phone, HttpSession session) {
	     Users user = (Users) session.getAttribute("user");
	     if(user == null) return "fail";
	     
	     user.setPhone(phone);
	     userService.updatePhone(user);
	     session.setAttribute("user", user); 
	     return "success";
	 }

	 
	 @PostMapping("/userPassUpdate")
	 @ResponseBody
	 public String userPassUpdate(@RequestParam("currentPass") String currentPass,
	                              @RequestParam("newPass") String newPass,
	                              HttpSession session) {
	     Users user = (Users) session.getAttribute("user");
	     if(user == null) return "fail";

	     if(!passwordEncoder.matches(currentPass, user.getPassword())) {
	         return "wrong"; 
	     }

	     user.setPassword(passwordEncoder.encode(newPass));
	     userService.updatePassword(user);
	     
	     return "success";
	 }

	 @PostMapping("/userAddressUpdate")
	 @ResponseBody
	 public String userAddressUpdate(@RequestParam("zipcode") String zipcode,
	                                 @RequestParam("addr1") String addr1,
	                                 @RequestParam("addr2") String addr2,
	                                 HttpSession session) {
	     Users user = (Users) session.getAttribute("user");
	     if(user == null) return "fail";
	     
	     String fullAddress = zipcode + "#" + addr1 + "#" + addr2;
	     
	     user.setAddress(fullAddress);
	     userService.updateAddress(user);
	     session.setAttribute("user", user); 
	     return "success";
	 }
	 
	 
	 @PostMapping("/userNicknameUpdate")
	 @ResponseBody
	 public String userNicknameUpdate(@RequestParam("nickname") String nickname, HttpSession session) {
	     
	     Users user = (Users) session.getAttribute("user");
	     
	     if (user != null) {
	         
	         user.setNickname(nickname);
	         userService.updateNickname(user); 
	         
	         
	         session.setAttribute("user", user);
	         
	         return "success";
	     }
	     
	     return "fail";
	 }
	 
	 
	 @GetMapping("/userUpdate")
	 public String updateForm(Model model, HttpSession session) {
	
	 return "view/mypage";
	 }
	 
	 @GetMapping("/nickCheck")
	 @ResponseBody
	 public boolean nickCheck(@RequestParam("nickname") String nickname) {
	     return userService.checkNickname(nickname);
	 }
	 
	 @GetMapping("/userLogout")
	 public String logout(HttpSession session) {
	 log.info("MemberController.logout(HttpSession session)");
	 session.invalidate();
	 return "redirect:/mainhome";
	 }
	 
	 @PostMapping("/userlogin")
	 @ResponseBody
	 public int login(Model model, @RequestParam("loginId") String id,@RequestParam("password") String pass,HttpSession session, HttpServletResponse response)
	 throws ServletException, IOException {

	 // MemberService 클래스를 사용해 로그인 성공여부 확인
		 int result = userService.login(id, pass);
		 System.out.println("컨트롤러 로그인 결과: " + result);
		 if (result == 1) {
		        Users user = userService.getMember(id);
		        session.setAttribute("isLogin", true);
		        session.setAttribute("user", user);

		        // 시큐리티 연동
		        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
		                user.getLoginId(), null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
		        SecurityContext context = SecurityContextHolder.createEmptyContext();
		        context.setAuthentication(token);
		        SecurityContextHolder.setContext(context);
		        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
		    }
		
		 return result; 
	 }
	 
	 
	 
	 
	 // AJAX 요청을 받는 메서드임시
	    @GetMapping("/idCheck")
	    @ResponseBody
	    public boolean idCheck(@RequestParam("loginId") String loginId) {
	        
	        boolean result = userService.checkId(loginId);
	        
	        System.out.println("아이디 중복 체크 요청: " + loginId + " / 결과: " + result); 
	        
	        return result; 
	    }
	
	 @PostMapping("/joinResult")
	    public String joinResult(Model model, Users user,
	            @RequestParam("pass1") String pass1, 
	            @RequestParam("mobile1") String mobile1, 
	            @RequestParam("mobile2") String mobile2, 
	            @RequestParam("mobile3") String mobile3,
	            

	            @RequestParam(value="zipcode", required=false, defaultValue="") String zipcode,
	            @RequestParam(value="addr1", required=false, defaultValue="") String addr1,
	            @RequestParam(value="addr2", required=false, defaultValue="") String addr2
	    ) {
	        

	        user.setPassword(pass1);

	        if(mobile2.isEmpty() || mobile3.isEmpty()) {
	            user.setPhone("");
	        } else {
	            user.setPhone(mobile1 + "-" + mobile2 + "-" + mobile3);
	        }

	        String fullAddress =  zipcode + "#" + addr1 + "#" + addr2;
	        user.setAddress(fullAddress.trim());

	        userService.addUser(user);
	        
	        return "redirect:login";
	    }
	
	
	
	@GetMapping("/usersjoin")
    public String joinPage() {
        return "views/usersJoinForm"; 
    }
	@GetMapping("/mypage")
    public String myPage() {
        return "views/mypageForm"; 
    }
	@GetMapping("/mypost")
    public String myPost() {
        return "views/mypostForm"; 
    }
	@GetMapping("/login")
    public String loginPage() {
        return "views/loginForm"; 
    }
	@GetMapping({"/" , "/mainhome"})
    public String home() {
        return "views/mainhome"; 
    }
}
