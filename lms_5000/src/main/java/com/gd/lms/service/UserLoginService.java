package com.gd.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gd.lms.commons.TeamColor;
import com.gd.lms.mapper.UserLoginMapper;
import com.gd.lms.vo.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class UserLoginService implements IUserLoginService {

	@Autowired UserLoginMapper userLoginMapper;
	
	@Override
	public User getUserLogin(User user) {
		User temp = userLoginMapper.selectUserLogin(user);
		
		return temp;
	}

	@Override
	public boolean addUser(User user) {
		boolean result = false;
		int row = userLoginMapper.insertUser(user);
		log.debug(TeamColor.AJH + row + " insert 결과");
		if(row == 1) {
			result = true;
		}
		return result;
	}

	@Override
	public boolean getUserIdCheck(String userId) {
		boolean result = false;
		int row = userLoginMapper.selectUserIdCheck(userId);
		//userId 와 같은 id 개수가 0개라면 (중복 된 아이디가 없다면)
		if( row == 0) {
			result = true;	//결과는 참
		}
		return result;
	}

}
