package com.project3.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.project3.dao.IdusMemberDAO;
import com.project3.vo.IdusMemberVO;
import com.project3.vo.IdusSessionVO;

@Service("memberService")
public class MemberServiceImpl {
	@Autowired
	private IdusMemberDAO memberDAO;
	
	/**
	 * 회원가입
	 */
	public String getResultJoin(IdusMemberVO vo) {
		boolean join_result = memberDAO.getInsert(vo);
		String result = "";
		
		if(join_result) {
			result = "join/join_success"; 
		}

		return result;
	}
	
	/**
	 * 로그인
	 */
	public ModelAndView getResultLogin(IdusMemberVO vo, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		IdusSessionVO svo = memberDAO.getLogin(vo);
		
		if(svo != null) {
			session.setAttribute("svo", svo);
			mv.addObject("vo", vo);
			mv.setViewName("index"); 			
		}else {
			mv.addObject("result", "�븘�씠�뵒 �샊��� 鍮꾨��踰덊샇媛� ���由쎈땲�떎.");
			mv.setViewName("/login/login");
		}
		
		return mv;
	}
}
