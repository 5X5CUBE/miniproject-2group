package com.springbootstudy.bbs.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springbootstudy.bbs.domain.Users;
import com.springbootstudy.bbs.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;



@Controller
@Slf4j
public class UsersController {
	
	 private final UserService userService;
	 
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
	 public String login(Model model, @RequestParam("userId") String id,@RequestParam("password") String pass,HttpSession session, HttpServletResponse response)
	 throws ServletException, IOException {

	 // MemberService 클래스를 사용해 로그인 성공여부 확인
		 int result = userService.login(id, pass);
		 if(result == -1) { // 회원 아이디가 존재하지 않으면
			 response.setContentType("text/html; charset=utf-8");
			 PrintWriter out = response.getWriter();
			 out.println("<script>");
			 out.println(" alert('존재하지 않는 아이디 입니다.');");
			 out.println(" history.back();");
			 out.println("</script>");
			 return null;
		 } else if(result == 0) { // 비밀번호가 틀리면
			 response.setContentType("text/html; charset=utf-8");
			 PrintWriter out = response.getWriter();
			 out.println("<script>");
			 out.println(" alert('비밀번호가 다릅니다.');");
			 out.println(" location.href='loginForm'");
			 out.println("</script>");
			 return null;
		 }
		 Users user = userService.getMember(id);
		 session.setAttribute("isLogin", true);
		 model.addAttribute("user", user);
		 UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
		            user.getUserId(), 
		            null, 
		            List.of(new SimpleGrantedAuthority("ROLE_USER")));

		    
		    SecurityContext context = SecurityContextHolder.createEmptyContext();
		    context.setAuthentication(token);
		    
		    SecurityContextHolder.setContext(context);

		    session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
		 return "redirect:/mainhome";
	 }
	 
	 @Autowired
	    public UsersController(UserService userService) {
	        this.userService = userService;
	    }
	 
	 
	 // AJAX 요청을 받는 메서드임시
	    @GetMapping("/idCheck")
	    @ResponseBody
	    public boolean idCheck(@RequestParam("userId") String userId) {
	        
	        boolean result = userService.checkId(userId);
	        
	        System.out.println("아이디 중복 체크 요청: " + userId + " / 결과: " + result); 
	        
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
