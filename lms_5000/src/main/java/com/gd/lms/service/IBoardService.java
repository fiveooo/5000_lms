package com.gd.lms.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.gd.lms.vo.Board;
import com.gd.lms.vo.BoardFile;
import com.gd.lms.vo.BoardPost;
import com.gd.lms.vo.Comment;

public interface IBoardService {
	//게시판 리스트 생성 서비스
	public List<Board> getBoardList(int lectureNo);
	
	
	//게시판 추가 쿼리
	public int addBoard(Board board);
	
	
	//선택 게시판의 게시글 리스트 생성 서비스
	public List<BoardPost> getBoardPostList(int boardNo);

	
	//게시글의 상세 조회 서비스
	public Map<String, Object> getBoardPostOne(int boardPostNo);
	
	
	//게시글 추가 서비스
	public int addBoardPostandFile(BoardPost boardPost, MultipartFile[] uploadFile,HttpServletRequest request);
		
	//댓글 추가 서비스
	public int addComment(Comment comment);
	
	//댓글 리스트 조회 서비스
	public List<Comment> getComment(int boardPostNo);
	
	//댓글 삭제 리스트
	public int removeComment(int commentNo);
	
}
