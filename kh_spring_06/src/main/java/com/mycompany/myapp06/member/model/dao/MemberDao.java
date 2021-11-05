package com.mycompany.myapp06.member.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
//import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.myapp06.member.model.vo.Member;

@Repository("memberDao")
public class MemberDao {
	@Autowired
	private SqlSession sqlSession;
	
	public List<Member> selectMembers(){
		return sqlSession.selectList("Member.listMember");
	}
	
}
