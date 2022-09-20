package com.gd.lms.controller;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gd.lms.commons.TeamColor;
import com.gd.lms.service.IBoardService;
import com.gd.lms.vo.Board;
import com.gd.lms.vo.BoardFile;
import com.gd.lms.vo.BoardPost;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	
	//서비스 객체 생성	
	@Autowired IBoardService boardService;
	
	
	//게시판 리스트 출력 메서드
	@GetMapping("/board/list")
	public String getboardList(int lectureNo, Model model) {
		
		//파라미터 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 확인 / lectureNo : " + lectureNo);
		
		//일반 변수
		//공지사항 및 qna 게시판 번호를 위한 변수
		int noticeNo = 0;
		int qnaNo = 0;
		
		
		//넘겨줄 리스트(게시판)
		List<Board> list = boardService.getBoardList(lectureNo);
		
		//변수 세팅
		for(int i = 0; i<list.size();i++) {
			if(list.get(i).getBoardType() == 1) {
				noticeNo = list.get(i).getBoardNo();
			}
			if(list.get(i).getBoardType() == 2) {
				qnaNo = list.get(i).getBoardNo();
			}
		};

		//값 넘겨주기
		model.addAttribute("boardList",list);
		model.addAttribute("lectureNo",lectureNo);
		
		model.addAttribute("noticeNo",noticeNo);
		model.addAttribute("qnaNo",qnaNo);
		

		//디버깅
		log.debug(TeamColor.KHJ + "값 확인 / boardList list : " + list);
		log.debug(TeamColor.KHJ + "게시판 리스트 폼으로 포워딩");

		
		//포워딩
		return "board/boardList";
		
	}
	
	//게시판 게시글 출력 메서드
	@GetMapping("/board/post")
	public String getBoardPostList(int boardNo, String boardName, Model model) {
		
		//파라미터 확인 디버깅
		System.out.println("[boardCtrl] boardNo : " + boardNo);	
		System.out.println("[boardCtrl] boardName : " + boardName);	
		log.debug(TeamColor.KHJ + "값 확인 / boardNo : " + boardNo);
		log.debug(TeamColor.KHJ + "값 확인 / boardName : " + boardName);
		
		//넘겨줄 리스트(게시판)
		List<BoardPost> list = boardService.getBoardPostList(boardNo);
		
		//값 넘겨주기
		model.addAttribute("boardPostList",list);
		model.addAttribute("boardName",boardName);
		model.addAttribute("boardNo",boardNo);
		
		//디버깅
		log.debug(TeamColor.KHJ + "값 확인 / boardPost list : " + list);
		log.debug(TeamColor.KHJ + "boardPost 리스트 생성 및 포워딩");
		
		
		//포워딩
		return "board/boardPost";
		
	}
	
	//게시글 상세 페이지 출력 메서드
	@GetMapping("/board/post/one")
	public String getBoardPostOne(int boardPostNo, String boardName, int boardNo, Model model) {
		

		//파라미터 확인 디버깅
		System.out.println("[boardCtrl] boardPostNo : " + boardPostNo);	
		
		//넘겨줄 값(BoardPost)
		Map<String, Object> boardOne = boardService.getBoardPostOne(boardPostNo);
		
		//값 넘겨주기
		model.addAttribute("boardOne",boardOne);
		model.addAttribute("boardName",boardName);
		model.addAttribute("boardNo",boardNo);
		
		//결과 디버깅
		log.debug(TeamColor.KHJ + "값 확인 / boardOne : " + boardOne);
		log.debug(TeamColor.KHJ + "결과 확인 / 게시글 상세보기 폼으로 포워딩");
		
		//포워딩
		return "board/boardOne";
		
	}

	
	//게시판 추가 메서드
	@GetMapping("/board/add/form")
	public String directAddBoard(int lectureNo, Model model) {
		
		//파라미터 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 확인 / lectureNo : " + lectureNo);

		//값 넘겨주기
		model.addAttribute("lectureNo", lectureNo);
		
		//결과확인 디버깅
		log.debug(TeamColor.KHJ + "결과 확인 / 게시글 작성 폼으로 포워딩");
		
		//포워딩
		return "board/addBoard";
	}
	
	//게시판 추가 메서드
	@GetMapping("/board/add")
	public String addBoard(Board board) {
		
		//파라미터 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 확인 / board : " + board);
		
		//추가할 게시판 내용
		int row = boardService.addBoard(board);
		
		//추가 여부 확인 디버깅
		log.debug(TeamColor.KHJ + "결과 확인 / 게시판 추가 행 수 : " + row);
		log.debug(TeamColor.KHJ + "결과 확인 / 게시판 리스트로 포워딩");
		
		//리다이렉션
		return "redirect:/board/list?lectureNo="+board.getLectureNo();
	}
	
	
	//게시글 추가 폼 전송 메서드
	@GetMapping("/board/post/add/form")
	public String directAddBoardPost(int boardNo, String boardName, Model model) {

		//파라미터 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 확인 / boardNo : " + boardNo);
		
		//값 넘겨주기
		model.addAttribute("boardName",boardName);
		model.addAttribute("boardNo",boardNo);
		
		//결과 확인 디버깅
		log.debug(TeamColor.KHJ + "결과 확인 / 게시글 추가 폼으로 포워딩");
		
		return "board/addBoardPost";
	}
	
	
	
	//게시글 추가 메서드-	확인 필요
	@PostMapping("/board/post/add")
	public String addBoardPost(Board board, BoardPost boardPost, MultipartFile[] uploadFile) throws UnsupportedEncodingException {
		
		//파라미터 확인 디버깅
		log.debug(TeamColor.KHJ + "파라미터 확인 / add boardPost boardPost : " + boardPost);
		log.debug(TeamColor.KHJ + "파라미터 확인 / add boardPost uploadFile : " + uploadFile);
		log.debug(TeamColor.KHJ + "파라미터 확인 / add boardPost board : " + board);
		
		
		//게시글 추가하기
		int row = boardService.addBoardPost(boardPost);
		
		//결과 확인 디버깅
		log.debug(TeamColor.KHJ + "완료 후 값 확인 / 게시글 추가 행 수 : " + row);
		
		
		//////파일 첨부 코드
		// 변수 확인
		String path = "C:\\Users\\82102\\git\\5000_lms\\lms_5000\\src\\main\\webapp\\boardFile";
		String originFileName = "";
		String contentType = "";

		//리스트 생성
		List<BoardFile> list = new ArrayList<>();
		
		if(uploadFile != null) {
			for(MultipartFile file : uploadFile) {
				if(!file.isEmpty()) {
					//변수 세팅
					originFileName = file.getOriginalFilename();
					contentType = file.getContentType();	
					
					//객체 생성
					BoardFile tempFile = new BoardFile();
					
					//디버깅
					log.debug(TeamColor.KHJ + "값 확인 / 파일 vo 세팅 전 값 : " + tempFile);
					
					//값 세팅하기
					tempFile.setFileOriginname(originFileName);
					tempFile.setFileName(UUID.randomUUID() + "_" + originFileName);
					tempFile.setFileType(contentType);
					tempFile.setBoardPostNo(boardPost.getBoardPostNo());
					
					//디버깅
					log.debug(TeamColor.KHJ + "값 확인 / 파일 vo 세팅 후 값 : " + tempFile);
				
	
					//리스트에 추가하기
					list.add(tempFile);
			
					//파일 객체 생성
					File newFileName = new File(path + File.separator + tempFile.getFileName());
	
					//저장 파일 이름 생성(연구 중)
					String saveFileName = path + File.separator + tempFile.getFileName();
					
					//저장 파일 경로 생성 (확인 중)
					Path savePath = Paths.get(saveFileName);
					
					
					try {
						//전송하기
						file.transferTo(savePath);
						
						//첨부파일 추가하기
						int row2 =+ boardService.addBoardFile(tempFile);
						
						//결과 확인 디버깅
						log.debug(TeamColor.KHJ + "결과 확인 / 게시글 첨부파일 추가 행 수 : " + row2);
						
					} catch (Exception e) {
						//실패 디버깅
						log.debug(TeamColor.KHJ + "파일 추가 실패");
						e.printStackTrace();
					}
				
				}
				
				
			}
		}
		
		//넘겨주는 값 인코딩
		String encodedboardName= URLEncoder.encode(board.getBoardName(), "UTF-8");
		
		//결과 확인 디버깅
		log.debug(TeamColor.KHJ + "결과 확인 / 게시글 리스트로 리다이렉션");
		
		//리다이렉션
		return "redirect:/board/post?boardNo=" + board.getBoardNo() +"&boardName=" + encodedboardName;
		
	}
	
	//파일 다운로드 메서드
	@GetMapping("board/download/file")
	public ResponseEntity<Object> downloadFile(String fileName, int boardPostNo, String boardName, int boardNo) throws UnsupportedEncodingException {
		
		//파일 경로 설정
		String path = "C:\\Users\\82102\\git\\5000_lms\\lms_5000\\src\\main\\webapp\\boardFile" +"\\"+ fileName;

		//값 확인 디버깅
		log.debug(TeamColor.KHJ + "값 확인 / path: "+path);
		try {
			
			//path의 경로 객체 생성
			Path filePath = Paths.get(path);
			
			// 파일 resource 얻기
			Resource resource = new InputStreamResource(Files.newInputStream(filePath)); 
			
			//파일 객체 생성
			File file = new File(path);
			
			//헤더 객체 생성
			HttpHeaders headers = new HttpHeaders();
			
			// 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더			
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build());  
			
			//결과 확인 디버깅
			log.debug(TeamColor.KHJ + "결과 확인 / 파일 다운로드 성공");
			
			//리턴
			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
		} catch(Exception e) {
			//결과확인 디버깅
			log.debug(TeamColor.KHJ + "결과 확인 / 파일 다운로드 실패");
			
			//리턴
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
				
	}

}
