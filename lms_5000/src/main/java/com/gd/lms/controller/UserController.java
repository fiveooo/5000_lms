package com.gd.lms.controller;


import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gd.lms.commons.TeamColor;
import com.gd.lms.service.IUserLoginService;
import com.gd.lms.vo.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
	
	@Autowired IUserLoginService userLoginService;
	
	//메인페이지
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	//로그인 페이지
	@GetMapping("/index/login")
	public String userLogin() {
		
		return "user/login";
	}
	
	//로그인 액션
	@PostMapping("/index/login")
	public String userLogin(Model model, HttpSession session, User user) {
		
		//디버깅
		log.debug(TeamColor.AJH + user + "로그인한 매개변수 확인");
		
		//입력한 id ,pw로 서비스 호출
		User loginUser = userLoginService.getUserLogin(user);
		
		//디버깅
		log.debug(TeamColor.AJH + loginUser + " db 정보유무확인");
		
		// id pw가 user정보에 없다면(로그인실패)시 실패메세지 알림
		if(loginUser == null) {
			model.addAttribute("errMsg","로그인 정보를 다시 확인해주세요");
			return "user/login";
		}
		// 유저 정보는 있지만 승인대기상태(N) 일 떄 메세지출력
		if("N".equals(loginUser.getUserActive())) {
			model.addAttribute("errMsg","승인 대기 상태입니다");
			return "user/login";
		}
		//마지막 로그인 날짜 업데이트
		userLoginService.modifyUserLastLogin(user.getUserId());
		
		// user 정보가 일치하고 계정 활성화(Y)인 계정에 세션부여
		session.setAttribute("loginUser", loginUser);
		log.debug(TeamColor.AJH + "세션에 저장된 로그인 유저 정보" + loginUser);
		
		return "redirect:/index";
	}
	
	
	//로그아웃
	@GetMapping("/index/logout")			
	public String logout(HttpSession session) {			
		log.debug(TeamColor.AJH + " 로그아웃 액션");
		
		// 세션 무효화			
		session.invalidate();			
					
		return "redirect:/index";			
	}
	
	// 운영자 가입 폼으로가기
	@GetMapping("/user/addAdmin")
	public String addUser() {
		return "user/addAdmin";
	}
	
	// 운영자 가입 액션
	@PostMapping("/user/addAdmin")
	public String addUser(Model model, User user, @RequestParam (value="positionNo") int positionNo) {
		
		log.debug(TeamColor.JCH + " addUser 파라미터 값 "+ user);
		
		//addUser 회원가입 페이지에서 입력한정보가 성공적으로 들어갔을 떄 로그인 폼, 확인메세지 출력
		if(userLoginService.addAdmin(user, positionNo)) {
			model.addAttribute("errMsg","회원가입 완료되었습니다");
			System.out.println("회원가입완료");
		}
		//회원가입 로직중 에러발생시 404페이지로 이동 추가 할 예정
		
		return "user/login";
	}
	
	// 학생,교수 가입 폼으로가기
	@GetMapping("/user/addUser")
	public String addStudent(Model model) {
		//학과 리스트 받아서 포워딩해야함
		return "user/addUser";
	}
	
	// 학생,교수 가입 액션
	@PostMapping("/user/addUser")
	public String addStudent(Model model, User user , @RequestParam (value="majorNo") int majorNo) {
		log.debug(TeamColor.AJH+"Student / Professro 파라미터 user 값 "+ user);
		log.debug(TeamColor.AJH+"Student / Professro 파라미터 majorNo 값 "+ majorNo);
		if(userLoginService.addStudentOrPro(user, majorNo)) {
			log.debug(TeamColor.AJH + "학생/교수 회원가입 성공");
			model.addAttribute("errMsg","회원가입 완료되었습니다");
		}
		return "user/login";
	}
	
	
	//회원가입시 아이디 중복체크
	@PostMapping("/user/idCheck")
	@ResponseBody
	public String userIdCkeck(@RequestParam (value="userId") String userId) {
		System.out.println(TeamColor.AJH + "id check 진입 "+ userId);
		if(userLoginService.getUserIdCheck(userId)) {
			return "true";
		}
		else {
			return "false";
		}
		
	}
	@GetMapping("/user/messageList")
	public String messageList() {
		return "user/messageList";
	}
	@GetMapping("/user/message")
	public String message() {
		return "user/message";
	}
		
}
