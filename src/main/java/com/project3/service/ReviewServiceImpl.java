package com.project3.service;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.project3.dao.IdusMemberDAO;
import com.project3.dao.IdusReviewDAO;
import com.project3.vo.IdusReviewVO;

@Service("reviewService")
public class ReviewServiceImpl implements BoardService{
	@Autowired
	private IdusReviewDAO reviewDAO;
	
	@Autowired
	private IdusMemberDAO memberDAO;
	
	public Object getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getResultWrite(Object vo) {
		ModelAndView mv = new ModelAndView();
		IdusReviewVO rvo = (IdusReviewVO)vo;
		if(rvo.getFile1().getSize()!=0) {
			UUID uuid = UUID.randomUUID();
			rvo.setRfile(rvo.getFile1().getOriginalFilename());
			rvo.setRsfile(uuid+"_"+rvo.getFile1().getOriginalFilename());
			
			boolean result = reviewDAO.getInsert(rvo);
			if(result) {
				try {
					File file = new File(rvo.getSavepath()+rvo.getRsfile());
					rvo.getFile1().transferTo(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
				mv.setViewName("/mypage/my_review");
			}
		}
		return mv;
	}

	@Override
	public Object getContent(String id) {
		ModelAndView mv = new ModelAndView();
		
		IdusReviewVO vo = reviewDAO.getContent(id);
		
		mv.addObject("vo", vo);
		mv.setViewName("/admin/review/review_mng_content");
		mv.addObject("psfile", memberDAO.getPsfile("admin"));
		return mv;
	}

	@Override
	public Object getUpdate(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getResultUpdate(Object vo) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Object getList(String rpage, String param) {
		ModelAndView mv = new ModelAndView();
		
		int start = 0;
		int end = 0;
		int pageSize = 10; //한 페이지당 출력되는 row
		int pageCount = 1; //전체 페이지 수 : 전체 row / 한 페이지당 출력되는 row
		int dbCount = reviewDAO.getCount(); //DB연동 후 전체로우수 출력
		int reqPage = 1; //요청 페이지
		
		//2-2. 전체 페이지 수 구하기
		if((dbCount%pageSize) == 0){
			pageCount = dbCount/pageSize;
		}else{
			pageCount = (dbCount/pageSize)+1;
		}
		
		//2-3. start, end 값 구하기
		if(rpage != null){
			reqPage = Integer.parseInt(rpage);
			start = (reqPage - 1) * pageSize + 1;
			end = reqPage*pageSize;
		}else{
			start = reqPage;
			end = pageSize;
		}
		
		//3. DAO 객체 연동
		ArrayList<IdusReviewVO> list = reviewDAO.getList(start, end);

		//board_list.jsp 파일로 데이터 전송
		mv.addObject("list", list);
		mv.addObject("dbCount", dbCount);
		mv.addObject("pageSize", pageSize);
		mv.addObject("reqPage", reqPage);
		mv.addObject("psfile", memberDAO.getPsfile("admin"));
		
		mv.setViewName("/admin/review/review_mng_list");
		return mv;
	}

	@Override
	public int getResultDelete(String id) {
		return 0;
	}
	
	public int getResultDelete(String[] dellist) {
		int count = 0;
		if(dellist[0].equals("all")) {
			count = reviewDAO.getDeleteAll();
		}else {
			count = reviewDAO.getDeleteSelect(dellist);
		}
		return count;
	}
	public Object getreviewList(String rpage, String param) {
		ModelAndView mv = new ModelAndView();
		
		int start = 0;
		int end = 0;
		int pageSize = 10; //한 페이지당 출력되는 row
		int pageCount = 1; //전체 페이지 수 : 전체 row / 한 페이지당 출력되는 row
		int dbCount = reviewDAO.getCount(); //DB연동 후 전체로우수 출력
		int reqPage = 1; //요청 페이지
		
		//2-2. 전체 페이지 수 구하기
		if((dbCount%pageSize) == 0){
			pageCount = dbCount/pageSize;
		}else{
			pageCount = (dbCount/pageSize)+1;
		}
		
		//2-3. start, end 값 구하기
		if(rpage != null){
			reqPage = Integer.parseInt(rpage);
			start = (reqPage - 1) * pageSize + 1;
			end = reqPage*pageSize;
		}else{
			start = reqPage;
			end = pageSize;
		}
		
		//3. DAO 객체 연동
		ArrayList<IdusReviewVO> list = reviewDAO.getList(start, end);

		//board_list.jsp 파일로 데이터 전송
		mv.addObject("list", list);
		mv.addObject("dbCount", dbCount);
		mv.addObject("pageSize", pageSize);
		mv.addObject("reqPage", reqPage);
		
		mv.setViewName("/mypage/my_review");
		return mv;
	}

}
