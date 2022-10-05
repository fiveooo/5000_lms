package com.gd.lms.service;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gd.lms.commons.TeamColor;
import com.gd.lms.mapper.MypageMapper;
import com.gd.lms.vo.Paging;
import com.gd.lms.vo.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class MypageService implements IMypageService {
	
	@Autowired MypageMapper mypageMapper;
	
	//마이페이지 로그인정보
	@Override
	public Map<String, Object> getUserInfo(String userId, int userLevel) {
		Map<String, Object> userInfo = null;
		
		//계층별 조회할 유저 분기
		switch(userLevel) {
		case 1 : userInfo = mypageMapper.selectAdminInfo(userId);break;
		case 2 : userInfo = mypageMapper.selectProfessorInfo(userId);break;
		case 3 : userInfo = mypageMapper.selectStudentInfo(userId);break;
		case 4 : userInfo = mypageMapper.selectSystemInfo(userId);break;
		}
		return userInfo;
	}

	@Override
	public int modifyUserInfo(Map<String, Object> map) {
		int row = -1;
		int levelRow = -1;
		
		String userId = (String)map.get("userId");
		String userInfo = (String)map.get("userInfo");
		int userLevel = (int)map.get("userLevel");
		
		System.out.println(TeamColor.AJH+"파라미터 레벨 확인 :" + userLevel);
		
		//정보변경하는 객체 이메일 전화번호 분기
		if( ((String)map.get("userInfo")).contains("@")) {
			System.out.println(TeamColor.AJH+"파라미터 형식 이메일");
			
			//통합 계정 이메일 변경
			row = mypageMapper.modifyUserEmail(userInfo, userId);
			
			// 레벨에 따른 계정 이메일 변경
			switch(userLevel) {
				case 1 : levelRow = mypageMapper.modifyAdminEmail(userInfo, userId);break;
				case 2 : levelRow = mypageMapper.modifyProEmail(userInfo, userId);break;
				case 3 : levelRow = mypageMapper.modifyStudentEmail(userInfo, userId);break;
			}
		} else {
			System.out.println(TeamColor.AJH+"파라미터 형식 번호");
			
			//통합 계정 번호 변경
			row = mypageMapper.modifyUserTel(userInfo, userId);
			
			//레벨에 따른 계정 번호 변경
			switch(userLevel) {
				case 1 : levelRow = mypageMapper.modifyAdminTel(userInfo, userId);break;
				case 2 : levelRow = mypageMapper.modifyProTel(userInfo, userId);break;
				case 3 : levelRow = mypageMapper.modifyStudentTel(userInfo, userId);break;
			}
		}
		
		if(row == 1 && levelRow == 1) {
			System.out.println(TeamColor.AJH+"정보 변경 성공 : " + row);
			System.out.println(TeamColor.AJH+"계층에따른 정보 변경 성공 : " + levelRow);
		}
		
		return row;
	}
	
	@Override
	public User getPwCheck(User user) {
		log.debug("컨트롤러 에서 넘어온값 확인 : " + user);
		return mypageMapper.selectPwCheck(user);
	}
	@Override
	public int modifyUserPw(User user) {
		return mypageMapper.modifyUserPw(user);
	}
	
	//게시글 리스트
	@Override
	public List<Map<String, Object>> getboardWriteList(int userLevel, Paging paging) {
		
		List<Map<String, Object>> row = null;
		
		paging.setBeginRow((paging.getNowPage()-1)*paging.getRowPerPage());
		log.debug(TeamColor.AJH + "서비스단 파라미터 beginRow : " + paging.getBeginRow());
		
		switch(userLevel) {
			case 1 : row = mypageMapper.selectAdminBoardList(paging);break;
			case 2 :
			case 3 : row = mypageMapper.selectboardWriteList(paging);break;
		}
		return row;
	}
	
	//학생,교수 게시글 카운트 마지막페이지 구하기
	@Override
	public Paging getPostCount(int userLevel,Paging paging) {
		
		int total = 0;
		
		switch(userLevel) {
			case 1 : total = mypageMapper.selectNoticeCount(paging.getUserId());break;
			case 2 : 
			case 3 : total = mypageMapper.selectBoardCount(paging.getUserId());break;
		}
		paging.setTotal(total);
		paging.setEndPage((int)Math.ceil(paging.getTotal()/(double)paging.getRowPerPage()));
		
		
		return paging;
	}
	
	//댓글리스트
	@Override
	public List<Map<String, Object>> getCommentWriteList(String userId, int nowPage, int rowPerPage) {
		List<Map<String, Object>> row = null;
		
		row = mypageMapper.selectCommentWriteList(userId);
		return row;
	}

}
