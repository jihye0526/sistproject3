package com.project3.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.project3.vo.IdusMemberVO;
import com.project3.vo.IdusSessionVO;

public class IdusMemberDAO {
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private static String namespace="mapper.member";
	
	public boolean getInsert(IdusMemberVO vo) {
		boolean result = false;
		int value = sqlSession.insert(namespace+".join", vo);
		if(value != 0) result = true;
		return result; 
	}
	
	public IdusSessionVO getLogin(IdusMemberVO vo) {
		return sqlSession.selectOne(namespace+".login", vo);
	}
	
	public int getCount() {
		return sqlSession.selectOne(namespace+".count");
	}
	
	public ArrayList<IdusMemberVO> getList(int start, int end){
		Map<String, String> param = new HashMap<String, String>();
		param.put("start", String.valueOf(start));
		param.put("end", String.valueOf(end));
		
	    List<IdusMemberVO> list = sqlSession.selectList(namespace+".list", param);
	    
	    return (ArrayList<IdusMemberVO>)list;
	}
}
